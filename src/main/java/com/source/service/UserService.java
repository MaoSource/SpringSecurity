package com.source.service;

import com.source.bean.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/27/14:44
 * @Description:
 */
public interface UserService {

    List<User> findAll();

    User selectByName(String username);
}
