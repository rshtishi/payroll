import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { LayoutRoutingModule } from './layout-routing.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { DepartmentModule } from './department/department.module';
import { EmployeeModule } from './employee/employee.module';



@NgModule({
  declarations: [LayoutComponent],
  imports: [
    CommonModule,
    LayoutRoutingModule,
    DashboardModule,
    DepartmentModule,
    EmployeeModule
  ]
})
export class LayoutModule { }
