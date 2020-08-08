import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartmentComponent } from './department/department.component';
import { DepartmentRoutingModule } from './department-routing.module';
import { GridModule } from '@progress/kendo-angular-grid';
import { DepartmentBindingDirective } from './directive/department-binding.directive';



@NgModule({
  declarations: [DepartmentComponent, DepartmentBindingDirective],
  imports: [
    CommonModule,
    DepartmentRoutingModule,
    GridModule
  ]
})
export class DepartmentModule { }
