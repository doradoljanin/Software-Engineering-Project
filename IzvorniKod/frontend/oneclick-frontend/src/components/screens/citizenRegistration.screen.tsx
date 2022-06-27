import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React from 'react';
import {useForm} from 'react-hook-form';
import CitizenRegistrationInfo from '../../models/citizenRegistration.model';
import '../../styles/Registration.css';
import RegistrationService from '../../services/registration.service';
import history from '../../misc/history';
import RegistrationValidationError from '../../models/registrationValidationError.model';
import {MdPets} from "react-icons/md";
import LoginService from "../../services/login.service";
import {Lock, Mail, Person, Phone} from "@material-ui/icons/";


function CitizenRegistration() {
    const {register, handleSubmit, errors, setError} = useForm<CitizenRegistrationInfo>();


    const onSubmit = (data: CitizenRegistrationInfo) => {
        console.log(data);

        RegistrationService.registerCitizen(
            data
        ).then(response => {
            if (response.status === 200) {
                history.push("/login");
            }
        }).catch(error => {
            const data = (error.response.data as RegistrationValidationError);

            switch (data.field) {
                case "USERNAME":
                    if (data.error === "NON_UNIQUE") {
                        setError("username", {type: "validate", message: "Korisničko ime je zauzeto!"});
                    }
                    break;
                case "EMAIL":
                    if (data.error === "NON_UNIQUE") {
                        setError("email", {type: "validate", message: "Email adresa je zauzeta!"});
                    }
                    break;
                case "PHONE_NUMBER":
                    if (data.error === "INVALID_FORMAT") {
                        setError("phoneNumber", {type: "validate", message: "Format broja telefona nije validan."});
                    }
            }
        });
    }

    return (
        !LoginService.isLoggedIn() ? (
                <div>
                    <div className="citizenregistrationbox">
                        <a href="/"><MdPets className="bigregistrationlogo"/></a>
                        <div className="container">
                            <form onSubmit={handleSubmit(onSubmit)} className="form-registration" noValidate>
                                <div className="horizontal-center">
                                    <h1 className="text">Registracija građanina:</h1>
                                </div>
                                <div className="login">
                                    <Person/>
                                    <h3 className="registrationtext">Korisničko ime</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="username"
                                               inputRef={register({
                                                   required: "Polje je obavezno.",
                                                   pattern: {
                                                       value: /^[A-Z0-9._-]{6,20}$/i,
                                                       message: "Format korisničkog imena nije validan."
                                                   }
                                               })}
                                               name="username"
                                               label="Unesite korisničko ime"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.username && errors.username.message}
                                               error={!!errors.username}/>
                                </div>
                                <div className="login">
                                    <Mail/>
                                    <h3 className="registrationtext">E-mail adresa</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="email"
                                               type="email"
                                               inputRef={register({
                                                   required: "Polje je obavezno.",
                                                   pattern: {
                                                       value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                                       message: "Format adrese nije validan."
                                                   }
                                               })}
                                               name="email"
                                               label="Unesite e-mail adresu"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.email && errors.email.message}
                                               error={!!errors.email}/>
                                </div>
                                <div className="login">
                                    <Lock/>
                                    <h3 className="registrationtext">Lozinka</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="password"
                                               type="password"
                                               inputRef={register({
                                                   required: "Polje je obavezno.",
                                                   pattern: {
                                                       value: /^[A-Z0-9.!_-]{8,30}$/i,
                                                       message: "Format šifre nije validan."
                                                   }
                                               })}
                                               name="password"
                                               label="Unesite lozinku"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.password && errors.password.message}
                                               error={!!errors.password}/>
                                </div>
                                <div className="login">
                                    <Person/>
                                    <h3 className="registrationtext">Ime</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="firstName"
                                               inputRef={register({required: "Polje je obavezno."})}
                                               name="firstName"
                                               label="Unesite ime"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.firstName && errors.firstName.message}
                                               error={!!errors.firstName}/>
                                </div>
                                <div className="login">
                                    <Person/>
                                    <h3 className="registrationtext">Prezime</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="lastName"
                                               inputRef={register({required: "Polje je obavezno."})}
                                               name="lastName"
                                               label="Unesite prezime"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.lastName && errors.lastName.message}
                                               error={!!errors.lastName}/>
                                </div>
                                <div className="login">
                                    <Phone/>
                                    <h3 className="registrationtext">Broj telefona</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="phoneNumber"
                                               type="tel"
                                               inputRef={register({required: "Polje je obavezno."})}
                                               name="phoneNumber"
                                               label="Unesite broj telefona"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.phoneNumber && errors.phoneNumber.message}
                                               error={!!errors.phoneNumber}/>
                                </div>
                                <div className="horizontal-center">
                                    <Button type="submit" variant="contained" color="primary">Registracija</Button>
                                </div>
                                <div className="horizontal-center">
                                    <p className="logintext">Već imate korisnički račun? <a href="/login" className="logintext">Prijavite
                                        se</a></p>
                                </div>
                            </form>
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

export default CitizenRegistration;