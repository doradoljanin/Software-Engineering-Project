package hr.fer.progi.oneclick.humanitarnisetacipasa.repositories;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
