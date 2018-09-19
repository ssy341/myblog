---
categories: linux
tags: [linux命令,鸟哥私房菜]
title: 如果从一个主机复制文件到另一个主机：rsync 
date: 2017-12-26
toc: false
---

`rsync`这个命令仅仅用来复制文件，是有点大材小用了，在我了解之后，它的用途太强大了。

> `rsync` 可以作为一个相当棒的异地备援系统的备份指令！ 因为 `rsync` 可以达到类似『镜相 (mirror) 』的功能！
> `rsync` 最早是想要取代 `rcp` 这个指令的，因为 `rsync` 不但传输的速度快，而且他在传输时， 
> 可以比对本地端与远程主机欲复制的档案内容，而仅复制两端有差异的档案而已，所以传输的时间就相对的降低很多！
 
虽然我没有去查，但根据上面的描述，我觉得rsync就是remote synchronization的简写，远程同步。没想到让我知道一个这么强大的命令。

`rsync` 语法如下：
 
```bash
rsync [-avrlptgoD] [-e ssh] [user@host:/dir] [/local/path]
选项与参数：
# -v ：观察模式，可以列出更多的信息，包括镜像时的档案档名等；
# -q ：与 -v  相反，安静模式，略过正常信息，仅显示错误讯息；
# -r ：递归复制！可以针对『目录』来处理！很重要！
# -u ：仅更新 (update)，若目标档案较新，则保留新档案不会覆盖；
# -l ：复制链接文件的属性，而非链接的目标源文件内容；
# -p ：复制时，连同属性 (permission) 也保存不变！
# -g ：保存源文件的拥有群组；
# -o ：保存源文件的拥有人；
# -D ：保存源文件的装置属性 (device)
# -t ：保存源文件的时间参数；
# -I ：忽略更新时间 (mtime) 的属性，档案比对上会比较快速；
# -z ：在数据传输时，加上压缩的参数！
# -e ：使用的信道协议，例如使用 ssh 通道，则 -e ssh
# -a ：相当于 -rlptgoD ，所以这个 -a 是最常用的参数了！ 
```


- 示例一：将 /etc 的数据备份到 /tmp 底下：

```bash
rsync -av /etc /tmp
```
说明： 第一次运作时会花比较久的时间，因为首次建立嘛！如果再次备份呢？



- 示例二：利用 ubuntu 的身份登入 hostname 将home目录复制到本机 /tmp

```bash
rsync -av -e ssh ubuntu@hostname:~ /tmp
```
ps： ~和home是一个意思

scp的使用参考[这里]({{ site.baseurl }}{% post_url linux/2017-12-26-how-to-copy-file-to-host-from-host-scp %})


参考：

[鸟哥私房菜：rsync](http://cn.linux.vbird.org/linux_server/0310telnetssh.php#rsync)
