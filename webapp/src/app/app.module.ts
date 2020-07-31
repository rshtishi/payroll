import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginModule } from './login/login.module';
import { AccessDeniedModule } from './access-denied/access-denied.module';
import { NotFoundModule } from './not-found/not-found.module';
import { LayoutModule } from './layout/layout.module';
import { AuthService } from './shared/service/auth.service';
import { HttpClientModule } from '@angular/common/http';

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
    HttpClientModule
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
