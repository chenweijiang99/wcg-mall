<script setup>
import { ref } from "vue";
import avatar from "@/assets/default.png";
import { userInfoService } from "@/api/user.js";
import useUserInfoStore from "@/stores/userInfo.js";
import { useTokenStore } from "@/stores/token.js";
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();


const userInfo = ref({});
const getUserInfo = async () => {
  const res = await userInfoService();
  userInfo.value = res.data;
  userInfoStore.setInfo(res.data);
};
getUserInfo();
//条目杯点击后调用函数
import { useRouter } from "vue-router";
import { ElMessageBox, ElMessage } from "element-plus";
import { onMounted } from "vue";
const router = useRouter();
const handelCommand = (command) => {
  if (command === "logout") {
    ElMessageBox.confirm("您确认要退出吗", "温馨提示", {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        //退出登录
        //清空pinia
        tokenStore.removeToken();
        userInfoStore.removeInfo();
        router.push("/login");
        ElMessage({
          type: "success",
          message: "退出登录成功",
        });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "用户取消退出",
        });
      });
  } else {
    router.push("/user/" + command);
  }
};

</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px"> 
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#fff"
        router
        default-active="/"
      >
      <el-menu-item index="/" >
        <el-icon><HomeFilled /></el-icon>
        <span>轮播图管理</span>
        </el-menu-item>
      <el-menu-item index="/productManager" >
        <el-icon><GoodsFilled /></el-icon>
        <span>商品管理</span>
        </el-menu-item>
        
        <el-menu-item index="/orderManager">
          <el-icon><Ticket /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="/userManager">
          <el-icon><UserFilled /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/blogManager">
          <el-icon><Comment /></el-icon>
          <span>博客管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>
          管理员：<strong>{{ userInfo.nickName }}</strong>
        </div>
        <!-- 下拉菜单 -->
        <el-dropdown placement="bottom-end" @command="handelCommand">
          <span class="el-dropdown__box">
            <el-avatar
              :src="
                userInfo.avatar ? userInfo.avatar : avatar
              "
            />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <!-- <el-dropdown-item command="info" :icon="User"
                >基本资料</el-dropdown-item
              >
              <el-dropdown-item command="avatar" :icon="Crop"
                >更换头像</el-dropdown-item
              >
              <el-dropdown-item command="resetPassword" :icon="EditPen"
                >重置密码</el-dropdown-item
              > -->
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
        <router-view></router-view>
      
      </el-main>
      <!-- 底部区域 -->
      <el-footer>文创购 ©2023 Created by chenweijiang</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url("@/assets/logo.jpg") no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>