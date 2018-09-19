---
title: java的split方法使用问题
categories: java
tags: [split]
date: 2014-03-05
toc: false
---

首先看如下代码

```java
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
 
public class StrTest {
    @Test
    public void sterTst(){
        String ster = "1-1-101|admin";
        System.out.println(ster.split("|")[0]);
        System.out.println(ster.split("|")[1]);
        System.out.println(StringUtils.split(ster,"|")[0]);
        System.out.println(StringUtils.split(ster,"|")[1]);
    }
}
```
    
<!--more-->   
 
执行结果如下：

```
1
-
1-1-101
admin
```

很奇怪，我想把1-1-101和admin分开，直接使用String类的split方法确得不到我想要的结果，而使用工具类却可以，这是怎么回事，查询java api后明白了

```java
public String[] split(String regex)
```

可以看出参数是regex正则表达式，如果直接使用split方法来处理`|`的话，还需要转义，否则直接使用`|`作为分割符号得不到正确结果，如下：

```java
String[] strarr = "1-1-101|admin".split("\\|");
```

这样就能达到和工具类一样的结果了

虽然split是平时用的比较频繁的方法，但恰恰容易犯错，以后使用方法前先看看api，就不会犯这个低级错误了
