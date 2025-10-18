package com.hr.employee.dto;

import com.hr.employee.model.Employee;
import com.hr.employee.model.Status;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String title,
        String department,
        LocalDate hireDate,
        BigDecimal salary,
        @NotNull Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
