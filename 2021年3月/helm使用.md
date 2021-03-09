# Helm
Helm使用的包格式称为 chart。 chart就是一个描述Kubernetes相关资源的文件集合。单个chart可以用来部署一些简单的， 类似于memcache pod，或者某些复杂的HTTP服务器以及web全栈应用、数据库、缓存等等。

Chart是作为特定目录布局的文件被创建的。它们可以打包到要部署的版本存档中。

如果你想下载和查看一个发布的chart，但不安装它，你可以用这个命令： helm pull chartrepo/chartname。

该文档解释说明了chart格式，并提供了用Helm构建chart的基本指导。

## Chart文件结构

chart是一个组织在文件目录中的集合。目录名称就是chart名称（没有版本信息）。因而描述WordPress的chart可以存储在wordpress/目录中。

在这个目录中，Helm 期望可以匹配以下结构：

```text
wordpress/
  Chart.yaml          # 包含了chart信息的YAML文件
  LICENSE             # 可选: 包含chart许可证的纯文本文件
  README.md           # 可选: 可读的README文件
  values.yaml         # chart 默认的配置值
  values.schema.json  # 可选: 一个使用JSON结构的values.yaml文件
  charts/             # 包含chart依赖的其他chart
  crds/               # 自定义资源的定义
  templates/          # 模板目录， 当和values 结合时，可生成有效的Kubernetes manifest文件
  templates/NOTES.txt # 可选: 包含简要使用说明的纯文本文件
```
Helm保留使用 `charts/`，`crds/`， `templates/`目录，以及列举出的文件名。其他文件保持原样。


> https://helm.sh/zh/docs/topics/charts/