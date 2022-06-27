import AssociationService from '../../services/association.service';
import '../../styles/Home.css';
import {Button, TextField} from '@material-ui/core';
import React, {ChangeEvent, useEffect, useState} from 'react';
import AssociationShortInfo from '../../models/associationShortInfo.model';
import AssociationCard from '../basic/association.card';
import api from '../../services/api.service';
import getHostName from '../../services/host.service';
import {AxiosResponse} from 'axios';
import '../../styles/Search.css';
import Association from '../../models/association.model';

function Index() {
    const associations = AssociationService.useGetAssociations();
    const [filteredData, setFilteredData] = useState(associations);
    const [query, setQuery] = useState("");
    useEffect(() => {
        setFilteredData(associations);
    }, [associations]);

    const searchAssociations = () => {
        api.get(getHostName(`/associations/search?str=${query}`)).then((response: AxiosResponse) => {
            setFilteredData(response.data);
        });
    }

    const updateQuery = (event: ChangeEvent<HTMLInputElement>) => {
        setQuery(event.target.value);
    }

    return (
        <div>
            <div className="proba">
            <div className="searchtitle">
                <p>Pretraživanje udruga:</p>
            <span className="searchfielddiv">
                <TextField
                    variant='outlined'
                    onChange={updateQuery}
                    label="Ime udruge ili mjesta" className="searchfield"
                />
                <Button variant="contained" color="primary" className="searchbutton"
                        onClick={searchAssociations}>Pretraži</Button>
            </span>
            </div>
        </div>
        <div className="hometitle">
                <p>Aktivne udruge za životinje:</p>
            </div>
            <div className="homediv">
                    { filteredData.map(a => (
                        <AssociationCard association={a} />))}
            </div>
        </div>
    );
}

export default Index;