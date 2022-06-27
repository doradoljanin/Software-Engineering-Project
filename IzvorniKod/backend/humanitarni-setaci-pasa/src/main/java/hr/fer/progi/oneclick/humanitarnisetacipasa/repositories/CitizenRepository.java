package hr.fer.progi.oneclick.humanitarnisetacipasa.repositories;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    boolean existsCitizenByUser(User user);

    Citizen getByUserId(Long id);

}
