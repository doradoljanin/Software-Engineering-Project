function getHostName(url: string): string {
    return String(process.env.REACT_APP_API_HOST) + "api" + url;
}

export default getHostName;