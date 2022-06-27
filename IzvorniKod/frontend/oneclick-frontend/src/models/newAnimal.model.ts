export default class NewAnimal {
    name: string;
    description: string;
    breedId: number;
    birthYear: number;
    gender: string;
    walkType: string;

    constructor(name: string, description: string, breedId: number, birthYear: number, gender: string, walkType: string) {
        this.name = name;
        this.description = description;
        this.breedId = breedId;
        this.birthYear = birthYear;
        this.gender = gender;
        this.walkType = walkType;
    }
}