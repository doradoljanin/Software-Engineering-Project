import getHostName from "./host.service";
import api from "./api.service";

function getAssociationImageUrl(id: number | string) {
    return getHostName(`/image/association/${id}`);
}

function getAnimalImageUrl(id: number | string) {
    return getHostName(`/image/animal/${id}`);
}

function getUserImageUrl() {
    return getHostName("/image/user");
}

function getDefaultImage() {
    return "/puppy.png"
}

function getUserImageAndSetIt(setterFun: Function) {
    api.get(getUserImageUrl(), {responseType: 'arraybuffer'})
        .then(res => {
            setterFun(Buffer.from(res.data, "binary").toString("base64"));
        });
}

const ImageService = {getAssociationImageUrl, getAnimalImageUrl, getUserImageUrl, getDefaultImage, getUserImageAndSetIt};

export default ImageService;