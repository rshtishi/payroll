import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { DepartmentComponent } from './department/department.component';
import { DepartmentViewComponent } from './department-view/department-view.component';
import { DepartmentAddComponent } from './department-add/department-add.component';
import { DepartmentEditComponent } from './department-edit/department-edit.component';

const routes:Routes=[
  {
    path:'',
    component:DepartmentComponent
  },
  {
    path:'new',
    component:DepartmentAddComponent
  },
  {
    path:':id/view',
    component:DepartmentViewComponent
  },
  {
    path:':id/edit',
    component:DepartmentEditComponent
  }
]
@NgModule({
 
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DepartmentRoutingModule { }