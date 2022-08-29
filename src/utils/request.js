import axios from "axios";

const request = axios.create({
  baseURL: "http://localhost:8089",
});

request.interceptors.request.use((config) => {
  config.headers["Authorization"] = localStorage.getItem("token");

  if (config.method == "post") {
    config.headers["Content-Type"] = "application/json;charset=UTF-8";
  }
  return config;
});

request.interceptors.response.use((response) => {
  const res = response.data;
  if (res.code && res.code !== 200) {
    console.log(res.msg);
    return Promise.reject(res.msg);
  } else {
    return res;
  }
});

export default request;
