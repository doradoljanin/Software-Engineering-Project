package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class CitizenAccountModel {

    private long id;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String password;

    private String firstName;
    private String lastName;
    private boolean isPublic;

    public CitizenAccountModel() {
    }

    public CitizenAccountModel(Citizen citizen) {
        this.id = citizen.getId();
        this.username = citizen.getUser().getUsername();
        this.email = citizen.getUser().getEmail();
        this.phoneNumber = citizen.getUser().getPhoneNumber();
        this.firstName = citizen.getFirstName();
        this.lastName = citizen.getLastName();
        this.isPublic = citizen.getUser().isPublic();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
