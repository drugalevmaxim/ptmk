# Employee DataBase application
This project is a test assignment for a junior developer. It's some kind of CRUD app, running on MySQL database. I tried to use repository pattern (most of the time).  
  
### Task:
Write console application with these modes:
Creating a table with employee directory fields representing “Last name First name Patronymic”, “date of birth”, “gender”.
- Create an employee database.
- Displays all records of the employee with a unique value of full name + date, sorted by full name.
- Automatically filling database with 1,000,000 records.
- Displays all records according to the criterion: gender male, Last name begins with "F". Measure the execution time.

Optimize the database or queries to the database to speed up the execution of step 5.

### How to compile
For compilation, you need:
- JDK 17
- Maven
- IDE

### How to run

1. Compile project with ```mvn install```
2. Open ```target``` directory
3. Set system variables ```DATABASE_URL```, ```DATABASE_NAME```, ```DATABASE_USERNAME``` and ```DATABASE_PASSWORD```
    i.e. ```jdbc:mysql://localhost:3307```, ```ptmk```, ```root```, ```123456```
4. Run ```java -jar ptmk-X.X.X-jar-with-dependencies.jar```

### Release notes
- **0.0.2** - Reworked database credentials 
- **0.0.1** - Initial release 

### Todos
- May move towards ODS or Spring
- Fix some of the messy code