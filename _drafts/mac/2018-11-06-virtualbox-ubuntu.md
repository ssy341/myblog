---
title: MAC下安装虚拟机-window篇 虚拟机配置文件共享
categories: linux
tags: [ubuntu,nginx,ssl,https]
---







sudo mount -t vboxsf -o uid=1000,gid=1000 Share_Documents ~/Mac_Share/
sudo mount -t vboxsf -o uid=1000,gid=1000 shared ~/shared/



切换了网络环境之后 网络会断掉 重启虚拟机即可
