package com.source.dao;

import com.source.bean.Permission;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {

    @Select("select permission.*\n" +
            "           from\n" +
            "             sys_user user\n" +
            "             inner join sys_user_role user_role on user.id = user_role.user_id\n" +
            "             inner join sys_role_permission role_permission on user_role.role_id = role_permission.role_id\n" +
            "             inner join sys_permission permission on role_permission.perm_id = permission.id\n" +
            "             where user.username = #{username};")
    List<Permission> selectByUsername(String username);

}
