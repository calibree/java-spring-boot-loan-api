-- Insert data into CUSTOMER table
INSERT INTO CUSTOMER (name, surname, credit_limit, used_credit_limit)
VALUES ('John', 'Doe', 10000, 5000);

INSERT INTO CUSTOMER (name, surname, credit_limit, used_credit_limit)
VALUES ('Alice', 'Smith', 15000, 2000);

INSERT INTO CUSTOMER (name, surname, credit_limit, used_credit_limit)
VALUES ('Bob', 'Brown', 12000, 4000);

-- Insert data into LOAN table
INSERT INTO LOAN (customer_id, loan_amount, number_of_installments, create_date, is_paid)
VALUES (1, 5000, 12, '2024-01-01', FALSE);

INSERT INTO LOAN (customer_id, loan_amount, number_of_installments, create_date, is_paid)
VALUES (2, 2000, 6, '2024-02-01', TRUE);

INSERT INTO LOAN (customer_id, loan_amount, number_of_installments, create_date, is_paid)
VALUES (3, 4000, 10, '2024-03-01', FALSE);


-- Loan 1 Installments (12 installments for loan_id 1)
INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 416.67, '2024-02-01', '2024-02-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 416.67, '2024-03-01', '2024-03-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-04-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-05-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-06-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-07-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-08-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-09-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-10-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-11-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2024-12-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (1, 416.67, 0.00, '2025-01-01', NULL, FALSE);

-- Loan 2 Installments (6 installments for loan_id 2)
INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (2, 333.33, 333.33, '2024-03-01', '2024-03-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (2, 333.33, 333.33, '2024-04-01', '2024-04-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (2, 333.33, 333.33, '2024-05-01', '2024-05-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (2, 333.33, 333.33, '2024-06-01', '2024-06-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (2, 333.33, 333.33, '2024-07-01', '2024-07-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (2, 333.33, 333.33, '2024-08-01', '2024-08-01', TRUE);

-- Loan 3 Installments (10 installments for loan_id 3)
INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 400.00, '2024-04-01', '2024-04-01', TRUE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-05-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-06-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-07-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-08-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-09-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-10-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-11-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2024-12-01', NULL, FALSE);

INSERT INTO LOAN_INSTALLMENT (loan_id, amount, paid_amount, due_date, payment_date, is_paid)
VALUES (3, 400.00, 0.00, '2025-01-01', NULL, FALSE);
