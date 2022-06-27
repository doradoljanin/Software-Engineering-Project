package hr.fer.progi.oneclick.humanitarnisetacipasa.responses;


public class RegistrationValidationErrorResponse {

    private RegistrationField field;
    private ErrorType error;
    private String message;

    public RegistrationValidationErrorResponse() {

    }

    public RegistrationValidationErrorResponse(RegistrationField field, ErrorType error) {
        this(field, error, "");
    }

    public RegistrationValidationErrorResponse(RegistrationField field, ErrorType error, String message) {
        this.field = field;
        this.error = error;
        this.message = message;
    }

    public RegistrationField getField() {
        return field;
    }

    public void setField(RegistrationField field) {
        this.field = field;
    }

    public ErrorType getError() {
        return error;
    }

    public void setError(ErrorType error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
