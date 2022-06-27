class AssociationRegistrationInfo {
    username: string;
    password: string;
    email: string;
    phoneNumber: string;
    oib: string;
    name: string;
    address: string;
    place: string;
    isPublic: boolean;

    constructor(username: string, password: string, email: string, phoneNumber: string, oib: string, name: string, address: string, place: string, isPublic: boolean) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.oib = oib;
        this.name = name;
        this.address = address;
        this.place = place;
        this.isPublic = isPublic;
    }
}

export default AssociationRegistrationInfo;