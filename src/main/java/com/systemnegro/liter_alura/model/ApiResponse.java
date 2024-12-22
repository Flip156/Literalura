package com.systemnegro.liter_alura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(List<BookApiResponse> results) {
}
