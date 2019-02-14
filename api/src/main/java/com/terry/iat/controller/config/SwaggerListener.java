package com.terry.iat.controller.config;

import com.terry.iat.swagger.SwaggerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description 容器加载完成后，上传Swagger Api到测试平台
 * @author terry
 * @Date 2019/1/22 11:29
 * @Version 1.0
 **/
@Component
@Profile("dev")
public class SwaggerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${api.server}")
    private String apiServer;

    @Value("${unique.key}")
    private String uniqueKey;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() != null) {
            SwaggerUtils.postSwagger(apiServer, uniqueKey, contextRefreshedEvent.getApplicationContext());
        }
    }
}
