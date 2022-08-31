import request from "../utils/request";

export function adminList() {
  return request({
    url: "/admin/list",
    method: "get",
    params: {
      pageNum: 1,
      pageSize: 1000000,
    },
  });
}

export function updateAdminStatus(params) {
  return request({
    url: "/admin/updateStatus",
    method: "post",
    params: params,
  });
}

export function deleteAdmin(id) {
  return request({
    url: "/admin/delete",
    method: "post",
    params: { userId: id },
  });
}

export function getAdmin(username) {
  return request({
    url: "/admin/get",
    method: "get",
    params: {
      username,
    },
  });
}

export function alloc_role(data) {
  return request({
    url: "/admin/alloc_role",
    method: "post",
    data,
  });
}

export function roleList() {
  return request({
    url: "role/list",
    method: "get",
    params: {
      pageNum: 1,
      pageSize: 100000,
    },
  });
}

export function getRoleByAdmin(id) {
  return request({
    url: "/admin/role",
    method: "get",
    params: { userId: id },
  });
}
export function createAdmin(data) {
  return request({
    url: "/admin/add",
    method: "post",
    data: data,
  });
}

export function updateAdmin(data) {
  return request({
    url: "/admin/update",
    method: "post",
    data: data,
  });
}

export function login(username, password) {
  return request({
    url: "/login",
    method: "post",
    // headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: {
      username,
      password,
    },
  });
}
