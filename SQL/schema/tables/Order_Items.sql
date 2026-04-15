CREATE TABLE Order_Items(
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id,product_id),
    FOREIGN KEY (order_id) REFERENCES virtusa.orders(order_id),
    FOREIGN KEY (product_id) REFERENCES virtusa.products(product_id)
);