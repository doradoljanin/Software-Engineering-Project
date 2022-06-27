import {Button} from '@material-ui/core';
import {addHours, format, startOfHour} from 'date-fns';
import React, {useState} from 'react';
import {RouteComponentProps} from 'react-router';
import Animal from '../../models/animal.model';
import WalkRequest from '../../models/walkRequest.model';
import AnimalCard from '../basic/animal.card';
import WalkService from '../../services/walk.service';
import history from '../../misc/history';
import {AxiosError, AxiosResponse} from 'axios';
import '../../styles/walk.css';

type WalkConfirmationProps = {
    startTime?: string | undefined,
    duration?: string | undefined,
    animals?: string | undefined,
}

function WalkConfirmationScreen({location}: RouteComponentProps<WalkConfirmationProps>) {
    const [error, setError] = useState();

    const walkRequest = location.state as WalkConfirmationProps;

    if (!walkRequest.startTime || !walkRequest.duration || !walkRequest.animals) {
        return (
            <div className={"container-page"}>
                Nešto je pošlo po krivu prilikom prijave šetnje. Molim vas da pokušate ponovo.
            </div>
        )
    }

    const startTime = startOfHour(Date.parse(walkRequest.startTime));
    const duration = parseInt(walkRequest.duration);
    const endTime = addHours(startTime, duration);
    const animals = JSON.parse(walkRequest.animals) as Animal[];
    const walkType = animals.length > 1 ? "GROUP" : "SINGLE";

    const confirmWalk = () => {
        const walk: WalkRequest = {
            startTime: startTime,
            duration: duration,
            animalIds: animals.map(a => a.id)
        }

        WalkService.postWalk(walk)
            .then((response: AxiosResponse) => {
                if (response.status === 200) {
                    history.push('/walk/confirmed', {success: "true"});
                }
            })
            .catch((errors: AxiosError) => {
                if (errors.response?.status === 400)
                    setError(errors.response.data);
            });
    }

    return (
        <div>
            <br/>
            <div className="walk">
                <div className="walk-header">
                    <div>
                        Termin: {format(startTime, "dd.MM.yyyy. HH")}-{format(endTime, "HH")} sati
                    </div>
                    <div>
                        Tip šetnje: {walkType === "SINGLE" ? "Pojedinačna" : "Grupna"} šetnja
                    </div>
                    <div>
                        <Button onClick={confirmWalk} variant="outlined">Potvrdi šetnju</Button>
                    </div>
                </div>
                <div>
                    {
                        animals.map(a => {
                            return <AnimalCard key={a.id} animal={a} selected={true}/>
                        })
                    }
                </div>
                <div className="horizontal-center">
                    {!!error && error}
                </div>
            </div>
        </div>
    );
}

export default WalkConfirmationScreen;
