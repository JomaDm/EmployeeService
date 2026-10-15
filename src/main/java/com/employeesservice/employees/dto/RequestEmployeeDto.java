package com.employeesservice.employees.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestEmployeeDto(

        @NotNull(message = "First name is required") @Size(max = 50, message = "First name must not exceed 50 characters") String firstName,
        @Size(max = 50, message = "Middle name must not exceed 50 characters") String middleName,
        @NotNull(message = "Paternal last name is required") @Size(max = 50, message = "Paternal last name must not exceed 50 characters") String lastNamePaternal,
        @Size(max = 50, message = "Maternal last name must not exceed 50 characters") String lastNameMaternal,
        @NotNull(message = "Age is required") @Min(value = 18, message = "Age must be at least 18") @Max(value = 100, message = "Age must be at most 100") Integer age,
        @NotNull(message = "Birth date is required") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate birthDate,
        @NotNull(message = "Gender is required") String gender,
        @NotNull(message = "Position is required") @Size(max = 100, message = "Position must not exceed 100 characters") String position) {
}
