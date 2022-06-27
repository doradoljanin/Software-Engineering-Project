package hr.fer.progi.oneclick.humanitarnisetacipasa.services;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Place;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;
import hr.fer.progi.oneclick.humanitarnisetacipasa.exceptions.ValidationException;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.PlaceRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.UserRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.AssociationAccountRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.CitizenAccountRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.ErrorType;
import hr.fer.progi.oneclick.humanitarnisetacipasa.responses.RegistrationField;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AssociationRepository associationRepository;
    private final PlaceRepository placeRepository;
    private final CitizenRepository citizenRepository;

    public AccountService(UserRepository userRepository, AssociationRepository associationRepository, PlaceRepository placeRepository, CitizenRepository citizenRepository) {
        this.userRepository = userRepository;
        this.associationRepository = associationRepository;
        this.placeRepository = placeRepository;
        this.citizenRepository = citizenRepository;
    }

    public Association registerAssociation(AssociationAccountRequest association) throws ValidationException {
        User user = registerUser(association);
        Association newAssociation = new Association();

        newAssociation.setAddress(association.getAddress());
        newAssociation.setName(association.getName());
        newAssociation.setOib(association.getOib());

        Place place = placeRepository.getOne(association.getPlace());
        newAssociation.setPlace(place);

        newAssociation.setUser(user);

        return associationRepository.save(newAssociation);
    }

    public Association updateAssociation(Association association, AssociationAccountRequest associationRequest) throws ValidationException {
        updateUser(association.getUser(), associationRequest.getUsername(), associationRequest.getEmail(), associationRequest.getPassword(), associationRequest.getPhoneNumber(), associationRequest.getIsPublic());

        if (associationRequest.getAddress() != null && !associationRequest.getAddress().isBlank())
            association.setAddress(associationRequest.getAddress());
        if (associationRequest.getName() != null && !associationRequest.getName().isBlank())
            association.setName(associationRequest.getName());
        if (associationRequest.getOib() != null && !associationRequest.getOib().isBlank())
            association.setOib(associationRequest.getOib());
        if (associationRequest.getPlace() != null && associationRequest.getPlace() != 0L) {
            Place place = placeRepository.getOne(associationRequest.getPlace());
            association.setPlace(place);
        }

        return associationRepository.save(association);
    }

    public Citizen registerCitizen(CitizenAccountRequest citizenAccountRequest) throws ValidationException {
        User user = registerUser(citizenAccountRequest);
        Citizen newCitizen = new Citizen();

        newCitizen.setFirstName(citizenAccountRequest.getFirstName());
        newCitizen.setLastName(citizenAccountRequest.getLastName());

        newCitizen.setUser(user);

        return citizenRepository.save(newCitizen);
    }

    public Citizen updateCitizen(Citizen citizen, CitizenAccountRequest citizenAccountRequest) throws ValidationException {
        updateUser(citizen.getUser(), citizenAccountRequest.getUsername(), citizenAccountRequest.getEmail(), citizenAccountRequest.getPassword(), citizenAccountRequest.getPhoneNumber(), citizenAccountRequest.getIsPublic());

        if (citizenAccountRequest.getFirstName() != null && !citizenAccountRequest.getFirstName().isBlank())
            citizen.setFirstName(citizenAccountRequest.getFirstName());
        if (citizenAccountRequest.getLastName() != null && !citizenAccountRequest.getLastName().isBlank())
            citizen.setLastName(citizenAccountRequest.getLastName());

        return citizenRepository.save(citizen);
    }

    public User registerUser(AssociationAccountRequest association) throws ValidationException {
        return registerUser(association.getUsername(), association.getEmail(), association.getPassword(), association.getPhoneNumber());
    }

    public User registerUser(CitizenAccountRequest citizenAccountRequest) throws ValidationException {
        return registerUser(citizenAccountRequest.getUsername(), citizenAccountRequest.getEmail(), citizenAccountRequest.getPassword(), citizenAccountRequest.getPhoneNumber());
    }

    private User registerUser(String username, String email, String password, String phoneNumber) throws ValidationException {
        validate(username, email, password, phoneNumber);
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPublic(false);

        String passHash = new BCryptPasswordEncoder().encode(password);
        user.setPassword(passHash);

        return userRepository.save(user);
    }

    private User updateUser(User user, String username, String email, String password, String phoneNumber, boolean aPublic) throws ValidationException {
        if (username == null || username.isBlank())
            username = user.getUsername();
        if (email == null || email.isBlank())
            email = user.getEmail();
        if (password == null || password.isBlank())
            password = null;
        if (phoneNumber == null || phoneNumber.isBlank())
            phoneNumber = user.getPhoneNumber();

        validate(username, email, password, phoneNumber);

        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPublic(aPublic);

        if (password != null) {
            String passHash = new BCryptPasswordEncoder().encode(password);
            user.setPassword(passHash);
        }

        return userRepository.save(user);
    }

    private void validate(String username, String email, String password, String phoneNumber) throws ValidationException {
        if (!username.toUpperCase().matches("^[A-Z0-9._\\-]{6,20}$")) {
            throw new ValidationException("Neispravan format usernamea.", RegistrationField.USERNAME, ErrorType.INVALID_FORMAT);
        }
        if (password != null && !password.toUpperCase().matches("^[A-Z0-9.!_\\-$]{8,30}$")) {
            throw new ValidationException("Neispravan format passworda.", RegistrationField.PASSWORD, ErrorType.INVALID_FORMAT);
        }
        if (!email.toUpperCase().matches("^[A-Z0-9._%+\\-]+@[A-Z0-9.\\-]+\\.[A-Z]{2,}$")) {
            throw new ValidationException("Neispravan format emaila.", RegistrationField.EMAIL, ErrorType.INVALID_FORMAT);
        }
        if (phoneNumber.startsWith("+385")) {
            if (phoneNumber.length() < 12 || phoneNumber.length() > 13) {
                throw new ValidationException("Neispravan format broja.", RegistrationField.PHONE_NUMBER, ErrorType.INVALID_FORMAT);
            }
        } else if (phoneNumber.startsWith("00385")) {
            if (phoneNumber.length() < 13 || phoneNumber.length() > 14) {
                throw new ValidationException("Neispravan format broja.", RegistrationField.PHONE_NUMBER, ErrorType.INVALID_FORMAT);
            }
        } else {
            if (phoneNumber.length() < 9 || phoneNumber.length() > 10) {
                throw new ValidationException("Neispravan format broja.", RegistrationField.PHONE_NUMBER, ErrorType.INVALID_FORMAT);
            }
        }
        try {
            Double.parseDouble(phoneNumber);
        } catch (NumberFormatException e) {
            throw new ValidationException("Neispravan format broja.", RegistrationField.PHONE_NUMBER, ErrorType.INVALID_FORMAT);
        }
    }
}
