package code.ticket;

import code.ticket.dto.TicketDTO;
import code.ticket.exception.TicketAlreadyCancelledException;
import code.ticket.exception.TooLateToCancelTicketException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TICKETS")
@EqualsAndHashCode(of = "uuid")
@ToString
class Ticket {

    private static final BigDecimal TICKET_BASIC_PRIZE = new BigDecimal("10.0");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private String firstName;

    private String lastName;

    private BigDecimal prize = TICKET_BASIC_PRIZE;

    private TicketStatus status = TicketStatus.OPEN;

    @Getter
    private Long screeningId;

    protected Ticket() {
    }

    Ticket(String firstName, String lastName, Long screeningId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.screeningId = screeningId;
    }

    void cancel(LocalDateTime screeningDate, Clock clock) {
        if (this.status.equals(TicketStatus.CANCELLED)) {
            throw new TicketAlreadyCancelledException(this.uuid);
        }
        var currentDate = LocalDateTime.now(clock);
        var differenceBetweenCurrentDateAndScreeningOne = Duration
                .between(screeningDate, currentDate)
                .abs()
                .toHours();
        if (differenceBetweenCurrentDateAndScreeningOne < 24) {
            throw new TooLateToCancelTicketException();
        }
        this.status = TicketStatus.CANCELLED;
    }

    TicketDTO toDTO() {
        return TicketDTO
                .builder()
                .ticketId(this.id)
                .ticketUuid(this.uuid)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .prize(this.prize)
                .status(this.status)
                .screeningId(this.screeningId)
                .build();
    }
}
