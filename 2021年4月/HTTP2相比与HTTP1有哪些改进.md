* HTTP/1.1 存在性能问题
* 兼容 1.1
* 头部压缩：静态编码、动态编码、Haffman编码
* 二进制帧：头部帧/数据帧（HPACK压缩算法）
* 实现并发传输：多个Stream复用一条TCP连接，达到并发效果。（不同Stream的帧可以乱序发送，同一Stream内部的帧必须严格一致）
* 服务器主动推送

> https://www.zhihu.com/question/34074946
> https://cloud.tencent.com/developer/article/1004874