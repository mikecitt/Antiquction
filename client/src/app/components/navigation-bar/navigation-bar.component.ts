import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AutobidSettingsComponent } from '../autobid-settings/autobid-settings.component';
import { AuthService, UserService } from '../../service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  public isMenuCollapsed = true;

  constructor(
    private authService: AuthService, 
    private userService: UserService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
  }

  onLogout() {
    this.authService.logout();
  }

  onAutobidSettings() {
    const modalRef = this.modalService.open(AutobidSettingsComponent);

    modalRef.result.then((data) => {
    }, (reason) => {
      
    });
  }

  getUsername() {
    return this.userService.currentUser.username;
  }

  isAdmin() {
    let authority = this.userService.getRole();
    return authority === 'ROLE_ADMIN';
  }

}
