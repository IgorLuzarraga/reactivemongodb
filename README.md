Spring Boot Book´s Repository With Reactive MongoDB
==============

Spring Boot application that implements a book's repository using 
CRUD (Create, Read, Update, Delete) operations to create and recover
objects (books) stored in a MongoDB data base using Spring Data 
MongoDB reactive.
The GUI is made using the framework Vaadin.

Modules:
========
- Spring Boot
- Vaadin - Java web framework - https://vaadin.com
- Spring Data MongoDB Reactive
- MongoDB https://www.mongodb.com

Build the jar:
-------------------------
./gradlew build

Run the jar:
-------------------------
java -jar build/libs/reactivemongodb-0.0.1-SNAPSHOT.jar

Test the application:
-------------------------
1. Download and install MongoDB data base
2. Connect to the server via http://localhost:8080 and use the GUI
to check the CRUD operations.

