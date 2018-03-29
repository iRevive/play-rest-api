import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Globals} from "./global";

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic dGVzdDp0ZXN0'
    })
};

@Injectable()
export class BlogService {

    constructor(private http: HttpClient, private global: Globals) {}

    getBlogs() {
        return this.http.get( this.global.serviceUrl + '/blog', httpOptions);
    }

    getBlog(id) {
        return this.http.get(this.global.serviceUrl + '/blog/' + id, httpOptions);
    }

    createBlog(blog) {
        let body = JSON.stringify(blog);
        return this.http.post(this.global.serviceUrl + '/blog', body, httpOptions);
    }

    updateBlog(id, blog) {
        let body = JSON.stringify(blog);
        return this.http.put(this.global.serviceUrl + '/blog/' + id, body, httpOptions);
    }

    deleteBlog(id) {
        return this.http.delete(this.global.serviceUrl + '/blog/' + id, httpOptions);
    }
}
