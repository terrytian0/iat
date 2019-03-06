# 简介
IAT一个接口测试平台，主要功能包括接口管理、接口测试等主要功能。在接口管理模块，集成了swagger，将swagger客户端集成到项目中，可以实现接口实时同步。接口测试包括接口参数化、关键字管理、测试用例数据驱动、测试计划管理、测试计划定时执行、测试任务管理、测试报告等重要功能。

Demo地址：http://2338ih6134.iask.in:38904

交流QQ：276518540

# 部署
## 数据库

## Docker部署

# 用户手册
## 首页
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/dashboard.jpg)
## 接口管理
### 自动同步
#### Spring MVC
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

#### Spring boot

### 手动添加
#### 接口列表
+ 通过服务下拉列表（只有服务成员才能查看），可以查询不同服务的接口。
+ 点击“创建”按钮，进入接口创建页面。
+ 点击“修改”操作按钮，进入接口修改页面。
+ 点击“删除”操作按钮，删除接口。（软删除）
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-list.jpg)
#### 创建接口
+ 在接口列表，点击“创建”按钮，进入接口创建页面。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-create.jpg)
#### 修改接口
+ 在接口列表，点击“修改”操作按钮，进入接口修改页面。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-update.jpg)

#### 接口调试
##### 参数化
+ Header、Form Data、Body中，都可以将对应的默认值进行参数化，格式为：#{参数名称}，如下图：
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-update.jpg)
##### 调试
+ 参数化后，在Request Parameter中设置参数。
+ 点击调试按钮，选择调试环境（调试环境在服务管理中设置，参考服务管理->环境设置），系统会返回调试结果，如下图：
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/api-debug.jpg)

## 关键字
### 关键字列表
+ 通过服务下拉列表（只有服务成员才能查看），可以查询不同服务的关键字。
+ 点击“创建”按钮，进入关键字创建页面。
+ 点击“修改”操作按钮，进入关键字修改页面。
+ 点击“删除”操作按钮，删除关键字。
+ 点击“添加”操作按钮，可以向关键字中添加接口。
+ 点击关键字前的“+”按钮，可以查看关键字中的接口。接口后的“上移”和“下移”按钮，可以对关键字中的接口排序，删除按钮可以将接口从关键字中移除。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/keyword-list.jpg)
### 创建关键字
+ 在关键字列表，点击创建按钮，进入关键字创建页面，在创建页面只需填写关键字名称和描述。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/keyword-create.jpg)
### 修改关键字
+ 在关键字列表，点击“修改”操作按钮，进入关键字修改页面。
+ 在关键字修改页面，可以修改关键字名称和描述
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/keyword-update.jpg)
### 添加接口
+ 在关键字列表，点击“添加”操作按钮，进入接口选择页，选择需要添加的接口，可以将接口添加到关键字中。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/keyword-addapi.jpg)

### 关键字调试
#### 提取器
+ 在关键字修改页，点击对应接口的提取器按钮,可以给当前接口添加提取器。
+ 提取器包括Json Path提取器和正则表达式提取器。
+ 提取规则，填写对应的Json Path表达式或者正则表达式。
+ 参数名称，填写提取值保存的名称，后面的测试接口可以通过当前名称使用提取出的值。
+ 描述，填写当前提取器的说明。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/extractor.jpg)
#### 断言
+ 在关键字修改页，点击对应接口的断言按钮,可以给当前接口添加断言。
+ 断言位置有3中，可以断言Http Code,Header中的值,Body中的值。
+ 提取规则同提取器。
+ 比较方法有4种，等于、大于、小于、包含
+ 预期结果，填写值或者参数，参数格式为：#{参数名称}。
+ 描述，填写当前断言的说明。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/assert.jpg)
#### 调试
+ 在Parameter中设置参数。
+ 点击调试按钮，选择调试环境（调试环境在服务管理中设置，参考服务管理->环境设置），系统会返回调试结果。点击接口前的“+”按钮，可以查看接口详细测试结果。

## 测试用例
### 测试用例列表
+ 通过服务下拉列表（只有服务成员才能查看），可以查询不同服务的测试用例。
+ 点击“创建”按钮，进入测试用例创建页面。
+ 点击“修改”操作按钮，进入测试用例修改页面。
+ 点击“删除”操作按钮，删除测试用例。
+ 点击“添加”操作按钮，可以向参数用例中添加关键字。
+ 点击测试用例前的“+”按钮，可以查看当前测试用例中的关键字。关键字后的“上移”和“下移”按钮，可以对关键字排序，删除按钮可以将关键字从测试用例中移除。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/testcase-list.jpg)
### 添加测试用例
+ 在测试用例列表，点击创建按钮，进入测试用例创建页面，在创建页面只需填写测试用例名称和描述。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/testcase-create.jpg)
### 添加关键字
+ 在测试用例列表，点击“添加”操作按钮，进入关键字选择页，选择需要添加的关键字，可以将关键字添加到测试用例中。
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/testcase-addkeyword.jpg)
### 修改关键字
+ 在测试用例列表，点击“修改”操作按钮，进入测试用例修改页面。
+ 在测试用例修改页面，可以修改参数用例名称和描述
![avatar](https://raw.githubusercontent.com/terrytian0/iat/master/image/keyword-update.jpg)
### 参数化
+ 在测试用例修改页，parameter中可以添加不同的测试参数值。后续测试过程中，会逐条测试每个参数。
### 调试
+ 选择需要调试的参数，可以调试当前参数组合。
+ 在关键字列表，可以通过点击“+”查看调试详情。

## 测试计划

## 测试任务
