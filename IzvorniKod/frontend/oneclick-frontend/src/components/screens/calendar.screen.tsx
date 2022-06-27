import { addDays, addHours, addSeconds, format, isAfter, isBefore, isSameDay } from 'date-fns';
import React, { Component, useEffect, useState } from 'react';
import CalendarEntry from '../../models/calendarEntry.model';
import CalendarService from '../../services/calendar.service';
import { PDFDownloadLink, Document, Page, View, Text, Image, Font } from '@react-pdf/renderer'
import CalendarView from '../advanced/calendar';
import DateUtils from '../../misc/date.utils';
import ImageService from '../../services/image.service';
import { Button, TextField } from '@material-ui/core';
import { DatePicker } from '@material-ui/pickers';
import { MaterialUiPickersDate } from '@material-ui/pickers/typings/date';

function CalendarDoc(props: {entries: CalendarEntry[], dates: Date[]}) {
    Font.registerHyphenationCallback(word => [word]);

    return (
        <Document>
            <Page style={{paddingTop: 35, paddingBottom: 65,paddingHorizontal: 35,}}>
                {
                    props.dates.map(d => (
                        <View style={{paddingBottom: '30px'}} wrap={false}>
                            <Text style={{paddingBottom: '10px', color: 'gray', fontSize: '12'}}>{format(d, 'dd.MM.yyyy.')}</Text>
                            {
                                props.entries.filter(e => DateUtils.datesEqual(d, new Date(Date.parse(e.startTime))))
                                    .map(e => {
                                        const time = new Date(Date.parse(e.startTime));
                                        const timeDisplay = `${format(time, 'HH')}-${format(addHours(time, e.duration), 'HH')}`
                                        return (<View style={{paddingBottom: '10px', display: 'flex'}}>
                                            <View style={{width: 'auto'}}>
                                                <Text>{timeDisplay} sati</Text>
                                                {!!e.association && (
                                                    <Text style={{color: 'gray', fontSize: '12'}}>{e.association.name} - {e.association.address}, {e.association.place}</Text>
                                                )}
                                                {!!e.citizen && (
                                                    <Text style={{color: 'gray', fontSize: '12'}}>{e.citizen.firstName} {e.citizen.lastName}, {e.citizen.phoneNumber}</Text>
                                                )}
                                            </View>
                                            {
                                                e.animalIds.map(a => (
                                                    <View style={{padding: '3px'}}>
                                                        <Image style={{width:'30px', height:'30px', borderRadius: '8', objectFit: 'cover'}} src={ImageService.getAnimalImageUrl(a)} />
                                                    </View>
                                                ))
                                            }
                                        </View>)
                                    })
                            }
                        </View>
                    ))
                }
                <Text style={{ position: 'absolute', fontSize: 12, bottom: 30, left: 0, right: 0, textAlign: 'center', color: 'grey',}} render={({ pageNumber, totalPages }) => (
                    `${pageNumber} / ${totalPages}`
                )} fixed />
            </Page>
        </Document>
    );
}

function CalendarScreen() {
    const [data, setData] = useState<CalendarEntry[]>();
    const [dates, setDates] = useState<Date[]>();

    const [startDate, setStartDate] = useState<Date>(new Date());
    const [endDate, setEndDate] = useState<Date>(addDays(startDate, 30));

    const [generate, setGenerate] = useState(false);

    const onStartDateChange = (date: Date | null) => {
        if (!!date)
            setStartDate(date);
    }
    
    const onEndDateChange = (date: Date | null) => {
        if (!!date)
            setEndDate(date);
    }

    useEffect(() => {
        CalendarService.getCalendar().then(response => {
            setData(response.data);
            if (!!response.data && response.data.length > 0)
                setDates((response.data as CalendarEntry[])
                    .map(e => new Date(Date.parse(e.startTime)))
                    .map(t => new Date(t.getFullYear(), t.getMonth(), t.getDate()))
                    .filter((date, i, self) =>
                        self.findIndex(d => d.getTime() === date.getTime()) === i
                    )
                    .sort((a,b) => a.getTime() - b.getTime()));
        });
    }, []);

    return (
        <div>
            <br/>

            <div className="container-page">
            <div>
                <DatePicker value={startDate} onChange={onStartDateChange} label="Od datuma" inputVariant='outlined' format="dd.MM." />
                <DatePicker value={endDate} minDate={startDate} onChange={onEndDateChange} label="Do datuma" inputVariant='outlined' format="dd.MM." />
                <Button variant="outlined" onClick={() => setGenerate(true)} >Stvori raspored</Button>
                {(generate && !!data && !!dates) && (<div>
                        <PDFDownloadLink document={
                        <CalendarDoc 
                            entries={data!.filter(e => (isSameDay(Date.parse(e.startTime), startDate) || isAfter(Date.parse(e.startTime), startDate)) && isBefore(Date.parse(e.startTime), addDays(endDate, 1)))} 
                            dates={dates!.filter(e => (isSameDay(e, startDate) || isAfter(e, startDate)) && isBefore(e, addSeconds(endDate, 50)))} />} fileName="kalendar.pdf">
                            {({ blob, url, loading, error }) => (loading ? 'Učitavanje dokumenta...' : 'Preuzmite raspored!')}
                        </PDFDownloadLink>
                    </div>
                )}
            </div>
            <CalendarView entries={data} dates={dates} />
        </div>
        </div>
    );
}


export default CalendarScreen;
