-- infra/db/init/V1__init_employee.sql
CREATE DATABASE IF NOT EXISTS hrdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hrdb;

CREATE TABLE IF NOT EXISTS employees (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(120),
    department VARCHAR(120),
    hire_date DATE,
    salary DECIMAL(12,2) DEFAULT 0,
    status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- optional seed row
INSERT INTO employees (first_name, last_name, email, title, department, hire_date, salary)
VALUES ('Alice', 'Doe', 'alice.doe@example.com', 'Engineer', 'R&D', '2022-01-15', 90000.00);
