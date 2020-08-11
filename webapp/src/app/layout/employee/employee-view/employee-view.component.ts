import { Component, OnInit, OnDestroy } from '@angular/core';
import { Location } from '@angular/common';
import { EmployeeService } from '../../../shared/service/employee.service';
import { Subject } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { Employee} from '../../../shared/model/employee.model';

@Component({
  selector: 'app-employee-view',
  templateUrl: './employee-view.component.html',
  styleUrls: ['./employee-view.component.css']
})
export class EmployeeViewComponent implements OnInit, OnDestroy {

  private _$alive = new Subject();
  public employee:Employee;

  constructor(private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _location: Location,
    private _employeeService: EmployeeService) { }

  ngOnInit(): void {
    this._activatedRoute.params.pipe(takeUntil(this._$alive)).subscribe(params => {
      let id = params['id'];
      this._employeeService.fetchById(id).subscribe(result => this.employee=result);
    });
  }

  public back() {
    this._location.back();
  }

  ngOnDestroy() {
    this._$alive.next();
    this._$alive.complete();
  }

}
