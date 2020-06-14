---
title: 在Ubuntu下安装MongoDB
categories: linux
tags: [ubuntu,mongodb]
---

MongoDB是专为可扩展性，高性能和高可用性而设计的面向文档的数据库


## 一、安装MongoDB

- 1，添加公钥
```bash
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 9DA31620334BD75D9DCB49F368818C72E52529D4
```

- 2，添加源到本地
```bash
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.3 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.3.list
```
> ps:可以把`https://repo.mongodb.org/apt/ubuntu`替换成`http://mirrors.aliyun.com/mongodb/apt/ubuntu`，安装时下载速度会有所改善


- 3，更新本地包数据库
```bash
sudo apt-get update
```

- 4，安装MongoDB
```bash
sudo apt-get install -y mongodb-org
```

## 二、配置MongoDB

MongoDB默认的配置文件存放在`/etc/mongod.conf`，通过修改这个配置文件可以更改MongoDB的数据实例目录，端口，访问ip

通过vi命令打开，如下：

```bash
sudo vi /etc/mongod.conf

# mongod.conf

# for documentation of all options, see:
#   http://docs.mongodb.org/manual/reference/configuration-options/

# Where and how to store data.
storage:
  dbPath: /var/lib/mongodb
  journal:
    enabled: true
#  engine:
#  mmapv1:
#  wiredTiger:

# where to write logging data.
systemLog:
  destination: file
  logAppend: true
  path: /var/log/mongodb/mongod.log

# network interfaces
net:
  port: 9007
  bindIp: 0.0.0.0

```

### 2.1、修改访问端口

在文件偏底部部分，找到 `port`，修改为自己想要的端口，这里修改为 `9007` (建议修改，使用默认端口容易被遭到攻击)

### 2.2、修改访问的ip

在同样的地方找到 `bindIp` 修改允许访问的ip，这里修改为 `0.0.0.0` 表示任意ip都可访问到此服务（线上环境根据实际情况配置）

保存，退出即可

### 2.3、添加访问用户
 
- 2.3.1 启动MongoDB 
```bash
sudo service mongod start
```
通过查看日志文件`/var/log/mongodb/mongod.log`，如果有如下内容表示启动成功
```bash
[initandlisten] waiting for connections on port 9007
```

- 2.3.2 通过shell连接到MongoDB(MongoDB默认端口是27017，由于上面修改了端口，连接的时候需要指定)
```bash
mongo --port 9007
MongoDB shell version: 3.3
connecting to: 127.0.0.1:9007/test
> 
```

- 2.3.3 创建用户(MongoDB默认有一个test的库，下面的操作结果为创建一个demo用户，密码是123456，并且指定test库可以读写)
```bash
db.createUser({user:'demo',pwd:'123456',roles: [{role:'readWrite',db:'test'}]})
```

- 2.3.4 校验新增的用户是否成功
```bash
db.auth('demo','123456')
1
```
控制台打印`1`代表验证成功，`0`代表失败
    
### 2.4、开启安全检查

编辑`/etc/mongod.conf`文件打开权限验证（在文件偏底部，找到`#security`，放开注释改成如下所示）

```bash
sudo vi /etc/mongod.conf

security:
  authorization: enabled
```
保存，退出

重新启动MongoDB，然后使用用户登录

```bash
sudo service mongod restart
mongo --port 9007 -u demo -p 123456
MongoDB shell version: 3.3
connecting to: 127.0.0.1:9007/test
> 
```

查看我们创建的用户，校验是否成功
```bash
> show users
  {
  	"_id" : "test.demo",
  	"user" : "demo",
  	"db" : "test",
  	"roles" : [
  		{
  			"role" : "readWrite",
  			"db" : "test"
  		}
  	]
  }
```





[返回目录]({{ site.baseurl }}{% post_url linux/2018-02-27-install-java-ee-environment-on-ubuntu %})


> Reference:
> - https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/
> - https://docs.mongodb.com/manual/reference/configuration-options/#security-options
