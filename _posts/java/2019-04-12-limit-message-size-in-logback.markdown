---
title: 使用logback限制日志打印内容长度
categories: java
tags: [logback,layout,pattern]
---

## 简介

好的日志打印，对于程序的调试和查找问题是很重要的。目前广泛使用的是logback，配合[lombok](//projectlombok.org)可以很方便
的打印日志。

```java
@Slf4j
public class LogExampleOther {
  
  public static void main(String... args) {
    log.error("Something else is wrong here");
  }
}
```

只需要在类上加上`@Slf4j`注解即可

<!--more-->

## 问题背景

由于在实际开发过程中打印日志内容的长度是不可控的，我想在输出的时候控制内容长度，于是我书写如下代码：

```java
@Slf4j
public class LogExampleOther {
  
  public static void main(String... args) {
    String message = "Something else is wrong here";
    if(message.length > 1000){
        log.info("the content length over limit ，only show the part of front : {} ",message.substring(0,1000));
    }else{
        log.info("the content is {}",message);
    }
  }
}
```

## 解决方案

时隔多日，发现这样非常不友好，随着需要控制的地方越来越多，这个代码重复出现在项目的各个地方。我想有没有什么配置可以设置日志
输出的最大长度呢？一番搜索后最终在[logback的文档](https://logback.qos.ch/manual/layouts.html#formatModifiers)里找到了答案，原来官方已经提供了方法。

以下是文档原文：

> By default the relevant information is output as-is. However, with the aid of format modifiers it is possible to change 
> the minimum and maximum width and the justifications of each data field.
> 
> The optional format modifier is placed between the percent sign and the conversion character or word.
> 
> The first optional format modifier is the left justification flag which is just the minus (-) character. 
> Then comes the optional minimum field width modifier. This is a decimal constant that represents the minimum number of 
> characters to output. If the data item contains fewer characters, it is padded on either the left or the right until 
> the minimum width is reached. The default is to pad on the left (right justify) but you can specify right padding with 
> the left justification flag. The padding character is space. If the data item is larger than the minimum field width, 
> the field is expanded to accommodate the data. The value is never truncated.

默认情况下输出给定的字符串，但借助修饰符可以配置最小和最大长度。在%和转换字符之间使用`.`（点）和`-`（减号）截断字符串

```xml
<pattern>%-4relative [%thread] %-5level - %.-1024msg%n</pattern>
```
上面的输出模板会把超过1024个字符的输出截断，只显示前1024个字符。

最终通过查询文档解决了我以前的疑问，让代码更加简洁。

## logback的转换符对照表

为了配置适合自己的日志格式，我们必须弄清楚转换字符的意思，下面附一张logback的转换符对照表

| 转换字符                                                                                                                                                                                             | 效果                                                          | 解释                                                               |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------|--------------------------------------------------------------------|
| c{length}  <br> lo{length} <br>  logger{length}                                                                                                                                                              | %logger mainPackage.sub.sample.Bar mainPackage.sub.sample.Bar | 原始记录器名称                                                     |
|                                                                                                                                                                                                      | %logger{5} mainPackage.sub.sample.Bar m.s.s.Bar               |                                                                    |
| C{length} <br>  class{length}                                                                                                                                                                            | %C mainPackage.sub.sample.Bar mainPackage.sub.sample.Bar      | 记录器所在类的全路径                                               |
| contextName/cn                                                                                                                                                                                       |                                                               | 原始记录器上下文名称                                               |
| d{pattern} <br>  date{pattern} <br>  d{pattern, timezone}  <br> date{pattern, timezone}                                                                                                                          | %d 2019-4-12 18:01:54,123                                     | 日志打印时间                                                       |
|                                                                                                                                                                                                      | %date{HH:mm:ss.SSS} 18:01:54.123                              |                                                                    |
| F/file                                                                                                                                                                                               | %F Bar.java                                                   | java文件名称                                                       |
| caller{depth}<br> caller{depthStart..depthEnd} <br> caller{depth, evaluator-1, ... evaluator-n} <br> caller{depthStart..depthEnd, evaluator-1, ... evaluator-n}                                                    |                                                               | 记录器调用者位置信息                                               |
| L / line                                                                                                                                                                                             |                                                               | 日志输出所在文件的行号                                             |
| m / msg / message                                                                                                                                                                                    |                                                               | 日志具体内容                                                       |
| M / method                                                                                                                                                                                           |                                                               | 方法名                                                             |
| n                                                                                                                                                                                                    |                                                               | 换行符                                                             |
| p / le / level                                                                                                                                                                                       |                                                               | 日志级别                                                           |
| r / relative                                                                                                                                                                                         |                                                               | 输出自应用程序启动以来直到创建日志记录事件所经过的毫秒数           |
| t / thread                                                                                                                                                                                           |                                                               | 当前线程的名称                                                     |
| X{key:-defaultVal} <br>  mdc{key:-defaultVal}                                                                                                                                                            |                                                               | 输出与生成日志记录事件的线程关联的MDC（映射的诊断上下文）          |
| ex{depth} <br>  exception{depth}  <br> throwable{depth}  <br> ex{depth, evaluator-1, ..., evaluator-n} <br>  exception{depth, evaluator-1, ..., evaluator-n}  <br> throwable{depth, evaluator-1, ..., evaluator-n}       |                                                               | 输出与日志记录事件关联的异常的堆栈信息。默认情况下，将输出完整堆栈 |
| xEx{depth} <br>  xException{depth} <br>  xThrowable{depth}  <br> xEx{depth, evaluator-1, ..., evaluator-n} <br>  xException{depth, evaluator-1, ..., evaluator-n} <br>  xThrowable{depth, evaluator-1, ..., evaluator-n} |                                                               | 与上面的％throwable相同，但添加了类包装信息                        |
| nopex/nopexception                                                                                                                                                                                   |                                                               | 不输出任何信息，有效的忽略了异常                                   |
| marker                                                                                                                                                                                               |                                                               | 输出与记录器请求关联的标记                                         |
| property{key}                                                                                                                                                                                        |                                                               | 输出名为key属性关联的值                                            |
| replace(p){r, t}                                                                                                                                                                                     |                                                               | 替换‘p’内容中‘r’为‘t’，正则表达式操作                              |
| rEx{depth}  <br> rootException{depth}  <br> rEx{depth, evaluator-1, ..., evaluator-n}  <br> rootException{depth, evaluator-1, ..., evaluator-n}                                                                  |                                                               | 输出与日志记录事件关联的异常的堆栈                                 |



> Reference:
> - https://logback.qos.ch/manual/layouts.html#formatModifiers
> - https://stackoverflow.com/questions/35710008/limit-message-size-in-logback
> - https://stackoverflow.com/questions/32704470/can-a-logback-message-field-be-truncated-trimmed-to-n-characters
