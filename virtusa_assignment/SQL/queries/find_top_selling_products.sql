select p.product_id,p.name,SUM(oi.quantity) as total_quantity
from
    orders o
join Order_Items oi on o.order_id = oi.order_id
join products p on oi.product_id = p.product_id
group by p.product_id,p.name
having total_quantity = (
    select MAX(ttl_qty)
    from(
        select SUM(quantity) as ttl_qty
        from Order_Items
        group by product_id
        ) t
);