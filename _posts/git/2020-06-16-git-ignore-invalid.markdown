---
title: git配置.gitignore无效
categories: git
tags: [git基本操作命令]
date: 2020-06-16
toc: false
---


## 问题描述

由于之前每个文件夹都是加入了版本控制的，没有在.gitignore配置，但现在需要移除某个文件夹版本控制，于是我做了如下操作，在.gitignore文件里加入了如下配置：

`_site`

发现加入配置后 `_site` 这个目录还是会处于更改的状态

## 发现问题

### 匹配规则没有书写正确

我怀疑是否匹配格式写错了，于是我查询了匹配规则如下：

- 以`/`开头表示目录；
- 以`?`通配单个字符
- 以`*`通配多个字符；
- 以方括号`[]`包含单个字符的匹配列表；
- 以叹号`!`跟踪某个文件或目录；

> git 对于.gitignore配置文件是按行从上到下进行规则匹配的，如果前面的规则匹配的范围更大，则后面的规则将不会生效

通过对规则的解读，发现我的没有加`/`，于是我把他加上.发现该目录还是没有被忽略掉，其实对于目录可以省略掉`/`，直接用文件夹的名字即可。既然没有影响就可以排除匹配规则的问题。

### .gitignore文件的作用

我又进步一查询，然后发现原来是

> .gitignore 只能忽略那些原来没有被 track 的文件，如果某些文件已经被纳入了版本管理中，则配置 .gitignore 是无效的。

了解到这个后，问题解决，先把本地缓存删除，然后再配置.gitignore，最后push到远程仓库，以后就不会对该文件夹进行版本控制了。


## 解决问题

如何移除某个文件夹在本地的缓存呢？

`git rm --cached "_site"`

通过这个命令可以删除`_site`目录在本地的缓存，到达移除版本控制的目的

注意！！

这个操作同时也会删除本地的文件和远程仓库的文件，如果文件还需要，那么我们需要添加两个参数

`git rm -r -n --cached "_site"`

> -r Allow recursive removal when a leading directory name is given.
> -n Don’t actually remove any file(s). Instead, just show if they exist in the index and would otherwise be removed by the command.
> --cached Use this option to unstage and remove paths only from the index. Working tree files, whether modified or not, will be left alone.

`-n` 只从缓存里删除文件，本地和远程仓库保留
`-r` 递归目录下所有目录
`--cached` 删除所有缓存，或指定目标文件删除

最后提交，并推送到远程仓库即可

`git commit -m" remove _site folder all file out of git control"   `
`git push origin master `


到此问题得到解决




参考：

[在git中如何停止对文件版本记录并忽略改变][stackoverflow]
[从仓库里移除文件历史记录][github]


[stackoverflow]: https://stackoverflow.com/questions/936249/how-to-stop-tracking-and-ignore-changes-to-a-file-in-git
[github]: https://help.github.com/en/enterprise/2.15/user/articles/removing-files-from-a-repositorys-history








