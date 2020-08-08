import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { AppSettings } from "../../app.settings";
import { BehaviorSubject, Observable } from "rxjs";
import { GridDataResult } from "@progress/kendo-angular-grid";
import { State } from "@progress/kendo-data-query";
import { map, tap } from "rxjs/operators";
import { Department } from "../model/department.model";

@Injectable()
export class DepartmentService extends BehaviorSubject<GridDataResult> {

    public _loading: boolean;
    private _lastSearchResults = 0;
    private _lastGridState: State;
    _currentNavigationState: any;

    constructor(private httpClient: HttpClient) {
        super(null);
    }

    public fetchAll() {
        return this.httpClient.get(AppSettings.DEPARTMENT_ENDPOINT);
    }

    public fetchById(id:string) {
        let url:string=`${AppSettings.DEPARTMENT_ENDPOINT}/${id}`;
        return this.httpClient.get(url);
    }

    public save(department:Department){
        return this.httpClient.post(AppSettings.DEPARTMENT_ENDPOINT,department);
    }

    public update(department:Department){
        let url = `${AppSettings.DEPARTMENT_ENDPOINT}/${department.id}`;
        return this.httpClient.put(url,department);
    }

    public delete(id:string){
        let url = `${AppSettings.DEPARTMENT_ENDPOINT}/${id}`;
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
            AppSettings.DEPARTMENT_ENDPOINT
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