package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.*;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationShortInfoModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.CalendarEntryModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.CitizenShortInfoModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.*;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CalendarController {

    private final AssociationRepository associationRepo;
    private final AnimalRepository animalRepo;
    private final CitizenRepository citizenRepo;

    public CalendarController(AssociationRepository associationRepo, AnimalRepository animalRepo, CitizenRepository citizenRepo) {

        this.associationRepo = associationRepo;
        this.animalRepo = animalRepo;
        this.citizenRepo = citizenRepo;
    }


    @GetMapping("/api/calendar")
    public List<CalendarEntryModel> getCalendar() {
        List<CalendarEntryModel> calendarEntries = new ArrayList<>();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


            if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
                Citizen citizen = citizenRepo.getByUserId(userDetails.getId());
                List<Walk> walks = citizen
                        .getWalks()
                        .stream()
                        .filter(walk -> walk.getStartTime().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());

                for (Walk walk: walks) {
                    Association association = walk.getAnimals().get(0).getAssociation();
                    AssociationShortInfoModel associationShort = new AssociationShortInfoModel(association);
                    List<Long> animalIds = walk
                            .getAnimals()
                            .stream()
                            .map(a -> a.getId())
                            .collect(Collectors.toList());

                    calendarEntries.add(new CalendarEntryModel(walk.getStartTime(),walk.getDuration(),animalIds,associationShort));
                }
            } else {
                Association association = associationRepo.getByUserId(userDetails.getId());

                List<Walk> walks = new ArrayList<>();

                List<List<Walk>> allWalks = association
                        .getAnimals()
                        .stream()
                        .map(a -> a.getWalks().stream()
                                .filter(walk -> walk.getStartTime().isAfter(LocalDateTime.now()))
                                .collect(Collectors.toList()))
                        .collect(Collectors.toList());

                for (List<Walk> animalWalks : allWalks) {
                    walks.addAll(animalWalks);
                }

                walks = walks.stream().distinct().collect(Collectors.toList());

                for (Walk walk : walks) {
                    Citizen citizen = walk.getCitizen();
                    CitizenShortInfoModel citizenShortModel = new CitizenShortInfoModel(citizen);
                    List<Long> animalIds = walk
                            .getAnimals()
                            .stream()
                            .map(a -> a.getId())
                            .collect(Collectors.toList());

                    calendarEntries.add(new CalendarEntryModel(walk.getStartTime(),walk.getDuration(),animalIds,citizenShortModel));
                }
            }



        return calendarEntries;
    }
}
