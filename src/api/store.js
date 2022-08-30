import request from "../utils/request";

export function storeList() {
  return request({
    url: "store/list",
    method: "Get",
    params: {
      pageNum: 1,
      pageSize: 100000,
    },
  });
}

export function update_working_status(params) {
  return request({
    url: "store/update_working_status",
    method: "post",
    params,
  });
}

export function update_takeout_status(params) {
  return request({
    url: "store/update_takeout_status",
    method: "post",
    params,
  });
}

export function deleteStore(id) {
  return request({
    url: "store/delete",
    method: "post",
    params: {
      storeIds: id,
    },
  });
}
export function createStore(data) {
  return request({
    url: "/store/add",
    method: "post",
    data: data,
  });
}

export function updateStore(data) {
  return request({
    url: "/store/update",
    method: "post",
    data: data,
  });
}

export function getStore(id) {
  return request({
    url: "/store/get?storeId="+id,
    method: 'get', 
  });
}
