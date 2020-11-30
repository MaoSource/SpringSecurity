package com.source.service;

import com.source.bean.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/30/10:59
 * @Description:
 */
public interface PermissionService {

    List<Permission> selectByUserName(String username);
}
