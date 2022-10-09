package com.example.screening.domain;

import com.example.film.domain.FilmFacade;
import com.example.film.domain.exception.FilmNotFoundException;
import com.example.screening.domain.dto.AddScreeningDTO;
import com.example.screening.domain.dto.ScreeningTicketDataDTO;
import com.example.screening.domain.dto.ScreeningDTO;
import com.example.screening.domain.exception.NoScreeningFreeSeatsException;
import com.example.screening.domain.exception.ScreeningNotFoundException;
import com.example.ticket.domain.exception.TooLateToCancelTicketReservationException;
import com.example.ticket.domain.exception.WrongTicketAgeException;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Year;
import java.util.List;

@AllArgsConstructor
public class ScreeningFacade {

    private final ScreeningRepository screeningRepository;
    private final FilmFacade filmFacade;

    public ScreeningDTO add(AddScreeningDTO dto, Year currentYear) {
        if (filmFacade.isPresent(dto.filmId())) {
            var screening = new Screening(
                    ScreeningDate.of(dto.date(), currentYear),
                    FreeSeats.of(dto.freeSeats()),
                    MinAge.of(dto.minAge()),
                    dto.filmId()
            );
            return screeningRepository
                    .save(screening)
                    .toDTO();
        } else {
            throw new FilmNotFoundException(dto.filmId());
        }
    }

    public ScreeningDTO readScreening(Long screeningId) {
        return getScreeningOrThrowException(screeningId).toDTO();
    }

    public List<ScreeningDTO> readAll() {
        return screeningRepository
                .findAll()
                .stream()
                .map(Screening::toDTO)
                .toList();
    }

    public List<ScreeningDTO> readAllByFilmId(Long filmId) {
        return screeningRepository
                .findAllByFilmId(filmId)
                .stream()
                .map(Screening::toDTO)
                .toList();
    }

    public List<ScreeningDTO> readAllByDate(ScreeningDate date) {
        return screeningRepository
                .findByDate(date)
                .stream()
                .map(Screening::toDTO)
                .toList();
    }

    public void checkReservationPossibility(Long screeningId, int age) {
        var screening = getScreeningOrThrowException(screeningId);
        if (!screening.hasFreeSeats()) {
            throw new NoScreeningFreeSeatsException(screeningId);
        }
        if (!screening.isAgeEnough(age)) {
            throw new WrongTicketAgeException(age);
        }
    }

    public void decreaseFreeSeatsByOne(Long screeningId) {
        var screening = getScreeningOrThrowException(screeningId);
        screening.decreaseFreeSeatsByOne();
    }

    public ScreeningTicketDataDTO readScreeningTicketData(Long screeningId) {
        var screening= getScreeningOrThrowException(screeningId).toDTO();
        return new ScreeningTicketDataDTO(screening.date(), screening.minAge(), screening.freeSeats());
    }

    private Screening getScreeningOrThrowException(Long screeningId) {
        return screeningRepository
                .findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundException(screeningId));
    }
}