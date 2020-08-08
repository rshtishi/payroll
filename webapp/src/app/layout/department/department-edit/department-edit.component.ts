import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { DepartmentService } from '../../../shared/service/department.service';
import { Location } from '@angular/common';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Department } from '../../../shared/model/department.model';

@Component({
  selector: 'app-department-edit',
  templateUrl: './department-edit.component.html',
  styleUrls: ['./department-edit.component.css']
})
export class DepartmentEditComponent implements OnInit {

  public editDepartmentForm: FormGroup;
  submitted = false;
  private _$alive = new Subject();
  

  constructor(private _formBuilder: FormBuilder,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _departmentService: DepartmentService,
    private _location: Location) {
  }

  ngOnInit(): void {
    this.createForm();
    this._activatedRoute.params.pipe(takeUntil(this._$alive)).subscribe(params =>{
      let id = params['id'];
      this._departmentService.fetchById(id).subscribe(result =>{
        this.editDepartmentForm.get('id').setValue(id);
        this.editDepartmentForm.get('name').setValue(result['name']);
      });
    });
      
  }

  private createForm() {
    this.editDepartmentForm = this._formBuilder.group({
      id: [''],
      name: ['', Validators.required]
    });
  }

  get inputFieldValue() {
    return this.editDepartmentForm.controls;
  }

  public submit() {
    this.submitted = true;
    if (this.editDepartmentForm.invalid) {
      return;
    }
    this._departmentService.update(this.editDepartmentForm.value).subscribe(result =>{
      this._router.navigate(['department']);
    });
  }

  cancel() {
    this.editDepartmentForm.get('name').reset();
  }

  back() {
    this._location.back();
  }

  public ngOnDestroy(): void {
    this._$alive.next();
    this._$alive.complete();
  }

}
