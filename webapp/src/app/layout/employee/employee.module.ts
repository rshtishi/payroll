import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeComponent } from './employee/employee.component';
import { EmployeeRoutingModule } from './employee-routing.module';
import { GridModule } from '@progress/kendo-angular-grid';
import { EmployeeBindingDirective } from './directive/employee-binding.directive';
import { EmployeeViewComponent } from './employee-view/employee-view.component';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { EmployeeEditComponent } from './employee-edit/employee-edit.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [EmployeeComponent, EmployeeBindingDirective, EmployeeViewComponent, EmployeeAddComponent, EmployeeEditComponent],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    GridModule,
    ReactiveFormsModule

  ]
})
export class EmployeeModule { }
