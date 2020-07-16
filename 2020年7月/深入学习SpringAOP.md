# SpringAOP 

### TargetClassAware 接口
`TargetClassAware` 是一个很顶层的接口，可以通过该接口的 `getTargetClass` 方法，获取代理类中目标类的 Class

### TargetSource 接口
`TargetSource` 是代理目标类的包装类，拥有获取代理目标对象的方法，属于AOP框架中的内部接口，开发人员一般不会直接用到。

### Advised 接口
接口由保存AOP代理工厂配置的类实现。此配置包括拦截器和其他通知、增强器和代理接口。

### ProxyConfig 类
创建代理的配置类，确保所有代理创建者具有一致的属性。

### AdvisedSupport 类
AOP代理配置管理器的基类。这些它们本身不是AOP代理，但此类的子类通常是直接从中获取AOP代理实例的工厂。

1. 配置当前代理的Adivsiors
2. 配置当前代理的目标对象
3. 配置当前代理的接口
4. 提供 `getInterceptorsAndDynamicInterceptionAdvice` 方法用来获取对应代理方法对应有效的拦截器链

> AdvisedSupport本身不会提供创建代理的任何方法，专注于生成拦截器链


### ProxyCreatorSupport 类

### ProxyFactory 类
![ProxyFactory类图](./pic/ProxyFactory类图.jpg)

### Pointcut 接口

### MethodMatcher 接口

### StaticMethodMatcher 类

### StaticMethodMatcherPointcut 类

### Advice 接口

### Interceptor 接口

### MethodInterceptor 接口
![MethodInterceptor类图](./pic/MethodInterceptor类图.jpg)

### Invocation 接口

### MethodInvocation 接口



