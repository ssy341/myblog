---
categories: storage
tags: [oss,阿里云,策略(Policy)]
title: 如何安全的使用OSS-访问控制之策略（Policy）管理
toc: true
toc_label: "目录"
---

安全使用oss

配置策略，限制操作

生成公网访问的url

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
[EXPONENTIAL_DECAY_RETRY]: https://help.aliyun.com/document_detail/27481.html?spm=a2c4g.11186623.2.5.RCmDd6