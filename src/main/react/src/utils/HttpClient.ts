import axios, {AxiosResponse, AxiosResponseHeaders, RawAxiosResponseHeaders} from "axios";

// GLOBAL AXIOS CONFIGURATION ==========================================================================================
axios.defaults.baseURL = import.meta.env.VITE_BACKEND_SERVER_URL;

type HttpClientHeaderValue = string | string[] | number | boolean | null;

type HttpClientRawHeaders = {
    [key: string]: HttpClientHeaderValue;
}

export class HttpClientHeaders {

    private readonly headers: HttpClientRawHeaders;

    constructor(axiosHeaders: RawAxiosResponseHeaders | AxiosResponseHeaders) {
        this.headers = JSON.parse(JSON.stringify(axiosHeaders))
    }

    get(key: string): HttpClientHeaderValue | undefined {
        const foundField = Object.keys(this.headers)
            .find(value => {
                return value.toLowerCase() === key.toLowerCase();
            });
        return foundField ? this.headers[foundField] : undefined;
    }

    has(key: string): boolean {
        return Object.keys(this.headers)
            .filter(value => {
                return value.toLowerCase() === key.toLowerCase();
            })
            .length > 0;
    }
}

export interface HttpClientResponse<T = any> {
    data: T;
    status: number;
    statusText: string;
    headers: HttpClientHeaders;
}

const mapToHttpClientResponse = <T>() => {
    return (response: AxiosResponse) => {
        return Promise.resolve<HttpClientResponse<T>>({
            data: response.data,
            status: response.status,
            statusText: response.statusText,
            headers: new HttpClientHeaders(response.headers)
        })
    };
}

export const httpGet = <T = any>(path: string): Promise<HttpClientResponse<T>> => {
    return axios.get(path)
        .then(mapToHttpClientResponse<T>());
};