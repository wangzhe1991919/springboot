package com.wz.springboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 这个类的作用与在web.xml中配置负责初始化Spring应用上下文的监听器作用类似，
 * 只不过在这里不需要编写额外的XML文件了。
 * 作用相当于web.xml
 *
 * 外部打war包启动项目时需要此类
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("spring ServletInitializer");
        return application.sources(SpringbootApplication.class);
    }

}
