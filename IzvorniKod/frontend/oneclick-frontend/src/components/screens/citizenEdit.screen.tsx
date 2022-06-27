import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React, { ChangeEvent, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import CitizenRegistrationInfo from '../../models/citizenRegistration.model';
import '../../styles/Registration.css';
import RegistrationService from '../../services/registration.service';
import history from '../../misc/history';
import RegistrationValidationError from '../../models/registrationValidationError.model';
import {MdPets} from "react-icons/md";
import ProfileService from '../../services/profile.service';
import { Checkbox, FormControlLabel } from '@material-ui/core';
import AccountEditService from '../../services/accountEdit.service';

function CitizenAccountEdit() {
    const profileData = ProfileService.useGetProfile();

    const { register, handleSubmit, errors, setError, setValue } = useForm<CitizenRegistrationInfo>();

    useEffect(() => {
        register("isPublic");
    }, []);

    let isPublicValueChanged = false;

    const isPublicChanged = (event: ChangeEvent<HTMLElement>, checked: boolean) => {
        setValue("isPublic", checked);
        isPublicValueChanged = true;
    }

    const onSubmit = (data: CitizenRegistrationInfo) => {
        console.log(data);

        if (!isPublicValueChanged)
            data.isPublic = profileData!.public;   

        AccountEditService.postCitizenEdit(
            data
        ).then(response => {
            if (response.status === 200) {
                history.push("/");
            }
        }).catch(error => {
            const data = (error.response.data as RegistrationValidationError);

            switch (data.field) {
                case "USERNAME":
                if (data.error === "NON_UNIQUE") {
                    setError("username", { type: "validate", message: "Korisničko ime je zauzeto!" });
                }
                break;
                case "EMAIL":
                if (data.error === "NON_UNIQUE") {
                    setError("email", { type: "validate", message: "Email adresa je zauzeta!" });
                }
                break;
                case "PHONE_NUMBER":
                if (data.error === "INVALID_FORMAT") {
                    setError("phoneNumber", { type: "validate", message: "Format broja telefona nije validan."});
                }  
            }
        });
    }

    return (
        <div>
            {(!!profileData && (
            <div className="citizenregistrationbox">
            <a href="/"><MdPets className="bigregistrationlogo"/></a>
                <div className="container">
                <form onSubmit={handleSubmit(onSubmit)} className="form-registration" noValidate>
                    <div className="horizontal-center">
                        <h1 className="text">Izmjena profila:</h1>
                    </div>
                    <h3 className="registrationtext">Korisničko ime</h3>
                    <div className="horizontal-center">
                        <TextField id="username" 
                            inputRef={register({
                            pattern: {
                                value: /^[A-Z0-9._-]{6,20}$/i,
                                message: "Format korisničkog imena nije validan."
                            }
                            })} 
                            name="username" 
                            label="Unesite korisničko ime" 
                            variant="outlined"
                            defaultValue={profileData.username}
                            className="textfield"
                            helperText={errors.username && errors.username.message} 
                            error={!!errors.username} />
                    </div>
                    <h3 className="registrationtext">E-mail adresa</h3>
                    <div className="horizontal-center">
                        <TextField id="email" 
                            type="email" 
                            inputRef={register({
                            pattern: {
                                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                message: "Format adrese nije validan."
                            }
                            })} 
                            name="email" 
                            label="Unesite e-mail adresu" 
                            variant="outlined"
                            defaultValue={profileData.email}
                            className="textfield"
                            helperText={errors.email && errors.email.message}
                            error={!!errors.email} />
                    </div>
                    <h3 className="registrationtext">Lozinka</h3>
                    <div className="horizontal-center">
                        <TextField id="password" 
                            type="password" 
                            inputRef={register({
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
                            error={!!errors.password} />
                    </div>
                    <h3 className="registrationtext">Ime</h3>
                    <div className="horizontal-center">
                        <TextField id="firstName" 
                            inputRef={register()} 
                            name="firstName" 
                            label="Unesite ime" 
                            variant="outlined"
                            defaultValue={profileData.firstName}
                            className="textfield" 
                            helperText={errors.firstName && errors.firstName.message}
                            error={!!errors.firstName} />
                    </div>
                    <h3 className="registrationtext">Prezime</h3>
                    <div className="horizontal-center">
                        <TextField id="lastName" 
                            inputRef={register()} 
                            name="lastName" 
                            label="Unesite prezime" 
                            variant="outlined"
                            defaultValue={profileData.lastName}
                            className="textfield"
                            helperText={errors.lastName && errors.lastName.message}
                            error={!!errors.lastName} />
                    </div>
                    <h3 className="registrationtext">Broj telefona</h3>
                    <div className="horizontal-center">
                        <TextField id="phoneNumber" 
                            type="tel" 
                            inputRef={register()} 
                            name="phoneNumber" 
                            label="Unesite broj telefona" 
                            variant="outlined"
                            defaultValue={profileData.phoneNumber}
                            className="textfield"
                            helperText={errors.phoneNumber && errors.phoneNumber.message}
                            error={!!errors.phoneNumber} />
                    </div>
                    <div className="horizontal-center">
                        <FormControlLabel
                            className="text"
                            control={
                                <Checkbox
                                        name="isPublic"
                                        id="isPublic" 
                                        onChange={isPublicChanged}
                                        defaultChecked={profileData.public}
                                        className="checkbox"
                                    />
                            }
                            label="Želim da moja statistika bude javna"  
                            />
                    </div>
                    <div className="horizontal-center">
                        <Button type="submit" variant="contained" color="primary">Spremi podatke</Button>
                    </div>
                </form>
                </div>
            </div>))}
        </div>
    );
}

export default CitizenAccountEdit;

