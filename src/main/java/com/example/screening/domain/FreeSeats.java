package com.example.screening.domain;

import com.example.screening.domain.exception.WrongScreeningFreeSeatsQuantityException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static com.example.screening.domain.ScreeningValues.SCREENING_MAX_FREE_SEATS;
import static com.example.screening.domain.ScreeningValues.SCREENING_MIN_FREE_SEATS;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class FreeSeats {

    private int value;

    static FreeSeats of(int value) {
        if (value == 0) {
            return new FreeSeats(value);
        } else if (value < SCREENING_MIN_FREE_SEATS || value > SCREENING_MAX_FREE_SEATS) {
            throw new WrongScreeningFreeSeatsQuantityException();
        } else {
            return new FreeSeats(value);
        }
    }
}