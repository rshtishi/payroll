import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../shared/service/department.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public chartTitle: any = { text: 'Payroll Chart' };
  public chartData: any[] = [{
    department: "Human Resource",
    employee_no: 200
  }, {
    department: "Development",
    employee_no: 250
  }];

  constructor(private departmentService:DepartmentService) { }

  ngOnInit(): void {
    this.departmentService.fetchAll().subscribe(result=>{
      console.log(result);
    });
  }

}
