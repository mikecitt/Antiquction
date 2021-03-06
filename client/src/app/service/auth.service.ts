import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';

const COOKIE_NAME = 'token';

export interface RegistrationForm {
	username: string;
	password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private access_token;

  constructor(
    private userService:UserService,
    private http:HttpClient,
    private cookieService:CookieService,
    private router: Router,
    public toastr: ToastrService
  ) {
    this.access_token = cookieService.get(COOKIE_NAME);
    if(!this.access_token) {
      userService.setupUser(null);
    }
  }

  login(user: { username: any; password: any; }) {
		const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    const body = {
      'username' : user.username,
      'password' : user.password
    };

    return this.http.post(`${environment.api_url}/auth/login`, body)
      .pipe(map((res:any) => {
        this.toastr.success('Login successful!', 'Success');
        this.access_token = res['token'];
        const dateNow = new Date();
        dateNow.setMinutes(dateNow.getMinutes() + (res['expiresIn'] / 60000));
        this.cookieService.set(COOKIE_NAME, this.access_token, dateNow);
        this.userService.getMyInfo().subscribe(() => {
          this.router.navigate(['/']);
        });
      }));
	}

  register(registration: RegistrationForm) {
		return this.http.post<any>(`${environment.api_url}/auth/register`, registration)
	}

  tokenIsPresent() {
    return this.access_token != undefined && this.access_token != null;
  }

  getToken() {
    return this.access_token;
  }

  logout() {
	  this.access_token = null;
	  this.cookieService.delete(COOKIE_NAME);
	  this.userService.setupUser(null);
	  this.router.navigate(['/login']);
	}
}
