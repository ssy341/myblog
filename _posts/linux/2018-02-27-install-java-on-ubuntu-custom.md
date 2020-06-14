---
title: 在Ubuntu下安装JAVA开发环境(手动)
categories: linux
tags: [ubuntu,jdk,java]
toc: true
---

JDK即Java Development Kit，java开发工具包，是java开发人员开发中需要用到的软件开发工具包。


## 下载JDK

在Ubuntu下安装java开发环境有两种途径：

- [通过apt-get在线安装]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-on-ubuntu %})
- 下载tar包，自行解压安装

本文介绍的是第二种方式

- 第一步：下载jdk，从[官网](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)下载需要的jdk压缩包
本次选择jdk8作为演示，下载文件到`/home/thxopen/app`目录下
```bash
thxopen@PC201503302026:~/app$ pwd
/home/thxopen/app
thxopen@PC201503302026:~/app$ ls
jdk-8u211-linux-x64.tar.gz
```

- 第二步：解压到当前目录
```bash
thxopen@PC201503302026:~/app$ tar -zxvf jdk-8u211-linux-x64.tar.gz
thxopen@PC201503302026:~/app$ ls
jdk-8u211-linux-x64.tar.gz  jdk1.8.0_211
```

## 配置JDK

- 第三步：配置环境变量
```bash
thxopen@PC201503302026:~/app$ sudo vi ~/.bashrc
```

> ps: /.bashrc 代表当前用户下的配置文件，只应用于当前用户，如果使用其他用户，需要重新配置

在配置文件的末尾添加如下配置：

```bash
export JAVA_HOME=/home/thxopen/app/jdk1.8.0_211
export JRE_HOME=${JAVA_HOME}/jre  
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib  
export PATH=${JAVA_HOME}/bin:$PATH
```

> ps: `/home/thxopen/app/jdk1.8.0_211` 为jdk解压后的路径，替换成自己

## 检验JDK

- 第四步：生效配置，检查是否配置成功
```bash
thxopen@PC201503302026:~/app$ source ~/.bashrc
thxopen@PC201503302026:~/app$ java -version
java version "1.8.0_211"
Java(TM) SE Runtime Environment (build 1.8.0_211-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.211-b12, mixed mode)
```

[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})

> Reference
> - https://blog.csdn.net/qq_2300688967/article/details/81702153#