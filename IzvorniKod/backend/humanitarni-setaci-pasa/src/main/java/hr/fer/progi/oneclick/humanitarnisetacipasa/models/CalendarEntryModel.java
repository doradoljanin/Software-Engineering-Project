package hr.fer.progi.oneclick.humanitarnisetacipasa.models;

import java.time.LocalDateTime;
import java.util.List;

public class CalendarEntryModel {
    private LocalDateTime startTime;
    private Integer duration;
    private List<Long> animalIds;
    private AssociationShortInfoModel association;
    private CitizenShortInfoModel citizen;

    public CalendarEntryModel() {
    }

    public CalendarEntryModel(LocalDateTime startTime, Integer duration, List<Long> animalIds, AssociationShortInfoModel association) {
        this.startTime = startTime;
        this.duration = duration;
        this.animalIds = animalIds;
        this.association = association;
    }

    public CalendarEntryModel(LocalDateTime startTime, Integer duration, List<Long> animalIds, CitizenShortInfoModel citizen) {
        this.startTime = startTime;
        this.duration = duration;
        this.animalIds = animalIds;
        this.citizen = citizen;
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

    public AssociationShortInfoModel getAssociation() {
        return association;
    }

    public void setAssociation(AssociationShortInfoModel association) {
        this.association = association;
    }

    public CitizenShortInfoModel getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenShortInfoModel citizen) {
        this.citizen = citizen;
    }
}
