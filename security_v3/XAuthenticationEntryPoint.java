package com.happysnaker.configuration.security;

import com.happysnaker.utils.JSONUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class XAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        System.out.println("身份校验失败: " + authException.getMessage());
        //用户已经登陆了，
//        response.setStatus(403);
        Map map = new HashMap(2);
        map.put("code", 401);
        map.put("msg", "用户信息验证未通过，请重新登陆");
        JSONUtils.writeJSON(response, map);
    }
}
