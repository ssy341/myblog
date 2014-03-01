---
layout: md
categories: [linux]
tags: [command]
title: linux常用标签
---
解压：`tar xvfz jdk.tar.gz`

demo：

zip命令可以用来将文件压缩成为常用的zip格式。unzip命令则用来解压缩zip文件。

想把一个文件abc.txt和一个目录dir1压缩成为yasuo.zip：

`zip -r yasuo.zip abc.txt dir1`

我下载了一个yasuo.zip文件，想解压缩：

`unzip yasuo.zip`

我当前目录下有abc1.zip，abc2.zip和abc3.zip，我想一起解压缩它们：

`unzip abc\?.zip`

注释：?表示一个字符，如果用*表示任意多个字符。

有一个很大的压缩文件large.zip，我不想解压缩，只想看看它里面有什么：

`unzip -v large.zip`

我下载了一个压缩文件large.zip，想验证一下这个压缩文件是否下载完全了

`unzip -t large.zip`

用-v选项发现music.zip压缩文件里面有很多目录和子目录，并且子目录中其实都是歌曲mp3文件，我想把这些文件都下载到第一级目录，而不是一层一层建目录：

`unzip -j music.zip`


如果不清楚命令的具体用法，可以在命令后跟上 `--help`

如果记不起命令了，直接输入 `help`

新建文件夹：`mkdir filename`

删除目录：`rmdir filename`

删除文件/目录：`rm -rf filename`

复制文件或目录
语法：cp [参数] 源文件目录 目标文件或目录 `cp -r  a.txt /home`

列出当前目录下的文件：`ls`

ls -l 
列出文件的详细信息

ls -tl   
按时间排序列出文件

ls -trl  
把最近修改的文件列在最后

通用的做法
ls -l |sort +[r]n

n用日期所在列的 列数－1 替代一下，r代表反向排序

