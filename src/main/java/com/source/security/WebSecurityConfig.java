package com.source.security;

import com.source.code.ValidateCodeFilter;
import com.source.handler.MyAuthenticationFailureHandler;
import com.source.handler.MyAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;


    //    自定义用户信息
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("root").password("123").authorities("ROLE_ALL").build());
//        manager.createUser(User.withUsername("admin").password("123").authorities("ROLE_ADD").build());
//        return manager;
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 该方法的作用就是代替之前的：<security:http>
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加验证码校验过滤器
                .formLogin()
                .loginPage("/login")    //添加自定义登录页面
                .successForwardUrl("/login-success")   //自定义登录成功地址
                .successHandler(authenticationSucessHandler) // 处理登录成功
                .failureHandler(authenticationFailureHandler) // 处理登录失败
                .and()
                .authorizeRequests()
                .antMatchers("/user/add").hasAnyAuthority("ROLE_ADD","ROLE_ALL")
                .antMatchers("/user/delete").hasAnyAuthority("ROLE_ADD","ROLE_ALL")
                .antMatchers("/user/update").hasAnyAuthority("ROLE_UPDATE","ROLE_ALL")
                .antMatchers("/user/list").hasAnyAuthority("ROLE_LIST","ROLE_ALL")
                .antMatchers("/user/**").hasAnyAuthority()  //所有/user/**的请求必须通过验证
                .antMatchers("/js/**").permitAll()
                .anyRequest().permitAll()   //除了/user/**,其他的可以访问
        .and()
                .csrf().disable();  //关闭跨载的功能
    }
}
