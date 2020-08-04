import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { AppSettings } from "../../app.settings";

@Injectable()
export class AuthService {

    constructor(
        private httpClient: HttpClient) {
    }

    public authenticate(username: string, password: string) {
        let requestBody = new FormData();
        requestBody.append("grant_type", "password");
        requestBody.append("scope", "webclient");
        requestBody.append("username", username);
        requestBody.append("password", password);
        let headers = new HttpHeaders();
        headers = headers.set("Authorization", "Basic " + btoa("payroll:test"));
        let httpOptions = {
            "Content-Type": "multipart/form-data",
            headers: headers
        };
        return this.httpClient.post(AppSettings.LOGIN_ENDPOINT, requestBody, httpOptions);
    }

    public retrieveUser() {
        return this.httpClient.get(AppSettings.USER_ENDPOINT);
    }

    public isAuthenticated():boolean {
        return (localStorage.getItem(AppSettings.ACCESS_TOKEN)
            && JSON.parse(localStorage.getItem(AppSettings.CURRENT_USER))) ? true:false;
    }

    public logOut(){
        localStorage.removeItem(AppSettings.ACCESS_TOKEN);
        localStorage.removeItem(AppSettings.CURRENT_USER);
    }
}