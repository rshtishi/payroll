import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { EmployeeService } from '../../../shared/service/employee.service';
import { Location } from '@angular/common';
import { DepartmentService } from '../../../shared/service/department.service';
import { Department } from '../../../shared/model/department.model';

@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent implements OnInit {

  public addEmployeeForm: FormGroup;
  submitted = false;
  private _$alive = new Subject();
  public departments: Department[];

  constructor(private _formBuilder: FormBuilder,
    private _router: Router,
    private _employeeService: EmployeeService,
    private _departmentService: DepartmentService,
    private _location: Location) { }

  ngOnInit(): void {
    this.createForm();
    this._departmentService.fetchAll().subscribe(result => {
      this.departments = result["content"];
    });
  }


  private createForm() {
    this.addEmployeeForm = this._formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      address: ['', Validators.required],
      phone: ['', Validators.required],
      departmentId: ['', Validators.required]

    });
  }

  get inputFieldValue() {
    return this.addEmployeeForm.controls;
  }

  public submit() {
    this.submitted = true;
    if (this.addEmployeeForm.invalid) {
      return;
    }
    this._employeeService.save(this.addEmployeeForm.value).subscribe(result => {
      this._router.navigate(['employee']);
    });
  }

  cancel() {
    this.addEmployeeForm.reset();
  }

  back() {
    this._location.back();
  }

}
