---
title: 入门
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 913c105077586054f3af87216fe848e0
---
本指南将创建一个由 BBjServices 运行的 webforJ 应用，在开发期间通过本地 BBj 运行该应用，并使用 BBjShip 部署它。

:::tip 前置条件
你需要一个 Maven 项目，以及与 webforJ 版本相同的 BBj 安装，例如 webforJ 26.03 对应 BBj 26.03，并且 BBjServices 正在运行。BBjShip 不支持 Gradle。其余工具的要求请参阅[前置条件](/docs/introduction/prerequisites)。
:::

## 创建 BBj 应用 {#create-a-bbj-app}

创建一个由 BBjServices 运行的新 webforJ 应用有两种方式：使用图形化工具 startforJ，或使用 Maven 命令行。

<!-- vale off -->
### 选项 1：使用 startforJ {#option-1-using-startforj}
<!-- vale on -->

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以提供以下信息来自定义应用：

- 基本项目元数据（App Name、Group ID、Artifact ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **运行环境** - 选择 **BBjServices**，以创建一个由 BBjServices 运行的项目

### 选项 2：使用命令行 {#option-2-using-the-command-line}

如果你更喜欢使用命令行，可以通过官方的 [webforJ 原型](/docs/building-ui/archetypes/overview)生成项目：

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
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
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
    ```powershell {8}
    mvn -B archetype:generate `
    -DarchetypeGroupId="com.webforj" `
    -DarchetypeArtifactId="webforj-archetype-blank" `
    -DarchetypeVersion="LATEST" `
    -DgroupId="org.example" `
    -DartifactId="my-app" `
    -Dversion="1.0-SNAPSHOT" `
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
    ```console {8}
    mvn -B archetype:generate ^
    -DarchetypeGroupId="com.webforj" ^
    -DarchetypeArtifactId="webforj-archetype-blank" ^
    -DarchetypeVersion="LATEST" ^
    -DgroupId="org.example" ^
    -DartifactId="my-app" ^
    -Dversion="1.0-SNAPSHOT" ^
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

通过 `flavor` 参数，原型会生成一个由 BBjServices 运行的项目。

这将创建一个包含以下内容的项目：
- 不包含 webforJ 引擎的 `webforj` 依赖（该引擎由 BBjServices 提供）
- webforJ Maven 插件，已配置为部署到本地 BBj 上的 BBjShip，并将应用名称设为 Artifact ID
- 用于开发运行的 HotswapAgent
- 示例视图
- 用于开发和生产环境的 webforJ 配置文件

## 运行应用 {#run-the-app}

原型项目会设置默认 Maven 目标，因此不带参数运行 `mvn` 会编译应用，并在本地 BBjServices 中启动[开发运行](/docs/configuration/deploy-reload/bbjservices)：

```bash
mvn
```

首次运行会准备本地 BBj。它会安装 BBjShip，并将 HotswapAgent 添加到 BBjServices；此操作会使 BBjServices 重启一次。随后，构建过程会输出应用的地址：

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

编译后的类无需重启即可在运行中的应用中生效。按 <kbd>Ctrl</kbd>+<kbd>C</kbd> 停止构建，即可停止应用。

## 部署应用 {#deploy-the-app}

要部署打包后的应用，请运行：

```bash
mvn install
```

此后，该应用将发布在 `http://localhost:8888/webapp/my-app`，直到再次部署或将其移除。请参阅[部署](/docs/configuration/bbjship/deploying)，了解如何部署到其他 BBj 服务器，例如通过 CI 流水线进行部署。
