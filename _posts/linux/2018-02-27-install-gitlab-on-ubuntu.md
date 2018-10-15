---
title: 在Ubuntu下安装Gitlab
categories: linux
tags: [ubuntu,Gitlab]
---

GitLab是由GitLab Inc.开发，使用MIT许可证的基于网络的Git仓库管理工具，且具有wiki和issue跟踪功能。

## 安装Gitlab

- 1，安装必要的依赖

```bash
sudo apt-get install -y curl openssh-server ca-certificates
```

> ps: -y 参数代表默认同意安装，不需要再手动输入y继续安装

接下来，为了发送提醒邮件，需要安装`Postfix`。如果你想使用其他方案发送邮件请跳过此步骤，在gitlab安装完成之后配置额外的smtp服务
[参考>>](https://docs.gitlab.com/omnibus/settings/smtp.html)
```bash
sudo apt-get install -y postfix
```
安装postfix的时候，会出现配置界面，如下所示：

```bash
Package configuration
┌───────────────────────────┤ Postfix Configuration ├───────────────────────────┐
│ Please select the mail server configuration type that best meets your needs.  │
│                                                                               │
│  No configuration:                                                            │
│   Should be chosen to leave the current configuration unchanged.              │
│  Internet site:                                                               │
│   Mail is sent and received directly using SMTP.                              │
│  Internet with smarthost:                                                     │
│   Mail is received directly using SMTP or by running a utility such           │
│   as fetchmail. Outgoing mail is sent using a smarthost.                      │
│  Satellite system:                                                            │
│   All mail is sent to another machine, called a 'smarthost', for delivery.    │
│  Local only:                                                                  │
│   The only delivered mail is the mail for local users. There is no network.   │
│                                                                               │
│ General type of mail configuration:                                           │
│                                                                               │
│                            No configuration                                   │
│                            Internet Site                                      │
│                            Internet with smarthost                            │
│                            Satellite system                                   │
│                            Local only                                         │
│                                                                               │
│                                                                               │
│                     <Ok>                         <Cancel>                     │
│                                                                               │
└───────────────────────────────────────────────────────────────────────────────┘
```
选择【Internet Site】选项完成本页配置。下一步配置mail_name，如下图所示：

```bash
Package configuration
 ┌────────────────────────────────────────────┤ Postfix Configuration ├────────────────────────────────────────────┐
 │ The "mail name" is the domain name used to "qualify" _ALL_ mail addresses without a domain name. This includes  │
 │ mail to and from <root>: please do not make your machine send out mail from root@example.org unless             │
 │ root@example.org has told you to.                                                                               │
 │                                                                                                                 │
 │ This name will also be used by other programs. It should be the single, fully qualified domain name (FQDN).     │
 │                                                                                                                 │
 │ Thus, if a mail address on the local host is foo@example.org, the correct value for this option would be        │
 │ example.org.                                                                                                    │
 │                                                                                                                 │
 │ System mail name:                                                                                               │
 │                                                                                                                 │
 │ localhost.localdomain                                                                                           │
 │                                                                                                                 │
 │                                <Ok>                                    <Cancel>                                 │
 │                                                                                                                 │
 └─────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

实际使用中不需要用到邮件通知，所以这里使用默认的地址，回车完成postfix的安装配置。

- 2，配置软件源
由于官网的资源安装会很慢，推荐使用国内的镜像去安装，网上推荐的使用[清华大学的镜像](https://mirror.tuna.tsinghua.edu.cn/help/gitlab-ce/)

信任 GitLab 的 GPG 公钥:
```bash
curl https://packages.gitlab.com/gpg.key 2> /dev/null | sudo apt-key add - &>/dev/null
```
添加清华大学的镜像到`/etc/apt/sources.list`
```bash
echo 'deb https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/ubuntu xenial main' | sudo tee /etc/apt/sources.list.d/gitlab.list
```
ps: 如果是Ubuntu 14.04就把`xenial`换成`trusty`，附一张对照表

系统版本 | Codename | 支持平台
---|---|---
14.04 | trusty | x86_64, i386, aarch64/arm64
16.04 | xenial | x86_64, i386


- 3，安装 gitlab-ce ，虽然换了国内的镜像，但是软件大小有400M，还是需要安装一会儿
```bash
sudo apt-get update
sudo apt-get install gitlab-ce
```

安装完成后出现如下界面：
```bash
       *.                  *.
      ***                 ***
     *****               *****
    .******             *******
    ********            ********
   ,,,,,,,,,***********,,,,,,,,,
  ,,,,,,,,,,,*********,,,,,,,,,,,
  .,,,,,,,,,,,*******,,,,,,,,,,,,
      ,,,,,,,,,*****,,,,,,,,,.
         ,,,,,,,****,,,,,,
            .,,,***,,,,
                ,*,.



     _______ __  __          __
    / ____(_) /_/ /   ____ _/ /_
   / / __/ / __/ /   / __ `/ __ \
  / /_/ / / /_/ /___/ /_/ / /_/ /
  \____/_/\__/_____/\__,_/_.___/


Thank you for installing GitLab!
GitLab was unable to detect a valid hostname for your instance.
Please configure a URL for your GitLab instance by setting `external_url`
configuration in /etc/gitlab/gitlab.rb file.
Then, you can start your GitLab instance by running the following command:
  sudo gitlab-ctl reconfigure

For a comprehensive list of configuration options please see the Omnibus GitLab readme
https://gitlab.com/gitlab-org/omnibus-gitlab/blob/master/README.md

```

## 配置Gitlab

- 4，配置gitlab实例访问路径

编辑gitlab配置文件

```bash
sudo vi /etc/gitlab/gitlab.rb
```

```bash
## GitLab configuration settings
##! This file is generated during initial installation and **is not** modified
##! during upgrades.
##! Check out the latest version of this file to know about the different
##! settings that can be configured by this file, which may be found at:
##! https://gitlab.com/gitlab-org/omnibus-gitlab/raw/master/files/gitlab-config-template/gitlab.rb.template


## GitLab URL
##! URL on which GitLab will be reachable.
##! For more details on configuring external_url see:
##! https://docs.gitlab.com/omnibus/settings/configuration.html#configuring-the-external-url-for-gitlab
external_url 'http://111.111.111.111/gitlab'

```

把 `gitlab.example.com` 修改为自己主机的ip或者域名（如果是绑定了域名），由于gitlab默认使用的80端口，这里我们使用nginx代理转发一下

在gitlab里配置nginx的监听端口，打开gitlab的配置文件

```bash
sudo vi /etc/gitlab/gitlab.rb
```
大概在文件中间部分，948行，有如下配置，去掉#注释，修改`nil`为`8888`
```bash
 946 ##! **Override only if you use a reverse proxy**
 947 ##! Docs: https://docs.gitlab.com/omnibus/settings/nginx.html#setting-the-nginx-listen-port
 948 nginx['listen_port'] = 8888
 949
```


## 配置Nginx代理

保存文件退出。接下来配置nginx，打开nginx配置文件

```bash
sudo vi /etc/nginx/conf.d/default.conf
```

在文件里添加如下配置：

```bash

upstream git {
    server  localhost:8888;
}

server {
    listen 80;
    server_name 111.111.111.111;

    location /gitlab {
        # 设置最大允许上传单个的文件大小
        client_max_body_size 1024m;
        proxy_redirect off;
        #以下确保 gitlab中项目的 url 是域名而不是 http://git，不可缺少
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        # 反向代理到 gitlab 内置的 nginx
        proxy_pass http://git/gitlab;
        index index.html index.htm;
    }
}

```

保存退出，重新加载nginx配置

```bash
sudo nginx -s reload
```

重新加载gitlab配置并重启
```bash
sudo gitlab-ctl reconfigure
sudo gitlab-ctl restart
```

打开必要的服务

```bash
sudo service sshd start
sudo service postfix start
```

查看gitlab运行状态

```bash
sudo gitlab-ctl status
run: alertmanager: (pid 26373) 1361s; run: log: (pid 26390) 1361s
run: gitaly: (pid 26289) 1362s; run: log: (pid 26306) 1362s
run: gitlab-monitor: (pid 26314) 1362s; run: log: (pid 26337) 1361s
run: gitlab-workhorse: (pid 32531) 125s; run: log: (pid 26280) 1362s
run: logrotate: (pid 25729) 1411s; run: log: (pid 26282) 1362s
run: nginx: (pid 32549) 125s; run: log: (pid 26281) 1362s
run: node-exporter: (pid 25949) 1399s; run: log: (pid 26388) 1361s
run: postgres-exporter: (pid 26397) 1360s; run: log: (pid 26487) 1360s
run: postgresql: (pid 25351) 1462s; run: log: (pid 26333) 1362s
run: prometheus: (pid 26346) 1361s; run: log: (pid 26366) 1361s
run: redis: (pid 25267) 1468s; run: log: (pid 26255) 1363s
run: redis-exporter: (pid 26034) 1391s; run: log: (pid 26338) 1361s
run: sidekiq: (pid 32456) 137s; run: log: (pid 26258) 1363s
run: unicorn: (pid 32631) 115s; run: log: (pid 26334) 1362s

```

打开浏览器，访问 `http://111.111.111.111/gitlab` 开始使用



> Reference
> - https://zh.wikipedia.org/wiki/Gitlab
> - https://about.gitlab.com/installation/#ubuntu
> - https://www.jianshu.com/p/a86a1529d253
> - https://docs.gitlab.com/omnibus/settings/smtp.html
> - https://www.jianshu.com/p/4a5877d1e14b
> - https://www.zybuluo.com/lovemiffy/note/418758