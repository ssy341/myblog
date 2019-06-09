---
title: spring之aop（ThrowsAdvice）拦截指定方法的的异常
categories: spring
tags: [ThrowsAdvice,aop]
---


近段时间需要对程序的异常做统一的处理，比如写日志或者发送邮件，在网上找了下aop貌似可以解决，经过不懈努力，终于完成了这个效果，异常统一发送邮件，迅速知道异常所在，下面看代码。

spring的配置文件：

```xml
<!-- 异常集中捕获-->
    <!-- 自定义拦截异常通知类 -->
    <bean id = "aopTest" class="com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest"/>
    
    <!-- 自定义异常通知类 -->
    <bean id="customAdvice" class="com.daja.paymp.presentation.interceptor.ExceptionThrowsAdvice" />
    <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"></bean> 
    <bean class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
        <property name="advice">
            <ref bean="customAdvice"/>
        </property>
        <property name="patterns">
            <value>com\.daja\.paymp\.infrastructure\.scheduling\.service\.AutoJobService\.work</value>
        </property>
    </bean>

```

ExceptionThrowsAdvice.java

```java
package com.daja.paymp.presentation.interceptor;
 
import java.lang.reflect.Method;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
 
import com.daja.paymp.infrastructure.exception.CustomException;
 
/**
 * 异常拦截类 aop spring
 * 
 * @author smotive
 * 
 */
 
public class ExceptionThrowsAdvice implements ThrowsAdvice {
    Logger logger = LoggerFactory.getLogger(ExceptionThrowsAdvice.class);
 
    // Second preference
    public void afterThrowing(Method m, Object args[], Object target,
            Throwable e) {
        try {
            throw new CustomException(e,"自动任务出现异常");
        } catch (CustomException e1) {
            e1.printStackTrace();
        }
    }
}
```

接口类： AutoJobService.java

```java

package com.daja.paymp.infrastructure.scheduling.service;
 
/**
 *  自动任务公共接口
 * @author smotive
 *
 */
public interface AutoJobService  {
    /**
     * 自动任务公共执行方法
     */
    public void work()throws Exception;
}
```

实现类：SpringAopTest.java

```java

package com.daja.paymp.infrastructure.scheduling.service.impl;
 
import org.springframework.stereotype.Service;
 
import com.daja.paymp.infrastructure.scheduling.service.AutoJobService;
 
 
public class SpringAopTest implements AutoJobService {
 
    @Override
    public void work(){
        System.out.print("aop拦截测试方法进入"+(1/0));
    }
}
```



测试方法类：

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
 
import com.daja.paymp.infrastructure.scheduling.service.AutoJobService;
public class AopTest {
 
    public static void main(String args[])
    {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:applicationContext-aop.xml");
 
        AutoJobService inter =(AutoJobService)context.getBean("aopTest");
        try {
            inter.work();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```


运行结果：

```bash
18:04:27 [main] &quot;ERROR&quot; c.d.p.i.exception.CustomException - / by zero
com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest.work(SpringAopTest.java:12)
com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest$$FastClassByCGLIB$$e20000f1.invoke(&lt;generated&gt;)
net.sf.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
org.springframework.aop.framework.Cglib2AopProxy$DynamicAdvisedInterceptor.intercept(Cglib2AopProxy.java:618)
com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest$$EnhancerByCGLIB$$d12593c6.work(&lt;generated&gt;)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
java.lang.reflect.Method.invoke(Method.java:597)
org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:318)
org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:183)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor.invoke(ThrowsAdviceInterceptor.java:124)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:202)
$Proxy7.work(Unknown Source)
AopTest.main(AopTest.java:14)
 
18:04:28 [main] &quot;INFO &quot; c.d.p.i.common.email.Warning - 邮件顺利送达!
com.daja.paymp.infrastructure.exception.CustomException: 自动任务出现异常
    at com.daja.paymp.presentation.interceptor.ExceptionThrowsAdvice.afterThrowing(ExceptionThrowsAdvice.java:25)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
    at java.lang.reflect.Method.invoke(Method.java:597)
    at org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor.invokeHandlerMethod(ThrowsAdviceInterceptor.java:144)
    at org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor.invoke(ThrowsAdviceInterceptor.java:129)
    at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
    at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:202)
    at $Proxy7.work(Unknown Source)
    at AopTest.main(AopTest.java:14)
Caused by: java.lang.ArithmeticException: / by zero
    at com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest.work(SpringAopTest.java:12)
    at com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest$$FastClassByCGLIB$$e20000f1.invoke(&lt;generated&gt;)
    at net.sf.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
    at org.springframework.aop.framework.Cglib2AopProxy$DynamicAdvisedInterceptor.intercept(Cglib2AopProxy.java:618)
    at com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest$$EnhancerByCGLIB$$d12593c6.work(&lt;generated&gt;)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
    at java.lang.reflect.Method.invoke(Method.java:597)
    at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:318)
    at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:183)
    at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
    at org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor.invoke(ThrowsAdviceInterceptor.java:124)
    ... 4 more
java.lang.ArithmeticException: / by zero
    at com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest.work(SpringAopTest.java:12)
    at com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest$$FastClassByCGLIB$$e20000f1.invoke(&lt;generated&gt;)
    at net.sf.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
    at org.springframework.aop.framework.Cglib2AopProxy$DynamicAdvisedInterceptor.intercept(Cglib2AopProxy.java:618)
    at com.daja.paymp.infrastructure.scheduling.service.impl.SpringAopTest$$EnhancerByCGLIB$$d12593c6.work(&lt;generated&gt;)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
    at java.lang.reflect.Method.invoke(Method.java:597)
    at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:318)
    at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:183)
    at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
    at org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor.invoke(ThrowsAdviceInterceptor.java:124)
    at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
    at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:202)
    at $Proxy7.work(Unknown Source)
    at AopTest.main(AopTest.java:14)
```

当指定的方法抛出异常后，spring就会拦截到，做出相应的动作，系统加入这个，出现问题就可以及时察觉了，O(∩_∩)O哈哈~



