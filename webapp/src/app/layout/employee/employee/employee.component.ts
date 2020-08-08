import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../../shared/service/employee.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  constructor(private employeeService:EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.fetchAll().subscribe(result => console.log(result));
  }

}
