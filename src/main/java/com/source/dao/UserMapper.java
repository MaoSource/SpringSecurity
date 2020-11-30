package com.source.dao;

import com.source.bean.MyUser;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<MyUser> {

    @Select("select * from sys_user where username = #{username}")
    MyUser findByUserName(String username);

}
