import request from '@/utils/request.js'

export const getBrandListService = ()=> {
    return request.get('/admin/brand');
}

export const updateBrandService = (brandData)=> {
    return request.put('/admin/brand',brandData);
}
export const addBrandService = (brandData)=> {
    return request.post('/admin/brand',brandData);
}
export const deleteBrandService = (id)=> {
    return request.delete('/admin/brand?id='+id);
}