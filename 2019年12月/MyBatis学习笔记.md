# MyBatis

## MyBatis核心类
* **SqlSessionFactory：** 每个基于MyBatis的应用都是以一个SqlSessionFactory的实例为中心的。SqlSessionFactory的实例可以通过SqlSessionFactoryBuilder获得。而SqlSessionFactoryBuilder则可以通过XML或者通过java的方式构建出SqlSessionFactory的实例。SqlSessionFactory一旦创建就应该在应用的运行期间一直存在，建议使用单例模式或者静态单例模式。一个SqlSessionFactory对应配置文件中的一个环境（enviroment），如果你要使用多个数据库就配置多个环境分别对应一个SqlSession。
* **SqlSession：** SqlSession是一个接口，它有2个实现类，分别是DefaultSqlSession和SqlSessionManager。SqlSession通过内部放置的执行期（Executor）来对数据进行CURD。此外SqlSession是线程不安全的，因为每一次操作完数据库后都要调用close对其进行关闭，官方建议通过try-finally来保证总是关闭SqlSession。
* **Executor：** Executor 执行器有两个实现类，其中BaseExecutor有三个继承类分别是BatchExecutor（重用语句并执行批量更新），ReuseExecutor（重用预处理语句prepared statements）,SimpleExecutor（普通的执行器）。以上就是主要的Executor。通过下图可以看到MyBatis在Executor的设计上面使用了装饰者模式，我们可以用CachingExecutor来装饰前面的三个执行器的目的就是用来实现缓存。![ExecutorUML](http://127.0.0.1/Desktop/ExecutorUML.jpg)
* **MappedStatement：** MappedStatement就是用来存放我们SQL映射文件的信息包括sql语句，输入参数，输出参数等等。一个SQL节点对应一个MappedStatement对象。

## MyBatis工作流程
`SqlSessionFactoryBuilder -> SqlSessionFactory -> SqlSession -> Configuration -> MapperRegistry -> mapperProxyFactory -> Proxy -> Mapper$Proxy -> MapperProxy -> DB`
1. 通过SqlSessionFactoryBuilder创建SqlSessionFactory
首先在SqlSessionFactoryBuilder中的build方法使用了一个叫做XMLConfigBuilder来解析mybatis-config.xml。针对配置文件的每一个节点进行解析并将数据存放到Configuration这个对象中，紧接着使用带有方法入参是Configuration的build方法创建DefaultSqlSessionFactory。
```java
public class SqlSessionFactoryBuilder {

   //省略 build()方法的各种重载....

  public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
    try {
      XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
    // parse() 方法对 mybatis-config.xml 进行解析
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        reader.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
    try {
      XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        inputStream.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(Configuration config) {
   // 默认使用的是DefaultSqlSessionFactory
    return new DefaultSqlSessionFactory(config);
  }

}
```
在来看一下XMLConfigBuilder类的一些重要内容：
```java
public class XMLConfigBuilder extends BaseBuilder {

  private boolean parsed;
  private XPathParser parser;
  private String environment;
  private ReflectorFactory localReflectorFactory = new DefaultReflectorFactory();

  // 省略其他各种构造函数

  private XMLConfigBuilder(XPathParser parser, String environment, Properties props) {
    super(new Configuration());
    ErrorContext.instance().resource("SQL Mapper Configuration");
    this.configuration.setVariables(props);
    this.parsed = false;
    this.environment = environment;
    this.parser = parser;
  }

  public Configuration parse() {
    if (parsed) {
      throw new BuilderException("Each XMLConfigBuilder can only be used once.");
    }
    parsed = true;
  // 获取根节点configuration
    parseConfiguration(parser.evalNode("/configuration"));
    return configuration;
  }

  // 开始解析mybatis-config.xml，并把解析后的数据存放到configuration中去
  private void parseConfiguration(XNode root) {
    try {
   // 解析settings节点配置
      Properties settings = settingsAsPropertiess(root.evalNode("settings"));
      //issue #117 read properties first
    // 配置外部的properties
      propertiesElement(root.evalNode("properties"));
    // 检查是否配置了VFS(virtual File System)
      loadCustomVfs(settings);
   // 是否设置了别名，减少完全限定名的冗余
      typeAliasesElement(root.evalNode("typeAliases"));
    // 配置插件拦截，例如分页插件
      pluginElement(root.evalNode("plugins"));
    // 查看是否配置了ObjectFactory，默认情况下使用对象的无参构造方法或者是带有参数的构造方法
      objectFactoryElement(root.evalNode("objectFactory"));
    // 查看是否配置了objectWrapperFatory,这个用来或者ObjectWapper
      objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
    // 查看是否配置了reflectorFactory,mybatis的反射工具，提供了很多反射方法。
      reflectionFactoryElement(root.evalNode("reflectionFactory"));
    // 放入参数到configuration对象
      settingsElement(settings);
      // read it after objectFactory and objectWrapperFactory issue #631
    // 查看数据库环境配置
      environmentsElement(root.evalNode("environments"));
    // 查看是否使用多种数据库
      databaseIdProviderElement(root.evalNode("databaseIdProvider"));
    // 查看是否配置了新的类型处理器，如果跟处理的类型跟默认的一致就会覆盖
      typeHandlerElement(root.evalNode("typeHandlers"));
    // 查看是否配置SQL映射文件,有四种配置方式，resource，url，class以及自动扫包package
      mapperElement(root.evalNode("mappers"));
    } catch (Exception e) {
      throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    }
  }

  private Properties settingsAsPropertiess(XNode context) {
    if (context == null) {
      return new Properties();
    }
    Properties props = context.getChildrenAsProperties();
    // Check that all settings are known to the configuration class
    MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
    for (Object key : props.keySet()) {
    // 验证配置项是否在Configuration中存在，不存在则抛出异常
      if (!metaConfig.hasSetter(String.valueOf(key))) {
        throw new BuilderException("The setting " + key + " is not known.  Make sure you spelled it correctly (case sensitive).");
      }
    }
    return props;
  }

 // ...

}
```
2. 通过SqlSessionFactory创建SqlSession
```java
 private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
    Transaction tx = null;
    try {
  // 拿到mybatis中的数据库配置环境
      final Environment environment = configuration.getEnvironment();
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
    // 获取事务管理器，mybatis中提供了两种：JdbcTransactionFactory 和 ManagedTrasactionFactory。我们的项目几乎都是spring的，spring提供了SpringManagedTransactionFactory
      tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
    // 根据ExecutorType创建执行器
      final Executor executor = configuration.newExecutor(tx, execType);
    // 获取 SqlSession
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      closeTransaction(tx); // may have fetched a connection so lets call close()
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }
  }

// 通过连接获取SqlSession
  private SqlSession openSessionFromConnection(ExecutorType execType, Connection connection) {
    try {
      boolean autoCommit;
      try {
        autoCommit = connection.getAutoCommit();
      } catch (SQLException e) {
        // Failover to true, as most poor drivers
        // or databases won't support transactions
        autoCommit = true;
      }
      final Environment environment = configuration.getEnvironment();
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
      final Transaction tx = transactionFactory.newTransaction(connection);
      final Executor executor = configuration.newExecutor(tx, execType);
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }

  // 获取事务工厂
    private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
         if (environment == null || environment.getTransactionFactory() == null) {
           return new ManagedTransactionFactory();
      }
    return environment.getTransactionFactory();
  }

  // 关闭事务
  private void closeTransaction(Transaction tx) {
    if (tx != null) {
      try {
        tx.close();
      } catch (SQLException ignore) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }
  }
```
3. 通过SqlSession拿到Mapper对象的代理
```java
 /********* DefaultSqlSession ************/
// DefaultSqlSession的getMapper其实是调用configuration获取mapper
  @Override
  public <T> T getMapper(Class<T> type) {
    return configuration.<T>getMapper(type, this);
  }

 /********* Configuration ************/
  // Congiuration获取mapper其实是调用mapperRegistry获取mapper
  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    return mapperRegistry.getMapper(type, sqlSession);
  }

 /********* MapperRegistry ************/
 // knowMapper是一个HashMap在存放mapperRegistry的过程中就以Maper对象的calss对象为key,MapperProxyFactory为value保存
  private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, MapperProxyFactory<?>>();

  public MapperRegistry(Configuration config) {
    this.config = config;
  }

  @SuppressWarnings("unchecked")
  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
  // 拿key直接从knownMappers中获取就好了
    final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
    if (mapperProxyFactory == null) {
      throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
    }
    try {
      return mapperProxyFactory.newInstance(sqlSession);
    } catch (Exception e) {
      throw new BindingException("Error getting mapper instance. Cause: " + e, e);
    }
  }

/********** MapperProxyFactory ***********/

  public T newInstance(SqlSession sqlSession) {
    final MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, mapperInterface, methodCache);
  // 利用jdk动态代理生成一个Mapper的代理
    return newInstance(mapperProxy);
  }

  protected T newInstance(MapperProxy<T> mapperProxy) {
    // 生成mapperProxy对象，这个对象实现了InvocationHandler，Serializable。
  // 就是JDK动态代理中的方法调用处理器
    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
  }

```
4. 通过MapperProxy调用Mapper中相应的方法
```java
public class MapperProxy<T> implements InvocationHandler, Serializable {

  private static final long serialVersionUID = -6424540398559729838L;
  private final SqlSession sqlSession;
  private final Class<T> mapperInterface;
  private final Map<Method, MapperMethod> methodCache;

  public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
    this.sqlSession = sqlSession;
    this.mapperInterface = mapperInterface;
    this.methodCache = methodCache;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
  // 判断当前调用的method方法是不是Object中声明的方法，如果是的话直接执行
    if (Object.class.equals(method.getDeclaringClass())) {
      try {
        return method.invoke(this, args);
      } catch (Throwable t) {
        throw ExceptionUtil.unwrapThrowable(t);
      }
    }
    final MapperMethod mapperMethod = cachedMapperMethod(method);
    return mapperMethod.execute(sqlSession, args);
  }

 // 把method放到ConcurrentHashMap中缓存起来
  private MapperMethod cachedMapperMethod(Method method) {
    MapperMethod mapperMethod = methodCache.get(method);
    if (mapperMethod == null) {
      mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
      methodCache.put(method, mapperMethod);
    }
    return mapperMethod;
  }
}


public class MapperMethod {

  private final SqlCommand command;
  private final MethodSignature method;

  public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
    this.command = new SqlCommand(config, mapperInterface, method);
    this.method = new MethodSignature(config, mapperInterface, method);
  }

// 关键方法
  public Object execute(SqlSession sqlSession, Object[] args) {
    Object result;
    if (SqlCommandType.INSERT == command.getType()) {
  // 转换参数为 ParamMap
      Object param = method.convertArgsToSqlCommandParam(args);
      result = rowCountResult(sqlSession.insert(command.getName(), param));
    } else if (SqlCommandType.UPDATE == command.getType()) {
      Object param = method.convertArgsToSqlCommandParam(args);
      result = rowCountResult(sqlSession.update(command.getName(), param));
    } else if (SqlCommandType.DELETE == command.getType()) {
      Object param = method.convertArgsToSqlCommandParam(args);
      result = rowCountResult(sqlSession.delete(command.getName(), param));
    } else if (SqlCommandType.SELECT == command.getType()) {
      if (method.returnsVoid() && method.hasResultHandler()) {
        executeWithResultHandler(sqlSession, args);
        result = null;
      } else if (method.returnsMany()) {
        result = executeForMany(sqlSession, args);
      } else if (method.returnsMap()) {
        result = executeForMap(sqlSession, args);
      } else if (method.returnsCursor()) {
        result = executeForCursor(sqlSession, args);
      } else {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = sqlSession.selectOne(command.getName(), param);
      }
    } else if (SqlCommandType.FLUSH == command.getType()) {
        result = sqlSession.flushStatements();
    } else {
      throw new BindingException("Unknown execution method for: " + command.getName());
    }
    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
      throw new BindingException("Mapper method '" + command.getName() 
          + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
    }
    return result;
  }

// 返回单个值的结果
  private Object rowCountResult(int rowCount) {
    final Object result;
    if (method.returnsVoid()) {
      result = null;
    } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
      result = Integer.valueOf(rowCount);
    } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
      result = Long.valueOf(rowCount);
    } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
      result = Boolean.valueOf(rowCount > 0);
    } else {
      throw new BindingException("Mapper method '" + command.getName() + "' has an unsupported return type: " + method.getReturnType());
    }
    return result;
  }
}


```

> https://blog.csdn.net/u010890358/article/details/80665753

