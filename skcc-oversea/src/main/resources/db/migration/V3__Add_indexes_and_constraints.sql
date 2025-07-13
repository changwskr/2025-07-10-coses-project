-- Performance optimization indexes and constraints for SKCC Oversea Banking System
-- Version: 3.0
-- Description: Add indexes and constraints for better performance and data integrity

-- Add foreign key constraints
ALTER TABLE cash_card 
ADD CONSTRAINT fk_cash_card_customer 
FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE cash_card 
ADD CONSTRAINT fk_cash_card_account 
FOREIGN KEY (account_no) REFERENCES deposit(account_no) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE deposit 
ADD CONSTRAINT fk_deposit_customer 
FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE deposit 
ADD CONSTRAINT fk_deposit_branch 
FOREIGN KEY (branch_code) REFERENCES branch(branch_code) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE deposit 
ADD CONSTRAINT fk_deposit_product 
FOREIGN KEY (product_code) REFERENCES product(product_code) ON DELETE RESTRICT ON UPDATE CASCADE;

-- Add composite indexes for better query performance
CREATE INDEX idx_cash_card_customer_status ON cash_card(customer_id, card_status);
CREATE INDEX idx_cash_card_type_status ON cash_card(card_type, card_status);
CREATE INDEX idx_cash_card_expiry_status ON cash_card(expiry_date, card_status);
CREATE INDEX idx_cash_card_balance_currency ON cash_card(current_balance, currency_code);

CREATE INDEX idx_deposit_customer_status ON deposit(customer_id, account_status);
CREATE INDEX idx_deposit_type_status ON deposit(account_type, account_status);
CREATE INDEX idx_deposit_balance_currency ON deposit(balance, currency_code);
CREATE INDEX idx_deposit_maturity_status ON deposit(maturity_date, account_status);
CREATE INDEX idx_deposit_branch_status ON deposit(branch_code, account_status);

CREATE INDEX idx_transaction_log_user_date ON transaction_log(user_id, created_date);
CREATE INDEX idx_transaction_log_system_date ON transaction_log(system_name, created_date);
CREATE INDEX idx_transaction_log_status_date ON transaction_log(error_code, created_date);
CREATE INDEX idx_transaction_log_channel_date ON transaction_log(channel_type, created_date);

CREATE INDEX idx_audit_log_user_action ON audit_log(user_id, action_type);
CREATE INDEX idx_audit_log_entity_action ON audit_log(entity_type, action_type);
CREATE INDEX idx_audit_log_timestamp_action ON audit_log(timestamp, action_type);

CREATE INDEX idx_user_session_user_active ON user_session(user_id, is_active);
CREATE INDEX idx_user_session_last_activity ON user_session(last_activity, is_active);

CREATE INDEX idx_error_log_code_timestamp ON error_log(error_code, timestamp);
CREATE INDEX idx_error_log_user_timestamp ON error_log(user_id, timestamp);

CREATE INDEX idx_business_event_type_status ON business_event_log(event_type, status);
CREATE INDEX idx_business_event_source_status ON business_event_log(event_source, status);
CREATE INDEX idx_business_event_created_status ON business_event_log(created_date, status);

-- Add check constraints for data validation
ALTER TABLE cash_card 
ADD CONSTRAINT chk_cash_card_status 
CHECK (card_status IN ('AC', 'IN', 'BL', 'EX', 'CA'));

ALTER TABLE cash_card 
ADD CONSTRAINT chk_cash_card_type 
CHECK (card_type IN ('DEBIT', 'CREDIT', 'PREPAID'));

ALTER TABLE cash_card 
ADD CONSTRAINT chk_cash_card_balance 
CHECK (current_balance >= 0);

ALTER TABLE cash_card 
ADD CONSTRAINT chk_cash_card_limits 
CHECK (daily_limit >= 0 AND monthly_limit >= 0);

ALTER TABLE deposit 
ADD CONSTRAINT chk_deposit_status 
CHECK (account_status IN ('AC', 'IN', 'BL', 'CL', 'CA'));

ALTER TABLE deposit 
ADD CONSTRAINT chk_deposit_type 
CHECK (account_type IN ('SAVINGS', 'CURRENT', 'FIXED', 'INVESTMENT'));

ALTER TABLE deposit 
ADD CONSTRAINT chk_deposit_balance 
CHECK (balance >= 0);

ALTER TABLE deposit 
ADD CONSTRAINT chk_deposit_interest_rate 
CHECK (interest_rate >= 0 AND interest_rate <= 1);

ALTER TABLE customer 
ADD CONSTRAINT chk_customer_type 
CHECK (customer_type IN ('INDIVIDUAL', 'CORPORATE', 'GOVERNMENT'));

ALTER TABLE customer 
ADD CONSTRAINT chk_customer_status 
CHECK (status IN ('AC', 'IN', 'BL', 'CA'));

ALTER TABLE customer 
ADD CONSTRAINT chk_customer_risk_level 
CHECK (risk_level IN ('LOW', 'MEDIUM', 'HIGH', 'VERY_HIGH'));

ALTER TABLE branch 
ADD CONSTRAINT chk_branch_status 
CHECK (status IN ('AC', 'IN', 'CL'));

ALTER TABLE product 
ADD CONSTRAINT chk_product_type 
CHECK (product_type IN ('SAVINGS', 'CURRENT', 'DEPOSIT', 'INVESTMENT', 'LOAN'));

ALTER TABLE product 
ADD CONSTRAINT chk_product_status 
CHECK (status IN ('AC', 'IN', 'CL'));

ALTER TABLE product 
ADD CONSTRAINT chk_product_interest_rate 
CHECK (interest_rate >= 0 AND interest_rate <= 1);

ALTER TABLE product 
ADD CONSTRAINT chk_product_amounts 
CHECK (min_amount >= 0 AND max_amount >= min_amount);

-- Add unique constraints for business rules
ALTER TABLE customer 
ADD CONSTRAINT uk_customer_id_number 
UNIQUE (id_type, id_number);

ALTER TABLE cash_card 
ADD CONSTRAINT uk_cash_card_account 
UNIQUE (account_no, card_type);

-- Add default values for important fields
ALTER TABLE cash_card 
ALTER COLUMN card_status SET DEFAULT 'AC',
ALTER COLUMN current_balance SET DEFAULT 0,
ALTER COLUMN currency_code SET DEFAULT 'KRW';

ALTER TABLE deposit 
ALTER COLUMN account_status SET DEFAULT 'AC',
ALTER COLUMN balance SET DEFAULT 0,
ALTER COLUMN currency_code SET DEFAULT 'KRW',
ALTER COLUMN interest_rate SET DEFAULT 0;

ALTER TABLE customer 
ALTER COLUMN status SET DEFAULT 'AC',
ALTER COLUMN risk_level SET DEFAULT 'MEDIUM';

ALTER TABLE branch 
ALTER COLUMN status SET DEFAULT 'AC';

ALTER TABLE product 
ALTER COLUMN status SET DEFAULT 'AC',
ALTER COLUMN interest_rate SET DEFAULT 0;

-- Add triggers for audit logging (MySQL syntax)
DELIMITER $$

CREATE TRIGGER tr_cash_card_audit_insert
AFTER INSERT ON cash_card
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (user_id, action_type, entity_type, entity_id, new_values, timestamp)
    VALUES (NEW.created_by, 'INSERT', 'CASH_CARD', NEW.id, JSON_OBJECT('card_no', NEW.card_no, 'customer_id', NEW.customer_id), NOW());
END$$

CREATE TRIGGER tr_cash_card_audit_update
AFTER UPDATE ON cash_card
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (user_id, action_type, entity_type, entity_id, old_values, new_values, timestamp)
    VALUES (NEW.modified_by, 'UPDATE', 'CASH_CARD', NEW.id, 
            JSON_OBJECT('card_status', OLD.card_status, 'current_balance', OLD.current_balance),
            JSON_OBJECT('card_status', NEW.card_status, 'current_balance', NEW.current_balance), NOW());
END$$

CREATE TRIGGER tr_deposit_audit_insert
AFTER INSERT ON deposit
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (user_id, action_type, entity_type, entity_id, new_values, timestamp)
    VALUES (NEW.created_by, 'INSERT', 'DEPOSIT', NEW.id, JSON_OBJECT('account_no', NEW.account_no, 'customer_id', NEW.customer_id), NOW());
END$$

CREATE TRIGGER tr_deposit_audit_update
AFTER UPDATE ON deposit
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (user_id, action_type, entity_type, entity_id, old_values, new_values, timestamp)
    VALUES (NEW.modified_by, 'UPDATE', 'DEPOSIT', NEW.id, 
            JSON_OBJECT('account_status', OLD.account_status, 'balance', OLD.balance),
            JSON_OBJECT('account_status', NEW.account_status, 'balance', NEW.balance), NOW());
END$$

DELIMITER ; 