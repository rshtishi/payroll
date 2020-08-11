import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../shared/service/department.service';
import 'hammerjs';
import { map } from 'rxjs/operators';
import { Department } from '../../../shared/model/department.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public chartTitle: any = { text: 'Payroll Chart' };
  public departments: Department[];

  constructor(private departmentService: DepartmentService) { }

  ngOnInit(): void {
    this.departmentService.fetchAll().subscribe(result => {
      this.departments=result['content'];
    });
  }



}
