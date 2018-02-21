---
layout: single
title: 在intellij idea下远程调试项目
categories: ide
tags: [intellij,远程调试,tomcat]
date: 2013-10-17
---

- 1，首先发布一个和本地一模一样的代码到服务器，假设到 f:/bjhgtest（最后有说明）
- 2，打开服务器下tomcat安装目录conf文件夹下的catalina.bat，加入以下代码：

<!--more-->
```bash
set JAVA_OPTS=%JAVA_OPTS%  -server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=54341,server=y,suspend=n
set JAVA_OPTS=%JAVA_OPTS%  -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
```

![插图一]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project1.png)

- 3，配置intellij，首先为项目添加一个remote tomcat

![插图二]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project2.png)

- 4,加入项目
![插图三]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project3.png)

- 5,配置jvm debug监听的端口
![插图四]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project4.png)

和上面修改的配置文件一致:
```bash
set JAVA_OPTS=%JAVA_OPTS%  -server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=54341,server=y,suspend=n
```

- 6,启动服务器的tomcat，可以看到以下信息
![插图五]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project5.png)

启动本地的tomcat,开始调试

![插图六]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project6.png)

本地tomcat会打印如下日志：

![插图七]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project7.png)


验证是否成功：

我在登陆这段代码上打上断点，然后我访问远程的项目，在登陆的时候，进入断点，远程调试成功~

需要注意的是本地的输出class路径要和服务器的上一致。

本地的：
![插图八]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project8.png)

服务器的：
![插图九]({{ site.baseurl }}/assets/images/post/intellij-idea-remote-debug-project9.png)

这样直接调试服务器上的代码，出现问题后就容易解决，一般服务器的运行环境是很难在本地模拟的，远程调试就不会有那样的问题了，而且还很方便
