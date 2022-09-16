package com.example.film.domain.dto;

import com.example.film.domain.FilmCategory;
import com.example.film.domain.FilmId;

public record FilmDTO(FilmId filmId, String title, FilmCategory category, int year) {
}
