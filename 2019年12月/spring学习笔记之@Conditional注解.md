# Spring学习笔记 - @Conditional 类型注解

# 一、@Conditional 注解
1、简介
@Conditional 是Spring中常用的一个注解，标记了该注解后，只有在满足@Conditional中指定的所有条件后，才可以向容器中注入组件。
2、接口信息
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Conditional {

	/**
	 * All {@link Condition}s that must {@linkplain Condition#matches match}
	 * in order for the component to be registered.
	 */
	Class<? extends Condition>[] value();

}
```
3、解析
在Condition接口的matches()方法加断点，可以追溯到ConfigurationClassBeanDefinitionReader类的loadBeanDefinitionsForBeanMethod方法中，该方法会根据配置类的标记了`@Bean`注解的方法，向容器中注入Bean。`loadBeanDefinitionsForBeanMethod `方法，该方法会扫描所有@Configuration配置类的标记了@Bean的注解的方法，将方法封装成一个BeanMethod，向容器中注入。在处理前，会调用ConditionEvaluator的shouldSkip()方法判断当前Bean是否要跳过注册。ConditionEvaluator对象是在ConfigurationClassBeanDefinitionReader构造器中实例化的，其主要作用就是处理@Conditional注解的相关逻辑。
在shouldSkip()方法中，会@Bean所在方法或者类上的@Conditional注解，并获取@Conditional注解的所有Condition条件对象，依次调用matcher()方法。只要有一个Condition匹配不成功，就跳过该Bean的注册。
# 二、@Conditional 的衍生注解

* `@ConditionalOnBean`：当IOC容器中已经存在指定Class的实例时，才满足条件。
* `@ConditionalOnClass`：判断当前classpath下是否存在指定的类，如果存在则将当前bean配置到spring容器
* `@ConditionalOnMissingBean`：当IOC容器中不存在指定类型的bean的时候
* `@ConditionalOnExpression`：当表达式为true的时候，才会实例化一个Bean
* `@ConditionalOnMissingClass`：某个classpath下不存在指定的类，才会实例化一个bean
* `@ConditionalOnNotWebApplication`：不是web应用才加载bean

