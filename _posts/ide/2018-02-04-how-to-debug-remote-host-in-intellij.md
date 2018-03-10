---
categories: ide
tags: [intellij idea,远程调试,tomcat]
title: 用Intellij IDEA断点调试远程服务器部署的tomcat项目
toc: true
toc_label: "目录"
---


## 回顾

在很早之前我已经写过一篇关于 [在intellij idea下远程调试项目][remote-debug] 的文章，时隔几年，又遇到同样的情况，
再参考自己写的东西已经不适用了，我总结了一下，可能是以下几个问题
- 之前没有写清楚
- 时隔久远，已经更新了，不再适用当前
- 没有完全理解，草率就记了，知其然不知其所以然
- ……

## 发现疑点一：相同的目录下
写上一篇文章的时候环境都是windows，所以可以把代码放在相同的目录下，但是这次环境不一样了一个是windows一个是linux，
怎么放到相同的目录下呢？

## 发现疑点二：配置remote tomcat
由于第一步已经发生了变化，导致第二步不知道该怎么配置了

为了搞清楚这两个疑点，我开始在网上重新查找了相关文章，看了几篇，在其中简书的一篇[IDEA远程调试Tomcat][jianshu-ref]文章解决了我的两个疑点

## 拨开云雾见青天

- 原来并不需要相同的目录，服务器代码位置和本地代码位置没有关系
- 应该是配置Remote而不是Tomcat Server Remote

但不知为什么在上篇文章中我使用Tomcat Server Remote的方式也达到效果，对这个功能的理解应该有误差，后面再说吧。


## 如何使用intellij idea如何远程调试？


搞清楚我的疑点，那我就可以进入正题了，如何使用intellij idea如何远程调试？一共分为2步

### 在intellij idea里添加remote配置

这一步是配置你要调试的目标，服务器的地址和调试端口

选择 【Select Run/Debug configuration】,【Edit Configurations...】点击左上角绿色加号，选择Remote，
这里需要填写的就是host和port，然后在【Search sources using module‘s classpath】选择自己的项目
![配置Remote]({{ site.baseurl }}/assets/images/post/config-remote.png)

复制remote JVM参数，后面需要用到
```bash
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
```

### 编辑远程服务器tomcat catalina.sh 文件

第二步需要打开tomcat远程调试的功能，找到tomcat目录下的bin目录的catalina.sh文件

```bash
ubuntu@VM-179-90:/home/apache-tomcat-8.5.23/bin$ pwd
/home/apache-tomcat-8.5.23/bin
ubuntu@VM-179-90:/home/apache-tomcat-8.5.23/bin$ ls
bootstrap.jar  catalina-tasks.xml            configtest.bat  digest.bat        setclasspath.sh  startup.bat      tomcat-native.tar.gz  version.bat
catalina.bat   commons-daemon.jar            configtest.sh   digest.sh         shutdown.bat     startup.sh       tool-wrapper.bat      version.sh
catalina.sh    commons-daemon-native.tar.gz  daemon.sh       setclasspath.bat  shutdown.sh      tomcat-juli.jar  tool-wrapper.sh
ubuntu@VM-179-90:/home/apache-tomcat-8.5.23/bin$ vi catalina.sh
```

在catalina.sh文件里添加上面复制的jvm参数 

```bash
export JAVA_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000'
```

```bash
#   UMASK           (Optional) Override Tomcat's default UMASK of 0027
#
#   USE_NOHUP       (Optional) If set to the string true the start command will
#                   use nohup so that the Tomcat process will ignore any hangup
#                   signals. Default is "false" unless running on HP-UX in which
#                   case the default is "true"
# -----------------------------------------------------------------------------

export JAVA_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000'
# OS specific support.  $var _must_ be set to either true or false.
```
大概在文件头部注释结束的地方加入，如上所示，保存并退出。

ps：需要注意的是address配置的端口8000是能够外网访问的，在后面idea的配置中需要用到的

两步配置完毕后，确保远程的tomcat是启动的，然后以Debug方式启动本机的Remote，当IDEA控制台打印如下语句表示成功

```bash
Connected to the target VM, address: '192.168.0.3:8000', transport: 'socket'
```

这个时候就可以远程调试代码了


[remote-debug]: {{ site.baseurl }}{% post_url ide/2013-10-17-intellij-idea-remote-debug-project %}
[jianshu-ref]: https://www.jianshu.com/p/f902ac5d29e4