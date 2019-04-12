---
title: ubuntu server下安装Virtualbox增强插件-实现文件夹共享
categories: mac
tags: [virtualbox,ubuntu server,文件夹共享]
---

由于ubuntu server不是一个桌面系统，那么对于剪贴板，文件拖拽这些需求就没有那么强烈，不过文件的共享倒是一个基本的问题。
为了方便虚拟机和主机之间的文件共享，虚拟机提供了virtualbox增强插件解决这个问题。

启动ubuntu server虚拟机，按照以下步骤安装virtualbox增强插件

## 一，安装virtualbox增强插件

- 1，左上角找到【Devices】- 【Insert Guest Additions CD Image...】
![1]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/1.png)

- 2，执行下面命令挂载CD
```bash
sudo mount /dev/cdrom /media/cdrom
```
挂载成功，进入目录可以看到`VBoxLinuxAdditions.run`安装脚本
```bash
ls /media/cdrom
32Bit  AUTORUN.INF  cert  runasroot.sh  VBoxLinuxAdditions.run    VBoxWindowsAdditions-amd64.exe  VBoxWindowsAdditions-x86.exe
64Bit  autorun.sh   OS2   TRANS.TBL     VBoxSolarisAdditions.pkg  VBoxWindowsAdditions.exe
```

- 3，安装VirtualBox guest additions所需要的依赖
```bash
sudo apt-get update
sudo apt-get install build-essential linux-headers-`uname -r`
```

- 4，执行`VBoxLinuxAdditions.run`脚本
```bash
sudo /media/cdrom/VBoxLinuxAdditions.run
```

## 二，配置增强插件

- 5，配置共享文件夹，【Devices】 - 【Shared Folders】 - 【Shared Folders Settings...】
![5]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/5.png)

    - 5-1,添加一个共享目录，点击右边的【➕】按钮
    ![8]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/5-1.png)
        
    - 5-2，选择本地的一个目录，【Folder Name】填写`wwwroot`（或者其他名称），然后勾选【Make Permanent】固定分配，点击【ok】
    ![9]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/5-2.png)
    
    - 5-3，保存共享文件夹配置，点击【ok】  
    ![10]({{ site.baseurl }}/assets/images/post/mac/development/installadditions/5-3.png)


- 5，重启vm
```bash
sudo shutdown -r now
```

- 6，待重启完毕，创建共享文件夹的挂载点
```bash
mkdir ~/wwwroot
```

- 7，把共享文件夹挂载到上一步创建的目录
```bash
sudo mount -t vboxsf -o uid=1000,gid=1000 wwwroot ~/wwwroot
```
> ps:uid和gid的值为当前用户的，这样就可以用当前登录的用户访问共享文件夹里的内容，不然挂载的文件默认用户是root，组也是root组。
> 通过命令`id`查看当前用户对应的信息

- 8，现在可以在vm内访问主机共享的文件夹类容
```bash
cd ~/wwwroot
```

[返回目录]({{ site.baseurl }}{% post_url mac/2018-11-01-windows-to-mac-skills %})


> Reference:
> https://gist.github.com/estorgio/1d679f962e8209f8a9232f7593683265
> https://ubuntuforums.org/showthread.php?t=1398340