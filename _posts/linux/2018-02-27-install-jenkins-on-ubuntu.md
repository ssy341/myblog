---
title: 在Ubuntu下安装jenkins
categories: linux
tags: [ubuntu,jenkins]
---

Jenkins是一款由Java编写的开源的持续集成工具。在与Oracle发生争执后，项目从Hudson项目复刻。 Jenkins提供了软件开发的持续集成服务。
它运行在Servlet容器中（例如Apache Tomcat）。它支持软件配置管理（SCM）工具（包括AccuRev SCM、CVS、Subversion、Git、Perforce、Clearcase和RTC），
可以执行基于Apache Ant和Apache Maven的项目，以及任意的Shell脚本和Windows批处理命令。


## 安装Jenkins

jenkins官网提供了在线安装的方式，软件包集成了jetty服务，安装即可访问，非常方便

> 在安装jenkins之前，确定已经配置了java环境，不同的jenkins版本需要的java环境也不一样
> - 2.54 (2017-04) 或者更新的版本: 需要 Java 8 支持
> - 1.612 (2015-05) 或者更新的版本: 需要 Java 7 支持


- 添加key到系统
```bash
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
```

- 添加资源到`/etc/apt/sources.list`
为了方便管理不同软件的安装源，我们新建一个文件`jenkins.list`，存放在 `sources.list.d` 目录下
```bash
echo 'deb https://pkg.jenkins.io/debian-stable binary/' | sudo tee /etc/apt/sources.list.d/jenkins.list
```

- 更新本地包，然后安装jenkins
```bash
sudo apt-get update
sudo apt-get install jenkins
```


## 配置端口

默认情况下，jenkins使用的是8080端口，如果端口没有被其他的程序占用，则可以直接在浏览器访问`http://127.0.0.1:8080`，开始使用jenkins。
如果端口被其他程序占用，可以通过修改jenkins的配置文件（在 `/etc/default/` 目录下），更改端口

```bash
sudo vi /etc/default/jenkins
```

打开文件，文件尾部部分，如下所示

```bash
...
# port for HTTP connector (default 8080; disable with -1)
HTTP_PORT=8080
...
```

把`8080`修改成自己想要的，保存退出，重新启动服务

停止jenkins服务（可能会要求你输入当前用户的密码）
```bash
/etc/init.d/jenkins stop

Correct java version found
[....] Stopping jenkins (via systemctl): jenkins.service==== AUTHENTICATING FOR org.freedesktop.systemd1.manage-units ===
Authentication is required to stop 'jenkins.service'.
Authenticating as: ubuntu,,, (ubuntu)
Password:

==== AUTHENTICATION COMPLETE ===
. ok
```

重新启动jenkins服务，同样需要输入密码
```bash
/etc/init.d/jenkins start
Correct java version found
[....] Starting jenkins (via systemctl): jenkins.service==== AUTHENTICATING FOR org.freedesktop.systemd1.manage-units ===
Authentication is required to start 'jenkins.service'.
Authenticating as: ubuntu,,, (ubuntu)
Password:

==== AUTHENTICATION COMPLETE ===
. ok
```

## 初始化jenkins
为了确保管理员安全地安装 Jenkins，首次访问jenkins会要求输入密码才能继续，密码存放在 `/var/lib/jenkins/secrets/initialAdminPassword`

![install-jenkins1]({{site.baseurl}}/assets/images/post/linux/ubuntu/install-jenkins/1.png)

查看密码，输入到密码框进行下一步
```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

点击继续后，会有一个等待时间，然后进入新手入门向导界面，如下图所示：

![install-jenkins2]({{site.baseurl}}/assets/images/post/linux/ubuntu/install-jenkins/2.png)

这里我们选择默认选项，推荐安装插件，确定后进入安装界面，如下图所示：

![install-jenkins3]({{site.baseurl}}/assets/images/post/linux/ubuntu/install-jenkins/3.png)

插件安装大概要持续一会儿，慢慢等待安装完成。插件安装完成之后需要配置一个管理员用户，创建一个账户，保存下一步。

![install-jenkins4]({{site.baseurl}}/assets/images/post/linux/ubuntu/install-jenkins/4.png)


接下来进行实例配置，我们使用默认给出的地址即可，他是jenkins访问的根路径，直接保存并完成。

![install-jenkins5]({{site.baseurl}}/assets/images/post/linux/ubuntu/install-jenkins/5.png)


大功告成，jenkins配置完成，可以开始使用了

![install-jenkins6]({{site.baseurl}}/assets/images/post/linux/ubuntu/install-jenkins/6.png)


> ps: 点击开始使用后页面是空白的，把jenkins服务重启一下，然后重新访问即可

## 升级jenkins

apt-get安装方式简化了软件安装以及软件启动和停止，但实际就是用内置的web服务加上jenkins.war，默认情况下这个文件在
`/usr/share/jenkins/` 目录，如果要对其升级，只需要替换这个文件即可。为了保险起见，你可以备份这个文件，然后在
[jenkins下载](https://jenkins.io/download/)页面找到 **Generic Java package (.war)** ，把下载下来的文件
放到`/usr/share/jenkins/` 目录，下载好了重新启动完成升级操作。

停止jenkins
```bash
$ sudo service jenkins stop
```
备份jenkins之前的版本
```bash
$ sudo mv /usr/share/jenkins/jenkins.war /usr/share/jenkins/jenkins_backup.war
```
下载最新版
```bash
$ cd /usr/share/jenkins/
$ sudo wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war

--2019-04-15 16:13:28--  http://mirrors.jenkins.io/war-stable/latest/jenkins.war
Resolving mirrors.jenkins.io (mirrors.jenkins.io)... 52.202.51.185
Connecting to mirrors.jenkins.io (mirrors.jenkins.io)|52.202.51.185|:80... connected.
HTTP request sent, awaiting response... 302 Found
Location: http://ftp-nyc.osuosl.org/pub/jenkins/war-stable/2.164.2/jenkins.war [following]
--2019-04-15 16:13:29--  http://ftp-nyc.osuosl.org/pub/jenkins/war-stable/2.164.2/jenkins.war
Resolving ftp-nyc.osuosl.org (ftp-nyc.osuosl.org)... 64.50.233.100, 2600:3404:200:237::2
Connecting to ftp-nyc.osuosl.org (ftp-nyc.osuosl.org)|64.50.233.100|:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 77330993 (74M) [application/x-java-archive]
Saving to: ‘jenkins.war’

```
启动jenkins
```bash
$ sudo service jenkins start
```


[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})


> Reference
> - https://pkg.jenkins.io/debian-stable/
> - https://zh.wikipedia.org/wiki/Jenkins_(%E8%BD%AF%E4%BB%B6)
> - https://stackoverflow.com/questions/38966105/jenkins-setup-wizard-blank-page