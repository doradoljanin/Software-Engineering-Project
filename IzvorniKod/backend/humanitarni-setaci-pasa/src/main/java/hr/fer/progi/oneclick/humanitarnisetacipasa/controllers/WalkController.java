package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;


import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Animal;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Walk;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.WalkRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.requests.WalkRequest;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AnimalRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WalkController {

    private final AnimalRepository animalRepo;
    private final WalkRepository walkRepo;
    private final CitizenRepository citizenRepository;
    private final Clock clock;

    public WalkController(AnimalRepository animalRepo, WalkRepository walkRepo, CitizenRepository citizenRepository, Clock clock) {
        this.animalRepo = animalRepo;
        this.walkRepo = walkRepo;
        this.citizenRepository = citizenRepository;
        this.clock = clock;
    }

    @PostMapping("/api/walk")
    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    public ResponseEntity<?> addWalk(@RequestBody WalkRequest walkRequest) {
        if (walkRequest.getStartTime() == null || walkRequest.getStartTime().isBefore(LocalDateTime.now(clock)))
            return ResponseEntity.badRequest().body("Vrijeme je prošlo.");
        if (walkRequest.getDuration() == null || walkRequest.getDuration() > 3 || walkRequest.getDuration() < 1)
            return ResponseEntity.badRequest().body("Duljina šetnje je neispravna.");
        if (walkRequest.getAnimalIds() == null || walkRequest.getAnimalIds().size() < 1)
            return ResponseEntity.badRequest().body("Treba izabrati životinju za šetnju.");

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Citizen citizen = citizenRepository.getByUserId(userDetails.getId());
        LocalDateTime startTime = walkRequest.getStartTime().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(walkRequest.getDuration());

        if (!isWalkerFree(citizen, startTime, endTime))
            return ResponseEntity.badRequest().body("Zauzeti ste u terminu koji ste odabrali.");

        Walk walk = new Walk();

        List<Animal> animals = new ArrayList<>();
        for (Long animalId : walkRequest.getAnimalIds()) {
            if (!animalRepo.existsById(animalId))
                return ResponseEntity.badRequest().body("Greška prilikom dohvaćanja životinja.");

            Animal animal = animalRepo.findById(animalId).get();

            if (isAnimalAvailable(animal, startTime, endTime)) {
                animals.add(animal);
            } else {
                return ResponseEntity.badRequest().body("Jedna od odabranih životinja (" + animal.getName() + ") je zauzeta u odabranom terminu.");
            }
        }

        walk.setAnimals(animals);
        walk.setCitizen(citizenRepository.getByUserId(userDetails.getId()));
        walk.setStartTime(startTime);
        walk.setDuration(walkRequest.getDuration());

        walkRepo.save(walk);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/walk/animals")
    public List<Long> getAnimals(@RequestParam String startTime, @RequestParam Integer duration, @RequestParam Long associationId) {
        List<Animal> list = new ArrayList<>();
        LocalDateTime reqStart = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.HOURS).plusHours(1);
        LocalDateTime reqEnd = reqStart.plusHours(duration);

        List<Animal> temp = animalRepo.getByAssociationId(associationId);

        for (Animal a: temp) {
            if (isAnimalAvailable(a, reqStart, reqEnd)) {
                list.add(a);
            }
        }

        list.sort(Animal::compareTo);

        return list.stream().map(Animal::getId).collect(Collectors.toList());
    }

    private boolean isAnimalAvailable(Animal a, LocalDateTime startTime, LocalDateTime endTime) {
        for (Walk w: a.getWalks()) {
            LocalDateTime start = w.getStartTime();
            LocalDateTime end = w.getStartTime().plusHours(w.getDuration());
            if (startTime.isEqual(start) || (startTime.isAfter(start) && startTime.isBefore(end)) || (startTime.isBefore(start) && endTime.isAfter(end)) || (endTime.isAfter(start) && endTime.isBefore(end)) || endTime.isEqual(end)) {
                return false;
            }
        }

        return true;
    }

    private boolean isWalkerFree(Citizen citizen, LocalDateTime startTime, LocalDateTime endTime) {
        for (Walk walk : citizen.getWalks()) {
            LocalDateTime start = walk.getStartTime();
            LocalDateTime end = start.plusHours(walk.getDuration());
            if (startTime.isEqual(start) || (startTime.isAfter(start) && startTime.isBefore(end)) || (startTime.isBefore(start) && endTime.isAfter(end)) || (endTime.isAfter(start) && endTime.isBefore(end)) || endTime.isEqual(end))
                return false;
        }
        return true;
    }
}
