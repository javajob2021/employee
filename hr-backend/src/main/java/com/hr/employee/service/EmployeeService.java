package com.hr.employee.service;

import com.hr.employee.dto.EmployeeRequest;
import com.hr.employee.dto.EmployeeResponse;
import com.hr.employee.model.Employee;
import com.hr.employee.repo.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repo;

    @Transactional
    public EmployeeResponse create(EmployeeRequest r) {
        if (repo.existsByEmail(r.email()))
            throw new DataIntegrityViolationException("Email already exists: " + r.email());
        Employee e = new Employee();
        EmployeeMapper.apply(r, e);
        return EmployeeMapper.toResponse(repo.save(e));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest r) {
        Employee e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee " + id + " not found"));
        if (!e.getEmail().equalsIgnoreCase(r.email()) && repo.existsByEmail(r.email()))
            throw new DataIntegrityViolationException("Email already exists: " + r.email());
        EmployeeMapper.apply(r, e);
        return EmployeeMapper.toResponse(e);
    }

    @Transactional public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Employee " + id + " not found");
        repo.deleteById(id);
    }

    @Transactional public EmployeeResponse get(Long id) {
        return repo.findById(id).map(EmployeeMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Employee " + id + " not found"));
    }

    @Transactional public Page<EmployeeResponse> list(String q, Pageable pageable) {
        return repo.findAll(EmployeeSpecifications.search(q), pageable).map(EmployeeMapper::toResponse);
    }
}
