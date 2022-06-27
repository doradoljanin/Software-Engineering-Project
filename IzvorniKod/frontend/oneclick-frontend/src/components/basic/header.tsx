import { Avatar, Button, Input } from '@material-ui/core';
import React, { ChangeEvent, useEffect, useState } from 'react';
import '../../styles/Header.css';
import history from '../../misc/history';
import LoginService from '../../services/login.service';
import {MdPets} from "react-icons/md";
import ImageService from '../../services/image.service';


function Header() {
    const [imgSrc, setImgSrc] = useState<string | undefined>(undefined);

    const [burger, setBurger] = useState(false);

    useEffect(() => {
        ImageService.getUserImageAndSetIt(setImgSrc);
    }, []);

    return (
        <div>
            <header>
                <div className="nav">
                    <div className="homeicon">
                        <a href="/"><MdPets className="headerlogo"/></a>
                        <a href="/"><h1 className="title">Humanitarni šetači pasa</h1></a>
                    </div>
                    <label htmlFor="toggle" className="burgerbar">&#9776;</label>
                    <input type="checkbox" checked={burger} onChange={(e: ChangeEvent<HTMLInputElement>) => setBurger(e.target.checked)} id="toggle"/>
                    <div className="menu">
                        <Button className="btn" onClick={() => {history.push("/"); setBurger(false);}}>Početna stranica</Button>
                        <Button className="btn" onClick={() => {history.push("/statistics"); setBurger(false);}}>Statistika</Button>
                        { LoginService.isLoggedIn() ? (
                            <>
                                <Button className="btn" onClick={() => {history.push("/calendar"); setBurger(false);}}>Raspored</Button>
                                <Button className="btn" onClick={() => {history.push("/profile"); setBurger(false);}}>Profil&nbsp;&nbsp;<Avatar src={`data:image/*;base64,${imgSrc}`} /></Button>
                                <Button className="btn" onClick={() => {LoginService.logout(); setBurger(false);}}>Odjava</Button>
                            </>
                        ) : (
                            <>
                                <Button className="btn" onClick={() => {history.push("/registration"); setBurger(false);}}>Registracija</Button>
                                <Button className="btn" onClick={() => {history.push("/login"); setBurger(false);}}>Prijava</Button>
                            </>
                        )}
                    </div>
                    

                </div>
            </header>
        </div>
    );
}

export default Header;