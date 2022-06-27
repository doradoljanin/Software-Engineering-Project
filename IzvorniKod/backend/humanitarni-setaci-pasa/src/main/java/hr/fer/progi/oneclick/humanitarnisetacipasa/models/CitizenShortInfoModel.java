package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;

public class CitizenShortInfoModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public CitizenShortInfoModel() {
    }

    public CitizenShortInfoModel(Long id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public CitizenShortInfoModel(Citizen c) {
        this.id = c.getId();
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.phoneNumber = c.getUser().getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
