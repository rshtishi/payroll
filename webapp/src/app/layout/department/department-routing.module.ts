import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { DepartmentComponent } from './department/department.component';
import { DepartmentViewComponent } from './department-view/department-view.component';

const routes:Routes=[
  {
    path:'',
    component:DepartmentComponent
  },
  {
    path:':id/view',
    component:DepartmentViewComponent
  }
]
@NgModule({
 
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DepartmentRoutingModule { }