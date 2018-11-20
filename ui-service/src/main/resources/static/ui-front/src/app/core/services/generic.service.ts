import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';


export class GenericService<T extends any> {

  constructor(protected httpClient: HttpClient,
              //protected authenticationService: AuthenticationService,
              private serviceUrl: string) {
  }

  /*protected headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authenticationService.getToken()
  });*/

  getAll(): Observable<T[]> {
    return this.httpClient.get<T[]>(this.serviceUrl/*, {headers: this.headers}*/);
  }

  get(id: number): Observable<T> {
    return this.httpClient.get<T>(this.serviceUrl + '/' + id/*, {headers: this.headers}*/);
  }

  save(entity: T): Observable<T> {
    if (entity.id !== null) {
      return this.httpClient.put<T>(this.serviceUrl + '/' + entity.id, entity/*, {headers: this.headers}*/);
    } else {
      return this.httpClient.post<T>(this.serviceUrl, entity/*, {headers: this.headers}*/);
    }
  }

  delete(id: number): Observable<number> {
    return this.httpClient.delete<number>(this.serviceUrl + '/' + id/*, {headers: this.headers}*/);
  }
}
