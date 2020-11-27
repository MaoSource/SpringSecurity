##### 首先先建立一个Spring Boot项目：

![image.png](https://asource.top/upload/2020/11/image-9fc054d98d88460ab9eeb1f2dcc91914.png)

##### 添加Spring Security配置：

```pom
	<!-- Spring Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

##### 添加配置文件：

```Java
/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/16:14
 * @Description:
 */
@Configuration
@EnableWebSecurity  //启动spring security过滤器链
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 该方法的作用就是代替之前的：<security:http>
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 授权配置
            .antMatchers("/**")     //拦截的路劲,/**:拦截所有
            .fullyAuthenticated()   //反问方式
            .and()
            .formLogin();	//表单验证
    }
}
```

从启项目就能看到控制台打印的密码，这个是自动生成加密后的密码

![image.png](https://asource.top/upload/2020/11/image-b2ca43ef82914256b6fc458868f0f891.png)

然后后打开网页：

![image.png](https://asource.top/upload/2020/11/image-3cc80455fdca42cf947538a4a454d737.png)

就能进入一个Security自带的一个页面，然后输入user和刚才控制台打印的密码就可以登录了

![image.png](https://asource.top/upload/2020/11/image-9448d86dacfc4bbabc2ca9320edd721c.png)

##### 自定义用户认证

修改Security配置文件：
```Java
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
                .successForwardUrl("/login-success");   //自定义登录成功地址
    }
}
```

添加处理成功Controller：
```Java

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/19:16
 * @Description:
 */
@Controller
public class loginInfo {

    @RequestMapping("/login-success")
    public String loginSuccess(){
        return "/index";
    }
}
```

添加列表访问Controller：
```Java
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add")
    public String add(){
        return "user/add";
    }
    @RequestMapping("/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/delete")
    public String delete(){
        return "user/delete";
    }

    @RequestMapping("/list")
    public String list(){
        return "user/list";
    }
}
```

添加完成后重新启动项目，访问项目,点击需要权限的页面，即可看到Security自带的登录页面
![image.png](https://asource.top/upload/2020/11/image-80fcb1b777874be1be3b8e34e89f601d.png)

输入账号：root	密码：123
即可访问所有的页面

添加自定义异常处理
```Java
/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/19:48
 * @Description: 自定义异常处理
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
        ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
        ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/405");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

        registry.addErrorPages(error401Page,error405Page, error404Page, error500Page, error403Page);
    }
}
```

在Controller添加请求：
```Java
@RequestMapping("/403")
    public String FORBIDDEN(){
        return "/403";
    }

    @RequestMapping("/404")
    public String NOT_FOUND(){
        return "/404";
    }

    @RequestMapping("/401")
    public String UNAUTHORIZED(){
        return "/401";
    }

    @RequestMapping("/500")
    public String INTERNAL_SERVER_ERROR(){
        return "/500";
    }

    @RequestMapping("/405")
    public String METHOD_NOT_ALLOWED(){
        return "/405";
    }
```

自定了登录页面
```Java
	...以上省略
	.and()
                .formLogin()
                .loginPage("/login")    //添加自定义登录页面
                .successForwardUrl("/login-success")   //自定义登录成功地址
        .and()
                .csrf().disable();  //关闭跨载的功能
```
