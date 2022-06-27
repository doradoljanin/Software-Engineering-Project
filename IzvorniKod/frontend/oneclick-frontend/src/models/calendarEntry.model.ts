import AssociationShortInfo from "./associationShortInfo.model";
import CitizenShortInfo from "./citizenShortInfo.model";

export default class CalendarEntry {
    startTime: string;
    duration: number;
    animalIds: number[];
    association: AssociationShortInfo | null;
    citizen: CitizenShortInfo | null;

    constructor(startTime: string, duration: number, animalIds: number[], association: AssociationShortInfo | null, citizen: CitizenShortInfo | null) {
        this.startTime = startTime;
        this.duration = duration;
        this.animalIds = animalIds;
        this.association = association;
        this.citizen = citizen;
    }
}