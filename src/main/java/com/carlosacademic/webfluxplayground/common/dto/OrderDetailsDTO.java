package com.carlosacademic.webfluxplayground.common.dto;

import java.time.LocalDate;
import java.util.UUID;

public record OrderDetailsDTO(
        UUID orderId,
        String customerName,
        String productName,
        Integer amount,
        LocalDate orderDate
) {
}
