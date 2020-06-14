---
title: 在ubuntu下使用mysqlbinlog恢复drop后的数据
categories: database
tags: [mysql,mysqlbinlog,drop,ubuntu]
---

## 背景

我肯定跟mysql过不去，覆盖数据在我身上已经发生了2次了，难道我这是上演从删库到跑路么？

在上次覆盖了数据之后，我就告诉自己操作数据库先备份，即便错了也可以恢复，这次操作之前我已经很谨慎了，可惜最后还是做错了。

同事辛苦几周操作的数据被我一秒钟给覆盖了，我没有着急，我淡定，我回想上次误操作后，打开了mysql的日志功能，据说可以一定程度的恢复数据，
这次我来验证一下，通过我几个小时的挣扎，数据恢复过来，但从结果来看，并不是100%的恢复了，没办法，只能再花时间精力把数据重新来过。

只可惜什么都有就是没有后悔药，写下此篇，引以为戒，**数据一定一定一定要备份！！！操作一定一定一定要谨慎！！！**

虽然没有100%的恢复，但是如果没有开启`log_bin`那一定就恢复不了了，至少还有个救命稻草，下文介绍如何使用`mysqlbinlog`命令恢复drop后的数据

## 查看mysql日志是否开启

进入mysql的配置目录，一般在`/etc/mysql/mysql.conf.d`目录里面，有如下两个文件

```bash
thxopen@Thxopen:/etc/mysql/mysql.conf.d$ ls
mysqld.cnf  mysqld_safe_syslog.cnf
```

编辑`mysqld.cnf`文件，找到`log_bin`配置

```bash
# The following can be used as easy to replay backup logs or for replication.
# note: if you are setting up a replication slave, see README.Debian about
#       other settings you may need to change.
server-id               = 1
log_bin                 = /var/log/mysql/mysql-bin.log
expire_logs_days        = 10
max_binlog_size   = 500M
```

默认情况下`log_bin`功能是关闭的，把 `server-id` 和 `log_bin` 取消注释，保存退出，重启mysql即可。

## 查找 mysql 日志文件
 
根据`log_bin`配置的日志目录，我找到了日志

```bash
thxopen@Thxopen:/etc/mysql/mysql.conf.d$ cd /var/log/mysql/
thxopen@Thxopen:/var/log/mysql$ ls
error.log  mysql-bin.000001  mysql-bin.000002  mysql-bin.000003  mysql-bin.000004  mysql-bin.index
thxopen@Thxopen:/var/log/mysql$ ll
总用量 364160
drwxr-x--- 1 mysql adm          512 8月  29 10:06 ./
drwxrwxr-x 1 root  syslog       512 11月 18  2017 ../
-rw-r----- 1 mysql adm    108427282 8月  29 10:13 error.log
-rw-r----- 1 mysql mysql        154 8月  28 19:37 mysql-bin.000001
-rw-r----- 1 mysql mysql        177 8月  28 19:55 mysql-bin.000002
-rw-r----- 1 mysql mysql  264390804 8月  29 10:06 mysql-bin.000003
-rw-r----- 1 mysql mysql        154 8月  29 10:06 mysql-bin.000004
-rw-r----- 1 mysql mysql        128 8月  29 10:06 mysql-bin.index

```

`mysql-bin.000001` 即为我们需要的日志文件，使用`mysqlbinlog`命令可以导出可执行的sql文件

## mysqlbinlog 介绍

像一般的系统一样，我们会对系统的每一个操作记录下操作日志，而`log_bin`就是对数据库的每一个操作记录下的一个二进制的日志，里面包含了我们
所有执行的sql语句。

通过此命令，我们可以导出我们操作的sql语句，这样达到恢复数据的目的。
关于`mysqlbinlog`命令几个常使用的参数，可以帮助我们更快的找到我们的数据

- `--start-position` 起始位置（不包含）
- `--stop-position` 截止位置
- `--start-datetime` 起始时间
- `--stop-datetime` 截止时间
- `--base64-output=decode-rows` 输出的文件base64解码

更多关于`mysqlbinlog`的参数可以通过参数`--help`获取

## 模拟实际的操作，新增数据，修改数据，删除表，恢复数据

- 1，创建一张测试表

```sql
create table tb_recovery_test
(
  id int not null ,
  column1 varchar(32)          null,
  column2 varchar(32)          null
);
```

运行结果：

```bash
mysql> create table tb_recovery_test
    -> (
    ->   id int not null ,
    ->   column1 varchar(32)          null,
    ->   column2 varchar(32)          null
    -> );
Query OK, 0 rows affected (0.05 sec)
```

- 2，插入测试数据

```sql
insert into tb_recovery_test (id,column1,column2) 
      values (1,'数据操作需谨慎','定期备份数据');
insert into tb_recovery_test (id,column1,column2) 
      values (2,'后悔了','可是没有后悔药');
```

运行结果：

```bash
mysql> insert into tb_recovery_test (id,column1,column2)
    ->       values (1,'数据操作需谨慎','定期备份数据');
Query OK, 1 row affected (0.01 sec)

mysql> insert into tb_recovery_test (id,column1,column2)
    ->       values (2,'后悔了','可是没有后悔药');
Query OK, 1 row affected (0.01 sec)

mysql> select * from tb_recovery_test;
+----+--------------+-------------------+
| id | column1      | column2           |
+----+--------------+-------------------+
|  1 | 数据操作需谨慎 | 定期备份数据        |
|  2 | 后悔了        | 可是没有后悔药      |
+----+--------------+-------------------+
2 rows in set (0.00 sec)
```

- 3，修改数据，这里模拟一下真实的场景，对数据进行修改，删除，添加字段操作

```sql
update tb_recovery_test set column1 = '数据操作需谨慎,定期备份数据,什么都有就是没有后悔药',
        column2 = null
        where id = 2;
delete from tb_recovery_test where id = 1;
ALTER TABLE tb_recovery_test ADD column3 varchar(32) NULL;

insert into tb_recovery_test (id,column1,column2,column3) 
      values (3,'新增了一个字段','新增了一个字段','新增了一个字段');
update tb_recovery_test set column3 = '修改了新增字段的值' where id = 3;
update tb_recovery_test set column2 = '修改了column2字段的值' where id = 2;
```

运行结果：

```bash
mysql> update tb_recovery_test set column1 = '数据操作需谨慎,定期备份数据,什么都有就是没有后悔药',
    ->         column2 = null
    ->         where id = 2;
Query OK, 1 row affected (0.01 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> delete from tb_recovery_test where id = 1;
Query OK, 1 row affected (0.00 sec)

mysql> ALTER TABLE tb_recovery_test ADD column3 varchar(32) NULL;
Query OK, 0 rows affected (0.10 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> insert into tb_recovery_test (id,column1,column2,column3)
    ->       values (3,'新增了一个字段','新增了一个字段','新增了一个字段');
Query OK, 1 row affected (0.01 sec)

mysql> update tb_recovery_test set column3 = '修改了新增字段的值' where id = 3;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> update tb_recovery_test set column2 = '修改了column2字段的值' where id = 2;
Query OK, 1 row affected (0.01 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> select * from tb_recovery_test;
+----+-------------------------------------------------+-------------------------+-----------------+
| id | column1                                         | column2                 | column3         |
+----+-------------------------------------------------+-------------------------+-----------------+
|  2 | 数据操作需谨慎,定期备份数据,什么都有就是没有后悔药       | 修改了column2字段的值     | NULL            |
|  3 | 新增了一个字段                                     | 新增了一个字段            | 修改了新增字段的值 |
+----+--------------------------------------------------+------------------------+-----------------+
2 rows in set (0.00 sec)
```

- 4，最后我误操作，把表`tb_recovery_test`删除

```sql
drop table tb_recovery_test;
```

运行结果：

```bash
mysql> drop table tb_recovery_test;
Query OK, 0 rows affected (0.03 sec)
```

显然，最后一步操作之后，我们之前所做的操作白费了，还好我们开启了`log_bin`功能，通过日志来找回误操作删除的数据

## 通过日志文件恢复 tb_recovery_test 数据

进入到mysql保存日志的目录，通过前面配置可以知道日志文件保存在 `/var/log/mysql/` 下

```bash
thxopen@Thxopen:/var/log/mysql$ cd /var/log/mysql/
thxopen@Thxopen:/var/log/mysql$ ls
error.log  mysql-bin.000001  mysql-bin.000002  mysql-bin.000003  mysql-bin.000004  mysql-bin.index
thxopen@Thxopen:/var/log/mysql$ ll
总用量 364164
drwxr-x--- 1 mysql adm          512 8月  29 10:06 ./
drwxrwxr-x 1 root  syslog       512 11月 18  2017 ../
-rw-r----- 1 mysql adm    108427282 8月  29 10:13 error.log
-rw-r----- 1 mysql mysql        154 8月  28 19:37 mysql-bin.000001
-rw-r----- 1 mysql mysql        177 8月  28 19:55 mysql-bin.000002
-rw-r----- 1 mysql mysql  264390804 8月  29 10:06 mysql-bin.000003
-rw-r----- 1 mysql mysql       3411 8月  29 13:14 mysql-bin.000004
-rw-r----- 1 mysql mysql        128 8月  29 10:06 mysql-bin.index
```

从列出的文件可以看出 `mysql-bin.000004` 文件是最近改动的，一般来说文件的后缀是增长的，根据实际情况，这里我选择最后一个日志文件进行恢复操作

执行导出日志操作，先看看导出的sql具体是什么样的

```bash
sudo mysqlbinlog --base64-output=decode-rows /var/log/mysql/mysql-bin.000004 > ./recovery-decode.sql
```
ps: 添加`--base64-output=decode-rows` 参数导出的sql文件里不包含数据部分（即insert，delete，update），主要是方便分析，实际导出的
sql是**不能**加这个参数的

查看导出sql，我截取了文件的开头和结尾部分，中间部分省略

```bash
/*!50530 SET @@SESSION.PSEUDO_SLAVE_MODE=1*/;
/*!50003 SET @OLD_COMPLETION_TYPE=@@COMPLETION_TYPE,COMPLETION_TYPE=0*/;
DELIMITER /*!*/;
# at 4
#180829 10:06:42 server id 1  end_log_pos 123 CRC32 0xa3922cf1 	Start: binlog v 4, server v 5.7.22-0ubuntu0.16.04.1-log created 180829 10:06:42 at startup
# Warning: this binlog is either in use or was not closed properly.
ROLLBACK/*!*/;
# at 123
#180829 10:06:43 server id 1  end_log_pos 154 CRC32 0xa18892ca 	Previous-GTIDs
# [empty]
# at 154
#180829 13:07:48 server id 1  end_log_pos 219 CRC32 0x7e4bd4d6 	Anonymous_GTID	last_committed=0	sequence_number=1	rbr_only=no
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 219
#180829 13:07:48 server id 1  end_log_pos 425 CRC32 0x331aefcb 	Query	thread_id=15	exec_time=0	error_code=0
use `thxopen`/*!*/;
SET TIMESTAMP=1535519268/*!*/;
SET @@session.pseudo_thread_id=15/*!*/;
SET @@session.foreign_key_checks=1, @@session.sql_auto_is_null=0, @@session.unique_checks=1, @@session.autocommit=1/*!*/;
SET @@session.sql_mode=1436549152/*!*/;
SET @@session.auto_increment_increment=1, @@session.auto_increment_offset=1/*!*/;
/*!\C utf8 *//*!*/;
SET @@session.character_set_client=33,@@session.collation_connection=33,@@session.collation_server=8/*!*/;
SET @@session.lc_time_names=0/*!*/;
SET @@session.collation_database=DEFAULT/*!*/;
create table tb_recovery_test
(
  id int not null ,
  column1 varchar(32)          null,
  column2 varchar(32)          null
)
/*!*/;
# at 425

#省略了中间的内容
#省略了中间的内容
#省略了中间的内容
#省略了中间的内容

# at 1970
#180829 13:10:10 server id 1  end_log_pos 2035 CRC32 0x8c8ac254 	Anonymous_GTID	last_committed=6	sequence_number=7	rbr_only=yes
/*!50718 SET TRANSACTION ISOLATION LEVEL READ COMMITTED*//*!*/;
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 2035
#180829 13:10:10 server id 1  end_log_pos 2110 CRC32 0x1d22149c 	Query	thread_id=15	exec_time=0	error_code=0
SET TIMESTAMP=1535519410/*!*/;
BEGIN
/*!*/;
# at 2110
#180829 13:10:10 server id 1  end_log_pos 2181 CRC32 0xcccc7fab 	Table_map: `thxopen`.`tb_recovery_test` mapped to number 168
# at 2181
#180829 13:10:10 server id 1  end_log_pos 2287 CRC32 0x322b3ff6 	Write_rows: table id 168 flags: STMT_END_F
# at 2287
#180829 13:10:10 server id 1  end_log_pos 2318 CRC32 0xaa8d7acf 	Xid = 3075
COMMIT/*!*/;
# at 2318
#180829 13:10:10 server id 1  end_log_pos 2383 CRC32 0x05b7bd59 	Anonymous_GTID	last_committed=7	sequence_number=8	rbr_only=yes
/*!50718 SET TRANSACTION ISOLATION LEVEL READ COMMITTED*//*!*/;
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 2383
#180829 13:10:10 server id 1  end_log_pos 2458 CRC32 0x171d4eab 	Query	thread_id=15	exec_time=0	error_code=0
SET TIMESTAMP=1535519410/*!*/;
BEGIN
/*!*/;
# at 2458
#180829 13:10:10 server id 1  end_log_pos 2529 CRC32 0xe95d3272 	Table_map: `thxopen`.`tb_recovery_test` mapped to number 168
# at 2529
#180829 13:10:10 server id 1  end_log_pos 2713 CRC32 0x4386d86f 	Update_rows: table id 168 flags: STMT_END_F
# at 2713
#180829 13:10:10 server id 1  end_log_pos 2744 CRC32 0xcde5fff8 	Xid = 3076
COMMIT/*!*/;
# at 2744
#180829 13:10:19 server id 1  end_log_pos 2809 CRC32 0x3b9cc08b 	Anonymous_GTID	last_committed=8	sequence_number=9	rbr_only=yes
/*!50718 SET TRANSACTION ISOLATION LEVEL READ COMMITTED*//*!*/;
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 2809
#180829 13:10:19 server id 1  end_log_pos 2884 CRC32 0xb86bbce3 	Query	thread_id=15	exec_time=0	error_code=0
SET TIMESTAMP=1535519419/*!*/;
BEGIN
/*!*/;
# at 2884
#180829 13:10:19 server id 1  end_log_pos 2955 CRC32 0x7b3dc472 	Table_map: `thxopen`.`tb_recovery_test` mapped to number 168
# at 2955
#180829 13:10:19 server id 1  end_log_pos 3180 CRC32 0x1ff6807f 	Update_rows: table id 168 flags: STMT_END_F
# at 3180
#180829 13:10:19 server id 1  end_log_pos 3211 CRC32 0xda138c1f 	Xid = 3077
COMMIT/*!*/;
# at 3211
#180829 13:14:00 server id 1  end_log_pos 3276 CRC32 0x9e823905 	Anonymous_GTID	last_committed=9	sequence_number=10	rbr_only=no
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 3276
#180829 13:14:00 server id 1  end_log_pos 3411 CRC32 0x2f9c474a 	Query	thread_id=15	exec_time=0	error_code=0
SET TIMESTAMP=1535519640/*!*/;
DROP TABLE `tb_recovery_test` /* generated by server */
/*!*/;
SET @@SESSION.GTID_NEXT= 'AUTOMATIC' /* added by mysqlbinlog */ /*!*/;
DELIMITER ;
# End of log file
/*!50003 SET COMPLETION_TYPE=@OLD_COMPLETION_TYPE*/;
/*!50530 SET @@SESSION.PSEUDO_SLAVE_MODE=0*/;
```

从文件里可以看出我一开始的建表语句，也就是我每一步操作都记录在这个日志里，那么我只要把我之前的每一步都再执行一边，数据就能恢复回来，我们按照这个思路操作

- 1，先确定起始position，这里我选择 `end_log_pos 219` ，前面参数解释了，起始点是不包含的所以要选建表语句之前的操作点（也可以选择起始时间 `180829 13:07:48`）
- 2，确定截止position `end_log_pos 3276` 或截止时间 `180829 13:14:00`
- 3，导出恢复数据sql

```bash
#使用position导出sql
sudo mysqlbinlog --start-position=219 --stop-position=3276 /var/log/mysql/mysql-bin.000004 > ./recovery-restore.sql
#使用时间导出sql
sudo mysqlbinlog --start-datetime='2018-08-29 13:07:48' --stop-datetime='2018-08-29 13:14:00' /var/log/mysql/mysql-bin.000004 > ./recovery-restore.sql
```

下面为导出的sql前半部分，可以看到`BINLOG`后面有base64的字符，这个就是对数据的操作

```bash
/*!50530 SET @@SESSION.PSEUDO_SLAVE_MODE=1*/;
/*!50003 SET @OLD_COMPLETION_TYPE=@@COMPLETION_TYPE,COMPLETION_TYPE=0*/;
DELIMITER /*!*/;
# at 4
#180829 10:06:42 server id 1  end_log_pos 123 CRC32 0xa3922cf1 	Start: binlog v 4, server v 5.7.22-0ubuntu0.16.04.1-log created 180829 10:06:42 at startup
# Warning: this binlog is either in use or was not closed properly.
ROLLBACK/*!*/;
BINLOG '
sv+FWw8BAAAAdwAAAHsAAAABAAQANS43LjIyLTB1YnVudHUwLjE2LjA0LjEtbG9nAAAAAAAAAAAA
AAAAAAAAAAAAAAAAAACy/4VbEzgNAAgAEgAEBAQEEgAAXwAEGggAAAAICAgCAAAACgoKKioAEjQA
AfEskqM=
'/*!*/;
# at 219
#180829 13:07:48 server id 1  end_log_pos 425 CRC32 0x331aefcb 	Query	thread_id=15	exec_time=0	error_code=0
use `thxopen`/*!*/;
SET TIMESTAMP=1535519268/*!*/;
SET @@session.pseudo_thread_id=15/*!*/;
SET @@session.foreign_key_checks=1, @@session.sql_auto_is_null=0, @@session.unique_checks=1, @@session.autocommit=1/*!*/;
SET @@session.sql_mode=1436549152/*!*/;
SET @@session.auto_increment_increment=1, @@session.auto_increment_offset=1/*!*/;
/*!\C utf8 *//*!*/;
SET @@session.character_set_client=33,@@session.collation_connection=33,@@session.collation_server=8/*!*/;
SET @@session.lc_time_names=0/*!*/;
SET @@session.collation_database=DEFAULT/*!*/;
create table tb_recovery_test
(
  id int not null ,
  column1 varchar(32)          null,
  column2 varchar(32)          null
)
/*!*/;
# at 425

#省略了后面的
#省略了后面的
#省略了后面的
#省略了后面的

```

- 4，导入sql到数据库

```bash
mysql -uroot -p database_name < recovery-restore.sql
```

ps：还可以加入[-f] 参数，强制执行，即忽略所有错误，根据实际情况使用这个参数

运行结果：

```bash
thxopen@Thxopen:~$ mysql -uroot -p thxopen < recovery-restore.sql
Enter password:
thxopen@Thxopen:~$ mysql -uroot -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 17
Server version: 5.7.22-0ubuntu0.16.04.1-log (Ubuntu)

Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use thxopen;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show tables;
+-------------------+
| Tables_in_thxopen |
+-------------------+
| tb_recovery_test  |
+-------------------+
1 row in set (0.00 sec)

mysql> select * from tb_recovery_test;
+----+----------------------------------------------+------------------------+------------------+
| id | column1                                      | column2                | column3          |
+----+----------------------------------------------+------------------------+------------------+
|  2 | 数据操作需谨慎,定期备份数据,什么都有就是没有后悔药    | 修改了column2字段的值    | NULL             |
|  3 | 新增了一个字段                                  | 新增了一个字段           | 修改了新增字段的值  |
+----+----------------------------------------------+------------------------+------------------+
2 rows in set (0.00 sec)

mysql>
```

导入之后，重新登录到mysql，查看表，查看数据，已经恢复到drop之前的状态了，到此大功告成，数据恢复成功！


## 导入恢复数据到数据库可能会遇到的错误

- Duplicate entry 重复的实体，出现此错误代表恢复的sql脚本里，包含创建了实体的语句，重新执行一边，则报错

- Duplicate column name 重复的列名称，理由同上

- Duplicate key name 重复的键索引，理由同上

- Can't find record in 'table_name' 由于业务逻辑，有些数据删除，修改，新增，删除，修改反复这样操作，导致恢复的时候出现此错误

- Cannot add or update a child row: a foreign key constraint fails (`thxopen`.`tb_recovery_test`, CONSTRAINT `FKan5m1ygnxb4ibmq1ncqklji26` FOREIGN KEY (`rid`) REFERENCES `tb_recovery_relate` (`id`))
由于外键关联，修改表的数据在另一个表中不存在导致

- Table 'table_name' already exists 重复的表，恢复sql脚本了包含数据库里已经存在的表

- Can't write; duplicate key in table 创建表的外键名称重复了

- MySQL server has gone away 由于导入的恢复sql太大，超过了mysql的限制，需要修改配置文件`/etc/mysql/mysql.conf.d/mysqld.cnf` 里 `max_allowed_packet = 16M` 配置，根据实际情况调大一些


## 总结

对于文中的例子，恢复数据是100%的，但是在实际的项目中，虽然开启了`log_bin`，
但不能想着万事大吉就不管了，我就是惨痛的经历，数据感觉50%都没恢复到。简直是血的教训，血的教训，血的教训啊~

不过从这个也可以看出系统设计本身的问题，逻辑复杂，耦合度高，关联性太强，比如我误删的数据，
由于表之间的关系比较复杂，恢复出来的数据有很多问题。

主要是如下几个问题：

- 手动在Intellij Database Console执行的update语句，在恢复之后没有体现出来，字段是null
- 部分表的数据完全没有，一条数据也没有
- 由于我是覆盖数据，都是先drop表，然后又create了表，而日志文件又没有从最开始创建表的，
所以执行恢复sql的时候，报了如上列出的所有error，对于重复表，重复字段这些，我把日志里的删除，可以解决，
但是对于数据的关联出错，找不到记录的就太多了，没有办法一一修复，为了执行完成sql，导入的时候加了[-f]参数，忽略所有错误

因为上面这些问题，加上日志本身也不全，导致数据根本没法100%的恢复

说了这么多，还是一句话，**没有规矩，不成方圆**，做事要小心谨慎，按照一定的流程做事就可以减少犯错误的几率。

**定期备份数据，操作数据谨慎**



> Reference 
> - [https://www.cnblogs.com/cenalulu/archive/2013/01/08/2850820.html][2850820]
> - [https://blog.csdn.net/leshami/article/details/41962243][41962243]
> - [http://www.unixfbi.com/499.html][499]
> - [http://blog.51cto.com/suifu/1845457][1845457]
> - [https://www.cnblogs.com/leezhxing/p/3347610.html][3347610]
> - [http://www.hankcs.com/appos/database/mysql-restore-dropped-table.html][mysql-restore-dropped-table]
> - [https://blog.csdn.net/weixin_41004350/article/details/78547005][78547005]
> - [https://blog.csdn.net/xyz846/article/details/52210542][52210542]

[2850820]:https://www.cnblogs.com/cenalulu/archive/2013/01/08/2850820.html
[41962243]:https://blog.csdn.net/leshami/article/details/41962243
[499]:http://www.unixfbi.com/499.html
[1845457]:http://blog.51cto.com/suifu/1845457
[3347610]:https://www.cnblogs.com/leezhxing/p/3347610.html
[mysql-restore-dropped-table]:http://www.hankcs.com/appos/database/mysql-restore-dropped-table.html
[78547005]:https://blog.csdn.net/weixin_41004350/article/details/78547005
[52210542]:https://blog.csdn.net/xyz846/article/details/52210542