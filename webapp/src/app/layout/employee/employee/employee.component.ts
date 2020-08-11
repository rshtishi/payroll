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
  }

  public goToNewEmployeView() {
    this._router.navigate(['employee','new']);
  }

  public delete(id:string){
    this._employeeService.delete(id).subscribe(result => {
      this._employeeService.query({});
    });
  }

}
