export default class RegistrationValidationError {
    field: string;
    error: string;
    message: string;

    constructor(field: string, error: string, message: string){
        this.field = field;
        this.error = error;
        this.message = message;
    }
}