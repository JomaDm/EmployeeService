package com.employeesservice.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employeesservice.employees.model.Employee;
import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {
    public List<Employee> findByIsActive(boolean isActive);

    @Query("SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', COALESCE(e.middleName, '')) ILIKE %:name%")
    List<Employee> searchByFullNameIlike(@Param("name") String name);
}
