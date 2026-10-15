package com.employeesservice.employees.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.employeesservice.employees.dto.RequestEmployeeDto;
import com.employeesservice.employees.mapper.EmployeeMapper;
import com.employeesservice.employees.model.Employee;
import com.employeesservice.employees.repository.EmployeesRepository;

@Service
public class EmployeeService {

    private EmployeesRepository employeesRepository;
    private EmployeeMapper employeeMapper;

    EmployeeService(EmployeesRepository employeesRepository, EmployeeMapper employeeMapper) {
        this.employeesRepository = employeesRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> getAllEmployees() {
        return employeesRepository.findByIsActive(true);
    }

    public Employee createEmployee(RequestEmployeeDto requestEmployeeDto) {
        Employee employee = employeeMapper.toEntity(requestEmployeeDto);
        return employeesRepository.save(employee);
    }

    public Employee getOneById(Long id) {
        Optional<Employee> employee = this.employeesRepository.findById(id);

        if (!employee.isPresent() || employee.get().getIsActive().equals(false)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found or inactive");
        }

        return employee.get();
    }

    public Employee deleteEmployee(Long id) {
        Optional<Employee> employee = this.employeesRepository.findById(id);

        if (!employee.isPresent() || employee.get().getIsActive().equals(false)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found or inactive");
        }
        employee.get().setIsActive(false);
        return employeesRepository.save(employee.get());
    }

    public Employee updateEmployee(Long id, RequestEmployeeDto requestEmployeeDto) {
        Optional<Employee> employeeOpt = this.employeesRepository.findById(id);

        if (employeeOpt.isEmpty() || Boolean.FALSE.equals(employeeOpt.get().getIsActive())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found or inactive");
        }

        Employee employee = employeeOpt.get();

        employeeMapper.updateEmployeeFromDto(requestEmployeeDto, employee);
        return employeesRepository.save(employee);
    }

    public List<Employee> searchEmployee(String name) {
        return this.employeesRepository.searchByFullNameIlike(name);
    }

}
