import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../shared/service/department.service';
import 'hammerjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public chartTitle: any = { text: 'Payroll Chart' };
  public chartData: any[] = [];

  constructor(private departmentService:DepartmentService) { }

  ngOnInit(): void {
    this.departmentService.fetchAll().subscribe(result=>{
      result['content'].forEach(element => {
        this.chartData.push({department:element['name'], employee_no:element['noOfEmployees']})
      });
    });
  }



}
