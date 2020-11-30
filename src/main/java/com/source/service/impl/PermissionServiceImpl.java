package com.source.service.impl;

import com.source.bean.Permission;
import com.source.dao.PermissionMapper;
import com.source.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/30/11:00
 * @Description:
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> selectByUserName(String username) {
        List<Permission> root = permissionMapper.selectByUsername(username);
        return root;
    }
}
