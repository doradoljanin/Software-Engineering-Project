import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react';
import {useForm} from 'react-hook-form';
import LoginInfo from '../../models/loginInfo.model';
import '../../styles/Login.css';
import LoginService from '../../services/login.service';
import {AxiosResponse} from 'axios';
import UserDetails from '../../models/userDetails.model';
import history from '../../misc/history';
import {MdPets} from "react-icons/md";
import AccountBox from '@material-ui/icons/AccountBox';
import LockIcon from '@material-ui/icons/Lock';

function Login() {
    const [error, setError] = useState("");

    const {register, handleSubmit} = useForm<LoginInfo>();


    const onSubmit = (data: LoginInfo) => {
        console.log(data);


        LoginService.login(data.username, data.password).then((response: AxiosResponse) => {
            if (response === null) {
                setError("Pogrešni podaci za prijavu!");
            } else if (response.status === 401) {
                setError("Pogrešni podaci za prijavu!");
            } else if ((response as AxiosResponse<UserDetails>).status === 200) {
                history.push("/");
                window.location.reload(false);
            }
        }).catch(error => {
            setError(error);
        });
    }

    return (
        !LoginService.isLoggedIn() ? (
                <div className="loginbox">
                    <MdPets className="loginlogo"/>
                    <h1 className="logintext">Prijavite se:</h1>
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <div className="centered">
                            {error}
                        </div>
                        <hr/>
                        <div className="login">
                            <AccountBox/>
                            <h3 className="logintext">Korisničko ime</h3>
                        </div>
                        <TextField id="username" inputRef={register} name="username" label="Unesite korisničko ime"
                                   variant="outlined" className="textfield"/>
                        <div className="login">
                            <LockIcon/>
                            <h3 className="logintext">Lozinka</h3>
                        </div>
                        <TextField id="password" type="password" inputRef={register} name="password" label="Unesite lozinku"
                                   variant="outlined" className="textfield"/>
                        <div className="horizontal-center">
                            <Button type="submit" variant="contained" color="primary">Prijava</Button>
                        </div>
                        <div className="horizontal-center">
                            <p className="logintext">Nemate korisnički račun? <a href="/registration" className="logintext">Registrirajte
                                se</a></p>
                        </div>
                    </form>
                </div>) :
            (<div>
                <br/>
                <div className="horizontal-center">
                    Već ste prijavljeni!
                </div>
            </div>)

    );
}

export default Login;