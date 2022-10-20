package code.screening.exception;

public class ScreeningNotFoundException extends ScreeningException {

    public ScreeningNotFoundException(Long screeningId) {
        super("Screening not found: " + screeningId);
    }
}
