import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../../shared/service/department.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  constructor(private _router: Router,
    private departmentService: DepartmentService) { }

  ngOnInit(): void {
  }

  newBtnClick() {
    this._router.navigate(['department','new']);
  }


}
