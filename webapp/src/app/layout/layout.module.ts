import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { LayoutRoutingModule } from './layout-routing.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { DepartmentModule } from './department/department.module';
import { EmployeeModule } from './employee/employee.module';
import { HeaderComponent } from './layout/template/header/header.component';
import { SidebarComponent } from './layout/template/sidebar/sidebar.component';



@NgModule({
  declarations: [LayoutComponent, HeaderComponent, SidebarComponent],
  imports: [
    CommonModule,
    LayoutRoutingModule,
    DashboardModule,
    DepartmentModule,
    EmployeeModule
  ]
})
export class LayoutModule { }
