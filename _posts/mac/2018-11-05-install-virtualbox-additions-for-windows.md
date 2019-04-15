---
title: windows下安装Virtualbox增强插件-实现文件夹共享，共享剪贴板，文件拖拽
categories: mac
tags: [virtualbox,windows10,文件夹共享,共享剪贴板,文件拖拽]
---

为了方便同时操作虚拟机（guest）和主机（host），剪贴板、文件拖拽和文件夹共享是必不可少的。
首先启动虚拟机，按照下面的步骤，安装virtualbox增强插件即可解决这些问题。

<!--more-->


## 一，安装virtualbox增强插件

- 1，左上角找到【Devices】- 【Insert Guest Additions CD Image...】
![1]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/1.png)

- 2，然后打开我的电脑，可以看到CD驱动器已经加载
![2]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/2.png)

- 3，打开光盘，看到如下目录，双击【VBoxWindowsAdditions.exe】
![3]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/3.png)

- 4，所有操作都默认即可，一路点击【Next】，然后点击【Install】
![44]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/44.png)

![55]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/55.png)

![66]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/66.png)

- 5，系统设备安装提示，点击【安装】
![33]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/33.png)

- 6，安装完成，要求重启，点击【Finish】重启即可
![4]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/4.png)

    

## 二，配置增强插件

- 1，左上角找到【Devices】- 【Shared Clipboard】，选择【Bidirectional】，实现剪贴板共享，现在你可以在主机上复制，在虚拟机里粘贴
（反之亦然，注意windows下是ctrl+c和ctrl+v，在mac下是commond+c和commond+v）
![6]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/6.png)

- 2，【Devices】 - 【Drag and Drop】，选择【Bidirectional】，实现文件拖拽，现在你可以拖拽文件到虚拟机，但是反过来不行，会报错，暂且没有找到方法，如果要从虚拟机拷贝文件到主机，参考下面【文件夹共享】
![7]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/7.png)

- 3，【Devices】 - 【Shared Folders】 - 【Shared Folders Settings...】
![5]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/5.png)

    - 3-1,添加一个共享目录，点击右边的【➕】按钮
    ![8]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/8.png)
    
    - 3-2，选择本地的一个目录，勾选【Auto-mount】自动挂载 和【Make Permanent】固定分配，点击【ok】
    ![9]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/9.png)

    - 3-3，保存共享文件夹配置，点击【ok】  
    ![10]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/10.png)
    
    - 3-4，这个时候重启虚拟机，可以在网络位置看到共享的文件夹（ps：如果没有，检查windows10是否已经开启了网络发现）
    ![11]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/11.png)

> ps：
> 如果遇到粘贴板不能正常使用，检查是否升级了virtualbox软件，如果是，那么需要将对应的增强插件也需要升级，
> 卸载掉已经安装的增强插件，按照以上步骤重新安装增强插件即可

[返回目录]({{ site.baseurl }}{% post_url mac/2018-11-01-windows-to-mac-skills %})
