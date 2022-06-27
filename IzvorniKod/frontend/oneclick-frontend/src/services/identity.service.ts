import UserDetails from "../models/userDetails.model";

function getIdentityContext(): UserDetails | null {
    const storedIdentity = localStorage.getItem("user");
    
    if (!storedIdentity)
        return null;

    const identity = JSON.parse(storedIdentity) as UserDetails;

    return identity;
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars   
function isLoggedIn() {
    let value = localStorage.getItem("user");
    
    return !!value;
}

const obj = {getIdentityContext: getIdentityContext};
export default obj;