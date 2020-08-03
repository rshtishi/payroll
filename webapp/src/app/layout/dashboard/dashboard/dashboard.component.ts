import { Component, OnInit } from '@angular/core';

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

 

  constructor() { }

  ngOnInit(): void {
  }

}
