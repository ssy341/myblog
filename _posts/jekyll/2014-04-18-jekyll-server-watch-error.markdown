---
layout: single
title: cannot load such file -- wdm (LoadError)
date: 2014-4-18
categories: jekyll
tags: [jekyll,wdm,gem]
---


好像是更新jekyll版本后，自动检测文件更新的命令不是`--auto`了，而是`--watch`，这个也好理解，观察，有变动我就更新

<!--more-->

在之前我都是一个命令窗口执行`jekyll server`，如果代码有更新了，再到另一个窗口执行`jekyll build`，时间长了会感觉有点繁琐，于是我就是用`--watch`参数，结果并不是那么好，执行`jekyll server --watch `直接报错，如下：

```bash
	F:/xxx>jekyll server --watch
Configuration file: F:/xxx/_config.yml
            Source: F:/xxx
       Destination: ../deploy
      Generating... done.
 Auto-regeneration: enabled
E:/tools/ruby/lib/ruby/site_ruby/2.0.0/rubygems/core_ext/kernel_require.rb:55:in
 'require': cannot load such file -- wdm (LoadError)
```
 

由于对ruby不熟悉，不知道什么意思，google查询

> cannot load such file -- wdm (LoadError)

我点开了排在第一个的网址，于是结果就出了，原来是缺少包，执行`gem install wdm`

wdm -- "Windows Directory Monitor" 由这个意思看来貌似是针对windows一个插件，windows文件夹监视器，是不是在linux下就不用安装这个插件就可以了呢？下会在linux下去试试