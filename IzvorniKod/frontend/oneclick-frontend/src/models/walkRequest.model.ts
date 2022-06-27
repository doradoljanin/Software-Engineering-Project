import Animal from "./animal.model";

export default class WalkRequest {
    startTime: Date;
    duration: number;
    animalIds: number[];

    constructor(startTime: Date, duration: number, animalIds: number[]) {
        this.startTime = startTime;
        this.duration = duration;
        this.animalIds = animalIds;
    }
}