package com.drugalevmaxim.repository;


import com.drugalevmaxim.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    void save(Employee employee);
    void bulkSave(List<Employee> employees);
    List<Employee> findAllUnique();
    List<Employee> findAllMaleStartingWithF();

}
