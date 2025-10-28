import request from '@/utils/request.js'

export const userGetWishListService = ()=> {
   return request.get('/user/wishList');
}

export const userDeleteWishListService = (id)=> {
   return request.delete('/user/wishList?productId=' + id);
}

export const userAddWishListProdcutToCartService = (id)=> {
   return request.post('/user/wishList?productId=' + id);
}
