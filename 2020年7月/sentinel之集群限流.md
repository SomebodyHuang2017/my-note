# Sentinel 集群限流

## 集群限流原理
一个请求过来，需要判断是否能够通过。怎么判断是否能通过呢？我们可以抽象出令牌（token）的概念，请求过来如果能拿到令牌，则可以通过，否则就拒绝。

集群流控中共有两种身份：

1. Token Client：集群流控客户端，用于向所属 Token Server 通信请求 token。集群限流服务端会返回给客户端结果，决定是否限流。
2. Token Server：即集群流控服务端，处理来自 Token Client 的请求，根据配置的集群规则判断是否应该发放 token（是否允许通过）

## 为什么需要集群限流？
为什么要使用集群流控呢？假设我们希望给某个用户限制调用某个 API 的总 QPS 为 50，但机器数可能很多（比如有 100 台）。这时候我们很自然地就想到，找一个 server 来专门来统计总的调用量，其它的实例都与这台 server 通信来判断是否可以调用。这就是最基础的集群流控的方式。

另外集群流控还可以解决流量不均匀导致总体限流效果不佳的问题。假设集群中有 10 台机器，我们给每台机器设置单机限流阈值为 10 QPS，理想情况下整个集群的限流阈值就为 100 QPS。不过实际情况下流量到每台机器可能会不均匀，会导致总量没有到的情况下某些机器就开始限流。因此仅靠单机维度去限制的话会无法精确地限制总体流量。而集群流控可以精确地控制整个集群的调用总量，结合单机限流兜底，可以更好地发挥流量控制的效果。

## 模块介绍
Sentinel 1.4.0 开始引入了集群流控模块，主要包含以下几部分：

* `sentinel-cluster-common-default`: 公共模块，包含公共接口和实体
* `sentinel-cluster-client-default`: 默认集群流控 client 模块，使用 Netty 进行通信，提供接口方便序列化协议扩展
* `sentinel-cluster-server-default`: 默认集群流控 server 模块，使用 Netty 进行通信，提供接口方便序列化协议扩展；同时提供扩展接口对接规则判断的具体实现（TokenService），默认实现是复用 sentinel-core 的相关逻辑

## 模块之间的通信
集群限流必然要涉及到 token-client 和 token-server 的通信问题，下面我们来看看他们是如何通信的。

1. InitFunc 加载 Netty 通信所需的编解码器
2. SPI 动态加载并创建 ClusterTokenClient
3. 配置变更后 ClusterStateManager 启动或停止 ClusterTokenClient
4. FlowRuleChecker 从 TokenClientProvider 获取到 ClusterTokenClient 后调用 requestToken 获取 token





