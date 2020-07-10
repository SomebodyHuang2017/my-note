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
* Context
* ContextUtil
* Entry
* Node
    * StatisticNode
        * DefaultNode
            * EntranceNode
        * ClusterNode
* SlotChain

### SDK 的入口
我们了解一个组件，需要有特定的思路，通常都是从整体架构开始看，再从入口逐步去了解每个模块。现在我们来探究 Sentinel 作为一个 SDK，它从哪里开始工作的。

#### Env —— Sentinel 初始化之源
Env类代码很简单，只有几行。初始化的源头就是从 `InitExecutor.doInit` 开始的，而静态变量 `sph` 主要就是 entry 申请的唯一入口。 

```java
/**
 * Sentinel Env. This class will trigger all initialization for Sentinel.
 * Sentinel 环境。这个类将触发 Sentinel 的所有初始化动作。
 * <p>
 * NOTE: to prevent deadlocks, other classes' static code block or static field should
 * 提示：为了防止死锁，其他类的静态代码块或静态字段永远不要引用此类。
 * NEVER refer to this class.
 * </p>
 *
 * @author jialiang.linjl
 */
public class Env {

    public static final Sph sph = new CtSph();

    static {
        // If init fails, the process will exit.
        InitExecutor.doInit();
    }

}
```

#### InitExecutor 初始化执行器
`InitExecutor` 的功能是加载已注册的init函数并按顺序执行，其核心也就是利用了 Java 的 SPI 机制，让程序变得可扩展。

SDK 的 ***通信模块***、***集群限流模块*** 和 ***数据源注册模块*** 都是通过该执行器来初始化的。当然，如果有需要我们也可以去进行扩展，方式很简单：只需要实现一个叫 `InitFunc` 的接口，并在 `META-INF/services` 加上全限定类名就好了。

接下来我们看下他是如何进行初始化的，直接看代码吧：
```java
public final class InitExecutor {

    // 原子变量，CAS 保证初始化只会执行一次
    private static AtomicBoolean initialized = new AtomicBoolean(false);

    /**
     * If one {@link InitFunc} throws an exception, the init process
     * will immediately be interrupted and the application will exit.
     *
     * The initialization will be executed only once.
     */
    public static void doInit() {
        // 保证 doInit 方法只会执行一次
        if (!initialized.compareAndSet(false, true)) {
            return;
        }
        try {
            // 通过 Java 的 SPI 机制，加载并是实例化目录 META-INF/services 下配置的所有实现了 InitFunc 接口的类
            ServiceLoader<InitFunc> loader = ServiceLoader.load(InitFunc.class);
            List<OrderWrapper> initList = new ArrayList<OrderWrapper>();
            for (InitFunc initFunc : loader) {
                RecordLog.info("[InitExecutor] Found init func: " + initFunc.getClass().getCanonicalName());
                // 重新包装 initFunc 并按照 InitOrder 注解的 value 值进行排序
                insertSorted(initList, initFunc);
            }
            // 遍历整个list，执行初始化方法
            for (OrderWrapper w : initList) {
                w.func.init();
                RecordLog.info(String.format("[InitExecutor] Executing %s with order %d",
                    w.func.getClass().getCanonicalName(), w.order));
            }
        } catch (Exception ex) {
            RecordLog.warn("[InitExecutor] WARN: Initialization failed", ex);
            ex.printStackTrace();
        } catch (Error error) {
            RecordLog.warn("[InitExecutor] ERROR: Initialization failed with fatal error", error);
            error.printStackTrace();
        }
    }

    private static void insertSorted(List<OrderWrapper> list, InitFunc func) {
        int order = resolveOrder(func);
        int idx = 0;
        for (; idx < list.size(); idx++) {
            if (list.get(idx).getOrder() > order) {
                break;
            }
        }
        list.add(idx, new OrderWrapper(order, func));
    }

    // 获取优先级，如果没有注解则使用默认优先级
    private static int resolveOrder(InitFunc func) {
        if (!func.getClass().isAnnotationPresent(InitOrder.class)) {
            return InitOrder.LOWEST_PRECEDENCE;
        } else {
            return func.getClass().getAnnotation(InitOrder.class).value();
        }
    }

    private InitExecutor() {}

    private static class OrderWrapper {
        private final int order;
        private final InitFunc func;

        OrderWrapper(int order, InitFunc func) {
            this.order = order;
            this.func = func;
        }

        int getOrder() {
            return order;
        }

        InitFunc getFunc() {
            return func;
        }
    }
}
``` 

我们可以看个例子，如果配置了SDK的通信模块它是怎么启动的，代码如下：

```java
/**
 * 命令中心：装载了该模块后我们就可以和SDK进行通信，例如查看当前SDK上的规则配置什么的 
 */
@InitOrder(-1)
public class CommandCenterInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        CommandCenter commandCenter = CommandCenterProvider.getCommandCenter();

        if (commandCenter == null) {
            RecordLog.warn("[CommandCenterInitFunc] Cannot resolve CommandCenter");
            return;
        }

        commandCenter.beforeStart();
        commandCenter.start();
        RecordLog.info("[CommandCenterInit] Starting command center: "
                + commandCenter.getClass().getCanonicalName());
    }
}

/**
 * 心跳发送器：主要是定时和管理端发送心跳信息，用来检测机器是否存活，还会附带发送SDK版本等信息 
 */
@InitOrder(-1)
public class HeartbeatSenderInitFunc implements InitFunc {
    // ...省略
    
    @Override
    public void init() {
        HeartbeatSender sender = HeartbeatSenderProvider.getHeartbeatSender();
        if (sender == null) {
            RecordLog.warn("[HeartbeatSenderInitFunc] WARN: No HeartbeatSender loaded");
            return;
        }

        initSchedulerIfNeeded();
        long interval = retrieveInterval(sender);
        setIntervalIfNotExists(interval);
        scheduleHeartbeatTask(sender, interval);
    }
    
    // ...省略
}
```
可以看到，两个模块初始化的流程基本都是类似的，第一行代码甚至都是再通过SPI来动态加载需要实例化的类，后续也就是初始化和启动操作。

#### Sph 获取 Entry 的入口
`Sph` 是一个接口，定义了获取 `Entry` 的各种重载方法.能够获取到 `Entry` 就说明通过了资源申请，否则会抛出 `BlockException`。

`Sph` 的唯一实现类是 `CtSph`。

#### CtSph 获取 Entry 
前面说到过 `Env` 是 Sentinel 程序的启动入口，`Env` 的一个静态变量就是 `CtSph`，现在来看 `CtSph` 的实现。

CtSph 有 3 个静态变量：
 1. OBJECTS0: 用作默认的接口参数
 2. chainMap: 保存所有的 *<资源:处理器链>* 的映射
 3. LOCK: 创建处理器链的锁

`CtSph` 实现了 `Sph` 所有的方法，这些获取 `Entry` 的方法可以分成两大类，一类是 **同步获取Entry**，另一类是 **异步获取Entry**。而获取两种 Entry 的方法，最终就是两个：`entryWithPriority` 和 `asyncEntryWithPriorityInternal`。 

```java
public class CtSph implements Sph {
    
    private Entry entryWithPriority(ResourceWrapper resourceWrapper, int count, boolean prioritized, Object... args)
        throws BlockException {
        // 获取上下文
        Context context = ContextUtil.getContext();
        if (context instanceof NullContext) {
            // 如果获取到的是空上下文则说明上下文个数超出了创建限制，这里直接返回，后续规则检查也不会进行了
            // The {@link NullContext} indicates that the amount of context has exceeded the threshold,
            // so here init the entry only. No rule checking will be done.
            return new CtEntry(resourceWrapper, null, context);
        }

        if (context == null) {
            // Using default context.
            // 如果上下文为空则使用默认的上下文
            context = MyContextUtil.myEnter(Constants.CONTEXT_DEFAULT_NAME, "", resourceWrapper.getType());
        }

        // Global switch is close, no rule checking will do.
        // 全局开关，默认是开启状态。如果规则开关关闭则所有策略失效。
        if (!Constants.ON) {
            return new CtEntry(resourceWrapper, null, context);
        }

        // 走到这里很关键，通过 resourceWrapper 查找处理器链（slotChain）
        ProcessorSlot<Object> chain = lookProcessChain(resourceWrapper);

        /*
         * Means amount of resources (slot chain) exceeds {@link Constants.MAX_SLOT_CHAIN_SIZE},
         * so no rule checking will be done.
         * 处理器链有可能获取到空，原因是在 lookProcessChain 里面做了创建数量限制，因该是怕SDK占用系统资源过多角度考虑设计的
         */
        if (chain == null) {
            return new CtEntry(resourceWrapper, null, context);
        }

        Entry e = new CtEntry(resourceWrapper, chain, context);
        try {
            // 执行处理器链处理资源的规则
            chain.entry(context, resourceWrapper, null, count, prioritized, args);
        } catch (BlockException e1) {
            // 退出 entry
            e.exit(count, args);
            throw e1;
        } catch (Throwable e1) {
            // This should not happen, unless there are errors existing in Sentinel internal.
            RecordLog.info("Sentinel unexpected exception", e1);
        }
        return e;
    }
}
```

接下来继续看 `lookProcessChain` 方法是如何获取处理链的：

```java
public class CtSph implements Sph {
    /**
     * Get {@link ProcessorSlotChain} of the resource. new {@link ProcessorSlotChain} will
     * be created if the resource doesn't relate one.
     * 获取当前资源的处理器链，如果当前资源没有相关联的处理器链则创建。
     *
     * <p>Same resource({@link ResourceWrapper#equals(Object)}) will share the same
     * {@link ProcessorSlotChain} globally, no matter in witch {@link Context}.<p/>
     * 相同的资源共享相同的处理器链，不管上下文是否相同。
     *
     * <p>
     * Note that total {@link ProcessorSlot} count must not exceed {@link Constants#MAX_SLOT_CHAIN_SIZE},
     * otherwise null will return.
     * 处理器链的数量必须小于 MAX_SLOT_CHAIN_SIZE，否则直接返回 null
     * </p>
     *
     * @param resourceWrapper target resource
     * @return {@link ProcessorSlotChain} of the resource
     */
    ProcessorSlot<Object> lookProcessChain(ResourceWrapper resourceWrapper) {
        ProcessorSlotChain chain = chainMap.get(resourceWrapper);
        // double check 方式获取处理器链
        if (chain == null) {
            synchronized (LOCK) {
                chain = chainMap.get(resourceWrapper);
                
                if (chain == null) {
                    // Entry size limit.
                    // 超出数量限制返回 null
                    if (chainMap.size() >= Constants.MAX_SLOT_CHAIN_SIZE) {
                        return null;
                    }

                    // 通过 SPI 机制获取 SlotChainBuilder 后创建处理器链
                    chain = SlotChainProvider.newSlotChain();
                    Map<ResourceWrapper, ProcessorSlotChain> newMap = new HashMap<ResourceWrapper, ProcessorSlotChain>(
                        chainMap.size() + 1);
                    newMap.putAll(chainMap);
                    newMap.put(resourceWrapper, chain);
                    // copy on write 更新 资源 到 处理器链 的map
                    chainMap = newMap;
                }
            }
        }
        return chain;
    }
}
```

### NodeSelectorSlot 
```java
/**
 * </p>
 * This class will try to build the calling traces via
 * 这个类用来尝试建立调用跟踪
 * <ol>
 * <li>adding a new {@link DefaultNode} if needed as the last child in the context.
 * The context's last node is the current node or the parent node of the context. </li>
 * 如果需要，添加新的 DefaultNode 作为上下文中的最后一个子级。上下文的最后一个节点是上下文的当前节点或父节点。
 * <li>setting itself to the context current node.</li>
 * 设置自己作为上下文的当前节点
 * </ol>
 * </p>
 *
 * <p>It works as follow:</p>
 * <pre>
 * ContextUtil.enter("entrance1", "appA");
 * Entry nodeA = SphU.entry("nodeA");
 * if (nodeA != null) {
 *     nodeA.exit();
 * }
 * ContextUtil.exit();
 * </pre>
 *
 * Above code will generate the following invocation structure in memory:
 * 上面的代码将在内存中生成如下调用结构：
 *
 * <pre>
 *
 *              machine-root
 *                  /
 *                 /
 *           EntranceNode1
 *               /
 *              /
 *        DefaultNode(nodeA)- - - - - -> ClusterNode(nodeA);
 * </pre>
 *
 * <p>
 * Here the {@link EntranceNode} represents "entrance1" given by
 * {@code ContextUtil.enter("entrance1", "appA")}.
 * </p>
 * <p>
 * Both DefaultNode(nodeA) and ClusterNode(nodeA) holds statistics of "nodeA", which is given
 * by {@code SphU.entry("nodeA")}
 * 上面的 DefaultNode(nodeA) 和 ClusterNode(nodeA) 持有 nodeA 的统计数据。
 * </p>
 * <p>
 * The {@link ClusterNode} is uniquely identified by the ResourceId; the {@link DefaultNode}
 * is identified by both the resource id and {@link Context}. In other words, one resource
 * id will generate multiple {@link DefaultNode} for each distinct context, but only one
 * {@link ClusterNode}.
 * ClusterNode 由 资源ID 唯一标识；
 * DefaultNode 由 资源ID 和 上下文 共同标识。换句话说，一个 资源ID 为不同的上下文生成多个 DefaultNode，但只有一个 ClusterNode。
 * </p>
 * <p>
 * the following code shows one resource id in two different context:
 * </p>
 *
 * <pre>
 *    ContextUtil.enter("entrance1", "appA");
 *    Entry nodeA = SphU.entry("nodeA");
 *    if (nodeA != null) {
 *        nodeA.exit();
 *    }
 *    ContextUtil.exit();
 *
 *    ContextUtil.enter("entrance2", "appA");
 *    nodeA = SphU.entry("nodeA");
 *    if (nodeA != null) {
 *        nodeA.exit();
 *    }
 *    ContextUtil.exit();
 * </pre>
 *
 * Above code will generate the following invocation structure in memory:
 *
 * <pre>
 *
 *                  machine-root
 *                  /         \
 *                 /           \
 *         EntranceNode1   EntranceNode2
 *               /               \
 *              /                 \
 *      DefaultNode(nodeA)   DefaultNode(nodeA)
 *             |                    |
 *             +- - - - - - - - - - +- - - - - - -> ClusterNode(nodeA);
 * </pre>
 *
 * <p>
 * As we can see, two {@link DefaultNode} are created for "nodeA" in two context, but only one
 * {@link ClusterNode} is created.
 * 正如我们所见，nodeA 创建了两个 DefaultNode，
 * </p>
 *
 * <p>
 * We can also check this structure by calling: <br/>
 * {@code curl http://localhost:8719/tree?type=root}
 * </p>
 *
 * @author jialiang.linjl
 * @see EntranceNode
 * @see ContextUtil
 */
public class NodeSelectorSlot extends AbstractLinkedProcessorSlot<Object> {

    /**
     * {@link DefaultNode}s of the same resource in different context.
     */
    private volatile Map<String, DefaultNode> map = new HashMap<String, DefaultNode>(10);

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, Object obj, int count, boolean prioritized, Object... args)
        throws Throwable {
        /*
         * It's interesting that we use context name rather resource name as the map key.
         * 有意思的是我们使用上下文名称而不是使用资源名来做map的key
         *
         * Remember that same resource({@link ResourceWrapper#equals(Object)}) will share
         * the same {@link ProcessorSlotChain} globally, no matter in which context. So if
         * code goes into {@link #entry(Context, ResourceWrapper, DefaultNode, int, Object...)},
         * the resource name must be same but context name may not.
         * 请记住相同的资源将共享相同的处理器链，不管上下文是什么。所以如果代码执行进 entry，资源名必须相同，但上下文可能不同。
         *
         * If we use {@link com.alibaba.csp.sentinel.SphU#entry(String resource)} to
         * enter same resource in different context, using context name as map key can
         * distinguish the same resource. In this case, multiple {@link DefaultNode}s will be created
         * of the same resource name, for every distinct context (different context name) each.
         * 如果我们在不同的上下文使用 SphU.entry 进入相同的资源，使用上下文名称作为map的key可以区分不同的资源。
         * 在这种情况下，将创建多个 DefaultNode 对于每个不同的上下文（不同的上下文名称），都具有相同的资源名称。
         *
         * Consider another question. One resource may have multiple {@link DefaultNode},
         * so what is the fastest way to get total statistics of the same resource?
         * The answer is all {@link DefaultNode}s with same resource name share one
         * {@link ClusterNode}. See {@link ClusterBuilderSlot} for detail.
         * 考虑另一个问题。一个资源可能具有多个 DefaultNode，那么获取同一资源的总统计信息的最快方法是什么？
         * 答案是所有具有相同资源名称的{@link DefaultNode}共享一个{@link ClusterNode}。
         * 有关详细信息，请参见{@link ClusterBuilderSlot}。
         */
        DefaultNode node = map.get(context.getName());
        if (node == null) {
            synchronized (this) {
                node = map.get(context.getName());
                if (node == null) {
                    node = new DefaultNode(resourceWrapper, null);
                    HashMap<String, DefaultNode> cacheMap = new HashMap<String, DefaultNode>(map.size());
                    cacheMap.putAll(map);
                    cacheMap.put(context.getName(), node);
                    map = cacheMap;
                    // Build invocation tree
                    ((DefaultNode) context.getLastNode()).addChild(node);
                }

            }
        }

        context.setCurNode(node);
        fireEntry(context, resourceWrapper, node, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
        fireExit(context, resourceWrapper, count, args);
    }
}
```


#### ClusterBuilderSolt
```java
/**
 * <p>
 * This slot maintains resource running statistics (response time, qps, thread
 * count, exception), and a list of callers as well which is marked by
 *  * {@link ContextUtil#enter(String origin)}
 * 这个slot保持这资源运行时的统计数据（响应时间，qps，线程数，异常情况），以及被 ContextUtil.entry 标记的调用者列表
 * </p>
 * <p>
 * One resource has only one cluster node, while one resource can have multiple
 * default nodes.
 * 一个资源只有一个ClusterNode，但是一个资源可以有多个DefaultNode
 * </p>
 *
 * @author jialiang.linjl
 */
public class ClusterBuilderSlot extends AbstractLinkedProcessorSlot<DefaultNode> {

    /**
     * <p>
     * Remember that same resource({@link ResourceWrapper#equals(Object)}) will share
     * the same {@link ProcessorSlotChain} globally, no matter in witch context. So if
     * code goes into {@link #entry(Context, ResourceWrapper, DefaultNode, int, boolean, Object...)},
     * the resource name must be same but context name may not.
     * </p>
     * <p>
     * To get total statistics of the same resource in different context, same resource
     * shares the same {@link ClusterNode} globally. All {@link ClusterNode}s are cached
     * in this map.
     * </p>
     * <p>
     * The longer the application runs, the more stable this mapping will
     * become. so we don't concurrent map but a lock. as this lock only happens
     * at the very beginning while concurrent map will hold the lock all the time.
     * 程序运行的时间越长，这个映射将变得越稳定。所以我们只需要一个锁而不需要使用并发map，锁只在一开始使用，而使用并发map那么锁将一直存在。
     * </p>
     */
    private static volatile Map<ResourceWrapper, ClusterNode> clusterNodeMap = new HashMap<>();

    private static final Object lock = new Object();

    private volatile ClusterNode clusterNode = null;

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode node, int count,
                      boolean prioritized, Object... args)
        throws Throwable {
        if (clusterNode == null) {
            synchronized (lock) {
                if (clusterNode == null) {
                    // Create the cluster node.
                    clusterNode = new ClusterNode();
                    HashMap<ResourceWrapper, ClusterNode> newMap = new HashMap<>(Math.max(clusterNodeMap.size(), 16));
                    newMap.putAll(clusterNodeMap);
                    newMap.put(node.getId(), clusterNode);

                    clusterNodeMap = newMap;
                }
            }
        }
        node.setClusterNode(clusterNode);

        /*
         * if context origin is set, we should get or create a new {@link Node} of
         * the specific origin.
         * 如果设置了上下文来源，则应获取或创建一个特定来源的新节点。
         */
        if (!"".equals(context.getOrigin())) {
            Node originNode = node.getClusterNode().getOrCreateOriginNode(context.getOrigin());
            context.getCurEntry().setOriginNode(originNode);
        }

        fireEntry(context, resourceWrapper, node, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
        fireExit(context, resourceWrapper, count, args);
    }

    /**
     * Get {@link ClusterNode} of the resource of the specific type.
     *
     * @param id   resource name.
     * @param type invoke type.
     * @return the {@link ClusterNode}
     */
    public static ClusterNode getClusterNode(String id, EntryType type) {
        return clusterNodeMap.get(new StringResourceWrapper(id, type));
    }

    /**
     * Get {@link ClusterNode} of the resource name.
     *
     * @param id resource name.
     * @return the {@link ClusterNode}.
     */
    public static ClusterNode getClusterNode(String id) {
        if (id == null) {
            return null;
        }
        ClusterNode clusterNode = null;

        for (EntryType nodeType : EntryType.values()) {
            clusterNode = clusterNodeMap.get(new StringResourceWrapper(id, nodeType));
            if (clusterNode != null) {
                break;
            }
        }

        return clusterNode;
    }

    /**
     * Get {@link ClusterNode}s map, this map holds all {@link ClusterNode}s, it's key is resource name,
     * value is the related {@link ClusterNode}. <br/>
     * DO NOT MODIFY the map returned.
     *
     * @return all {@link ClusterNode}s
     */
    public static Map<ResourceWrapper, ClusterNode> getClusterNodeMap() {
        return clusterNodeMap;
    }

    /**
     * Reset all {@link ClusterNode}s. Reset is needed when {@link IntervalProperty#INTERVAL} or
     * {@link SampleCountProperty#SAMPLE_COUNT} is changed.
     */
    public static void resetClusterNodes() {
        for (ClusterNode node : clusterNodeMap.values()) {
            node.reset();
        }
    }
}

```


