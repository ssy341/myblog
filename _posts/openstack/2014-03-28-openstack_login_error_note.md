---
title: 登陆openstack提示You are not authorized for any projects.
date: 2014-3-28
categories: openstack
tags: [keystone]
---

一路配置过来，安装keystone glance nova 最后能打开主页面，我输入登录名和密码提示我没有授权任何project
下面是我执行的 `keystone_data.sh`

```bash
#!/bin/sh
#
# Keystone Datas
#
# Description: Fill Keystone with datas.
# Mainly inspired by http://www.hastexo.com/resources/docs/installing-openstack-essex-20121-ubuntu-1204-precise-pangolin
# Written by Martin Gerhard Loschwitz / Hastexo
# Modified by Emilien Macchi / StackOps
#
# Support: openstack@lists.launchpad.net
# License: Apache Software License (ASL) 2.0
#
#ADMIN_PASSWORD=${ADMIN_PASSWORD:-password}
ADMIN_PASSWORD=${ADMIN_PASSWORD:-admin}
SERVICE_PASSWORD=${SERVICE_PASSWORD:-$ADMIN_PASSWORD}
export SERVICE_TOKEN="1234567890"
export SERVICE_ENDPOINT="http://localhost:35357/v2.0"
SERVICE_TENANT_NAME=${SERVICE_TENANT_NAME:-service}
get_id () {
    echo `$@ | awk '/ id / { print $4 }'`
}
# Tenants
ADMIN_TENANT=$(get_id keystone tenant-create --name=admin)
SERVICE_TENANT=$(get_id keystone tenant-create --name=$SERVICE_TENANT_NAME)
DEMO_TENANT=$(get_id keystone tenant-create --name=demo)
INVIS_TENANT=$(get_id keystone tenant-create --name=invisible_to_admin)
# Users
ADMIN_USER=$(get_id keystone user-create --name=admin --pass="$ADMIN_PASSWORD" --email=admin@domain.com)
DEMO_USER=$(get_id keystone user-create --name=demo --pass="$ADMIN_PASSWORD" --email=demo@domain.com)
# Roles
ADMIN_ROLE=$(get_id keystone role-create --name=admin)
KEYSTONEADMIN_ROLE=$(get_id keystone role-create --name=KeystoneAdmin)
KEYSTONESERVICE_ROLE=$(get_id keystone role-create --name=KeystoneServiceAdmin)
# Add Roles to Users in Tenants
keystone user-role-add --user-id $ADMIN_USER --role-id $ADMIN_ROLE --tenant-id $ADMIN_TENANT
keystone user-role-add --user-id $ADMIN_USER --role-id $ADMIN_ROLE --tenant-id $DEMO_TENANT
keystone user-role-add --user-id $ADMIN_USER --role-id $KEYSTONEADMIN_ROLE --tenant-id $ADMIN_TENANT
keystone user-role-add --user-id $ADMIN_USER --role-id $KEYSTONESERVICE_ROLE --tenant-id $ADMIN_TENANT
# The Member role is used by Horizon and Swift
MEMBER_ROLE=$(get_id keystone role-create --name=Member)
keystone user-role-add --user-id $DEMO_USER --role-id $MEMBER_ROLE --tenant-id $DEMO_TENANT
keystone user-role-add --user-id $DEMO_USER --role-id $MEMBER_ROLE --tenant-id $INVIS_TENANT
# Configure service users/roles
NOVA_USER=$(get_id keystone user-create --name=nova --pass="$SERVICE_PASSWORD" --tenant-id $SERVICE_TENANT --email=nova@domain.com)
keystone user-role-add --tenant-id $SERVICE_TENANT --user-id $NOVA_USER --role-id $ADMIN_ROLE
GLANCE_USER=$(get_id keystone user-create --name=glance --pass="$SERVICE_PASSWORD" --tenant-id $SERVICE_TENANT --email=glance@domain.com)
keystone user-role-add --tenant-id $SERVICE_TENANT --user-id $GLANCE_USER --role-id $ADMIN_ROLE
SWIFT_USER=$(get_id keystone user-create --name=swift --pass="$SERVICE_PASSWORD" --tenant-id $SERVICE_TENANT --email=swift@domain.com)
keystone user-role-add --tenant-id $SERVICE_TENANT --user-id $SWIFT_USER --role-id $ADMIN_ROLE
RESELLER_ROLE=$(get_id keystone role-create --name=ResellerAdmin)
keystone user-role-add --tenant-id $SERVICE_TENANT --user-id $NOVA_USER --role-id $RESELLER_ROLE
QUANTUM_USER=$(get_id keystone user-create --name=quantum --pass="$SERVICE_PASSWORD" --tenant-id $SERVICE_TENANT --email=quantum@domain.com)
keystone user-role-add --tenant-id $SERVICE_TENANT --user-id $QUANTUM_USER --role-id $ADMIN_ROLE
CINDER_USER=$(get_id keystone user-create --name=cinder --pass="$SERVICE_PASSWORD" --tenant-id $SERVICE_TENANT --email=cinder@domain.com)
keystone user-role-add --tenant-id $SERVICE_TENANT --user-id $CINDER_USER --role-id $ADMIN_ROLE
```


执行完上面的，用命令查看 `keystone user-list   keystone role-list  keystone tenant-list` 均有值 ，这是为什么呢？   

根据错误提示“没有被授权任何项目”，我联想到是不是user和role没有进行关联呢？

打开keystone实例，总共10张表，分别为：
- ec2_credential
- ENDPOINT
- metadata
- migrate_version
- role
- service
- tenant
- token
- user
- user_tenant_membership

我一个表一个表查看，发现`user_tenant_membership`这张表有两个字段`user_id`和`tenant_id`，但是没有值，我查看表结构发现时外键关联的，
我尝试把`user`和`tenant`进行关联，重新登录，竟然ok了

分析：初次接触openstack，太多东西不明白，对照书上操作，新的技术更新比较快，书上的内容就不同步了，以上的`keystone datas` 应该是少添加了`user`的权限或添加错误，
或许还有其他问题，不过暂时还没有发现，再以后的操作过程中再记录下来