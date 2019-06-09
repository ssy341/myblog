---
title: 在Ubuntu下安装JAVA开发环境
categories: linux
tags: [ubuntu,jdk,java]
toc: false
---

JDK即Java Development Kit，java开发工具包，是java开发人员开发中需要用到的软件开发工具包。


## 安装JDK

在Ubuntu下安装java开发环境有两种途径：

- 通过apt-get在线安装
- [下载tar包，自行解压安装]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-on-ubuntu-custom %})

本文介绍的是第一种方式，使用apt-get安装java开发环境

- 第一步：添加源
```bash
thxopen@PC201503302026:~$ sudo add-apt-repository ppa:webupd8team/java
```

> ps: 如果提示 sudo: add-apt-repository: command not found 先执行下面命令
> - sudo apt-get install python-software-properties

- 第二步：更新源
```bash
thxopen@PC201503302026:~$ sudo apt-get update
```

- 第三步：安装jdk，这里选择的是jdk 8
```bash
thxopen@PC201503302026:~$ sudo apt-get install oracle-java8-installer
```
![oracle java 条款]({{ site.baseurl }}/assets/images/post/linux/ubuntu/oracle1.png)
![oracle java 条款]({{ site.baseurl }}/assets/images/post/linux/ubuntu/oracle2.png)

安装过程中，会提示同意条款，输入y即可

> ps: 也可以在安装命令后加上`-y`参数`sudo apt-get install oracle-java8-installer -y`，表示安装默认同意，则不会出现这些提示

- 第四步：检查是否安装成功，控制台输出如下，表示安装成功
```bash
thxopen@PC201503302026:~$ java -version
java version "1.8.0_161"
Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)
```

[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})


> Reference
> - http://www.cnblogs.com/a2211009/p/4265225.html
> - http://blog.csdn.net/yuzaipiaofei/article/details/7281723