package org.andersenTask.service;

import org.andersenTask.entity.Employee;
import org.andersenTask.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService() {
        this.employeeRepository = new EmployeeRepository();
    }

    public void create(Employee employee) {
        employeeRepository.insert(employee);
    }

    public Employee getById(Long id) {
        return employeeRepository.getById(id);
    }

    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public void updateById(Employee employee) {
        employeeRepository.update(employee);
    }
}
