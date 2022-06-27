package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.exceptions.ValidationException;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationAccountModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.CitizenAccountModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.AssociationAccountRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.CitizenAccountRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.ErrorType;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.RegistrationField;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.RegistrationValidationErrorResponse;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import hr.fer.progi.oneclick.humanitarnisetacipasa.services.AccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class AccountController {

    private final AssociationRepository assocRepo;
    private final CitizenRepository citizenRepo;
    private final AccountService accountService;

    public AccountController(AssociationRepository assocRepo, CitizenRepository citizenRepo, AccountService accountService) {
        this.assocRepo = assocRepo;
        this.citizenRepo = citizenRepo;
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<?> getProfile() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASSOCIATION"))) {
            return ResponseEntity.ok(new AssociationAccountModel(assocRepo.getByUserId(user.getId())));
        } else if (user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
            return ResponseEntity.ok(new CitizenAccountModel(citizenRepo.getByUserId(user.getId())));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @PostMapping("/citizen/edit")
    public ResponseEntity<?> editCitizenProfile(@RequestBody CitizenAccountRequest request) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Citizen citizen = this.citizenRepo.getByUserId(user.getId());

            try {
                this.accountService.updateCitizen(citizen, request);
                return ResponseEntity.ok().build();
            } catch (DataIntegrityViolationException e) {
                String message = e.getMostSpecificCause().getMessage();
                if (message.contains("uq_users_username")) {
                    return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.USERNAME, ErrorType.NON_UNIQUE));
                } else if (message.contains("uq_users_email")) {
                    return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.EMAIL, ErrorType.NON_UNIQUE));
                }
                throw new IllegalStateException("Unexpected value: " + e.getMostSpecificCause().getMessage());
            } catch (ValidationException e) {
                return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(e.getField(), e.getError(), e.getMessage()));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ASSOCIATION')")
    @PostMapping("/association/edit")
    public ResponseEntity<?> editAssociationProfile(@RequestBody AssociationAccountRequest request) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Association association = this.assocRepo.getByUserId(user.getId());

            try {
                this.accountService.updateAssociation(association, request);
                return ResponseEntity.ok().build();
            } catch (DataIntegrityViolationException e) {
                String message = e.getMostSpecificCause().getMessage();
                if (message.contains("uq_users_username")) {
                    return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.USERNAME, ErrorType.NON_UNIQUE));
                } else if (message.contains("uq_users_email")) {
                    return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(RegistrationField.EMAIL, ErrorType.NON_UNIQUE));
                }
                throw new IllegalStateException("Unexpected value: " + e.getMostSpecificCause().getMessage());
            } catch (ValidationException e) {
                return ResponseEntity.badRequest().body(new RegistrationValidationErrorResponse(e.getField(), e.getError(), e.getMessage()));
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
