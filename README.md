# 简介
IAT一个接口测试平台，主要功能包括接口管理、接口测试等主要功能。在接口管理模块，集成了swagger，将swagger客户端集成到项目中，可以实现接口实时同步。接口测试包括接口参数化、关键字管理、测试用例数据驱动、测试计划管理、测试计划定时执行、测试任务管理、测试报告等重要功能。

Demo地址：http://2338ih6134.iask.in:38904

交流QQ：276518540

# 部署
## 数据库

## Docker部署

# 接口管理
## 自动同步
### Spring MVC
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

```

### Spring boot

## 手动添加
### 接口列表
+ 通过服务下拉列表（只有服务成员才能查看），可以查询不同服务的接口。
+ 点击“创建”按钮，进入接口创建页面。
+ 点击“修改”操作按钮，进入接口修改页面。
+ 点击“删除”操作按钮，删除接口。（软删除）
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-list.jpg)
### 创建接口
+ 在接口列表，点击“创建”按钮，进入接口创建页面。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-create.jpg)
### 修改接口
+ 在接口列表，点击“修改”操作按钮，进入接口修改页面。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-update.jpg)

### 接口调试
#### 参数化
+ Header、Form Data、Body中，都可以将对应的默认值进行参数化，格式为：#{参数名称}，如下图：
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-update.jpg)
#### 调试
+ 参数化后，在Request Parameter中设置参数。
+ 点击调试按钮，选择调试环境（调试环境在服务管理中设置，参考服务管理->环境设置），系统会返回调试结果，如下图：
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-debug.jpg)

## 关键字

## 测试用例

## 测试计划

## 测试任务
