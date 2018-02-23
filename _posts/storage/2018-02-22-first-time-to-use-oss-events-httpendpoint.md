---
categories: storage
tags: [oss,阿里云]
title: 初探oss-事件通知（http endpoint）
toc: true
toc_label: "目录"
---

## 初识oss

it技术更新换代太快了，我们的思想也必须跟上才行。

最近项目里需要用到文件上传，我想想挺简单的啊，关于spring mvc文件上传网上的示例太多了啊，抄一抄，三下五除二就弄好了。

好景不长，文件上传问题太多了，虽然从头到尾只出了两个问题，但是用户体验太差了，该怎么办呢？又要支持大文件，又要支持批量，
又在web端，感觉再好的插件也拯救不了我了。于是问问公司的前辈，才刚说上几句，前辈就说怎么不用oss？

啥？oss是什么？oss（Object Storage Service）即对象存储服务。

> 海量、安全、低成本、高可靠的云存储服务，提供99.999999999%的数据可靠性。
> 使用RESTful API 可以在互联网任何位置存储和访问，容量和处理能力弹性扩展，多种存储类型供选择全面优化存储成本。

了解到这里，我感觉这个就是能拯救我的方案了，立马查阅文档开始试试。

## 确定使用场景

根据之前的需求，用户通过网站上传图片到服务器上，然后对图片进行识别处理。

改用oss方案后，用户通过使用[oss控制台客户端][oss-client]上传文件,项目监听到文件上传完成，再获取资源进行识别处理。

使用oss之后逻辑稍有改变，由于文件存放不在同一个地方，这里需要使用oss提供的[事件通知][oss-events]来完成。

oss事件通知整体架构图如下（借用图一张）
![oss事件通知整体架构]({{ site.baseurl }}/assets/images/post/oss-first/first-meet-oss1.png)

由图中可以看到，oss事件通知提供了两种方式：

- HttpServer http的方式，即配置一个自己项目的访问地址（公网），当匹配规则匹配到时，往该地址推送数据
- MNS Queue 队列的方式，即在项目中订阅oss提供的主题，获取匹配规则推送的数据

这里要介绍的是Http方式，后面如果需要再使用队列方式试试。

## 小试牛刀

关于oss事件通知的配置，阿里云已经提供了一篇文档 [如何使用OSS事件通知功能？][how-to-use-oss-event]，
虽然讲的很清楚了（事后才觉得清楚，在没会之前是蒙圈的），但我觉得有必要把一些要点再提一下，少踩坑！

### 第一步：配置事件通知

简单理解，就是当bucket有文件变化（上传，覆盖，删除，追加……）时给予通知

帮助文档提供的截图和截止文章当前时间已经有所不同，下面带大家一步一步配置

首先进入[oss管理控制台][oss-console]如下图：
![oss-console]({{ site.baseurl }}/assets/images/post/oss-first/oss-console.png)

选中bucket，右侧页面进入到该bucket的相关信息，然后点击【事件通知】，由于我已经创建了一条规则，所以这里
能看到有一条规则
![create-rule]({{ site.baseurl }}/assets/images/post/oss-first/create-rule.png)

点击【创建规则】,在右侧会弹出界面，如下图所示，需要填写规则名称，事件类型，资源描述，接受终端
![creating-rule]({{ site.baseurl }}/assets/images/post/oss-first/creating-rule.png)

- 规则名称
    - 规则的唯一标识，同一账号同一 Region 同一产品下规则名称不能重名。字符必须是英文，数字，横划线，长度不超过 128 个
- 事件类型
    - 选择您想要触发通知的事件，可以选择多个。同样的事件不可以多次配置在同一资源上
    - PutObject ，创建/覆盖文件：简单上传
    - 这里我只举一个例子，更多其他的事件类型参考[事件类型列表][oss-event-sjtz]
- 资源描述
    - 资源描述可以是全名、前缀、后缀以及前后缀，不同资源描述不能有交集。
    - 在本次实例中，我配置了全名，前后缀
        - 第一行代表固定文件名（movie.zip）上传，就会触发事件
        - 第二行代表以m开头的文件上传，就会触发事件
        - 第三行代表.jpg格式的文件上传，就会触发事件
    - 在我第一次使用时这个地方没有理解资源描述是什么意思，我上传文件怎么都不触发，直到发送工单，才得知是这里的问题    
- 接受终端
    - 有两种可以选择，一个是http，一个是队列，本篇讲的是http方式，这里我选择http
    - http://domain.com:8080/oss/listener 地址是公网能够访问到的
    
填写完毕后，点击底部的【确认】按钮确认添加该规则，确认之后就会像之前看到的，出现一条规则。

这里需要注意的是，每添加一条规则会自动创建一条对应的主题，可以在[消息服务控制台][oss-mns-topic]查看到该主题，如下图：
![oss-topic]({{ site.baseurl }}/assets/images/post/oss-first/oss-topic.png)

**还需要注意的是图中指出的[温馨提示][oss-mns-topic-notice],主题实例是会产生费用的**

到这里，在阿里云控制台配置的工作就已经完成了，接下来要做的就是实现接受消息

### 第二步：接受消息通知

在上一步接受终端操作中我配置了一个可以公网访问的地址`http://domain.com:8080/oss/listener`，他的作用就是当规则匹配之后
oss会向该地址发送消息。根据oss技术人员提供的信息，我们可以从 [MNS Java SDK][mns-java-sdk] 
这个页面下载[示例代码][mns-sample-code]

解压下载后的文件，我们需要关注的是 `HttpEndpoint.java` 这个文件，这里对该文件代码不做详细的解释，根据自己项目的环境，做相应改动即可

我的项目环境是spring mvc，下面贴出我已经调试好的代码：

```java
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.InputStreamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.*;

/**
 * oss文件上传成功
 *
 * @return
 */
@PostMapping(value = "/oss/listener")
@ResponseBody
public String ossfileuploadsuccess(HttpServletRequest request) {
    log.info("oss客户端上传文件");
    try {
        HttpEntity entity = new InputStreamEntity(request.getInputStream(),
                request.getContentLength());

        String method = request.getMethod().toUpperCase(Locale.ENGLISH);

        String target = request.getRequestURI();


        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> hm = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
            hm.put(name, value);
        }

        //verify request
        String certHeader = request.getHeader("x-mns-signing-cert-url");
        if (certHeader == null) {
            log.info("SigningCerURL Header not found");
            return "SigningCerURL Header not found";
        }

        String cert = certHeader;
        if (cert.isEmpty()) {
            log.info("SigningCertURL empty");
            return "SigningCertURL empty";
        }
        cert = new String(Base64.decodeBase64(cert));
        log.info("SigningCertURL:\t" + cert);


        if (!authenticate(method, target, hm, cert)) {
            log.info("authenticate fail");
            return "authenticate fail";
        }

        //parser content of simplified notification
        InputStream is = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String content = buffer.toString();
        String result = null;
        byte[] messageBodyAsBytes = content.getBytes();
        if (messageBodyAsBytes != null) {
            try {
                result = new String(Base64.decodeBase64(messageBodyAsBytes), "UTF-8");
            } catch (UnsupportedEncodingException var4) {
                throw new RuntimeException("Not support encoding: UTF-8");
            }
        }
        //这里的result就是文件的信息
        log.info("Simplified Notification: \n {}", result);

        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray events = jsonObject.getJSONArray("events");

        if(Objects.nonNull(events) && CollectionUtils.isNotEmpty(events)){
            JSONObject eventEntity = (JSONObject) events.get(0);
            JSONObject userIdentity = eventEntity.getJSONObject("userIdentity");
            //用户uuid  根据uuid查询本系统对应的用户信息
            String principalId =userIdentity.getString("principalId");
        }

    } catch (Exception e) {
        log.error("错误", e);
    }
    return ajaxJson;
}

/**
 * check if this request comes from MNS Server
 *
 * @param method,  http method
 * @param uri,     http uri
 * @param headers, http headers
 * @param cert,    cert url
 * @return true if verify pass
 */
private Boolean authenticate(String method, String uri, Map<String, String> headers, String cert) {
    String str2sign = getSignStr(method, uri, headers);
    //System.out.println(str2sign);
    //这里需要注意大小写，官方给出的代码这里是大写A，在我实际操作中为小写，到底是什么，需要结合实际情况
    String signature = headers.get("authorization");
    byte[] decodedSign = Base64.decodeBase64(signature);
    //get cert, and verify this request with this cert
    try {
        //String cert = "http://mnstest.oss-cn-hangzhou.aliyuncs.com/x509_public_certificate.pem";
        URL url = new URL(cert);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        DataInputStream in = new DataInputStream(conn.getInputStream());
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        Certificate c = cf.generateCertificate(in);
        PublicKey pk = c.getPublicKey();

        java.security.Signature signetcheck = java.security.Signature.getInstance("SHA1withRSA");
        signetcheck.initVerify(pk);
        signetcheck.update(str2sign.getBytes());
        Boolean res = signetcheck.verify(decodedSign);
        return res;
    } catch (Exception e) {
        e.printStackTrace();
        log.warn("authenticate fail, " + e.getMessage());
        return false;
    }
}

/**
 * build string for sign
 *
 * @param method,  http method
 * @param uri,     http uri
 * @param headers, http headers
 * @return String fro sign
 */
private String getSignStr(String method, String uri, Map<String, String> headers) {
    StringBuilder sb = new StringBuilder();
    sb.append(method);
    sb.append("\n");
    //注意大小写
    sb.append(safeGetHeader(headers, "content-md5"));
    sb.append("\n");
    //注意大小写
    sb.append(safeGetHeader(headers, "content-type"));
    sb.append("\n");
    //注意大小写
    sb.append(safeGetHeader(headers, "date"));
    sb.append("\n");

    List<String> tmp = new ArrayList<String>();
    for (Map.Entry<String, String> entry : headers.entrySet()) {
        if (entry.getKey().startsWith("x-mns-")){
            tmp.add(entry.getKey() + ":" + entry.getValue());
        }
    }
    Collections.sort(tmp);

    for (String kv : tmp) {
        sb.append(kv);
        sb.append("\n");
    }

    sb.append(uri);
    return sb.toString();
}

private String safeGetHeader(Map<String, String> headers, String name) {
        if (headers.containsKey(name)) {
            return headers.get(name);
        } else {
            return "";
        }
    }
```

代码虽然很多，但是不复杂，其实就是对request进行解析，进行验证，把oss包含信息取出来，最终获取到base64编码的文件信息。

### 第三步：检验成果

把项目启动，然后使用[oss控制台客户端][oss-client1]上传文件
![oss-upload-success]({{ site.baseurl }}/assets/images/post/oss-first/oss-upload-success.png)

当文件上传完毕后，在java控制台打印出如下数据：

```json
{
  "events": [
    {
      "eventName": "ObjectCreated:PutObject",
      "eventSource": "acs:oss",
      "eventTime": "2018-02-22T08:32:21.000Z",
      "eventVersion": "1.0",
      "oss": {
        "bucket": {
          "arn": "acs:oss:cn-beijing:1234346345345345:display-sjf-event-notification-test",
          "name": "display-sjf-event-notification-test",
          "ownerIdentity": "1234346345345345",
          "virtualBucket": ""
        },
        "object": {
          "deltaSize": 312148,
          "eTag": "D9703079D307A57C4967B30AC36FCA81",
          "key": "20170731095417740.png",
          "size": 312148
        },
        "ossSchemaVersion": "1.0",
        "ruleId": "oss-upload-success-img-http"
      },
      "region": "ch-china",
      "requestParameters": {
        "sourceIPAddress": "10.0.0.0"
      },
      "responseElements": {
        "requestId": "5A815115776D389D9FE118C1"
      },
      "userIdentity": {
        "principalId": "12345678934534534"
      }
    }
  ]
}

//参数解释
{
  "events": [
    {
      "eventName": "", //事件通知类型
      "eventSource": "", //消息源，固定为"acs:oss"
      "eventTime": "", //事件事件，格式为ISO-8601
      "eventVersion": "", //版本号，目前为"1.0"
      "oss": {
        "bucket": {
          "arn": "", //bucket的唯一标识符，格式为"acs:oss:region:uid:bucket"
          "name": "", //bucket名称
          "ownerIdentity": "" //bucket的owner
        },
        "object": {
          "deltaSize":, //object大小的变化量，比如新增一个文件，这个值就是文件大小，如果是覆盖一个文件，这个值就是新文件与旧文件的差值，因此可能为负数
          "eTag": "", //object的etag，与GetObject()请求返回的ETag头的内容相同
          "key": "", //object名称
          "position":, //可变项，只有在ObjectCreated:AppendObject事件中才有，表示此次请求开始append的位置，注意是从0开始
          "readFrom":, //可变项，只有在ObjectDownloaded:GetObject事件中才有，表示文件开始读取的位置，如果不是Range请求，则此项为0，否则则是Range请求的开始字节，注意是从0开始
          "readTo":,//可变项，只有在ObjectDownloaded:GetObject事件中才有，表示文件最后读取的位置，如果不是Range请求，则此项为文件的大小，否则则是Range请求的结束字节增1
          "size"://object大小
        }
        "ossSchemaVersion": "", //此字段域的版本号，目前为"1.0"
        "ruleId": "GetObject" //此事件匹配的规则ID
      },
      "region": "", //bucket所在的region
      "requestParameters": {
        "sourceIPAddress": "" //请求的源IP
      },
      "responseElements": {
        "requestId": ""  //请求对应的requestid
      },
      "userIdentity": {
        "principalId": ""  //请求发起者的uid
      },
      "xVars": {
        //oss的callback功能中的自定义参数
        "x:callback-var1": "value1",
        "x:vallback-var2": "value2"
      }
    }
  ]
}
```

## 结语

在信息化时代，我们需要跟上时代的步伐，与时俱进。本篇关于oss事件通知的介绍到此结束，希望对家有所帮助。




[oss-client]: https://market.aliyun.com/products/53690006/cmgj000281.html
[oss-client1]: https://help.aliyun.com/document_detail/61872.html
[oss-events]: https://help.aliyun.com/document_detail/52656.html
[how-to-use-oss-event]: https://yq.aliyun.com/articles/71881
[oss-console]: https://oss.console.aliyun.com/overview
[oss-event-sjtz]: https://help.aliyun.com/document_detail/52656.html#sjtz
[oss-mns-topic]: https://mns.console.aliyun.com/#/topics
[oss-mns-topic-notice]: https://www.aliyun.com/price/product#/mns/detail
[mns-java-sdk]: https://help.aliyun.com/document_detail/27508.html
[mns-sample-code]: http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/27508/cn_zh/1491978276754/aliyun-sdk-mns-samples-1.1.8.zip?spm=a2c4g.11186623.2.5.KVAOlJ&file=aliyun-sdk-mns-samples-1.1.8.zip 