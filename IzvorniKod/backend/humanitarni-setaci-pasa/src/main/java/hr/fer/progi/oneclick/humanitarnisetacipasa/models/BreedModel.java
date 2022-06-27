package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Breed;

public class BreedModel {

    private long id;
    private String breedName;
    private String height;
    private String weight;
    private String lifeExpectancy;
    private String group;

    public BreedModel() {
    }

    public BreedModel(long id, String breedName, String height, String weight, String lifeExpectancy, String group) {
        this.id = id;
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lifeExpectancy = lifeExpectancy;
        this.group = group;
    }

    public BreedModel(Breed breed) {
        this.id = breed.getId();
        this.breedName = breed.getBreedName();
        this.height = breed.getHeight();
        this.weight = breed.getWeight();
        this.lifeExpectancy = breed.getLifeExpectancy();
        this.group = breed.getGroup();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(String lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
