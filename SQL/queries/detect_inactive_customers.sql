select vc.customer_id, vc.name
from virtusa.customers vc
where vc.customer_id not in (
    select vo.customer_id
    from virtusa.orders vo
);