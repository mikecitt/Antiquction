import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { AdminGuard, LoggedGuard, LoginGuard, RegularGuard } from './guard';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: '', component: HomePageComponent, canActivate: [LoggedGuard], 
  },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: '403', component: ForbiddenComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
