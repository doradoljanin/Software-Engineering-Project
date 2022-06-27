package hr.fer.progi.oneclick.humanitarnisetacipasa.requests;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Animal;

public class EditAnimalRequest {

    private long id;
    private String description;
    private String name;
    private long breedId;
    private int birthYear;
    private String walkType;
    private String gender;

    public EditAnimalRequest() {

    }

    public EditAnimalRequest(long id, String description, String name, long breedId, int birthYear, String walkType, String gender) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.breedId = breedId;
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

    public long getBreedId() {
        return breedId;
    }

    public void setBreedId(long breedId) {
        this.breedId = breedId;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
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
