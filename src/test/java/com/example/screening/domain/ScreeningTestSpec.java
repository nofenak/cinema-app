package com.example.screening.domain;

import com.example.film.domain.FilmTestSpec;
import com.example.film.domain.dto.FilmDTO;
import com.example.screening.domain.dto.AddScreeningDTO;
import com.example.screening.domain.dto.ScreeningDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ScreeningTestSpec extends FilmTestSpec {

    @Autowired
    public ScreeningAPI screeningAPI;

    public static AddScreeningDTO sampleAddScreeningDTO(FilmDTO addedFilm) {
        return AddScreeningDTO
                .builder()
                .filmId(addedFilm.filmId())
                .date(LocalDateTime.parse("2022-05-05T16:30"))
                .freeSeats(100)
                .minAge(13)
                .build();
    }

    public ScreeningDTO addSampleScreening(UUID sampleFilmId) {
        return screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId)
                        .date(LocalDateTime.parse("2022-05-05T16:30"))
                        .freeSeats(100)
                        .minAge(13)
                        .build());
    }

    public ScreeningDTO addSampleScreening(UUID sampleFilmId, LocalDateTime screeningDate) {
        return screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId)
                        .date(screeningDate)
                        .freeSeats(100)
                        .minAge(13)
                        .build());
    }

    public ScreeningDTO addSampleScreeningWithNoFreeSeats(UUID sampleFilmId) {
        return screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId)
                        .date(LocalDateTime.parse("2022-05-05T16:30"))
                        .freeSeats(0)
                        .minAge(13)
                        .build());
    }

    public List<ScreeningDTO> addSampleScreenings(UUID sampleFilmId1, UUID sampleFilmId2) {
        var screening1 = screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId1)
                        .date(LocalDateTime.parse("2022-05-05T16:00"))
                        .freeSeats(100)
                        .minAge(13)
                        .build());
        var screening2 = screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId1)
                        .date(LocalDateTime.parse("2022-06-09T17:30"))
                        .freeSeats(100)
                        .minAge(13)
                        .build());
        var screening3 = screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId2)
                        .date(LocalDateTime.parse("2022-08-01T19:30"))
                        .freeSeats(100)
                        .minAge(15)
                        .build());
        return List.of(screening1, screening2, screening3);
    }
}
