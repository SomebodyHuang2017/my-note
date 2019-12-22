端口占用分配

dubbo端口：
rbac-service: 20880
sso-service: 20881
personal-service: 20882
notice-service: 20883
message-service: 20884
question-service: 20885
search-service: 20886

浏览器访问端口：
rbac-web: 8082
sso-web: 8081

tomcat的dubbo-admin端口：8080

想法：热门部门id可以用redis缓存起来，减少服务器count查询的压力。在管理员通过审核的时候可以对值进行增加。
数据结构用zset

想法：公告列表通过数据库查询，公告详情页面静态化，公告元数据缓存到hash中
      -|是否可以变成zset？不存content，不行，增删改都不方便，就存读取数据库


@annotation.AccessDecriptor 访问描述注解
AccessExceptionHandler 访问异常类 ControllerAdvice
AccessProcessingAspect 访问注解切面类
#### tracer用来唯一标识一次访问信息
{
    生成tracer，放入tracer在相应体
    明确AccessDecriptor是标注在方法上还是参数上
    如果是参数上并且auth()标识为false则
        --|授权通过
        --|进行权限验证
                获取cookie信息
                cookie信息不存在则直接返回失败
                否则获取SSRX_TICKET键值
                如果值为空则返回
                RestResult加入ticket字段？？？
                通过ticket获取用户缓存信息
                缓存信息获取为空，说明用户登录已过期，清除cookie，清除缓存？？返回未授权
                如果存在则延期票据过期时间，延长票据cookie过期时间
                RestResult加入用户id???
                如果feature为0，说明只需要验证登录状态，直接返回成功
                拿到用户所有权限，开始验证用户权限
                如果用户权限为空，则直接返回无权限
                一一验证用户权限集合是否包含当前验证的权限，如果包含则成功，否则失败
    放入tracer
    记录访问请求日志 参数(注解，连接点，request，权限验证结果)
        判断注解是否为空，且注解的autitLog()是否打开，如果打开则记录审计日志
            --|
        判断注解是否为空，且注解的accessLog()是否打开，如果打开则记录访问日志
            --|
    判断权限校验返回结果，成功则继续，失败直接返回，方法结束
    判断注解是否为null，且（全局signature是否打开 或 是否开启sign())，如果打开则进行签名验证doVerify(request)，签名验证失败则返回
    运行被切方法，获得返回结果
    如果返回结果是RestResult类型，则放入tracer
    记录访问相应日志 参数(注解，返回值)
        判断注解是否为空，且注解的autitLog()是否打开，如果打开则记录审计日志
            --|
        判断注解是否为空，且注解的accessLog()是否打开，如果打开则记录访问日志
            --|
    返回






}


## 论文描述jsonp的原理，

## 有意思，新东西，fetch

## docker
