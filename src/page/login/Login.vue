<template>
  <div class="login">
    <div class="loginBox">
      <h3>在线点餐系统后厨管理</h3>
      <span class="deadline"></span>
      <div class="form">
        <div class="input">
          <img src="../../assets/login/username.png" alt="" />
          <input type="text" placeholder="请输入用户名" v-model="username" />
        </div>
        <div class="input">
          <img src="../../assets/login/password.png" alt="" />
          <input type="password" placeholder="请输入密码" v-model="password" />
        </div>

        <!-- 登录按钮 -->
        <div class="btn" @click="setFlag">登录</div>
      </div>
    </div>
    <!-- <div class="loginbg2"></div> -->
  </div>
</template>

<script setup>
//导入
import { login } from "../../api/authority";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { ref } from "vue";
import { JSEncrypt } from 'jsencrypt'
const $router = useRouter();

let username = ref();
let password = ref();

const setFlag = () => {
  login(username.value, password.value)
    .then((res) => {
      ElMessage.success("登陆成功");
      localStorage.setItem("isLogin", 1);
      localStorage.setItem("token", res.token);
      $router.push({
        path: "/",
      });
    })
    .catch(() => {
      ElMessage.error("用户名或密码错误，请重新登录");
    });
};
</script>

<style lang="scss">
  @import '../../../scss/login.scss'
  </style>