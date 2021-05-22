import { Component, OnInit } from '@angular/core';
import { AuthService, UserService } from '../service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  public isMenuCollapsed = true;

  constructor(private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
  }

  onLogout() {
    this.authService.logout();
  }

  isAdmin() {
    let authority = this.userService.getRole();
    return authority === 'ROLE_ADMIN';
  }

}
