package hr.fer.progi.oneclick.humanitarnisetacipasa.requests;

import java.time.LocalDateTime;
import java.util.List;

public class WalkRequest {

    private LocalDateTime startTime;
    private Integer duration;
    private List<Long> animalIds;

    public WalkRequest() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Long> getAnimalIds() {
        return animalIds;
    }

    public void setAnimalIds(List<Long> animalIds) {
        this.animalIds = animalIds;
    }
}
