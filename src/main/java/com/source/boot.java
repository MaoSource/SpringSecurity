package com.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/18:59
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.source.dao")
public class boot {
    public static void main(String[] args) {
        SpringApplication.run(boot.class);
    }
}
