import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Globals} from "./global";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Basic dGVzdDp0ZXN0'
  })
};

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private global: Globals) {
  }

  getUsers() {
    return this.http.get(this.global.serviceUrl + '/users', httpOptions);
  }

  getUser(id) {
    return this.http.get(this.global.serviceUrl + '/users/' + id, httpOptions);
  }

  createUser(user) {
    let body = JSON.stringify(user);
    return this.http.post(this.global.serviceUrl + '/users', body, httpOptions);
  }

  updateUser(id, user) {
    let body = JSON.stringify(user);
    return this.http.put(this.global.serviceUrl + '/users/' + id, body, httpOptions);
  }

  deleteUser(id) {
    return this.http.delete(this.global.serviceUrl + '/users/' + id, httpOptions);
  }

}
