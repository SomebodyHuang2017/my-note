# Dubbo之ExtensionLoader
dubbo具有很强的扩展性，看过dubbo的架构设计图就知道，它的每一层都是单向依赖的，面向接口的设计让其不同分层的组件都可以灵活替换，
而ExtensionLoader就类似于java的SPI机制，并且在上面做了增强，可以用来启用框架扩展和替换组件，堪称模块间的桥梁。

## 如何使用ExtensionLoader
ExtensionLoader使用起来比较简单：
第一步：通过ExtensionLoader的getExtensionLoader方法来创建或获取一个ExtensionLoader实例。
第二部：通过ExtensionLoader的getExtension方法获取到具体的组件。

示例如下：
```java
class ExtensionLoaderDemo {
    
    // 获取ExtensionLoader实例
    private static final ExtensionLoader<Container> LOADER = ExtensionLoader.getExtensionLoader(Container.class);
    
    public void xxxx() {
        // 传入名称获取具体的组件
        Container springContainer = LOADER.getExtension("spring");
        
    }
    
}
```

## ExtensionLoader相关的几个类
* **ExtensionLoader**：类，扩展器加载工具类
* **ExtensionFactory**：接口，扩展组件的工厂接口
* **LoadingStrategy**：接口，描述的是加载策略，有三个实现类。DubboInternalLoadingStrategy（加载dubbo内部组件）、DubboLoadingStrategy（加载由用户自定义的dubbo组件）、ServicesLoadingStrategy（加载java SPI目录下配置的组件）
* **SPI**：注解，用在扩展点的接口上，标识后可被dubbo加载
* **Adaptive**：注解，为ExtensionLoader注入依赖扩展实例提供有用的信息
* **Activate**：注解，给定特定的条件后可以自动激活组件，如果有多个实现，可以选择加载某些组件
* **Wrapper**：注解，带有该主机的类仅在条件匹配时作为包装器工作

## ExtensionLoader运行流程
ExtensionLoader运行流程可以分两部进行描述：
* 第一部分：ExtensionLoader的创建
* 第二部分：Extension的加载

## ExtensionLoader 的创建
ExtensionLoader创建比较简单，就是一个方法getExtensionLoader，传入扩展点的类型就可以。
看代码可以知道它首先会对type类型进行校验：
1. 不能为空
2. 必须保证该类型是接口类型
3. 必须保证该接口类型上加上了@SPI注解

校验过后，首先从map中获取ExtensionLoader，如果不存在的话则new一个新的ExtensionLoader，放到map中缓存起来，再返回。
```java
public class ExtensionLoader<T> {
  public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type (" + type + ") is not an interface!");
        }
        if (!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException("Extension type (" + type +
                    ") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
        }

        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }
}
```

这里需要注意的是，ExtensionLoader的构造方法中有一些逻辑，是去加载ExtensionFactory。
如果加载的组件类型不是ExtensionFactory类型，那么就会主动加载ExtensionFactory组件。

```java
public class ExtensionLoader<T> {
        private ExtensionLoader(Class<?> type) {
            this.type = type;
            objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
        }
}
```

从这里可以知道，ExtensionFactory也是通过ExtensionLoader去加载的，那么下面就继续走向扩展加载的第二步。

## Extension 的加载
