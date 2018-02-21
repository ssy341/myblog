---
layout: single
title: jekyll支持中文解决办法
date: 2014-4-17
categories: jekyll
tags: [jekyll中文]
---

如果文件里包含中文，会报如下错误：
```bash
F:\xxx\jekyll server
Configuration file: F:/xxx/_config.yml
            Source: F:/xxx
       Destination: F:/xxx/_site
      Generating... Error reading file F:/xxx/_layouts/post.html: invalid
te sequence in GBK
error: invalid byte sequence in GBK. Use --trace to view backtrace
```

 <!--more-->

在网上找到一个谁都能找到的解决方法，进入如下路径

`E:\tools\ruby\lib\ruby\gems\2.0.0\gems\jekyll-1.5.1\lib\jekyll`

打开convertible.rb文件，找到如下代码：


```bash
self.content = File.read_with_options(File.join(base, name),
                                             merged_file_read_opts(opts))
```

ps:不知道是不是版本的原因，网上的都是
```bash
self.content = File.read_with_options(File.join(base, name)）
```

没有我上面贴出的后面一节，一开始找到这个代码，不知道如何修改，以为是错误的，去掉后面一节修改为：
```bash
self.content = File.read_with_options(File.join(base, name), :encoding => "utf-8")
```

然后在`_config.yml` 文件里加上 `encoding: utf-8` 属性，中文问题即可解决
