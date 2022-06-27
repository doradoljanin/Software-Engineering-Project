package hr.fer.progi.oneclick.humanitarnisetacipasa.requests;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;

public class AssociationAccountRequest {

    private String oib;
    private String name;
    private String address;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Long place;
    private boolean isPublic;

    public AssociationAccountRequest() {
    }

    public AssociationAccountRequest(Association association) {
        this.oib = association.getOib();
        this.name = association.getName();
        this.address = association.getAddress();
        this.username = association.getUser().getUsername();
        this.email = association.getUser().getEmail();
        this.phoneNumber = association.getUser().getPhoneNumber();
        this.place = association.getPlace().getId();
    }

    public AssociationAccountRequest(String oib, String name, String address, String password, String username, String email, String phoneNumber, Long place) {
        this.oib = oib;
        this.name = name;
        this.address = address;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.place = place;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
