package hr.fer.progi.oneclick.humanitarnisetacipasa.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"Walks\"")
public class Walk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Integer duration;
    @Column(name = "\"start_time\"")
    private LocalDateTime startTime;
    @Column(name = "\"walk_type\"")
    @Enumerated(EnumType.STRING)
    private WalkType walkType;
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToMany
    @JoinTable(
            name = "\"Walk_Animal\"",
            joinColumns = @JoinColumn(name = "walk_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    private List<Animal> animals;

    public Walk() {
    }

    public Walk(long id, Citizen citizen, Integer duration, LocalDateTime startTime, WalkType walkType, List<Animal> animals) {
        this.id = id;
        this.citizen = citizen;
        this.duration = duration;
        this.startTime = startTime;
        this.walkType = walkType;
        this.animals = animals;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public WalkType getWalkType() {
        return walkType;
    }

    public void setWalkType(WalkType walkType) {
        this.walkType = walkType;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals (List<Animal> animals) {
        this.animals = animals;
    }
}