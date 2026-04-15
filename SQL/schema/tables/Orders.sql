create table orders(
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT not null,
    date DATE,
    FOREIGN KEY (customer_id) REFERENCES virtusa.Customers(customer_id)
)
