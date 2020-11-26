package com.source.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/16:14
 * @Description:
 */
@Configuration
//@EnableWebSecurity  //启动spring security过滤器链
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //    自定义用户信息
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("root").password("123").authorities("ROLE_ALL").build());
        manager.createUser(User.withUsername("admin").password("123").authorities("ROLE_ADD").build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    // 该方法的作用就是代替之前的：<security:http>
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/add").hasAnyAuthority("ROLE_ADD","ROLE_ALL")
                .antMatchers("/user/delete").hasAnyAuthority("ROLE_ADD","ROLE_ALL")
                .antMatchers("/user/update").hasAnyAuthority("ROLE_UPDATE","ROLE_ALL")
                .antMatchers("/user/list").hasAnyAuthority("ROLE_LIST","ROLE_ALL")
                .antMatchers("/user/**").hasAnyAuthority()  //所有/user/**的请求必须通过验证
                .anyRequest().permitAll()   //出了/user/**,其他的可以访问
                .and()
                .formLogin()
                .loginPage("/login")    //添加自定义登录页面
                .successForwardUrl("/login-success")   //自定义登录成功地址
        .and()
                .csrf().disable();  //关闭跨载的功能
    }
}
