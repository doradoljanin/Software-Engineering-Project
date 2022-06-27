package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Place;

public class PlaceModel {

    private long id;
    private String name;

    public PlaceModel() {
    }

    public PlaceModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlaceModel(Place place) {
        this.id = place.getId();
        this.name = place.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
