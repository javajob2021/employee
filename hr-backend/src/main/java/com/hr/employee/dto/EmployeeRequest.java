package com.hr.employee.dto;

import com.hr.employee.model.Employee;
import com.hr.employee.model.Status;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        String title,
        String department,
        LocalDate hireDate,
        @DecimalMin("0.0") BigDecimal salary,
        @NotNull Status status
) {}
