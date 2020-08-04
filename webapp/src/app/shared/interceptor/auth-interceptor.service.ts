import { Injectable } from "@angular/core";
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Observable } from "rxjs";
import { AppSettings } from "../../app.settings";

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(this.addAuthorizationHeader(req));
  }

  private addAuthorizationHeader(req: HttpRequest<any>) {
    if (req.url.startsWith(AppSettings.EMPLOYEE_ENDPOINT) ||
        req.url.startsWith(AppSettings.DEPARTMENT_ENDPOINT) ||
        req.url.startsWith(AppSettings.USER_ENDPOINT)) {
      let token = localStorage.getItem(AppSettings.ACCESS_TOKEN);
      if (token) {
        console.log('Append token "' + token + '" to request header...');
        return req.clone({ setHeaders: { [AppSettings.AUTHORIZATION_HEADER_NAME]: 'Bearer ' + token } });
      }
    }
    return req;
  }
}