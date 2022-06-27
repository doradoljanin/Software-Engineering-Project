package hr.fer.progi.oneclick.humanitarnisetacipasa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "\"Users\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private byte[] picture;

    @JsonIgnore
    private String password;
    @Column(name = "\"phone_number\"")
    private String phoneNumber;

    @Column(name = "\"is_public\"", nullable = false)
    private boolean isPublic;

    public User() {
    }

    public User(long id, String username, String email, byte[] picture, String password, String phoneNumber, boolean isPublic) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isPublic = isPublic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
