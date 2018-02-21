---
categories: linux
tags: [linux命令]
title: linux 文件操作命令
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


tar/zip 解压
tar xvfz jdk.tar.gz
zip -r 1.zip a.txt rule
unzip yasuo.zip
unzip abc\?.zip` ?表示一个字符，如果用*表示任意多个字符
unzip -v large.zip
unzip -t large.zip
unzip -j music.zip
