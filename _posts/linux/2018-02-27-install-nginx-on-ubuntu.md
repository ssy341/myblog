---
title: 在Ubuntu下安装Nginx
categories: linux
tags: [ubuntu,nginx]
---


Nginx是一个异步框架的 Web服务器，也可以用作反向代理，负载平衡器 和 HTTP缓存。

## 安装Nginx

在Ubuntu下安装Nginx非常简单，只需要几行命令即可安装完成

```bash
sudo apt-get install nginx
```

默认安装后会自己启动，打开浏览器访问127.0.0.1即可看到nginx默认的欢迎界面

ps：确认80端口没有被其他应用程序占用

> Reference
> - https://www.jianshu.com/p/7cb1a824333e


## Permission denied

由于我使用Nginx对本地路径做映射，默认情况下nginx会创建自己的用户名和用户组，这样我用非Nginx的用户创建的文件可能会不能访问，
出现Permission denied的错误提示，这里我修改了配置文件让nginx使用同样的用户

配置文件一般会在
```bash
/etc/nginx/nginx.conf
```

这个文件第一行即是

```bash
user nginx; # 修改成自己的用户，这里我用户名是ubuntu
#下面是修改后的
user ubuntu;
```

修改完后保存退出，然后重启nginx即可
```bash
sudo service nginx restart
```



## 如何安装最新版Nginx

#### 2018年7月7日 更新

默认情况下，通过命令安装的Nginx不是官方的最新版本，而是一个相对稳定的版本（1.10.0）。

在项目中我需要用到gRPC，希望也能通过Nginx来进行负载均衡，发现Nginx从1.13.10版本才开始支持gRPC，迫使我要升级Nginx

哪怎么通过命令安装官方最新的Nginx呢？下面来告诉大家，希望能帮助到需要的小伙伴


- 第一步：找到最新的[发行版](https://nginx.org/en/linux_packages.html) ,添加源到本地apt-get sources列表中，
如果你的系统是ubuntu 16.04，则可以使用下面的源
```bash
echo 'deb http://nginx.org/packages/mainline/ubuntu/ xenial nginx' | sudo tee /etc/apt/sources.list.d/nginx.list
```

ps: 如果是Ubuntu 14.04就把`xenial`换成`trusty`，附一张对照表

系统版本 | Codename | 支持平台
---|---|---
12.04 | precise | x86_64, i386
14.04 | trusty | x86_64, i386, aarch64/arm64
15.10 | wily   | x86_64, i386
16.04 | xenial | x86_64, i386

- 第二步：添加秘钥
```bash
wget -O- http://nginx.org/keys/nginx_signing.key | sudo apt-key add
```

- 第三步：更新源
```bash
sudo apt-get update
```

- 第四步：安装最新Nginx
```bash
sudo apt-get install nginx
```

- 第五步：查看版本
```bash
nginx -v
nginx version: nginx/1.15.0
```

可以看到，已经是最新的1.15.0

[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})


> Reference
> - https://juejin.im/entry/5a274c7751882533d022eec2