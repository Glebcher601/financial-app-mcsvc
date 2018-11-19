import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  moduleId: module.id,
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {
  error = '';
  loginForm: FormGroup;

  constructor(private router: Router,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.createForm();
  }

  private createForm(): void {
    this.loginForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  login() {
    const formModel = this.loginForm.value;
    this.router.navigate(['home']);
  }

}
