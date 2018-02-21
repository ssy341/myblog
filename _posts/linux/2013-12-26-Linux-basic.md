---
categories: linux
tags: [linux命令,鸟哥私房菜]
title: linux查看文件基本命令 ls pwd
date: 2013-12-26
---

如果不清楚命令的具体用法，可以在命令后跟上 `--help`

如果记不起命令了，直接输入 `help`

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

pwd 列出当前路径

