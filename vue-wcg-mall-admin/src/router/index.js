import {createRouter,createWebHistory} from 'vue-router'
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import productManageVue from '@/views/productManager.vue'
import orderManageVue from '@/views/orderManager.vue'
import userManagerVue from '@/views/userManager.vue'
import blogManagerVue from '@/views/blogManager.vue'
import indexManagerVue from '@/views/indexManager.vue'
const routes = [
    {
        path: '/login',
        component: LoginVue
    },
    {
        path: '/',
        component: LayoutVue,
        redirect: '/indexManagerVue',
        children:[
            {path:'/productManager',component:productManageVue},
            {path:'/orderManager',component:orderManageVue},
            {path:'/userManager',component:userManagerVue},
            {path:'/blogManager',component:blogManagerVue},
            {path:'/indexManagerVue',component:indexManagerVue},
            // {path:'/user/info',component:UserInfoVue},
            // {path:'/user/avatar',component:UserAvatarVue},
            // {path:'/user/resetPassword',component:UserResetPasswordVue},
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes:routes
})

export default router