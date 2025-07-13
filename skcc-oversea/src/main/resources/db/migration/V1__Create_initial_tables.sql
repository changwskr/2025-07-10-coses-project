-- Initial database schema for SKCC Oversea Banking System
-- Version: 1.0
-- Description: Create initial tables for the migrated Spring Boot application

-- Enable UUID extension for PostgreSQL (if using PostgreSQL)
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Transaction Log Table
CREATE TABLE transaction_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(50) NOT NULL,
    transaction_no VARCHAR(50),
    host_name VARCHAR(100),
    system_name VARCHAR(50),
    method_name VARCHAR(100),
    bank_code VARCHAR(10),
    branch_code VARCHAR(10),
    user_id VARCHAR(50),
    channel_type VARCHAR(20),
    business_date VARCHAR(8),
    register_date VARCHAR(8),
    in_time VARCHAR(6),
    out_time VARCHAR(6),
    response_time BIGINT,
    error_code VARCHAR(10),
    event_no VARCHAR(20),
    ip_address VARCHAR(15),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_transaction_id (transaction_id),
    INDEX idx_transaction_no (transaction_no),
    INDEX idx_user_id (user_id),
    INDEX idx_system_name (system_name),
    INDEX idx_business_date (business_date),
    INDEX idx_created_date (created_date),
    INDEX idx_error_code (error_code),
    INDEX idx_channel_type (channel_type),
    INDEX idx_bank_branch (bank_code, branch_code)
);

-- Cash Card Table
CREATE TABLE cash_card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_no VARCHAR(20) NOT NULL,
    account_no VARCHAR(20),
    customer_id VARCHAR(20),
    card_type VARCHAR(10),
    card_status VARCHAR(2),
    issue_date DATE,
    expiry_date DATE,
    daily_limit DECIMAL(15,2),
    monthly_limit DECIMAL(15,2),
    current_balance DECIMAL(15,2),
    currency_code VARCHAR(3),
    pin_number VARCHAR(6),
    card_holder_name VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_card_no (card_no),
    INDEX idx_account_no (account_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_card_status (card_status),
    INDEX idx_card_type (card_type),
    INDEX idx_expiry_date (expiry_date),
    INDEX idx_currency_code (currency_code)
);

-- Deposit Table
CREATE TABLE deposit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no VARCHAR(20) NOT NULL,
    customer_id VARCHAR(20) NOT NULL,
    account_type VARCHAR(10),
    account_status VARCHAR(2),
    balance DECIMAL(15,2),
    currency_code VARCHAR(3),
    interest_rate DECIMAL(5,4),
    open_date DATE,
    maturity_date DATE,
    last_transaction_date TIMESTAMP,
    branch_code VARCHAR(10),
    product_code VARCHAR(10),
    account_name VARCHAR(100),
    minimum_balance DECIMAL(15,2),
    daily_limit DECIMAL(15,2),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_account_no (account_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_account_status (account_status),
    INDEX idx_account_type (account_type),
    INDEX idx_currency_code (currency_code),
    INDEX idx_branch_code (branch_code),
    INDEX idx_product_code (product_code),
    INDEX idx_maturity_date (maturity_date),
    INDEX idx_open_date (open_date)
);

-- Customer Table
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(20) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    customer_type VARCHAR(10),
    id_type VARCHAR(10),
    id_number VARCHAR(50),
    birth_date DATE,
    phone_number VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    nationality VARCHAR(3),
    occupation VARCHAR(50),
    annual_income DECIMAL(15,2),
    risk_level VARCHAR(10),
    status VARCHAR(2) DEFAULT 'AC',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_customer_id (customer_id),
    INDEX idx_customer_name (customer_name),
    INDEX idx_customer_type (customer_type),
    INDEX idx_id_number (id_number),
    INDEX idx_status (status),
    INDEX idx_risk_level (risk_level)
);

-- Branch Table
CREATE TABLE branch (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    branch_code VARCHAR(10) NOT NULL,
    branch_name VARCHAR(100) NOT NULL,
    bank_code VARCHAR(10),
    address TEXT,
    phone_number VARCHAR(20),
    manager_name VARCHAR(100),
    status VARCHAR(2) DEFAULT 'AC',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_branch_code (branch_code),
    INDEX idx_bank_code (bank_code),
    INDEX idx_branch_name (branch_name),
    INDEX idx_status (status)
);

-- Product Table
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(10) NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    product_type VARCHAR(20),
    description TEXT,
    min_amount DECIMAL(15,2),
    max_amount DECIMAL(15,2),
    interest_rate DECIMAL(5,4),
    term_months INT,
    currency_code VARCHAR(3),
    status VARCHAR(2) DEFAULT 'AC',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_product_code (product_code),
    INDEX idx_product_type (product_type),
    INDEX idx_currency_code (currency_code),
    INDEX idx_status (status)
);

-- Insert sample data for testing
INSERT INTO branch (branch_code, branch_name, bank_code, address, phone_number, manager_name) VALUES
('001', 'Seoul Main Branch', 'SKCC', '123 Gangnam-gu, Seoul', '02-1234-5678', 'Kim Manager'),
('002', 'Busan Branch', 'SKCC', '456 Haeundae-gu, Busan', '051-234-5678', 'Lee Manager'),
('003', 'Incheon Branch', 'SKCC', '789 Namdong-gu, Incheon', '032-345-6789', 'Park Manager');

INSERT INTO product (product_code, product_name, product_type, description, min_amount, max_amount, interest_rate, term_months, currency_code) VALUES
('SAV001', 'Regular Savings', 'SAVINGS', 'Basic savings account with competitive interest rate', 10000, 1000000000, 0.0250, 12, 'KRW'),
('DEP001', 'Fixed Deposit', 'DEPOSIT', 'Fixed term deposit with guaranteed interest rate', 100000, 1000000000, 0.0350, 12, 'KRW'),
('CUR001', 'Current Account', 'CURRENT', 'Checking account for daily transactions', 0, 1000000000, 0.0000, 0, 'KRW');

INSERT INTO customer (customer_id, customer_name, customer_type, id_type, id_number, birth_date, phone_number, email, nationality) VALUES
('CUST001', 'John Doe', 'INDIVIDUAL', 'PASSPORT', 'M12345678', '1985-03-15', '010-1234-5678', 'john.doe@email.com', 'KOR'),
('CUST002', 'Jane Smith', 'INDIVIDUAL', 'PASSPORT', 'F87654321', '1990-07-22', '010-2345-6789', 'jane.smith@email.com', 'USA'),
('CUST003', 'ABC Corporation', 'CORPORATE', 'BUSINESS_LICENSE', 'BL123456789', NULL, '02-3456-7890', 'info@abc.com', 'KOR'); 