---
title: 使用Docker安装oracle 11g
categories: [linux, docker]
tags: [oracle 11g, docker]
---

## 简介

Oracle Database，又名Oracle RDBMS，或简称Oracle。是甲骨文公司的一款关系数据库管理系统。

借助docker，安装oracle不再困难，只需要几步即可。

需要注意，在参考本文章之前，需要具备操作docker的基础，怎么使用docker，可以参考[这里]()

## 安装
 
### 一、准备工作

从[oracle 官网]( http://www.oracle.com/technetwork/database/enterprise-edition/downloads/index-092322.html)
下载所需要的安装包，这里我们以[oracle 11g](https://www.oracle.com/technetwork/database/enterprise-edition/downloads/112010-linx8664soft-100572.html)
为例子，分别下载 `linux.x64_11gR2_database_1of2.zip` 和 `linux.x64_11gR2_database_2of2.zip`两个压缩包，下载完成后解压到D盘
(如下目录结构)

```bash
D:.
├─database
│  ├─doc
│  ├─install
│  ├─response
│  ├─rpm
│  ├─sshsetup
│  ├─stage
│  ├─runInstaller
│  └─welcome.html
```


### 二、创建一个volume
本次演示是使用Docker Desktop，在windows下直接使用本地路径会有权限的问题，为了避免这个问题，采用卷的方式挂在到容器上。

1，首先创建一个卷
```bash
docker volume create oracleinstall
```
2，把下载好的oracle安装文件拷贝到卷中，这里需要使用busybox（一个软件工具箱，里边集成了linux中几百个常用的linux命令以及工具）镜像。
先把卷挂在到`/data`目录下，然后使用`cp`命令把本机文件拷贝到卷中（windows要绕一点，如果是linux不用这么麻烦）
```bash
docker run -v oracleinstall:/data --name helper busybox true
docker cp d:\\database helper:/data
docker rm helper
```
拷贝需要一会儿，耐心等待（busybox没有，系统会自动下载）

3，查看拷贝到卷中的文件，确认拷贝成功
```bash
docker run -v oracleinstall:/data --name helper busybox ls /data/database

doc
install
response
rpm
runInstaller
sshsetup
stage
welcome.html

docker rm helper
```
如果输出如上内容代表成功拷贝到卷中

### 三、安装oracle 11g镜像到docker

1、搜索符合条件的镜像

```bash
docker search oracle

NAME                                  DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
oraclelinux                           Official Docker builds of Oracle Linux.         573                 [OK]
jaspeen/oracle-11g                    Docker image for Oracle 11g database            99                                      [OK]
oracle/openjdk                        Docker images containing OpenJDK Oracle Linux   55                                      [OK]
……
```

这里选择安装 jaspeen/oracle-11g
```bash
docker pull jaspeen/oracle-11g
```
等待下载安装完成

查看下载好的镜像
```bash
docker images

REPOSITORY                 TAG                 IMAGE ID            CREATED             SIZE
busybox                    latest              af2f74c517aa        2 weeks ago         1.2MB
jaspeen/oracle-11g         latest              0c8711fe4f0f        3 years ago         281MB
```

这个镜像没有直接安装好oracle，他帮我们配置好了环境，只需要把安装文件配置好，启动镜像，它就会自动安装

### 四、安装oracle

```bash
docker run --privileged --name oracle11g -p 1521:1521 -v oracleinstall:/install jaspeen/oracle-11g

Database is not installed. Installing...
Installing Oracle Database 11g
Starting Oracle Universal Installer...

Checking Temp space: must be greater than 120 MB.   Actual 47303 MB    Passed
Checking swap space: must be greater than 150 MB.   Actual 1023 MB    Passed
Preparing to launch Oracle Universal Installer from /tmp/OraInstall2019-04-17_08-14-23AM. Please wait ...You can find the log of this install session at:
 /opt/oracle/oraInventory/logs/installActions2019-04-17_08-14-23AM.log
The following configuration scripts need to be executed as the "root" user.
 #!/bin/sh
 #Root scripts to run

/opt/oracle/oraInventory/orainstRoot.sh
/opt/oracle/app/product/11.2.0/dbhome_1/root.sh
To execute the configuration scripts:
         1. Open a terminal window
         2. Log in as "root"
         3. Run the scripts
         4. Return to this window and hit "Enter" key to continue

Successfully Setup Software.
Changing permissions of /opt/oracle/oraInventory.
Adding read,write permissions for group.
Removing read,write,execute permissions for world.

Changing groupname of /opt/oracle/oraInventory to oinstall.
The execution of the script is complete.
Check /opt/oracle/app/product/11.2.0/dbhome_1/install/root_7f53f07c93e5_2019-04-17_08-29-29.log for the output of root script
Checking shared memory...
Filesystem      Size  Used Avail Use% Mounted on
shm              64M     0   64M   0% /dev/shm
Database does not exist. Creating database...
2019-04-17 08:29:32
START DBCA
tail: tail: cannot open '/opt/oracle/app/diag/rdbms/orcl/orcl/trace/alert_orcl.log' for readingcannot open '/opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/listener.log' for reading: No such file or directory: No such file or directory

tail: tail: cannot watch parent directory of '/opt/oracle/app/diag/rdbms/orcl/orcl/trace/alert_orcl.log'cannot watch parent directory of '/opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/listener.log': No such file or directory: No such file or directory

tail: tail: inotify cannot be used, reverting to pollinginotify cannot be used, reverting to polling

/bin/cat: /proc/sys/net/core/wmem_default: No such file or directory
/bin/cat: /proc/sys/net/core/wmem_default: No such file or directory
/bin/cat: /proc/sys/net/core/wmem_default: No such file or directory
Copying database files
1% complete
tail: '/opt/oracle/app/diag/rdbms/orcl/orcl/trace/alert_orcl.log' has appeared;  following end of new file
3% complete
11% complete
alertlog: Wed Apr 17 08:33:28 2019
alertlog: Starting ORACLE instance (normal)
alertlog: LICENSE_MAX_SESSION = 0
alertlog: LICENSE_SESSIONS_WARNING = 0
alertlog: Shared memory segment for instance monitoring created
alertlog: Picked latch-free SCN scheme 3
alertlog: Using LOG_ARCHIVE_DEST_1 parameter default value as USE_DB_RECOVERY_FILE_DEST
alertlog: Autotune of undo retention is turned on.
alertlog: IMODE=BR
alertlog: ILAT =27
alertlog: LICENSE_MAX_USERS = 0
alertlog: SYS auditing is disabled
alertlog: Starting up:
alertlog: Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
alertlog: With the Partitioning, OLAP, Data Mining and Real Application Testing options.
alertlog: Using parameter settings in client-side pfile /opt/oracle/app/admin/orcl/pfile/init.ora on machine 7f53f07c93e5
alertlog: System parameters with non-default values:
alertlog: processes                = 150
alertlog: sga_target               = 500M
alertlog: memory_target            = 0
alertlog: control_files            = "/opt/oracle/app/oradata/orcl/control01.ctl"
alertlog: control_files            = "/opt/oracle/app/flash_recovery_area/orcl/control02.ctl"
alertlog: db_block_size            = 8192
alertlog: compatible               = "11.2.0.0.0"
alertlog: db_recovery_file_dest    = "/opt/oracle/app/flash_recovery_area"
alertlog: db_recovery_file_dest_size= 3882M
alertlog: undo_tablespace          = "UNDOTBS1"
alertlog: remote_login_passwordfile= "EXCLUSIVE"
alertlog: db_domain                = ""
alertlog: dispatchers              = "(PROTOCOL=TCP) (SERVICE=orclXDB)"
alertlog: audit_file_dest          = "/opt/oracle/app/admin/orcl/adump"
alertlog: audit_trail              = "DB"
alertlog: db_name                  = "orcl"
alertlog: open_cursors             = 300
alertlog: pga_aggregate_target     = 100M
alertlog: diagnostic_dest          = "/opt/oracle/app"
alertlog: Wed Apr 17 08:33:28 2019
alertlog: PMON started with pid=2, OS id=3070
alertlog: Wed Apr 17 08:33:28 2019
alertlog: VKTM started with pid=3, OS id=3072
alertlog: VKTM running at (100ms) precision
alertlog: Wed Apr 17 08:33:28 2019
alertlog: GEN0 started with pid=4, OS id=3076
alertlog: Wed Apr 17 08:33:28 2019
alertlog: DIAG started with pid=5, OS id=3078
alertlog: Wed Apr 17 08:33:28 2019
alertlog: DBRM started with pid=6, OS id=3080
alertlog: Wed Apr 17 08:33:28 2019
alertlog: PSP0 started with pid=7, OS id=3082
alertlog: Wed Apr 17 08:33:28 2019
alertlog: DIA0 started with pid=8, OS id=3084
alertlog: Wed Apr 17 08:33:28 2019
alertlog: MMAN started with pid=9, OS id=3086
alertlog: Wed Apr 17 08:33:28 2019
alertlog: DBW0 started with pid=10, OS id=3088
alertlog: Wed Apr 17 08:33:28 2019
alertlog: LGWR started with pid=11, OS id=3090
alertlog: Wed Apr 17 08:33:28 2019
alertlog: CKPT started with pid=12, OS id=3092
alertlog: Wed Apr 17 08:33:28 2019
alertlog: SMON started with pid=13, OS id=3094
alertlog: Wed Apr 17 08:33:28 2019
alertlog: RECO started with pid=14, OS id=3096
alertlog: Wed Apr 17 08:33:28 2019
alertlog: MMON started with pid=15, OS id=3098
alertlog: Wed Apr 17 08:33:28 2019
alertlog: MMNL started with pid=16, OS id=3100
alertlog: starting up 1 dispatcher(s) for network address '(ADDRESS=(PARTIAL=YES)(PROTOCOL=TCP))'...
alertlog: starting up 1 shared server(s) ...
alertlog: ORACLE_BASE from environment = /opt/oracle/app
alertlog: Wed Apr 17 08:33:29 2019
alertlog: Full restore complete of datafile 4 to datafile copy /opt/oracle/app/oradata/orcl/users01.dbf.  Elapsed time: 0:00:00
alertlog: checkpoint is 945183
alertlog: Full restore complete of datafile 3 to datafile copy /opt/oracle/app/oradata/orcl/undotbs01.dbf.  Elapsed time: 0:00:00
alertlog: checkpoint is 945183
18% complete
alertlog: Wed Apr 17 08:34:02 2019
alertlog: Full restore complete of datafile 2 to datafile copy /opt/oracle/app/oradata/orcl/sysaux01.dbf.  Elapsed time: 0:00:25
alertlog: checkpoint is 945183
alertlog: last deallocation scn is 944925
26% complete
37% complete
Creating and starting Oracle instance
alertlog: Wed Apr 17 08:34:15 2019
alertlog: Full restore complete of datafile 1 to datafile copy /opt/oracle/app/oradata/orcl/system01.dbf.  Elapsed time: 0:00:40
alertlog: checkpoint is 945183
alertlog: last deallocation scn is 940169
alertlog: Wed Apr 17 08:34:15 2019
alertlog: Create controlfile reuse set database "orcl"
alertlog: MAXINSTANCES 8
alertlog: MAXLOGHISTORY 1
alertlog: MAXLOGFILES 16
alertlog: MAXLOGMEMBERS 3
alertlog: MAXDATAFILES 100
alertlog: Datafile
alertlog: '/opt/oracle/app/oradata/orcl/system01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/sysaux01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/undotbs01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/users01.dbf'
alertlog: LOGFILE GROUP 1 ('/opt/oracle/app/oradata/orcl/redo01.log') SIZE 51200K,
alertlog: GROUP 2 ('/opt/oracle/app/oradata/orcl/redo02.log') SIZE 51200K,
alertlog: GROUP 3 ('/opt/oracle/app/oradata/orcl/redo03.log') SIZE 51200K RESETLOGS
alertlog: WARNING: Default Temporary Tablespace not specified in CREATE DATABASE command
alertlog: Default Temporary Tablespace will be necessary for a locally managed database in future release
alertlog: Wed Apr 17 08:34:19 2019
alertlog: Successful mount of redo thread 1, with mount id 1533571207
alertlog: Completed: Create controlfile reuse set database "orcl"
alertlog: MAXINSTANCES 8
alertlog: MAXLOGHISTORY 1
alertlog: MAXLOGFILES 16
alertlog: MAXLOGMEMBERS 3
alertlog: MAXDATAFILES 100
alertlog: Datafile
alertlog: '/opt/oracle/app/oradata/orcl/system01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/sysaux01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/undotbs01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/users01.dbf'
alertlog: LOGFILE GROUP 1 ('/opt/oracle/app/oradata/orcl/redo01.log') SIZE 51200K,
alertlog: GROUP 2 ('/opt/oracle/app/oradata/orcl/redo02.log') SIZE 51200K,
alertlog: GROUP 3 ('/opt/oracle/app/oradata/orcl/redo03.log') SIZE 51200K RESETLOGS
alertlog: Shutting down instance (immediate)
alertlog: Shutting down instance: further logons disabled
alertlog: Stopping background process MMNL
alertlog: Stopping background process MMON
alertlog: License high water mark = 1
alertlog: All dispatchers and shared servers shutdown
alertlog: ALTER DATABASE CLOSE NORMAL
alertlog: ORA-1109 signalled during: ALTER DATABASE CLOSE NORMAL...
alertlog: ALTER DATABASE DISMOUNT
alertlog: Completed: ALTER DATABASE DISMOUNT
alertlog: ARCH: Archival disabled due to shutdown: 1089
alertlog: Shutting down archive processes
alertlog: Archiving is disabled
alertlog: Archive process shutdown avoided: 0 active
alertlog: ARCH: Archival disabled due to shutdown: 1089
alertlog: Shutting down archive processes
alertlog: Archiving is disabled
alertlog: Archive process shutdown avoided: 0 active
alertlog: Wed Apr 17 08:34:24 2019
alertlog: Stopping background process VKTM:
40% complete
alertlog: Wed Apr 17 08:34:26 2019
alertlog: Instance shutdown complete
alertlog: Wed Apr 17 08:34:26 2019
alertlog: Starting ORACLE instance (normal)
alertlog: LICENSE_MAX_SESSION = 0
alertlog: LICENSE_SESSIONS_WARNING = 0
alertlog: Picked latch-free SCN scheme 3
alertlog: Using LOG_ARCHIVE_DEST_1 parameter default value as USE_DB_RECOVERY_FILE_DEST
alertlog: Autotune of undo retention is turned on.
alertlog: IMODE=BR
alertlog: ILAT =27
alertlog: LICENSE_MAX_USERS = 0
alertlog: SYS auditing is disabled
alertlog: Starting up:
alertlog: Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
alertlog: With the Partitioning, OLAP, Data Mining and Real Application Testing options.
alertlog: Using parameter settings in client-side pfile /opt/oracle/app/cfgtoollogs/dbca/orcl/initorclTemp.ora on machine 7f53f07c93e5
alertlog: System parameters with non-default values:
alertlog: processes                = 150
alertlog: sga_target               = 500M
alertlog: memory_target            = 0
alertlog: control_files            = "/opt/oracle/app/oradata/orcl/control01.ctl"
alertlog: control_files            = "/opt/oracle/app/flash_recovery_area/orcl/control02.ctl"
alertlog: db_block_size            = 8192
alertlog: compatible               = "11.2.0.0.0"
alertlog: db_recovery_file_dest    = "/opt/oracle/app/flash_recovery_area"
alertlog: db_recovery_file_dest_size= 3882M
alertlog: _no_recovery_through_resetlogs= TRUE
alertlog: undo_tablespace          = "UNDOTBS1"
alertlog: remote_login_passwordfile= "EXCLUSIVE"
alertlog: db_domain                = ""
alertlog: dispatchers              = "(PROTOCOL=TCP) (SERVICE=orclXDB)"
alertlog: audit_file_dest          = "/opt/oracle/app/admin/orcl/adump"
alertlog: audit_trail              = "DB"
alertlog: db_name                  = "orcl"
alertlog: open_cursors             = 300
alertlog: pga_aggregate_target     = 100M
alertlog: diagnostic_dest          = "/opt/oracle/app"
alertlog: Wed Apr 17 08:34:26 2019
alertlog: PMON started with pid=2, OS id=3119
alertlog: Wed Apr 17 08:34:26 2019
alertlog: VKTM started with pid=3, OS id=3121
alertlog: VKTM running at (100ms) precision
alertlog: Wed Apr 17 08:34:26 2019
alertlog: GEN0 started with pid=4, OS id=3125
alertlog: Wed Apr 17 08:34:26 2019
alertlog: DIAG started with pid=5, OS id=3127
alertlog: Wed Apr 17 08:34:26 2019
alertlog: DBRM started with pid=6, OS id=3129
alertlog: Wed Apr 17 08:34:26 2019
alertlog: PSP0 started with pid=7, OS id=3131
alertlog: Wed Apr 17 08:34:26 2019
alertlog: DIA0 started with pid=8, OS id=3133
alertlog: Wed Apr 17 08:34:26 2019
alertlog: MMAN started with pid=9, OS id=3135
alertlog: Wed Apr 17 08:34:26 2019
alertlog: DBW0 started with pid=10, OS id=3137
alertlog: Wed Apr 17 08:34:26 2019
alertlog: LGWR started with pid=11, OS id=3139
alertlog: Wed Apr 17 08:34:26 2019
alertlog: CKPT started with pid=12, OS id=3141
alertlog: Wed Apr 17 08:34:26 2019
alertlog: SMON started with pid=13, OS id=3143
alertlog: Wed Apr 17 08:34:26 2019
alertlog: RECO started with pid=14, OS id=3145
alertlog: Wed Apr 17 08:34:26 2019
alertlog: MMON started with pid=15, OS id=3147
alertlog: starting up 1 dispatcher(s) for network address '(ADDRESS=(PARTIAL=YES)(PROTOCOL=TCP))'...Wed Apr 17 08:34:26 2019
alertlog:
alertlog: MMNL started with pid=16, OS id=3149
alertlog: starting up 1 shared server(s) ...
alertlog: ORACLE_BASE from environment = /opt/oracle/app
alertlog: Wed Apr 17 08:34:26 2019
alertlog: Create controlfile reuse set database "orcl"
alertlog: MAXINSTANCES 8
alertlog: MAXLOGHISTORY 1
alertlog: MAXLOGFILES 16
alertlog: MAXLOGMEMBERS 3
alertlog: MAXDATAFILES 100
alertlog: Datafile
alertlog: '/opt/oracle/app/oradata/orcl/system01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/sysaux01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/undotbs01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/users01.dbf'
alertlog: LOGFILE GROUP 1 ('/opt/oracle/app/oradata/orcl/redo01.log') SIZE 51200K,
alertlog: GROUP 2 ('/opt/oracle/app/oradata/orcl/redo02.log') SIZE 51200K,
alertlog: GROUP 3 ('/opt/oracle/app/oradata/orcl/redo03.log') SIZE 51200K RESETLOGS
alertlog: WARNING: Default Temporary Tablespace not specified in CREATE DATABASE command
alertlog: Default Temporary Tablespace will be necessary for a locally managed database in future release
alertlog: Successful mount of redo thread 1, with mount id 1533621394
alertlog: Completed: Create controlfile reuse set database "orcl"
alertlog: MAXINSTANCES 8
alertlog: MAXLOGHISTORY 1
alertlog: MAXLOGFILES 16
alertlog: MAXLOGMEMBERS 3
alertlog: MAXDATAFILES 100
alertlog: Datafile
alertlog: '/opt/oracle/app/oradata/orcl/system01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/sysaux01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/undotbs01.dbf',
alertlog: '/opt/oracle/app/oradata/orcl/users01.dbf'
alertlog: LOGFILE GROUP 1 ('/opt/oracle/app/oradata/orcl/redo01.log') SIZE 51200K,
alertlog: GROUP 2 ('/opt/oracle/app/oradata/orcl/redo02.log') SIZE 51200K,
alertlog: GROUP 3 ('/opt/oracle/app/oradata/orcl/redo03.log') SIZE 51200K RESETLOGS
alertlog: Stopping background process MMNL
alertlog: Stopping background process MMON
alertlog: Starting background process MMON
alertlog: Starting background process MMNL
alertlog: Wed Apr 17 08:34:33 2019
alertlog: MMON started with pid=15, OS id=3160
alertlog: Wed Apr 17 08:34:33 2019
alertlog: MMNL started with pid=16, OS id=3162
alertlog: ALTER SYSTEM enable restricted session;
alertlog: alter database "orcl" open resetlogs
alertlog: RESETLOGS after incomplete recovery UNTIL CHANGE 945183
alertlog: Errors in file /opt/oracle/app/diag/rdbms/orcl/orcl/trace/orcl_ora_3154.trc:
alertlog: ORA-00313: open failed for members of log group 1 of thread 1
alertlog: ORA-00312: online log 1 thread 1: '/opt/oracle/app/oradata/orcl/redo01.log'
alertlog: ORA-27037: unable to obtain file status
alertlog: Linux-x86_64 Error: 2: No such file or directory
alertlog: Additional information: 3
alertlog: Errors in file /opt/oracle/app/diag/rdbms/orcl/orcl/trace/orcl_ora_3154.trc:
alertlog: ORA-00313: open failed for members of log group 1 of thread 1
alertlog: ORA-00312: online log 1 thread 1: '/opt/oracle/app/oradata/orcl/redo01.log'
alertlog: ORA-27037: unable to obtain file status
alertlog: Linux-x86_64 Error: 2: No such file or directory
alertlog: Additional information: 3
alertlog: Errors in file /opt/oracle/app/diag/rdbms/orcl/orcl/trace/orcl_ora_3154.trc:
alertlog: ORA-00313: open failed for members of log group 2 of thread 1
alertlog: ORA-00312: online log 2 thread 1: '/opt/oracle/app/oradata/orcl/redo02.log'
alertlog: ORA-27037: unable to obtain file status
alertlog: Linux-x86_64 Error: 2: No such file or directory
alertlog: Additional information: 3
alertlog: Errors in file /opt/oracle/app/diag/rdbms/orcl/orcl/trace/orcl_ora_3154.trc:
alertlog: ORA-00313: open failed for members of log group 2 of thread 1
alertlog: ORA-00312: online log 2 thread 1: '/opt/oracle/app/oradata/orcl/redo02.log'
alertlog: ORA-27037: unable to obtain file status
alertlog: Linux-x86_64 Error: 2: No such file or directory
alertlog: Additional information: 3
alertlog: Wed Apr 17 08:34:37 2019
alertlog: Errors in file /opt/oracle/app/diag/rdbms/orcl/orcl/trace/orcl_ora_3154.trc:
alertlog: ORA-00313: open failed for members of log group 3 of thread 1
alertlog: ORA-00312: online log 3 thread 1: '/opt/oracle/app/oradata/orcl/redo03.log'
alertlog: ORA-27037: unable to obtain file status
alertlog: Linux-x86_64 Error: 2: No such file or directory
alertlog: Additional information: 3
alertlog: Errors in file /opt/oracle/app/diag/rdbms/orcl/orcl/trace/orcl_ora_3154.trc:
alertlog: ORA-00313: open failed for members of log group 3 of thread 1
alertlog: ORA-00312: online log 3 thread 1: '/opt/oracle/app/oradata/orcl/redo03.log'
alertlog: ORA-27037: unable to obtain file status
alertlog: Linux-x86_64 Error: 2: No such file or directory
alertlog: Additional information: 3
alertlog: Wed Apr 17 08:34:43 2019
alertlog: Setting recovery target incarnation to 2
alertlog: Wed Apr 17 08:34:43 2019
alertlog: Checker run found 4 new persistent data failures
alertlog: Wed Apr 17 08:34:44 2019
alertlog: Assigning activation ID 1533621394 (0x5b693492)
alertlog: Thread 1 opened at log sequence 1
alertlog: Current log# 1 seq# 1 mem# 0: /opt/oracle/app/oradata/orcl/redo01.log
alertlog: Successful open of redo thread 1
alertlog: MTTR advisory is disabled because FAST_START_MTTR_TARGET is not set
alertlog: Wed Apr 17 08:34:45 2019
alertlog: SMON: enabling cache recovery
alertlog: Successfully onlined Undo Tablespace 2.
alertlog: Dictionary check beginning
alertlog: Tablespace 'TEMP' #3 found in data dictionary,
alertlog: but not in the controlfile. Adding to controlfile.
alertlog: Dictionary check complete
alertlog: Verifying file header compatibility for 11g tablespace encryption..
alertlog: Verifying 11g file header compatibility for tablespace encryption completed
alertlog: SMON: enabling tx recovery
alertlog: *********************************************************************
alertlog: WARNING: The following temporary tablespaces contain no files.
alertlog: This condition can occur when a backup controlfile has
alertlog: been restored.  It may be necessary to add files to these
alertlog: tablespaces.  That can be done using the SQL statement:
alertlog:
alertlog: ALTER TABLESPACE <tablespace_name> ADD TEMPFILE
alertlog:
alertlog: Alternatively, if these temporary tablespaces are no longer
alertlog: needed, then they can be dropped.
alertlog: Empty temporary tablespace: TEMP
alertlog: *********************************************************************
alertlog: Database Characterset is US7ASCII
alertlog: Wed Apr 17 08:34:48 2019
alertlog: Create Relation IPS_PACKAGE_UNPACK_HISTORY
alertlog: No Resource Manager plan active
alertlog: **********************************************************
alertlog: WARNING: Files may exists in db_recovery_file_dest
alertlog: that are not known to the database. Use the RMAN command
alertlog: CATALOG RECOVERY AREA to re-catalog any such files.
alertlog: If files cannot be cataloged, then manually delete them
alertlog: using OS command.
alertlog: One of the following events caused this:
alertlog: 1. A backup controlfile was restored.
alertlog: 2. A standby controlfile was restored.
alertlog: 3. The controlfile was re-created.
alertlog: 4. db_recovery_file_dest had previously been enabled and
alertlog: then disabled.
alertlog: **********************************************************
alertlog: replication_dependency_tracking turned off (no async multimaster replication found)
alertlog: Starting background process QMNC
alertlog: Wed Apr 17 08:34:50 2019
alertlog: QMNC started with pid=20, OS id=3168
alertlog: LOGSTDBY: Validating controlfile with logical metadata
alertlog: LOGSTDBY: Validation complete
alertlog: Global Name changed to ORCL
45% complete
alertlog: Completed: alter database "orcl" open resetlogs
alertlog: alter database rename global_name to "orcl"
alertlog: Completed: alter database rename global_name to "orcl"
alertlog: ALTER TABLESPACE TEMP ADD TEMPFILE '/opt/oracle/app/oradata/orcl/temp01.dbf' SIZE 20480K REUSE AUTOEXTEND ON NEXT 640K MAXSIZE UNLIMITED
alertlog: Completed: ALTER TABLESPACE TEMP ADD TEMPFILE '/opt/oracle/app/oradata/orcl/temp01.dbf' SIZE 20480K REUSE AUTOEXTEND ON NEXT 640K MAXSIZE UNLIMITED
alertlog: ALTER DATABASE DEFAULT TABLESPACE "USERS"
alertlog: Completed: ALTER DATABASE DEFAULT TABLESPACE "USERS"
alertlog: alter database character set INTERNAL_CONVERT AL32UTF8
alertlog: Wed Apr 17 08:35:01 2019
alertlog: Thread 1 advanced to log sequence 2 (LGWR switch)
alertlog: Current log# 2 seq# 2 mem# 0: /opt/oracle/app/oradata/orcl/redo02.log
alertlog: Wed Apr 17 08:35:02 2019
alertlog: Updating character set in controlfile to AL32UTF8
alertlog: Synchronizing connection with database character set information
alertlog: SYS.RULE$ (CONDITION) - CLOB representation altered
alertlog: SYS.SCHEDULER$_EVENT_LOG (ADDITIONAL_INFO) - CLOB representation altered
alertlog: SYS.WRI$_OPTSTAT_HISTHEAD_HISTORY (EXPRESSION) - CLOB representation altered
alertlog: SYS.WRI$_REPT_FILES (SYS_NC00005$) - CLOB representation altered
alertlog: SYS.SNAP$ (REL_QUERY) - CLOB representation altered
alertlog: SYS.SNAP$ (ALIAS_TXT) - CLOB representation altered
alertlog: SYS.WRI$_ADV_OBJECTS (ATTR4) - CLOB representation altered
alertlog: SYS.WRI$_ADV_OBJECTS (OTHER) - CLOB representation altered
alertlog: SYS.WRI$_DBU_FEATURE_METADATA (INST_CHK_LOGIC) - CLOB representation altered
alertlog: SYS.WRI$_DBU_FEATURE_METADATA (USG_DET_LOGIC) - CLOB representation altered
alertlog: Wed Apr 17 08:35:12 2019
alertlog: SYS.WRI$_DBU_HWM_METADATA (LOGIC) - CLOB representation altered
alertlog: SYS.SUM$ (SRC_STMT) - CLOB representation altered
alertlog: SYS.SUM$ (DEST_STMT) - CLOB representation altered
alertlog: SYS.WRI$_ADV_DIRECTIVE_META (DATA) - CLOB representation altered
alertlog: SYS.ATTRIBUTE_TRANSFORMATIONS$ (XSL_TRANSFORMATION) - CLOB representation altered
alertlog: SYS.AUD$ (SQLBIND) - CLOB representation altered
alertlog: SYS.AUD$ (SQLTEXT) - CLOB representation altered
alertlog: Wed Apr 17 08:36:46 2019
alertlog: SYS.METASTYLESHEET (STYLESHEET) - CLOB representation altered
alertlog: XDB.XDB$RESOURCE (SYS_NC00027$) - CLOB representation altered
alertlog: XDB.XDB$XDB_READY (DATA) - CLOB representation altered
alertlog: XDB.XDB$DXPTAB (SYS_NC00006$) - CLOB representation altered
alertlog: ORDDATA.ORDDCM_MAPPING_DOCS (SYS_NC00007$) - CLOB representation altered
alertlog: ORDDATA.ORDDCM_CT_PRED_OPRD (SYS_NC00004$) - CLOB representation altered
alertlog: Wed Apr 17 08:36:57 2019
alertlog: ORDDATA.ORDDCM_DOCS (SYS_NC00005$) - CLOB representation altered
alertlog: Wed Apr 17 08:37:28 2019
alertlog: Starting background process SMCO
alertlog: Wed Apr 17 08:37:28 2019
alertlog: SMCO started with pid=23, OS id=3174
alertlog: Wed Apr 17 08:38:13 2019
alertlog: MDSYS.SDO_COORD_OP_PARAM_VALS (PARAM_VALUE_FILE) - CLOB representation altered
alertlog: Wed Apr 17 08:38:17 2019
alertlog: Thread 1 advanced to log sequence 3 (LGWR switch)
alertlog: Current log# 3 seq# 3 mem# 0: /opt/oracle/app/oradata/orcl/redo03.log
alertlog: Wed Apr 17 08:38:27 2019
alertlog: MDSYS.SDO_COORD_OP_PARAM_VALS (SYS_NC00008$) - CLOB representation altered
alertlog: MDSYS.SDO_XML_SCHEMAS (XMLSCHEMA) - CLOB representation altered
alertlog: MDSYS.OLS_DIR_CATEGORY_TYPES (SYS_NC00004$) - CLOB representation altered
alertlog: MDSYS.OPENLS_NODES (SYS_NC00004$) - CLOB representation altered
alertlog: MDSYS.SDO_GEOR_XMLSCHEMA_TABLE (XMLSCHEMA) - CLOB representation altered
alertlog: MDSYS.SDO_STYLES_TABLE (DEFINITION) - CLOB representation altered
alertlog: SYSMAN.MGMT_IP_ELEM_DEFAULT_PARAMS (VALUE) - CLOB representation altered
alertlog: SYSMAN.MGMT_IP_REPORT_ELEM_PARAMS (VALUE) - CLOB representation altered
alertlog: SYSMAN.MGMT_IP_SQL_STATEMENTS (SQL_STATEMENT) - CLOB representation altered
alertlog: SYSMAN.MGMT_JOB_LARGE_PARAMS (PARAM_VALUE) - CLOB representation altered
alertlog: SYSMAN.MGMT_SEC_INFO (B64_LOCAL_CA) - CLOB representation altered
alertlog: SYSMAN.MGMT_SEC_INFO (B64_INTERNET_CA) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_BANNER (BANNER) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_BUTTON_TEMPLATES (TEMPLATE) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_PAGE_GENERIC_ATTR (ATTRIBUTE_VALUE) - CLOB representation altered
alertlog: Wed Apr 17 08:38:46 2019
alertlog: APEX_030200.WWV_FLOW_PAGE_PLUGS (PLUG_SOURCE) - CLOB representation altered
alertlog: Wed Apr 17 08:38:53 2019
alertlog: Thread 1 cannot allocate new log, sequence 4
alertlog: Checkpoint not complete
alertlog: Current log# 3 seq# 3 mem# 0: /opt/oracle/app/oradata/orcl/redo03.log
alertlog: APEX_030200.WWV_FLOW_PAGE_PLUGS (CUSTOM_ITEM_LAYOUT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_PAGE_PLUG_TEMPLATES (TEMPLATE) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_PAGE_PLUG_TEMPLATES (TEMPLATE2) - CLOB representation altered
alertlog: Wed Apr 17 08:39:25 2019
alertlog: Thread 1 advanced to log sequence 4 (LGWR switch)
alertlog: Current log# 1 seq# 4 mem# 0: /opt/oracle/app/oradata/orcl/redo01.log
alertlog: Wed Apr 17 08:39:25 2019
alertlog: APEX_030200.WWV_FLOW_PAGE_PLUG_TEMPLATES (TEMPLATE3) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_PROCESSING (PROCESS_SQL_CLOB) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_INSTALL (DEINSTALL_SCRIPT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_STEP_PROCESSING (PROCESS_SQL_CLOB) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_TEMPLATES (HEADER_TEMPLATE) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_TEMPLATES (FOOTER_TEMPLATE) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_TEMPLATES (BOX) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (LIST_TEMPLATE_CURRENT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (LIST_TEMPLATE_NONCURRENT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (SUB_LIST_ITEM_CURRENT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (SUB_LIST_ITEM_NONCURRENT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (ITEM_TEMPLATE_CURR_W_CHILD) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (ITEM_TEMPLATE_NONCURR_W_CHILD) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (SUB_TEMPLATE_CURR_W_CHILD) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_LIST_TEMPLATES (SUB_TEMPLATE_NONCURR_W_CHILD) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_REGION_REPORT_COLUMN (PK_COL_SOURCE) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_ROW_TEMPLATES (ROW_TEMPLATE1) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_ROW_TEMPLATES (ROW_TEMPLATE2) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_ROW_TEMPLATES (ROW_TEMPLATE3) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_ROW_TEMPLATES (ROW_TEMPLATE4) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_SHORTCUTS (SHORTCUT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_STEPS (HELP_TEXT) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_STEPS (HTML_PAGE_HEADER) - CLOB representation altered
alertlog: Wed Apr 17 08:39:35 2019
alertlog: APEX_030200.WWV_FLOW_WORKSHEETS (SQL_QUERY) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_CUSTOM_AUTH_SETUPS (PAGE_SENTRY_FUNCTION) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_CUSTOM_AUTH_SETUPS (SESS_VERIFY_FUNCTION) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_CUSTOM_AUTH_SETUPS (PRE_AUTH_PROCESS) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_CUSTOM_AUTH_SETUPS (AUTH_FUNCTION) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_CUSTOM_AUTH_SETUPS (POST_AUTH_PROCESS) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_FLASH_CHARTS (CHART_XML) - CLOB representation altered
alertlog: APEX_030200.WWV_FLOW_FLASH_CHART_SERIES (SERIES_QUERY) - CLOB representation altered
alertlog: Refreshing type attributes with new character set information
50% complete
alertlog: Completed: alter database character set INTERNAL_CONVERT AL32UTF8
55% complete
alertlog: Wed Apr 17 08:39:43 2019
alertlog: ALTER SYSTEM disable restricted session;
56% complete
60% complete
62% complete
Completing Database Creation
66% complete
70% complete
73% complete
alertlog: Wed Apr 17 08:40:00 2019
alertlog: Thread 1 advanced to log sequence 5 (LGWR switch)
alertlog: Current log# 2 seq# 5 mem# 0: /opt/oracle/app/oradata/orcl/redo02.log
alertlog: Wed Apr 17 08:40:01 2019
alertlog: Starting background process CJQ0
alertlog: Wed Apr 17 08:40:01 2019
alertlog: CJQ0 started with pid=30, OS id=3203
alertlog: Wed Apr 17 08:40:01 2019
alertlog: db_recovery_file_dest_size of 3882 MB is 0.00% used. This is a
alertlog: user-specified limit on the amount of space that will be used by this
alertlog: database for recovery-related files, and does not reflect the amount of
alertlog: space available in the underlying filesystem or ASM diskgroup.
85% complete
alertlog: Wed Apr 17 08:40:47 2019
alertlog: Shutting down instance (immediate)
alertlog: Stopping background process SMCO
alertlog: Shutting down instance: further logons disabled
alertlog: Wed Apr 17 08:43:01 2019
alertlog: Stopping background process CJQ0
alertlog: Wed Apr 17 08:43:01 2019
alertlog: Stopping background process QMNC
alertlog: Stopping background process MMNL
alertlog: Stopping background process MMON
alertlog: License high water mark = 12
alertlog: All dispatchers and shared servers shutdown
alertlog: ALTER DATABASE CLOSE NORMAL
alertlog: Wed Apr 17 08:43:04 2019
alertlog: SMON: disabling tx recovery
alertlog: SMON: disabling cache recovery
alertlog: Wed Apr 17 08:43:39 2019
alertlog: Shutting down archive processes
alertlog: Archiving is disabled
alertlog: Archive process shutdown avoided: 0 active
alertlog: Thread 1 closed at log sequence 5
alertlog: Successful close of redo thread 1
alertlog: Wed Apr 17 08:43:39 2019
alertlog: Completed: ALTER DATABASE CLOSE NORMAL
alertlog: ALTER DATABASE DISMOUNT
alertlog: Completed: ALTER DATABASE DISMOUNT
alertlog: ARCH: Archival disabled due to shutdown: 1089
alertlog: Shutting down archive processes
alertlog: Archiving is disabled
alertlog: Archive process shutdown avoided: 0 active
alertlog: ARCH: Archival disabled due to shutdown: 1089
alertlog: Shutting down archive processes
alertlog: Archiving is disabled
alertlog: Archive process shutdown avoided: 0 active
alertlog: Wed Apr 17 08:43:41 2019
alertlog: Stopping background process VKTM:
alertlog: Wed Apr 17 08:43:43 2019
alertlog: Instance shutdown complete
alertlog: Wed Apr 17 08:43:43 2019
alertlog: Starting ORACLE instance (normal)
alertlog: LICENSE_MAX_SESSION = 0
alertlog: LICENSE_SESSIONS_WARNING = 0
alertlog: Picked latch-free SCN scheme 3
alertlog: Using LOG_ARCHIVE_DEST_1 parameter default value as USE_DB_RECOVERY_FILE_DEST
alertlog: Autotune of undo retention is turned on.
alertlog: IMODE=BR
alertlog: ILAT =27
alertlog: LICENSE_MAX_USERS = 0
alertlog: SYS auditing is disabled
alertlog: Starting up:
alertlog: Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
alertlog: With the Partitioning, OLAP, Data Mining and Real Application Testing options.
alertlog: Using parameter settings in server-side spfile /opt/oracle/app/product/11.2.0/dbhome_1/dbs/spfileorcl.ora
alertlog: System parameters with non-default values:
alertlog: processes                = 150
alertlog: sga_target               = 500M
alertlog: memory_target            = 0
alertlog: control_files            = "/opt/oracle/app/oradata/orcl/control01.ctl"
alertlog: control_files            = "/opt/oracle/app/flash_recovery_area/orcl/control02.ctl"
alertlog: db_block_size            = 8192
alertlog: compatible               = "11.2.0.0.0"
alertlog: db_recovery_file_dest    = "/opt/oracle/app/flash_recovery_area"
alertlog: db_recovery_file_dest_size= 3882M
alertlog: undo_tablespace          = "UNDOTBS1"
alertlog: remote_login_passwordfile= "EXCLUSIVE"
alertlog: db_domain                = ""
alertlog: dispatchers              = "(PROTOCOL=TCP) (SERVICE=orclXDB)"
alertlog: audit_file_dest          = "/opt/oracle/app/admin/orcl/adump"
alertlog: audit_trail              = "DB"
alertlog: db_name                  = "orcl"
alertlog: open_cursors             = 300
alertlog: pga_aggregate_target     = 100M
alertlog: diagnostic_dest          = "/opt/oracle/app"
alertlog: Wed Apr 17 08:43:43 2019
alertlog: PMON started with pid=2, OS id=3230
alertlog: Wed Apr 17 08:43:43 2019
alertlog: VKTM started with pid=3, OS id=3232
alertlog: VKTM running at (100ms) precision
alertlog: Wed Apr 17 08:43:43 2019
alertlog: GEN0 started with pid=4, OS id=3236
alertlog: Wed Apr 17 08:43:43 2019
alertlog: DIAG started with pid=5, OS id=3238
alertlog: Wed Apr 17 08:43:44 2019
alertlog: DBRM started with pid=6, OS id=3240
alertlog: Wed Apr 17 08:43:44 2019
alertlog: PSP0 started with pid=7, OS id=3242
alertlog: Wed Apr 17 08:43:44 2019
alertlog: DIA0 started with pid=8, OS id=3244
alertlog: Wed Apr 17 08:43:44 2019
alertlog: MMAN started with pid=9, OS id=3246
alertlog: Wed Apr 17 08:43:44 2019
alertlog: DBW0 started with pid=10, OS id=3248
alertlog: Wed Apr 17 08:43:44 2019
alertlog: LGWR started with pid=11, OS id=3250
alertlog: Wed Apr 17 08:43:44 2019
alertlog: CKPT started with pid=12, OS id=3252
alertlog: Wed Apr 17 08:43:44 2019
alertlog: SMON started with pid=13, OS id=3254
alertlog: Wed Apr 17 08:43:44 2019
alertlog: RECO started with pid=14, OS id=3256
alertlog: Wed Apr 17 08:43:44 2019
alertlog: MMON started with pid=15, OS id=3258
alertlog: Wed Apr 17 08:43:44 2019
alertlog: MMNL started with pid=16, OS id=3260
alertlog: starting up 1 dispatcher(s) for network address '(ADDRESS=(PARTIAL=YES)(PROTOCOL=TCP))'...
alertlog: starting up 1 shared server(s) ...
alertlog: ORACLE_BASE from environment = /opt/oracle/app
alertlog: Wed Apr 17 08:43:44 2019
alertlog: ALTER DATABASE   MOUNT
alertlog: Changing di2dbun from  to orcl
alertlog: Successful mount of redo thread 1, with mount id 1533616832
alertlog: Database mounted in Exclusive Mode
alertlog: Lost write protection disabled
alertlog: Completed: ALTER DATABASE   MOUNT
alertlog: Wed Apr 17 08:43:50 2019
alertlog: ALTER DATABASE OPEN
alertlog: Thread 1 opened at log sequence 5
alertlog: Current log# 2 seq# 5 mem# 0: /opt/oracle/app/oradata/orcl/redo02.log
alertlog: Successful open of redo thread 1
alertlog: MTTR advisory is disabled because FAST_START_MTTR_TARGET is not set
alertlog: SMON: enabling cache recovery
alertlog: Successfully onlined Undo Tablespace 2.
alertlog: Verifying file header compatibility for 11g tablespace encryption..
alertlog: Verifying 11g file header compatibility for tablespace encryption completed
alertlog: SMON: enabling tx recovery
alertlog: Database Characterset is AL32UTF8
alertlog: No Resource Manager plan active
alertlog: replication_dependency_tracking turned off (no async multimaster replication found)
alertlog: Starting background process QMNC
alertlog: Wed Apr 17 08:43:53 2019
alertlog: QMNC started with pid=20, OS id=3272
96% complete
100% complete
Look at the log file "/opt/oracle/app/cfgtoollogs/dbca/orcl/orcl.log" for further details.
Database created.
2019-04-17 08:43:54
Changind dpdump dir to /opt/oracle/dpdump
sqlplus:
sqlplus: SQL*Plus: Release 11.2.0.1.0 Production on Wed Apr 17 08:43:54 2019
sqlplus:
sqlplus: Copyright (c) 1982, 2009, Oracle.  All rights reserved.
sqlplus:
sqlplus:
sqlplus: Connected to:
sqlplus: Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
sqlplus: With the Partitioning, OLAP, Data Mining and Real Application Testing options
sqlplus:
sqlplus: SQL>
sqlplus: Directory created.
sqlplus:
sqlplus: SQL> Disconnected from Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
sqlplus: With the Partitioning, OLAP, Data Mining and Real Application Testing options
Starting listener...
tail: cannot open '/opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/listener.log' for reading: No such file or directory
tail: cannot watch parent directory of '/opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/listener.log': No such file or directory
tail: inotify cannot be used, reverting to polling
lsnrctl:
lsnrctl: LSNRCTL for Linux: Version 11.2.0.1.0 - Production on 17-APR-2019 08:43:54
lsnrctl:
lsnrctl: Copyright (c) 1991, 2009, Oracle.  All rights reserved.
lsnrctl:
lsnrctl: Starting /opt/oracle/app/product/11.2.0/dbhome_1/bin/tnslsnr: please wait...
lsnrctl:
tail: '/opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/listener.log' has appeared;  following end of new file
alertlog: Completed: ALTER DATABASE OPEN
alertlog: Wed Apr 17 08:43:53 2019
alertlog: Starting background process CJQ0
alertlog: Wed Apr 17 08:43:54 2019
alertlog: CJQ0 started with pid=22, OS id=3286
alertlog: Wed Apr 17 08:43:54 2019
alertlog: db_recovery_file_dest_size of 3882 MB is 0.00% used. This is a
alertlog: user-specified limit on the amount of space that will be used by this
alertlog: database for recovery-related files, and does not reflect the amount of
alertlog: space available in the underlying filesystem or ASM diskgroup.
tail: '/opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/listener.log' has appeared;  following end of new file
listener: Wed Apr 17 08:43:54 2019
listener: Create Relation ADR_CONTROL
listener: Wed Apr 17 08:43:54 2019
listener: Create Relation ADR_CONTROL
listener: Create Relation ADR_INVALIDATION
listener: Create Relation ADR_INVALIDATION
listener: Create Relation INC_METER_IMPT_DEF
listener: Create Relation INC_METER_IMPT_DEF
listener: Create Relation INC_METER_PK_IMPTS
listener: Create Relation INC_METER_PK_IMPTS
lsnrctl: TNSLSNR for Linux: Version 11.2.0.1.0 - Production
lsnrctl: Log messages written to /opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/alert/log.xml
lsnrctl: Listening on: (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=7f53f07c93e5)(PORT=1521)))
lsnrctl:
lsnrctl: Connecting to (ADDRESS=(PROTOCOL=tcp)(HOST=)(PORT=1521))
lsnrctl: STATUS of the LISTENER
lsnrctl: ------------------------
lsnrctl: Alias                     LISTENER
lsnrctl: Version                   TNSLSNR for Linux: Version 11.2.0.1.0 - Production
lsnrctl: Start Date                17-APR-2019 08:44:02
lsnrctl: Uptime                    0 days 0 hr. 0 min. 7 sec
lsnrctl: Trace Level               off
lsnrctl: Security                  ON: Local OS Authentication
lsnrctl: SNMP                      OFF
lsnrctl: Listener Log File         /opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/alert/log.xml
lsnrctl: Listening Endpoints Summary...
lsnrctl: (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=7f53f07c93e5)(PORT=1521)))
lsnrctl: The listener supports no services
lsnrctl: The command completed successfully
Starting database...
tail: unrecognized file system type 0x794c7630 for '/opt/oracle/app/diag/rdbms/orcl/orcl/trace/alert_orcl.log'. please report this to bug-coreutils@gnu.org. reverting to polling
sqlplus:
sqlplus: SQL*Plus: Release 11.2.0.1.0 Production on Wed Apr 17 08:44:02 2019
sqlplus:
sqlplus: Copyright (c) 1982, 2009, Oracle.  All rights reserved.
sqlplus:
sqlplus:
sqlplus: Connected to:
sqlplus: Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
sqlplus: With the Partitioning, OLAP, Data Mining and Real Application Testing options
sqlplus:
sqlplus: SQL> Starting with pfile='/opt/oracle/app/product/11.2.0/dbhome_1/dbs/initorcl.ora' ...
sqlplus: SQL> ORA-01081: cannot start already-running ORACLE - shut it down first
sqlplus: SQL>
sqlplus: System altered.
sqlplus:
sqlplus: SQL> Disconnected from Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
sqlplus: With the Partitioning, OLAP, Data Mining and Real Application Testing options
listener: Log messages written to /opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/alert/log.xml
listener: Trace information written to /opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/ora_3299_140437755688768.trc
listener: Trace level is currently 0
listener:
listener: Started with pid=3299
listener: Listening on: (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=7f53f07c93e5)(PORT=1521)))
listener: Listener completed notification to CRS on start
listener:
listener: TIMESTAMP * CONNECT DATA [* PROTOCOL INFO] * EVENT [* SID] * RETURN CODE
listener: WARNING: Subscription for node down event still pending
listener: 17-APR-2019 08:44:02 * (CONNECT_DATA=(CID=(PROGRAM=)(HOST=7f53f07c93e5)(USER=oracle))(COMMAND=status)(ARGUMENTS=64)(SERVICE=LISTENER)(VERSION=186646784)) * status * 0
listener: Dynamic address is already listened on (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=7f53f07c93e5)(PORT=1521)))
listener: 17-APR-2019 08:44:02 * service_register * orcl * 0
listener: Log messages written to /opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/alert/log.xml
listener: Trace information written to /opt/oracle/app/diag/tnslsnr/7f53f07c93e5/listener/trace/ora_3299_140437755688768.trc
listener: Trace level is currently 0
listener:
listener: Started with pid=3299
listener: Listening on: (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=7f53f07c93e5)(PORT=1521)))
listener: Listener completed notification to CRS on start
listener:
listener: TIMESTAMP * CONNECT DATA [* PROTOCOL INFO] * EVENT [* SID] * RETURN CODE
listener: WARNING: Subscription for node down event still pending
listener: 17-APR-2019 08:44:02 * (CONNECT_DATA=(CID=(PROGRAM=)(HOST=7f53f07c93e5)(USER=oracle))(COMMAND=status)(ARGUMENTS=64)(SERVICE=LISTENER)(VERSION=186646784)) * status * 0
listener: Dynamic address is already listened on (DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=7f53f07c93e5)(PORT=1521)))
listener: 17-APR-2019 08:44:02 * service_register * orcl * 0
alertlog: Wed Apr 17 08:44:02 2019
alertlog: Starting ORACLE instance (normal)
alertlog: Wed Apr 17 08:44:02 2019
alertlog: Starting ORACLE instance (normal)
alertlog: Wed Apr 17 08:48:53 2019
alertlog: Starting background process SMCO
alertlog: Wed Apr 17 08:48:53 2019
alertlog: SMCO started with pid=23, OS id=3313
alertlog: Wed Apr 17 08:48:53 2019
alertlog: Starting background process SMCO
alertlog: Wed Apr 17 08:48:53 2019
alertlog: SMCO started with pid=23, OS id=3313
listener: Wed Apr 17 08:50:05 2019
listener: 17-APR-2019 08:50:05 * service_update * orcl * 0
listener: Wed Apr 17 08:50:05 2019
listener: 17-APR-2019 08:50:05 * service_update * orcl * 0
listener: Wed Apr 17 08:50:38 2019
listener: 17-APR-2019 08:50:38 * service_update * orcl * 0
listener: Wed Apr 17 08:50:38 2019
listener: 17-APR-2019 08:50:38 * service_update * orcl * 0
listener: Wed Apr 17 08:53:56 2019
listener: 17-APR-2019 08:53:56 * service_update * orcl * 0
listener: Wed Apr 17 08:53:56 2019
listener: 17-APR-2019 08:53:56 * service_update * orcl * 0
listener: Wed Apr 17 08:58:57 2019
listener: 17-APR-2019 08:58:57 * service_update * orcl * 0
listener: Wed Apr 17 08:58:57 2019
listener: 17-APR-2019 08:58:57 * service_update * orcl * 0
listener: Wed Apr 17 09:03:57 2019
listener: 17-APR-2019 09:03:57 * service_update * orcl * 0
listener: Wed Apr 17 09:03:57 2019
listener: 17-APR-2019 09:03:57 * service_update * orcl * 0
```
这个安装过程会很漫长，注意到日志里有 `100% complete` 打印，代表oracle安装成功

如果日志长时间没有更新，检查docker是否已经死掉
```bash
docker ps -a

Error response from daemon: An invalid argument was supplied.
```
如果出现如上提示，重新执行安装步骤
```bash
docker rm oracle11g
docker run --privileged --name oracle11g -p 1521:1521 -v oracleinstall:/install jaspeen/oracle-11g
```
执行完安装操作，再查看运行状态，发现oracle已经启动好
```bash
docker ps -a

CONTAINER ID        IMAGE                COMMAND                  CREATED             STATUS                      PORTS                                                                             NAMES
7f53f07c93e5        jaspeen/oracle-11g   "/assets/entrypoint.…"   About an hour ago   Up About an hour            0.0.0.0:1521->1521/tcp, 8080/tcp                                                  oracle11g
```

## 配置

默认scott用户是被锁定的，我们需要解锁，通过数据库工具即可成功连接到oracle

1，连接到容器，
```bash
docker exec -it oracle11g /bin/bash
```

2，切换到oracle用户，然后连接到sql控制台
```bash
[root@7f53f07c93e5 /]# su - oracle
Last login: Wed Apr 17 08:29:31 UTC 2019
[oracle@7f53f07c93e5 ~]$ sqlplus / as sysdba

SQL*Plus: Release 11.2.0.1.0 Production on Wed Apr 17 09:29:49 2019

Copyright (c) 1982, 2009, Oracle.  All rights reserved.


Connected to:
Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production
With the Partitioning, OLAP, Data Mining and Real Application Testing options

SQL>
```

3，解锁账户
```bash
SQL> alter user scott account unlock;
User altered.
SQL> commit;
Commit complete.
SQL> conn scott/tiger
ERROR:
ORA-28001: the password has expired
Changing password for scott
New password:
Retype new password:
Password changed
Connected.
SQL> 
```

4，使用dataGrip连接oracle数据库
数据库安装完成后，使用默认的sid为orcl，端口为1521，scott/tiger即可连接
![datagrid连接oracle]({{ site.baseurl }}/assets/images/post/linux/docker/datagrip-oracle.png)







> Reference:
> - https://zh.wikipedia.org/wiki/Oracle%E6%95%B0%E6%8D%AE%E5%BA%93
> - https://hub.docker.com/r/jaspeen/oracle-11g
> - https://stackoverflow.com/questions/37468788/what-is-the-right-way-to-add-data-to-an-existing-named-volume-in-docker
> - https://hub.docker.com/_/busybox
> - http://blog.grayidea.cn/archives/67
> - https://blog.csdn.net/u013238950/article/details/50999401
