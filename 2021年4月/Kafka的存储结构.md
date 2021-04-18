# kafka是怎么存储消息的呢？

我们知道，kafka中的一个topic在物理层面被分为多个partition，其实存储的时候partition也是最粗粒度的。

1. partition的表现形式就是一个一个文件夹
2. 每个文件夹下面会有很多组segment文件
3. 每组segment文件又分为3个文件：
    * .index文件：消息偏移量索引文件
    * .log文件：数据文件
    * .timeindex文件（0.8版本之后才有）：时间索引文件

如下图所示：
```java
/*
 *                            +--> 000000000.index      ...
 *                            |
 *          +--> partition0 --+--> 000000000.log        ...
 *          |                 |
 *  topic --+--> partition1   +--> 000000000.timeindex  ...
 *          |
 *          +--> partition2
 *          
 *          
 */
```

message的格式：固定长度header + 变长的body    
header组成：
    * 8byte的消息偏移量，标识消息在partition的位置
    * 4byte的消息大小
    * 4byte的CRC32循环冗余校验码
    * 1byte的kafka服务程序协议版本号
    * 1byte标识类型：独立版本类型？压缩类型？编码类型？
    * 4byte的keyLength
    * 4byte的payloadLength消费消息长度

# kafka怎么查找消息？
## kafka查找消息步骤
第一步：根据offset二分查找segment
第二步：找到segment的.index文件，再二分查找稀疏索引对应的position
第三步：打开.log数据文件，从position开始顺序查找offset对应的消息

## kafka查找消息例子
假如现在需要查找一个offset为368801的message是什么样的过程呢？我们先看看下面的图：
![kafka查找消息](./pic/kafka_message_search_flow.png)

1、先找到offset的368801message所在的segment文件（利用二分法查找），这里找到的就是在第二个segment文件。
2、打开找到的segment中的.index文件（也就是368796.index文件，该文件起始偏移量为368796+1，我们要查找的offset为368801的message在该index内的偏移量为368796+5=368801，所以这里要查找的相对offset为5）。由于该文件采用的是稀疏索引的方式存储着相对offset及对应message物理偏移量的关系，所以直接找相对offset为5的索引找不到，这里同样利用二分法查找相对offset小于或者等于指定的相对offset的索引条目中最大的那个相对offset，所以找到的是相对offset为4的这个索引。
3、根据找到的相对offset为4的索引确定message存储的物理偏移位置为256。打开数据文件，从位置为256的那个地方开始顺序扫描直到找到offset为368801的那条Message。

## kafka查找消息总结
这套机制是建立在offset为有序的基础上，利用**segment+有序offset+稀疏索引+二分查找+顺序查找**等多种手段来高效的查找数据！

> 参考：https://www.cnblogs.com/sujing/p/10960832.html

# 什么是密集索引？什么是稀疏索引？
简单来说密集索引就是为每个关键字创建一条索引项；
而稀疏索引只会为部分关键字创建索引，通过索引定位到关键字后再顺序搜索，直到找到数据；

> 参考：https://zhuanlan.zhihu.com/p/261130303