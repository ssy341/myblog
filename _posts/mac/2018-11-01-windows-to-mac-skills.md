---
title: 一个Java Developer从windows转mac的那点事
categories: mac
tags: [windows,mac]
---

## 前言

从工作以来一直都是使用windows系统，随着工作时间的增长，身边还有使用mac的。都说开发使用mac是绝配，我也没有体验过，只是听说，直到自己
真实体验了mac后，一发不可收拾，系统的流畅，让我欲罢不能。

不过这么说也有点偏激，但是我也没办法，来看看为什么会这样。

- 我第一台笔记本（联想 y450）购于2009年10月，退役与2017年2月（没有坏，只是慢了点，除了开发，其他的事情还是杠杠滴）。

- 然后接着又购买了一台超级本（联想 yoga 910），使用她开发了接近2年。但由于是低压的cpu（很后悔买了这个笔记本做开发，当时不知道怎么想的，买了个低压的），
以至于我稍微开多了程序，她就会表现的很不好，让我对低压的笔记本已经很抵触。

> ps:做开发的一定不要买低压的cpu，一定！！！

- 2018年3月我头脑发热，在苹果官网下单了15寸的MacBook Pro（16年款，￥26,488），本来满怀期待，结果第二天早晨我又把订单取消了，原因是朋友告诉我马上要上新一代的cpu了，
现在买就太亏了。我想想，16年我没买，我选择18年买，都又要出新的了，确实，当时也没了解清楚，头一热就下单买了，可能是因为招行12期分期免息，
让我霸气的就下单了（其实这样是不对的，爽了一时，要痛苦1年，面对接下来每个月2k的还款，我又是多么的伤心）

- 就在2018年7月份，苹果发布了18年款的MacBook Pro。cpu确实上了新一代的，而且还有更加强劲的i9处理器，这把我心动的，但是有点追求完美的我，不满足，依然忍住了，
没有立马就去购买了。在接下来的几个月，我看了各位大神的使用体验，以及评价，发现虽然有了强劲的i9处理器，但因为框架没有更新，承受不住他的发热，导致i9达到一定温度后，
会被降频，而不能发挥他本身的性能，这个让要求完美的我，更加纠结了。

- 纠结了几个月，最终，我抵挡不住诱惑，2018年11月我在网上租了一台MacBook Pro 14年款（ 2.2 GHz Intel Core i7，16G，256G ssd ，15寸），决定先试后买，这样就不会后悔了啊！
11月2号收到笔记本后，很是激动！虽然是14年款，但是配置还是挺不错的，我不知道我这个时候要是有一台同配置的windows，使用起来是不是同样的感觉，快，舒服？如果有机会，
我再去体验下高配置的windows再说吧。目前来说这台mac已经让我明显感觉用起来很舒服。使用了短短两周，已经喜欢上mac这种感觉，当我再次翻开我的yoga 910时，我已经受不了windows的卡顿，
以及从睡眠中唤醒，要等很久才会流畅起来，而mac则像一个丝滑的巧克力在嘴中融化的感觉。

不过联想yoga 910和这款mac本身没有可比性，所以上面说的所有就不要当真啦。本篇文章的正题是下面即将要说的这些。

毕竟使用了那么多年windows，一下子切换到mac下很多不习惯和不适应，稍不留神就把windows的习惯带到mac下来，没办法遇到问题只能网上搜一搜。鉴于后面我要购买自己的mac，
我把我这个期间遇到的问题记录下来，方便后面再去查。

下面这些就是我从windows切换到mac后不懂的，我总结下来，希望能帮助到需要的人。如果你一开始就是使用mac，本篇文章你也能收获很多。
我分为以下几个大块，有简单的也有难一点的，没办法，谁叫我是一个开发呢？太喜欢喜欢折腾了……


## 系统篇
- 打开终端
- [修改hosts][system-how-to-modify-host-in-mac]
- 更改mac的名字
- 端口占用
- tomcat启动提示permission denied
- smb window 和 mac 互传文件
- 在Finder中查看文件大小

 
## 快捷键篇
- 更改fn的行为
- insert按键
- delete反方向删除 fn+delete
- ctrl+x    commond+c   alt+commond+v
- 重命名文件 回车
- 新建文件夹 commond+shift+n
- 打开文件/文件夹 commond+o

 
## 硬件篇
- [滚轮方向][hardware-how-to-change-wheel-direction-in-mac]
- [触控板设置][hardware-touch-pad-function]
 
## 开发篇
- 安装java环境
- [在虚拟机里安装windows10][install-windows10-in-virtualbox]
- [安装virtualbox增强插件实现剪贴板共享，文件拖拽，文件夹共享（windows10）][install-virtualbox-additions-for-windows]
- [在虚拟机里安装ubuntu Server][install-ubuntu-server-in-virtualbox]
- [安装virtualbox增强插件实现文件夹共享（Ubuntu Server）][install-virtualbox-additions-for-ubuntu-server]
- mac安装最新rvm 升级 ruby

## 常用软件
- 上网
    - chrome
    - 迅雷
- 娱乐
    - 视频播放
- 社交
    - qq
    - 微信
    - 钉钉
- 办公
    - wps for Mac
    - 有道云笔记
    - 网易邮箱大师
- 工具
    - ftp软件
    - 终端软件
    - 虚拟机
    - 文本编辑器
    - 有道词典
    - OneDrive
    - intellij idea
    
## mac独有功能
- dashboard使用
    
    
[system-how-to-modify-host-in-mac]: {{ site.baseurl }}{% post_url mac/2018-11-03-system-how-to-modify-host-in-mac %}
[hardware-how-to-change-wheel-direction-in-mac]: {{ site.baseurl }}{% post_url mac/2018-11-03-hardware-how-to-change-wheel-direction-in-mac %}
[install-virtualbox-additions-for-windows]: {{ site.baseurl }}{% post_url mac/2018-11-05-install-virtualbox-additions-for-windows %}
[install-windows10-in-virtualbox]: {{ site.baseurl }}{% post_url mac/2018-11-05-install-windows10-in-virtualbox %}
[install-ubuntu-server-in-virtualbox]: {{ site.baseurl }}{% post_url mac/2018-11-05-install-ubuntu-server-in-virtualbox %}
[hardware-touch-pad-function]: {{ site.baseurl }}{% post_url mac/2018-11-04-hardware-touch-pad-function %}
[install-virtualbox-additions-for-ubuntu-server]: {{ site.baseurl }}{% post_url mac/2018-11-05-install-virtualbox-additions-for-ubuntu-server %}
