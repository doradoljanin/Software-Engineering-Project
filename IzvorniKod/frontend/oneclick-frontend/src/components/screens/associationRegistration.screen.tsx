import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React from 'react';
import AssociationRegistrationInfo from '../../models/associationRegistration.model';
import RegistrationService from '../../services/registration.service';
import {Controller, useForm} from 'react-hook-form';
import {FormControl, FormHelperText, InputLabel, MenuItem, Select} from '@material-ui/core';
import {useGetPlaces} from '../../services/place.service';
import '../../styles/Registration.css';
import history from '../../misc/history';
import RegistrationValidationError from '../../models/registrationValidationError.model';
import {MdPets} from "react-icons/md";
import LoginService from "../../services/login.service";
import {Business, Fingerprint, LocationCity, Lock, Mail, Person, Phone, Place} from "@material-ui/icons";

function AssociationRegistration() {
    const data = useGetPlaces();

    const {register, handleSubmit, control, errors, setError} = useForm<AssociationRegistrationInfo>();


    const onSubmit = (data: AssociationRegistrationInfo) => {
        console.log(data);

        RegistrationService.registerAssociation(
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

                    <div className="associationregistrationbox">
                        <a href="/"><MdPets className="bigregistrationlogo"/></a>
                        <div className="container">
                            <form onSubmit={handleSubmit(onSubmit)} className="form-registration" noValidate>
                                <div className="horizontal-center">
                                    <h2 className="text">Registracija udruge:</h2>
                                </div>
                                <div className="login">
                                    <Person/>
                                    <h3 className="registrationtext">Korisničko ime</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField
                                        id="username"
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
                                    <h3 className="registrationtext">E-mail</h3>
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
                                                       value: /^[A-Za-z0-9.!_-]{8,30}$/,
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
                                    <Business/>
                                    <h3 className="registrationtext">Naziv</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="name"
                                               inputRef={register({required: "Polje je obavezno."})}
                                               name="name"
                                               label="Unesite naziv udruge"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.name && errors.name.message}
                                               error={!!errors.name}/>
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
                                <div className="login">
                                    <Fingerprint/>
                                    <h3 className="registrationtext">OIB</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="oib"
                                               type="oib"
                                               inputRef={register({
                                                   required: "Polje je obavezno.",
                                                   pattern: {
                                                       value: /^[0-9]{11}$/i,
                                                       message: "Format OIB-a nije validan."
                                                   }
                                               })}
                                               name="oib"
                                               label="Unesite OIB"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.oib && errors.oib.message}
                                               error={!!errors.oib}/>
                                </div>
                                <div className="login">
                                    <Place/>
                                    <h3 className="registrationtext">Adresa</h3>
                                </div>
                                <div className="horizontal-center">
                                    <TextField id="address"
                                               inputRef={register({required: "Polje je obavezno."})}
                                               name="address"
                                               label="Unesite adresu"
                                               variant="outlined"
                                               className="textfield"
                                               helperText={errors.address && errors.address.message}
                                               error={!!errors.address}/>
                                </div>
                                <div className="login">
                                    <LocationCity/>
                                    <h3 className="registrationtext">Mjesto</h3>
                                </div>
                                <div className="horizontal-center">
                                    <FormControl className="form-registration-select" error={!!errors.place} variant="outlined">
                                        <InputLabel htmlFor="place-select">Odaberite mjesto</InputLabel>
                                        <Controller
                                            as={
                                                <Select name="place-select" id="place-select" label="Mjesto">
                                                    {
                                                        data.map(place =>
                                                            (<MenuItem key={place.id} value={place.id}>{place.name}</MenuItem>)
                                                        )
                                                    }
                                                </Select>
                                            }
                                            defaultValue={""}
                                            control={control}
                                            name="place"
                                            id="place"
                                            rules={{required: "Polje je obavezno."}}
                                        />
                                        <FormHelperText>{errors.place && errors.place.message}</FormHelperText>
                                    </FormControl>
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

                </div>)
            : (
                <div>
                    <br/>
                    <div className="horizontal-center">
                        Već ste registrirani!
                    </div>
                </div>
            )
    );
}


export default AssociationRegistration;
