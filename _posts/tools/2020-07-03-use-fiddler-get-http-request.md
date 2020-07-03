---
title: 移动端怎么用fiddler抓包
categories: tools
tags: [fiddler,tools,抓包,移动端]
---

## 前言

说到抓包，这里还得提一下[Chrome devtools][network]。前端开发少不了接口调试，发送的任何一个请求都可以在这个面板看到，这样就可以很清楚知道请求和返回。

![internet]({{site.baseurl}}/assets/images/post/tools/fiddler/panes.png)

如上图所示，是不是很强大？

PC上开发调试有这么方便的工具，那移动端上可以用什么方式可以这么方便的查看请求很返回，也有这么方便的工具么，答案当然是有的。

[Fiddler][fiddler]是一个用于HTTP调试的代理服务器应用程序。使用这个软件，他可以查看到电脑上所有的HTTP请求。那么chrome可以查看一个网站所有的HTTP请求，而Fiddler不仅能查看到chrome能看到的，他还能看到chrome看不到的，是不是非常强大。下面就来介绍下Fiddler如何抓包移动端APP的数据包。

## 代理

说到移动端抓包，不得不提一个概念，就是代理（网络代理）。这个词语很好理解，就是找别人代做一件事情叫代理。那手机和PC是完全两个不同的设备，怎么才能到pc上看到手机上的请求数据呢？乍看好像没法做到，一点联系都没有啊？但要说联系，他们还是有联系的，只要你的pc和手机处于同一个网络下，他们就可以通过网络联系上了。看如下一张图片：

![internet]({{site.baseurl}}/assets/images/post/tools/fiddler/fiddler-internet.jpg)


可以看出正常情况下，手机的网络访问路径是 B -> A -> E -> F 然后访问到 Internet

而这里说到的代理就是指使用Fiddler这个软件代理手机的网络访问，所以代理后的网络访问路径变成

B -> A -> D -> C -> Fiddler -> C -> D -> E -> F 然后访问到 Internet

通俗来讲，就是手机的网络通过了pc的一个软件打了一个转才访问到了internet，虽然路程远了一点点，但是这样不正好达到我们想要的目的么，这样我们就可以让Fiddler来检测手机发送的HTTP请求了。

## 如何让手机通过Fiddler呢？

### 1，设置Fiddler允许远程设备连接

打开fiddler，按照操作路径 【Tools】 - 【Options】 - 【Connections】 可以看到如下图所示：

![options]({{site.baseurl}}/assets/images/post/tools/fiddler/fiddler-options.jpg)

勾选 【Allow remote computers to connect】，完成这步操作即可让其他设备通过Fiddler访问网络

需要注意的是，Fiddler默认监听的是8888端口，确保该端口没有被其他应用占用或者设置了防火墙，也可以修改成其他端口，保险起见可以改为10000以上的端口，这样冲突的几率会大大降低。这里先暂时用默认的8888端口，记住这个端口，在下面设置会使用

### 2，查看本机IP地址

这个很简单，只需要通过ipconfig命令即可。效果如下图所示：

![ipconfig]({{site.baseurl}}/assets/images/post/tools/fiddler/fiddler-ipconfig.jpg)

从图中可以看出，本机的ip地址为：192.168.101.143，记住这个ip地址，在下面的设置需要使用。

### 3，设置手机代理

打开手机的网络设置，在这里以ios手机为例，安卓的大小异同

图一点击已连接网络的详情 -> 图二滑倒最底部，【配置代理】 -> 图三选择【手动】,在【服务器】和【端口】分别填写上192.168.101.143和8888 -> 点击右上角存储，保存更改，如下图所示：

![ipconfig]({{site.baseurl}}/assets/images/post/tools/fiddler/fiddler-iphone.jpg)

需要注意的是，在不用测试后，需要回到这里把代理关闭，否则会影响手机正常上网。

### 4，对手机端进行抓包测试

这里以微信APP为例，当我手机打开微信后，查看朋友圈，我可以看到微信发出的http请求，如下图所示：


![ipconfig]({{site.baseurl}}/assets/images/post/tools/fiddler/fiddler-network.jpg)


## 总结

通过对代理的了解之后，利用Fiddler这个软件就可以实现对移动端的抓包，你学会了么？是不是很简单呢？



[network]: https://developers.google.com/web/tools/chrome-devtools#%E7%BD%91%E7%BB%9C%E9%9D%A2%E6%9D%BF
[fiddler]: https://www.telerik.com/fiddler