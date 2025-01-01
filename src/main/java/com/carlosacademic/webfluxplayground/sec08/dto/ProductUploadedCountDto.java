package com.carlosacademic.webfluxplayground.sec08.dto;

import java.util.UUID;

public record ProductUploadedCountDto(UUID uploadId, Long productsUploaded) {
}
