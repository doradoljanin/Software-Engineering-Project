import React, { useEffect, useState } from 'react';
import Animal from '../models/animal.model';
import CitizenAccountModel from '../models/citizenAccount.model';
import api from '../services/api.service'
import getHostName from '../services/host.service'

async function getCitizenProfile(): Promise<CitizenAccountModel>{
    const { data } = await api.get(getHostName("/profile"));
    return data;

}

const useGetProfile = () => {
    const [data, setData] = useState<CitizenAccountModel>();

    const getData = async () => {
        const results = await getCitizenProfile();
        setData(results);
    }

    useEffect(() => {
        getData()
    }, []);

    return data;
}

async function getAnimals(): Promise<Animal[]>{
    const { data } = await api.get(getHostName("/association/animals"));

    return data;
}

const useGetAnimals = () => {
    const [data, setData] = useState<Animal[]>();

    const getData = async () => {
        const results = await getAnimals();
        setData(results);
    }

    useEffect(() => {
        getData()
    }, []);

    return data;
}

const ProfileService = {useGetProfile, useGetAnimals};

export default ProfileService;
