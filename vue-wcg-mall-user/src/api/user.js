import request from '@/utils/request.js'

export const userRegisterService = (registerData)=>{
    
    return request.post('/user/user/register',registerData);
}

export const userLoginService = (loginData)=>{
    const params = new URLSearchParams();
    for(let key in loginData){
        params.append(key,loginData[key]);
    }
    return request.post('/user/user/login',params);
}
export const getUserInfoService = ()=>{
    return request.get('/user/user/userInfo');
}

export const logoutService = ()=>{
    return request.post('/user/user/logout');
}

export const userActiveService = (activeData)=>{
    const params = new URLSearchParams();
    for(let key in activeData){
        params.append(key,activeData[key]);
    }
    return request.post('/user/user/activate',params);
}

export const userCheckCode = (checkData)=>{
    const params = new URLSearchParams();
    for(let key in checkData){
        params.append(key,checkData[key]);
    }
    return request.post('/user/user/activateCode',params);
}

export const userGetCode = (email)=>{
    return request.get('/user/user/getCode?email='+email);
}

export const userUpdateService = (updateData)=>{
    return request.post('/user/user/update',updateData);
}

export const userEditePasswordService = (passwordData)=>{
     const params = new URLSearchParams();
    for(let key in passwordData){
        params.append(key,passwordData[key]);
    }
    return request.post('/user/user/editPassword',params);
}

export const userUploadAvatarService = (avatarData)=>{
    return request.post('/user/user/upload',avatarData);
}

export const userGetCodeByRestPwdService = (email)=>{
    return request.get('/user/user/getCodeByResetPwd?email='+email);
}
export const userActivateCodeByRestPwdService = (checkData)=>{
    const params = new URLSearchParams();
    for(let key in checkData){
        params.append(key,checkData[key]);
    }
    return request.post('/user/user/activateCodeByRestPwd',params);
}
export const userResetPasswordService  = (checkData)=>{
    const params = new URLSearchParams();
    for(let key in checkData){
        params.append(key,checkData[key]);
    }
    return request.post('/user/user/resetPassword',params);
}

export const userGetJuHeAuthService = (type)=>{
    return request.get(`/user/user/juhe/getJuHeAuth/${type}`);
}