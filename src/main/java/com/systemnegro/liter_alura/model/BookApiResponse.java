package com.systemnegro.liter_alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookApiResponse(String title,
                              List<AuthorApiResponse> authors,
                              @JsonAlias("download_count") Integer downloadCount,
                              List<String> languages) {
}
