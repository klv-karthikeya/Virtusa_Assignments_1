select p.category,SUM(oi.quantity) as total_quantity,sum(oi.quantity * p.price) as total_revenue
from products p
join order_items oi on oi.product_id = p.product_id
join orders o on o.order_id = oi.order_id
group by p.category
order by total_revenue desc;