---
categories: database
tags: [mysql,database]
title: 在mysql中获取表格自增长列（AUTO_INCREMENT）当前的值
toc: false
---

使用下面的查询语句获得表格详细的信息

```bash
SHOW TABLE STATUS FROM `DatabaseName` WHERE `name` LIKE 'TableName' ;
```

使用下面的查询语句获得表格具体的信息

```bash
SELECT `AUTO_INCREMENT`
FROM  INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'DatabaseName'
AND   TABLE_NAME   = 'TableName';
```

修改自动增长列的值

```bash
ALTER TABLE tablename AUTO_INCREMENT = 1;
```


> Reference:
> - https://stackoverflow.com/questions/15821532/get-current-auto-increment-value-for-any-table


