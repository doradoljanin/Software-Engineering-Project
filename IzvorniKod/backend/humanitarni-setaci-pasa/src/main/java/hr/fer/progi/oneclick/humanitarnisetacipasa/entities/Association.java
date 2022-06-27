package hr.fer.progi.oneclick.humanitarnisetacipasa.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"Associations\"")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String oib;
    private String name;
    private String address;

    @ManyToOne
    @JoinColumn(name = "\"place_id\"")
    private Place place;

    @OneToOne
    @JoinColumn(name = "\"user_id\"")
    private User user;

    @OneToMany(mappedBy = "association")
    @JsonManagedReference
    private List<Animal> animals;

    public Association() {
    }

    public Association(long id, String oib, String name, String address, Place place, User user, List<Animal> animals) {
        this.id = id;
        this.oib = oib;
        this.name = name;
        this.address = address;
        this.place = place;
        this.user = user;
        this.animals = animals;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
