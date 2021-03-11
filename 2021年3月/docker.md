# Docker

## Docker是什么
Docker的三个基本概念：
1. 镜像（Image）：Docker 镜像（Image），就相当于是一个 root 文件系统。比如官方镜像 ubuntu:16.04 就包含了完整的一套 Ubuntu16.04 最小系统的 root 文件系统。
2. 容器（Container）：镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。
3. 仓库（Repository）：仓库可看成一个代码控制中心，用来保存镜像。

Docker 使用客户端-服务器 (C/S) 架构模式，使用远程API来管理和创建Docker容器。
Docker 容器通过 Docker 镜像来创建。
容器与镜像的关系类似于面向对象编程中的对象与类。

![docker架构](./pic/docker.png)

> https://www.runoob.com/docker/docker-architecture.html

## Docker Hello world 初探

```shell
docker run ubuntu:15.10 /bin/echo "Hello world"
```
参数解析：
* docker: Docker 的二进制执行文件。
* run: 与前面的 docker 组合来运行一个容器。
* ubuntu:15.10 指定要运行的镜像，Docker 首先从本地主机上查找镜像是否存在，如果不存在，Docker 就会从镜像仓库 Docker Hub 下载公共镜像。
* /bin/echo "Hello world": 在启动的容器里执行的命令

以上命令完整的意思可以解释为：Docker 以 ubuntu15.10 镜像创建一个新容器，然后在容器里执行 bin/echo "Hello world"，然后输出结果。


```shell
docker run -i -t ubuntu:15.10 /bin/bash
# -t: 在新容器内指定一个伪终端或终端。
# -i: 允许你对容器内的标准输入 (STDIN) 进行交互。
```

## Docker 容器使用
```shell
docker pull ubuntu # 拉取镜像
docker run -it ubuntu /bin/bash # 启动容器
docker ps -a # 查看docker进程
docker logs 2b1b7a428627 # 查看容器内的标准输出
docker stop 2b1b7a428627 # 停止容器
docker start b750bbbcfd88  # 使用 docker start 启动一个已停止的容器
docker restart <容器 ID> # 重启容器

# 进入容器
docker attach <容器 ID>
docker exec # 推荐大家使用 docker exec 命令，因为此退出容器终端，不会导致容器的停止。

# 导出和导入容器
# 1. 导出容器
docker export 1e560fca3906 > ubuntu.tar

# 2. 导入容器快照
cat docker/ubuntu.tar | docker import - test/ubuntu:v1
docker import http://example.com/exampleimage.tgz example/imagerepo

docker rm -f 1e560fca3906 # 删除容器

docker pull training/webapp  # 载入镜像
docker run -d -P training/webapp python app.py # 运行容器 -d:让容器在后台运行。-P:将容器内部使用的网络端口随机映射到我们使用的主机上。

docker port bf08b7f2cd89 # 查看容器端口的映射情况
docker top wizardly_chandrasekhar # 查看容器内部运行的进程
docker inspect wizardly_chandrasekhar # 查看 Docker 的底层信息

```

## Docker 镜像使用
当运行容器时，使用的镜像如果在本地中不存在，docker 就会自动从 docker 镜像仓库中下载，默认是从 Docker Hub 公共镜像源下载。
下面我们来学习：
1、管理和使用本地 Docker 主机镜像
2、创建镜像

```shell
docker images # 列出本地主机上的镜像
docker pull ubuntu:13.10  # 获取一个新的镜像
docker search httpd # 查找镜像
docker rmi hello-world # 删除镜像
docker commit -m="has update" -a="runoob" e218edb10161 runoob/ubuntu:v2 # 更新镜像 
# 各个参数说明：
  # -m: 提交的描述信息
  # -a: 指定镜像作者
  # e218edb10161：容器 ID
  # runoob/ubuntu:v2: 指定要创建的目标镜像名
  
docker build -t runoob/centos:6.7 . # 构建镜像 -t ：指定要创建的目标镜像名 . ：Dockerfile 文件所在目录，可以指定Dockerfile 的绝对路径
docker tag 860c279d2fec runoob/centos:dev # 设置镜像标签
```

## Docker 容器连接

## Docker 仓库管理
仓库（Repository）是集中存放镜像的地方。以下介绍一下 Docker Hub。当然不止 docker hub，只是远程的服务商不一样，操作都是一样的。

## Docker Dockerfile
### 什么是 Dockerfile？
Dockerfile 是一个用来构建镜像的文本文件，文本内容包含了一条条构建镜像所需的指令和说明。

> https://www.runoob.com/docker/docker-dockerfile.html

### Dockerfile 指令

+ **FROM** 指定基础镜像
+ **RUN** RUN指令将在当前镜像顶部的新层中执行任何命令，并提交结果
+ **CMD** CMD的主要目的是为执行中的容器提供默认值
+ **LABEL** LABEL指令将元数据添加到图像
+ **EXPOSE** EXPOSE指令通知Docker容器在运行时监听指定的网络端口
+ **ENV** ENV指令将环境变量<key>设置为值<value>
+ **ADD** ADD指令从<src>复制新文件，目录或远程文件URL，并将它们添加到映像的文件系统中的路径<dest>
+ **COPY** COPY指令从<src>复制新文件或目录，并将它们添加到容器的文件系统中，路径为<dest>
+ **ENTRYPOINT** 格式和 RUN 指令格式一样，目的和 CMD 一样，都是在指定容器启动程序及参数
+ **VOLUME** VOLUME指令创建具有指定名称的安装点，并将其标记为保存来自本机主机或其他容器的外部安装的卷
+ **USER** USER指令设置运行映像时要使用的用户名（或UID）以及可选的用户组（或GID），以及Dockerfile中跟随该映像的所有RUN，CMD和ENTRYPOINT指令
+ **WORKDIR** WORKDIR指令为Dockerfile中跟随它的所有RUN，CMD，ENTRYPOINT，COPY和ADD指令设置工作目录
+ **ARG** ARG指令定义了一个变量，用户可以在构建时使用--build-arg <varname> = <value>标志使用docker build命令将其传递给构建器
+ **ONBUILD** 当映像用作另一个构建的基础时，ONBUILD指令会在映像中添加一条触发指令，以便稍后执行
+ **STOPSIGNAL** STOPSIGNAL指令设置将被发送到容器退出的系统调用信号
+ **HEALTHCHECK** HEALTHCHECK指令告诉Docker如何测试容器以检查其是否仍在工作
+ **SHELL** SHELL指令允许覆盖用于命令的shell形式的默认shell

> [docker---Dockerfile编写](https://www.cnblogs.com/zpcoding/p/11450686.html)

> [Dockerfile reference](https://docs.docker.com/engine/reference/builder/#from)

## Docker 私有仓库搭建
### 前提
本地有个docker环境
### 搭建过程
* 1、拉取 `docker镜像仓库` 镜像：`docker pull registry`
* 2、创建容器并映射端口：`docker run -d -p 5000:5000 -v /tmp/data/registry:/tmp/registry --name registry registry` （其中，-v /tmp/data/registry表示挂载该路径到 /tmp/registry）

好了，私有仓库就搭建好了，可以通过 http://localhost:5000/v2/_catalog 访问仓库啦。

### 上传镜像到私有仓库
示例：
* 拉取镜像：`docker pull busybox`
* 修改镜像tag：`docker tag busybox 127.0.0.1:5000/busybox`
* 上传到私有仓库：`docker push 127.0.0.1:5000/busybox`
* 下载镜像：`docker pull 127.0.0.1:5000/nginx:latest`

> [Docker 仓库](https://www.jianshu.com/p/e97393e5ea32)
