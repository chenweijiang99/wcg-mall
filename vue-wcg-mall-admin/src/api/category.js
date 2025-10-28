import request from '@/utils/request.js'

export const getCategoryListService = ()=> {
    return request.get('/admin/category');
}

export const updateCategoryService = (categoryData)=> {
   
    return request.put('/admin/category',categoryData);
}

export const addCategoryService = (categoryData)=> {
    
    return request.post('/admin/category',categoryData);
}


export const deleteCategoryService = (id)=> {
    return request.delete('/admin/category?id='+id);
}