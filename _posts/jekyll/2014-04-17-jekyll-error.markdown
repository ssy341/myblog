---
layout: single
title: 运行jekyll相关命令给出警告
date: 2014-4-17
categories: jekyll
tags: [jekyll]
---

jekyll安装完后，执行jekyll的相关命令，都报如下警告信息：

 <!--more-->
```bash
    SafeYAML Warning
  ----------------
  You appear to have an outdated version of libyaml (0.1.5) installed on your system.


  Prior to 0.1.6, libyaml is vulnerable to a heap overflow exploit from malicious YAML payloads.


  For more info, see:
  https://www.ruby-lang.org/en/news/2014/03/29/heap-overflow-in-yaml-uri-escape-parsing-cve-2014-2525/


  The easiest thing to do right now is probably to update Psych to the latest version and enable
  the 'bundled-libyaml' option, which will install a vendored libyaml with the vulnerability patched:


  gem install psych -- --enable-bundled-libyaml

```


一开始百思不得其解，在网上搜索也没有什么答案，然后仔细看看这些提示，发现在提示最后不是告诉我怎么做么？输入下面命令，问题解决，自己太粗心了

```bash
gem install psych -- --enable-bundled-libyaml
```

以后遇到错误提示，不要着急，首先简单阅读错误提示，或许就能找到答案 :)
