资源清单：资源 掌握资源清单的语法 编写pod 掌握pod生命周期***
Pod控制器：掌握各种控制器的特定以及定义使用方式
服务发现：掌握svc原理以及构建方式
存储：掌握多种存储类型的特点 并且能够在不同的环境中选择合适的存储方案（有自己的见解）
调度器：掌握调度器原理 并且能够根据要求把pod定义到自己想运行的节点
安全：集群认证 鉴权 访问控制 原理以及流程
HELM：掌握helm原理 helm模版 helm模版自定义 helm部署常用的插件
运维：修改kubeadm 能够构建高可用的kubernetes集群
 
* api server：所有服务访问统一入口
* controller manager：维护副本期望数
* scheduler：负责介绍任务，选择合适的节点进行分配任务
* ETCD：键值对数据库 存储k8s集群所有重要信息（持久化）
* Kubelet：直接跟容器引擎交互实现容器的生命周期管理
* Kube-proxy：负责写入规则至 IPTABLES、IPVS 实现服务访问映射
* CoreDNS：可以为集群中的SVC创建一个域名IP的对应关键解析
* Dashboard：给k8s集群提供一个B/S结构访问体系
* Ingress controller：官方只能实现四层代理，INGRESS 可以实现七层代理
* Federation：提供一个可以跨集群中心多k8s统一管理功能
* Prometheus：普罗米修斯 提供k8s集群的监控能力
* ELK：提供k8s集群日志统一分析接入平台

同一个pod内部通讯，同一个pod共享一个网络命名空间，共享一个linux协议栈。

pod1至pod2
* pod1与pod2在同一台机器，由docker0网桥直接转发请求至pod2，不需要经过flannel
* pod1与pod2不在同一台机器，pod的地址是与docker0在同一个网段的，但docker0网段与宿主机网卡是两个完全不同的IP网段，并且不同的Node之间的网络通讯只能通过宿主机的物理网卡进行。将pod的ip关联起来，通过这个关联让pod可以互相访问。

pod至service的网络：目前基于性能考虑，全部为iptables(新的通过LVS)维护和转发

pod到外网：pod向外网发送请求，查找路由表，转发数据包到宿主机的网卡，宿主网卡完成路由选择后，iptables执行masquerade，把源ip更改为宿主机网卡的ip，然后向外网服务器发送请求。

外网访问pod：service