import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserFormComponent} from './user-form/user-form.component';
import {UserListComponent} from './user-list/user-list.component';
import {AdminRoutingModule} from "./admin-routing.module";
import {MaterialModule} from "../material.module";

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    AdminRoutingModule
  ],
  declarations: [
    UserFormComponent,
    UserListComponent
  ]
})
export class AdminModule {
}
