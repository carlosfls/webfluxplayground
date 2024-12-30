package com.carlosacademic.webfluxplayground.common.dto;

public record CalculatorResponseDTO(Integer first,
                                    Integer second,
                                    String operation,
                                    Double result) {
}
