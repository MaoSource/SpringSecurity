package com.source.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.source.bean.User;
import com.source.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Map map = new HashMap<>();
//        获取当前用户信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null){
            if (principal instanceof UserDetails){
                UserDetails userDetails = (UserDetails)principal;
                String username = userDetails.getUsername();
                User user = userService.selectByName(username);
                map.put("User",user);
            }
        }

        map.put("Success",true);

        String s = objectMapper.writeValueAsString(map);
        httpServletResponse.setContentType("text/json;charset=utf8");
        httpServletResponse.getWriter().write(s);
    }
}
