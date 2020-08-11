import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { Department } from '../../../shared/model/department.model';
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../../../shared/service/employee.service';
import { DepartmentService } from '../../../shared/service/department.service';
import { Location } from '@angular/common';
import { takeUntil } from 'rxjs/operators';
import { Employee } from '../../../shared/model/employee.model';

@Component({
  selector: 'app-employee-edit',
  templateUrl: './employee-edit.component.html',
  styleUrls: ['./employee-edit.component.css']
})
export class EmployeeEditComponent implements OnInit {

  public editEmployeeForm: FormGroup;
  submitted = false;
  private _$alive = new Subject();
  public departments: Department[];

  constructor(private _formBuilder: FormBuilder,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _employeeService: EmployeeService,
    private _departmentService: DepartmentService,
    private _location: Location) { }

  ngOnInit(): void {
    this.createForm();
    this._departmentService.fetchAll().subscribe(result => {
      this.departments = result["content"];
    });
    this._activatedRoute.params.pipe(takeUntil(this._$alive)).subscribe(params => {
      let id = params['id'];
      this._employeeService.fetchById(id).subscribe(result => {
        this.editEmployeeForm.get('id').setValue(result['id']);
        this.editEmployeeForm.get('firstname').setValue(result['firstname']);
        this.editEmployeeForm.get('lastname').setValue(result['lastname']);
        this.editEmployeeForm.get('address').setValue(result['address']);
        this.editEmployeeForm.get('phone').setValue(result['phone']);
        this.editEmployeeForm.get('departmentId').setValue(result['departmentId']);

      });
    });
  }

  private createForm() {
    this.editEmployeeForm = this._formBuilder.group({
      id: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      address: ['', Validators.required],
      phone: ['', Validators.required],
      departmentId: ['', Validators.required]

    });
  }

  get inputFieldValue() {
    return this.editEmployeeForm.controls;
  }

  public submit() {
    this.submitted = true;
    if (this.editEmployeeForm.invalid) {
      return;
    }
    this._employeeService.update(this.editEmployeeForm.value).subscribe(result => {
      this._router.navigate(['employee']);
    });
  }

  cancel() {
    this.editEmployeeForm.get('firstname').reset();
    this.editEmployeeForm.get('lastname').reset();
    this.editEmployeeForm.get('address').reset();
    this.editEmployeeForm.get('phone').reset();
    this.editEmployeeForm.get('departmentId').reset();
  }

  back() {
    this._location.back();
  }

}
