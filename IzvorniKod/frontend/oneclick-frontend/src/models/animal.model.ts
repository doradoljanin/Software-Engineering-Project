import Breed from "./breed.model";

export default class Animal {
    id: number;
    name: string;
    description: string;
    picture: string;
    breed: Breed;
    birthYear: number;
    gender: string;
    walkType: string;

    constructor(id: number, name: string, description: string, picture:string, breed: Breed, birthYear: number, gender: string, walkType: string) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.breed = breed;
        this.birthYear = birthYear;
        this.gender = gender;
        this.walkType = walkType;
    }
}