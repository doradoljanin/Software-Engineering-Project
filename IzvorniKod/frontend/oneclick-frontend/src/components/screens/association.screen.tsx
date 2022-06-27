import React from 'react';
import '../../styles/Association.css';
import '../../styles/Global.css';
import imageService from '../../services/image.service';
import AssociationService from '../../services/association.service';
import { RouteComponentProps } from 'react-router';
import AnimalSelector from '../advanced/animalSelector';
import AssociationScreenInternal from './association.screen.internal';
import { Button } from '@material-ui/core';

type AssociationScreenProps = {
    id: string
}

function AssociationScreen({ match }: RouteComponentProps<AssociationScreenProps>) {
    return ( 
        <AssociationScreenInternal id={match.params.id} />
    );
}

export default AssociationScreen;
