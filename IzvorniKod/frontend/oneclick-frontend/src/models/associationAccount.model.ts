import Animal from "./animal.model";
import PlaceModel from "./place.model";

export default class AssociationAccountModel {
    id: number;
    username: string;
    email: string;
    phoneNumber: string;
    password: string;
    place: PlaceModel;
    oib: string;
    name: string;
    address: string;
    animals: Animal[];
    public: boolean;
    
    constructor(id:number, username:string, email:string, phoneNumber:string, password:string, place:PlaceModel, oib:string, name:string, address:string, animals:Animal[], isPublic: boolean) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.place = place;
        this.oib = oib;
        this.name = name;
        this.address = address;
        this.animals = animals;
        this.public = isPublic;
    }
    
}