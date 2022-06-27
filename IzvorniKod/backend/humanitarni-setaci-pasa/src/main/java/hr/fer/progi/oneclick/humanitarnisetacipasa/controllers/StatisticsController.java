package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.GlobalStatisticsModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.RankingEntryModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.StatisticsModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.WalkRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import hr.fer.progi.oneclick.humanitarnisetacipasa.services.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/statistics")
public class StatisticsController {

    private final CitizenRepository citizenRepository;
    private final AssociationRepository associationRepository;
    private final WalkRepository walkRepository;
    private final StatisticsService statisticsService;

    public StatisticsController(CitizenRepository citizenRepository, AssociationRepository associationRepository, WalkRepository walkRepository) {
        this.citizenRepository = citizenRepository;
        this.associationRepository = associationRepository;
        this.walkRepository = walkRepository;
        this.statisticsService = new StatisticsService(associationRepository, walkRepository, citizenRepository);
    }


    @GetMapping(value = "/global")
    public GlobalStatisticsModel getGlobalStatistics() {
        GlobalStatisticsModel globalStatisticsModel = statisticsService.getGlobalStatistics();

        return globalStatisticsModel;
    }

    @GetMapping(value = "/personal")
    public ResponseEntity<?> getMyStatistics() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
            StatisticsModel statisticsModel = statisticsService.getPersonalStatistics(citizenRepository.getByUserId(user.getId()));
            return ResponseEntity.ok(statisticsModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/rankings")
    public List<RankingEntryModel> getRankings() {
        return statisticsService.getRanking();
    }
}
