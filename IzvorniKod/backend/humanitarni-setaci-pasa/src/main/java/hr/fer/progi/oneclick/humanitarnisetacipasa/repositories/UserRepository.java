package hr.fer.progi.oneclick.humanitarnisetacipasa.repositories;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1")
    Optional<User> getByUsername(String username);

    @Query("select u from User u where u.email = ?1")
    User getByEmail(String email);

    @Query("select u from User u where u.username = ?1 or u.email = ?1")
    User getByUsernameOrEmail(String identifier);
}
