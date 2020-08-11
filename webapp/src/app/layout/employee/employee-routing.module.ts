import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeComponent } from './employee/employee.component';
import { EmployeeViewComponent } from './employee-view/employee-view.component';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { EmployeeEditComponent } from './employee-edit/employee-edit.component';

const routes:Routes=[
  {
    path:'',
    component:EmployeeComponent
  },
  {
    path:'new',
    component:EmployeeAddComponent
  },
  {
    path:':id/view',
    component:EmployeeViewComponent
  },
  {
    path:':id/edit',
    component:EmployeeEditComponent
  }
]
@NgModule({
 
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }