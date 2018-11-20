import {NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from "./login/login.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CoreRoutingModule} from "./core-routing.module";
import {RouterModule} from "@angular/router";
import {MaterialModule} from "../material.module";
import {HeaderComponent} from "./header/header.component";
import {FilterService} from "./services/filter.service";
import {UsersService} from "./services/users.service";
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CoreRoutingModule,
    HttpClientModule,
    MaterialModule
  ],
  declarations: [
    LoginComponent,
    HeaderComponent
  ],
  exports: [
    RouterModule,
    HeaderComponent,

    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  providers: [
    FilterService,
    UsersService
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import only in AppModule');
    }
  }
}
