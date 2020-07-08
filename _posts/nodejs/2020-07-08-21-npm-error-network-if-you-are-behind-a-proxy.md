---
title: npm ERR! network If you are behind a proxy
---

## 问题背景

今天通过npm安装vue，由于很久没有用npm安装东西了，输入命令后，等了很久，然后得到如下错误：

```bash
thxopenwork@MacBook-Pro ~ % npm install vue                                                         
npm ERR! code ETIMEDOUT
npm ERR! errno ETIMEDOUT
npm ERR! network request to https://registry.npm.taobao.org/vue failed, reason: connect ETIMEDOUT 192.168.101.219:1080
npm ERR! network This is a problem related to network connectivity.
npm ERR! network In most cases you are behind a proxy or have bad network settings.
npm ERR! network 
npm ERR! network If you are behind a proxy, please make sure that the
npm ERR! network 'proxy' config is set properly.  See: 'npm help config'

npm ERR! A complete log of this run can be found in:
npm ERR!     /Users/thxopenwork/.npm/_logs/2020-07-08T04_35_13_813Z-debug.log
```

得到错误我也没有细看，就看到最后一行有一个错误日志生成，于是我打开错误日志如下：

```bash
thxopenwork@MacBook-Pro ~ % tail -f /Users/thxopenwork/.npm/_logs/2020-07-08T04_25_31_524Z-debug.log
17 verbose npm  v6.9.0
18 error code ETIMEDOUT
19 error errno ETIMEDOUT
20 error network request to https://registry.npm.taobao.org/vue failed, reason: connect ETIMEDOUT 192.168.101.219:1080
21 error network This is a problem related to network connectivity.
21 error network In most cases you are behind a proxy or have bad network settings.
21 error network
21 error network If you are behind a proxy, please make sure that the
21 error network 'proxy' config is set properly.  See: 'npm help config'
22 verbose exit [ 1, true ]
```

看到日志最后一条，我贴上google搜索了一下，找到 [npm ERR! network If you are behind a proxy #7945][github]，我点进去查阅了一番，提到最多的就是代理。

## 发现问题

我思考了一会儿，想着自己之前是不是设置过什么代理，我查看了本机的http代理设置：
```bash
thxopenwork@MacBook-Pro ~ % echo $http_proxy

```
得到一个空的结果，我又查看https的：
```bash
thxopenwork@MacBook-Pro ~ % echo $https_proxy

```
同样也是空的结果，于是我查看了npm的配置：

```bash
thxopenwork@MacBook-Pro ~ % npm config list
; cli configs
metrics-registry = "https://registry.npm.taobao.org/"
scope = ""
user-agent = "npm/6.9.0 node/v12.1.0 darwin x64"

; userconfig /Users/thxopenwork/.npmrc
proxy = "http://192.168.101.219:1080/"
registry = "https://registry.npm.taobao.org/"

; builtin config undefined
prefix = "/usr/local"

; node bin location = /usr/local/Cellar/node/12.1.0/bin/node
; cwd = /Users/thxopenwork
; HOME = /Users/thxopenwork
; "npm config ls -l" to show all defaults.
```

果然就是设置了代理！！！

原来是我不知道什么时候给npm设置了一个代理，一看ip地址，还是一个本地的`proxy = "http://192.168.101.219:1080/"`，知道问题所在，那问题就好解决了

## 解决问题

由于代理地址已经失效，我需要安装的资源国内也有，所以不需要设置代理，我去掉npm的代理设置即可：

```bash
thxopenwork@MacBook-Pro ~ % npm config rm proxy
```

> 后期如果需要重新配置代理，则使用 `npm config set proxy http://<proxy.company.com>` 即可

到此问题得到解决


## 后记

其实在最开始就已经暴露出问题，在日志中有这么个信息`npm ERR! network request to https://registry.npm.taobao.org/vue failed, reason: connect ETIMEDOUT 192.168.101.219:1080`，已经很明显表示连接出了问题，但我由于很久没有配置了，所以对这个没有在意这个信息，饶了一圈回来还是代理的问题。

**一顿操作猛如虎，仔细一看原地杵~~~**

总结，出现问题不要慌张，冷静认真对待每一行报错，抓住每个细节，你就能解决问题










[github]: https://github.com/npm/npm/issues/7945 