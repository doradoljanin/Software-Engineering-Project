import { useEffect, useState } from "react"
import AssociationShortInfo from "../models/associationShortInfo.model";
import Association from "../models/association.model";
import getHostName from "./host.service";
import api from './api.service'

async function getAssociations(): Promise<AssociationShortInfo[]> {
    const { data } = await api.get(getHostName("/associations"));
    return data;
}

const useGetAssociations = () => {
    const [data, setData] = useState<AssociationShortInfo[]>([]);

    const getData = async () => {
        const results = await getAssociations();
        setData(results);
    }

    useEffect(() => {
        getData()
    }, []);

    return data;
}

const useGetAssociation = (id: string | number): [Association | undefined, boolean] => {
    const [data, setData] = useState<Association>();
    const [isFetching, setIsFetching] = useState(true);

    useEffect(() => {
        const fetchAssociation = async () => {
            try {
                setData(data);
                setIsFetching(true);
                const response = await api.get(getHostName(`/associations/${id}`));
                setData(response.data);
                setIsFetching(false);
            } catch (e) {
                console.log(e);
                setData(data);
                setIsFetching(false);
            }
        };
        fetchAssociation();

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return [data, isFetching];
}

const exports = {useGetAssociations, useGetAssociation};
export default exports;