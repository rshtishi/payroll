import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { LoggingUser } from '../../shared/model/logging-user';
import { AuthService } from '../../shared/service/auth.service';
import { AppSettings } from '../../app.settings';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loggingUser = new LoggingUser();
  appName = "payroll";
  errorMessage = '';

  constructor(    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService:AuthService) { }

  ngOnInit(): void {
  }

  login() {
    this.authService.authenticate(this.loggingUser.username,this.loggingUser.password).subscribe(response =>{
      localStorage.setItem(AppSettings.ACCESS_TOKEN,response[AppSettings.ACCESS_TOKEN]);
      this.authService.retrieveUser().subscribe(user =>{
        localStorage.setItem(AppSettings.CURRENT_USER,JSON.stringify(user));
        console.log(this.authService.isAuthenticated());
        if(user){
          this.router.navigate(["/"]);
        }
      });
    });
  }

}
