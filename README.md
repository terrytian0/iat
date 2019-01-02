# iat
同步Swagger、Api管理、Api调试、Api测试。

# 同步Swagger
## Spring MVC
+ pom文件中添加依赖
```
<dependency>
  <groupId>com.terry</groupId>
  <artifactId>iat-swagger</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
+ 配置Api管理平台地址和服务唯一Key
```
#Api管理平台地址
api.server=http://127.0.0.1:8080/api/push
#服务唯一Key
unique.key=bc5abb3a-0b2a-11e9-bd16-080027546a5b
```
+ 添加监听器
```
import com.terry.iat.swagger.SwaggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

@Component
public class SwaggerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DocumentationCache documentationCache;
    
    @Autowired
    private ServiceModelToSwagger2Mapper mapper;
    
    @Value("${api.server}")
    private String apiServer;

    @Value("${unique.key}")
    private String uniqueKey;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() != null) {
            new Thread(
                    () -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        SwaggerUtils.postSwagger(apiServer, uniqueKey, documentationCache, mapper);
                    })
                    .start();
        }
    }
}

```

## Spring boot

# Api管理

# Api调试

# Api测试

## 关键字

## 测试用例

## 测试计划

## 测试任务
