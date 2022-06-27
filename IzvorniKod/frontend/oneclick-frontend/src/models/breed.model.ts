export default class Breed {
    id: number;
    breedName: string;
    height: string;
    weight: string;
    lifeExpectancy: string;
    group: string;

    constructor(id:number, breedName: string, height: string, weight:string, lifeExpectancy:string, group:string) {
        this.id = id;
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lifeExpectancy = lifeExpectancy;
        this.group = group;
    }
}