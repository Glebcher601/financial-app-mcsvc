import {NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from "./login/login.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CoreRoutingModule} from "./core-routing.module";
import {HeaderComponent} from './header/header.component';
import {RouterModule} from "@angular/router";
import {MaterialModule} from "../material.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CoreRoutingModule,
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
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import only in AppModule');
    }
  }
}
