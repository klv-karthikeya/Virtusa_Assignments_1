select DATE_FORMAT(o.date,'%m-%Y') as month,SUM(p.price * oi.quantity) as total_revenue
from products p
join order_items oi on p.product_id = oi.product_id
join orders o on o.order_id = oi.order_id
group by DATE_FORMAT(o.date,'%m-%Y')
order by total_revenue desc;