class UserDetails {
    id: string;
    username: string;
    email: string;
    token: string;
    roles: string[];
    
    constructor(id:string, username: string, email: string, token: string, roles: string[]) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
        this.roles = roles;
    }
}

export default UserDetails;