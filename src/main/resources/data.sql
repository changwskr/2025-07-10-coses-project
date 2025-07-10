-- Banking System Initial Data
-- This script will be executed automatically when the application starts

-- Insert sample customers
INSERT INTO customers (customer_id, first_name, last_name, email, phone_number, date_of_birth, address, status, created_at, bank_code, branch_code, customer_type, kyc_status) VALUES
('CUST1234567890', 'John', 'Doe', 'john.doe@email.com', '010-1234-5678', '1990-01-15', 'Seoul, South Korea', 'ACTIVE', CURRENT_TIMESTAMP(), '001', '001', 'INDIVIDUAL', 'COMPLETED'),
('CUST0987654321', 'Jane', 'Smith', 'jane.smith@email.com', '010-8765-4321', '1985-05-20', 'Busan, South Korea', 'ACTIVE', CURRENT_TIMESTAMP(), '001', '002', 'INDIVIDUAL', 'COMPLETED'),
('CUST1122334455', 'Mike', 'Johnson', 'mike.johnson@email.com', '010-1122-3344', '1992-08-10', 'Incheon, South Korea', 'ACTIVE', CURRENT_TIMESTAMP(), '002', '001', 'INDIVIDUAL', 'PENDING'),
('CUST5566778899', 'Sarah', 'Wilson', 'sarah.wilson@email.com', '010-5566-7788', '1988-12-03', 'Daegu, South Korea', 'ACTIVE', CURRENT_TIMESTAMP(), '002', '003', 'INDIVIDUAL', 'COMPLETED'),
('CUST9988776655', 'David', 'Brown', 'david.brown@email.com', '010-9988-7766', '1995-03-25', 'Daejeon, South Korea', 'ACTIVE', CURRENT_TIMESTAMP(), '003', '001', 'INDIVIDUAL', 'COMPLETED');

-- Insert sample cash cards
INSERT INTO cash_cards (card_number, customer_id, card_type, balance, status, bank_code, branch_code, created_at, expiry_date, daily_limit, monthly_limit) VALUES
('1234567890123456', 'CUST1234567890', 'DEBIT', 1000000.00, 'ACTIVE', '001', '001', CURRENT_TIMESTAMP(), DATEADD('YEAR', 3, CURRENT_TIMESTAMP()), 1000000.00, 10000000.00),
('2345678901234567', 'CUST0987654321', 'CREDIT', 500000.00, 'ACTIVE', '001', '002', CURRENT_TIMESTAMP(), DATEADD('YEAR', 3, CURRENT_TIMESTAMP()), 1000000.00, 10000000.00),
('3456789012345678', 'CUST1122334455', 'DEBIT', 250000.00, 'ACTIVE', '002', '001', CURRENT_TIMESTAMP(), DATEADD('YEAR', 3, CURRENT_TIMESTAMP()), 1000000.00, 10000000.00),
('4567890123456789', 'CUST5566778899', 'CREDIT', 750000.00, 'ACTIVE', '002', '003', CURRENT_TIMESTAMP(), DATEADD('YEAR', 3, CURRENT_TIMESTAMP()), 1000000.00, 10000000.00),
('5678901234567890', 'CUST9988776655', 'DEBIT', 300000.00, 'ACTIVE', '003', '001', CURRENT_TIMESTAMP(), DATEADD('YEAR', 3, CURRENT_TIMESTAMP()), 1000000.00, 10000000.00);

-- Insert sample transactions
INSERT INTO transactions (transaction_id, card_id, transaction_type, amount, balance_before, balance_after, description, status, transaction_date, created_at) VALUES
('TXN1234567890', 1, 'DEPOSIT', 500000.00, 500000.00, 1000000.00, 'Initial deposit', 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('TXN2345678901', 2, 'DEPOSIT', 300000.00, 200000.00, 500000.00, 'Salary deposit', 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('TXN3456789012', 3, 'WITHDRAWAL', 50000.00, 300000.00, 250000.00, 'ATM withdrawal', 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('TXN4567890123', 4, 'TRANSFER', 100000.00, 850000.00, 750000.00, 'Transfer to friend', 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('TXN5678901234', 5, 'DEPOSIT', 200000.00, 100000.00, 300000.00, 'Bonus deposit', 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()); 