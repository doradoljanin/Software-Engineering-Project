package hr.fer.progi.oneclick.humanitarnisetacipasa.services;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Citizen;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Walk;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.GlobalStatisticsModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.RankingEntryModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.StatisticsModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.WalkRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsService {

    private final AssociationRepository associationRepository;
    private final WalkRepository walkRepository;
    private final CitizenRepository citizenRepository;

    public StatisticsService(AssociationRepository associationRepository, WalkRepository walkRepository,
                             CitizenRepository citizenRepository) {
        this.associationRepository = associationRepository;
        this.walkRepository = walkRepository;
        this.citizenRepository = citizenRepository;
    }

    public GlobalStatisticsModel getGlobalStatistics() {
        GlobalStatisticsModel globalStatisticsModel = new GlobalStatisticsModel();
        globalStatisticsModel.setAssociationCount(associationRepository.count());
        globalStatisticsModel.setWalkCount(walkRepository.count());
        globalStatisticsModel.setDogCount(calculateDogCount(walkRepository));
        globalStatisticsModel.setWalksDuration(calculateWalksDuration(walkRepository));

        return globalStatisticsModel;
    }

    public StatisticsModel getPersonalStatistics(Citizen citizen) {
        StatisticsModel personalStatisticsModel = new StatisticsModel();
        personalStatisticsModel.setCitizenId(citizen.getId());
        personalStatisticsModel.setDogCount(calculateCitizenDogCount(citizen));
        personalStatisticsModel.setWalkCount(calculateCitizenWalkCount(citizen));
        personalStatisticsModel.setWalksDuration(calculateCitizenWalksDuration(citizen));
        personalStatisticsModel.setPoints(calculatePoints(citizen));

        return personalStatisticsModel;
    }

    public List<RankingEntryModel> getRanking() {
        List<RankingEntryModel> ranking = new ArrayList<>();

        List<Citizen> citizens = citizenRepository
                .findAll()
                .stream()
                .filter(c -> c.getUser().isPublic())
                .collect(Collectors.toList());

        for (Citizen c : citizens) {
            String initials = c.getFirstName().toUpperCase().substring(0,1) + c.getLastName().toUpperCase().substring(0,1);
            RankingEntryModel rankEntry = new RankingEntryModel(initials, calculateCitizenWalkCount(c),
                    calculateCitizenWalksDuration(c), calculateCitizenDogCount(c), calculatePoints(c));
            ranking.add(rankEntry);
        }

        ranking.sort(Comparator.comparing(RankingEntryModel::getPoints).reversed());

        return ranking;
    }

    public Integer calculateCitizenWalksDuration(Citizen citizen) {
        Integer walksDuration = 0;
        for (Walk walk : citizen.getWalks()) {
            if (walk.getStartTime().isAfter(LocalDateTime.now().minusDays(30)) && walk.getStartTime().isBefore(LocalDateTime.now())) {
                walksDuration += walk.getDuration();
            }
        }

        return walksDuration;
    }

    public Integer calculateWalksDuration(WalkRepository walkRepository) {
        Integer walksDuration = 0;

        for (Walk walk : walkRepository.findAll()) {
            walksDuration += walk.getDuration();
        }

        return walksDuration;
    }

    public long calculateCitizenDogCount(Citizen citizen) {
        long dogCount = 0;
        List<Walk> walkSet = citizen.getWalks();

        for (Walk walk : walkSet) {
            if (walk.getStartTime().isAfter(LocalDateTime.now().minusDays(30)) && walk.getStartTime().isBefore(LocalDateTime.now())) {
                dogCount += walk.getAnimals().size();
            }
        }

        return dogCount;
    }

    public long calculateDogCount(WalkRepository walkRepository) {
        long dogCount = 0;

        for (Walk walk : walkRepository.findAll()) {
            dogCount += walk.getAnimals().size();
        }

        return dogCount;
    }

    public long calculateCitizenWalkCount(Citizen citizen) {
        long walkCount = 0;
        for (Walk walk : citizen.getWalks()) {
            if (walk.getStartTime().isAfter(LocalDateTime.now().minusDays(30)) && walk.getStartTime().isBefore(LocalDateTime.now())) {
                walkCount++;
            }
        }

        return walkCount;
    }

    public long calculatePoints(Citizen citizen) {
        return calculateCitizenWalkCount(citizen) * 50 + calculateCitizenWalksDuration(citizen) * 30 + calculateCitizenDogCount(citizen) * 25;
    }

}
