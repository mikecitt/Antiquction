import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoggedGuard, LoginGuard } from './guard';
import { UserGuard } from './guard/user.guard';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginComponent } from './pages/login/login.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';

const routes: Routes = [
  { path: '', component: HomePageComponent, canActivate: [LoggedGuard] },
  { path: 'profile', component: UserProfileComponent, canActivate: [UserGuard] },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
