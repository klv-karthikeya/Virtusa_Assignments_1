select
    vc.customer_id,
    vc.name,
    SUM(voi.quantity * p.price) as total_spent
from virtusa.Customers vc
join virtusa.orders vo on vc.customer_id = vo.customer_id
join virtusa.Order_Items voi on vo.order_id = voi.order_id
join virtusa.products p on voi.product_id = p.product_id
group by vc.customer_id, vc.name
having SUM(voi.quantity * p.price) = (
        select max(total_amt)
        from (
            select SUM(voi2.quantity * p2.price) as total_amt
            from virtusa.Customers vc2
            join virtusa.orders vo2 on vc2.customer_id = vo2.customer_id
            join virtusa.Order_Items voi2 on vo2.order_id = voi2.order_id
            join virtusa.products p2 on voi2.product_id = p2.product_id
            group by vc2.customer_id
        ) t
);