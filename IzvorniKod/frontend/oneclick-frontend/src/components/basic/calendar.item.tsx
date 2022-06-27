import { Avatar, Divider, ListItem, ListItemAvatar, ListItemText } from '@material-ui/core';
import { addHours, format } from 'date-fns';
import React from 'react';
import CalendarEntry from '../../models/calendarEntry.model';
import ImageService from '../../services/image.service';

export default function CalendarEntryItem(props: {entry: CalendarEntry, index: number, list: CalendarEntry[]}) {
    const e = props.entry;

    let secondary = "";
    if (!!e.association)
        secondary = `${e.association.name} - ${e.association.address}, ${e.association.place}`;
    else if (!!e.citizen)
        secondary = `${e.citizen.firstName} ${e.citizen.lastName} - ${e.citizen.phoneNumber}`

    const startTime = new Date(Date.parse(e.startTime));

    return (
        <>
            <ListItem key={`entry-${format(startTime, "yyyy-MM-dd-HH")}`}>
                <ListItemText primary={`${format(startTime, "HH")}-${format(addHours(startTime, e.duration), "HH")} sati`}
                    secondary={secondary}></ListItemText>
                {e.animalIds.map(id => (
                    <ListItemAvatar>
                        <Avatar
                            alt={`/logo512.png`}
                            src={ImageService.getAnimalImageUrl(id)}
                        />
                    </ListItemAvatar>
                ))}
            </ListItem>{props.index !== props.list.length-1 ? <Divider component="li" /> : ""}
        </>);
}