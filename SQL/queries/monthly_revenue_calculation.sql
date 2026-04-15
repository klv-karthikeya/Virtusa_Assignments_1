select DATE_FORMAT(vo.date,'%m-%Y') as month,SUM(vp.price * voi.quantity) as total_revenue
from virtusa.products vp
join virtusa.order_items voi on vp.product_id = voi.product_id
join virtusa.orders vo on vo.order_id = voi.order_id
group by DATE_FORMAT(vo.date,'%m-%Y')
order by total_revenue desc;