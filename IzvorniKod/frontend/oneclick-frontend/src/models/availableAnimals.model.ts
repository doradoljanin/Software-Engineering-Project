export default class AvailableAnimalsRequest {
    startTime: Date | null;
    duration: number;
    associationId: number;

    constructor(startTime: Date | null, duration: number, associationId: number) {
        this.startTime = startTime;
        this.duration = duration;
        this.associationId = associationId;
    }
}