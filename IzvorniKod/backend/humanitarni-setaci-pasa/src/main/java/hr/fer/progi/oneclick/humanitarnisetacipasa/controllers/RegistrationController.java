package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.exceptions.ValidationException;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.AssociationAccountRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.CitizenAccountRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.ErrorType;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.RegistrationField;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.RegistrationValidationErrorResponse;
import hr.fer.progi.oneclick.humanitarnisetacipasa.services.AccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/api/auth/association/register")
    public ResponseEntity<?> registerAssociation(@RequestBody AssociationAccountRequest associationAccountRequest) {
        try {
            Association newAssociation = accountService.registerAssociation(associationAccountRequest);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message.contains("uq_users_username")) {
                return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.USERNAME, ErrorType.NON_UNIQUE));
            } else if (message.contains("uq_users_email")) {
                return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.EMAIL, ErrorType.NON_UNIQUE));
            }
            throw new IllegalStateException("Unexpected value: " + e.getMostSpecificCause().getMessage().matches("[.*?]"));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(e.getField(), e.getError(), e.getMessage()));
        }
    }

    @PostMapping(value = "/api/auth/citizen/register")
    public ResponseEntity<?> registerCitizen(@RequestBody CitizenAccountRequest citizenAccountRequest) {
        try {
            Citizen newCitizen = accountService.registerCitizen(citizenAccountRequest);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message.contains("uq_users_username")) {
                return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.USERNAME, ErrorType.NON_UNIQUE));
            } else if (message.contains("uq_users_email")) {
                return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.EMAIL, ErrorType.NON_UNIQUE));
            }
            throw new IllegalStateException("Unexpected value: " + e.getMostSpecificCause().getMessage().matches("[.*?]"));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(e.getField(), e.getError(), e.getMessage()));
        }
    }
}
