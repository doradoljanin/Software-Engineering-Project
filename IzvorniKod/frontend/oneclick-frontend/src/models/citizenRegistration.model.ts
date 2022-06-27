class CitizenRegistrationInfo {
    username: string;
    password: string;
    email: string;
    phoneNumber: string;
    firstName: string;
    lastName: string;
    isPublic: boolean;

    constructor(username: string, password: string, email: string, phoneNumber: string, firstName: string, lastName: string, isPublic: boolean) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPublic = isPublic;
    }
}

export default CitizenRegistrationInfo;