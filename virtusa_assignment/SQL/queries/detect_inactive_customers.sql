select c.customer_id, c.name
from customers c
where c.customer_id not in (
    select o.customer_id
    from orders o
);