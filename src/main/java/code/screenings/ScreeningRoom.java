package code.screenings;

import code.screenings.dto.ScreeningRoomDTO;
import code.screenings.exception.ScreeningNoFreeSeatsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "SCREENING_ROOMS")
@EqualsAndHashCode(of = "uuid")
@ToString
class ScreeningRoom {

    @Id
    @Getter
    private UUID uuid = UUID.randomUUID();

    private int number;

    private int freeSeats;

    protected ScreeningRoom() {
    }

    ScreeningRoom(int number, int freeSeats) {
        this.number = number;
        this.freeSeats = freeSeats;
    }

    void decreaseFreeSeatsByOne(Long screeningId) {
        if (this.freeSeats == 0) {
            throw new ScreeningNoFreeSeatsException(screeningId);
        } else {
            this.freeSeats = this.freeSeats - 1;
        }
    }

    void increaseFreeSeatsByOne() {
        this.freeSeats = this.freeSeats + 1;
    }

    int currentFree() {
        return this.freeSeats;
    }

    ScreeningRoomDTO toDTO() {
        return new ScreeningRoomDTO(this.uuid, number, freeSeats);
    }
}