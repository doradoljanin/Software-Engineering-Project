package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;

public class AssociationShortInfoModel {

    private Long id;
    private String name;
    private String address;
    private String place;

    public AssociationShortInfoModel() {
    }

    public AssociationShortInfoModel(Long id, String name, String address, String place) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.place = place;
    }

    public AssociationShortInfoModel(Association association) {
        this.id = association.getId();
        this.name = association.getName();
        this.address = association.getAddress();
        this.place = association.getPlace().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
