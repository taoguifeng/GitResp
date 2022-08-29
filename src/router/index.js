import { createWebHistory, createRouter } from "vue-router";

const routes = [
  {
    path: "/login",
    name: "login",
    component: () => import("../page/login/Login.vue"),
  },
  {
    path: "/",
    name: "Layout",
    component: () => import("../page/MainLayout.vue"),
    // 嵌套路由
    children: [
      {
        // 这里不设置值，是把main作为默认页面
        path: "",
        redirect: "/dishList",
      },
      {
        path: "dishList",
        name: "DishList",
        component: () => import("../page/dish/dish/index.vue"),
      },
      {
        path: "dishForm",
        name: "DishForm",
        component: () => import("../page/dish/dish/form.vue"),
      },
      {
        path: "comboList",
        name: "ComboList",
        component: () => import("../page/dish/combo/List.vue"),
      },
      {
        path: "comboForm",
        name: "ComboForm",
        component: () => import("../page/dish/combo/Form.vue"),
      },
      {
        path: "orderList",
        name: "OrderList",
        component: () => import("../page/order/List.vue"),
      },
      {
        path: "storeList",
        name: "StoreList",
        component: () => import("../page/store/List.vue"),
      },
      {
        path: "storeForm",
        name: "StoreForm",
        component: () => import("../page/store/Form.vue"),
      },
      {
        path: "adminList",
        name: "AdminList",
        component: () => import("../page/authority/AdminList.vue"),
      },
      {
        path: "adminForm",
        name: "AdminForm",
        component: () => import("../page/authority/AdminForm.vue"),
      },
    ],
  },
];

const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHistory("/"),
  routes, // `routes: routes` 的缩写
});

//进行路由拦截：当没有登陆标识，直接打回登陆页面，如何避免退回到 登陆页呢？
router.beforeEach((to, from, next) => {
  if (to.path == "/login") {
    next();
  } else {
    let token = localStorage.getItem("isLogin");
    if (token) {
      next();
    } else {
      next("/login");
    }
  }
});

export default router;

export const sidebar = [
  {
    title: "菜品",
    index: "DishManager",
    subs: [
      {
        title: "菜品列表",
        index: "dishList",
      },
      {
        title: "添加菜品",
        index: "dishForm",
      },
    ],
  },
  {
    title: "套餐",
    index: "ComboManager",
    subs: [
      {
        title: "套餐列表",
        index: "comboList",
      },
      {
        title: "添加套餐",
        index: "comboForm",
      },
    ],
  },

  {
    title: "订单",
    subs: [
      {
        title: "订单列表",
        index: "orderList",
      },
      {
        title: "待支付订单",
        index: "orderList?orderType=1",
      },
      {
        title: "确认中订单",
        index: "orderList?orderType=2",
      },
      {
        title: "备餐中订单",
        index: "orderList?orderType=3",
      },
      {
        title: "待用餐订单",
        index: "orderList?orderType=4",
      },
      {
        title: "待取餐订单",
        index: "orderList?orderType=5",
      },
      {
        title: "配送中订单",
        index: "orderList?orderType=6",
      },
      {
        title: "已完成订单",
        index: "orderList?orderType=7",
      },
      {
        title: "取消确认中订单",
        index: "orderList?orderType=8",
      },
      {
        title: "已取消订单",
        index: "orderList?orderType=9",
      },
    ],
  },
  {
    title: "店铺",
    subs: [
      {
        title: "店铺列表",
        index: "storeList",
      },
      {
        title: "添加店铺",
        index: "storeForm",
      },
    ],
  },
  {
    title: "权限",
    subs: [
      {
        title: "用户管理",
        index: "adminList",
      },
      {
        title: "添加用户",
        index: "adminForm",
      },
    ],
  },
];
