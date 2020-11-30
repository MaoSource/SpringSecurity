package com.source.security;

import com.source.bean.MyUser;
import com.source.bean.Permission;
import com.source.service.PermissionService;
import com.source.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/30/10:44
 * @Description:
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = Logger.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("当前用户：" + s );

        //根据用户名查询用户信息
        MyUser myUser = userService.findUsername(s);

        if(myUser!=null) {
            //根据用户名查询当前用户所有权限
            List<Permission> permissions = permissionService.selectByUserName(s);
            //authorities：存放所有用户权限
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            for (Permission perm : permissions) {
                GrantedAuthority authority = new SimpleGrantedAuthority(perm.getPermTag());
                authorities.add(authority);
            }
            //把所有权限赋值给user
            myUser.setAuthorities(authorities);

            logger.info(myUser.toString());
        }

//        UserDetails user = User.withUsername("root").password("123").authorities("ROLE_ALL").build();
        return myUser;
    }
}
