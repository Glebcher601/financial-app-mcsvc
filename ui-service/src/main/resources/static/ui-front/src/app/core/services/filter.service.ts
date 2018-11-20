import {Injectable} from "@angular/core";
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class FilterService {

  userFilterSubject = new BehaviorSubject<string>('');

  userFilterValue = this.userFilterSubject.asObservable();

  constructor() {
  }
}
