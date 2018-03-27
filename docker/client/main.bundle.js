webpackJsonp(["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app.component.css":
/***/ (function(module, exports) {

module.exports = "table, th, td {\n  border: 1px solid black;\n  border-collapse: collapse;\n}\nth, td {\n  padding: 5px;\n  text-align: left;\n}\n"

/***/ }),

/***/ "./src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<div style=\"text-align:center\">\n    <h1>\n        Welcome to Play Framework + Angular 4 Example!\n    </h1>\n</div>\n\n<div style=\"text-align: center\">\n    <h2>Users: </h2>\n</div>\n<table style=\"width:100%\">\n    <tr>\n        <th>Id</th>\n        <th>First name</th>\n        <th>Last name</th>\n        <th>Last blog DT</th>\n        <th>Delete</th>\n    </tr>\n    <tr *ngFor=\"let user of users.result.values\">\n        <td>{{user.id}}</td>\n        <td>{{user.firstName}}</td>\n        <td>{{(user.lastName || 'Empty')}}</td>\n        <td>{{renderTime(user.lastBlogDt)}}</td>\n        <td>\n            <button (click)=\"deleteUser(user.id)\">Delete</button>\n        </td>\n    </tr>\n</table>\n\n<h2>Add user: </h2>\n<div>\n    <label>First name: </label><br>\n    <input type=\"text\" name=\"firstName\" [(ngModel)]=\"firstName\"><br>\n\n    <label>Last name: </label><br>\n    <input type=\"text\" name=\"lastName\" [(ngModel)]=\"lastName\"><br>\n\n    <button (click)=\"createUser(firstName, lastName)\">Save</button>\n</div>\n\n<div style=\"text-align: center\">\n    <h2>Blogs: </h2>\n</div>\n<table style=\"width:100%\">\n    <tr>\n        <th>Id</th>\n        <th>User Id</th>\n        <th>Header</th>\n        <th>Details</th>\n        <th>Delete</th>\n    </tr>\n    <tr *ngFor=\"let blog of blogs.result.values\">\n        <td>{{blog.id}}</td>\n        <td>{{blog.userId}}</td>\n        <td>{{blog.header}}</td>\n        <td>\n            <ul *ngFor=\"let detail of blog.details\">{{detail.blogContent}}</ul>\n        </td>\n        <td>\n            <button (click)=\"deleteBlog(blog.id)\">Delete</button>\n        </td>\n    </tr>\n</table>\n\n<h2>Add blog: </h2>\n<div>\n    <label>User id: </label><br>\n    <input type=\"number\" name=\"userId\" [(ngModel)]=\"userId\"><br>\n\n    <label>Header: </label><br>\n    <input type=\"text\" name=\"header\" [(ngModel)]=\"header\"><br>\n\n    <label>Content: </label><br>\n    <input type=\"text\" name=\"content\" [(ngModel)]=\"content\"><br>\n\n    <button (click)=\"createBlog(userId, header, content)\">Save</button>\n</div>"

/***/ }),

/***/ "./src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__blog_service__ = __webpack_require__("./src/app/blog.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__ = __webpack_require__("./node_modules/rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__user_service__ = __webpack_require__("./src/app/user.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var AppComponent = /** @class */ (function () {
    function AppComponent(_blogService, _userService) {
        this._blogService = _blogService;
        this._userService = _userService;
    }
    AppComponent.prototype.ngOnInit = function () {
        this.getBlogs();
        this.getUsers();
    };
    AppComponent.prototype.renderTime = function (dateTime) {
        if (dateTime) {
            return new Date(dateTime);
        }
        else {
            return 'Empty';
        }
    };
    // Users block
    AppComponent.prototype.getUsers = function () {
        var _this = this;
        this._userService.getUsers().subscribe(function (data) { return _this.users = data; }, function (err) { return console.error(err); }, function () { return console.log('done loading foods'); });
    };
    AppComponent.prototype.createUser = function (firstName, lastName) {
        var _this = this;
        var user = { firstName: firstName, lastName: lastName };
        this._userService.createUser(user).subscribe(function (data) {
            _this.getUsers();
            return true;
        }, function (error) {
            console.error("Error saving user!");
            return __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__["a" /* Observable */].throw(error);
        });
    };
    AppComponent.prototype.deleteUser = function (id) {
        var _this = this;
        this._userService.deleteUser(id).subscribe(function (data) {
            _this.getUsers();
            return true;
        }, function (error) {
            console.error("Error deleting user!");
            return __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__["a" /* Observable */].throw(error);
        });
    };
    //Blogs block
    AppComponent.prototype.getBlogs = function () {
        var _this = this;
        this._blogService.getBlogs().subscribe(function (data) { return _this.blogs = data; }, function (err) { return console.error(err); }, function () { return console.log('done loading foods'); });
    };
    AppComponent.prototype.createBlog = function (userId, header, content) {
        var _this = this;
        var blog = { userId: userId, header: header, blogContent: content };
        this._blogService.createBlog(blog).subscribe(function (data) {
            _this.getBlogs();
            _this.getUsers();
            return true;
        }, function (error) {
            console.error("Error saving user!");
            return __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__["a" /* Observable */].throw(error);
        });
    };
    AppComponent.prototype.deleteBlog = function (id) {
        var _this = this;
        this._blogService.deleteBlog(id).subscribe(function (data) {
            _this.getBlogs();
            return true;
        }, function (error) {
            console.error("Error deleting user!");
            return __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__["a" /* Observable */].throw(error);
        });
    };
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("./src/app/app.component.html"),
            styles: [__webpack_require__("./src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__blog_service__["a" /* BlogService */], __WEBPACK_IMPORTED_MODULE_3__user_service__["a" /* UserService */]])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__blog_service__ = __webpack_require__("./src/app/blog.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__user_service__ = __webpack_require__("./src/app/user.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__app_component__ = __webpack_require__("./src/app/app.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};







var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["E" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_6__app_component__["a" /* AppComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */], __WEBPACK_IMPORTED_MODULE_3__angular_forms__["a" /* FormsModule */], __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["b" /* HttpClientModule */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_4__blog_service__["a" /* BlogService */], __WEBPACK_IMPORTED_MODULE_5__user_service__["a" /* UserService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_6__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/blog.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BlogService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var httpOptions = {
    headers: new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["c" /* HttpHeaders */]({
        'Content-Type': 'application/json',
        'Authorization': 'Basic dGVzdDp0ZXN0'
    })
};
var BlogService = /** @class */ (function () {
    function BlogService(http) {
        this.http = http;
    }
    BlogService.prototype.getBlogs = function () {
        return this.http.get('http://145.239.78.41:9000/blog', httpOptions);
    };
    BlogService.prototype.getBlog = function (id) {
        return this.http.get('http://145.239.78.41:9000/blog/' + id, httpOptions);
    };
    BlogService.prototype.createBlog = function (blog) {
        var body = JSON.stringify(blog);
        return this.http.post('http://145.239.78.41:9000/blog', body, httpOptions);
    };
    BlogService.prototype.updateBlog = function (id, blog) {
        var body = JSON.stringify(blog);
        return this.http.put('http://145.239.78.41:9000/blog/' + id, body, httpOptions);
    };
    BlogService.prototype.deleteBlog = function (id) {
        return this.http.delete('http://145.239.78.41:9000/blog/' + id, httpOptions);
    };
    BlogService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], BlogService);
    return BlogService;
}());



/***/ }),

/***/ "./src/app/user.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var httpOptions = {
    headers: new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["c" /* HttpHeaders */]({
        'Content-Type': 'application/json',
        'Authorization': 'Basic dGVzdDp0ZXN0'
    })
};
var UserService = /** @class */ (function () {
    function UserService(http) {
        this.http = http;
    }
    UserService.prototype.getUsers = function () {
        return this.http.get('http://145.239.78.41:9000/users', httpOptions);
    };
    UserService.prototype.getUser = function (id) {
        return this.http.get('http://145.239.78.41:9000/users/' + id, httpOptions);
    };
    UserService.prototype.createUser = function (user) {
        var body = JSON.stringify(user);
        return this.http.post('http://145.239.78.41:9000/users', body, httpOptions);
    };
    UserService.prototype.updateUser = function (id, user) {
        var body = JSON.stringify(user);
        return this.http.put('http://145.239.78.41:9000/users/' + id, body, httpOptions);
    };
    UserService.prototype.deleteUser = function (id) {
        return this.http.delete('http://145.239.78.41:9000/users/' + id, httpOptions);
    };
    UserService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], UserService);
    return UserService;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "./src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("./node_modules/@angular/platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("./src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("./src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("./src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map