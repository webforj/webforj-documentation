---
title: 入门
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 84b3ad107540f450350f754730522e0e
---
本指南将创建一个运行于 BBjServices 的 webforJ 应用，在开发过程中使用本地 BBj 运行该应用，并通过 BBjShip 部署它。

:::tip 前提条件
你需要一个 Maven 项目，以及一个与 webforJ 版本相同的 BBj 安装，例如 webforJ 26.03 对应 BBj 26.03，并且 BBjServices 正在运行。BBjShip 不支持 Gradle。其余工具要求请参阅[前提条件](/docs/introduction/prerequisites)。
:::

## 创建 BBj 应用 {#create-a-bbj-app}

创建一个运行于 BBjServices 的新 webforJ 应用有两种方式：使用图形化的 startforJ 工具，或使用 Maven 命令行。

<!-- vale off -->
### 选项 1：使用 startforJ {#option-1-using-startforj}
<!-- vale on -->

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以提供以下信息来自定义项目：

- 基本项目元数据（应用名称、Group ID、Artifact ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **运行于** - 选择 **BBjServices**，以创建一个运行于 BBjServices 的项目

### 选项 2：使用命令行 {#option-2-using-the-command-line}

如果你更喜欢使用命令行，可以通过官方 webforJ 原型生成项目：

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

使用 `flavor` 参数，原型会生成一个运行于 BBjServices 的项目。

这将创建一个包含以下内容的项目：
- 不包含 webforJ 引擎的 `webforj` 依赖；该引擎由 BBjServices 提供
- webforJ Maven 插件，已配置为将应用部署到本地 BBj 的 BBjShip，并使用 Artifact ID 作为应用名称
- 用于开发运行的 HotswapAgent
- 示例视图
- 用于开发和生产环境的 webforJ 配置文件

## 运行应用 {#run-the-app}

原型项目会设置默认的 Maven 目标，因此不带参数运行 `mvn` 会编译应用，并在本地 BBjServices 中启动[开发运行](/docs/configuration/deploy-reload/bbjservices)：

```bash
mvn
```

首次运行会准备本地 BBj。它会安装 BBjShip，并将 HotswapAgent 添加到 BBjServices；随后 BBjServices 会重启一次。然后，构建过程会输出应用地址：

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

编译后的类会直接应用到正在运行的应用，无需重启。按 <kbd>Ctrl</kbd>+<kbd>C</kbd> 停止构建进程和应用。

## 部署应用 {#deploy-the-app}

要部署打包后的应用，请运行：

```bash
mvn install
```

应用随后会发布在 `http://localhost:8888/webapp/my-app`，直到你再次部署或将其移除。请参阅[部署](/docs/configuration/bbjship/deploying)，了解如何部署到其他 BBj 服务器，例如从 CI 流水线进行部署。
