package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Breed;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.BreedModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.BreedRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/breeds")
public class BreedController {

    private final BreedRepository breedRepo;

    public BreedController(BreedRepository breedRepo) {
        this.breedRepo = breedRepo;
    }

    @GetMapping(value = "")
    public List<BreedModel> getAllBreeds() {
        return breedRepo.findAll().stream().map(BreedModel::new).collect(Collectors.toList());
    }
}
