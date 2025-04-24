CREATE DATABASE employee_management;
USE employee_management;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE user_roles (
    username VARCHAR(50),
    role VARCHAR(50),
    PRIMARY KEY (username, role),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    department_id BIGINT NOT NULL,
    salary DOUBLE NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    entity_id BIGINT,
    timestamp DATETIME NOT NULL
);

CREATE TABLE session_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    session_id VARCHAR(100) NOT NULL,
    ip_address VARCHAR(45),
    created_at DATETIME NOT NULL,
    destroyed_at DATETIME
);

-- Dữ liệu mẫu
INSERT INTO users (username, password, enabled) VALUES 
('admin', '$2a$10$XURPShQ5uSTf66Qck2rwSu4lGNenW5n3Z6uAk3tMnu7rVWJvlK5iC', true), -- admin123
('user', '$2a$10$XURPShQ5uSTf66Qck2rwSu4lGNenW5n3Z6uAk3tMnu7rVWJvlK5iC', true);  -- user123

INSERT INTO user_roles (username, role) VALUES 
('admin', 'admin'), ('admin', 'user'), ('user', 'user');

INSERT INTO departments (name) VALUES 
('IT'), ('HR'), ('Finance');

INSERT INTO employees (full_name, birth_date, department_id, salary) VALUES 
('Nguyen Van A', '1990-01-01', 1, 5000.00),
('Tran Thi B', '1995-02-15', 2, 4500.00);