select vp.category,SUM(voi.quantity) as total_quantity,sum(voi.quantity * vp.price) as total_revenue
from virtusa.products vp
join virtusa.order_items voi on voi.product_id = vp.product_id
join virtusa.orders vo on vo.order_id = voi.order_id
group by vp.category
order by total_revenue desc;