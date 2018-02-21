---
layout: single
title: 更换ruby源，解决gem install可能安装速度慢或者失败
date: 2014-4-17
categories: jekyll
tags: [jekyll,gem sources]
---

首先移除默认源

```bash
$ gem sources --remove https://rubygems.org/
```
 <!--more-->

再添加淘宝ruby源

```bash
gem sources -a http://ruby.taobao.org/
```

然后检查是否添加成功

```bash
gem sources -l
*** CURRENT SOURCES ***

http://ruby.taobao.org # 请确保只有 ruby.taobao.org
```


参考：[http://ruby.taobao.org/](http://ruby.taobao.org/)

