---
layout: single
title: jekyll初级入门-jekyll安装运行
date: 2014-4-25
category: jekyll
tags: [jekyll,jekyll入门]
---
![jekyll-logo]({{site.baseurl}}/assets/images/logo-2x.png)

> jekyll['dʒekil; 'dʒi:kil] - 官方解释为"把你的纯文本转换为静态网页和blog",可以读"杰克"或者"吉克'。

<!--more-->


说到jekyll，我也是无意之间接触到的，以前都用svn管理代码，中国近几年【开源】也是越来越旺盛，不过我们不得不感谢开源给我们带来的好处。
说到开源，有些人就会想起github（简单说就是一个在线托管代码的），上面托管了很多优秀的开源软件，给我们程序员带来极大的便利，再次感谢开源。
从github开始，我管理代码的方式也从svn到git了，通过后面的学习，发现git确实有独特之处，让我一下子爱上了她。

github是外国人做的，她的功能不简单，不仅仅可以托管代码，他还提供了一个生成demo的静态网页的功能，这个就是jekyll的功劳。
jekyll是ruby上的一款插件（ps：我对于ruby不熟悉，如果对ruby的描述上有误的还请指出）
基于jekyll的性质，在github上搭建免费、不限流量的blog貌似就火起来了，有些程序员爱折腾，不喜欢那些已经很成熟的blog系统，比如c**n,博客*等等，即使jekyll提供的功能没有那些丰富、高级，但是用来做blog，我觉得已经非常好了。

本站就是使用jekyll，不过在这里给大家提醒下，虽然github上搭建blog是免费的，但是对于我们中国人来说，免费好像就是无限制的压榨，像之前github上就因为一个js的引用，导致中国ip暂时不能访问github，所以我觉得开源，免费我们还得好好像外国人学习。
之前我也是在github上弄的，后面还是自己购买了空间，而且速度还快些，毕竟github是国外的服务器。


说了这么多废话，还么进入正题呢？呵呵！其实我也是比较爱折腾的，而且希望能一直折腾下去.
我在使用jekyll的过程中有遇到问题和困难，自己搜索记录下来，下面就简单讲讲怎么安装jekyll搭建自己的网站。


安装jekyll之前要安装ruby环境，ruby安装很简单，在网站下载ruby核心包和开发包，解压后配置下环境变量即可使用，下面是在windows下安装ruby

相关包下载地址：[http://rubyinstaller.org/downloads/](http://rubyinstaller.org/downloads/)

下载自己对应系统的版本即可，我是64位的操作系统，下载的
- [Ruby 2.0.0-p451 (x64)](http://dl.bintray.com/oneclick/rubyinstaller/ruby-2.0.0-p451-x64-mingw32.7z?direct)
- [DevKit-mingw64-64-4.7.2-20130224-1432-sfx.exe](http://cdn.rubyinstaller.org/archives/devkits/DevKit-mingw64-64-4.7.2-20130224-1432-sfx.exe)

下载完后，把ruby放在d:/tool/ruby下，devkit放在d:/tool/devkit,接下来打开命令行(开始键+r，输入cmd)

然后添加环境变量：

RUBY_HOME  d:/tool/ruby

修改path环境，在最后追加 %RUBY_HOME%/bin

保存环境变量，再到命令行输入 `ruby --version`，可以看到打印出版本信息

```bash
d:\tool\ruby>ruby --version
ruby 2.0.0p451 (2014-02-24) [i386-mingw32]
```

这里配置好后，cmd下进入到devkit目录


```bash
d:\tool>cd devkit
d:\tool\devkit>ls
bin         devkitvars.bat  dk.rb  include  m.ico  msys.bat  postinstall  share
devkitvars.ps1  etc    lib      mingw  msys.ico  sbin
```


执行初始化命令, 执行完后可以看到有config.yml文件生成


```bash
d:\tool\devkit>ruby dk.rb init
d:\tool\devkit>ls
bin         devkitvars.bat  dk.rb  include  m.ico  msys.bat  postinstall  share
config.yml  devkitvars.ps1  etc    lib      mingw  msys.ico  sbin
```


打开config.yml文件，配置ruby的目录

```bash
---
- d:\tool\ruby
```

保存退出，然后再执行
`ruby dk.rb install`


到这里ruby的环境已经安装完毕，你可以查看相关信息，比如gem版本

```bash
d:\tool\devkit>gem --version
2.2.2
```

本地安装了那些插件

```bash
E:\tools\devkit>gem list --local

*** LOCAL GEMS ***

bigdecimal (1.2.0)
blankslate (2.1.2.4)
celluloid (0.15.2)
celluloid-io (0.15.0)
classifier (1.3.4)
colorator (0.1)
commander (4.1.6)
fast-stemmer (1.0.2)
ffi (1.9.3 x86-mingw32)
highline (1.6.21)
io-console (0.4.2)
jekyll (1.5.1)
json (1.7.7)
liquid (2.5.5)
listen (2.7.1, 1.3.1)
maruku (0.7.0)
minitest (4.3.2)
nio4r (1.0.0)
parslet (1.5.0)
posix-spawn (0.3.8)
psych (2.0.5, 2.0.0)
pygments.rb (0.5.4)
rake (0.9.6)
rb-fsevent (0.9.4)
rb-inotify (0.9.3)
rb-kqueue (0.2.2)
rdoc (4.0.0)
redcarpet (2.3.0)
rubygems-update (2.2.2)
safe_yaml (1.0.2)
test-unit (2.0.0.0)
timers (1.1.0)
toml (0.1.1)
wdm (0.1.0)
yajl-ruby (1.1.0 x86-mingw32)
```

以上步骤安装完后，在命令行输入`gem install jekyll`，等待自动安装完成，成功之后，再输入`jekyll --version`查看版本

接下来最神奇的时刻来到了，在命令行下随便进入一个目录，这里假设是d:/,输入`jekyll new myblog`，这时会在该目录下生成一个myblog的文件夹，先不管，命令行进入该目录`cd myblog`，再输入`jekyll serve `,这个时候打开浏览器访问`http://localhost:4000`

怎么样，是不是很神奇，一个简单的站点就完成了，如果你需要更好的使用jekyll，下面有官方的api，有详细的介绍，相信只要按照api来，你很快就会创建出属于自己的站点，just do it

如果遇到问题，在文章下方留言，我尽快回复，或者直接发邮件和在微博私信我

附：
- [git基本操作]({{site.baseurl}}/git/2013/12/18/git.html)
- [jekyll英文官方文档](http://jekyllrb.com/docs/home/)
- [jekyll中文官方文档](http://jekyllcn.com/docs/home/)