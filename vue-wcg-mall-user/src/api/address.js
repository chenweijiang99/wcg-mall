import request from '@/utils/request.js'

export const userGetAddressListService = ()=> {
    return request.get('/user/address');
}

export const userAddAddressService = (data)=> {
    return request.post('/user/address',data);
}

export const userUpdateAddressService = (data)=> {
    return request.put('/user/address',data);
}

export const userDeleteAddressService = (id)=> {
    return request.delete('/user/address?id='+id);
}