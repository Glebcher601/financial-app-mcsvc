import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "./user-list/user-list.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
  {
    path: '',
    component: UserListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
