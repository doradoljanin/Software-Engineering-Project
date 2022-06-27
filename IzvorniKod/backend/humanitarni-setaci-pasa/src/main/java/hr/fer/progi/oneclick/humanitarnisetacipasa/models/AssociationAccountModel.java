package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Animal;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssociationAccountModel {

    private long id;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String password;

    private PlaceModel place;
    private String oib;
    private String name;
    private String address;
    private List<AnimalModel> animals;
    private boolean isPublic;

    public AssociationAccountModel() {
    }

    public AssociationAccountModel(Association assoc) {
        this.id = assoc.getId();
        this.animals = assoc.getAnimals().stream().map(AnimalModel::new).collect(Collectors.toList());
        this.oib = assoc.getOib();
        this.place = new PlaceModel(assoc.getPlace());
        this.name = assoc.getName();
        this.address = assoc.getAddress();
        this.username = assoc.getUser().getUsername();
        this.email = assoc.getUser().getEmail();
        this.phoneNumber = assoc.getUser().getPhoneNumber();
        this.isPublic = assoc.getUser().isPublic();
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

    public PlaceModel getPlace() {
        return place;
    }

    public void setPlace(PlaceModel place) {
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

    public List<AnimalModel> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalModel> animals) {
        this.animals = animals;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
