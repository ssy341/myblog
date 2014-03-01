---
layout: md
categories: [linux]
tags: [command]
title: linux常用标签
---
解压：`tar xvfz jdk.tar.gz` 
`zip -r 1.zip a.txt rule` 
`unzip yasuo.zip`
`unzip abc\?.zip` ?表示一个字符，如果用*表示任意多个字符
`unzip -v large.zip`
`unzip -t large.zip`
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

