import { Directive, OnInit, OnDestroy } from "@angular/core";
import { DataBindingDirective, GridComponent, GridDataResult } from "@progress/kendo-angular-grid";
import { DepartmentService } from "../../../shared/service/department.service";
import { takeUntil } from 'rxjs/operators';
import { Subject, Observable } from "rxjs";
import { State } from "@progress/kendo-data-query";

@Directive({
    selector: '[departmentBinding]'
})
export class DepartmentBindingDirective extends DataBindingDirective implements OnInit, OnDestroy {

    private _$alives = new Subject();

    constructor(grid: GridComponent,
        private departmentService: DepartmentService) {
        super(grid);
    }

    public ngOnInit(): void {
        this.departmentService.pipe(takeUntil(this._$alives)).subscribe(
            (result) => {
                this.grid.loading = false;
                this.grid.data = result;
                this.notifyDataChange();
            }
        );
        super.ngOnInit();
        this.rebind();
    }

    public ngOnDestroy(): void {
        this._$alives.next();
        this._$alives.complete();
        super.ngOnDestroy();
    }

    public rebind(): void {
        this.grid.loading = true;
        this.departmentService.query(this.state);
    }

}


