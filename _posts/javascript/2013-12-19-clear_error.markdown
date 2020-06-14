---
layout: single
title: 方法命名务必避开关键字
categories: javascript
tags: [Document.clear]
date: 2013-12-19
---

我在js里定义了如下方法
```javascript
function clear(){
    alert(123);
}
```

<!--more-->
html里如下调用

```html
<button onclick="clear()">清除</button>
```

但是死活不打印123，查看控制台后有如下提示： 


document.clear() is deprecated. This method doesn't do anything.
```

查阅资料后，得知clear本为浏览器的内置方法，现在已经废弃了，但是少部分浏览器还支持，如果使用这个，可能会导致程序异常
 更改方法名后，程序正常

参考: [Document.clear][clear]

[clear]: https://developer.mozilla.org/en-US/docs/Web/API/document.clear