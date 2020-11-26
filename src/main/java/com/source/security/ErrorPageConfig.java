package com.source.security;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/26/19:48
 * @Description:
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN,"/403"));
    }
}
