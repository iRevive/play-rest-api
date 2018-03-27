import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Basic dGVzdDp0ZXN0'
  })
};

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUsers() {
    return this.http.get('http://145.239.78.41:9000/users', httpOptions);
  }

  getUser(id) {
    return this.http.get('http://145.239.78.41:9000/users/' + id, httpOptions);
  }

  createUser(user) {
    let body = JSON.stringify(user);
    return this.http.post('http://145.239.78.41:9000/users', body, httpOptions);
  }

  updateUser(id, user) {
    let body = JSON.stringify(user);
    return this.http.put('http://145.239.78.41:9000/users/' + id, body, httpOptions);
  }

  deleteUser(id) {
    return this.http.delete('http://145.239.78.41:9000/users/' + id, httpOptions);
  }

}
