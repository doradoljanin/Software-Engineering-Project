package hr.fer.progi.oneclick.humanitarnisetacipasa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"Citizens\"")
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "\"first_name\"")
    private String firstName;

    @Column(name = "\"last_name\"")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "\"user_id\"", unique = true)
    private User user;

    @OneToMany(mappedBy = "citizen",fetch = FetchType.LAZY)
    private List<Walk> walks;

    public Citizen() {
    }

    public Citizen(long id, String firstName, String lastName, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Walk> getWalks() {
        return walks;
    }

    public void setWalks(List<Walk> walks) {
        this.walks = walks;
    }
}
