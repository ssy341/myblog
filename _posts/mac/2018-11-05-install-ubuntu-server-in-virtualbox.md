---
title: 在虚拟机里安装Ubuntu Server 16
categories: mac
tags: [ubuntu,mac,virtualbox]
---

服务器环境大部分都是linux，作为开发来说，和线上环境保持一致会比较方便，而且开发起来也会比较方便。
虽然macOS也是类unix，但终究还是有点区别，况且安装了虚拟机出了问题还可以重来。
对本身的系统保持一个好的状态，这是我们首先要做到的，想起以前学oracle的时候，在本机安装，结果没装好，连系统都要重做，那是多么痛苦的一段经历。

<!--more-->


## 准备工作

- [virtualbox](https://www.virtualbox.org/)（虚拟机软件）
- [Ubuntu Server 下载页面](https://www.ubuntu.com/download/alternative-downloads)
    - [Ubuntu 16.04.5 Server (64-bit) 种子](http://releases.ubuntu.com/16.04/ubuntu-16.04.5-server-amd64.iso.torrent?_ga=2.238927552.1516861282.1544003123-1140686435.1544003123)

通过上面的连接，下载virtualbox和ubuntu server的安装镜像，我们就可以开始了

## 开始安装

### 一，配置虚拟机

- 1，虚拟机名称和系统类型，这里系统类型选择【Linux】，版本选择【Ubuntu（64-bit）】
![step 1]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/1.png)

- 2，内存大小，这里可以根据软件推荐的大小配置，我这里推荐的是1G（1024 MB），为了系统更加流畅，我调到 4G （4096 MB）。可以根据电脑的配置适当上调
![step 2]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/2.png)

- 3，虚拟硬盘，选择【现在创建虚拟硬盘】
![step 3]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/3.png)

    - 3-1，虚拟硬盘文件类型，选择【VDI（VirtualBox 磁盘映像）】
    ![step 3-1]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/3-1.png)

    - 3-2，存储在物理硬盘上，选择【动态分配】
    ![step 3-2]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/3-2.png)

    - 3-3，文件位置和大小，输入磁盘映像的文件名，或者保持默认，调整磁盘大小到10G
    （因为是动态分配，这里如果选择超过电脑本身物理大小也没有关系，假设我选择了1T，代表最大不能超过该设定的值，文件实际大小是没有1T的。
    所以尽量设置大一些，避免后面系统使用时间越长，占用空间也越大）
    ![step 3-3]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/3-3.png)

- 4，配置启动光盘，选择建好的虚拟机，点击【设置】
![step 4]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/4.png)

    - 4-1，选择【存储】
    ![step 4-1]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/4-1.png)
    
    - 4-2，选中光驱，在右边操作界面点击【光盘】按钮
    ![step 4-2]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/4-2.png)

    - 4-3，添加下载好的Ubuntu Server iso文件，点击【ok】
    ![step 4-3]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/4-3.png)

- 5，选择新建的虚拟机，点击【启动】，稍等片刻，进入到安装界面
![step 5]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/5.png)


### 二，安装Ubuntu Server

- 1，进入安装界面，首先需要选择语言，这里选择【English】（这里不选择中文是因为安装会有问题，据说是一个bug）
![step 6]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/6.png)

- 2，选择语言后，进入到ubuntu正式安装界面，选择【Install Ubuntu Server】
![step 7]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/7.png)

- 3, 在正式安装之前，先看看 Ubuntu 安装程序主菜单，如果在安装过程中出现了问题，可以返回到主菜单，重试某些步骤
（实际安装过程直接进入第四步，不会有此界面）
![step 8]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/8.png)
    - 选择安装语言
    - 允许盲人使用盲人显示器访问软件
    - 配置键盘
    - 探测并挂在光盘
    - 装载 debconf 预配置文件
    - 从光盘加载安装程序组件
    - 改变 debconf 的优先级设置
    - 检测光盘的完整性
    - 保存调试日志
    - 允许 shell
    - 终止安装

- 4，选择安装语言，这里选择【English】
![step 9]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/9.png)

- 5，选择你的位置，这里选择【United States】
![step 10]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/10.png)

- 6，配置键盘布局，是否检测键盘布局，这里选择【No】 
![step 11]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/11.png)

- 7，配置键盘布局，选择键盘类型，这里选择【English（US）】
![step 12]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/12.png)

- 8，配置键盘布局，选择键盘布局，这里选择【English（US）】
![step 13]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/13.png)

- 9，生效以上配置，需要等待一会儿
![step 14]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/14.png)

- 10，配置网络，设置hostname，这里输入【ubuntunode1.thxopen.com】，然后选择【Continue】(ps：目的是在其他机器上通过此hostname访问到主机，可以是任意的字符串)
![step 15]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/15.png)

- 11，设置用户和密码，输入用户的全称，这里输入【ubuntu】，然后选择【Continue】
![step 16]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/16.png)

- 12，设置用户和密码，输入用户的登录账号，这里输入【ubuntu】，然后选择【Continue】
![step 17]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/17.png)

- 13，设置用户和密码，输入登录账号的密码，这里输入【12345678】，然后选择【Continue】（根据实际需求设置相应强度的密码）
![step 18]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/18.png)

- 14，设置用户和密码，重复上一步输入的密码，这里输入【12345678】，然后选择【Continue】
![step 19]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/19.png)

- 15，设置用户和密码，由于这里密码设置过于简单，提示是否要使用弱密码，这里选择【Yes】
![step 20]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/20.png)

- 16，设置用户和密码，是否加密home目录，这里选择【No】
![step 21]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/21.png)

- 17，生效以上配置，需要等一会儿
![step 22]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/22.png)

- 18，配置时钟，检测到当前时区是亚洲、重庆，这里选择【Yes】
![step 23]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/23.png)

- 19，磁盘分区，分区方式，这里选择【Guided - use entire disk】使用全部磁盘
![step 24]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/24.png)

- 20，磁盘分区，选择用来分区的磁盘，这里选择【ATA VBOX HARDDISK】(前面新建虚拟机时候创建的)
![step 25]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/25.png)

- 21，磁盘分区，使用默认的分区配置，这里选择【Yes】使用默认的配置来分区磁盘
![step 26]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/26.png)

- 22，生效以上配置，需要等待一会儿
![step 27]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/27.png)

- 23，配置包管理器，是否设置代理服务器，这里留空，不填写，直接【Continue】
![step 28]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/28.png)

- 24，配置自动升级，这里选择【No automatic updates】（根据自己的需要是否自动更新系统保持系统安全）
![step 29]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/29.png)

- 25，选择需要安装的软件，这里勾选【standard system utilities】和【OpenSSH server】，然后选择【Continue】(方向键切换，空格键选择或取消)
![step 30]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/30.png)

- 26，生效以上配置，需要等一会儿
![step 31]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/31.png)

- 27，安装GRUB引导装载程序在磁盘上，选择【Yes】
![step 32]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/32.png)

- 28，安装完成，选择【Continue】系统自动重启
![step 33]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/33.png)

- 29，重启后自动进入到登录界面
![step 34]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/34.png)

- 30，输入前面步骤中设置的账号【ubuntu】和密码【12345678】，登录到系统
![step 35]({{ site.baseurl }}/assets/images/post/mac/development/installubuntu/35.png)


[返回目录]({{ site.baseurl }}{% post_url mac/2018-11-01-windows-to-mac-skills %})


> Reference:
> - https://www.linuxidc.com/Linux/2017-11/148341.htm