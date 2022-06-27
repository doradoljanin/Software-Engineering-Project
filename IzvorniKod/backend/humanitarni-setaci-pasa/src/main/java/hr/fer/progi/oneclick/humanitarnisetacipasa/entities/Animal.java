package hr.fer.progi.oneclick.humanitarnisetacipasa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"Animals\"")
public class Animal implements Comparable<Animal> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private byte[] picture;
    private String description;
    private String name;

    @ManyToOne
    @JoinColumn(name = "\"breed_id\"")
    private Breed breed;

    @Column(name = "\"birthyear\"")
    private long birthYear;

    @Column(name = "\"preferred_walk_type\"")
    @Enumerated(EnumType.STRING)
    private WalkType walkType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "\"association_id\"")
    @JsonBackReference
    private Association association;

    @ManyToMany (mappedBy = "animals")
    private List<Walk> walks;

    public Animal() {
    }

    public Animal(long id, byte[] picture, String description, String name, Breed breed, long birthYear, WalkType walkType, Gender gender, Association association) {
        this.id = id;
        this.picture = picture;
        this.description = description;
        this.name = name;
        this.breed = breed;
        this.birthYear = birthYear;
        this.walkType = walkType;
        this.gender = gender;
        this.association = association;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public long getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(long birthYear) {
        this.birthYear = birthYear;
    }

    public WalkType getWalkType() {
        return walkType;
    }

    public void setWalkType(WalkType walkType) {
        this.walkType = walkType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public List<Walk> getWalks() {
        return walks;
    }

    public void setWalks(List<Walk> walks) {
        this.walks = walks;
    }

    @Override
    public int compareTo(Animal o) {
        LocalDateTime timeStart = LocalDateTime.now().minusDays(30);
        long a = this.getWalks().stream().filter(walk -> walk.getStartTime().isAfter(timeStart)).filter(walk -> walk.getStartTime().isBefore(LocalDateTime.now())).count();
        long b = o.getWalks().stream().filter(walk -> walk.getStartTime().isAfter(timeStart)).filter(walk -> walk.getStartTime().isBefore(LocalDateTime.now())).count();
        return (int) (a - b);
    }
}
