---
title: 在Ubuntu下安装Redis
categories: linux
tags: [ubuntu,redis]
---


Redis是一个使用ANSI C编写的开源、支持网络、基于内存、可选持久性的键值对存储数据库。


## 安装Redis

```bash
sudo apt-get install redis-server
```

## 更改默认的端口

redis默认的访问端口是`6379`，避免端口被恶意程序使用，我们修改redis的默认端口

打开配置文件`/etc/redis/redis.conf` 

```bash
sudo vi /etc/redis/redis.conf
```

大概在文件50行左右，有如下内容

> ps:vi编辑器小提示：
> `:set number` 设置显示文件内容的行号，方便查找内容
> `/search_content` 斜杠加搜索内容，搜索文件里指定内容 

```bash
# Accept connections on the specified port, default is 6379.
# If port 0 is specified Redis will not listen on a TCP socket.
port 6379
```

修改端口为自己需要的，保存退出，重启redis即可

## 设置访问密码

仅仅修改了端口号还是不够的，我们还要设置密码，避免黑客有机可乘

还是继续打开配置文件 `/etc/redis/redis.conf` 

```bash
sudo vi /etc/redis/redis.conf
```

大概在390行左右，有如下内容

```bash
################################## SECURITY ###################################

# Require clients to issue AUTH <PASSWORD> before processing any other
# commands.  This might be useful in environments in which you do not trust
# others with access to the host running redis-server.
#
# This should stay commented out for backward compatibility and because most
# people do not need auth (e.g. they run their own servers).
#
# Warning: since Redis is pretty fast an outside user can try up to
# 150k passwords per second against a good box. This means that you should
# use a very strong password otherwise it will be very easy to break.
#
#requirepass foobared
```

去掉注释，把`foobared`改为自己的密码，保存退出，重启redis即可

## 设置从任意ip访问redis服务

默认情况下，redis只能在本地使用，通过修改配置，可以远程操作redis

同上，打开配置文件 `/etc/redis/redis.conf` 
          
```bash
sudo vi /etc/redis/redis.conf
```

大概在69行，有如下内容


```bash
# By default Redis listens for connections from all the network interfaces
# available on the server. It is possible to listen to just one or multiple
# interfaces using the "bind" configuration directive, followed by one or
# more IP addresses.
#
# Examples:
#
# bind 192.168.1.100 10.0.0.1
bind 127.0.0.1
```

- 将 `127.0.0.1` 改为指定的ip地址 `192.168.1.100` （允许添加多个ip，用空格隔开），即允许ip为`192.168.1.100`机器访问redis服务。（推荐）
- 也可以将 `127.0.0.1` 改为 `0.0.0.0` 表示任意ip都可以访问redis服务。 （不推荐）

## 使用console连接到redis

```bash
thxopen@Thxopen:/etc/redis$ redis-cli -p 6379 -a your_password
127.0.0.1:6379> ping
PONG
127.0.0.1:6379>
```

看到输出 `PONG` 表示redis已经启动成功


## 使用Redis Desktop Manager连接到Redis

[Redis Desktop Manager](https://redisdesktop.com/) 是一个跨平台的，开源的redis桌面管理工具。使用这个工具可以很方便的操作redis。

从官网选择对应平台的安装包[下载](https://redisdesktop.com/download)

安装完成之后打开软件，点击左上角的 【连接到Redis服务器】，填入ip地址、端口和密码，然后点击左下角【测试连接】

![redis desktop manager]({{site.baseurl}}/assets/images/post/linux/ubuntu/redis desktop manager.png)

如上图所示，表示你已经连接成功，你可以使用工具对redis进行操作


[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})
