package com.source.service;

import com.source.bean.MyUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/27/14:44
 * @Description:
 */
public interface UserService {

    List<MyUser> findAll();

    MyUser findUsername(String username);
}
