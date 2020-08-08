import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../shared/service/department.service';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  constructor(private departmentService:DepartmentService) { }

  ngOnInit(): void {
    this.departmentService.fetchAll().subscribe(result => {
      console.log(result);
    });
  }


}
