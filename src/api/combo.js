import request from "../utils/request";

export function comboList(params) {
  return request({
    url: "combo/list",
    method: "get",
    params,
  });
}

export function update_publish_status(data) {
  return request({
    url: "combo/update_publish_status",
    method: "post",
    params: data,
  });
}

export function delCombo(id) {
  return request({
    url: "combo/delete",
    method: "post",
    params: {
      comboIds: id,
      deleteStatus: 1,
    },
  });
}

export function createCombo(data) {
  return request({
    url: "/combo/add",
    method: "post",
    data: data,
  });
}

export function updateCombo(data) {
  return request({
    url: "/combo/update",
    method: "post",
    data: data,
  });
}

export function get_combo(id) {
  return request({
    url: "/combo/get_combo",
    params: {
      comboId: id,
    },
  });
}
