---
categories: database
tags: [mysql,database,error,exception]
title: Unknown column 'NaN' in 'field list'
toc: false
---

### 起因

使用mysql数据库，在插入数据时，抛出以下异常

```bash
Unknown column 'NaN' in 'field list'
```

字面上意思：未知的列'NaN'在字段列表中

我首先想到的是自己 insert 语句是不是有问题，但想到插入语句是由框架完成的，列因该不会弄错，那是什么原因呢？

再看关键字`NaN`，这个是`Not a Number`的意思，我联想到我的插入数据包含浮点类型的数据，调试代码发现有除数为0的情况

```java
//misV+rigVf 可能为 0
float recallRate = rigVf / (misV + rigVf);
perCategory.setReCallRate(recallRate);
```

当被除数为0，`recallRate`的结果就是`NaN`，导致mysql插入数据的时候，本来因该是一个浮点类型，结果插入了`NaN`，mysql报错。

找到原因了问题就好解决，在设置`recallRate`值的时候判断一下
```java
if(!Float.isNaN(recallRate)){
    perCategory.setReCallRate(recallRate);
}
```
加上判断之后，问题解决

### 总结

编码需要规范，不然就会出现这些低级错误



> Reference:
> - https://www.cnblogs.com/big-xuyue/p/4106130.html


