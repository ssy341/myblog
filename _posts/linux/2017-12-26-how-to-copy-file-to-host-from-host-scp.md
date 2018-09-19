---
categories: linux
tags: [linux命令,鸟哥私房菜]
title: 如果从一个主机复制文件到另一个主机：scp 
date: 2017-12-26
toc: false
---

在linux下复制文件通常使用`cp`命令完成，今天介绍另外两个命令`scp`,`rsync`

在操作服务器的时候，要求把a服务器的文件备份到b服务器上来，最开始想的就是通过ftp先把文件下载的本地，然后
上传到另一个服务器，由于文件太大，放弃了这个想法，开始搜索其他办法，在查看了鸟哥私房菜工具书后，得知今天要
讲的这两个命令，在两台主机上直接进行复制操作，有一种"山重水复疑无路，柳暗花明又一村"的感觉，我马上试试，果然好用。
下面分享下这两个命令的用法：

```bash
scp [-pr] [-l 速率] file [账号@]主机:目录名  <== 上传
scp [-pr] [-l 速率] [账号@]主机:file 目录名   <== 下载
# 选项与参数：
# -p 保留文件原有的权限信息
# -r 复制来源为目录时，可以复制整个目录（包含子目录）
# -l 可以限制传输的速率，单位为 Kbits/s ，例如 [-l 800] 代表传输速率 100KKbytes/s 
```

- 示例一：从远程服务器复制单个文件到本地目录 

```bash
scp ubuntua@hostname:/home/ubuntua/images/1.jpg /home/ubuntub/images/ 
```
说明： 从hostname机器上的/home/ubuntua/images/的目录中下载 1.jpg 文件到本地/home/ubuntub/images/ 目录中

- 示例二：从远程复制目录到本地目录 

```bash
scp -r ubuntua@hostname:/home/ubuntua/images /home/ubuntub/
```
说明： 从hostname机器上的/home/ubuntua/中下载images目录到本地的/home/ubuntub/目录来。

- 示例三：复制本地文件到远程机器指定目录 

```bash
scp /home/ubuntub/images/1.jpg ubuntua@hostname:/home/ubuntua/images
```
说明： 复制本地/home/ubuntub/images/目录下的文件1.jpg 到远程机器hostname的/home/ubuntua/images目录

- 示例四：复制本地目录到远程机器指定目录 

```bash
scp -r /home/ubuntub/images ubuntua@hostname:/home/ubuntua
```
说明： 上传本地目录 /home/ubuntub/images到远程机器hostname上/home/ubuntua的目录中

rsync的使用参考[这里]({{ site.baseurl }}{% post_url linux/2017-12-26-how-to-copy-file-to-host-from-host-rsync %})



参考：

[鸟哥私房菜：scp](http://cn.linux.vbird.org/linux_server/0310telnetssh.php#scp)

[scp命令使用](https://linuxtools-rst.readthedocs.io/zh_CN/latest/tool/scp.html)