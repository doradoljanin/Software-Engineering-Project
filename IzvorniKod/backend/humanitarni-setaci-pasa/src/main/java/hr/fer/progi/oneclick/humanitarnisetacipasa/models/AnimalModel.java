package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.*;

public class AnimalModel {

    private long id;
    private String description;
    private String name;
    private BreedModel breed;
    private long birthYear;
    private String walkType;
    private String gender;

    public AnimalModel() {
    }


    public AnimalModel(Animal animal) {
        this.id = animal.getId();
        this.description = animal.getDescription();
        this.name = animal.getName();
        this.breed = new BreedModel(animal.getBreed());
        this.birthYear = animal.getBirthYear();
        this.walkType = animal.getWalkType().toString();
        this.gender = animal.getGender().toString();
    }

    public AnimalModel(long id, String description, String name, BreedModel breed, long birthYear, String walkType, String gender) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.breed = breed;
        this.birthYear = birthYear;
        this.walkType = walkType;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BreedModel getBreed() {
        return breed;
    }

    public void setBreed(BreedModel breed) {
        this.breed = breed;
    }

    public long getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(long birthYear) {
        this.birthYear = birthYear;
    }

    public String getWalkType() {
        return walkType;
    }

    public void setWalkType(String walkType) {
        this.walkType = walkType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
