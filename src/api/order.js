import request from "../utils/request";

export function orderList(params) {
  return request({
    url: "order/list",
    params,
  });
}
