import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { AppSettings } from "../../app.settings";

@Injectable()
export class EmployeeService {

    constructor(private httpClient:HttpClient){
    }

    public fetchAll(){
        return this.httpClient.get(AppSettings.EMPLOYEE_ENDPOINT);
    }

}