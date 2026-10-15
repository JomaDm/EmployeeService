package com.employeesservice.employees;

import com.employeesservice.employees.dto.RequestEmployeeDto;
import com.employeesservice.employees.mapper.EmployeeMapper;
import com.employeesservice.employees.model.Employee;
import com.employeesservice.employees.repository.EmployeesRepository;
import com.employeesservice.employees.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private RequestEmployeeDto dto;

    @BeforeEach
    void setUp() {
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
    void getAllEmployeesReturnsActiveEmployees() {
        when(employeesRepository.findByIsActive(true))
                .thenReturn(List.of(employee));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertSame(employee, result.get(0));

        verify(employeesRepository).findByIsActive(true);
    }

    @Test
    void createEmployeeMapsAndSavesEmployee() {
        when(employeeMapper.toEntity(dto)).thenReturn(employee);
        when(employeesRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(dto);

        assertSame(employee, result);
        verify(employeeMapper).toEntity(dto);
        verify(employeesRepository).save(employee);
    }

    @Test
    void getOneByIdReturnsEmployeeWhenActive() {
        when(employeesRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee result = employeeService.getOneById(1L);

        assertSame(employee, result);
    }

    @Test
    void getOneByIdThrowsNotFoundWhenEmployeeDoesNotExist() {
        when(employeesRepository.findById(99L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> employeeService.getOneById(99L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getOneByIdThrowsNotFoundWhenEmployeeIsInactive() {
        employee.setIsActive(false);

        when(employeesRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> employeeService.getOneById(1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deleteEmployeeMarksEmployeeAsInactive() {
        when(employeesRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeesRepository.save(employee))
                .thenReturn(employee);

        Employee result = employeeService.deleteEmployee(1L);

        assertFalse(result.getIsActive());
        verify(employeesRepository).save(employee);
    }

    @Test
    void deleteEmployeeThrowsNotFoundWhenEmployeeDoesNotExist() {
        when(employeesRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> employeeService.deleteEmployee(99L));

        verify(employeesRepository, never()).save(any());
    }

    @Test
    void deleteEmployeeThrowsNotFoundWhenEmployeeIsInactive() {
        employee.setIsActive(false);

        when(employeesRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        assertThrows(
                ResponseStatusException.class,
                () -> employeeService.deleteEmployee(1L));

        verify(employeesRepository, never()).save(any());
    }

    @Test
    void updateEmployeeUpdatesAndSavesEmployee() {
        when(employeesRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        doAnswer(invocation -> {
            Employee target = invocation.getArgument(1);
            target.setPosition("Senior Developer");
            return null;
        }).when(employeeMapper)
                .updateEmployeeFromDto(dto, employee);

        when(employeesRepository.save(employee))
                .thenReturn(employee);

        Employee result = employeeService.updateEmployee(1L, dto);

        assertEquals("Senior Developer", result.getPosition());

        verify(employeeMapper)
                .updateEmployeeFromDto(dto, employee);

        verify(employeesRepository).save(employee);
    }

    @Test
    void updateEmployeeThrowsNotFoundWhenEmployeeDoesNotExist() {
        when(employeesRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> employeeService.updateEmployee(99L, dto));

        verify(employeeMapper, never())
                .updateEmployeeFromDto(any(), any());

        verify(employeesRepository, never()).save(any());
    }

    @Test
    void updateEmployeeThrowsNotFoundWhenEmployeeIsInactive() {
        employee.setIsActive(false);

        when(employeesRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        assertThrows(
                ResponseStatusException.class,
                () -> employeeService.updateEmployee(1L, dto));

        verify(employeeMapper, never())
                .updateEmployeeFromDto(any(), any());

        verify(employeesRepository, never()).save(any());
    }

    @Test
    void searchEmployeeDelegatesToRepository() {
        when(employeesRepository.searchByFullNameIlike("Ana"))
                .thenReturn(List.of(employee));

        List<Employee> result = employeeService.searchEmployee("Ana");

        assertEquals(1, result.size());
        assertSame(employee, result.get(0));

        verify(employeesRepository)
                .searchByFullNameIlike("Ana");
    }
}
