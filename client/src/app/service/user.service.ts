import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { environment } from './../../environments/environment';

const STORAGE_KEY = 'currUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser;

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient) {
    this.currentUser = this.storage.get(STORAGE_KEY);
  }

  setupUser(user) {
    this.currentUser = user;
    this.storage.set(STORAGE_KEY, this.currentUser);
  }

  initUser() {
    const promise = this.http.get(`${environment.api_url}/auth/refresh`)
      .pipe(map((res:any) => {
         if (res['token'] !== null) {
           return this.getMyInfo().toPromise()
             .then(user => {
               this.setupUser(user);
             });
         }
       }));
    return promise;
  }

  getMyInfo() {
    return this.http.get(`${environment.api_url}/auth/whoami`)
      .pipe(map(user => {
        this.setupUser(user);
        return user;
      }));
  }

  getRole() {
    var role = null;
    if(this.currentUser) {
      role = this.currentUser['authorities'][0]['authority'];
    }

    return role;
  }
}
