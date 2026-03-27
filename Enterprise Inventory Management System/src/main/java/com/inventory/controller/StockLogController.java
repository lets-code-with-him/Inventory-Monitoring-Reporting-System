package com.inventory.controller;

import com.inventory.entity.StockLog;
import com.inventory.repository.StockLogRepository;
import com.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockLogController {

    private final ProductService productService;
    private final StockLogRepository stockLogRepository;

    // Default logs page
    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("logs", stockLogRepository.findAllByOrderByChangeDateDesc());
        return "logs";
    }

    // Filter by date or product
    @GetMapping("/logs/filter")
    public String filterLogs(
            @RequestParam(required = false) Integer productId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model
    ) {

        List<StockLog> logs;

        if (productId != null) {
            // Filter by product, newest first
            logs = stockLogRepository.findByProductId(productId)
                    .stream()
                    .sorted((a, b) -> b.getChangeDate().compareTo(a.getChangeDate()))
                    .toList();
        } else if (date != null) {
            // Filter by date, newest first
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();
            logs = productService.getLogsBetween(start, end)
                    .stream()
                    .sorted((a, b) -> b.getChangeDate().compareTo(a.getChangeDate()))
                    .toList();
        } else {
            // Default: all logs
            logs = stockLogRepository.findAllByOrderByChangeDateDesc();
        }

        model.addAttribute("logs", logs);
        return "logs";
    }
}