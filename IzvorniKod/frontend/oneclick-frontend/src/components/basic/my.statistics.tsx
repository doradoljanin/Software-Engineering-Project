import React, { useEffect, useState } from 'react';
import { makeStyles, Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import StatisticsModel from '../../models/statistics.model';
import StatisticsService from '../../services/statistics.service';

const useStyles = makeStyles((theme) => ({
    root: {
      width: 'fit-content',
      borderRadius: theme.shape.borderRadius,
      backgroundColor: theme.palette.background.paper,
      color: theme.palette.text.secondary,
    },
  }));

export default function MyStatistics() {

    const [myStat, setMyStat] = useState<StatisticsModel>();

    useEffect(() => {
        StatisticsService.getMyStats().then(response => {
            setMyStat(response.data);
        });
    }, []);

    const classes = useStyles();
  
    return ( <> {!!myStat && (
      <div>
        <Box display="flex" flexWrap="wrap" alignItems="center"  className={classes.root}>
            <Box pl={5} pr={5} pt={3} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                 {myStat.walkCount}
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                šetnji
                </Typography>
            </Box>
            
            <Box pt={3} pl={5} pr={5} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {Math.round(myStat.walksDuration / 24)}d {myStat.walksDuration%24}h
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                provedeno u šetnjama
                </Typography>
            </Box>

            <Box pt={3} pl={5} pr={5} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {myStat.dogCount}
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                pasa šetano
                </Typography>
            </Box>

            <Box pr={5} pl={5} pt={3} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {myStat.points}
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                bodova
                </Typography>
            </Box>
        </Box>
      </div>
    )}
    </>
    );
  }