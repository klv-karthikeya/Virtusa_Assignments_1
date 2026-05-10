DELIMITER $$
create procedure RegisterCustomer(
    IN p_name VARCHAR(100),
    IN p_city VARCHAR(100)
)
BEGIN
    insert into Customers(name,city)
    values (p_name,p_city);
end $$
DELIMITER ;