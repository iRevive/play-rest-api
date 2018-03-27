**Summary**
--
Project structure:    
1) `<root>/conf/application.conf` - configuration file;   
2) `<root>/conf/logback.xml` - logback configuration;  
3) `<root>/app/controllers` - entity-specific controllers;  
4) `<root>/app/persistence/dao` - entity-specific dao;  
5) `<root>/app/persistence/mapping` - database mappings and query projections;  
6) `<root>/play-rest-api-client/src/app` - Angular 4 client;  
7) `<root>/docker` - docker configuration.

#### Development requirements
1) Scala 2.12.4;  
2) SBT 0.13.16;  

# Configuration

#### How to install sbt
1) [MacOS](http://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html)  
2) [Windows](http://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Windows.html)   
3) [Linux](http://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html)   

#### How to install Angular 4 environment
First of all, [install](https://docs.npmjs.com/cli/install) NPM. Then [install]((https://angular.io/guide/quickstart)) Angular 4 CLI.  

#### How to run server and client in a development mode
Don't forget to configure a database in the `application.conf` file.   
In a `<root>` folder:  
```sbtshell
sbt run
```
In a `<root>/play-rest-api-client` folder:
```sbtshell
ng serve --open
```
You application will be available at `http://localhost:4200/`.

#### How to configure an application
All settings are stored in the `application.conf`.  
1) `application.rest.max-query-limit` - max value for the 'limit' parameter for 'list' requests;  
2) `application.rest.auth.username` - basic auth login;  
3) `application.rest.auth.password` - basic auth password;  
4) `slick.dbs.default.db.url` - database connection url;  
5) `slick.dbs.default.db.user` - database user;  
6) `slick.dbs.default.db.password` - database password;  
By default, application is listening on port 9000.  

#### How to generate Slick models
In a `<root>` project folder write in a console  
```sbtshell
sbt gen-tables
```
Check database configuration in the `application.conf` file before.

#### How to build a standalone application
In a `<root>` project folder execute  
```sbtshell
sbt universal:packageBin
```

The output file will be located at this place:
```
<root>/target/universal/play_rest_api.zip
```

#### How to run an application
Unzip an `play_rest_api.zip` archive in any directory (`<root>/dist`, for example).    
Execute in a `<root>/dist/play_rest_api` folder:    
```sbtshell
sh bin/play_rest_api
```

#### How to create a dev docker image
SBT will publish an image locally using a name based on a git hash.  
Execute in a `<root>` project folder:  
```sbtshell
sbt dev:docker
```

#### How to run a docker container
Check configuration properties in the `docker-compose.yml` file.  
Execute in a `<root>/docker` project folder: 
```
docker-compose up
```

Don't forget to create a database and tables.

#### How to release a version
Execute in a `<root>` project folder:  
```sbtshell
sbt "release with-defaults"
```
