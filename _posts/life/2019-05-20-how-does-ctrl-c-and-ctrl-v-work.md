---
title: Ctrl+C 和 Ctrl+V 是如何工作的？ - How does Ctrl+c and Ctrl+v work?
categories: life
tags: [翻译,好文]
toc: false
---

本来忙着录视频，但运气不好跟公司发生了一点小矛盾，心情不美丽，所以就瞎逛，
在 StackExchange 上看到有人提问[“How does Ctrl+c and Ctrl+v work?”](https://superuser.com/questions/1436622/how-does-ctrlc-and-ctrlv-work)，
一下就吸引了我的目光，这两个快捷键可是说我们天天都在用，但你要我说出来是如何工作的？我还真是不晓得，不多说，
跟着提问者进去瞧瞧，到底是如何工作的？

以下是译文:




## Question：Ctrl+C 和 Ctrl+V 是如何工作的?

我一直很好奇，当我将图像（选择它使用快捷键Ctrl+c）复制到word文件（使用ctrl+v粘贴）[幕后](https://www.quora.com/What-does-under-the-hood-mean-in-programming)（操作系统层面）发生了什么事情？


## Answer

### Windows

在windows下，剪贴板API和存储缓冲区是由OS提供（显然是内核级别）：

- Ctrl+C 告诉程序使用 Win32 API 方法 [SetClipboardData()][SetClipboardData]去存储“被复制”的数据



[SetClipboardData]:https://docs.microsoft.com/en-us/windows/desktop/api/Winuser/nf-winuser-setclipboarddata