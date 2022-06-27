package hr.fer.progi.oneclick.humanitarnisetacipasa.repositories;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationShortInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssociationRepository extends JpaRepository<Association, Long> {

    boolean existsAssociationByUser(User user);

    @Query("SELECT new hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationShortInfoModel(a) FROM Association a WHERE a.user.isPublic = true")
    List<AssociationShortInfoModel> getAllAsShortInfoModels();

    Association getByUserId(Long id);

}
