---
categories: database
tags: [mysql,database]
title: mysql以utf-8字符集创建数据库
date: 2016-09-22
toc: false
---

CREATE DATABASE 的语法：

```bash
CREATE {DATABASE | SCHEMA} [IF NOT EXISTS] db_name
[create_specification [, create_specification] ...]
create_specification:
[DEFAULT] CHARACTER SET charset_name
| [DEFAULT] COLLATE collation_name
```


创建utf-8字符集数据库

```bash
CREATE DATABASE db_name DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

对已有的数据库更改字符编码

```bash
ALTER DATABASE db_name DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci
```



