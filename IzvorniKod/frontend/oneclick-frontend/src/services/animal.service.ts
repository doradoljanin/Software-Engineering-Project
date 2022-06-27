import api from './api.service'
import { useEffect, useState } from "react";
import PlaceModel from "../models/place.model";
import getHostName from "./host.service";
import Breed from '../models/breed.model';
import NewAnimal from '../models/newAnimal.model';

const postAnimal = (animal: NewAnimal) => {
    return api.post(getHostName("/association/animals/add"), animal);
}

async function getBreedsData(): Promise<Breed[]> {
    const { data } = await api.get(getHostName("/breeds"));
    return data;
}

const useGetBreeds = () => {
    const [data, setData] = useState<Breed[]>([]);

    const getData = async () => {
        const results = await getBreedsData();
        setData(results)
    }

    useEffect(() => {
        getData()
    }, [])

    return data;
}

const AnimalService = {useGetBreeds, postAnimal};
export default AnimalService;