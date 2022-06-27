import { AxiosResponse } from "axios";
import AvailableAnimalsRequest from "../models/availableAnimals.model";
import getHostName from "./host.service";
import api from "./api.service";
import WalkRequest from "../models/walkRequest.model";

function getAvailableAnimals(info: AvailableAnimalsRequest): Promise<AxiosResponse> {
    let url = getHostName("/walk/animals");

    return api.get(url, {
        headers: { 'Content-Type': 'application/json'},
        params: info
    });
}

function postWalk(info: WalkRequest): Promise<AxiosResponse> {
    let url = getHostName("/walk");

    return api.post(url, info, {
        headers: { 'Content-Type': 'application/json'}
    });
}

const exports = {getAvailableAnimals, postWalk}
export default exports;