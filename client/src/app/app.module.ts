import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { TokenInterceptor } from './interceptor/token.interceptor';
import { CookieService } from 'ngx-cookie-service';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { ItemListComponent } from './components/item-list/item-list.component';
import { ItemDetailsComponent } from './components/item-details/item-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { ItemEditComponent } from './components/item-edit/item-edit.component';
import { ItemAddComponent } from './components/item-add/item-add.component';
import { TimeLeftPipe } from './pipes/time-left.pipe';
import { PaginationBarComponent } from './components/pagination-bar/pagination-bar.component';
import { AutobidSettingsComponent } from './components/autobid-settings/autobid-settings.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { UserItemListComponent } from './components/user-item-list/user-item-list.component';
import { BillComponent } from './components/bill/bill.component';
import { RegistrationComponent } from './pages/registration/registration.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomePageComponent,
    NavigationBarComponent,
    ItemListComponent,
    ItemDetailsComponent,
    ItemEditComponent,
    ItemAddComponent,
    TimeLeftPipe,
    PaginationBarComponent,
    AutobidSettingsComponent,
    UserProfileComponent,
    UserItemListComponent,
    BillComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    StorageServiceModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    CookieService,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
