import React, { ChangeEvent } from 'react';
import ProfileService from "../../services/profile.service";
import { Avatar, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@material-ui/core";
import ImageService from "../../services/image.service";
import { AxiosResponse } from 'axios';
import api from '../../services/api.service';
import getHostName from '../../services/host.service';
import history from '../../misc/history';
import '../../styles/EditAnimal.css';

export default function AnimalEditScreen() {
    let upload: HTMLInputElement | null = null;

    let clickedId = 0;

    const imageUpload = (id: number) => {
        upload!.click();
        clickedId = id;
    }

    const onChangeFile = (event: ChangeEvent<HTMLInputElement>) => {
        event.stopPropagation();
        event.preventDefault();
        if (event.target.files) {
            var file = event.target.files[0];
            console.log(file);

            var form = new FormData();
            form.append('imagefile', file);

            api.post(ImageService.getAnimalImageUrl(clickedId), form, {
                headers: 'multipart/form-data'
            }).then((response: AxiosResponse) => {
                window.location.reload(false);
            });
        }
    }

    const deleteAnimal = (id: number) => {
        api.get(getHostName(`/association/animals/remove/${id}`)).then((response: AxiosResponse) => {
            window.location.reload(false);
        })
    }

    const animalData = ProfileService.useGetAnimals();
    
    return (<div>

        <input id="imageInput"
            type="file"
            ref={(ref) => upload = ref}
            style={{display: 'none'}}
            onChange={onChangeFile}
        />
        <Button variant="contained" color="primary" className="adddogbtn" onClick={() => {history.push("/profile/association/animals/add")}}>Dodaj psa</Button>

        <TableContainer>
            <Table>
                <TableHead>
                    <TableCell>Ime</TableCell>
                    <TableCell>Slika</TableCell>
                    <TableCell>Pasmina</TableCell>
                    <TableCell>Godina rođenja</TableCell>
                    <TableCell>Spol</TableCell>
                    <TableCell>Vrsta šetnje</TableCell>
                    <TableCell>Opis</TableCell>
                    <TableCell></TableCell>
                </TableHead>
                <TableBody>
                    {!!animalData && animalData.map(a => (
                        <TableRow>
                            <TableCell>
                                {a.name}
                            </TableCell>
                            <TableCell>
                                <Avatar src={ImageService.getAnimalImageUrl(a.id)} />
                                <Button variant="contained" color="primary" className="editimgbtn" onClick={() => {imageUpload(a.id)}}>Promijeni sliku</Button>
                            </TableCell>
                            <TableCell>
                                {a.breed.breedName}
                            </TableCell>
                            <TableCell>
                                {a.birthYear}
                            </TableCell>
                            <TableCell>
                                {a.gender === "MALE" ? "Muško" : "Žensko"}
                            </TableCell>
                            <TableCell>
                                {a.walkType === "SINGLE" ? "Pojedinačna" : "Grupna"}
                            </TableCell>
                            <TableCell>
                                {a.description}
                            </TableCell>
                            <TableCell>
                                <Button variant="contained" color="primary" onClick={() => {deleteAnimal(a.id)}}>Izbriši životinju</Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    </div>);
}