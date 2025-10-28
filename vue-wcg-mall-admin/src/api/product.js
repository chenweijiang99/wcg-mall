import request from '@/utils/request.js'
import {useTokenStore} from '@/stores/token.js'
export const ArticleCategoryListService = ()=>{
//     const tokenStore = useTokenStore()
//    return request.get('/category',{headers:{'Authorization':tokenStore.token}})
return request.get('/category')
}

export const articleCategoryAddService = (categoryData)=>{
    return request.post('/category',categoryData)
}

export const articleCategoryUpdateService = (categoryData)=>{
    return request.put(`/category`,categoryData)
}
export const articleCategoryDeleteService = (id)=>{
    return request.delete(`/category?id=`+id)
}
export const productListService = ()=>{
    return request.get('/admin/product')
}
export const addProductService = (productData)=>{
    return request.post('/admin/product/addProduct',productData)
}
export const productUpdateService = (productData)=>{
    return request.put('/admin/product',productData)
}
export const productDeleteService = (id)=>{
    return request.delete('/admin/product/delete'+id)
}

export const stopOrStartService = (id)=>{
    return request.put('/admin/product/stopOrStart/'+id)
}