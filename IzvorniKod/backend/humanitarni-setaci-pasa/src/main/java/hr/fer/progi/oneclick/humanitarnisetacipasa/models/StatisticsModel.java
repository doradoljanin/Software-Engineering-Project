package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import hr.fer.progi.oneclick.humanitarnisetacipasa.services.StatisticsService;

public class StatisticsModel {

    private long citizenId;
    private long walkCount;
    private Integer walksDuration;
    private long dogCount;
    private long points;

    public StatisticsModel() {
    }

    public long getCitizenId() { return citizenId; }

    public void setCitizenId(long citizenId) { this.citizenId = citizenId; }

    public long getWalkCount() { return walkCount; }

    public void setWalkCount(long walkCount) { this.walkCount = walkCount; }

    public Integer getWalksDuration() { return walksDuration; }

    public void setWalksDuration(Integer walksDuration) { this.walksDuration = walksDuration; }

    public long getDogCount() { return dogCount; }

    public void setDogCount(long dogCount) { this.dogCount = dogCount; }

    public long getPoints() { return points; }

    public void setPoints(long points) { this.points = points; }

}
