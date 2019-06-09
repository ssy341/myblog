---
title: 使用OBS录制屏幕
categories: tools
tags: [obs,tools,录屏软件]
---


由于要制作Datatables入门第二期视频，需要用到屏幕录制，在网上搜索了很多软件，最后发现[OBS][obs]非常好。

> OBS Studio - Free and open source software for live streaming and screen recording

> OBS Studio - 直播和屏幕录制的免费开源软件

这个简直太合我意了（偷笑中），不仅免费，而且功能强大，独乐了不如众乐乐，分享下如何使用OBS录制自己的屏幕。


## 下载安装

你可以通过[官网][obs-download]下载对应的版本，支持windows、mac和linux。本次选择mac版作为例子来讲解。


## 录制屏幕

### 指定窗口录制

和其他录制屏幕软件不同的是，OBS它可以指定应用程序窗口来录制，这样即便你在操作其他软件，也不会影响到录制

软件打开后，默认会有一个场景，在对应场景里选中【窗口捕获】，点击下方的⚙配置选择窗口捕获来源

![windows-target-1]({{ site.baseurl }}/assets/images/post/tools/obs/windows-target-1.png)

在下拉列表里可以选择你要捕获的窗口，每个应用程序都会列在这里

![windows-target-2]({{ site.baseurl }}/assets/images/post/tools/obs/windows-target-2.png)

通过上面两个步骤的设置，就可以录制选定窗口


### 不同应用程序切换

在录制Datatables入门第二期视频时，会在ppt和IDE应用程序之间来回切换，这样就需要使用场景来实现。

首先，点击左下角场景区域➕，新建一个场景，按照自己的需要命名

![scene-1]({{ site.baseurl }}/assets/images/post/tools/obs/scene-1.png)
![scene-2]({{ site.baseurl }}/assets/images/post/tools/obs/scene-2.png)

其次，点击来源区域➕，新建捕获来源
![target-1]({{ site.baseurl }}/assets/images/post/tools/obs/target-1.png)

选择窗口捕获
![target-2]({{ site.baseurl }}/assets/images/post/tools/obs/target-2.png)

按照自己的需要命名
![target-3]({{ site.baseurl }}/assets/images/post/tools/obs/target-3.png)

选择窗口捕获来源
![target-4]({{ site.baseurl }}/assets/images/post/tools/obs/target-4.png)

最后就有两个场景可供录制
![target-5]({{ site.baseurl }}/assets/images/post/tools/obs/target-5.png)

我们为场景指定不同的快捷键，这样切换场景的时候就不用回到OBS软件操作

点击上图右下方【设置】按钮，进入【热键】设置部分，给场景切换指定快捷键，这样在录制的时候我们可以随时切换录制那个场景

![settings-1]({{ site.baseurl }}/assets/images/post/tools/obs/settings-1.png)

> 注意：不要设置和其他软件冲突的快捷键，不然会起冲突导致切换场景失效


## 录制电脑输出声音

默认设置下，视频录制的声音来自麦克风


如果我在电脑上播放歌曲，这时候声音从扬声器播放，然后被麦克风采集（注意看下图两处红色框框，上方的代表在播放音乐，下方代表麦克风采集到扬声器声音）

![sound-1]({{ site.baseurl }}/assets/images/post/tools/obs/sound-1.png)

如果我想电脑的声音直接被OBS采集而不是通过麦克风采集，这里需要用到虚拟声卡 —— Soundflower


### 第一步：安装Soundflower

> Soundflower - a free audio system extension that allows applications to pass audio to other applications.

> Soundflower - 一款免费的音频系统扩展，允许应用程序将音频传递到其他的应用程序。

首先下载安装[Soundflower][soundflower]，安装完成后打开【Launchpad】找到【音频MIDI设置】

![midi-settings-1]({{ site.baseurl }}/assets/images/post/tools/obs/midi-settings-1.png)

点击左下角➕ =》【创建多输出设备】=》点击标题处可以命名，这里重命名为【obs】=》勾选【内建输出】和【Soundflower(2ch)】

![midi-settings-2]({{ site.baseurl }}/assets/images/post/tools/obs/midi-settings-2.png)

> 解释：这里创建多输出设备的用意在于在录制屏幕的时候同时可以听到电脑输出的声音，如果不设置录制视频的时候自己听不到声音，但视频可以采集到电脑输出的声音

### 第二步：配置OBS

上面配置好的虚拟声卡后，在OBS【设置】界面  =》 【音频】 =》 桌面音频设备选择【Soundflower(ch2)】 =》 麦克风/辅助音频设备选择【已禁用】

![obs-settings-1]({{ site.baseurl }}/assets/images/post/tools/obs/obs-settings-1.png)

然后电脑声音输出选择第一步新建的【obs】虚拟声卡

![mac-sound-settings-1.png]({{ site.baseurl }}/assets/images/post/tools/obs/mac-sound-settings-1.png)

> 解释：由于obs是两个设备，一个是内置的扬声器，一个是虚拟声卡Soundflower(2ch)，这里选择输出设备为obs意味着声卡将会把声音从这两个设备输出，
> 而在上面OBS 【音频】 设置里，我们把桌面音频设备选择的是Soundflower(2ch)，即我们想要的，录制的声音直接从电脑声卡采集，而不是从麦克风

> ps: 在选择obs输出之前，请先调好声音大小，因为选择obs后将不能修改声音

通过上面两步设置，我们录制屏幕的时候，声音就可以直接采集从电脑声卡输出的声音了

![obs-record-1.png]({{ site.baseurl }}/assets/images/post/tools/obs/obs-record-1.png)

> 第一个红框表示电脑正在播放音乐

> 第二个红框表示OBS采集到声卡的输出


























[obs]:https://obsproject.com/
[obs-download]:https://obsproject.com/download
[obs-github]:https://github.com/obsproject/obs-studio
[soundflower]:https://soundflower.en.softonic.com/mac
