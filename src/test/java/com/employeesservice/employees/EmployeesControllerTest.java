package com.employeesservice.employees;

import com.employeesservice.employees.controller.EmployeesController;
import com.employeesservice.employees.dto.RequestEmployeeDto;
import com.employeesservice.employees.model.Employee;
import com.employeesservice.employees.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeesControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeesController controller;
    private Employee employee;
    private RequestEmployeeDto dto;

    @BeforeEach
    void setUp() {
        controller = new EmployeesController(employeeService);

        employee = new Employee(
                "Ana",
                "Maria",
                "Lopez",
                "Diaz",
                30,
                "Femenino",
                LocalDate.of(1996, 1, 10),
                "Developer");

        dto = new RequestEmployeeDto(
                "Ana",
                "Maria",
                "Lopez",
                "Diaz",
                30,
                LocalDate.of(1996, 1, 10),
                "Femenino",
                "Developer");
    }

    @Test
    void getAllReturnsEmployeesFromService() {
        when(employeeService.getAllEmployees())
                .thenReturn(List.of(employee));

        List<Employee> result = controller.getAll();

        assertEquals(1, result.size());
        assertSame(employee, result.get(0));

        verify(employeeService).getAllEmployees();
    }

    @Test
    void getByIdReturnsEmployeeFromService() {
        when(employeeService.getOneById(1L))
                .thenReturn(employee);

        Employee result = controller.getById(1L);

        assertSame(employee, result);
        verify(employeeService).getOneById(1L);
    }

    @Test
    void getByNameReturnsSearchResults() {
        when(employeeService.searchEmployee("Ana"))
                .thenReturn(List.of(employee));

        List<Employee> result = controller.getByName("Ana");

        assertEquals(1, result.size());
        verify(employeeService).searchEmployee("Ana");
    }

    @Test
    void createEmployeeDelegatesToService() {
        when(employeeService.createEmployee(dto))
                .thenReturn(employee);

        Employee result = controller.createEmployee(dto);

        assertSame(employee, result);
        verify(employeeService).createEmployee(dto);
    }

    @Test
    void updateEmployeeDelegatesToService() {
        when(employeeService.updateEmployee(1L, dto))
                .thenReturn(employee);

        Employee result = controller.updateEmployee(1L, dto);

        assertSame(employee, result);
        verify(employeeService).updateEmployee(1L, dto);
    }

    @Test
    void deleteEmployeeDelegatesToService() {
        when(employeeService.deleteEmployee(1L))
                .thenReturn(employee);

        Employee result = controller.deleteEmployee(1L);

        assertSame(employee, result);
        verify(employeeService).deleteEmployee(1L);
    }
}
