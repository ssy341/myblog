---
categories: linux
tags: [fstab,鸟哥私房菜]
title: ubuntu server 恢复模式下修改/etc/fstab文件
date: 2014-3-30
toc: false
---

由于自己操作不慎，在修改挂载目录信息的时候少些了一个字母，导致系统不能启动，下面介绍怎么在恢复模式下修改/etc/fstab文件

首先进入ubuntu server的启动选项，一共四个，第二个就是恢复模式，根据下面的提示，按e键进入启动参数的编辑模式，
其中有一句 `no recovery nomodeset`，把它替换为`rw single init=/bin/bash`

然后按ctrl+x进入系统，接下里的操作都是在root下进行

```bash
vi /etc/fatab
```

修改完后保存退出，重启系统即可


参考：

[ubuntu 进入单用户模式，修改sudoers权限，修改root密码](http://blog.csdn.net/gudaoqianfu/article/details/7254700)



