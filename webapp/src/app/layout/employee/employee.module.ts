import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeComponent } from './employee/employee.component';
import { EmployeeRoutingModule } from './employee-routing.module';
import { GridModule } from '@progress/kendo-angular-grid';
import { EmployeeBindingDirective } from './directive/employee-binding.directive';



@NgModule({
  declarations: [EmployeeComponent, EmployeeBindingDirective],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    GridModule
  ]
})
export class EmployeeModule { }
