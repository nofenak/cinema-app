package code.films.dto;

import lombok.With;

import javax.validation.constraints.NotNull;

@With
public record AddFilmDto(
        @NotNull
        String title,

        @NotNull
        FilmCategoryDto filmCategory,
        @NotNull
        Integer year
) {
}
