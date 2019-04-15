---
title: 在虚拟机里安装windows10
categories: mac
tags: [virtualbox,windows10,mac,虚拟机]
---

见过很多人购买了mac后安装的是windows，现在看来只能说windows普及的好，用不习惯就是用不习惯，mac装windows又怎么了？
macOS的确很流畅，就像苹果手机和安卓手机那样的区别，没有细究过为什么macOS系统要比windows流畅，但是不管怎么说各有所长，你要打游戏那就选windows，
他们都只是一个工具，哪个用着上手舒服，你就选择那个。

<!--more-->


同时需要多个操作系统的时候怎么办呢？有些只能在windows下才能运行的程序又怎么办呢？虚拟机在这方面帮我们解决了很多问题，找到问题的解决办法就行，没有绝对的对与错，好与坏。

## 准备工作

- [virtualbox](https://www.virtualbox.org/)（虚拟机软件）
- [windows10 iso](https://www.microsoft.com/zh-cn/software-download/windows10ISO)

通过上面的连接，下载virtualbox和windows10的安装镜像，我们就可以开始了

## 开始安装

### 一，配置虚拟机

- 1，虚拟机名称和系统类型，这里系统类型选择【Microsoft Windows】，版本选择【windows 10（64-bit）】
![step 1]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step1.png)

- 2，内存大小，这里可以根据软件推荐的大小配置，我这里推荐的是2G（2048 MB），为了系统更加流畅，我调到 4G （4096 MB）。可以根据电脑的配置适当上调
![step 2]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step2.png)

- 3，虚拟硬盘，选择【现在创建虚拟硬盘】
![step 3]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step3.png)

    - 3-1，虚拟硬盘文件类型，选择【VDI（VirtualBox 磁盘映像）】
    ![step 3-1]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step3-1.png)

    - 3-2，存储在物理硬盘上，选择【动态分配】
    ![step 3-2]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step3-2.png)

    - 3-3，文件位置和大小，输入磁盘映像的文件名，或者保持默认，调整磁盘大小到50G
    （因为是动态分配，这里如果选择超过电脑本身物理大小也没有关系，假设我选择了1T，代表最大不能超过该设定的值，文件实际大小是没有1T的。
    所以尽量设置大一些，避免后面系统使用时间越长，占用空间也越大）
    ![step 3-3]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step3-3.png)

- 4，配置启动光盘，选择建好的虚拟机，点击【设置】
![step 4]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step4.png)

    - 4-1，选择【存储】
    ![step 4-1]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step4-1.png)
    
    - 4-2，选中光驱，在右边操作界面点击【光盘】按钮
    ![step 4-2]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step4-2.png)

    - 4-3，添加下载好的windows10 iso文件，点击【ok】
    ![step 4-3]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step4-3.png)

- 5，选择新建的虚拟机，点击【启动】，稍等片刻，进入到安装界面
![step 5]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step5.png)


### 二，安装 windows 10

- 6，进入到windows10的安装界面，点击【下一步】
![step 6]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step6.png)

- 7，点击【现在安装】
![step 7]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step7.png)

- 8，要求输入产品密钥，点击【我没有产品密钥】
![step 8]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step8.png)

- 9，选择安装的版本，选择【windows 10 专业版】，点击【下一步】
![step 9]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step9.png)

- 10，适用的声明和许可条款，勾选【我接受许可条款】，点击【下一步】
![step 10]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step10.png)

- 11，选择安装类型，点击【自定义：仅安装 Windows（高级）】
![step 11]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step11.png)

- 12，选择安装windows的路径，由于是新装，选择我们新建的磁盘，点击【新建】，使用默认大小，点击【应用】
![step 12]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step12.png)

    - 12-1，确定创建额外分区，点击【确定】
    ![step 12-1]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step12-1.png)

    - 12-2，选择【主分区】，点击【下一步】
    ![step 12-2]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step12-2.png)

- 13，开始安装，等待自动安装完成
![step 13]({{ site.baseurl }}/assets/images/post/mac/development/installwindows/windows-step13.png)

- 14，安装完成后需要对windows10进行个性化配置，按照界面提示操作即可，到此windows10安装完成 :)


[返回目录]({{ site.baseurl }}{% post_url mac/2018-11-01-windows-to-mac-skills %})



    