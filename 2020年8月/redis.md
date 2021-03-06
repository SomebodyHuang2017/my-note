# Redis 是什么？能干啥？
REmote DIctionary Server(Redis) 是一个由Salvatore Sanfilippo写的key-value存储系统。
Redis是一个开源的使用ANSI C语言编写、遵守BSD协议、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。
它通常被称为数据结构服务器，因为值（value）可以是 字符串(String), 哈希(Hash), 列表(list), 集合(sets) 和 有序集合(sorted sets)等类型。

# Redis 数据类型
Redis支持五种常见的数据类型：string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)。

## String（字符串）
string 是 redis 最基本的类型，你可以理解成与 Memcached 一模一样的类型，一个 key 对应一个 value。
string 类型是二进制安全的。意思是 redis 的 string 可以包含任何数据。比如jpg图片或者序列化的对象。
string 类型是 Redis 最基本的数据类型，string 类型的值最大能存储 512MB。

## Hash（哈希）
Redis hash 是一个键值(key=>value)对集合。
Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。
每个 hash 可以存储 2^32 - 1 键值对（40多亿）。

## List（列表）
Redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）。
每个 list 可以存储 2^32 - 1 键值对（40多亿）。

## Set（集合）
Redis 的 Set 是 string 类型的无序集合。
集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
集合中最大的成员数为 2^32 - 1(4294967295, 每个集合可存储40多亿个成员)。

## zset(sorted set：有序集合)
Redis zset 和 set 一样也是string类型元素的集合,且不允许重复的成员。
不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
zset的成员是唯一的,但分数(score)却可以重复。

## Redis 支持多个数据库吗？
注意：Redis支持多个数据库，并且每个数据库的数据是隔离的不能共享，并且基于单机才有，如果是集群就没有数据库的概念。
Redis是一个字典结构的存储服务器，而实际上一个Redis实例提供了多个用来存储数据的字典，客户端可以指定将数据存储在哪个字典中。这与我们熟知的在一个关系数据库实例中可以创建多个数据库类似，所以可以将其中的每个字典都理解成一个独立的数据库。
每个数据库对外都是一个从0开始的递增数字命名，Redis默认支持16个数据库（可以通过配置文件支持更多，无上限），可以通过配置databases来修改这一数字。客户端与Redis建立连接后会自动选择0号数据库，不过可以随时使用SELECT命令更换数据库，如要选择1号数据库：

```text
redis> SELECT 1
OK
redis [1] > GET foo
(nil)
```

然而这些以数字命名的数据库又与我们理解的数据库有所区别。首先Redis不支持自定义数据库的名字，每个数据库都以编号命名，开发者必须自己记录哪些数据库存储了哪些数据。
另外Redis也不支持为每个数据库设置不同的访问密码，所以一个客户端要么可以访问全部数据库，要么连一个数据库也没有权限访问。
最重要的一点是多个数据库之间并不是完全隔离的，比如FLUSHALL命令可以清空一个Redis实例中所有数据库中的数据。
综上所述，这些数据库更像是一种命名空间，而不适宜存储不同应用程序的数据。比如可以使用0号数据库存储某个应用生产环境中的数据，使用1号数据库存储测试环境中的数据，
但不适宜使用0号数据库存储A应用的数据而使用1号数据库B应用的数据，不同的应用应该使用不同的Redis实例存储数据。
由于Redis非常轻量级，一个空Redis实例占用的内在只有1M左右，所以不用担心多个Redis实例会额外占用很多内存。

# Redis 命令

## 连接服务器
启动本地 redis 客户端：
启动 redis 服务器，打开终端并输入命令 redis-cli，该命令会连接本地的 redis 服务。

在远程服务上执行命令：`redis-cli -h host -p port -a password`

## Redis 键(key)
* DEL key：该命令用于在 key 存在时删除 key。
* DUMP key：序列化给定 key ，并返回被序列化的值。
* EXISTS key：判断 key 是否存在。
* EXPIRE key seconds：给 key 设置过期时间。
* EXPIREAT key timestamp：给 key 设置绝对的过期时间（时间戳）。
* PEXPIRE key millis：设置 key 的过期时间以毫秒计。
* KEYS pattern：查找所有符合给定模式( pattern)的 key 。
* MOVE key db：将当前数据库的 key 移动到给定的数据库 db 当中。
* PERSIST key：移除 key 的过期时间，key 将持久保持。
* TTL key：以秒为单位返回 key 的剩余过期时间。
* PTTL key：以毫秒为单位返回 key 的剩余的过期时间。
* RANDOMKEY：从当前数据库中随机返回一个 key 。
* RENAME key newkey：修改 key 的名称。
* RENAMENX key newkey：仅当 newkey 不存在时，将 key 改名为 newkey 。
* TYPE key：返回 key 所储存的值的类型。
* SCAN cursor \[MATCH pattern\] \[COUNT count\]：迭代数据库中的数据库键。

## Redis 字符串（String）
* SET key value：设置指定 key 的值
* GET key：获取指定 key 的值。
* GETRANGE key start end：返回 key 中字符串值的子字符。
* GETSET key value：将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
* GETBIT key offset：对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
* MGET key1 \[key2..\]：获取所有(一个或多个)给定 key 的值。
* SETBIT key offset value：对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
* SETEX key seconds value：将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
* SETNX key value：只有在 key 不存在时设置 key 的值。
* SETRANGE key offset value：用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。
* STRLEN key：返回 key 所储存的字符串值的长度。
* MSET key value \[key value ...\]：同时设置一个或多个 key-value 对。
* MSETNX key value \[key value ...\]：同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
* PSETEX key milliseconds value：这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。
* INCR key：将 key 中储存的数字值增一。
* INCRBY key increment：将 key 所储存的值加上给定的增量值（increment）。
* INCRBYFLOAT key increment：将 key 所储存的值加上给定的浮点增量值（increment）。
* DECR key：将 key 中储存的数字值减一。
* DECRBY key decrement：key 所储存的值减去给定的减量值（decrement）。
* APPEND key value：如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾。

## Redis 哈希（Hash）
* HDEL key field1 \[field2\]：删除一个或多个哈希表字段
* HEXISTS key field：查看哈希表 key 中，指定的字段是否存在。
* HGET key field：获取存储在哈希表中指定字段的值。
* HGETALL key：获取在哈希表中指定 key 的所有字段和值
* HINCRBY key field increment：为哈希表 key 中的指定字段的整数值加上增量 increment 。
* HINCRBYFLOAT key field increment：为哈希表 key 中的指定字段的浮点数值加上增量 increment 。
* HKEYS key：获取所有哈希表中的字段
* HLEN key：获取哈希表中字段的数量
* HMGET key field1 \[field2\]：获取所有给定字段的值
* HMSET key field1 value1 \[field2 value2 \]：同时将多个 field-value (域-值)对设置到哈希表 key 中。
* HSETNX key field value：只有在字段 field 不存在时，设置哈希表字段的值。
* HVALS key：获取哈希表中所有值。
* HSCAN key cursor \[MATCH pattern\] \[COUNT count\]：迭代哈希表中的键值对。

## Redis 列表（List）
* BLPOP key1 \[key2 \] timeout：移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
* BRPOP key1 \[key2 \] timeout：移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
* BRPOPLPUSH source destination timeout：从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
* LINDEX key index：通过索引获取列表中的元素
* LINSERT key BEFORE|AFTER pivot value：在列表的元素前或者后插入元素
* LLEN key：获取列表长度
* LPOP key：移出并获取列表的第一个元素
* LPUSH key value1 \[value2\]：将一个或多个值插入到列表头部
* LPUSHX key value：将一个值插入到已存在的列表头部
* LRANGE key start stop：获取列表指定范围内的元素
* LREM key count value：移除列表元素
* LSET key index value：通过索引设置列表元素的值
* LTRIM key start stop：对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
* RPOP key：移除列表的最后一个元素，返回值为移除的元素。
* RPOPLPUSH source destination：移除列表的最后一个元素，并将该元素添加到另一个列表并返回
* RPUSH key value1 \[value2\]：在列表中添加一个或多个值
* RPUSHX key value：为已存在的列表添加值

## Redis 集合（Set）
* SADD key member1 \[member2\]：向集合添加一个或多个成员
* SCARD key：获取集合的成员数。
* SDIFF key1 \[key2\]：返回第一个集合与其他集合之间的差异。
* SDIFFSTORE destination key1 \[key2\]：返回给定所有集合的差集并存储在 destination 中
* SINTER key1 \[key2\]：返回给定所有集合的交集
* SINTERSTORE destination key1 \[key2\]：返回给定所有集合的交集并存储在 destination 中
* SISMEMBER key member：判断 member 元素是否是集合 key 的成员
* SMEMBERS key：返回集合中的所有成员
* SMOVE source destination member：将 member 元素从 source 集合移动到 destination 集合
* SPOP key：移除并返回集合中的一个随机元素
* SRANDMEMBER key \[count\]：返回集合中一个或多个随机数
* SREM key member1 \[member2\]：移除集合中一个或多个成员
* SUNION key1 \[key2\]：返回所有给定集合的并集
* SUNIONSTORE destination key1 \[key2\]：所有给定集合的并集存储在 destination 集合中
* SSCAN key cursor \[MATCH pattern\] \[COUNT count\]：迭代集合中的元素 

## Redis 有序集合(sorted set)
* ZADD key score1 member1 \[score2 member2\]：向有序集合添加一个或多个成员，或者更新已存在成员的分数
* ZCARD key：获取有序集合的成员数
* ZCOUNT key min max：计算在有序集合中指定区间分数的成员数
* ZINCRBY key increment member：有序集合中对指定成员的分数加上增量 increment
* ZINTERSTORE destination numkeys key \[key ...\]：计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
* ZLEXCOUNT key min max：在有序集合中计算指定字典区间内成员数量
* ZRANGE key start stop \[WITHSCORES\]：通过索引区间返回有序集合指定区间内的成员
* ZRANGEBYLEX key min max \[LIMIT offset count\]：通过字典区间返回有序集合的成员
* ZRANGEBYSCORE key min max \[WITHSCORES\] \[LIMIT\]：通过分数返回有序集合指定区间内的成员
* ZRANK key member：返回有序集合中指定成员的索引
* ZREM key member \[member ...\]：移除有序集合中的一个或多个成员
* ZREMRANGEBYLEX key min max：移除有序集合中给定的字典区间的所有成员
* ZREMRANGEBYRANK key start stop：移除有序集合中给定的排名区间的所有成员
* ZREMRANGEBYSCORE key min max：移除有序集合中给定的分数区间的所有成员
* ZREVRANGE key start stop \[WITHSCORES\]：返回有序集中指定区间内的成员，通过索引，分数从高到低
* ZREVRANGEBYSCORE key max min \[WITHSCORES\]：返回有序集中指定分数区间内的成员，分数从高到低排序
* ZREVRANK key member：返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序
* ZSCORE key member：返回有序集中，成员的分数值
* ZUNIONSTORE destination numkeys key \[key ...\]：计算给定的一个或多个有序集的并集，并存储在新的 key 中
* ZSCAN key cursor \[MATCH pattern\] \[COUNT count\]：迭代有序集合中的元素（包括元素成员和元素分值）

# RDB
产生内存快照
save命令
bgsave命令

# AOF
always(每次)
每次写入操作均同步到AOF文件中，数据零误差，性能较低
everysec(每秒)
每秒将缓冲区的指令同步到AOF文件中，数据准确性较高，性能较高
在系统突然宕机的情况下丢失1秒内的数据
no(系统控制)
由操作系统每次同步到AOF文件的周期，整体过程不可控

## AOF功能开启
appendonly yes|no
## 作用
是否开启AOF持久化功能，默认为不开启状态
## 配置
appendfsync always|everysec|no
## 作用
AOF写数据策略
## AOF重写
随着命令不断写入AOF，文件会越来越大，为了解决这个问题，Redis引入了AOF重写机制压缩文件体积。AOF文件重写是将Redis进程内的数据转化为写命令同步到新AOF文件的过程。简单来说就是将对同一个数据的若干条命令执行结果转化成最终结果数据对应的指令进行记录。
## AOF重写方式
1. 手动重写
bgrewriteaof

2. 自动重写
auto-aof-rewrite-min-size size
auto-aof-rewrite-percentage percentage

## RDB vs AOF

持久化|RDB|AOF
---|---|---
占用存储空间|小（数据级：压缩）|大（指令级：重写）
存储速度|慢|快
数据安全性|会丢数据|依据策略决定
资源消耗|高/重量级|低/轻量级
启动优先级|低|高


# redis事务的基本操作
## 开启事务
multi
## 作用
设定事务的开启位置，此指令执行后，后续的所有指令均加入到事务中
## 执行事务
exec
## 作用
设定事务的结束位置，同时执行事务。与multi成对出现，成对使用
## 取消事务
discard

## 事务的注意事项
定义事务的过程中，命令格式输入错误怎么办?
* 语法错误：指命令书写格式有误
* 处理结果：如果定义的事务中所包含的命令存在语法错误，整体事务中所有命令均不会执行。包括那些语法正确的命令。

## 定义事务的过程中，命令执行出现错误怎么办？
* 运行错误：指命令格式正确，但是无法正确的执行，例如对list进行incr操作
* 处理结果：能够正确运行的命令会执行，运行错误的命令不会执行

注意：已经执行完毕的命令对应的数据不会自动回滚，需要程序员自己在代码中实现回滚。

# 锁
基于特定条件的事务执行 - 锁
## 解决方案
* 对key添加监视锁，在执行exec前如果key发生了变化，终止事务执行
`watch key1 key2...`
* 取消对所有key的监视
`unwatch`

基于特定条件的事务执行 - 分布式锁
## 解决方案
* 使用setnx设置一个公共锁
`setnx lock-key value`
利用setnx命令的返回值特征，有值返回设置失败，无值则返回设置成功

* 对于返回设置成功的，拥有控制权，进行下一步的具体业务操作
* 对于返回设置失败的，不具有控制权，排队或等待

操作完毕通过del操作释放锁

# 数据删除策略
1. 定时策略
2. 惰性删除
 

