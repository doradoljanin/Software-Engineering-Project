import Animal from "./animal.model";
import PlaceModel from "./place.model";

export default class Association {
    id: number;
    name: string;
    address: string;
    oib: string;
    email: string;
    phoneNumber: string;
    place: PlaceModel;  
    animals: Animal[];

    constructor(id:number, name: string, address: string, oib: string, email: string, phoneNumber: string, place:PlaceModel, animals: Animal[]) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.oib = oib;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.animals = animals;
    }
}