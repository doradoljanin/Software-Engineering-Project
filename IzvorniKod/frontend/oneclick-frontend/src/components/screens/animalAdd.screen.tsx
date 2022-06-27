import { Button, FormControl, FormHelperText, InputLabel, MenuItem, Select, TextField } from '@material-ui/core';
import React from 'react';
import { Controller, useForm } from 'react-hook-form';
import { MdPets } from 'react-icons/md';
import NewAnimal from '../../models/newAnimal.model';
import AnimalService from '../../services/animal.service';
import history from '../../misc/history';
import '../../styles/AddAnimal.css';

export default function AnimalAddScreen() {
    const breeds = AnimalService.useGetBreeds();

    const { register, handleSubmit, control, errors, setError } = useForm<NewAnimal>();

    const onSubmit = (data: NewAnimal) => {
        AnimalService.postAnimal(data).then(() => {
            history.push("/profile/association/animals");
        });
    }

    return (<div>
        <div className="addanimalbox">
         <a href="/"><MdPets className="addanimallogo"/></a>
            <div className="container">
               <form onSubmit={handleSubmit(onSubmit)} className="form-registration" noValidate>
                  <div className="horizontal-center-add-animal">
                     <h2 className="text">Dodavanje psa:</h2>
                  </div>
                  <h3 className="addanimaltext">Ime</h3>
                  <div className="horizontal-center-add-animal">
                     <TextField id="name" 
                        inputRef={register({
                           required: "Polje je obavezno."
                        })} 
                        name="name" 
                        label="Unesite ime psa" 
                        variant="outlined"
                        className="textfield"
                        helperText={errors.name && errors.name.message} 
                        error={!!errors.name} />
                  </div>
                  <h3 className="addanimaltext">Pasmina</h3>
                  <div className="horizontal-center">
                    <FormControl className="form-registration-select" error={!!errors.breedId} variant="outlined">
                        <InputLabel htmlFor="breed-select" >Odaberite pasminu</InputLabel>
                        <Controller
                           as={
                              <Select name="breed-select" id="breed-select" label="Pasmina">
                                 {
                                    breeds.map(b => 
                                       (<MenuItem key={b.id} value={b.id}>{b.breedName}</MenuItem>)
                                    )
                                 }
                              </Select>
                           }
                           defaultValue={""}
                           control={control}
                           name="breedId"
                           id="breedId"
                           rules = {{ required: "Polje je obavezno." }}
                        />
                        <FormHelperText>{errors.breedId && errors.breedId.message}</FormHelperText>
                     </FormControl>
                  </div>
                  <h3 className="addanimaltext">Godište</h3>
                  <div className="horizontal-center-add-animal">
                     <TextField id="birthYear" 
                        inputRef={register({
                           required: "Polje je obavezno."
                        })} 
                        name="birthYear" 
                        label="Unesite godište" 
                        variant="outlined"
                        className="textfield"
                        helperText={errors.birthYear && errors.birthYear.message}
                        error={!!errors.birthYear} />
                  </div>
                  <h3 className="addanimaltext">Spol</h3>
                  <div className="horizontal-center">
                     <FormControl className="form-registration-select" error={!!errors.gender} variant="outlined">
                        <InputLabel htmlFor="gender-select" >Odaberite spol</InputLabel>
                        <Controller
                           as={
                              <Select name="gender-select" id="gender-select" label="Mjesto">
                                 <MenuItem key={"m"} value={"MALE"}>Muško</MenuItem>
                                 <MenuItem key={"f"} value={"FEMALE"}>Žensko</MenuItem>
                              </Select>
                           }
                           defaultValue={""}
                           control={control}
                           name="gender"
                           id="gender"
                           rules = {{ required: "Polje je obavezno." }}
                        />
                        <FormHelperText>{errors.gender && errors.gender.message}</FormHelperText>
                     </FormControl>
                  </div>
                  <h3 className="addanimaltext">Vrsta šetnje</h3>
                  <div className="horizontal-center">
                     <FormControl className="form-registration-select" error={!!errors.walkType} variant="outlined">
                        <InputLabel htmlFor="walktype-select" >Odaberite vrstu šetnje</InputLabel>
                        <Controller
                           as={
                              <Select name="walktype-select" id="walktype-select" label="Mjesto">
                                 <MenuItem key={"single"} value={"SINGLE"}>Pojedinačna</MenuItem>
                                 <MenuItem key={"group"} value={"GROUP"}>Grupna</MenuItem>
                              </Select>
                           }
                           defaultValue={""}
                           control={control}
                           name="walkType"
                           id="walkType"
                           rules = {{ required: "Polje je obavezno." }}
                        />
                        <FormHelperText>{errors.walkType && errors.walkType.message}</FormHelperText>
                     </FormControl>
                  </div>
                  <h3 className="addanimaltext">Opis</h3>
                  <div className="horizontal-center-add-animal">
                     <TextField id="description" 
                        inputRef={register()} 
                        name="description" 
                        label="Unesite opis" 
                        variant="outlined"
                        className="textfield"
                        helperText={errors.description && errors.description.message}
                        error={!!errors.description} />
                  </div>
                  <div className="horizontal-center-add-animal-btn">
                     <Button type="submit" variant="contained" color="primary">Dodaj</Button>
                  </div>
               </form>
            </div>
         </div>
    </div>);
}