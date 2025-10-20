-- V1__init_employee.sql
-- Initial schema and seed data for HR Employee system

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(120),
    department VARCHAR(120),
    hire_date DATE,
    salary DECIMAL(12,2) DEFAULT 0,
    status ENUM('ACTIVE','INACTIVE','ON_LEAVE','TERMINATED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Optional sample employee
INSERT INTO employees (first_name, last_name, email, title, department, hire_date, salary, status)
VALUES ('Mike', 'Lee', 'mike@hr.com', 'Software Engineer', 'R&D', '2023-01-15', 95000.00, 'ACTIVE');
