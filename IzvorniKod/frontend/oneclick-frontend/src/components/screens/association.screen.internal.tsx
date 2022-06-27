import React from 'react';
import '../../styles/Association.css';
import '../../styles/Global.css';
import imageService from '../../services/image.service';
import AssociationService from '../../services/association.service';
import AnimalSelector from '../advanced/animalSelector';
import obj from '../../services/identity.service';

type AssociationScreenProps = {
    id: string
}

function AssociationScreenInternal(props: AssociationScreenProps) {
    const [data, isFetching] = AssociationService.useGetAssociation(props.id);

    const userDetails = obj.getIdentityContext();

    return (<>
            {((!isFetching && !data) &&
                <div className={"container-page"}>
                    <div className={"association-error"}>
                        <h1>
                            Ova udruga ne postoji!
                        </h1>
                    </div>
                </div>)
            || ((!isFetching && !!data) &&
                <div className={"container-page"}>
                    <div className={"container-association"}>
                        <div>
                            <img src={imageService.getAssociationImageUrl(props.id)} alt=""/>
                        </div>
                        <div className="associationinfo">
                            <div className={"association-name"}>
                                <h1>
                                    {data!.name}
                                </h1>
                            </div>
                            <div className={"association-address"}>
                                <h2>
                                    {data!.address}, {data!.place.name}
                                </h2>
                            </div>
                            <div className={"association-address"}>
                                <h2>
                                    OIB: {data!.oib}
                                </h2>
                            </div>
                            <div className={"association-address"}>
                                <h2>
                                    Broj mobitela: {data!.phoneNumber}
                                </h2>
                            </div>
                        </div>
                        <div className="association-map">
                            <iframe
                                width="320"
                                height="330"
                                frameBorder="0"
                                style={{border: 0}}
                                src={`https://www.google.com/maps/embed/v1/place?key=AIzaSyCIIskDofJHbJtt5-dBh7_p2s1HExY93BA
                                &q=${data!.address},${data!.place.name}
                                `}
                                allowFullScreen>
                                {/*
                                &zoom=20
                                &maptype=satellite*/}

                            </iframe>

                        </div>
                    </div>
                    {userDetails?.roles.includes("ROLE_ASSOCIATION") ?
                        (<>
                        <div className="container-animalList">
                            <AnimalSelector animals={data!.animals} associationId={props.id}/>
                        </div>
                        </>) :
                        (<>
                            <div className="container-animalList">
                                <h3>Odaberite termin šetnje</h3>
                                <AnimalSelector animals={data!.animals} associationId={props.id}/>
                            </div>
                        </>)}
                </div>)
            }
        </>
    );
}

export default AssociationScreenInternal;
