import request from '@/utils/request.js';
export const getBlogListService = ()=> {
    return request.get('/admin/blog');
}
export const deleteBlogService = (id)=> {
    return request.delete('/admin/blog?id='+id);
}