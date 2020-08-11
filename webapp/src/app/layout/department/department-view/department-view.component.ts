import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { DepartmentService } from '../../../shared/service/department.service';
import { takeUntil } from 'rxjs/operators';
import { Department} from '../../../shared/model/department.model';
import { Location } from '@angular/common';

@Component({
  selector: 'app-department-view',
  templateUrl: './department-view.component.html',
  styleUrls: ['./department-view.component.css']
})
export class DepartmentViewComponent implements OnInit {

  private _$alive = new Subject();
  public department:Department;

  constructor(
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _location:Location,
    private _departmentService: DepartmentService) { }

  ngOnInit(): void {
    this._activatedRoute.params.pipe(takeUntil(this._$alive)).subscribe(params => {
      let id = params['id'];
      this._departmentService.fetchById(id).pipe(takeUntil(this._$alive)).subscribe(result =>{
        this.department = result;
      });
    });
  }

  public back(){
    this._location.back();
  }

  public ngOnDestroy(): void {
    this._$alive.next();
    this._$alive.complete();
  }

}
