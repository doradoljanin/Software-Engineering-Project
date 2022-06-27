import React, { useEffect, useState } from 'react';
import { makeStyles, Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import GlobalStatisticsModel from '../../models/global.statistics.model';
import StatisticsService from '../../services/statistics.service';

const useStyles = makeStyles((theme) => ({
    root: {
      width: 'fit-content',
      borderRadius: theme.shape.borderRadius,
      backgroundColor: theme.palette.background.paper,
      color: theme.palette.text.secondary,
    },
  }));

export default function TotalStatistics() {
    const [totalStats, setTotalStats] = useState<GlobalStatisticsModel>();

    useEffect(() => {
        StatisticsService.getGlobalStats().then(response => {
            setTotalStats(response.data);
        });
    }, []);

    const classes = useStyles();
  
    return ( <> {!!totalStats && (
      <div>
        <Box display="flex" flexWrap="wrap" alignItems="center"  className={classes.root}>
            <Box pl={5} pr={5} pt={3} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {totalStats.associationCount}
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                udruga
                </Typography>
            </Box>
            
            <Box pt={3} pl={5} pr={5} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {totalStats.walkCount}
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                šetnji
                </Typography>
            </Box>
            
            <Box pt={3} pl={5} pr={5} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {Math.round(totalStats.walksDuration / 24)}d {totalStats.walksDuration%24}h
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                provedeno u šetnjama
                </Typography>
            </Box>

            <Box pr={5} pl={5} pt={3} border={1} borderColor="grey.500" boxShadow="3px 3px 3px #9E9E9E">
                <Typography gutterBottom variant="h4" align="center">
                {totalStats.dogCount}
                </Typography>

                <Typography gutterBottom variant="subtitle1" align="center">
                pasa šetano
                </Typography>
            </Box>
        </Box>
      </div>
    )}
    </>
    );
  }
