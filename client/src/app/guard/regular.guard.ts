import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../service';

@Injectable({
  providedIn: 'root'
})
export class RegularGuard implements CanActivate {

  constructor(private router: Router, private userService: UserService) { }
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.userService.currentUser) {
      let authorities = JSON.stringify(this.userService.currentUser.authorities);
      if (authorities.search('ROLE_REGULAR') !== -1) {
        return true;
      } else {
        this.router.navigate(['/403']);
        return false;
      }
    }
  }
  
}