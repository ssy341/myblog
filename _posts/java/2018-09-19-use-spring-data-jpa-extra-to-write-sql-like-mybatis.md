---
title: 像使用mybatis一样使用spring data jpa
categories: java
tags: [java,jpa,spring boot,spring data jpa,mybatis,template,query]
---

## 简介

自从用上了spring data jpa后，已经深深的喜欢上她的这种风格。简单的CURD操作、根据方法名动态生成sql，就这两点我已经很满足了。
虽然已经很强大，美中不足的是对原生sql的支持有点欠缺，不过好在有大神弥补了这个小小遗憾，下面介绍大神给的解决方案[spring-data-jpa-extra](https://github.com/slyak/spring-data-jpa-extra)


`spring-data-jpa-extra`是一个可以像使用mybatis一样的spring data jpa扩展，她在spring data jpa上扩展了sql模板的功能，解决下面三个问题：

- 动态原生查询支持，如mybatis
- 可以返回任何类型的数据
- 没有代码，只有sql语句

本身强大的spring data jpa，加上动态原生sql的功能，简直就是如虎添翼，下面通过简单的介绍，告诉大家怎么使用这个插件

## 配置

作者已经把源码放在github上，并在maven仓库里发布了对应的jar，可供依赖使用，如果你正在使用maven，只需要添加下面代码，即可使用她


- 第一步：引入依赖

```xml
<dependency>
    <groupId>com.slyak</groupId>
    <artifactId>spring-data-jpa-extra</artifactId>
    <version>2.1.1.RELEASE</version>
</dependency>
```
ps:github上的readme给出的是`2.1.2.RELEASE`，但我在使用的时候，只能下载到2.1.1的版本，具体原因不太清楚，大家参考的时候多尝试一下

- 第二步：开启功能并配置Freemark

```java
import com.slyak.spring.jpa.FreemarkerSqlTemplates;
import com.slyak.spring.jpa.GenericJpaRepositoryFactoryBean;
import com.slyak.spring.jpa.GenericJpaRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * spring data jpa extra 配置
 * <p>
 * 2018/9/18
 *
 * @author Keith
 * @version v0.0.1
 */

@Configuration
//your base package
@ComponentScan("com.thxopen")
@EnableJpaRepositories(
        //your repository package
        basePackages = "com.thxopen.dao.repository" ,
        repositoryBaseClass = GenericJpaRepositoryImpl.class, 
        repositoryFactoryBeanClass = GenericJpaRepositoryFactoryBean.class)
public class RepositoryConfig {

    @Bean
    public FreemarkerSqlTemplates freemarkerSqlTemplates() {
        FreemarkerSqlTemplates templates = new FreemarkerSqlTemplates();
        templates.setSuffix(".sftl");
        return templates;
    }
}
```


## 使用

- 第一步：创建实体和DTO

Sample.java

```java
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * demo例子
 * <p>
 * 2018/9/18
 *
 * @author Keith
 * @version v0.0.1
 */
@Entity(name = "tb_sample")
@Table(appliesTo = "tb_sample",comment = "demo例子")
@Data
public class Sample {

    @Column(columnDefinition = "varchar(255) COMMENT '测试字段'")
    private String content;

}
```

SampleDTO.java

```java
public class SampleDTO {
    private long id;

    private String contentShow;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContentShow() {
        return contentShow;
    }

    public void setContentShow(String contentShow) {
        this.contentShow = contentShow;
    }
}
```

SampleQuery.java

```java
public class SampleQuery {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content != null) {
            this.content = "%" + content + "%";
        }
    }
}
```


- 第二步：创建Repository

与spring data jpa用法不一样的是，需要把继承`JpaRepository`改为继承`GenericJpaRepository`，代码如下

```java
import com.slyak.spring.jpa.GenericJpaRepository;
import com.slyak.spring.jpa.TemplateQuery;
import com.thxopen.dao.entity.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author keith
 * @create 2018年9月18日15:03:59
 * @desc
 **/
public interface SampleRepository extends GenericJpaRepository<Sample,Long> {


    /**
     * spring data jpa 原生
     * 根据条件过滤
     * 更多写法参考 https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     * @param content
     * @return
     */
    List<Sample> findAllByContent(String content);
    
    /**
     * spring data jpa 原生的native
     * @param name
     * @return
     */
    @Query(nativeQuery = true, value = "select * from tb_sample where content like ?1")
    List<Sample> findDtos2(@Param("name") String name);


    /**
     * 分页过滤查询
     * @param content
     * @param pageable
     * @return
     */
    @TemplateQuery
    Page<Sample> findByContent(@Param("content") String content, Pageable pageable);

    /**
     * 分页对象过滤查询
     * @param sampleQuery
     * @param pageable
     * @return
     */
    @TemplateQuery
    List<Sample> findByTemplateQueryObject(SampleQuery sampleQuery, Pageable pageable);

    /**
     * 过滤计算总数
     * @param content
     * @return
     */
    @TemplateQuery
    Long countContent(@Param("content") String content);

    /**
     * 返回自定义的dto
     * @return
     */
    @TemplateQuery
    List<SampleDTO> findDtos();

    
    /**
     * 返回map
     * keith-fix 目前还有报错，暂且不用
     * @return
     */
    @TemplateQuery
    List<Map<String,Object>> findMap();

}
```

ps:使用`@TemplateQuery`自定义注解代表会根据方法名去模板文件里找相应的sql

- 第三步：在/resources/sqls创建对应的sql模板（tb_sample.sftl），文件名同Entity的name值

```bash
--findByContent
  SELECT * FROM tb_sample WHERE 1 = 1
<#if content??>
        AND content LIKE :content
</#if>

--countContent
SELECT count(*) FROM tb_sample WHERE 1 = 1
<#if content??>
  AND content LIKE :content
</#if>

--findDtos
SELECT id, content as contentShow FROM tb_sample

--findByTemplateQueryObject
SELECT * FROM tb_sample WHERE 1 = 1
<#if content??>
 AND content LIKE :content
</#if>

--findMap
SELECT * FROM tb_sample
```


`--`后面的字符串和`Repository`里的方法名一一对应

- 第四步：创建测试用例，验证方法

```java

import com.thxopen.Application;
import com.thxopen.dao.entity.Sample;
import com.thxopen.dao.repository.sample.SampleDTO;
import com.thxopen.dao.repository.sample.SampleQuery;
import com.thxopen.dao.repository.sample.SampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * jpa类似mybatis用法测试用例
 * 教程参考：https://github.com/slyak/spring-data-jpa-extra
 * <p>
 * 2018/9/18
 *
 * @author Keith
 * @version v0.0.1
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration
@Slf4j
public class SampleTest {

    @Autowired
    SampleRepository sampleRepository;

    @Test
    public void delSomeSample() {
        sampleRepository.deleteAll();
    }

    @Test
    public void addSomeSample() {
        for (int i = 0; i < 10; i++) {
            Sample sample = new Sample();
            sample.setContent("hello world" + i);
            sampleRepository.save(sample);
        }
    }

    @Test
    public void findByTemplateQuery() {
        PageRequest pageRequest = new PageRequest(1, 10);
        Page<Sample> samples = sampleRepository.findByContent("%world%", pageRequest);
        Assert.assertTrue(samples.getTotalElements() == 10);
    }

    @Test
    public void countByTemplateQuery() {
        long count = sampleRepository.countContent("%world%");
        Assert.assertTrue(count == 10);
    }

    @Test
    public void findByTemplateQueryAndReturnDTOs() {
        List<SampleDTO> dtos = sampleRepository.findDtos();
        Assert.assertTrue(dtos.size() == 10);
    }

    @Test
    public void findByTemplateQueryWithTemplateQueryObject() {
        SampleQuery sq = new SampleQuery();
        sq.setContent("%world%");
        List<Sample> samples = sampleRepository.findByTemplateQueryObject(sq, null);
        Assert.assertTrue(samples.size() == 10);
    }

    @Test
    public void findBySpringElQuery() {
        List<Sample> dtos = sampleRepository.findDtos2("%world%");
        Assert.assertTrue(dtos.size() == 10);
    }

    /**
     * keith-fix 会报错，问题暂时不知
     */
    @Test
    public void findMap() {
        List<Map<String, Object>> listMaps = sampleRepository.findMap();
        Assert.assertTrue(listMaps.size() == 10);
    }
}
```

## 总结

从上面给出的代码可以看出`spring-data-jpa-extra`确实很强大，在开发中效率能提高很多，希望本文能帮助到大家，同时在这里还要感谢作者的付出，谢谢


> Reference:
> - https://github.com/slyak/spring-data-jpa-extra
> - https://blog.csdn.net/qq_27384769/article/details/79283391