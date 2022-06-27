export default class AssociationShortInfo {
    id: number;
    name: string;
    address: string;
    place: string;

    constructor(id:number, name: string, address: string, place:string) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.place = place;
    }
}