import {Button, FormControl, InputAdornment, InputLabel, OutlinedInput} from '@material-ui/core';
import React, {useEffect, useState} from 'react';
import Animal from "../../models/animal.model";
import '../../styles/AnimalSelector.css';
import {DateTimePicker} from '@material-ui/pickers';
import {useForm} from 'react-hook-form';
import AvailableAnimalsRequest from '../../models/availableAnimals.model';
import WalkService from '../../services/walk.service';
import history from '../../misc/history';
import {startOfHour} from 'date-fns/esm';
import IdentityService from '../../services/identity.service';
import AnimalCard from "../basic/animal.card";

type AnimalSelectorProps = {
    animals: Animal[],
    associationId: string
}

class AnimalUnit {
    animal: Animal;
    selected: boolean;
    selectable: boolean;

    constructor(animal: Animal, selected: boolean, selectable: boolean) {
        this.animal = animal;
        this.selected = selected;
        this.selectable = selectable;
    }
}

function AnimalSelector(props: AnimalSelectorProps) {
    const identity = IdentityService.getIdentityContext();

    const defaultValues: AvailableAnimalsRequest = {
        associationId: parseInt(props.associationId),
        duration: 1,
        startTime: new Date()
    }

    const {register, handleSubmit, setValue} = useForm<AvailableAnimalsRequest>({defaultValues});
    useEffect(() => {
        register({name: "startTime", type: "date"});
        register({name: "duration", type: "number"});
        register({name: "associationId", type: "number"});
    })

    const onSubmit = (data: AvailableAnimalsRequest) => {
        WalkService.getAvailableAnimals(data)
            .then(response => {
                setAnimals(animals.map(a => new AnimalUnit(a.animal, false, response.data.includes(a.animal.id))));
            }).catch(error => {

        });
    }

    const registerWalk = () => {
        const walkRequest = {
            animals: JSON.stringify(animals.filter(a => a.selected).map(a => a.animal)),
            duration: String(duration),
            startTime: String(startTime),
        };

        history.push('/walk/confirm', walkRequest);
    }

    const isWalkRegisterable = (): boolean => {
        let registerable = false;

        animals.forEach(a => {
            registerable = registerable || a.selected;
        });

        return registerable;
    }

    const [animals, setAnimals] = useState<AnimalUnit[]>(props.animals.map(a => new AnimalUnit(a, false, true)));
    const [duration, setDuration] = useState(defaultValues.duration);
    const [startTime, setStartTime] = useState(defaultValues.startTime);

    const handleDurationChange = () => (event: React.ChangeEvent<HTMLInputElement>) => {
        setDuration(parseInt(event.target.value));
        setValue('duration', event.target.value);
    };

    const handleDateChange = (date: Date | null) => {
        if (!!date) {
            const fixedDate = startOfHour(date);

            setStartTime(fixedDate);
            setValue('startTime', fixedDate);
        }
    };

    const onSelected = (id: number, selected: boolean) => {
        const animal = animals.find(a => a.animal.id === id);
        if (animal!.animal.walkType === "SINGLE") {
            if (selected)
                setAnimals(animals.map(a => new AnimalUnit(a.animal, a.animal.id === id, a.animal.id === id)));
            else
                setAnimals(animals.map(a => new AnimalUnit(a.animal, false, true)));
        } else if (animal!.animal.walkType === "GROUP") {
            if (selected)
                setAnimals(animals.map(a => new AnimalUnit(a.animal, a.animal.id === id || a.selected, a.animal.walkType === "GROUP")));
            else {
                let allUnselected = true;
                animals.forEach(a => {
                    if (a.selected && a.animal.id !== id) allUnselected = false;
                });

                if (allUnselected)
                    setAnimals(animals.map(a => new AnimalUnit(a.animal, false, true)));
                else
                    setAnimals(animals.map(a => new AnimalUnit(a.animal, a.selected && a.animal.id !== id, a.animal.walkType === "GROUP")))
            }
        }
    }

    return (
        <div className="animal-selector-root">
            {identity?.roles.includes("ROLE_ASSOCIATION") ? (<></>) : (<>

                    <div className="animal-selector-appointment-picker">
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <div>
                                <DateTimePicker
                                    id="startTime"
                                    name="startTime"
                                    value={startTime}
                                    onChange={handleDateChange}
                                    disablePast={true}
                                    format="dd.MM.yyyy HH' sati'"
                                    inputVariant="outlined"
                                    label="Početak šetnje"
                                    minutesStep={60}
                                    ampm={false}
                                    views={["date", "hours"]}
                                />
                            </div>

                            <div>
                                <FormControl variant="outlined">
                                    <InputLabel htmlFor="duration">Duljina šetnje</InputLabel>
                                    <OutlinedInput
                                        id="duration"
                                        name="duration"
                                        value={duration}
                                        onChange={handleDurationChange()}
                                        endAdornment={<InputAdornment position="end">sati</InputAdornment>}
                                        aria-describedby="outlined-duration-helper-text"
                                        inputProps={{
                                            'aria-label': 'duration',
                                            'min': 1,
                                            'max': 3
                                        }}
                                        labelWidth={0}
                                        type='number'
                                        label="Duljina šetnje"
                                    />
                                </FormControl>
                            </div>
                            <div>
                                <Button type="submit" variant="outlined">Pretraži</Button>
                            </div>
                        </form>

                        {

                        }
                        <div className={"animal-selector-confirm-button"}>
                            {
                                !identity
                                    ? (<div>Morate se ulogirati prije prijave šetnje!</div>)
                                    : identity.roles.includes("ROLE_CITIZEN")
                                    && (<Button onClick={registerWalk} disabled={!isWalkRegisterable()}
                                                variant="outlined">Prijavi
                                        šetnju</Button>)
                            }
                        </div>
                    </div>
                </>
            )}
            <div>
                {animals.map(a => {
                    return <AnimalCard key={a.animal.id} animal={a.animal} disabled={!a.selectable}
                                       selected={a.selected} onSelected={onSelected}/>
                })}
            </div>
        </div>
    );
}

export default AnimalSelector;