import request from '@/utils/request.js'
import exp from 'constants';

export const userLoginService = (loginData)=>{
    const params = new URLSearchParams();
    for(let key in loginData){
        params.append(key,loginData[key]);
    }
    return request.post('/admin/user/login',params);
}

export const userInfoService = ()=>{
    return request.get('/admin/user');
}

export const getUserListService = ()=>{
    return request.get('/admin/user/getUserList');
}

export const resetUserPasswordService = (id)=>{
    return request.post('/admin/user/resetPassword?id='+id);
}
export const deleteUserService = (id)=>{
    return request.delete('/admin/user?id='+id);
}