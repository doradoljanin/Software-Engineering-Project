export default class CitizenShortInfo {
    id: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;

    constructor(id:number, firstName: string, lastName: string, phoneNumber:string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}