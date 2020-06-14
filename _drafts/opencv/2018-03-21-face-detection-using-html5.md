---
title: 使用Html5、Javascript、WebSockets、OpenCV进行人脸检测
categories: opencv
tags: [opencv,spring-boot,websockets,html5]
---

通过浏览器访问用户的网络摄像头，结合html5、Javascript，websocket，制作一个实时视频的应用变得很容易。
这得益于现在大多数浏览器都支持Html5，让浏览器获得更多标准功能。还有Websocket，它可以让你轻松地与服务器建立一个双向通信通道。

在本文中，我将带大家如何使用Websocket，Html5和Javascript来完成实时人脸检测。

效果图

要做到实时人脸检测，需要按照下面几个步骤：

- 通过getUserMedia功能获得用户的网络摄像头数据
- 使用Websocket发送网络摄像头数据到服务器端
- 在服务端，使用OpenCV分析收到的数据，检测是否有人脸并标记出来
- 使用Websocket发送数据返回到客户端
- 在浏览器显示服务器返回到客户端的数据



> [Face Detection using HTML5, Javascript, Webrtc, Websockets, Jetty and OpenCV](https://dzone.com/articles/face-detection-using-html5)