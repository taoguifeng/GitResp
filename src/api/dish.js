import { def } from "@vue/shared";
import request from "../utils/request";

export function dishList(params) {
  return request({
    url: "/dish/list",
    method: "get",
    params,
  });
}
// 上架接口
export function update_publish_status(data) {
  return request({
    url: "/dish/update_publish_status",
    method: "post",
    params: data,
  });
}

// 推荐接口
export function update_recommend_status(data) {
  return request({
    url: "/dish/update_recommend_status",
    params: data,
    method: "post",
  });
}

// 新品接口
export function update_new_status(data) {
  return request({
    url: "dish/update_new_status",
    method: "post",
    params: data,
  });
}

// 获取所有的分类列表
export function classification_list() {
  return request({
    url: "dish/classification_list",
    method: "get",
  });
}

export function createDish(data) {
  return request({
    url: "/dish/add",
    method: "post",
    data: data,
  });
}

export function updateDish(data) {
  return request({
    url: "/dish/update",
    method: "post",
    data: data,
  });
}

export function getDish(id) {
  return request({
    url: `dish/get`,
    params: {
      dishId: id,
    },
  });
}
