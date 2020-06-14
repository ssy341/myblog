---
title: 在Docker上安装常用的和Java相关的工具
categories: [linux,docker]
tags: [ubuntu,rabbitmq,nginx,redis,jdk,mysql,jenkins,gitlab,mongodb]
toc: false
---

之前已经写过[在Ubuntu上安装常用的和Java相关的工具]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %}),
这次要说的是如何在docker上安装这些工具。

何为Docker？

> Docker是一个开放源代码软件项目，让应用程序部署在软件货柜下的工作可以自动化进行，借此在Linux操作系统上，
> 提供一个额外的软件抽象层，以及操作系统层虚拟化的自动管理机制。

[为什么要使用Docker？](https://wiki.jikexueyuan.com/project/docker-technology-and-combat/why.html)已经有现成的了，我就不再多说。
之所以写这个，就是觉得Docker给我带来极大的便利，我同样希望更多人能够享受到Docker带来的好处。

我一直有听说Docker，但一直没有揭开她神秘的面纱。终于我忍不住了，由于一次服务器故障，因为同事执行apt-get命令安装了新的软件，
导致系统环境变量发生了改变，导致ssh服务异常，大家都不能远程连上服务器。这个问题在服务器上一直存在，之前不知道什么原因把


让我下决心要去了解Docker。


/usr/local/lib/libssl.so.1.0.0
/usr/local/lib/libcrypto.so.1.0.0
> Reference:
> - https://zh.wikipedia.org/wiki/Docker
