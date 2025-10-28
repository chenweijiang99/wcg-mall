<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'


const loginData= ref({
    email: '',
    password: '',
})


//定义表单验证规则
// const rules = {
//     email: [
//         { required: true, message: '请输入邮箱', trigger: 'blur' },
//         { type:"email",message: '请输入正确的邮箱格式', trigger: 'blur' }
//     ],
//     password: [
//         { required: true, message: '请输入密码', trigger: 'blur' },
//         { min: 5, max: 16, message: '长度在 5 到 16个字符', trigger: 'blur' }
//     ],
    
// }

import {userLoginService } from '@/api/user'
import { ElMessage } from 'element-plus'

import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
import useUserInfoStore from "@/stores/userInfo.js";
const userInfoStore = useUserInfoStore();
const router = useRouter()
const tokenStore = useTokenStore()
const login = async () => {
    let result = await userLoginService(loginData.value);
    if(result.code === 1){
        ElMessage.success(result.msg?result.msg:'登录成功');
        tokenStore.setToken(result.data);
        router.push('/');
    }else{
        ElMessage.error(result.msg?result.msg:'登录失败');
    }
    
};




</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
           
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off"  >
                <el-form-item>
                    <h1>管理员登录</h1>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input :prefix-icon="User" placeholder="请输入邮箱" v-model="loginData.email"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="loginData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: 
            url('@/assets/背景1.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>