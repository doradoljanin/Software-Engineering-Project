package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Animal;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssociationModel {

    private String oib;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private PlaceModel place;
    private List<AnimalModel> animals;

    public AssociationModel() {
    }

    public AssociationModel(Association association) {
        this.oib = association.getOib();
        this.name = association.getName();
        this.address = association.getAddress();
        this.email = association.getUser().getEmail();
        this.phoneNumber = association.getUser().getPhoneNumber();
        this.place = new PlaceModel(association.getPlace());
        this.animals = association.getAnimals().stream().sorted(Animal::compareTo).map(AnimalModel::new).collect(Collectors.toList());
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

    public PlaceModel getPlace() {
        return place;
    }

    public void setPlace(PlaceModel place) {
        this.place = place;
    }

    public List<AnimalModel> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalModel> animals) {
        this.animals = animals;
    }
}
