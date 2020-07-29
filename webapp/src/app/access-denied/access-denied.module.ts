import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessDeniedRoutingModule } from './access-denied-routing.module';
import { AccessDeniedComponent } from './access-denied/access-denied.component';



@NgModule({
  declarations: [AccessDeniedComponent],
  imports: [
    CommonModule,
    AccessDeniedRoutingModule
  ]
})
export class AccessDeniedModule { }
