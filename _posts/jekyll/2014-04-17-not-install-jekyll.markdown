---
layout: single
title: jekyll安装出现错误
date: 2014-4-17
categories: jekyll
tags: [jekyll]
---

在本地部署jekyll环境，不仅要安装ruby，还要安装devkit（网上都这么说，具体不知什么原因，个人觉得是ruby的开发环境？暂时不知）

 <!--more-->

我电脑上的ruby和devkit是在这里下载 [http://rubyinstaller.org/downloads/](http://rubyinstaller.org/downloads/) 

下载和电脑对应的版本，我是64位的操作系统，下载的

- [Ruby 2.0.0-p451 (x64)](http://dl.bintray.com/oneclick/rubyinstaller/ruby-2.0.0-p451-x64-mingw32.7z?direct)
- [DevKit-mingw64-64-4.7.2-20130224-1432-sfx.exe](http://cdn.rubyinstaller.org/archives/devkits/DevKit-mingw64-64-4.7.2-20130224-1432-sfx.exe)


这个我在公司的电脑里安装没有问题，但是在我笔记本上同样都是64位的操作系统，却出现问题，配置好ruby的环境后，执行

```bash
gem install jekyll`
```
        
会报错，我查看错误日志，gem_make.out 文件如下
        
```bash
E:/tools/ruby/bin/ruby.exe extconf.rb
	creating Makefile

make "DESTDIR=" clean

make "DESTDIR="
generating stemmer-i386-mingw32.def
compiling porter.c
porter.c: In function 'step1ab':
porter.c:233:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:234:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:234:7: warning: passing argument 2 of 'setto' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:196:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:237:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:238:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:238:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:240:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:240:7: warning: passing argument 2 of 'setto' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:196:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:241:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:241:7: warning: passing argument 2 of 'setto' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:196:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:242:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:242:7: warning: passing argument 2 of 'setto' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:196:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:249:7: warning: passing argument 2 of 'setto' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:196:13: note: expected 'char *' but argument is of type 'const char *'
porter.c: In function 'step1c':
porter.c:257:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c: In function 'step2':
porter.c:267:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:267:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:268:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:268:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:270:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:270:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:271:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:271:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:273:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:273:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:275:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:275:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:280:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:280:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:281:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:281:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:282:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:282:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:283:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:283:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:285:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:285:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:286:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:286:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:287:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:287:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:289:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:289:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:290:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:290:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:291:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:291:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:292:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:292:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:294:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:294:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:295:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:295:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:296:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:296:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:298:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:298:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c: In function 'step3':
porter.c:308:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:308:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:309:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:309:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:310:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:310:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:312:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:312:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:314:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:314:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:315:14: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:315:14: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c:317:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:317:4: warning: passing argument 2 of 'r' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:205:13: note: expected 'char *' but argument is of type 'const char *'
porter.c: In function 'step4':
porter.c:325:4: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:326:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:327:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:328:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:329:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:330:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:331:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:332:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:333:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:334:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:335:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:336:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:337:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:339:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:340:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:341:17: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:342:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:343:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
porter.c:344:7: warning: passing argument 2 of 'ends' discards 'const' qualifier from pointer target type [enabled by default]
porter.c:182:12: note: expected 'char *' but argument is of type 'const char *'
compiling porter_wrap.c
porter_wrap.c: In function 'stem_word':
porter_wrap.c:20:17: warning: unused variable 'i' [-Wunused-variable]
linking shared-object stemmer.so

make "DESTDIR=" install
/usr/bin/install -c -m 0755 stemmer.so ./.gem.20140409-3484-15df381
installing default stemmer libraries
```

看上去是编译错误，无奈只好求助google，搜了一片，和我这个问题沾不上边，虽然都是同样的错误，不过有个说道可能是devkit版本的问题，抱着试试的心态，
我下载了32位的devkit，重新安装jekyll没有出现上述错误，安装一路ok

可是公司也是这样的环境能成啊，这是什么原因呢？


附：更换ruby源，解决`gem install`可能安装速度慢或者失败[点我]({{site.baseurl}}/jekyll/2014/04/17/speed-gem-source.html)

