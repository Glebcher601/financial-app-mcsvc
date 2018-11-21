import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "./user-list/user-list.component";
import {NgModule} from "@angular/core";
import {UserFormComponent} from "./user-form/user-form.component";

const routes: Routes = [
  {
    path: '',
    component: UserListComponent
  },
  {
    path: 'userDetails/:id',
    component: UserFormComponent
  },
  {
    path: "addPerson",
    component: UserFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
