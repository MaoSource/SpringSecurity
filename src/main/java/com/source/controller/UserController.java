package com.source.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/15:38
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add")
    public String add(){
        return "user/add";
    }
    @RequestMapping("/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/delete")
    public String delete(){
        return "user/delete";
    }

    @RequestMapping("/list")
    public String list(){
        return "user/list";
    }
}
