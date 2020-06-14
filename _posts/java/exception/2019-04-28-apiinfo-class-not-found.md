---
title: "Caused by: java.lang.ClassNotFoundException: springfox.documentation.service.ApiInfo"
categories: [java,exception]
tags: [ClassNotFoundException,java,swagger,intellij idea]
---

## 起因

项目中需要使用swagger提供api文档，于是加上依赖：

```html
  <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.2.2</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.2.2</version>
    </dependency>
```

配置swagger扫描的路径：

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 * <p>
 * 2019年4月28日
 *
 * @author Keith
 * @version v0.0.1
 */
@Configuration
//开启swagger
@EnableSwagger2
public class Swagger2 {

    //在配置文件里设置扫描controller的路径
    @Value("${swagger.basePackage}")
    String basePackage;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authorization").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(pars)  ;  //所有接口请求需要带上的参数
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口名称)
                .description("接口描述")
                .termsOfServiceUrl("服务url")
                .contact("联系人")
                .version("版本号")
                .build();
    }
}
```
以上配置在另一个项目中是没问题的，我拷贝过来到新的项目上，启动tomcat，结果报如下错误：

```bash
Caused by: java.lang.ClassNotFoundException: springfox.documentation.service.ApiInfo
```

## 疑问

intellij idea编译能通过，但是启动tomcat的时候却报类找不到，为什么呢？

## 解决问题

我使用的是intellij idea开发，我查看了编译好的war目录，虽然我maven已经引用了依赖，构建也没有问题，但我发现`/WEB_INFO/lib`
确实没有swagger的相关jar。

我把问题锁定到Artifacts配置上，我打开Project Structure -》 Project Settings -》Artifacts 发现Available Elements里面
Swagger相关的jar没有打包war中，所以tomcat启动会提示类找不到。于是选中swagger相关jar，右键`put into /WEB_INFO/lib`，
再次启动tomcat，问题得到解决。

![]({{site.baseurl}}/assets/images/post/java/artifacts.png)

## 总结

intellij idea在修改pom.xml后，没有自动把相关的jar构建到war中，导致发布到tomcat里的war缺失相关jar。手动把缺失的jar构建到war中
即可解决问题。