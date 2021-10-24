package com.example.employeesmanager.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new IllegalStateException("Employee with id" + employeeId + "does not exist");
        }
        return employee.get();
    }

    public void addEmployee(Employee employee) {
        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (employeeByEmail.isPresent()) {
            throw new IllegalStateException("Employee already exists");
        }
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (optionalEmployee.isPresent() && optionalEmployee.get().getId() != employee.getId()) {
            throw new IllegalStateException("Employee with the same email already exists");
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalStateException("Employee with id" + employeeId + "does not exist");
        }
        employeeRepository.deleteById(employeeId);
    }
}
