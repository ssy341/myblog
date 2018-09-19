---
categories: linux
tags: [parted,鸟哥私房菜,mkfs,ext4,linux,fdisk]
title: ubuntu server下建立分区表/分区/格式化/自动挂载
date: 2014-3-30
toc: false
---

流程为：新建分区--》格式化分区--》挂载分区

首先弄明白分区的定义，我在网上找到MBR和GPT分区的介绍：


MBR分区（主引导记录）表：
支持最大卷：2T （T; terabytes,1TB=1024GB）
分区的设限：最多4个主分区或3个主分区加一个扩展分区。

GPT分区（GUID分区表）表：
支持最大卷：18EB，（E：exabytes,1EB=1024TB）
每个磁盘最多支持128个分区
第一：新建分区
在linux下有fdisk和parted命令，由于fdisk不支持gpt，需要使用parted来对硬盘进行接下来的操作
（ps：使用fdisk命令，会有下面的警告信息：**WARNING: GPT (GUID Partition Table) detected on '/dev/sda'! The util fdisk doesn't support GPT. Use GNU Parted.**)

> 以下所有操作本人已经操作过，显示的信息略有不同。数据无价，操作前请备份好数据。不过首先要通过fdisk查看当前系统识别的硬盘

```bash
smotive@ubuntu-nas:~$ sudo fdisk -l
[sudo] password for smotive: 

Disk /dev/sda: 320.1 GB, 320072933376 bytes
255 heads, 63 sectors/track, 38913 cylinders, total 625142448 sectors
Units = 扇区 of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 4096 bytes
I/O size (minimum/optimal): 4096 bytes / 4096 bytes
Disk identifier: 0x000109ef

   设备 启动      起点          终点     块数   Id  系统
/dev/sda1   *        2048   617381887   308689920   83  Linux
/dev/sda2       617383934   625141759     3878913    5  扩展
Partition 2 does not start on physical sector boundary.
/dev/sda5       617383936   625141759     3878912   82  Linux 交换 / Solaris

WARNING: GPT (GUID Partition Table) detected on '/dev/sdb'! The util fdisk doesn't support GPT. Use GNU Parted.


Disk /dev/sdb: 500.1 GB, 500107862016 bytes
255 heads, 63 sectors/track, 60801 cylinders, total 976773168 sectors
Units = 扇区 of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disk identifier: 0xd87cd87c

   设备 启动      起点          终点     块数   Id  系统
/dev/sdb1               1   976773167   488386583+  ee  GPT	  --这里显示不同，是因为我已经操作过了，对照完成下面的操作即可 ,/dev/sdb1 在下面将会用到
```

可以看出系统识别了两个硬盘 /dev/sda & /dev/sdb,现在我要把/dev/sdb 进行建立分区表，分区和格式化并让系统自动挂载

```bash
smotive@ubuntu-nas:~$ sudo parted /dev/sdb
GNU Parted 2.3
使用 /dev/sdb
欢迎使用 GNU Parted! 输入 'help'可获得命令列表.
(parted)  mklabel gpt 
```

此时会提示你此操作会删掉所有数据，是否继续，这里输入yes，执行完成后，再输入打印命令，查看已经建立好分区表的硬盘信息

```bash
(parted) print
Model: ATA WDC WD5000AADS-0 (scsi)
磁盘 /dev/sdb: 500GB
Sector size (logical/physical): 512B/512B
分区表：gpt

数字  开始：  End    大小   文件系统  Name     标志
 1    17.4kB  500GB  500GB  ext4      primary     --在实际操作过程这个是暂时不会显示，这里由于我的硬盘已经执行过操作了
```

接下来分区，输入如下命令

```bash
mkpart primary 0 -1
```

这个代表把整个硬盘作为一个分区使用，在执行的时候会提示你是否继续，这里选择yes，之后会警告，分区后的对齐不能达到最佳性能，忽略or取消，这里忽略，然后再打印
信息

```bash
(parted) print
Model: ATA WDC WD5000AADS-0 (scsi)
磁盘 /dev/sdb: 500GB
Sector size (logical/physical): 512B/512B
分区表：gpt

数字  开始：  End    大小   文件系统  Name     标志
 1    17.4kB  500GB  500GB  ext4      primary   --分区完后这里即显示，但是文件系统还是空白，接下来需要格式化才行
```

到这里，建立分区表，分区就完成了，退出parted

```bash
(parted)quit                                                             
Information: You may need to update /etc/fstab. --这里的提示就是下面要说的系统启动自动挂载，需要修改   /etc/fstab这个文件
```

现在来格式化硬盘，一块硬盘需要格式化了，才能被系统使用，根据fdisk -l 列出的信息，我需要格式化的设备名称为/dev/sdb1,具体参考上面的信息

```bash
mkfs.ext4 /dev/sdb1  --把硬盘格式化为ext4的文件系统格式 
```

接下来会自动完成，等待提示成功即可。接下来就是挂载硬盘到系统，windows会有c d e f盘之分，而linux是按文件夹的名称才区别设备的，
既然系统要使用这块硬盘，那么就需要建立一个文件夹来和这个硬盘进行关联
我在我的主目录下创建一个文件夹

```bash 
mkdir /home/smotive/wd500
```

然后挂载硬盘

```bash
mount /dev/sdb1 /home/smotive/wd500
```

然后再查看系统挂载信息
```bash
smotive@ubuntu-nas:~$ df -h
文件系统        容量  已用  可用 已用% 挂载点
/dev/sda1       290G  2.3G  273G    1% /
udev            1.8G  4.0K  1.8G    1% /dev
tmpfs           731M  420K  730M    1% /run
none            5.0M     0  5.0M    0% /run/lock
none            1.8G     0  1.8G    0% /run/shm
cgroup          1.8G     0  1.8G    0% /sys/fs/cgroup
/dev/sdb1       459G   70M  435G    1% /home/smotive/wd500  --此时硬盘已经挂载到系统，可以存放文件使用了
```
接下来修改/etc/fstab文件让系统重启后自动挂载硬盘，打开文件
```bash
# /etc/fstab: static file system information.
#
# Use 'blkid' to print the universally unique identifier for a
# device; this may be used with UUID= as a more robust way to name devices
# that works even if disks are added and removed. See fstab(5).
#
# <file system> <mount point>   <type>  <options>       <dump>  <pass>
proc            /proc           proc    nodev,noexec,nosuid 0       0
# / was on /dev/sda1 during installation
UUID=6ffe07b3-2c5f-4a82-b3b0-bed73c0efe47 /               ext4    errors=remount-ro 0       1
# swap was on /dev/sda5 during installation
UUID=9eaf6d20-c2cf-407b-b06b-fc93c486634c none            swap    sw              0       0
/dev/sdb1       /home/smotive/wd500     ext4    defaults        0       2  

#第一列：设备名或者设备卷标名，（/dev/sda10 或者 LABEL=/）
#第二列：设备挂载目录        （例如上面的“/”或者“/mnt/D/”）
#第三列：设备文件系统       （例如上面的“ext3”或者“vfat”）
#第四列：挂载参数     （看帮助man mount）
#第五列：指明是否要备份。（0为不备份，1为要备份，一般根分区要备份）
#第六列：指明自检顺序。 （0为不自检，1或者2为要自检，如果是根分区要设为1，其他分区只能是2）
```
修改好后保存退出
修改完/etc/fstab时，应该用 mount -a将所以设备挂载进行测试 ，这时mount读取/etc/fstab中内容进行挂载，如果/etc/fstab的条目无错误，
则mount -a后无显示，表示挂载成功；如有错误，则根据提示排查。我第一次操作由于挂载目录写错导致不能开机，后再网上寻找办法，进入恢复模式修改，
具体看这里[恢复模式下修改/etc/fatab文件]({{ site.baseurl }}{% post_url linux/2014-03-30-Linux-recover-etc-fstab %})
大功告成，以上为本人学习鸟哥私房菜的笔记，同时也参考了网上的资料，希望能给大家带来帮助


参考：

[第八章、Linux 磁盘与文件系统管理](http://vbird.dic.ksu.edu.tw/linux_basic/0230filesystem_4.php)

[ext4介绍](http://zh.wikipedia.org/zh-cn/Ext4)

[Linux 下添加硬盘/新建分区（fdisk + mkfs.ext4 + mount）](http://www.fikker.com/isp/help/linux-fdisk.html)

[parted创建GPT分区（fdisk不支持创建GPT分区，GPT支持大于2TB分区，MBR不支持）](http://www.blogjava.net/haha1903/archive/2011/12/21/366942.html)    

[一次添加硬盘分区并修改/etc/fstab引起的故障](http://haibusuanyun.blog.51cto.com/2701158/756949)  
    



