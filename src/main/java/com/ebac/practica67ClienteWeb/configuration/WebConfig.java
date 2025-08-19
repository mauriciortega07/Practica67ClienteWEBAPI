package com.ebac.practica67ClienteWeb.configuration;

import com.ebac.practica67ClienteWeb.component.AuthorizationFilter;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/pagina-login").setViewName("pagina-login");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        AuthorizationFilter authorizationFilter = new AuthorizationFilter();

        registrationBean.setName("authorization");
        registrationBean.setFilter(authorizationFilter);
        registrationBean.setOrder(1);

        return registrationBean;


    }
}
