---
title: macOS下如何修改hosts文件
categories: mac
tags: [mac,hosts]
---

hosts文件（域名解析文件）是一个用于储存计算机网络中各节点信息的计算机文件。这个文件负责将主机名称映射到相应的IP地址。
hosts文件通常用于补充或取代网络中DNS的功能。和DNS不同的是，计算机的用户可以直接对hosts文件进行控制。

由于Mac OS是属于类unix，一些能在unix下用的命令在macOS上也同样试用，而且目录结构也比较相似。

作为开发者，我推荐试用命令行方式修改hosts文件，简单快速（如果你有linux的使用经验，你也会认同的）

macOS的hosts文件存放在 `/etc` 目录下

> ps:操作下面的命令你需要有vi命令的基础才行

- 第一步：vi命令打开hosts文件（因为是系统文件，需要使用sudo，会要求输入用户的密码）

```bash
$ sudo vi /etc/hosts 
```

执行完成后是下面的样子：

```bash
##
# Host Database
#
# localhost is used to configure the loopback interface
# when the system is booting.  Do not change this entry.
##
127.0.0.1       localhost
::1             localhost

```

- 第二步：

输入 `i` 进入编辑模式，在文件末尾添加自己需要添加的映射即可，然后 `esc` 退出编辑模式，最后输入`:wq!` 保存并退出（如果不希望更改，输入
`:q!` 直接退出即可）

简单的两步操作即可完成对hosts文件的编辑

[返回目录]({{ site.baseurl }}{% post_url mac/2018-11-01-windows-to-mac-skills %})



> Reference:
> - https://zh.wikipedia.org/wiki/Hosts%E6%96%87%E4%BB%B6
> - https://www.jianshu.com/p/752211238c1b