package hr.fer.progi.oneclick.humanitarnisetacipasa.entities;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"Breeds\"")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "\"breed_name\"")
    private String breedName;


    private String height;
    private String weight;

    @Column(name = "\"life_expectancy\"")
    private String lifeExpectancy;

    private String group;

    @OneToMany(mappedBy = "breed")
    private List<Animal> animals;

    public Breed() {
    }

    public Breed(long id, String breedName, String height, String weight, String lifeExpectancy, String group) {
        this.id = id;
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lifeExpectancy = lifeExpectancy;
        this.group = group;
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
