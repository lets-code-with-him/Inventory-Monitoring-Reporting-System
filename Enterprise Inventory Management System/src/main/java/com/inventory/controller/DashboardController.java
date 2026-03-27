package com.inventory.controller;

import com.inventory.entity.Product;
import com.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProductService productService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        // Dashboard cards
        model.addAttribute("totalProducts",
                productService.getTotalProductCount());

        model.addAttribute("totalInventoryValue",
                productService.getTotalInventoryValue());

        model.addAttribute("lowStockCount",
                productService.getLowStockCount());


        // Chart data
        List<Product> products = productService.getAllProducts();

        List<String> names = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        int low = 0;
        int medium = 0;
        int high = 0;

        for (Product p : products) {

            names.add(p.getName());
            quantities.add(p.getQuantity());

            if (p.getQuantity() <= p.getReorderLevel()) {
                low++;
            }
            else if (p.getQuantity() <= p.getReorderLevel() * 3) {
                medium++;
            }
            else {
                high++;
            }
        }

        model.addAttribute("productNames", names);
        model.addAttribute("productQuantities", quantities);

        model.addAttribute("lowStock", low);
        model.addAttribute("mediumStock", medium);
        model.addAttribute("highStock", high);

        return "dashboard";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
