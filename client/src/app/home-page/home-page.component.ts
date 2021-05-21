import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  authority;

  constructor(private service: AuthService) { }

  ngOnInit(): void {
  }

  onLogout() {
    this.authority = null;
    this.service.logout();
  }

}
