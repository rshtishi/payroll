import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartmentComponent } from './department/department.component';
import { DepartmentRoutingModule } from './department-routing.module';
import { GridModule } from '@progress/kendo-angular-grid';
import { DepartmentBindingDirective } from './directive/department-binding.directive';
import { DepartmentViewComponent } from './department-view/department-view.component';



@NgModule({
  declarations: [DepartmentComponent, DepartmentBindingDirective, DepartmentViewComponent],
  imports: [
    CommonModule,
    DepartmentRoutingModule,
    GridModule
  ]
})
export class DepartmentModule { }
