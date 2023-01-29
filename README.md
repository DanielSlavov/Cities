# Cities application

## Getting started
### 1. Before starting we have to populate the Database:

In mysql console we need to run the init_db.sql, which is located in the resources folder:

Cities/src/main/resources


```
mysql> source /path/to/db_init.sql
```
### 2. Config
After that we need to set the database connection string, username and password
in a your_props.properties file.

You can use the application.properties file for reference, which is located in the resources folder

### 3. Build the project
In the root folder of the project run
```
mvn clean
mvn install
````

Or if you prefer to do it from an IDE make sure to add it as a Maven project :)

### 4. We are ready to run
Obtain the .jar file from /target and run it with your application.properties
```
java -jar Cities-1.0.0.jar --spring.config.location=./your_props.properties
````
The provided test accounts are:
`user:pass`
`admin:admin`

### System requirements:
Java 11, MySQL

### Side notes
The Front-end is in React and it is pre-built so you don't have to do anything in that regard
 
However if you want to build it yourself go into /cities-frontend and run
```
npm install
npm run build
```
Then into the newly generated /build folder move all the files outside of /static into /static - this is because by default Spring serves inside of static only.
Replace all `/static` paths to just `/` and move the /static folder to Cities/src/main/resources/

You can now build the project with the newly compiled React front-end
