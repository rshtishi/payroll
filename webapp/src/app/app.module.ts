import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginModule } from './login/login.module';
import { AccessDeniedModule } from './access-denied/access-denied.module';
import { NotFoundModule } from './not-found/not-found.module';
import { LayoutModule } from './layout/layout.module';
import { AuthService } from './shared/service/auth.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthGuard } from './shared/guards/auth.guard';
import { ChartsModule } from '@progress/kendo-angular-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthorizationInterceptor } from './shared/interceptor/auth-interceptor.service';
import { DepartmentService } from './shared/service/department.service';
import { EmployeeService } from './shared/service/employee.service';
import { GridModule } from '@progress/kendo-angular-grid';





@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    AccessDeniedModule,
    NotFoundModule,
    LayoutModule,
    HttpClientModule,
    ChartsModule,
    BrowserAnimationsModule,
    GridModule
  ],
  providers: [AuthService, AuthGuard, DepartmentService, EmployeeService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
