import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {BlogService} from './blog.service';
import {UserService} from "./user.service";
import {Globals} from "./global";

import {AppComponent} from './app.component';


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule, FormsModule, HttpClientModule
    ],
    providers: [BlogService, UserService, Globals],
    bootstrap: [AppComponent]
})
export class AppModule {
}
