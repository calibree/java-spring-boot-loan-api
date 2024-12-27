CREATE TABLE CUSTOMER (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL,
                          surname VARCHAR(50) NOT NULL,
                          credit_limit DOUBLE NOT NULL,
                          used_credit_limit DOUBLE DEFAULT 0
);

CREATE TABLE LOAN (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      customer_id BIGINT NOT NULL,
                      loan_amount DOUBLE NOT NULL,
                      number_of_installments INT NOT NULL,
                      create_date DATE NOT NULL,
                      is_paid BOOLEAN DEFAULT FALSE,
                      FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id) ON DELETE CASCADE
);

CREATE TABLE LOAN_INSTALLMENT (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  loan_id BIGINT NOT NULL,
                                  amount DOUBLE NOT NULL,
                                  paid_amount DOUBLE DEFAULT 0,
                                  due_date DATE NOT NULL,
                                  payment_date DATE DEFAULT NULL,
                                  is_paid BOOLEAN DEFAULT FALSE,
                                  FOREIGN KEY (loan_id) REFERENCES LOAN(id) ON DELETE CASCADE
);
