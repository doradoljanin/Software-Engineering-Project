import Axios, { AxiosRequestConfig } from "axios";
import identityService from "../services/identity.service";

const instance = Axios.create();

instance.interceptors.request.use((config: AxiosRequestConfig) => {
    const identityContext = identityService.getIdentityContext();
    if (identityContext) {
        return {
            ...config,
            headers: {
                'Authorization': 'Bearer ' + identityContext.token,
                'Access-Control-Allow-Origin': '*',
            },
            mode: 'cors',
        }
    }
    return {
        ...config,
        headers: {
            'Access-Control-Allow-Origin': '*',
        },
        mode: 'cors',
    };
}, errors => {
    Promise.reject(errors);
});

export default instance;