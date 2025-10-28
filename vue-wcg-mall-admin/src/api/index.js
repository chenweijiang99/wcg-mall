import request from '@/utils/request.js'

export const getIndexSliderListService = ()=> {
    return request.get('/admin/index/getIndexSlider');
}

export const addIndexSliderService = (url)=>{
    return request.post('/admin/index/addIndexSlider?url='+url)
}

export const deleteIndexSliderService = (id)=>{
    return request.delete('/admin/index/deleteIndexSlider?id='+id)
}
export const getShopSliderListService = ()=>{
    return request.get('/admin/index/getShopSlider')
}
export const addShopSliderService = (url)=>{
    return request.post('/admin/index/addShopSlider?url='+url)
}
export const deleteShopSliderService = (id)=>{
    return request.delete('/admin/index/deleteShopSlider?id='+id)
}