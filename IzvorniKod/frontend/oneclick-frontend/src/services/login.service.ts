import { AxiosResponse } from 'axios';
import UserDetails from '../models/userDetails.model';
import api from './api.service'
import getHostName from "./host.service";
import history from './../misc/history';

function getLoginRequest(username: string, password: string) {
    return api.post(getHostName("/auth/login"), {
        username: username,
        password: password
    });
}   

async function login(username: string, password: string) {
    let result = {};
    let error = null;

    await getLoginRequest(username, password).then(response => {
        localStorage.setItem("user", JSON.stringify(response.data));
        result = response;
    }, (errors) => {
        error = errors.response;
    });

    if (error !== null) {
        return error as AxiosResponse;
    }

    return result as AxiosResponse<UserDetails>;
}

function logout() {
    let value = localStorage.getItem("user");

    if (value) {
        localStorage.removeItem("user");
        history.push("/");
        window.location.reload(false);
        return true;
    } else {
        return false;
    }
}

function isLoggedIn() {
    let value = localStorage.getItem("user");
    
    return !!value;
}

const LoginService = {login: login, logout, isLoggedIn};
export default LoginService;