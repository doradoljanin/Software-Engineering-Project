import React, { ChangeEvent, useEffect, useState } from 'react';
import { Button, Grid } from '@material-ui/core';
import obj from '../../services/identity.service';
import {Box, Typography} from '@material-ui/core';
import imageService from '../../services/image.service';
import MyStatistics from '../basic/my.statistics';
import ProfileService from '../../services/profile.service';
import AssociationScreenInternal from './association.screen.internal';
import '../../styles/Profie.css';
import '../../styles/Global.css';
import history from '../../misc/history';
import api from '../../services/api.service';
import { AxiosResponse } from 'axios';

function Profile() {
    let upload: HTMLInputElement | null = null;

    const imageUpload = () => {
        upload!.click();
    }

    const onChangeFile = (event: ChangeEvent<HTMLInputElement>) => {
        event.stopPropagation();
        event.preventDefault();
        if (event.target.files) {
            var file = event.target.files[0];
            console.log(file);

            var form = new FormData();
            form.append('imagefile', file);

            api.post(imageService.getUserImageUrl(), form, {
                headers: 'multipart/form-data'
            }).then((response: AxiosResponse) => {
                window.location.reload(false);
            });
        }
    }

    const userDetails = obj.getIdentityContext();

    const [imgSrc, setImgSrc] = useState<string | undefined>(undefined);

    useEffect(() => {
        imageService.getUserImageAndSetIt(setImgSrc);
    }, []);

    const data = ProfileService.useGetProfile();

    return ((!!data ?
        (<div>

            <input id="imageInput"
                type="file"
                ref={(ref) => upload = ref}
                style={{display: 'none'}}
                onChange={onChangeFile}
            />

           {userDetails?.roles.includes("ROLE_ASSOCIATION") ? (
            <>
                <div style={{margin: "10px 0px", display: 'flex'}}>
                    <Button variant="contained" color="primary" onClick={() => {history.push("/profile/association/edit")}}>Uredi podatke</Button>
                    <Button variant="contained" color="primary" onClick={() => {imageUpload()}}>Promijeni sliku</Button>
                    <Button variant="contained" color="primary" onClick={() => {history.push("/profile/association/animals")}}>Uredi pse</Button>
                </div>
                <AssociationScreenInternal id={data.id.toString()} />
            </>
            ): ( 
            <>
           
           <div className={"container-page"}>
                <div className="container-profile">
                    <div>
                        <img alt="" src={`data:image/*;base64,${imgSrc}`}></img>
                    </div>
                    <div>
                        <div className="container-profileinfo">
                            <h2>Moj profil</h2>
                        </div>
                        <Typography variant="h4" align="center">{data!.firstName}</Typography>
                        <Typography variant="h4" align="center">{data!.lastName}</Typography>
                        <Typography variant="h4" align="center">{data!.email}</Typography>
                        <Typography variant="h4" align="center">{data!.username}</Typography>
                        <Typography variant="h4" align="center">{data!.phoneNumber}</Typography>
                        <Button variant="contained" color="primary" onClick={() => {history.push("/profile/citizen/edit")}} className="editbutton">Uredi podatke</Button>
                        <Button variant="contained" color="primary" onClick={() => {imageUpload()}} className="editbutton">Promijeni sliku</Button>
                    </div>  
                </div>
                <div className="profiletitle">
                    <p>Moja statistika:</p>
                </div>   
                <div className="statisticsdiv">
                    <MyStatistics/>
                </div>         
            </div>
            </>
             )} 
           
        
        </div>) : <>
                <div className="center">
                   Niste prijavljeni. Ako već imate račun molimo pritisnite na
                    <a href="/login"><Button className="btn" onClick={() => history.push("/login")}>Prijavi se</Button></a>
                    a ako nemate pritisnite
                    <a href="/registration"><Button className="btn"
                                                    onClick={() => history.push("/registration")}>Registriraj se</Button></a>

                </div>

            </>
            ));
   
}

export default Profile;