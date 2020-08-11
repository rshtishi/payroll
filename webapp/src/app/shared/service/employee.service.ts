import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { AppSettings } from "../../app.settings";
import { BehaviorSubject, Observable } from "rxjs";
import { GridDataResult } from "@progress/kendo-angular-grid";
import { State } from "@progress/kendo-data-query";
import { map, tap } from "rxjs/operators";
import { Employee } from "../model/employee.model";

@Injectable()
export class EmployeeService extends BehaviorSubject<GridDataResult> {

    public _loading: boolean;
    private _lastSearchResults = 0;
    private _lastGridState: State;
    _currentNavigationState: any;

    constructor(private httpClient: HttpClient) {
        super(null);
    }

    public fetchAll() {
        return this.httpClient.get(AppSettings.EMPLOYEE_ENDPOINT);
    }

    public fetchById(id: string) {
        let url: string = `${AppSettings.EMPLOYEE_ENDPOINT}/${id}`;
        return this.httpClient.get(url);
    }

    public save(employee: Employee) {
        return this.httpClient.post(AppSettings.EMPLOYEE_ENDPOINT,employee);
    }

    public update(employee: Employee) {
        let url: string = `${AppSettings.EMPLOYEE_ENDPOINT}/${employee.id}`;
        return this.httpClient.put(url,employee);
    }

    public delete(id:string){
        let url: string = `${AppSettings.EMPLOYEE_ENDPOINT}/${id}`;
        return this.httpClient.delete(url);
    }

    public query(state: any): void {
        this.fetch(state).subscribe(result => {
            super.next(result);
        });
    }

    protected fetch(state: State): Observable<GridDataResult> {
        this._lastGridState = { ...state };
        const url = `${
            AppSettings.EMPLOYEE_ENDPOINT
            }?page=${Math.floor(state.skip / state.take)}&size=${
            state.take
            }${this.getSort(state)}`;
        this._loading = true;
        return this.httpClient.get<any>(url).pipe(
            map(response => {
                this._lastSearchResults = response.totalElements;
                return <GridDataResult>{
                    data: response.content,
                    total: response.totalElements
                };
            }),
            tap(() => (this._loading = false))
        );
    }

    protected getSort(state: State): string {
        let ret = '';
        const sep = '&sort=';
        if (state.sort != null) {
            state.sort.forEach(e => {
                if (e.dir != null && e.dir !== undefined) {
                    ret += sep + e.field + ',' + e.dir;
                }
            });
            return ret;
        }
        return '';
    }



}