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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    //注入DataSource,需在配置中配置持久层
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUserDetailsService userDetailsService;

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

    /**
     * 注册remember-me接口
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // createTableOnStartup属性用于是否启动项目时创建保存token信息的数据表，这里设置为false，我们自己手动创建,首次可以选择true自动创建。
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
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
                .rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
                .tokenValiditySeconds(3600) // remember 过期时间，单为秒
                .userDetailsService(userDetailsService) // 处理自动登录逻辑
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
