package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.*;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AnimalModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AnimalRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.BreedRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.WalkRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.EditAnimalRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AnimalController {

    private final AnimalRepository animalRepo;
    private final AssociationRepository assocRepo;
    private final BreedRepository breedRepo;
    private final WalkRepository walkRepo;

    public AnimalController(AnimalRepository animalRepo, AssociationRepository assocRepo, BreedRepository breedRepo, WalkRepository walkRepo) {
        this.animalRepo = animalRepo;
        this.assocRepo = assocRepo;
        this.breedRepo = breedRepo;
        this.walkRepo = walkRepo;
    }

    @PreAuthorize("hasRole('ROLE_ASSOCIATION')")
    @GetMapping(value = "/association/animals")
    public List<AnimalModel> getAllAnimals() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Association association = this.assocRepo.getByUserId(user.getId());

        return association.getAnimals()
                .stream()
                .map(AnimalModel::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ASSOCIATION')")
    @PostMapping("/association/animals/edit")
    public ResponseEntity<?> editAnimal(@RequestBody EditAnimalRequest request) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Association association = this.assocRepo.getByUserId(user.getId());

            if (!animalRepo.getByAssociationId(association.getId()).stream().map(Animal::getId).collect(Collectors.toList()).contains(request.getId())) {
                return ResponseEntity.badRequest().build();
            }
            Animal editor = animalRepo.getOne(request.getId());
            editor.setName(request.getName());
            editor.setBirthYear(request.getBirthYear());
            editor.setBreed(breedRepo.getOne(request.getBreedId()));
            editor.setDescription(request.getDescription());
            editor.setGender(Gender.valueOf(request.getGender()));
            editor.setWalkType(WalkType.valueOf(request.getWalkType()));
            animalRepo.save(editor);
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ASSOCIATION')")
    @GetMapping("/association/animals/remove/{id}")
    public ResponseEntity<?> removeAnimal(@PathVariable Long id) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Association association = this.assocRepo.getByUserId(user.getId());

            if (animalRepo.getByAssociationId(association.getId()).stream().map(Animal::getId).collect(Collectors.toList()).contains(id)) {
                Animal animal = animalRepo.getOne(id);
                for (Walk walk : animal.getWalks()) {
                    walk.getAnimals().clear();
                    walkRepo.save(walk);
                    walkRepo.delete(walk);
                }
                animalRepo.deleteById(id);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ASSOCIATION')")
    @PostMapping("/association/animals/add")
    public ResponseEntity<?> addAnimal(@RequestBody EditAnimalRequest request) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Association association = this.assocRepo.getByUserId(user.getId());
            Animal animal = new Animal();
            animal.setName(request.getName());
            animal.setBirthYear(request.getBirthYear());
            animal.setBreed(breedRepo.getOne(request.getBreedId()));
            animal.setDescription(request.getDescription());
            animal.setGender(Gender.valueOf(request.getGender()));
            animal.setWalkType(WalkType.valueOf(request.getWalkType()));
            animal.setAssociation(association);
            animalRepo.save(animal);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
