package code.screenings.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.With;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@With
public record ScreeningCreatingRequest(
        @NotNull
        @Schema(type = "string", example = "2022-01-01T16:30")
        LocalDateTime date,
        @NotNull
        Integer minAge,
        @NotNull
        UUID filmId,
        @NotNull
        UUID roomId
) {
}
