package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

public class RankingEntryModel {

    private String citizenInitials;
    private long walkCount;
    private Integer walksDuration;
    private long dogCount;
    private long points;

    public RankingEntryModel() {
    }

    public RankingEntryModel(String citizenInitials, long walkCount, Integer walksDuration, long dogCount, long points) {
        this.citizenInitials = citizenInitials;
        this.walkCount = walkCount;
        this.walksDuration = walksDuration;
        this.dogCount = dogCount;
        this.points = points;
    }

    public String getCitizenInitials() {
        return citizenInitials;
    }

    public void setCitizenInitials(String citizenInitials) {
        this.citizenInitials = citizenInitials;
    }

    public long getWalkCount() {
        return walkCount;
    }

    public void setWalkCount(long walkCount) {
        this.walkCount = walkCount;
    }

    public long getWalksDuration() {
        return walksDuration;
    }

    public void setWalksDuration(Integer walksDuration) {
        this.walksDuration = walksDuration;
    }

    public long getDogCount() {
        return dogCount;
    }

    public void setDogCount(long dogCount) {
        this.dogCount = dogCount;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
