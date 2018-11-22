import {GenericService} from "./generic.service";
import {User} from "../domain/user";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/from";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {USERS_RESOURCE} from "../domain/constants";

@Injectable()
export class UsersMockTestService extends GenericService<User> {

  users: User[];

  constructor(httpClient: HttpClient) {
    super(httpClient, USERS_RESOURCE);
    const usr1: User = new User;

    usr1.id = 1;
    usr1.enabled = true;
    usr1.login = "login";
    usr1.password = "psswd";

    const usr2: User = new User;

    usr2.id = 2;
    usr2.enabled = true;
    usr2.login = "login1";
    usr2.password = "psswd1";

    const usr3: User = new User;

    usr3.id = 3;
    usr3.enabled = true;
    usr3.login = "login2";
    usr3.password = "psswd2";

    const usr4: User = new User;

    usr4.id = 4;
    usr4.enabled = true;
    usr4.login = "login3";
    usr4.password = "psswd3";

    this.users = [usr1, usr2, usr3, usr4];
  }

  getAll(): Observable<User[]> {
    return Observable.from([this.users]);
  }


  get(id: number): Observable<User> {
    return Observable.from(this.users.filter((value) => value.id === id));
  }

  save(entity: User): Observable<User> {
    this.users.push(entity);
    return Observable.from([entity]);
  }

  delete(id: number): Observable<number> {
    this.users = this.users.filter((value) => value.id !== id);
    return Observable.from([id]);
  }
}