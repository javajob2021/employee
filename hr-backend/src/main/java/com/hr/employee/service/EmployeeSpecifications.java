package com.hr.employee.service;

import com.hr.employee.model.Employee;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecifications {
    private EmployeeSpecifications() {}
    public static Specification<Employee> search(String q) {
        if (q == null || q.isBlank()) return null;
        String like = "%" + q.toLowerCase() + "%";
        return (root, cq, cb) -> cb.or(
                cb.like(cb.lower(root.get("firstName")), like),
                cb.like(cb.lower(root.get("lastName")), like),
                cb.like(cb.lower(root.get("email")), like),
                cb.like(cb.lower(root.get("department")), like),
                cb.like(cb.lower(root.get("title")), like)
        );
    }
}
