select p.product_id,p.name,SUM(voi.quantity) as total_quantity
from
    virtusa.orders vo
join virtusa.Order_Items voi on vo.order_id = voi.order_id
join virtusa.products p on voi.product_id = p.product_id
group by p.product_id,p.name
having total_quantity = (
    select MAX(ttl_qty)
    from(
        select SUM(quantity) as ttl_qty
        from virtusa.Order_Items
        group by product_id
        ) t
);