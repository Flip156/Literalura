package com.systemnegro.liter_alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorApiResponse(
        String name,
        @JsonAlias("birth_year") Integer birthYear,
        @JsonAlias("death_year") Integer deathYear) {
}
