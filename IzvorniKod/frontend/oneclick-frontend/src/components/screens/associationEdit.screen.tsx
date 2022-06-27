import AssociationAccountModel from "../../models/associationAccount.model";
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React, { ChangeEvent, useEffect } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { Checkbox, FormControl, FormControlLabel, FormHelperText, InputLabel, MenuItem, Select } from '@material-ui/core';
import { useGetPlaces } from '../../services/place.service';
import '../../styles/Registration.css';
import history from '../../misc/history';
import RegistrationValidationError from '../../models/registrationValidationError.model';
import {MdPets} from "react-icons/md";
import ProfileService from '../../services/profile.service';
import AccountEditService from "../../services/accountEdit.service";
import AssociationRegistrationInfo from "../../models/associationRegistration.model";

export default function AssociationEditScreen() {
    const profileData = ProfileService.useGetProfile() as unknown as AssociationAccountModel;

    const data = useGetPlaces();

    const { register, handleSubmit, control, errors, setError, setValue } = useForm<AssociationRegistrationInfo>();

    useEffect(() => {
        register("isPublic");
    }, []);

    let isPublicValueChanged = false;

    const isPublicChanged = (event: ChangeEvent<HTMLElement>, checked: boolean) => {
            setValue("isPublic", checked);
            isPublicValueChanged = true;
    }

    const onSubmit = (data: AssociationRegistrationInfo) => {
        console.log(data);

        if (!isPublicValueChanged)
            data.isPublic = profileData.public;   

        AccountEditService.postAssociationEdit(
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
         <div className="associationregistrationbox">
         <a href="/"><MdPets className="bigregistrationlogo"/></a>
            <div className="container">
               <form onSubmit={handleSubmit(onSubmit)} className="form-registration" noValidate>
                  <div className="horizontal-center">
                     <h2 className="text">Izmjena udruge:</h2>
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
                        className="textfield"
                        defaultValue={profileData.username}
                        helperText={errors.username && errors.username.message} 
                        error={!!errors.username} />
                  </div>
                  <h3 className="registrationtext">E-mail</h3>
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
                              value: /^[A-Za-z0-9.!_-]{8,30}$/,
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
                  <h3 className="registrationtext">Naziv</h3>
                  <div className="horizontal-center">
                     <TextField id="name" 
                        inputRef={register()} 
                        name="name" 
                        label="Unesite naziv udruge" 
                        variant="outlined" 
                        defaultValue={profileData.name}
                        className="textfield"
                        helperText={errors.name && errors.name.message}
                        error={!!errors.name} />
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
                  <h3 className="registrationtext">OIB</h3>
                  <div className="horizontal-center">
                     <TextField id="oib" 
                        type="oib" 
                        inputRef={register({
                           pattern: {
                              value: /^[0-9]{11}$/i,
                              message: "Format OIB-a nije validan."
                           }
                        })} 
                        name="oib" 
                        label="Unesite OIB" 
                        variant="outlined"
                        defaultValue={profileData.oib}
                        className="textfield"
                        helperText={errors.oib && errors.oib.message}
                        error={!!errors.oib} />
                  </div>
                  <h3 className="registrationtext">Adresa</h3>
                  <div className="horizontal-center">
                     <TextField id="address" 
                        inputRef={register()} 
                        name="address" 
                        label="Unesite adresu" 
                        variant="outlined"
                        defaultValue={profileData.address}
                        className="textfield"
                        helperText={errors.address && errors.address.message}
                        error={!!errors.address} />
                  </div>
                  <h3 className="registrationtext">Mjesto</h3>
                  <div className="horizontal-center">
                     <FormControl className="form-registration-select" error={!!errors.place} variant="outlined">
                        <InputLabel htmlFor="place-select" >Odaberite mjesto</InputLabel>
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
                           defaultValue={profileData.place.id}
                           control={control}
                           name="place"
                           id="place"
                           rules = {{ required: "Polje je obavezno." }}
                        />
                        <FormHelperText>{errors.place && errors.place.message}</FormHelperText>
                     </FormControl>
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
                        label="Želim da moja udruga bude javna"  
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