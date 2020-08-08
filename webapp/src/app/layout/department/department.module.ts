import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartmentComponent } from './department/department.component';
import { DepartmentRoutingModule } from './department-routing.module';
import { GridModule } from '@progress/kendo-angular-grid';
import { DepartmentBindingDirective } from './directive/department-binding.directive';
import { DepartmentViewComponent } from './department-view/department-view.component';
import { DepartmentAddComponent } from './department-add/department-add.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DepartmentEditComponent } from './department-edit/department-edit.component';



@NgModule({
  declarations: [DepartmentComponent, DepartmentBindingDirective, DepartmentViewComponent, DepartmentAddComponent, DepartmentEditComponent],
  imports: [
    CommonModule,
    DepartmentRoutingModule,
    GridModule,
    ReactiveFormsModule
  ]
})
export class DepartmentModule { }
