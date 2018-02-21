---
categories: openstack
tags: [keystone]
title: 执行keystone-manage db_sync错误
date: 2014-3-27
---


最近学习openstack，事情总是不会想象的那么好，挫折总会有,我的系统是ubuntu12.0.4,
执行`keystone-manage db_sync`报如下错误：
  
```bash
Traceback (most recent call last):
  File "/usr/bin/keystone-manage", line 28, in <module>
    cli.main(argv=sys.argv, config_files=config_files)
  File "/usr/lib/python2.7/dist-packages/keystone/cli.py", line 148, in main
    return run(cmd, (args[:1] + args[2:]))
  File "/usr/lib/python2.7/dist-packages/keystone/cli.py", line 134, in run
    return CMDS[cmd](argv=args).run()
  File "/usr/lib/python2.7/dist-packages/keystone/cli.py", line 36, in run
    return self.main()
  File "/usr/lib/python2.7/dist-packages/keystone/cli.py", line 55, in main
    driver = utils.import_object(getattr(CONF, k).driver)
  File "/usr/lib/python2.7/dist-packages/keystone/common/utils.py", line 60, in import_object
    __import__(import_str)
TypeError: __import__() argument 1 must be string, not None
```

解决办法：在执行命令前加上sudo，获取到管理员权限即可，这是因为/etc/keystone/文件夹有权限，普通用户无法直接访问，
而执行keystone-manage db_sync命令需要使用到keystone的配置文件：/etc/keystone/keystone.conf 所以导致了此错误

参考网址:
[tools/openstack_ubuntu install fails on Ubuntu 12.04](https://bugs.launchpad.net/heat/+bug/1175817)


