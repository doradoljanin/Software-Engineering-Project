import React from 'react';
import {Card, CardActionArea, CardContent, CardMedia, Collapse, makeStyles, Typography} from '@material-ui/core';
import Animal from '../../models/animal.model';
import ImageService from '../../services/image.service';
import clsx from 'clsx';
import CardActions from '@material-ui/core/CardActions';
import IconButton from '@material-ui/core/IconButton';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles((theme) => ({
    root: {
        background: 'background-image: linear-gradient(315deg, #ffffff 0%, #d7e1ec 74%);',
        display: 'inline-block',
        margin: '15px',
        minWidth: 275,
        maxWidth: 275,
        position: 'relative'
    },
    disabled: {
        filter: 'grayscale(100%)'
    },
    selected: {
        boxShadow: '0 4px 7px 3px rgba(0, 55, 255, 0.7)',
    },
    media: {
        height: 140,
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    },

}));

type AnimalCardProps = {
    animal: Animal,
    disabled?: boolean,
    onSelected?: Function
    selected: boolean
    setSelected?: Function
}

function AnimalCard(props: AnimalCardProps) {
    const disabled = !props.disabled ? false : true;

    const styles = useStyles();

    const gender = props.animal.gender === "MALE" ? "Muško" : "Žensko";

    const [expanded, setExpanded] = React.useState(false);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const onClicked = () => {
        if (!disabled) {
            if (!!props.onSelected)
                props.onSelected(props.animal.id, !props.selected);
            return !props.selected;
        }
    }

    return (
        <Card
            className={`${styles.root} ${props.selected && !disabled ? styles.selected : ''} ${disabled ? styles.disabled : ''}`}
            variant="outlined">
            <CardActionArea onClick={event => {
                onClicked()
            }} disableRipple={disabled}>
                <CardMedia
                    className={styles.media} component='img' src={ImageService.getAnimalImageUrl(props.animal.id)}
                    onError={(e: any) => {
                        e.target.src = "/logo512.png"

                    }}
                />
                <CardContent>
                    <div className="animal-card-name-time">
                        <Typography variant="h5" component="h2">
                            {props.animal.name}
                        </Typography>
                    </div>
                    <Typography color="textSecondary" gutterBottom>
                        {props.animal.breed.breedName + ', ' + gender + ', ' + props.animal.birthYear}
                    </Typography>
                    <Typography color="textSecondary" gutterBottom>
                        {props.animal.description}
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>

                    <IconButton
                        className={clsx(styles.expand, {
                            [styles.expandOpen]: expanded,
                        })}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more"
                    >
                        <ExpandMoreIcon/>
                    </IconButton>
                </CardActions>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <Typography paragraph>
                            Starost: {2020 - props.animal.birthYear}
                            <br/>
                            Visina: {props.animal.breed.height}
                            <br/>
                            Težina: {props.animal.breed.weight}
                            <br/>
                            Životni vijek: {props.animal.breed.lifeExpectancy}
                            <br/>
                            Grupa: {props.animal.breed.group}
                            <br/>
                            Vrsta šetnje: {props.animal.walkType === "GROUP" ? "GRUPNA" : "POJEDINAČNA"}
                        </Typography>
                        <Typography>
                            Opis: {props.animal.description}
                        </Typography>
                    </CardContent>
                </Collapse>
            </CardActionArea>
        </Card>
    );
}

export default AnimalCard;