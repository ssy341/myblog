---
title: 在Ubuntu上安装常用的和Java相关的工具
categories: linux
tags: [ubuntu,rabbitmq,nginx,redis,jdk,mysql,jenkins]
toc: false
---

最近工作中经常需要在新服务器上搭建环境，项目中用到的工具有RabbitMQ、Nginx、Redis、MySQL、Jenkins。

还别说，这几个工具把他安装好配置好，需要花的时间还是挺长的。在多次安装和配置过程中我也遇到一些问题，也总结了些经验。

在此纪录下来，方便自己也方便他人。

本篇为目录，列出标题，我在单独的文章里再详细介绍每个工具的安装和使用。

- [在Ubuntu下安装JAVA开发环境][install-java-on-ubuntu] 
- [在Ubuntu下安装RabbitMQ][install-rabbitmq-on-ubuntu] 
- [在Ubuntu下安装Nginx][install-nginx-on-ubuntu] 
- [在Ubuntu下安装MySQL][install-mysql-on-ubuntu] 
- [在Ubuntu下安装Redis][install-redis-on-ubuntu] 
- [在Ubuntu下安装Jenkins][install-jenkins-on-ubuntu] 


上面的方法同样适用 Win10 带的子系统 Bash on Windows ，都是Ubuntu，本人电脑就是直接在子系统里安装这些软件用来开发，
比起安装虚拟机要方便很多，希望这一系列文章对大家有所帮助


[install-java-on-ubuntu]: {{ site.baseurl }}{% post_url linux/2018-02-27-install-java-on-ubuntu %}
[install-rabbitmq-on-ubuntu]: {{ site.baseurl }}{% post_url linux/2018-02-27-install-rabbitmq-on-ubuntu %}
[install-nginx-on-ubuntu]: {{ site.baseurl }}{% post_url linux/2018-02-27-install-nginx-on-ubuntu %}
[install-mysql-on-ubuntu]: {{ site.baseurl }}{% post_url linux/2018-02-27-install-mysql-on-ubuntu %}
[install-redis-on-ubuntu]: {{ site.baseurl }}{% post_url linux/2018-02-27-install-redis-on-ubuntu %}
[install-jenkins-on-ubuntu]: {{ site.baseurl }}{% post_url linux/2018-02-27-install-jenkins-on-ubuntu %}
