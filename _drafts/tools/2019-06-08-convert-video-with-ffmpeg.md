---
title: 使用ffmpeg处理视频
categories: tools
tags: [ffmpeg,tools,视频处理,格式转换,视频裁剪,视频拼接]
---




brew update
  387  brew install ffmpeg
  388  xcode-select --install 
  389  brew install ffmpeg
  
  
  ffmpeg -i 2019-05-02\ 17-31-59.flv -b:v 640k demo.mp4
  
  ffmpeg -i 2019-05-02 17-31-59.flv -vcodec h264 demo2.mp4
    425  ffmpeg -i demo.mp4 -vcodec h264 demo2.mp4
    
    ffmpeg -i 2019-05-12\ 16-09-09.flv -b:v 640k demo3.mp4
      485  ffmpeg -i 2019-05-12\ 22-41-36.flv -b:v 640k 138.mp4
      
      ffmpeg -i 2019-06-12\ 16-45-38.flv -v:v 640k tiaopi.mp4
        505  ffmpeg -i 2019-06-12\ 16-45-38.flv -b:v 640k tiaopi.mp4
        506  ffmpeg -ss 00:00:00 -t 00:01:00 -i demo2.mp4 output.mp4 
        507  ffmpeg -i 2019-06-13\ 14-20-38.flv -b:v 640k season2-2.mp4