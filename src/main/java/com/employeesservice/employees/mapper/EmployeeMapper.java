package com.employeesservice.employees.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.employeesservice.employees.dto.RequestEmployeeDto;
import com.employeesservice.employees.model.Employee;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    void updateEmployeeFromDto(RequestEmployeeDto dto, @MappingTarget Employee employee);

    @Mapping(target = "isActive", constant = "true")
    Employee toEntity(RequestEmployeeDto dto);
}