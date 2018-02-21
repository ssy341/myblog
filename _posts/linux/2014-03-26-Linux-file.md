---
categories: linux
tags: [linux命令,鸟哥私房菜]
title: linux 文件操作命令 rmdir mkdir rm 
date: 2014-3-26
---

rmdir 删除一个空的目录
● -v 选项
提示删除操作成功
● -p 选项
如果一个目录及其子目录都是空的，其中在删除最子目录的时候，使用-p选项，则这些相关的目录都会被删除掉。


mkdir filename 新建文件夹

rmdir filename 删除目录

rm -rf filename 删除文件/目录


cp -r  a.txt /home 复制文件或目录
语法：cp [参数] 源文件目录 目标文件或目录 
