---
title: Spring Data JPA自动创建表同时生成表和列的注释
categories: java
tags: [java,jpa,spring boot,spring data jpa,mysql,comment,table,column]
---

## 创建表格时同时带上注释

一般情况下我们使用注解的方式很方便的就可以通过java类生成数据库的表，然后把注释写在字段上，就像下面一样

```java

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * demo例子
 * <p>
 * 2018/9/18
 *
 * @author Keith
 * @version v0.0.1
 */
@Entity
@Table(name = "tb_sample")
@Data
public class Sample {

    /**
     * 内容字段
     */
    private String content;

}

```

乍看，觉得已经很明白了，后面维护的人也能看明白。但如对于维护数据库的人来说可能就不是那么容易了，因为他们从数据库里看到的是下面的语句

```bash
CREATE TABLE `tb_sample` (
  `content` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
```

确实，这样看起来不怎么友好，为了解决这个问题，我们可以利用`org.hibernate.annotations.Table`注解和`javax.persistence.Column`
给表和列添加注释，更改之后如下所示：

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

这样自动创建的表格在数据库里就会是下面的样子：

```bash
CREATE TABLE `tb_sample` (
  `content` varchar(255) DEFAULT NULL COMMENT '测试字段'
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='demo例子';
```

这样，不仅java开发能方便的知道实体和每个字段的意思，维护数据库的人也没有压力，是不是挺方便的

## 注意事项

使用上面的方式，虽然挺方便，但需要注意以下三点：

- 1.数据库是mysql
- 2.需要配合Entity注解name属性一起使用，用上面的例子说明
```java
@Entity(name = "tb_sample")
@Table(appliesTo = "tb_sample",comment = "demo例子")
public class Sample {
```
这里Entity的name的值要和Table的appliesTo的值一样。
一般情况下，我们没有给Entity的name赋值，默认就是实体的名称（这里是Sample），
如果你不指定Entity的name，那么Table的appliesTo的值为`Sample`，即数据库表名也为`Sample`

- 3.实体的别名不再默认是类名，而是name的值
这就意味着Repository里本来是可以写类名的（因为默认是Sample）而要写成`tb_sample`（如下面代码），否则语句会解析错误，报找不到`Sample`这个实体
```java
@Modifying
@Query("delete from tb_sample d where d.content=:content")
int deleteByContent(@Param("content") String content);
```


> Reference:
> - https://segmentfault.com/a/1190000015047290
> - https://blog.csdn.net/xiaxia_jessica/article/details/43274735
