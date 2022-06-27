package hr.fer.progi.oneclick.humanitarnisetacipasa.repositories;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkRepository extends JpaRepository<Walk, Long> {
}
