import {Component} from '@angular/core';
import {BlogService} from './blog.service';
import {Observable} from 'rxjs/Rx';
import {UserService} from "./user.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    public blogs;
    public users;

    constructor(private _blogService: BlogService, private _userService: UserService) {
    }

    ngOnInit() {
        this.getBlogs();
        this.getUsers();
    }

    renderTime(dateTime) {
        if (dateTime) {
            return new Date(dateTime);
        } else {
            return 'Empty';
        }
    }

    // Users block
    getUsers() {
        this._userService.getUsers().subscribe(
            data => this.users = data,
            err => console.error(err),
            () => console.log('done loading foods')
        );
    }

    createUser(firstName, lastName) {
        let user = {firstName: firstName, lastName: lastName};

        this._userService.createUser(user).subscribe(
            data => {
                this.getUsers();
                return true;
            },
            error => {
                console.error("Error saving user!");
                return Observable.throw(error);
            }
        );
    }

    deleteUser(id) {
        this._userService.deleteUser(id).subscribe(
            data => {
                this.getUsers();
                return true;
            },
            error => {
                console.error("Error deleting user!");
                return Observable.throw(error);
            }
        );
    }

    //Blogs block
    getBlogs() {
        this._blogService.getBlogs().subscribe(
            data => this.blogs = data,
            err => console.error(err),
            () => console.log('done loading foods')
        );
    }

    createBlog(userId, header, content) {
        let blog = {userId: userId, header: header, blogContent: content};

        this._blogService.createBlog(blog).subscribe(
            data => {
                this.getBlogs();
                this.getUsers();
                return true;
            },
            error => {
                console.error("Error saving user!");
                return Observable.throw(error);
            }
        );
    }

    deleteBlog(id) {
        this._blogService.deleteBlog(id).subscribe(
            data => {
                this.getBlogs();
                return true;
            },
            error => {
                console.error("Error deleting user!");
                return Observable.throw(error);
            }
        );
    }
}
