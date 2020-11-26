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

    @RequestMapping("/login-success")
    public String loginSuccess(){
        return "/index";
    }
    @RequestMapping("/403")
    public String error(){
        return "/403";
    }
}
