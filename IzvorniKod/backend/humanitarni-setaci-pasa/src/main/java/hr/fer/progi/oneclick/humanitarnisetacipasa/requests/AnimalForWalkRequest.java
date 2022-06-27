package hr.fer.progi.oneclick.humanitarnisetacipasa.requests;

import java.sql.Date;
import java.time.LocalDateTime;

public class AnimalForWalkRequest {

    private long associationId;
    private LocalDateTime startTime;
    private Integer duration;

    public AnimalForWalkRequest() {

    }

    public long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(long associationId) {
        this.associationId = associationId;
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
}
