package com.example.screening;

import com.example.screening.dto.AddScreeningRoomDTO;
import com.example.screening.exception.WrongScreeningYearException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScreeningIT extends ScreeningTestSpec {

    @Test
    void should_add_screening() {
        var addedFilm = addSampleFilm();
        var addedScreening = screeningFacade.add(
                sampleAddScreeningDTO(addedFilm.id()),
                currentYear
        );
        assertThat(
                screeningFacade.readScreening(addedScreening.id())
        ).isEqualTo(addedScreening);
    }

    @Test
    void should_throw_exception_when_new_screening_year_is_not_current_or_previous_one() {
        var addedFilm = addSampleFilm();
        assertThrows(
                WrongScreeningYearException.class,
                () -> screeningFacade.add(
                        sampleAddScreeningDTOwithWrongFilmYear(addedFilm),
                        currentYear
                )
        );
    }

    @Test
    void should_return_all_screenings() {
        var sampleFilm = addSampleFilm();
        var sampleScreenings = addSampleScreenings(sampleFilm.id());
        assertThat(
                screeningFacade.readAll()
        ).isEqualTo(sampleScreenings);
    }

    @Test
    void should_return_screenings_by_film_id() {
        var sampleFilms = addSampleFilms();
        var sampleFilmId1 = sampleFilms.get(0).id();
        var sampleFilmId2 = sampleFilms.get(1).id();
        addSampleScreening(sampleFilmId1);
        addSampleScreening(sampleFilmId2);
        assertThat(
                screeningFacade.readAllByFilmId(sampleFilmId1)
        ).allMatch(
                screening -> screening.filmId().equals(sampleFilmId1)
        );
    }

    @Test
    void should_return_screenings_by_date() {
        var sampleFilm = addSampleFilm();
        var sampleScreenings = addSampleScreenings(sampleFilm.id());
        var sampleDate = sampleScreenings
                .get(0)
                .date();
        assertThat(
                screeningFacade.readAllByDate(ScreeningDate.of(sampleDate, currentYear))
        ).allMatch(
                screening -> screening.date().equals(sampleDate)
        );
    }

    @Test
    void should_add_screening_room() {
        var sampleDTO= AddScreeningRoomDTO
                .builder()
                .number(1)
                .freeSeats(200)
                .build();
        var screeningRoomDTO = screeningFacade.addRoom(sampleDTO);
        var addedRoom= screeningFacade.readRoom(screeningRoomDTO.uuid());
        assertThat(addedRoom.number()).isEqualTo(sampleDTO.number());
        assertThat(addedRoom.freeSeats()).isEqualTo(sampleDTO.freeSeats());
    }
}

