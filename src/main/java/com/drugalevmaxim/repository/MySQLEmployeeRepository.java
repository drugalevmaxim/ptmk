package com.drugalevmaxim.repository;

import com.drugalevmaxim.db.DatabaseConnection;
import com.drugalevmaxim.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLEmployeeRepository implements EmployeeRepository {

    private long latestRequestExecutionTime = 0;

    @Override
    public void save(Employee employee) {
        String query = "INSERT INTO employees (fullName, birthDate, gender) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getFullName());
            stmt.setDate(2, Date.valueOf(employee.getBirthDate()));
            stmt.setString(3, employee.getGender() == Employee.Gender.MALE ? "m" : "f");
            long start = System.currentTimeMillis();
            stmt.executeUpdate();
            latestRequestExecutionTime = System.currentTimeMillis() - start;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bulkSave(List<Employee> employees) {
        String query = "INSERT INTO employees (fullName, birthDate, gender) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Employee employee : employees) {
                stmt.setString(1, employee.getFullName());
                stmt.setDate(2, Date.valueOf(employee.getBirthDate()));
                stmt.setString(3, employee.getGender() == Employee.Gender.MALE ? "m" : "f");
                long start = System.currentTimeMillis();
                stmt.addBatch();
                latestRequestExecutionTime = System.currentTimeMillis() - start;
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Employee> getEmployees( String query) {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            long start = System.currentTimeMillis();
            ResultSet rs = stmt.executeQuery();
            latestRequestExecutionTime = System.currentTimeMillis() - start;
            while (rs.next()) {
                String fullName = rs.getString("fullName");
                LocalDate birthdate = LocalDate.parse(rs.getString("birthdate"));
                Employee.Gender gender = rs.getString("gender").equals("m") ? Employee.Gender.MALE : Employee.Gender.FEMALE;
                employeeArrayList.add(new Employee(fullName, birthdate, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeArrayList;
    }

    @Override
    public List<Employee> findAllUnique() {
        String query = "SELECT fullName, birthdate, MIN(gender) as gender FROM employees GROUP BY fullName, birthdate;";
        return getEmployees(query);
    }

    @Override
    public List<Employee> findAllMaleStartingWithF() {
        String query = "SELECT fullName, birthdate, gender FROM employees WHERE gender = 'm' AND fullName LIKE 'F%';";
        return getEmployees(query);
    }

    public long getLatestRequestExecutionTime() {
        return latestRequestExecutionTime;
    }
}
