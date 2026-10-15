package com.employeesservice.employees.controller;

import org.springframework.web.bind.annotation.RestController;

import com.employeesservice.employees.dto.RequestEmployeeDto;
import com.employeesservice.employees.model.Employee;
import com.employeesservice.employees.service.EmployeeService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return employeeService.getOneById(id);
    }

    @GetMapping("/search")
    public List<Employee> getByName(@RequestParam String name) {
        return employeeService.searchEmployee(name);
    }

    @PostMapping("")
    public Employee createEmployee(@RequestBody RequestEmployeeDto entity) {
        return employeeService.createEmployee(entity);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody RequestEmployeeDto entity) {
        return this.employeeService.updateEmployee(id, entity);
    }

    @DeleteMapping("{id}")
    public Employee deleteEmployee(@PathVariable Long id) {
        return this.employeeService.deleteEmployee(id);
    }

}
