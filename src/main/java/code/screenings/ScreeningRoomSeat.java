package code.screenings;

import code.screenings.dto.ScreeningSeatDto;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "SCREENINGS_SEATS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
@ToString
class ScreeningRoomSeat {

    @Id
    @Getter
    private UUID id;

    private int rowNumber;

    private int number;

    @Enumerated(EnumType.STRING)
    private ScreeningSeatStatus status;

    boolean isFree() {
        return this.status.equals(ScreeningSeatStatus.FREE);
    }

    void free() {
        this.status = ScreeningSeatStatus.FREE;
    }

    void busy() {
        this.status = ScreeningSeatStatus.BUSY;
    }

    ScreeningSeatDto toDTO() {
        return new ScreeningSeatDto(
                this.id,
                this.rowNumber,
                this.number,
                this.status.name()
        );
    }
}