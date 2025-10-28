import request from '@/utils/request.js'
export const userAddBlogService = (data)=> {
    const params = new URLSearchParams();
    for(let key in data){
        params.append(key,data[key]);
    }
  return request.post('/user/blog', params)
}

export const userGetBlogListService = ()=> {
  return request.get('/user/blog')
}

export const userGetBlogListPageService = (pageNum,data)=> {
  return request.post('/user/blog/page?pageNum='+pageNum,data);
}

export const userGetHotBlogService = ()=> {
  return request.get('/user/blog/getHotBlog');
}
export const userGetBlogListByUserIdService = ()=> {
  return request.get('/user/blog/userBlog')
}
export const userDelBlogService = (id) =>{
  return request.delete('/user/blog?id='+id)
}
export const userUpdateBlogService = (data)=> {
  return request.put('/user/blog', data)
}

export const userGetBlogByIdService = (id) =>{
  return request.get('/user/blog/getBlogDetail?id='+id)
}
export const userGetLatestBlogService = ()=> {
  return request.get('/user/blog/getLatestBlog')
}
export const userGetRelatedBlogService = (id) =>{
  return request.get('/user/blog/getRelatedBlog?id='+id)
}
export const userGetBlogCommentListService = (blogId) =>{
  return request.get('/user/blog/getComments?blogId='+blogId)
}
export const userAddBlogCommentsService = (blogId,content)=> {
  return request.post('/user/blog/addComments?blogId='+blogId+'&content='+content)
}
export const userDeleteBlogCommentsService = (blogId,id) =>{
  return request.delete('/user/blog/deleteComments?blogId='+blogId+'&id='+id)
}