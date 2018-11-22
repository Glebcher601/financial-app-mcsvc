import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {PERMISSIONS, UserFormConstants} from "../../core/domain/constants";
import {PasswordValidator} from "../../core/validator/common-validators";
import {User} from "../../core/domain/user";
import {UsersMockTestService} from "../../core/services/users.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  userForm: FormGroup;
  userBehaviourSubject: BehaviorSubject<User> = new BehaviorSubject(null);
  userSubscription: Subscription;
  showFormBehaviorSubject: BehaviorSubject<boolean> = new BehaviorSubject(false);

  permissions: any = PERMISSIONS;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private activeRoute: ActivatedRoute,
              private usersService: UsersMockTestService) {
    this.createForm();
  }

  ngOnInit() {
    this.userSubscription = this.userBehaviourSubject.subscribe(user => {
      this.populateForm(user);
    });
  }

  save() {
    const formModel = this.userForm.value;
    const user = new User;

    user.id = formModel.userId;
    user.enabled = formModel.isEnabled;
    user.login = formModel.login;
    user.password = formModel.password;
    user.permissionGroup = formModel.permissionGroup;

    this.usersService.save(user).subscribe(user => this.hideForm());
  }

  populateForm(user: User) {
    if (!user) {
      return;
    }

    this.showFormBehaviorSubject.next(true);
    this.userForm.setValue({
      userId: user.id || '',
      login: user.login || '',
      password: user.password || '',
      isEnabled: user.enabled || true,
      permissionGroup: user.permissionGroup || PERMISSIONS[0].id
    });

  }

  hideForm() {
    this.showFormBehaviorSubject.next(false);
  }

  private createForm(): void {
    this.userForm = this.formBuilder.group({
      "userId": [''],
      "login": ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(12)
      ]],
      "password": ['', [
        Validators.required,
        PasswordValidator
      ]],
      "isEnabled": [
        Validators.required
      ],
      "permissionGroup": [
        Validators.required
      ]
    });
  }

  get personUserIdCtrl(): AbstractControl | null {
    return this.userForm.get(UserFormConstants.USER_ID_CTRL);
  }

  get personFirstNameCtrl(): AbstractControl | null {
    return this.userForm.get(UserFormConstants.LOGIN_NAME_CTRL);
  }

  get personLastNameCtrl(): AbstractControl | null {
    return this.userForm.get(UserFormConstants.PASSWORD_NAME_CTRL);
  }

  get personIsEnabledCtrl(): AbstractControl | null {
    return this.userForm.get(UserFormConstants.IS_ENABLED_CTRL);
  }

  get permissionGroupCtrl(): AbstractControl | null {
    return this.userForm.get(UserFormConstants.PERM_GROUP);
  }
}
