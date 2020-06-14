---
title: 使用Docker安装oracle 11g
categories: [linux, docker]
tags: [oracle 11g, docker]
---

## 一，简介

Oracle Database，又名Oracle RDBMS，或简称Oracle。是甲骨文公司的一款关系数据库管理系统。

借助docker，安装oracle不再困难，只需要几步即可。

需要注意，在参考本文章之前，需要具备操作docker的基础，怎么使用docker，可以参考[这里]()

## 二，安装
 
### 2.1、安装oracle 11g镜像到docker
      
#### 2.1.1、搜索符合条件的镜像

```bash
docker search oracle

NAME                                  DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
oraclelinux                           Official Docker builds of Oracle Linux.         573                 [OK]
jaspeen/oracle-11g                    Docker image for Oracle 11g database            99                                      [OK]
oracle/openjdk                        Docker images containing OpenJDK Oracle Linux   55                                      [OK]
……
```

#### 2.1.2、选择安装 jaspeen/oracle-11g，等待下载安装完成
```bash
docker pull jaspeen/oracle-11g
```

#### 2.1.3、查看下载好的镜像
```bash
docker images

REPOSITORY                 TAG                 IMAGE ID            CREATED             SIZE
jaspeen/oracle-11g         latest              0c8711fe4f0f        3 years ago         281MB
```

注意，这个镜像没有直接安装好oracle，他帮我们配置好了环境，提供了安装脚本，我们只需要按照要求把oracle的安装目录配置好，启动镜像，即可
      

### 2.2、准备oracle 11g安装文件

#### 2.2.1、下载oracle 11g安装文件
从[oracle 官网]( http://www.oracle.com/technetwork/database/enterprise-edition/downloads/index-092322.html)
下载所需要的安装包，这里我们以[oracle 11g](https://www.oracle.com/technetwork/database/enterprise-edition/downloads/112010-linx8664soft-100572.html)
为例子，分别下载 `linux.x64_11gR2_database_1of2.zip` 和 `linux.x64_11gR2_database_2of2.zip`两个压缩包，下载完成后解压到D盘
(如下目录结构)

```bash
D:.
└─oracleinstall
    └─database
        ├─doc
        ├─install
        ├─response
        ├─rpm
        ├─sshsetup
        ├─stage
        ├─runInstaller
        └─welcome.html
```

### 2.3、安装oracle

#### 2.3.1、注意事项
为什么要解压成上面的目录结构，我们先来看看`jaspeen/oracle-11g`镜像提供的安装脚本

```bash
#!/usr/bin/env bash
set -e
source /assets/colorecho

trap "echo_red '******* ERROR: Something went wrong.'; exit 1" SIGTERM
trap "echo_red '******* Caught SIGINT signal. Stopping...'; exit 2" SIGINT

if [ ! -d "/install/database" ]; then
	echo_red "Installation files not found. Unzip installation files into mounted(/install) folder"
	exit 1
fi

echo_yellow "Installing Oracle Database 11g"

su oracle -c "/install/database/runInstaller -silent -ignorePrereq -waitforcompletion -responseFile /assets/db_install.rsp"
/opt/oracle/oraInventory/orainstRoot.sh
/opt/oracle/app/product/11.2.0/dbhome_1/root.sh
```

从脚本里可以看到它会读取`/install/database`目录，如果不存在会给出提示`Installation files not found. Unzip installation files into mounted(/install) folder`


#### 2.3.2、启动镜像（执行安装oracle）

命令的解释：

- docker run 启动容器的命令
- privileged 给这个容器特权，安装oracle可能需要操作需要root权限的文件或目录
- name 给这个容器名一个名字
- p 映射端口
- v 挂在文件到容器指定目录 (`d:/oracleinstall/database` 对应容器 `/install/database`)
- jaspeen/oracle-11g 代表启动指定的容器

```bash
docker run --privileged --name oracle11g -p 1521:1521 -v d:/oracleinstall:/install jaspeen/oracle-11g

Database is not installed. Installing...
Installing Oracle Database 11g
Starting Oracle Universal Installer...

Checking Temp space: must be greater than 120 MB.   Actual 47303 MB    Passed
Checking swap space: must be greater than 150 MB.   Actual 1023 MB    Passed
Preparing to launch Oracle Universal Installer from /tmp/OraInstall2019-04-17_08-14-23AM. Please wait ...
You can find the log of this install session at:
 /opt/oracle/oraInventory/logs/installActions2019-04-17_08-14-23AM.log
 ……
```

这个安装过程会很漫长，日志也很多，这里只提供部分。注意到日志里有 `100% complete` 打印，代表oracle安装成功

#### 2.3.3、安装完成

再次查看运行状态，oracle已经启动完成
```bash
docker ps -a

CONTAINER ID        IMAGE                COMMAND                  CREATED             STATUS                      PORTS                                                                             NAMES
7f53f07c93e5        jaspeen/oracle-11g   "/assets/entrypoint.…"   About an hour ago   Up About an hour            0.0.0.0:1521->1521/tcp, 8080/tcp                                                  oracle11g
```

#### 2.3.4、其他需要注意的，如果日志长时间没有更新，检查docker是否已经死掉

查看docker的状态
```bash
docker ps -a
```

```bash
Error response from daemon: An invalid argument was supplied.
```
如果出现如上提示，表示docker已经死掉，我们只需要重新执行安装步骤，让oracle安装完成
> ps:根据我的猜测，我给docker分配的资源不够导致的，我重新把docker的内存和cpu调高一点后oracle顺利安装完成。

```bash
docker rm oracle11g
docker run --privileged --name oracle11g -p 1521:1521 -v oracleinstall:/install jaspeen/oracle-11g
```

## 三，配置

默认scott用户是被锁定的，我们需要解锁，通过数据库工具即可成功连接到oracle

### 3.1，连接到容器，
```bash
docker exec -it oracle11g /bin/bash
```

### 3.2，切换到oracle用户，然后连接到sql控制台
```bash
[root@7f53f07c93e5 /]# su - oracle
Last login: Wed Apr 17 08:29:31 UTC 2019
[oracle@7f53f07c93e5 ~]$ sqlplus / as sysdba

SQL*Plus: Release 11.2.0.1.0 Production on Wed Apr 17 09:29:49 2019

Copyright (c) 1982, 2009, Oracle.  All rights reserved.


Connected to:
Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
With the Partitioning, OLAP, Data Mining and Real Application Testing options

SQL>
```

### 3.3，解锁账户
```bash
SQL> alter user scott account unlock;
User altered.
SQL> commit;
Commit complete.
SQL> conn scott/tiger
ERROR:
ORA-28001: the password has expired
Changing password for scott
New password:
Retype new password:
Password changed
Connected.
SQL> 
```

### 3.4，使用dataGrip连接oracle数据库
数据库安装完成后，使用默认的sid为orcl，端口为1521，scott/tiger即可连接
![datagrid连接oracle]({{ site.baseurl }}/assets/images/post/linux/docker/datagrip-oracle.png)







> Reference:
> - https://zh.wikipedia.org/wiki/Oracle%E6%95%B0%E6%8D%AE%E5%BA%93
> - https://hub.docker.com/r/jaspeen/oracle-11g
> - https://stackoverflow.com/questions/37468788/what-is-the-right-way-to-add-data-to-an-existing-named-volume-in-docker
> - https://hub.docker.com/_/busybox
> - http://blog.grayidea.cn/archives/67
> - https://blog.csdn.net/u013238950/article/details/50999401
