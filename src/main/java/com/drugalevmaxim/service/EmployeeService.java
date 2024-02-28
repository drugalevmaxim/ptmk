package com.drugalevmaxim.service;

import com.drugalevmaxim.model.Employee;
import com.drugalevmaxim.repository.EmployeeRepository;
import com.drugalevmaxim.util.RandomEmployeeGenerator;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository repository;

    // Внедрение зависимостей через конструктор
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void addEmployee(Employee employee) {
        repository.save(employee);
    }

    public List<Employee> getAllUniqueEmployees() {
        return repository.findAllUnique();
    }

    public List<Employee> getAllMaleStartingWithF() {
        return repository.findAllMaleStartingWithF();
    }

    public void fillMillionRows() {
        for (int i = 0; i < 1000; i++) {
            List<Employee> employees = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                employees.add(RandomEmployeeGenerator.GenerateRandomEmployee());
            }
            repository.bulkSave(employees);
        }
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            employees.add(RandomEmployeeGenerator.GenerateRandomMaleStartingWithF());
        }
        repository.bulkSave(employees);
    }

}
