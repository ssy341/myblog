---
layout: single
title: ajaxFileupLoad多文件上传
date: 2014-3-31
categories: javascript
tags: [ajaxFileupload]
---
 
打开google 搜索 ‘ajaxFileupload’ ‘多文件上传’ 可以搜到许许多多类似的，那我为什么还要写一下呢？

- 一个是对之前大神的贡献表示感谢；
- 二个是自己知识的总结；
- 三个是自己在原有的基础上改动了下，在此记录，可能帮助其他朋友。

用过这个插件的都知道这个插件的基本用法，我就不废话，直接上代码。
<!--more-->
我需要实现多个文件上传，之前的做法是定义多个不同id的input，然后把ajaxfileuplod方法放在for循环里，这个方法是在网上看到的，我觉得不怎么好，
后面在网上找到的，就高级点了，直接改源码（因为作者好久没有跟新了，也确实满足不了要求了）。接下来看看我是怎么改的。

引用网上的做法：

- 1，看没有修改前的代码

```javascript
var oldElement = jQuery('#' + fileElementId);  
var newElement = jQuery(oldElement).clone();  
jQuery(oldElement).attr('id', fileId);  
jQuery(oldElement).before(newElement);  
jQuery(oldElement).appendTo(form);
```


很容易看出，这个就是根据id把input加到from里去，那么要实现多个文件上传，就改成下面的样子：

```javascript
if(typeof(fileElementId) == 'string'){  
    fileElementId = [fileElementId];  
}  
for(var i in fileElementId){  
    var oldElement = jQuery('#' + fileElementId[i]);  
    var newElement = jQuery(oldElement).clone();  
    jQuery(oldElement).attr('id', fileId);  
    jQuery(oldElement).before(newElement);  
    jQuery(oldElement).appendTo(form);  
}
```

这样改之后，初始化的代码就要这么写：

```javascript
$.ajaxFileUpload({  
    url:'/ajax.php',  
    fileElementId:['id1','id2']//原先是fileElementId:’id’ 只能上传一个  
});
```

到这里，确实可以上传多个文件，但是对于我来说新问题又来，多个id，我的界面的文件不是固定的，是动态加载的，那么id要动态生成，我觉得太麻烦，
为什么不取name呢？然后把以上代码改为如下：

```javascript
if (typeof(fileElementId) == 'string') {
    fileElementId = [fileElementId];
}
for (var i in fileElementId) {
    //按name取值  
    var oldElement = jQuery("input[name=" + fileElementId[i] + "]");
    oldElement.each(function () {
        var newElement = jQuery($(this)).clone();
        jQuery(oldElement).attr('id', fileId);
        jQuery(oldElement).before(newElement);
        jQuery(oldElement).appendTo(form);
    });
}
```

这样改了 那么就可以实现多组多个文件上传，接下来看我是怎么应用的。
```html

<div>
    <img id="loading" src="scripts/ajaxFileUploader/loading.gif" style="display:none;">

    <table cellpadding="0" cellspacing="0" class="tableForm" id="calculation_model">
        <thead>
        <tr>
            <th>多组多个文件</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>第一组</td>
            <td>第二组</td>
        </tr>
        <tr>
            <td><input type="file" name="gridDoc" class="input"></td>
            <td><input type="file" name="caseDoc" class="input"></td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td>
                <button class="button" id="up1">Upload</button>
            </td>
            <td>
                <button class="button" id="addInput">添加一组</button>
            </td>
        </tr>
        </tfoot>
    </table>
</div>          
```

```javascript
/** 
 * Created with IntelliJ IDEA. 
 * User: Administrator 
 * Date: 13-7-3 
 * Time: 上午9:20 
 * To change this template use File | Settings | File Templates. 
 */  
$(document).ready(function () {  
    $("#up1").click(function(){  
        var temp = ["gridDoc","caseDoc"];  
        ajaxFileUpload(temp);  
    });  
  
    $("#addInput").click(function(){  
        addInputFile();  
    });  
  
});  
  
//动态添加一组文件  
function addInputFile(){  
    $("#calculation_model").append(" <tr>"+  
        "<td><input type='file'  name='gridDoc' class='input'></td>   "+  
        "<td><input type='file' name='caseDoc' class='input'></td> "+  
        "</tr>");  
}  
  
  
//直接使用下载下来的文件里的demo代码  
function ajaxFileUpload(id){  
    //starting setting some animation when the ajax starts and completes  
    $("#loading").ajaxStart(function(){  
            $(this).show();  
        }).ajaxComplete(function(){  
            $(this).hide();  
        });  
  
    /* 
     prepareing ajax file upload 
     url: the url of script file handling the uploaded files 
     fileElementId: the file type of input element id and it will be the index of  $_FILES Array() 
     dataType: it support json, xml 
     secureuri:use secure protocol 
     success: call back function when the ajax complete 
     error: callback function when the ajax failed 
 
     */  
    $.ajaxFileUpload({  
            url:'upload.action',  
            //secureuri:false,  
            fileElementId:id,  
            dataType: 'json'  
        }  
    )  
  
    return false;  
  
}
```

我action是用的struts2，它的上传是比较简单的，只要声明约定的名字，即可得到文件对象，和名称，代码如下：

```java

package com.ssy.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-2
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class Fileupload extends ActionSupport implements ServletContextAware {
    private File[] gridDoc, caseDoc;
    private String[] gridDocFileName, caseDocFileName;

    private ServletContext context;


    public String execute() {
        for (int i = 0; i < gridDocFileName.length; i++) {
            System.out.println(gridDocFileName[i]);
        }
        for (int i = 0; i < caseDocFileName.length; i++) {
            System.out.println(caseDocFileName[i]);
        }


        //System.out.println(doc1FileName);  
        //System.out.println(doc2FileName);  
        String targetDirectory = context.getRealPath("/uploadFile");

        /*
         *这里我只取得  第一组的文件进行上传，第二组的类似
         */
        try {
            for (int i = 0; i < gridDoc.length; i++) {
                String targetFileName = generateFileName(gridDocFileName[i]);
                File target = new File(targetDirectory, targetFileName);
                FileUtils.copyFile(gridDoc[i], target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public File[] getGridDoc() {
        return gridDoc;
    }

    public void setGridDoc(File[] gridDoc) {
        this.gridDoc = gridDoc;
    }

    public File[] getCaseDoc() {
        return caseDoc;
    }

    public void setCaseDoc(File[] caseDoc) {
        this.caseDoc = caseDoc;
    }

    public String[] getGridDocFileName() {
        return gridDocFileName;
    }

    public void setGridDocFileName(String[] gridDocFileName) {
        this.gridDocFileName = gridDocFileName;
    }

    public String[] getCaseDocFileName() {
        return caseDocFileName;
    }

    public void setCaseDocFileName(String[] caseDocFileName) {
        this.caseDocFileName = caseDocFileName;
    }

    /**
     * 用日期和随机数格式化文件名避免冲突
     *
     * @param fileName
     * @return
     */
    private String generateFileName(String fileName) {
        System.out.println(fileName);
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        String formatDate = sf.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

}

```

写到这里，我就有疑问了，之前的大神改的多文件，为什么还是取id，而且后台是怎么取的，我还是没怎么弄明白，我改的这个代码可行么？是不是存在bug呢？
这个还有待考验，如果看出问题，请指出，共同学习

修改后的js文件:[ajaxfileupload.zip]({{site.baseurl}}/assets/attachment/ajaxfileupload.zip)
