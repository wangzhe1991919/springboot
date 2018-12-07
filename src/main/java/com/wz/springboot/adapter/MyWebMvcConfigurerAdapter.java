package com.wz.springboot.adapter;

import com.wz.springboot.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurationSupport {
    /**
     * 配置静态访问资源
     * @param registry
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/views/**").addResourceLocations("classpath:/views/");
        super.addResourceHandlers(registry);
    }*/

    /**
     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toLogin就跳转到login.jsp页面了
     * @param registry
     */
    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toLogin").setViewName("login");
        super.addViewControllers(registry);
    }*/

    /**
     * 拦截器
     * addPathPatterns("/**")对所有请求都拦截，但是排除了/toLogin和/login请求的拦截。
     * @param registry
     */
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin","/login");
        super.addInterceptors(registry);
    }*/
}
