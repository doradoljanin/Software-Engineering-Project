import React from 'react';
import { Card, CardContent, CardMedia, CardActionArea, makeStyles, Typography } from '@material-ui/core';
import AssociationShortInfo from '../../models/associationShortInfo.model';
import imageService from '../../services/image.service';
import history from '../../misc/history';
import { blueGrey, grey, red } from '@material-ui/core/colors';

const useStyles = makeStyles({
    root: {
        display: 'inline-block',
        margin: '25px 0px 0px 1.5%',  
        width: 290,
        height: 245
    },
    media: {
        height: 140
    },
    card: {
        height: 255,
        color: 'rgb(45, 45, 193)',
        width: 'fit-content',
        minWidth: 280
    }
});

type AssociationCardProps = {
    association: AssociationShortInfo
}

function AssociationCard(props: AssociationCardProps) {

    const styles = useStyles();

    return (
        <div className={styles.root}>
            <Card className={styles.card} variant="outlined">
                <CardActionArea onClick={event => {history.push(`/association/${props.association.id}`)}}>
                    <CardMedia
                        className={styles.media} component='img' src={imageService.getAssociationImageUrl(props.association.id)}
                        onError={(e:any) => {
                            e.target.src = imageService.getDefaultImage()
                        }}
                    />
                    <CardContent>
                        <Typography variant="h5" component="h2">
                            {props.association.name}
                        </Typography>
                        <Typography color="textSecondary" gutterBottom>
                            {props.association.address + ', ' + props.association.place}
                        </Typography>
                    </CardContent>
                </CardActionArea>
            </Card>
        </div>
    );
}

export default AssociationCard;