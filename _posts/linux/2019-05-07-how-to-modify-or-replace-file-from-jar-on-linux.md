---
categories: linux
tags: [jar,linux,vi,zip,unzip]
title: 在linux下从jar中替换、修改文件
toc: true
---

## 修改文件内容

`vi`命令在linux下再熟悉不过了，搭配`unzip`和`zip`还可以修改压缩包里的文件。

> ps: 如果本机还没有安装`zip,unzip`，先执行安装命令
> - sudo apt-get install unzip
> - sudo apt-get install zip

假设现在有如下结构的目录:

```bash
HelloWorld/
└── src
    └── main
        ├── META-INF
        │   └── MANIFEST.MF
        ├── java
        │   └── com
        │       └── thxopen
        │           └── demo
        │               └── Main.java
        └── resources
            └── demo.txt
```

Main.java 文件内容如下：

```java
package com.thxopen.demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Keith Shan
 * @date 2019年5月7日
 * @site http://www.thxopen.com
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        InputStream uri = Main.class.getClassLoader().getResourceAsStream("demo.txt");
        BufferedInputStream bf = null;
        try {
            bf = new BufferedInputStream(uri);
            byte[] b = new byte[bf.available()];
            bf.read(b);
            System.out.println(new String(b));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```
代码很简单，输出一个`Hello World!` 然后读取`resources/demo.txt`文件内容并输出。

我们把项目构建成jar上传到服务器，并在服务器上执行。下面是执行输出的结果
```bash
thxopen@PC201503302026:~/work$ java -jar HelloWorld.jar
Hello World!
I am Keith Shan.
```

现在需要修改demo.txt的内容，改变输出，不需要重新打包上传，使用`vi`命令即可

```bash
thxopen@PC201503302026:~/work$ vi HelloWorld.jar

" zip.vim version v28
" Browsing zipfile /home/thxopen/work/HelloWorld.jar
" Select a file with cursor and press ENTER

META-INF/MANIFEST.MF
com/
com/thxopen/
com/thxopen/demo/
com/thxopen/demo/Main.class
demo.txt
META-INF/
META-INF/HelloWorld.kotlin_module
                               
```
然后会列出jar里面的内容，把光标移动到需要编辑的文件，回车即可编辑。

> ps: 如果文件比较多，可以使用`/filename` 来查找定位

进入文件编辑状态后，操作就和`vi`命令一样，这里我新增`I am the new String.` 内容，`:wq!`保存退出vi，然后`:exit`退出jar编辑状态。
重新执行

```bash
thxopen@PC201503302026:~/work$ java -jar HelloWorld.jar
Hello World!
I am Keith Shan.
I am the new String.
```
从输出可以看到`demo.txt`内容被成功修改

## 替换文件

修改文件内容对于.class文件来说似乎就不行了，那只能直接替换文件，如果替换文件呢？

我修改Main.java文件，修改后内容如下：
```java
package com.thxopen.demo;
/**
 * @author Keith Shan
 * @date 2019年5月7日
 * @site http://www.thxopen.com
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Class file modified");
    }
}
```
我把编译好的class文件放到 `/com/thxopen/demo`目录下，如下所示

```bash
thxopen@PC201503302026:~/work$ tree
.
├── HelloWorld.jar
└── com
    └── thxopen
        └── demo
            └── Main.class (重新编译好的文件)

3 directories, 2 files
```
执行如下命令把文件替换到jar中
```bash
thxopen@PC201503302026:~/work$ jar -uvf HelloWorld.jar com/thxopen/demo/Main.class
adding: com/thxopen/demo/Main.class(in = 557) (out= 351)(deflated 36%)
```

重新执行，class文件已经被替换
```bash
thxopen@PC201503302026:~/work$ java -jar HelloWorld.jar
Class file modified
```


附：jar命令的基本用法
```bash
thxopen@PC201503302026:~/work$ jar
Usage: jar {ctxui}[vfmn0PMe] [jar-file] [manifest-file] [entry-point] [-C dir] files ...
Options:
    -c  create new archive
    -t  list table of contents for archive
    -x  extract named (or all) files from archive
    -u  update existing archive 更新存在的压缩包
    -v  generate verbose output on standard output 生成日志到标准输出
    -f  specify archive file name 指定压缩包名称
    -m  include manifest information from specified manifest file
    -n  perform Pack200 normalization after creating a new archive
    -e  specify application entry point for stand-alone application
        bundled into an executable jar file
    -0  store only; use no ZIP compression
    -P  preserve leading '/' (absolute path) and ".." (parent directory) components from file names
    -M  do not create a manifest file for the entries
    -i  generate index information for the specified jar files
    -C  change to the specified directory and include the following file
If any file is a directory then it is processed recursively.
The manifest file name, the archive file name and the entry point name are
specified in the same order as the 'm', 'f' and 'e' flags.

Example 1: to archive two class files into an archive called classes.jar:
       jar cvf classes.jar Foo.class Bar.class
Example 2: use an existing manifest file 'mymanifest' and archive all the
           files in the foo/ directory into 'classes.jar':
       jar cvfm classes.jar mymanifest -C foo/ .
```


