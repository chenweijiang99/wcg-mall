<script setup>
import headerView from "../components/header.vue";
import footerView from "../components/footer.vue";
import { useScrollToTop } from "@/assets/base.js";
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";
const { showLoader, backtopStyle, handleMouseEnter, handleMouseLeave } = useScrollToTop();

import emitter from "@/event/eventBus.js";
//验证码图片
import Img1 from "@/assets/images/code/code1.jpg";
import Img2 from "@/assets/images/code/code2.jpg";
import Img3 from "@/assets/images/code/code3.jpg";
import Img4 from "@/assets/images/code/code4.jpg";
const captchaImages = [Img1, Img2, Img3, Img4];

//引入'vue3-puzzle-vcode'插件
import Vcode from "vue3-puzzle-vcode";

//验证码模态框是否出现，默认不展示
const isShow = ref(false);

//用户通过了验证
const success = () => {
  isShow.value = false;
  login();
};
//用户点击遮罩层，关闭模态框
const close = () => {
  isShow.value = false;
};

const loginData = ref({
  email: "",
  password: "",
});

import { useTokenStore } from "@/stores/token.js";
import { useRouter } from "vue-router";

const router = useRouter();
const tokenStore = useTokenStore();
import { userLoginService, userGetJuHeAuthService } from "@/api/user.js";
// 打开验证组件
const validate = () => {
  if (
    emailError.value != "" ||
    passwordError.value != "" ||
    loginData.value.email == "" ||
    loginData.value.password == ""
  ) {
    ElMessage({
      showClose: true,
      type: "error",
      message: "请检查邮箱或密码是否符合规则",
      plain: true,
    });
    return;
  }
  //展现验证码模态框
  isShow.value = true;
};
//登录
const login = async () => {
  let result = await userLoginService(loginData.value);
  if (result.code === 1) {
    ElMessage({
      showClose: true,
      type: "success",
      message: result.msg ? result.msg : "登录成功",
      plain: true,
    });
    tokenStore.setToken(result.data);
    emitter.emit("refresh");
    emitter.emit("login");
    router.push("/index");
  } else if (result.code === 0) {
    ElMessage({
      showClose: true,
      type: "error",
      message: result.msg ? result.msg : "登录失败",
      plain: true,
    });
  }
};
//邮箱错误信息
const emailError = ref("");
const validateEmail = () => {
  const email = loginData.value.email;
  if (!email) {
    emailError.value = "邮箱不能为空";
  } else if (!/\S+@\S+\.\S+/.test(email)) {
    emailError.value = "请输入有效的邮箱地址";
  } else {
    emailError.value = "";
  }
};
//密码错误信息
const passwordError = ref("");
const validatePassword = () => {
  const password = loginData.value.password;
  if (!password) {
    passwordError.value = "请输入密码";
  } else if (password.length < 6 || password.length > 12) {
    passwordError.value = "密码长度在6-12个字符之间";
  } else {
    passwordError.value = "";
  }
};

const loginWithJuHe = (type) => {
  userGetJuHeAuthService(type).then((res) => {
    if (res.code === 200) {
      window.open(res.logurl, "_self");
    }
  });
};

onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search);
  const code = urlParams.get("code");
  const message = urlParams.get("message");
  if (code == 200 && message) {
    ElMessage({
      showClose: true,
      type: "success",
      message: message,
      plain: true,
    });
  } else if (message) {
     ElMessage({
      showClose: true,
      type: "error",
      message: message,
      plain: true,
    });
  }
});
</script>
<template>
  <div>
    <!-- <headerView /> -->
  </div>

  <!-- breadcrumb -->
  <div class="container header-mt">
    <div class="row">
      <div class="col-12">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb custom-breadcrumb">
            <li class="breadcrumb-item">
              <router-link to="/index"> 主页 </router-link>
            </li>
            <li class="breadcrumb-item active">登录</li>
          </ol>
        </nav>
      </div>
    </div>
  </div>
  <!-- end breadcrumb -->
  <!-- main content -->
  <div class="main-content pb-100">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 col-md-8 col-12 mx-auto">
          <div class="custom-form custom-form--box">
            <h3 class="custom-form__title">登录您的账户</h3>
            <form :model="loginData" @submit.prevent="validate">
              <div class="form-group custom-form__input">
                <label>邮箱地址</label>
                <input
                  id="email"
                  type="email"
                  class="form-control ltr"
                  placeholder="输入您的邮箱"
                  v-model="loginData.email"
                  @blur="validateEmail"
                />
                <small v-if="emailError">{{ emailError }}</small>
              </div>
              <div class="form-group custom-form__input">
                <label>密码</label>
                <div class="input-box password-box">
                  <input
                    type="password"
                    class="form-control ltr"
                    id="password"
                    placeholder="输入您的密码"
                    v-model="loginData.password"
                    @blur="validatePassword"
                  />
                  <small v-if="passwordError">{{ passwordError }}</small>
                </div>
              </div>

              <Vcode
                :show="isShow"
                @success="success"
                @close="close"
                @fail="fail"
                :imgs="captchaImages"
              ></Vcode>

              <div class="custom-form__btn">
                <button class="btn submit-btn">登录</button>
              </div>

              <div class="custom-form__footer">
                <!-- login/sign up with social media -->
                <div class="devider">
                  <span>其他账户登录</span>
                </div>
                <div class="social-login">
                  <ul>
                    <li>
                      <a
                        href="javascript:void(0)"
                        class="social-icon tr-icon"
                        @click="loginWithJuHe(1)"
                        ><i class="bi-tencent-qq"></i
                      ></a>
                    </li>

                    <li>
                      <a
                        href="javascript:void(0)"
                        class="social-icon gr-icon"
                        @click="loginWithJuHe(2)"
                        ><i class="bi-wechat"></i
                      ></a>
                    </li>
                    <li>
                      <a
                        href="javascript:void(0)"
                        class="social-icon tr-icon"
                        @click="loginWithJuHe(3)"
                        ><i class="bi-alipay"></i
                      ></a>
                    </li>
                    <li>
                      <a
                        href="javascript:void(0)"
                        class="social-icon gl-icon"
                        @click="loginWithJuHe(4)"
                        ><i class="bi-sina-weibo"></i
                      ></a>
                    </li>
                  </ul>
                </div>

                <!-- form footer -->
                <div class="custom-form__footer--link">
                  <h6>您还没有账户?</h6>
                  <router-link to="/register">
                    <a href="javascript:void(0)" class="btn">注册</a>
                  </router-link>
                </div>

                <router-link to="/resetPassword">
                  <a href="">忘记密码?</a>
                </router-link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- end main content -->

  <!-- scroll up btn -->
  <el-backtop
    :right="100"
    :bottom="100"
    :style="backtopStyle"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  />
  <!-- end scroll up btn -->

  <!-- loader -->
  <div class="loader" v-if="showLoader">
    <div class="spinner">
      <div class="cube1"></div>
      <div class="cube2"></div>
    </div>
  </div>
  <!-- end loader -->

  <!-- <footerView /> -->
</template>

<style scoped>
@import "@/assets/main.css";

a {
  text-decoration: none;
}
small {
  margin-left: 1rem;
  color: darkgrey;
}
</style>
