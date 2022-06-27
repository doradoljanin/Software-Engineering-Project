import Button from '@material-ui/core/Button';
import React from 'react';
import '../../styles/Registration.css';
import {MdPets} from "react-icons/md";
import history from '../../misc/history';
import LoginService from "../../services/login.service";

function Registration() {
    return (
        !LoginService.isLoggedIn() ? (
                <div>
                    <div className="registrationbox">
                        <a href="/"><MdPets className="bigregistrationlogo"/></a>
                        <div className="container">
                            <h1 className="registrationtitle">Registracija:</h1>
                            <div className="registration-horizontal-center">
                                <div className="horizontal-center">
                                    <Button className="registration-button" variant="contained" color="primary"
                                            onClick={() => history.push("/registration/citizen")}>Registrirajte se kao
                                        građanin</Button>
                                </div>
                                <div className="horizontal-center">
                                    <Button variant="contained" color="primary"
                                            onClick={() => history.push("/registration/association")}>Registrirajte se kao
                                        udruga</Button>
                                </div>
                            </div>
                            <p className="logintext">Već imate korisnički račun? <a href="/login" className="logintext">Prijavite
                                se</a></p>
                        </div>
                    </div>
                </div>) :
            (
                <div>
                    <br/>
                    <div className="horizontal-center">
                        Već ste registrirani!
                    </div>
                </div>
            )
    );
}

export default Registration;

