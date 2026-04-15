delimiter $$
create procedure addItems(
    IN p_order_id INT,
    IN p_product INT,
    IN quantity INT
)
BEGIN
    insert into virtusa.Order_Items(order_id,product_id,quantity)
        values(p_order_id,p_product,quantity);

end $$