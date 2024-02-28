package com.drugalevmaxim;

import com.drugalevmaxim.db.DatabaseConnection;
import com.drugalevmaxim.model.Employee;
import com.drugalevmaxim.repository.MySQLEmployeeRepository;
import com.drugalevmaxim.service.EmployeeService;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PTMK {

    private static void printHelp() {
        System.out.println("""
                Usage: [1] [2 <fullName> <birthDate> <gender>] [3] [4] [5]
                1                                   Generates table
                2 <fullName> <birthDate> <gender>   Add employee to database.
                                                    Birthday should be formated as
                                                    yyyy-mm-dd, and gender as M or F
                3                                   Outputs all workers with unique
                                                    birthday and fullname combo
                4                                   Adds 1000000 random employees
                                                    to database
                5                                   Outputs all males whoes name
                                                    starts with "F"\s""");
    }
    public static void main(String[] args) {
        if (args.length < 1) {
            printHelp();
            return;
        }
        MySQLEmployeeRepository sqlRepo = new MySQLEmployeeRepository();
        EmployeeService employeeService = new EmployeeService(sqlRepo);
        switch (args[0]) {
            case "1": {
                System.out.println("Creating table..");
                try {
                    DatabaseConnection.generateTable();
                    System.out.println("Table created");
                } catch (SQLException e) {
                    System.out.println("Unable to create table: " + e.getMessage());
                }
                break;
            }
            case "2": {
                if (args.length < 4) {
                    printHelp();
                    return;
                }
                try {
                    LocalDate birthday = LocalDate.parse(args[2]);
                    Employee.Gender gender;
                    if (args[3].equalsIgnoreCase("male")) {
                        gender = Employee.Gender.MALE;
                    } else if (args[3].equalsIgnoreCase("female")) {
                        gender = Employee.Gender.FEMALE;
                    } else {
                        throw new Exception();
                    }
                    employeeService.addEmployee(new Employee(args[1], birthday, gender));
                    System.out.println("Employee added");
                } catch (Exception e) {
                    System.out.println("Unable to parse given arguments");
                }
                break;
            }
            case "3": {
                long start = System.currentTimeMillis();
                List<Employee> employees = employeeService.getAllUniqueEmployees();
                long executionTime = System.currentTimeMillis() - start;
                printEmployees(employees);
                System.out.printf("Done in %d ms", sqlRepo.getLatestRequestExecutionTime());
                break;
            }
            case "4": {
                System.out.println("Filling table...");
                long start = System.currentTimeMillis();
                employeeService.fillMillionRows();
                long executionTime = System.currentTimeMillis() - start;
                System.out.printf("Done in %d ms", executionTime);
                System.out.println("Table filled");
                break;
            }
            case "5": {
                List<Employee> employees = employeeService.getAllMaleStartingWithF();
                printEmployees(employees);
                System.out.printf("Done in %d ms", sqlRepo.getLatestRequestExecutionTime());
                break;
            }
            default:
                printHelp();
                break;
        }
    }

    private static void printEmployees(List<Employee> employees) {
        OutputStream out = new BufferedOutputStream( System.out );
        for (Employee employee : employees) {
            try {
                out.write(String.format("%s, %s, %s\n", employee.getFullName(), employee.getGender(), employee.getBirthDate()).getBytes());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            out.flush();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
