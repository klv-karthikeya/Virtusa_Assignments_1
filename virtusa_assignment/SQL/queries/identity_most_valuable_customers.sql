select
    c.customer_id,
    c.name,
    SUM(oi.quantity * p.price) as total_spent
from Customers c
join orders o on c.customer_id = o.customer_id
join Order_Items oi on o.order_id = oi.order_id
join products p on oi.product_id = p.product_id
group by c.customer_id, c.name
having SUM(oi.quantity * p.price) = (
        select max(total_amt)
        from (
            select SUM(oi2.quantity * p2.price) as total_amt
            from Customers c2
            join orders o2 on c2.customer_id = o2.customer_id
            join Order_Items oi2 on o2.order_id = oi2.order_id
            join products p2 on oi2.product_id = p2.product_id
            group by c2.customer_id
        ) t
);