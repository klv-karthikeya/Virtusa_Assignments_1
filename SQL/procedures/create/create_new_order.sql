DELIMITER $$
create procedure CreateOrder(
    IN p_customer_id INT
)
BEGIN
    insert into orders(customer_id,date)
        values (p_customer_id,CURDATE());
    select last_insert_id() as order_id;
end $$
delimiter ;