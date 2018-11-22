import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeleteDialogComponent} from "./delete-dialog/delete-dialog.component";
import {MaterialModule} from "../material.module";

@NgModule({
  imports: [
    CommonModule,
    MaterialModule
  ],
  declarations: [
    DeleteDialogComponent
  ],
  entryComponents: [
    DeleteDialogComponent
  ]
})
export class SharedModule {
}
