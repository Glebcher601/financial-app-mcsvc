import {FormControl} from "@angular/forms";

export function PasswordValidator(ctrl: FormControl) {
  return /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/.test(ctrl.value) ? null : {
    PasswordValidator: {
      valid: false
    }
  }
}