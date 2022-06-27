import { AxiosResponse } from "axios";
import AssociationRegistrationInfo from "../models/associationRegistration.model";
import getHostName from "./host.service";
import api from "./api.service";
import CitizenRegistrationInfo from "../models/citizenRegistration.model";

function postAssociationEdit(info: AssociationRegistrationInfo): Promise<AxiosResponse> {
    let url = getHostName("/profile/association/edit");

    return api.post(url, info, {
        headers: { 'Content-Type': 'application/json'}
    });
}

function postCitizenEdit(info: CitizenRegistrationInfo): Promise<AxiosResponse> {
    let url = getHostName("/profile/citizen/edit");

    return api.post(url, info, {
        headers: { 'Content-Type': 'application/json'}
    });
}

const AccountEditService = { postAssociationEdit, postCitizenEdit }
export default AccountEditService;