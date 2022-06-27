export default class StatisticsModel {
    citizenInitials: string;
    walkCount: number;
    walksDuration: number;
    dogCount: number;
    points: number;

    constructor(citizenInitials: string, walkCount: number, walksDuration: number, dogCount: number, points: number) {
        this.citizenInitials = citizenInitials;
        this.walkCount = walkCount;
        this.walksDuration = walksDuration;
        this.dogCount = dogCount;
        this.points = points;
    }
}