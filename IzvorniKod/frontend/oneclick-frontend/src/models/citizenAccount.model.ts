import { getWeekYearWithOptions } from "date-fns/fp";

class CitizenAccountModel {
    id: number;
    username: string;
    email: string;
    phoneNumber: string;
    firstName: string;
    lastName: string;
    public: boolean;

    constructor(id: number, username: string, email: string, phoneNumber: string, firstName: string, lastName: string, isPublic: boolean) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.public = isPublic;
    }
}

export default CitizenAccountModel;