---
title: 开始使用
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 06ef0b29df76b835ecd12d7268e4eca6
---
本指南创建一个与 BBjServices 一起运行的 webforJ 应用，在本地 BBj 上进行开发，并使用 BBjShip 部署。

:::tip 前提条件
您需要一个 Maven 项目和与 webforJ 相同版本的 BBj 安装，例如 webforJ 26.03 对应的 BBj 26.03，并且需要运行 BBjServices。BBjShip 不支持 Gradle。请查看 [前提条件](/docs/introduction/prerequisites) 了解其它工具的要求。
:::

## 创建一个 BBj 应用 {#create-a-bbj-app}

您有两种选项可以创建一个与 BBjServices 一起运行的新 webforJ 应用：使用图形界面的 startforJ 工具或 Maven 命令行。

<!-- vale off -->
### 选项 1：使用 startforJ {#option-1-using-startforj}
<!-- vale on -->

通过 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以通过提供以下信息来自定义它：

- 基本项目元数据（应用名称，组 ID，工件 ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **运行于** - 选择 **BBjServices**，以创建一个与 BBjServices 一起运行的项目

### 选项 2：使用命令行 {#option-2-using-the-command-line}

如果您更喜欢使用命令行，请使用官方的 webforJ 原型生成项目：

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-bbj
```

通过 `flavor` 参数，原型生成一个与 BBjServices 一起运行的项目。

这将创建一个项目，其中包含：
- `webforj` 依赖项，没有 webforJ 引擎，由 BBjServices 提供
- webforJ Maven 插件，设置为在本地 BBj 下以工件 ID 作为应用名称进行部署到 BBjShip
- 用于开发运行的 HotswapAgent
- 示例视图
- 用于开发和生产的 webforJ 配置文件

## 运行应用 {#run-the-app}

原型项目设置了默认的 Maven 目标，因此没有参数的 `mvn` 会编译应用并在本地 BBjServices 中启动 [开发运行](/docs/configuration/deploy-reload/bbjservices)：

```bash
mvn
```

第一次运行会准备您的本地 BBj。它会安装 BBjShip，并将 HotswapAgent 添加到 BBjServices，这样会重启 BBjServices 一次。构建完成后，将打印您应用的地址：

```text
[INFO] webforJ watch: my-app 正在运行于 http://localhost:8888/webapp/my-app
[INFO] webforJ watch: 按 Ctrl+C 停止应用和监视
```

您编译的类会即时生效，无需重启。按 `Ctrl+C` 停止构建以停止应用。

## 部署应用 {#deploy-the-app}

要部署打包的应用，请运行：

```bash
mvn install
```

然后应用将在 `http://localhost:8888/webapp/my-app` 发布，直到您再次部署或将其删除。请参阅 [部署](/docs/configuration/bbjship/deploying) 了解如何部署到其他 BBj 服务器，例如通过 CI 管道。
