import request from '@/utils/request.js'

export const getOLList = ()=> {
    return request.get('/admin/index/getOL');
}
export const addOL = (id)=> {
    return request.post('/admin/index/setAsOL?id='+id);
}
export const deleteOL = (id)=> {
    return request.post('/admin/index/unSetAsOL?id='+id);
}