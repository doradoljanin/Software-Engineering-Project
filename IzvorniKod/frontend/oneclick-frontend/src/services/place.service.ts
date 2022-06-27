import api from './api.service'
import { useEffect, useState } from "react";
import PlaceModel from "../models/place.model";
import getHostName from "./host.service";

async function getPlacesData(): Promise<PlaceModel[]> {
    const { data } = await api.get(getHostName("/places"));
    return data;
}

export const useGetPlaces = () => {
    const [data, setData] = useState<PlaceModel[]>([]);

    const getData = async () => {
        const results = await getPlacesData();
        setData(results)
    }

    useEffect(() => {
        getData()
    }, [])

    return data;
}