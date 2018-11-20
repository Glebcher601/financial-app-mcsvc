import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

//import {AuthenticationService} from './authentication.service';

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(public router: Router) {
  }

  canActivate(): boolean {
    /*if (!this.auth.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }*/

    return true;
  }
}
