<template>
  <div class="common-layout">
    <el-container class="box">
      <!-- 左侧菜单栏 -->
      <el-aside class="el-aside">
        <el-menu
          active-text-color="#409EFF"
          background-color="#304156"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="rgb(191, 203, 217)"
          :router="true"
        >
          <template v-for="item in asideMenu">
            <!-- 两级菜单 -->
            <template v-if="item.subs">
              <el-sub-menu :index="item.title" :key="item.title">
                <!-- 一级菜单标题 -->
                <template #title>
                  <el-icon><IconMenu /></el-icon>
                  <span>{{ item.title }}</span>
                </template>
                <!-- 二级菜单标题 -->
                <div v-for="subItem in item.subs" :key="subItem.index">
                  <el-menu-item
                    :index="subItem.index"
                    @click="() => handleMenuItem(subItem)"
                    >{{ subItem.title }}</el-menu-item
                  >
                </div>
              </el-sub-menu>
            </template>

            <!-- 一级菜单 -->
            <template v-else>
              <el-menu-item
                :index="item.index"
                :key="item.title"
                @click="() => handleMenuItem(item)"
              >
                <el-icon><IconMenu /></el-icon>
                <span>{{ item.title }}</span>
              </el-menu-item>
            </template>
          </template>
        </el-menu>
      </el-aside>

      <el-container>
        <!-- header头部菜单 -->
        <el-header class="header">
          <el-menu
            class="el-menu-demo"
            mode="horizontal"
            background-color="#fff"
            text-color="#333"
            active-text-color="#ffd04b"
          >
            <el-sub-menu index="2" class="fr">
              <template #title class="fr">我的工作台</template>
              <el-menu-item index="2-3" @click="exitLogin">
                退出登陆
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-header>

        <!-- 主体模块：标签页 + 当前路由内容 -->
        <el-main class="el-main">
          <el-tabs
            v-if="editableTabs.length > 0"
            type="border-card"
            v-model="activeTabName"
            class="demo-tabs"
            @tab-remove="handleRemove"
            @tab-click="handleSwitchRoute"
          >
            <el-tab-pane
              v-for="item in editableTabs"
              :key="item.index"
              :label="item.title"
              :name="item.index"
              :closable="handleisClose(item)"
            >
              <router-view />
            </el-tab-pane>
          </el-tabs>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { sidebar } from "../router/index";
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from "@element-plus/icons-vue";
import { ElMessageBox } from "element-plus";
export default {
  name: "MainLayout",
  mounted() {},
  data() {
    return {
      //当前选项卡
      activeTabName: "home",
      //需要显示的标签数组
      editableTabs: [],
      //左侧菜单选项配置
      asideMenu: sidebar,
    };
  },
  components: {
    Document,
    IconMenu,
    Location,
    Setting,
  },
  watch: {
    activeTabName: function () {
      console.log("高亮的index值", this.activeTabName);
    },
  },
  methods: {
    handleisClose(item) {
      if (item.index == "home") {
        return false;
      }
      return true;
    },
    //点击二级菜单标题 和 没有下一级菜单的标题
    //添加显示的标签
    handleMenuItem(obj) {
      //高亮设置
      this.activeTabName = obj.index;
      let result = this.editableTabs.findIndex((item) => {
        return item.index == obj.index;
      });
      if (result != -1) {
        return;
      }
      this.editableTabs.push(obj);
    },

    //点击切换tab标签，切换组件
    handleSwitchRoute(tabsPaneContext) {
      let tabPaneName = tabsPaneContext.paneName;
      //处理一个特殊情况，首页的index 为 '' ，这里取得值为0
      if (tabPaneName == 0) {
        tabPaneName = "";
      }
      this.$router.push("/" + tabPaneName);
    },

    //(1)移除标签，（2）返回前一个路由
    //删除: 需要当前索引 ，设置路由和高亮，上一个对象的index
    handleRemove(tabPaneName) {
      let tempArr = this.editableTabs;
      let eleIndex = this.editableTabs.findIndex((item) => {
        return item.index == tabPaneName;
      });
      //上一个路由的index
      let routeIndex;
      for (let p in tempArr) {
        if (tempArr[p].index == tabPaneName) {
          routeIndex = tempArr[p - 1].index;
        }
      }
      //高亮和退到前一个路由
      this.activeTabName = routeIndex;
      this.$router.push("/" + routeIndex);
      //删除当前关闭的路由标签
      this.editableTabs.splice(eleIndex, 1);
    },

    //退出登陆
    exitLogin() {
      ElMessageBox.confirm("真的要退出登陆吗?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          localStorage.removeItem("isLogin");
          localStorage.removeItem("token");
          this.$router.push("/login");
        })
        .catch(() => {
          //取消：就不做任何提示了
        });
    },
  },
};
</script>

<style scoped>
.logoBox {
  position: absolute;
  top: 18px;
  left: 30px;
  font-size: 24px;
  color: #fff;
  background: #fff;
}

.box {
  width: 100vw;
  height: 100vh;
}
.header {
  padding: 0;
  height: 58px;
}
/* 将消息中心和我的控制台摆放在最右侧 */
.el-menu--horizontal {
  justify-content: flex-end;
}

/* 去除默认的边框样式 */
.el-header .el-menu {
  border-bottom: none;
}
.el-aside .el-menu {
  border-right: none;
}

.el-main {
  background-color: #e9eef3;
}

.el-aside {
  width: 200px;
  background: #304156;

  /* padding-top: 58px; */
}

.el-aside::-webkit-scrollbar {
  display: none;
}

.el-tabs--border-card .el-tabs__content {
  padding: 0;
}
.el-tabs--border-card > .el-tabs__content {
  padding: 0px;
}
.el-main .el-tabs__content {
  padding: 0 !important;
}
.demo-tabs > .el-tabs__content {
  background-color: brown;
  padding: 0 !important;
}
</style>
