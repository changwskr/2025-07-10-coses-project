-- Audit and logging tables for SKCC Oversea Banking System
-- Version: 2.0
-- Description: Add audit tables for compliance and monitoring

-- Audit Log Table
CREATE TABLE audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    action_type VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50),
    entity_id VARCHAR(50),
    old_values TEXT,
    new_values TEXT,
    ip_address VARCHAR(15),
    user_agent TEXT,
    session_id VARCHAR(100),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_action_type (action_type),
    INDEX idx_entity_type (entity_type),
    INDEX idx_timestamp (timestamp),
    INDEX idx_session_id (session_id)
);

-- System Configuration Table
CREATE TABLE system_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL,
    config_value TEXT,
    config_type VARCHAR(20),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    UNIQUE KEY uk_config_key (config_key),
    INDEX idx_config_type (config_type),
    INDEX idx_is_active (is_active)
);

-- User Session Table
CREATE TABLE user_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(100) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logout_time TIMESTAMP NULL,
    ip_address VARCHAR(15),
    user_agent TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    last_activity TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_session_id (session_id),
    INDEX idx_user_id (user_id),
    INDEX idx_login_time (login_time),
    INDEX idx_is_active (is_active)
);

-- Error Log Table
CREATE TABLE error_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    error_code VARCHAR(20),
    error_message TEXT,
    stack_trace TEXT,
    user_id VARCHAR(50),
    transaction_id VARCHAR(50),
    request_url VARCHAR(500),
    request_method VARCHAR(10),
    request_params TEXT,
    ip_address VARCHAR(15),
    user_agent TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_error_code (error_code),
    INDEX idx_user_id (user_id),
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_timestamp (timestamp)
);

-- Business Event Log Table
CREATE TABLE business_event_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    event_source VARCHAR(50),
    event_data TEXT,
    user_id VARCHAR(50),
    transaction_id VARCHAR(50),
    status VARCHAR(20),
    error_message TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_date TIMESTAMP NULL,
    INDEX idx_event_type (event_type),
    INDEX idx_event_source (event_source),
    INDEX idx_user_id (user_id),
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_status (status),
    INDEX idx_created_date (created_date)
);

-- Insert default system configurations
INSERT INTO system_config (config_key, config_value, config_type, description) VALUES
('SYSTEM_NAME', 'SKCC Oversea Banking System', 'STRING', 'System name for display'),
('MAX_LOGIN_ATTEMPTS', '5', 'INTEGER', 'Maximum login attempts before account lock'),
('SESSION_TIMEOUT_MINUTES', '30', 'INTEGER', 'Session timeout in minutes'),
('PASSWORD_EXPIRY_DAYS', '90', 'INTEGER', 'Password expiry in days'),
('MIN_PASSWORD_LENGTH', '8', 'INTEGER', 'Minimum password length'),
('REQUIRE_SPECIAL_CHAR', 'true', 'BOOLEAN', 'Require special characters in password'),
('AUDIT_LOG_RETENTION_DAYS', '365', 'INTEGER', 'Audit log retention period in days'),
('TRANSACTION_LOG_RETENTION_DAYS', '180', 'INTEGER', 'Transaction log retention period in days'),
('ERROR_LOG_RETENTION_DAYS', '90', 'INTEGER', 'Error log retention period in days'),
('SYSTEM_MAINTENANCE_MODE', 'false', 'BOOLEAN', 'System maintenance mode flag'),
('DEFAULT_CURRENCY', 'KRW', 'STRING', 'Default currency code'),
('DEFAULT_LANGUAGE', 'ko', 'STRING', 'Default language code'),
('MAX_FILE_UPLOAD_SIZE_MB', '10', 'INTEGER', 'Maximum file upload size in MB'),
('ENABLE_TWO_FACTOR_AUTH', 'true', 'BOOLEAN', 'Enable two-factor authentication'),
('API_RATE_LIMIT_PER_MINUTE', '100', 'INTEGER', 'API rate limit per minute per user'); 