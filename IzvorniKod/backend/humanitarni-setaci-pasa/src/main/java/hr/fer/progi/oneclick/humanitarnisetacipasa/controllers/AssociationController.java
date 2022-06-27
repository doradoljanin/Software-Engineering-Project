package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationShortInfoModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/associations")
public class AssociationController {

    private final AssociationRepository associationRepository;

    public AssociationController(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    @GetMapping(value = "")
    public List<AssociationShortInfoModel> getAllAssociations() {
        return associationRepository.getAllAsShortInfoModels();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAssociationById(@PathVariable(value = "id") Long id) {
        Optional<Association> association = associationRepository.findById(id);
        if(association.isPresent()) {
            AssociationModel associationModel = new AssociationModel(association.get());
            return ResponseEntity.ok(associationModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> getAssociationBySearch(@RequestParam String str) {

        List<AssociationShortInfoModel> modelList = new ArrayList<>();

        for (AssociationShortInfoModel assoc : associationRepository.getAllAsShortInfoModels()) {
            if (assoc.getName().toLowerCase().contains(str.toLowerCase()) || assoc.getPlace().toLowerCase().contains(str.toLowerCase())) {
                modelList.add(assoc);
            }
        }

        if (!modelList.isEmpty()) {
            return ResponseEntity.ok(modelList);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
