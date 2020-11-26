package com.source.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/19:16
 * @Description:
 */
@Controller
public class loginInfo {

    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(){
        return "/index";
    }
    @RequestMapping("/403")
    public String FORBIDDEN(){
        return "/403";
    }

    @RequestMapping("/404")
    public String NOT_FOUND(){
        return "/404";
    }

    @RequestMapping("/401")
    public String UNAUTHORIZED(){
        return "/401";
    }

    @RequestMapping("/500")
    public String INTERNAL_SERVER_ERROR(){
        return "/500";
    }

    @RequestMapping("/405")
    public String METHOD_NOT_ALLOWED(){
        return "/405";
    }
}
