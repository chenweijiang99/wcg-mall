import request from '@/utils/request.js'
export const getOrderList = ()=> {
    return request.get('/admin/order');
};
export const getOrderDetailList = ()=> {
    return request.get('/admin/order/getOrderDetailList');
};
export const OrderShipping = (orderNumber)=> {
    return request.post('/admin/order/OrderShipping?orderNumber='+orderNumber);
};