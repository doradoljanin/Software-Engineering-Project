import { createStyles, List, ListSubheader, makeStyles, Theme } from '@material-ui/core';
import { format } from 'date-fns';
import React from 'react';
import CalendarEntry from '../../models/calendarEntry.model';
import CalendarEntryItem from '../basic/calendar.item';
import DateUtils from '../../misc/date.utils';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      backgroundColor: theme.palette.background.paper,
      position: 'relative',
      overflow: 'auto',
    },
    listSection: {
      backgroundColor: 'inherit',
    },
    ul: {
      backgroundColor: 'inherit',
      padding: 0,
    },
  }),
);

export default function CalendarView(props: {entries: CalendarEntry[] | undefined, dates: Date[] | undefined}) {
    const classes = useStyles();

    return (<List className={classes.root} subheader={<li />}>
        {!!props.dates && props.dates.map(date => (
            <li key={`date-${format(date, "yyyy-MM-dd")}`} className={classes.listSection}>
                <ul className={classes.ul}>
                    <ListSubheader>{format(date, "dd.MM.yyyy.")}</ListSubheader>
                    {props.entries!.filter((e) => DateUtils.datesEqual(new Date(Date.parse(e.startTime)), date)).map((e, i, self) =>
                        (<CalendarEntryItem entry={e} index={i} list={self}/>)
                        )}
                </ul>
            </li>
        ))}
    </List>);
}