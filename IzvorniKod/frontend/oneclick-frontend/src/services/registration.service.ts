import { AxiosResponse } from "axios";
import AssociationRegistrationInfo from "../models/associationRegistration.model";
import getHostName from "./host.service";
import api from "./api.service";
import CitizenRegistrationInfo from "../models/citizenRegistration.model";

function registerAssociation(info: AssociationRegistrationInfo): Promise<AxiosResponse> {
    let url = getHostName("/auth/association/register");

    return api.post(url, info, {
        headers: { 'Content-Type': 'application/json'}
    });
}

function registerCitizen(info: CitizenRegistrationInfo): Promise<AxiosResponse> {
    let url = getHostName("/auth/citizen/register");

    return api.post(url, info, {
        headers: { 'Content-Type': 'application/json'}
    });
}

const obj = { registerAssociation, registerCitizen }
export default obj;