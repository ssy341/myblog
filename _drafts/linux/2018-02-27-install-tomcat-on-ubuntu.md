---
title: 在Ubuntu下安装Tomcat
categories: linux
tags: [ubuntu,tomcat]
---

Tomcat是Java语言里常用的一个WEB应用服务器容器，把开发好的应用程序部署进去，我们就可以通过浏览器访问到应用了。

本篇文章通过两个部分介绍Tomcat

- 安装
- 配置

第一部分介绍怎么安装到主机，第二部分介绍安装好之后怎么配置。

## 安装Tomcat

在Ubuntu上安装Tomcat有两种方式：

- 在线安装
- 本地安装

这里介绍的是在线安装，本地安装以后再更新。在线安装只要主机能够连上网络，就能很方便安装好Tomcat。

第一步：进入到用户目录，可以直接安装在用户目录下，也可以在用户目录下新建文件夹，这里我放在用户目录下的app文件夹里
```bash
thxopen@Thxopen:~$ mkdir app
thxopen@Thxopen:~$ ls
app
```
ps: `~` 代表/home/用户名 路径

第二步：下载Tomcat到app目录，本次选择Tomcat-8.5.29作为演示
```bash
thxopen@Thxopen:~$cd app
thxopen@Thxopen:~/app$ wget http://mirrors.shu.edu.cn/apache/tomcat/tomcat-8/v8.5.29/bin/apache-tomcat-8.5.29.tar.gz
--2018-02-27 22:22:15--  http://mirrors.shu.edu.cn/apache/tomcat/tomcat-8/v8.5.29/bin/apache-tomcat-8.5.29.tar.gz
正在解析主机 mirrors.shu.edu.cn (mirrors.shu.edu.cn)... 202.121.199.235
正在连接 mirrors.shu.edu.cn (mirrors.shu.edu.cn)|202.121.199.235|:80... 已连接。
已发出 HTTP 请求，正在等待回应...
200 OK
长度： 9532698 (9.1M) [application/x-gzip]
正在保存至: “apache-tomcat-8.5.29.tar.gz”

apache-tomcat-8.5.29.tar.gz  100%[================================================>]   9.09M   886KB/s    in 11s

2018-02-27 22:23:10 (853 KB/s) - 已保存 “apache-tomcat-8.5.29.tar.gz” [9532698/9532698])
thxopen@Thxopen:~/app$ls
apache-tomcat-8.5.29.tar.gz
```
ps: 可能会出现地址无法访问，是正常的，下载地址不定时会有变动，如出现不能下载的情况，前往[官网下载页面][tomcat.tar.gz]复制新的下载地址

![tomcat.tar.gz]({{ site.baseurl }}/assets/images/post/linux/ubuntu/tomcat.tar.gz.jpg)

右键复制链接地址，替换上面命令wget 后面的url地址即可

第三步：解压并更改Tomcat的名称

```bash
thxopen@Thxopen:~/app$ tar vxf apache-tomcat-8.5.29.tar.gz
apache-tomcat-8.5.29/conf/
apache-tomcat-8.5.29/conf/catalina.policy
apache-tomcat-8.5.29/conf/catalina.properties
apache-tomcat-8.5.29/conf/context.xml
apache-tomcat-8.5.29/conf/jaspic-providers.xml
apache-tomcat-8.5.29/conf/jaspic-providers.xsd
apache-tomcat-8.5.29/conf/logging.properties
apache-tomcat-8.5.29/conf/server.xml
apache-tomcat-8.5.29/conf/tomcat-users.xml
apache-tomcat-8.5.29/conf/tomcat-users.xsd
apache-tomcat-8.5.29/conf/web.xml
...
apache-tomcat-8.5.29/bin/catalina.sh
apache-tomcat-8.5.29/bin/configtest.sh
apache-tomcat-8.5.29/bin/daemon.sh
apache-tomcat-8.5.29/bin/digest.sh
apache-tomcat-8.5.29/bin/setclasspath.sh
apache-tomcat-8.5.29/bin/shutdown.sh
apache-tomcat-8.5.29/bin/startup.sh
apache-tomcat-8.5.29/bin/tool-wrapper.sh
apache-tomcat-8.5.29/bin/version.sh
thxopen@Thxopen:~/app$ ls
apache-tomcat-8.5.29  apache-tomcat-8.5.29.tar.gz
thxopen@Thxopen:~/app$ mv apache-tomcat-8.5.29 tomcat-demo-8888
thxopen@Thxopen:~/app$ ls
apache-tomcat-8.5.29.tar.gz  tomcat-demo-8888
thxopen@Thxopen:~/app$cd tomcat-demo-8888/
thxopen@Thxopen:~/app/tomcat-demo-8888$ls
bin  conf  lib  LICENSE  logs  NOTICE  RELEASE-NOTES  RUNNING.txt  temp  webapps  work
```
ps：这里我把tomcat重命名为tomcat-demo-8888，这样很直观就能看出这个tomcat是运行什么应用端口是多少，建议大家也根据实际情况重命名，当出现多个tomcat时是很有帮助的


[tomcat.tar.gz]: https://tomcat.apache.org/download-80.cgi