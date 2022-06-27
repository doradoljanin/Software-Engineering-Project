package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

public class GlobalStatisticsModel {

    private long associationCount;
    private long walkCount;
    private Integer walksDuration;
    private long dogCount;

    public GlobalStatisticsModel () {
    }

    public long getAssociationCount() { return associationCount; }

    public void setAssociationCount(long associationCount) { this.associationCount = associationCount; }

    public long getWalkCount() { return walkCount; }

    public void setWalkCount(long walkCount) { this.walkCount = walkCount; }

    public Integer getWalksDuration() { return walksDuration; }

    public void setWalksDuration(Integer walksDuration) { this.walksDuration = walksDuration; }

    public long getDogCount() { return dogCount; }

    public void setDogCount(long dogCount) { this.dogCount = dogCount; }

}
