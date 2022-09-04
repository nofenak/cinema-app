package com.example.screening.domain;

import com.example.film.domain.FilmAPI;
import com.example.film.domain.FilmCategory;
import com.example.film.domain.dto.AddFilmDTO;
import com.example.film.domain.dto.FilmDTO;
import com.example.screening.domain.dto.AddScreeningDTO;
import com.example.screening.domain.dto.ScreeningDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ScreeningIT {
    @Autowired
    private ScreeningAPI screeningAPI;

    @Autowired
    private FilmAPI filmAPI;

    @Test
    void should_add_screening() {
        var addedFilm = addSampleFilm();
        var addedScreening= screeningAPI.addScreening(
                sampleAddScreeningDTO(addedFilm)
        );
        assertThat(screeningAPI.readScreeningById(addedScreening.id() ) ).isEqualTo(addedScreening);
    }

    @Test
    void should_return_all_screenings() {
        var sampleFilms= addSampleFilms();
        var sampleFilmId1= sampleFilms.get(0).filmId();
        var sampleFilmId2= sampleFilms.get(1).filmId();
        var sampleScreenings= addSampleScreenings(sampleFilmId1, sampleFilmId2 );
        assertThat(screeningAPI.readAllScreenings() ).isEqualTo(sampleScreenings);
    }

    @Test
    void should_return_screenings_by_film_id() {
        var sampleFilms= addSampleFilms();
        var sampleFilmId1= sampleFilms.get(0).filmId();
        var sampleFilmId2= sampleFilms.get(1).filmId();
        addSampleScreenings(sampleFilmId1, sampleFilmId2);
        assertThat(
                screeningAPI.readScreeningsByFilmId(sampleFilmId1 )
        ).allMatch(
                screening -> screening.filmId().equals(sampleFilmId1 )
        );
    }

    @Test
    void should_return_screenings_by_date() {
        var sampleFilms= addSampleFilms();
        var sampleFilmId1= sampleFilms.get(0).filmId();
        var sampleFilmId2= sampleFilms.get(1).filmId();
        var sampleScreenings= addSampleScreenings(sampleFilmId1, sampleFilmId2);
        var date = sampleScreenings.get(0).shownAt();
        assertThat(
                screeningAPI.readAllScreeningsByDate(date)
        ).allMatch(
                screening -> screening.filmId().equals(sampleFilmId1 )
        );
    }

    private FilmDTO addSampleFilm() {
        return filmAPI.addFilm(new AddFilmDTO("Sample film", FilmCategory.COMEDY));
    }

    private List<FilmDTO> addSampleFilms() {
        var sampleFilm1= filmAPI.addFilm(new AddFilmDTO("Sample film 1", FilmCategory.COMEDY) );
        var sampleFilm2= filmAPI.addFilm(new AddFilmDTO("Sample film 2", FilmCategory.ACTION) );
        return List.of(sampleFilm1, sampleFilm2);
    }

    private static AddScreeningDTO sampleAddScreeningDTO(FilmDTO addedFilm) {
        return AddScreeningDTO
                .builder()
                .filmId(addedFilm.filmId() )
                .shownAt(LocalDateTime.parse("2022-05-05T16:30") )
                .freeSeats(100)
                .minAge(13)
                .build();
    }

    private List<ScreeningDTO> addSampleScreenings(UUID sampleFilmId1, UUID sampleFilmId2) {
        var screening1 = screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId1)
                        .shownAt(LocalDateTime.parse("2022-05-05T16:00") )
                        .freeSeats(100)
                        .minAge(13)
                        .build() );
        var screening2= screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId1 )
                        .shownAt(LocalDateTime.parse("2022-06-09T17:30") )
                        .freeSeats(100)
                        .minAge(13)
                        .build() );
        var screening3= screeningAPI.addScreening(
                AddScreeningDTO
                        .builder()
                        .filmId(sampleFilmId2 )
                        .shownAt(LocalDateTime.parse("2022-08-01T19:30") )
                        .freeSeats(100)
                        .minAge(15)
                        .build() );
        return List.of(screening1, screening2, screening3);
    }
}
