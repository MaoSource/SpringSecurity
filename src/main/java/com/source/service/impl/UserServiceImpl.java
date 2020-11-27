package com.source.service.impl;

import com.source.bean.User;
import com.source.dao.UserMapper;
import com.source.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/27/14:45
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public User selectByName(String username) {
        User user = new User();
        user.setUsername(username);
        User userInfo = userMapper.selectOne(user);
        return userInfo;
    }
}
