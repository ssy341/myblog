---
title: 在Ubuntu下安装RabbitMQ
categories: linux
tags: [ubuntu,rabbitmq]
---


RabbitMQ是一套开源（MPL）的消息队列服务软件，是由 LShift 提供的一个 Advanced Message Queuing Protocol (AMQP) 
的开源实现，由以高性能、健壮以及可伸缩性出名的 Erlang 写成。


## 安装RabbitMQ

- 第一步：添加源
```bash
echo 'deb http://www.rabbitmq.com/debian/ testing main' | sudo tee /etc/apt/sources.list.d/rabbitmq.list
```

- 第二步：添加秘钥
```bash
wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add
```

- 第三步：更新源
```bash
sudo apt-get update
```

- 第四步：安装
```bash
sudo apt-get install rabbitmq-server
```

## 开启WEB管理界面

RabbitMQ默认没有打开WEB管理界面，需要手动操作

```bash
sudo rabbitmq-plugins enable rabbitmq_management
```
通过上面的命令即可开启，打开浏览器访问 http://127.0.0.1:15672 即可看到如下界面

![RabbitMQ登录界面]({{ site.baseurl }}/assets/images/post/linux/ubuntu/rabbitmq_login.png)

使用默认用户`guest`，密码`guest`可以登录


## 允许非本地访问管理界面

打开管理界面的功能开关后，虽然guest用户可以访问，但远程访问还需要配置用户才能可以


- 第一步：配置vhost
语法：add_vhost <vhostname>
```bash
sudo rabbitmqctl add_vhost demo_vhost
sudo rabbitmqctl list_vhosts
```
rabbitmq默认创建了`/`vhost，也可以自己创建一个新的vhost来区分，这里可以直接使用默认的

- 第二步：添加用户
语法：add_user <username> <password>
```bash
sudo rabbitmqctl add_user demo 123456
sudo rabbitmqctl list_users
```
添加一个用户名demo，密码为123456的用户记录

- 第三步：设置权限
语法：set_permissions [-p <vhost>] <user> <conf> <write> <read>
```bash
sudo rabbitmqctl set_permissions -p "/" demo ".*" ".*" ".*"
sudo rabbitmqctl list_permissions -p /
```
设置demo用户可以操作 `/` vhost 下的资源，比如queue

- 第四步：设置角色
语法：set_user_tags <user> <role>
```bash
sudo rabbitmqctl set_user_tags demo administrator
sudo rabbitmqctl list_users
```
设置demo用户为 administrator (管理员权限)

完成上面操作之后即可使用demo用户登录，登录成功之后可以看到如下界面：

![RabbitMQ管理界面]({{ site.baseurl }}/assets/images/post/linux/ubuntu/rabbitmq_dashborad.png)

## 配置nginx通过80端口访问RabbitMQ web界面


在ngxin配置文件里配置以下代码

```bash
server{
    server_name www.example.com;
    listen 80;
    location /rabbitmq/ {
        proxy_pass http://127.0.0.1:15672/;
        rewrite ^/rabbitmq/(.*)$ /$1 break;
        client_body_buffer_size 128k;
        proxy_send_timeout   90;
        proxy_read_timeout   90;
        proxy_buffer_size    4k;
        proxy_buffers     16 32k;
        proxy_busy_buffers_size 64k;
        proxy_temp_file_write_size 64k;
        proxy_connect_timeout 30s;
        proxy_set_header   Host   $host;
        proxy_set_header   X-Real-IP  $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
    }
    
    location /rabbitmq/api/queues/ {
        proxy_pass http://127.0.0.1:15672/api/queues/%2F/;
    }
    
    location /rabbitmq/api/exchanges/ {
        proxy_pass http://127.0.0.1:15672/api/exchanges/%2F/;
    }
}
```

这样，原本要通过 `http://www.example.com:15672` 访问，现在可以直接使用 `http://www.example.com/rabbitmq` 访问web界面


[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})


> Reference:
> - https://blog.csdn.net/rickey17/article/details/72756766
> - https://blog.haohtml.com/archives/15249
