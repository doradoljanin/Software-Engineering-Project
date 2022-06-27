import React, {useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import MyStatistics from '../basic/my.statistics';
import StatisticsModel from '../../models/statistics.model';
import StatisticsService from '../../services/statistics.service';
import obj from '../../services/identity.service';
import LoginService from "../../services/login.service";
import TotalStatistics from '../basic/total.statistics';

const useStyles = makeStyles({
    table: {
        width: 'fit-content',
        margin: 'auto',
    },
    tableHead: {
        fontSize: '1.2em',
        fontFamily: 'Century Gothic',
        background: '#E9ECEF',
    },
    tableData: {
        fontSize: '1em',
        fontFamily: 'Arial',
    },
    titles: {
        width: 'fit-content',
        margin: 'auto',
        fontSize: '2em',
        fontFamily: 'Century Gothic',
    },
});

function Statistics() {
    const [rankings, setRankings] = useState<StatisticsModel[]>([]);

    useEffect(() => {
        StatisticsService.getRankings().then(response => {
            setRankings(response.data);
        });
    }, []);

    const classes = useStyles();
    var rang = 1;

    const userDetails = obj.getIdentityContext();

    return (
        <div>
            <br/>
            <div className={classes.titles}>
                Rang lista šetača
            </div>
            <br/>
            <TableContainer component={Paper} className={classes.table}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center" className={classes.tableHead}>Rang</TableCell>
                            <TableCell align="center" className={classes.tableHead}>Inicijali građanina</TableCell>
                            <TableCell align="center" className={classes.tableHead}>Broj šetnji</TableCell>
                            <TableCell align="center" className={classes.tableHead}>Ukupna duljina šetnji</TableCell>
                            <TableCell align="center" className={classes.tableHead}>Broj pasa</TableCell>
                            <TableCell align="center" className={classes.tableHead}>Bodovi</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rankings.map((row) => (
                            <TableRow key={row.citizenInitials}>
                                <TableCell align="center" className={classes.tableData}>{rang++}</TableCell>
                                <TableCell component="th" scope="row" align="center" className={classes.tableData}>
                                    {row.citizenInitials}
                                </TableCell>
                                <TableCell align="center" className={classes.tableData}>{row.walkCount}</TableCell>
                                <TableCell align="center"
                                           className={classes.tableData}>{Math.round(row.walksDuration / 24)}d {row.walksDuration % 24}h</TableCell>
                                <TableCell align="center" className={classes.tableData}>{row.dogCount}</TableCell>
                                <TableCell align="center" className={classes.tableData}>{row.points}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <br/> <br/>

            {LoginService.isLoggedIn() ? (
                <>
                    {userDetails?.roles.includes("ROLE_ASSOCIATION") ? (<></>) : (
                        <>
                            < div className={classes.titles}>
                                Osobna statistika
                            </div>

                            <div className={classes.table}>
                                <br/>
                                <MyStatistics></MyStatistics>
                                <br/> <br/>
                            </div>
                        </>
                    )}
                </>) : (<></>)
            }

            < div className={classes.titles}>
                Ukupna statistika stranice
            </div>

            <div className={classes.table}>
                <br/>
                <TotalStatistics></TotalStatistics>
                <br/> <br/>
            </div>
        </div>
    )
}
;


export default Statistics;