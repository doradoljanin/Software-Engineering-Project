import {Button, TextField} from '@material-ui/core';
import React, {ChangeEvent, useState} from 'react';
import AssociationShortInfo from '../../models/associationShortInfo.model';
import AssociationCard from '../basic/association.card';
import api from '../../services/api.service';
import getHostName from '../../services/host.service';
import {AxiosResponse} from 'axios';
import '../../styles/Search.css';

export default function SearchScreen() {
    const [data, setData] = useState<AssociationShortInfo[]>([]);
    const [query, setQuery] = useState("");

    const searchAssociations = () => {
        api.get(getHostName(`/associations/search?str=${query}`)).then((response: AxiosResponse) => {
            setData(response.data);
        });
    }

    const updateQuery = (event: ChangeEvent<HTMLInputElement>) => {
        setQuery(event.target.value);
    }

    return (

        <div className="proba">
            <div className="searchtitle">
                <p>Pretraživanje udruga:</p>
            </div>
            <span className="searchfielddiv">
                <TextField
                    variant='outlined'
                    onChange={updateQuery}
                    label="Ime udruge ili mjesta" className="searchfield"
                />
                <Button variant="contained" color="primary" className="searchbutton"
                        onClick={searchAssociations}>Pretraži</Button>
            </span>
            <div className="searchresult">

                {data.map(a => <AssociationCard association={a}/>)}
            </div>
        </div>
    );
}