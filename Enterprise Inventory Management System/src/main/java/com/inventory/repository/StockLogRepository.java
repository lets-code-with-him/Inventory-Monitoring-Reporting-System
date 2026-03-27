package com.inventory.repository;

import com.inventory.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {
    List<StockLog> findByChangeDateBetween(LocalDateTime start, LocalDateTime end);
    List<StockLog> findByProductId(Integer productId);
    List<StockLog> findAllByOrderByChangeDateDesc();
}