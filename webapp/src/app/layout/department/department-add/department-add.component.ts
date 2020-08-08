import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Department } from '../../../shared/model/department.model';
import { DepartmentService } from '../../../shared/service/department.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-department-add',
  templateUrl: './department-add.component.html',
  styleUrls: ['./department-add.component.css']
})
export class DepartmentAddComponent implements OnInit {

  public addDepartmentForm: FormGroup;
  submitted = false;
  department: Department;

  constructor(private _formBuilder: FormBuilder,
    private _router: Router,
    private _departmentService: DepartmentService,
    private _location: Location) {
  }

  ngOnInit(): void {
    this.createForm();
  }

  private createForm() {
    this.addDepartmentForm = this._formBuilder.group({
      name: ['', Validators.required]
    });
  }

  get inputFieldValue() {
    return this.addDepartmentForm.controls;
  }

  public submit() {
    this.submitted = true;
    if (this.addDepartmentForm.invalid) {
      return;
    }
    this._departmentService.save(this.addDepartmentForm.value).subscribe(result => {
      this._router.navigate(['department']);
    });
  }

  cancel() {
    this.addDepartmentForm.reset();
  }

  back() {
    this._location.back();
  }


}
