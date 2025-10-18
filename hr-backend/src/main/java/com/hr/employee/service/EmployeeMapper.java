package com.hr.employee.service;

import com.hr.employee.dto.EmployeeRequest;
import com.hr.employee.dto.EmployeeResponse;
import com.hr.employee.model.Employee;

public final class EmployeeMapper {
    private EmployeeMapper() {}

    public static void apply(EmployeeRequest r, Employee e) {
        e.setFirstName(r.firstName());
        e.setLastName(r.lastName());
        e.setEmail(r.email());
        e.setTitle(r.title());
        e.setDepartment(r.department());
        e.setHireDate(r.hireDate());
        e.setSalary(r.salary());
        e.setStatus(r.status());
    }

    public static EmployeeResponse toResponse(Employee e) {
        return new EmployeeResponse(
                e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(),
                e.getTitle(), e.getDepartment(), e.getHireDate(), e.getSalary(),
                e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
