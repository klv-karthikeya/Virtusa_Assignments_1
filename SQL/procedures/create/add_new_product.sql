DELIMITER $$
create procedure AddProduct(
    p_product_name varchar(100),
    p_category varchar(100),
    p_price decimal
)
begin
    insert into products(name,category,price)
        values(p_product_name,p_category,p_price);
end $$
delimiter ;