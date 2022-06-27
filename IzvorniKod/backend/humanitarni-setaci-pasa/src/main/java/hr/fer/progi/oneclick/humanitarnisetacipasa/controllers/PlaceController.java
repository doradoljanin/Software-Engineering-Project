package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.models.PlaceModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.PlaceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlaceController {

    private final PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @GetMapping(value = "/api/places")
    public List<PlaceModel> getAllPlaces() {
        return placeRepository
                .findAll()
                .stream()
                .map(place -> new PlaceModel(place.getId(), place.getName()))
                .collect(Collectors.toList());
    }

}
