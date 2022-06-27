export default class GlobalStatisticsModel {
    associationCount: number;
    walkCount: number;
    walksDuration: number;
    dogCount: number;

    constructor(associationCount: number, walkCount: number, walksDuration: number, dogCount: number) {
        this.associationCount = associationCount;
        this.walkCount = walkCount;
        this.walksDuration = walksDuration;
        this.dogCount = dogCount;
    }
}