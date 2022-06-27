package hr.fer.progi.oneclick.humanitarnisetacipasa.repositories;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal,Long> {

    List<Animal> getByAssociationId(Long id);

}
