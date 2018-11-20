import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserFormComponent} from './user-form/user-form.component';
import {UserListComponent} from './user-list/user-list.component';
import {AdminRoutingNodule} from "./admin.routing.nodule";

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingNodule
  ],
  declarations: [
    UserFormComponent,
    UserListComponent]
})
export class AdminModule {
}
