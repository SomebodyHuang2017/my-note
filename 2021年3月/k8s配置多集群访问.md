# 使用kubectl配置多集群访问
kubectl作为一个客户端，是可以连接任意的k8s集群的。
它怎么发现k8s集群？怎么连接k8s集群？怎么操作k8s集群呢？
原因就在 `kubeconfig` 文件中，用于配置集群访问的文件有时被称为 `kubeconfig` 文件。
这是一种引用配置文件的通用方式，并不意味着存在一个名为 `kubeconfig` 的文件。

## 当前环境和背景
本地启动了一个minikube，腾讯云也有一个TKE，安装minikube后kubectl默认是连接本地的minikube，现在想连接腾讯云的k8s怎么搞？


## 前情概要
本地安装了minikube后，会在用户空间下自动创建一个文件夹：`.kube`
`.kube` 文件夹下面有三样东西：`cache`（文件夹），`config`（文件），`http-cache`（文件夹）
而`config`就是默认创建的 `kubeconfig` 文件，其内容如下：

```yaml
apiVersion: v1
clusters:
- cluster:
    certificate-authority-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUM1ekNDQWMrZ0F3SUJBZ0lCQURBTkJna3Foa2lHOXcwQkFRc0ZBREFWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVnpNQjRYRFRJeE1ETXdOVEE1TlRNeE0xb1hEVE14TURNd016QTVOVE14TTFvd0ZURVRNQkVHQTFVRQpBeE1LYTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDNoCnplbzYvU0JlcFFYSEtBbFloWUtXUWorS1MvMzl3L3FkTGRnQjdTUzMyZ0RBSVdOVkhTTTI4alhaaHl5ZjhndEEKMU1EL0tFaWNEQUV1aW9pdE1nWnVodE5KMGswM2FIVWtMVEFhYktaQUpaaCtERHlPNlp0OVVJZU1EZUFQWk5oZgp4VkZVMmRHTTZDcVRUVEpuWEFnUEdMOW9UbnAyZU0rbFJYU3pWb3oyV2drdzBqTWg4ejlxeHY4SDFKc2J5YUpSCjhML2pRWVBhSWx1S2NlSThWUnRvYk8rTEJjUWVvdHlJbGtqSEZ3dHVENDFxZE9YeDVDa3k1dFRQRndsOU5iOEoKMERHZUJGSWdScTVvTE5kbmRKT0xBUllhRUJPMFU1NEJOamxVZ2xXV2pER0Z4MGVJWFhKKzNENENGaGNlbXBsTApKRExUSFU0dlRNdXplUXlNM1BNQ0F3RUFBYU5DTUVBd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4R0ExVWRFd0VCCi93UUZNQU1CQWY4d0hRWURWUjBPQkJZRUZIMmZVaURrZ2ZuTWxxQ0RReDJMNDA5VTNvY3hNQTBHQ1NxR1NJYjMKRFFFQkN3VUFBNElCQVFDbVdRMWxzejRMWGtjQ3Vac1c1V1NNR2dnWlFCd21mZlBVTnRFSnp6R3dUclJOdzEzUAp4YTZVSTI3dGdkQWo3TDlTZFFzYkR6eWxCZEhmczhtMkNtRjB1eW9QSjZNZGg3cmRCQXBuSVZKaS9yUWtHZUtRClVSOCtOVXluMGdQcTRrN2c3c0ExSy9ISC9FUWk0WjYveGtuRjl0NVNoalRsVURKajMyMDl2am05T2gzR28wUG0KbTJSYzBHbG1OaU84cjY0RXgwTVNaU0ttSy81bE9WNm9XRjl3cU5PVEM0dUYzeXYvdkgxd3VSb2ZsUjBhVWxXZQpyMUh0UnhnSFpiK215cTNzdzljQWV2aUczS0xuQktHTUxTd2k1OTlSNVdNOE90NWZPYWlHU0pDVXNkOGlHbEROCjFOdmdubUFFRzJnallDWUVaWHlzKzhBeGJxOHlUQ2ovakduUwotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==
    server: https://kubernetes.docker.internal:6443
  name: docker-desktop
- cluster:
    certificate-authority: /Users/somebody/.minikube/ca.crt
    server: https://127.0.0.1:62700
  name: minikube
contexts:
- context:
    cluster: docker-desktop
    user: docker-desktop
  name: docker-desktop
- context:
    cluster: minikube
    user: minikube
  name: minikube
current-context: minikube
kind: Config
preferences: {}
users:
- name: docker-desktop
  user:
    client-certificate-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURGVENDQWYyZ0F3SUJBZ0lJZlZCMno3Mm5MV1l3RFFZSktvWklodmNOQVFFTEJRQXdGVEVUTUJFR0ExVUUKQXhNS2EzVmlaWEp1WlhSbGN6QWVGdzB5TVRBek1EVXdPVFV6TVROYUZ3MHlNakF6TURnd09URXhNREJhTURZeApGekFWQmdOVkJBb1REbk41YzNSbGJUcHRZWE4wWlhKek1Sc3dHUVlEVlFRREV4SmtiMk5yWlhJdFptOXlMV1JsCmMydDBiM0F3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRRFU1NGcwdHpSN2d1V08KcGgzcEFQNVYvOHk4RnFodWU0dS9ZRFRlQlhlalFvSStlZEVJQmVnYUxhODM5Q1ArTlROQ0laMFhlTXViOWJZeQpjYW14SlplRU1rNnJZM1dmYjBPMWVSYVAvZTlWb0IxYlA3ZjY5dmVZd0RGOXU5OGVpWGl5WmlxSXdFcy85YnpRCnJtNVZzWGcvR0pTeEswNmdpejFvTUtmM0FwMmRick5MMEtaNmV1MXM5NGlZeXc5bjlaaGEzU29wSndrcFFnbzYKOVhwUmVoQ3BPNGlvSi8xZ2VKVUxBTkR6bFFFVmwxNnhsUnpEdnBoY2xYUUxESXNQTGZubHlTUXF1R0tSbTUwMwpTeFkvYzVqNzdvWHJIME54b3I0RzNrNGdqR2ludnNRQjA0Y1NZUkJ5ZmN1SFord1IvU05ITkJBc01yOWVUNG9rCjJLWFFTWUgzQWdNQkFBR2pTREJHTUE0R0ExVWREd0VCL3dRRUF3SUZvREFUQmdOVkhTVUVEREFLQmdnckJnRUYKQlFjREFqQWZCZ05WSFNNRUdEQVdnQlI5bjFJZzVJSDV6SmFnZzBNZGkrTlBWTjZITVRBTkJna3Foa2lHOXcwQgpBUXNGQUFPQ0FRRUFLekJhekk0bEZETWtLbFdybGpzUmFYblRrNk9aWXZDU2xZY2w4aFlpTGVCTnZ6NEZnY2dDClRwYUxpZFZqZStIbUY5bnAvT2ZheW9WMExsTjJndmYzQzdsZ1J2N2tFWm5qMkJuTkhVcjUrYmxJd0JsRGJNc3gKb0Q4Nm9hcEdMNU1UNUttamxJWkhNSUFTVHFneFBnblpGMEkxYkc4SkpXeXA4TXZjOXJHOWREWk1qQURvNW8yegpwbVZEbmQvbXFJaUxaK25XdnYzY3BmNDI1YTh4TCtESXZQVWdUdHd4TUZYNlRrMURFVW53M0JmK2pnSDY4NklRCndFLzFiL0dsT3U0KzBiV1ltdmoyaVUvQVhlRGFPRWtQcjRqcnhJSC9ZcEhoc1Jpd2I2WkNrMjVVcGx2TDh1Z20KZGRuSXduU2FUUEU2SlNpdFo5QkpRcWtKTlo5aVV0NGJrdz09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K
    client-key-data: LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBMU9lSU5MYzBlNExsanFZZDZRRCtWZi9NdkJhb2JudUx2MkEwM2dWM28wS0NQbm5SCkNBWG9HaTJ2Ti9Rai9qVXpRaUdkRjNqTG0vVzJNbkdwc1NXWGhESk9xMk4xbjI5RHRYa1dqLzN2VmFBZFd6KzMKK3ZiM21NQXhmYnZmSG9sNHNtWXFpTUJMUC9XODBLNXVWYkY0UHhpVXNTdE9vSXM5YURDbjl3S2RuVzZ6UzlDbQplbnJ0YlBlSW1Nc1BaL1dZV3QwcUtTY0pLVUlLT3ZWNlVYb1FxVHVJcUNmOVlIaVZDd0RRODVVQkZaZGVzWlVjCnc3NllYSlYwQ3d5TER5MzU1Y2trS3JoaWtadWROMHNXUDNPWSsrNkY2eDlEY2FLK0J0NU9JSXhvcDc3RUFkT0gKRW1FUWNuM0xoMmZzRWYwalJ6UVFMREsvWGsrS0pOaWwwRW1COXdJREFRQUJBb0lCQUZzRFgwYXB5dFhCNzBBNwo0eTFvTDFHME9HRnZqakVzdjJEZ1Q3bFp2UHNkdzIvUnczb2FqRTRrbVhMaCswRUFuUmo0SlorVEUvZG40cXROCnk2emUxUmErS3NNNWtWN0d3YnNpT0hMMXF3cC9tQTBhQUJ4WTF4K0ZpYUttWFl0QVdVaHFSR1NSVDVuWUF3OXkKZ2RoQU5aZERlczBDSm1xYkczVWUxNjdRZjNCOHlpMzhCYVpIbTkySjhreTA1L0J0VGlCUVN5eHZiZi9TMUtjRgpORUFCNmt0c0ZGTUp1aENHQUlTb2FPUXdkYXFEelhyUDhIdkJDdkozYUdlM0hhTXkwQ2FMQVlZZS9tZFpINTZNCnZGT2o0ZWw5cEc5SDlEOGZQZlRPNXgzdmZlVHhNNE5KNk5KdGZEQjFSdVV5NlRacS8zdDZWcmF2dlNBL0paT2kKdDR0MWx3RUNnWUVBK3B1ZXhWK0pMRW5oWU9LNTI0MXNmbmZzQ3RYaTBtM25sQWQ0aStnY3U2U0FEbGQ0cFEzegpLbDJ1NWYwdVRQMk9ZWlBHNE5GekNuWEtFUDJvcG1OS2JRNUxPWG45U0x5aTAveVlEem1FT1QzYk04bXhVS3poCnBKRzhZNE9yYnJZL0FGc1RvNGZaQ2JCWTJ1SlRBWStWODgrM1g2TzZRQThBWUJVWFQ1eElLVGtDZ1lFQTJYdzgKaE5TbGF0U3JTR1pTT0ZaU0pHOVBkc2kzVTJUd3QzYW1wR0s2cVVGWkZnZFBLNk1UOUUxVWJQZWhyZHBsd1FvOAo4OE1PMGJVUWNQTVVCSmh3bXNxcm1yVWN1UHoyWWhZdW5YWTNacU5URlh3Ym1yQ3hWaGd0TmhUL0lkRG5mWlJhCkJYNVd4TmNKcmVHRkVWQmRDeVpCVDd6bDk4ZlFXcE5DNm5nRDlLOENnWUVBczZlNFRIcE9saWRvb05Jb3R4Sk4KZ3VScG5PTDBOakovMzRqempDaWI0SkR2RkZqMHJpNnY4ZUV6THNFdjNoV3ZhcGhMN0lONld6ZFI0cGhWdk9KVgpTcFVDckhPZGRmVjFMVTlabXlCNmY2YXViQlBkUDU2UUxEQWx3NWx5M20xY0FOOUoxdVBlWGFuRUtUWXlsMTNkCisra2U1anN6bkJTbENqeFVkOGZybTFFQ2dZQmYyVHdHd256Z2x6ZWhvYzZzbTRaNkhrQmtObE50UW5oQU10K0EKWlM2a3QzTWVuVHdNSEVRSUVDaWNHWXE4eHhxL045YmpDMTAxbU5uVkhadjk1bjBDQ3o0VDRhdjE3eTVhempIZgpIYUdPNzM0SFl2bjdjOUhFQXNXeUp4REdBMzV5UHZacG8yTjBQdDA2TDhEb2Y0VnlDM3NQUEU0UnBKTXp4K0RlCjJ1UExhd0tCZ0ZuQlUwcDVsMHF2RzgzcFI0a3hNTEJGMXl4NmNsWTd2RWFmb1RmaVBmMldXL2hsNXpwdHJUcCsKZzI3VGhkQ0d3SVZYUml4VzFXZS9JN2RQeEYyWkw0T253bU9wbzhWRFZDK3crMk9wRE9Qei9FN2l6ZVVCNzhsWQpIMWdEN0dmRUt4cFdqMERIcUFpZHVxRXg3QmI3ZW9xK1gxNTU2QW9oNGU2RnFTSlE1Q1BMCi0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0tCg==
- name: minikube
  user:
    client-certificate: /Users/somebody/.minikube/profiles/minikube/client.crt
    client-key: /Users/somebody/.minikube/profiles/minikube/client.key

```

可以看到有三层结构：
* 定义集群 
* 用户
* 上下文

## 如何将kubectl切换到腾云讯TKE？
1、查看 KUBECONFIG 环境变量
```shell
echo $KUBECONFIG
```
如果输出为空，则定义环境变量：
```shell
export KUBECONFIG=$HOME/.kube/config
```
2、去腾云讯TKE平台下载kubeconfig文件
3、下载好kubeconfig文件后，放到一个地方，并且将这个文件追加到 $KUBECONFIG 环境变量中去
```shell
export KUBECONFIG=$KUBECONFIG:/path/to/xxx-config
# 例如
export KUBECONFIG=$KUBECONFIG:$HOME/Downloads/cls-8t9vedvf-config
```
3、操作完上一步后我们可以看下现在环境变量内容是啥
```shell
echo $KUBECONFIG
# 输出
# /Users/somebody/.kube/config:/Users/somebody/Downloads/cls-8t9vedvf-config
```
4、查看当前kubeconfig配置上下文
```shell
kubectl config get-contexts
# 输出
# CURRENT   NAME                                        CLUSTER          AUTHINFO         NAMESPACE
# *        cls-8t9vedvf-100015397713-context-default   cls-8t9vedvf     100015397713
#          docker-desktop                              docker-desktop   docker-desktop
#          minikube                                    minikube         minikube

# 加了 * 就是当前选中的上下文
```
5、通过命令切换上下文
```shell
kubectl config use-context <上下文名称>
# 例如
# kubectl config use-context minikube
```
恭喜你掌握了切换集群的办法 🎉

## 参考
> [kubectl 配置对多集群的访问](https://kubernetes.io/zh/docs/tasks/access-application-cluster/configure-access-multiple-clusters/)

> [使用 kubeconfig 文件组织集群访问](https://kubernetes.io/zh/docs/concepts/configuration/organize-cluster-access-kubeconfig/)



