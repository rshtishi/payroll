import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../../shared/service/employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  constructor(private _router: Router,
    private _employeeService: EmployeeService) { }

  ngOnInit(): void {
    this._employeeService.fetchAll().subscribe(result => console.log(result));
  }

  public goToNewEmployeView() {
    this._router.navigate(['employee','new']);
  }

}
