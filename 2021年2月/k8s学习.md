
k8s学习

1. 了解k8s是什么，了解k8s能干什么？

Kubernetes 是一个可移植的、可扩展的开源平台，用于管理容器化的工作负载和服务，可促进声明式配置和自动化。 Kubernetes 拥有一个庞大且快速增长的生态系统。Kubernetes 的服务、支持和工具广泛可用。

Kubernetes 为你提供：
* 服务发现和负载均衡
* 存储编排
* 自动部署和回滚
* 自动完成装箱计算
* 自我修复
* 密钥与配置管理

参考：https://kubernetes.io/zh/docs/concepts/overview/what-is-kubernetes/


2. k8s的基础组件和概念？

控制平面组件：
* kube-apiserver API 服务器是 Kubernetes 控制面的组件， 该组件公开了 Kubernetes API。 API 服务器是 Kubernetes 控制面的前端。
* etcd etcd 是兼具一致性和高可用性的键值数据库，可以作为保存 Kubernetes 所有集群数据的后台数据库。
* kube-scheduler 控制平面组件，负责监视新创建的、未指定运行节点（node）的 Pods，选择节点让 Pod 在上面运行。
* kube-controller-manager 在主节点上运行 控制器 的组件。
* cloud-controller-manager 云控制器管理器是指嵌入特定云的控制逻辑的 控制平面组件。 云控制器管理器允许您链接聚合到云提供商的应用编程接口中， 并分离出相互作用的组件与您的集群交互的组件。

Node组件：
* kubelet 一个在集群中每个节点（node）上运行的代理。 它保证容器（containers）都 运行在 Pod 中。
* kube-proxy kube-proxy 是集群中每个节点上运行的网络代理， 实现 Kubernetes 服务（Service） 概念的一部分。
* 容器运行时（Container Runtime） 容器运行环境是负责运行容器的软件。

插件（Addons）
* DNS
* Web 界面（仪表盘）
* 容器资源监控
* 集群层面日志

参考：https://kubernetes.io/zh/docs/concepts/overview/components/


3. 