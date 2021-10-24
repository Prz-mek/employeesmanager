package com.example.employeesmanager.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/all")
    public List<Employee> getPhrases() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee newEmployee) {
        employeeService.addEmployee(newEmployee);
    }

    @PutMapping
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
