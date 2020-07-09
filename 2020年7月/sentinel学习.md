## Sentinel 分布式系统的流量防卫兵
随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。

Sentinel 具有以下特征:

* 丰富的应用场景：Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀（即突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等。
* 完备的实时监控：Sentinel 同时提供实时的监控功能。您可以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况。
* 广泛的开源生态：Sentinel 提供开箱即用的与其它开源框架/库的整合模块，例如与 Spring Cloud、Dubbo、gRPC 的整合。您只需要引入相应的依赖并进行简单的配置即可快速地接入 Sentinel。
* 完善的 SPI 扩展点：Sentinel 提供简单易用、完善的 SPI 扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如定制规则管理、适配动态数据源等。

Sentinel特性：
![sentinel特性](./pic/sentinel特性.png)


## Sentinel 是什么？

随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel 是面向分布式服务架构的流量控制组件，主要以流量为切入点，从限流、流量整形、熔断降级、系统负载保护、热点防护等多个维度来帮助开发者保障微服务的稳定性。


## Sentinel 的基本概念？

### 资源
资源是 Sentinel 的关键概念。它可以是 Java 应用程序中的任何内容，例如，由应用程序提供的服务，或由应用程序调用的其它应用提供的服务，甚至可以是一段代码。在接下来的文档中，我们都会用资源来描述代码块。

只要通过 Sentinel API 定义的代码，就是资源，能够被 Sentinel 保护起来。大部分情况下，可以使用方法签名，URL，甚至服务名称作为资源名来标示资源。
### 规则
围绕资源的实时状态设定的规则，可以包括流量控制规则、熔断降级规则以及系统保护规则。所有规则可以动态实时调整。

## Sentinel 功能和理念设计
### 流量控制
#### 什么是流量控制
流量控制在网络传输中是一个常用的概念，它用于调整网络包的发送数据。然而，从系统稳定性角度考虑，在处理请求的速度上，也有非常多的讲究。任意时间到来的请求往往是随机不可控的，而系统的处理能力是有限的。我们需要根据系统的处理能力对流量进行控制。Sentinel 作为一个调配器，可以根据需要把随机的请求调整成合适的形状，如下图所示：
![流量控制](./pic/limitflow.gif)

#### 流量控制设计理念
流量控制有以下几个角度:

* 资源的调用关系，例如资源的调用链路，资源和资源之间的关系；
* 运行指标，例如 QPS、线程池、系统负载等；
* 控制的效果，例如直接限流、冷启动、排队等。

Sentinel 的设计理念是让您自由选择控制的角度，并进行灵活组合，从而达到想要的效果。

### 熔断降级
#### 什么是熔断降级
除了流量控制以外，及时对调用链路中的不稳定因素进行熔断也是 Sentinel 的使命之一。由于调用关系的复杂性，如果调用链路中的某个资源出现了不稳定，可能会导致请求发生堆积，进而导致级联错误。
![熔断降级](./pic/circuit.png)
Sentinel 和 Hystrix 的原则是一致的: 当检测到调用链路中某个资源出现不稳定的表现，例如请求响应时间长或异常比例升高的时候，则对这个资源的调用进行限制，让请求快速失败，避免影响到其它的资源而导致级联故障。

#### 熔断降级的设计理念
在限制的手段上，Sentinel 和 Hystrix 采取了完全不一样的方法。

Hystrix 通过 线程池隔离 的方式，来对依赖（在 Sentinel 的概念中对应 资源）进行了隔离。这样做的好处是资源和资源之间做到了最彻底的隔离。缺点是除了增加了线程切换的成本（过多的线程池导致线程数目过多），还需要预先给各个资源做线程池大小的分配。

Sentinel 对这个问题采取了两种手段:
* 通过并发线程数进行限制
* 通过响应时间对资源进行降级

## 为什么需要集群限流？
为什么要使用集群流控呢？假设我们希望给某个用户限制调用某个 API 的总 QPS 为 50，但机器数可能很多（比如有 100 台）。这时候我们很自然地就想到，找一个 server 来专门来统计总的调用量，其它的实例都与这台 server 通信来判断是否可以调用。这就是最基础的集群流控的方式。

另外集群流控还可以解决流量不均匀导致总体限流效果不佳的问题。假设集群中有 10 台机器，我们给每台机器设置单机限流阈值为 10 QPS，理想情况下整个集群的限流阈值就为 100 QPS。不过实际情况下流量到每台机器可能会不均匀，会导致总量没有到的情况下某些机器就开始限流。因此仅靠单机维度去限制的话会无法精确地限制总体流量。而集群流控可以精确地控制整个集群的调用总量，结合单机限流兜底，可以更好地发挥流量控制的效果。

## Sentinel 的熔断降级策略
1. 平均响应时间（`DEGRADE_GRADE_RT`）：当 1s 内持续进入 N 个请求，对应时刻的平均响应时间（秒级）均超过阈值（`count`，以 ms 为单位），那么在接下的时间窗口（`DegradeRule` 中的 `timeWindow`，以 s 为单位）之内，对这个方法的调用都会自动地熔断（抛出 `DegradeException`）。注意 Sentinel 默认统计的 RT 上限是 4900 ms，超出此阈值的都会算作 4900 ms，若需要变更此上限可以通过启动配置项 `-Dcsp.sentinel.statistic.max.rt=xxx` 来配置。
2. 异常比例（`DEGRADE_GRADE_RT`）：当资源的每秒请求量 >= N（可配置），并且每秒异常总数占通过量的比值超过阈值（`DegradeRule` 中的 count）之后，资源进入降级状态，即在接下的时间窗口（`DegradeRule` 中的 `timeWindow`，以 s 为单位）之内，对这个方法的调用都会自动地返回。异常比率的阈值范围是 `[0.0, 1.0]`，代表 0% - 100%。
3. 异常数 (`DEGRADE_GRADE_EXCEPTION_COUNT`)：当资源近 1 分钟的异常数目超过阈值之后会进行熔断。注意由于统计时间窗口是分钟级别的，若 `timeWindow` 小于 60s，则结束熔断状态后仍可能再进入熔断状态。

熔断策略上猫眼 Oceanus 优势：
1. 梯度熔断、梯度恢复
2. 异常统计参数支持业务返回值

## Sentinel 底层数据结构解析
Sentinel 做熔断限流需要统计一段时间内的 QPS、RT、异常数等数据，在高并发场景下这些数据如何正确且高效的记录、存储就是一个非常有难度的事情。

为了解决实时数据统计的问题，Sentinel 采用了一套精密的数据结构来支持。

* Striped64 累加器并发组件
* LongAdder 继承自 `Striped64`，用于高并发计数
* MetricBucket 表示一段时间内的指标数据，底层使用 `LongAdder` 进行计数，指标由 `MetricEvent` 枚举定义
    * MetricEvent 目前定义了6个指标，分别是 PASS（通过量）、BLOCK（拒绝量）、EXCEPTION（异常数）、SUCCESS（成功数）、RT（平均响应时间）、OCCUPIED_PASS（通过了未来的配额）
* WindowWrap 包装一段时间窗口数据的实体
* LeapArray 滑动窗口对数据进行计数的抽象实现，需要子类实现 `newEmptyBucket` 和 `resetWindowTo` 方法
    * BucketLeapArray 继承自 `LeapArray` ，时间跨度度量统计的基本数据结构
    * ClusterMetricLeapArray
    * ClusterParameterLeapArray
    * FutureBucketLeapArray
    * HotParameterLeapArray
    * OccupiableBucketLeapArray
    * UnaryLeapArray
* ArrayMetric 最高层的数据结构，Sentinel 最基本的指标类

下面让我们一起逐个分析这些数据结构吧。

### Striped64 & LongAdder
`Striped64` 提供了并发计数的基本组件，`LongAdder` 继承自 `Striped64`，实现了 `Number` 类的特性。
> [Java 并发计数组件Striped64详解](https://www.jianshu.com/p/30d328e9353b)

### MetricBucket
`MetricBucket` 内部有一个 `LongAdder[]` 实现指标计数，在构造函数中默认定义了 `MetricEvent` 里的所有指标，通过枚举中的ordinal为数组索引定位指标。

### WindowWrap
`WindowWrap` 故名思议就是一段窗口内容的包装类，主要有三个字段：
1. `windowLengthInMs` 窗口的大小（单位ms）
2. `windowStart` 窗口的开始时间
3. `value` 真正的数据 

### LeapArray
`LeapArray` 是一个基于数组实现的滑动窗口。LeapArray的源码学习见：[sentinel学习之LeapArray](./sentinel学习之LeapArray.md)

### BucketLeapArray
继承自 `LeapArray`，最基本的时间跨度度量统计的数据结构，是 `ArrayMetric` 默认使用的数据结构。

### ArrayMetric
Sentinel中的基本度量标准类，使用内部{@link BucketLeapArray}，有很多获取指标信息的方法。

## Sentinel 的 Slot 链
在 Sentinel 里面，所有的资源都对应一个资源名称（`resourceName`），每次资源调用都会创建一个 `Entry` 对象。Entry 可以通过对主流框架的适配自动创建，也可以通过注解的方式或调用 `SphU` API 显式创建。Entry 创建的时候，同时也会创建一系列功能插槽（slot chain），这些插槽有不同的职责，例如:

* `NodeSelectorSlot` 负责收集资源的路径，并将这些资源的调用路径，以树状结构存储起来，用于根据调用路径来限流降级；
* `ClusterBuilderSlot` 则用于存储资源的统计信息以及调用者信息，例如该资源的 RT, QPS, thread count 等等，这些信息将用作为多维度限流，降级的依据；
* `StatisticSlot` 则用于记录、统计不同纬度的 runtime 指标监控信息；
* `FlowSlot` 则用于根据预设的限流规则以及前面 slot 统计的状态，来进行流量控制；
* `AuthoritySlot` 则根据配置的黑白名单和调用来源信息，来做黑白名单控制；
* `DegradeSlot` 则通过统计信息以及预设的规则，来做熔断降级；
* `SystemSlot` 则通过系统的状态，例如 load1 等，来控制总的入口流量；

总体的框架如下:
![slots](./pic/slots.gif)

Sentinel 将 ProcessorSlot 作为 SPI 接口进行扩展（1.7.2 版本以前 SlotChainBuilder 作为 SPI），使得 Slot Chain 具备了扩展的能力。您可以自行加入自定义的 slot 并编排 slot 间的顺序，从而可以给 Sentinel 添加自定义的功能。
![slotChainBuilder](./pic/slotchainbuilder.png)

### Slot 链的创建 
* SlotChainProvider
提供 SPI 扩展机制，可以根据开发者需要加载自己的 `SlotChainBuilder`，默认使用 `DefaultSlotChainBuilder`。

> [高级开发必须理解的Java中SPI机制](https://www.jianshu.com/p/46b42f7f593c)
* SlotChainBuilder
建造默认的 Slot 链，包括 8 个 slot。
1. NodeSelectorSlot 建立树状结构（调用链路）
2. ClusterBuilderSlot 根据资源保存统计簇点
3. LogSlot 日志记录
4. StatisticSlot 实时数据统计
5. SystemSlot 系统保护
6. AuthoritySlot 权限控制 
7. FlowSlot 流量控制
8. DegradeSlot 熔断降级

### Slot 链何时创建？
CtSph 获取 entry 时会调用 `lookProcessChain` 方法创建 slot 链，一个 resource 对应一条 slot 链。

### Sentinel 的基本组件
Context
ContextUtil
Entry
Node
    - StatisticNode
        - DefaultNode
            - EntranceNode
        - ClusterNode
SlotChain






