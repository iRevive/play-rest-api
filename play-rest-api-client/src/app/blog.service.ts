import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic dGVzdDp0ZXN0'
    })
};

@Injectable()
export class BlogService {

    constructor(private http: HttpClient) {}

    getBlogs() {
        return this.http.get('http://145.239.78.41:9000/blog', httpOptions);
    }

    getBlog(id) {
        return this.http.get('http://145.239.78.41:9000/blog/' + id, httpOptions);
    }

    createBlog(blog) {
        let body = JSON.stringify(blog);
        return this.http.post('http://145.239.78.41:9000/blog', body, httpOptions);
    }

    updateBlog(id, blog) {
        let body = JSON.stringify(blog);
        return this.http.put('http://145.239.78.41:9000/blog/' + id, body, httpOptions);
    }

    deleteBlog(id) {
        return this.http.delete('http://145.239.78.41:9000/blog/' + id, httpOptions);
    }
}