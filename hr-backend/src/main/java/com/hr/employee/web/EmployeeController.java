package com.hr.employee.web;

import com.hr.employee.dto.EmployeeRequest;
import com.hr.employee.dto.EmployeeResponse;
import com.hr.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public EmployeeResponse create(@Valid @RequestBody EmployeeRequest request) { return service.create(request); }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}") public EmployeeResponse get(@PathVariable Long id) { return service.get(id); }

    @DeleteMapping("/{id}") @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping
    public Page<EmployeeResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        Sort s = Sort.by(sort.split(",")[0]);
        s = sort.endsWith(",asc") ? s.ascending() : s.descending();
        Pageable pageable = PageRequest.of(page, size, s);
        return service.list(q, pageable);
    }
}
