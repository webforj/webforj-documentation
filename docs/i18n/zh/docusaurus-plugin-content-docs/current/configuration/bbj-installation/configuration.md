---
title: 安装插件
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: 1019285125db3439621951fe22047837
---
:::danger 已弃用并将在未来版本中删除
webforJ 安装插件已被弃用，将在未来的发布中删除。请勿使用它开始新的项目。将现有项目移至 [BBjShip](/docs/configuration/bbjship/overview)，该项目安装、部署并在 BBjServices 中运行 webforJ 应用。 [现有项目](/docs/configuration/bbjship/existing-projects) 列出了项目所需的更改。
:::

您可以使用项目的 POM 文件配置 webforJ，该文件旨在简化应用的部署。以下部分概述了您可以更改的各种选项，以实现所需的结果。

## 引擎排除 {#engine-exclusion}

在使用 `BBjServices` 时，应排除 `webforj-engine` 依赖项，因为引擎提供的功能已经可用。

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
```

## POM 文件标签 {#pom-file-tags}

可以更改 `<configuration>` 标签中的标签以配置您的应用。更改随 [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) 启动仓库附带的默认 POM 文件中的以下行将导致这些更改：

```xml {13-16} showLineNumbers
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-install-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>install</goal>
      </goals>
  </execution>
  </executions>
  <configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
  </configuration>
</plugin>
```

- **`<deployurl>`** 此标签是可以访问项目安装的 webforJ 端点的 URL。对于本地运行应用的用户，使用默认端口 8888。对于运行 Docker 的用户，端口应更改为在 [配置 Docker 容器](./docker#2-configuration) 时输入的端口。

- **`<classname>`** 此标签应包含您希望运行的应用的包和类名。这将是您项目中唯一扩展 `App` 类并从基本 URL 运行的类。

- **`<publishname>`** 此标签指定发布 URL 中应用的名称。通常，为了运行您的程序，您将导航到类似 `http://localhost:8888/webapp/<publishname>` 的 URL，用 `<publishname>` 的值替换 `<publishname>` 标签中的值。然后，指定的由 `<classname>` 标签运行的程序将被执行。

- **`<debug>`** 调试标签可以设置为 true 或 false，决定浏览器控制台是否显示您的程序抛出的错误消息。

## 运行特定程序 {#running-a-specific-program}

有两种方法可以在您的应用中运行特定程序：

1. 将程序放置在扩展 `App` 的类的 `run()` 方法中。
2. 在您的 webforJ 应用中利用 [路由](../../routing/overview) 为程序提供专用 URL。

## webforJ 如何选择入口点 {#how-webforj-selects-an-entry-point}

应用的入口点由 POM 文件中指定的 `<classname>` 确定。 
如果在 POM 文件中未指定入口点，系统将开始进行入口点搜索。

### 入口点搜索 {#entry-point-search}

1. 如果有单个类扩展了 `App` 类，则该类将成为入口点。
2. 如果多个类扩展了 `App`，系统将检查是否有类具有 `com.webforj.annotation.AppEntry` 注释。带有 `@AppEntry` 注释的单个类将成为入口点。
    :::warning
    如果多个类带有 `@AppEntry` 注释，将抛出异常，并列出所有发现的类。
    :::

如果有多个扩展 `App` 的类且没有一个带有 `@AppEntry` 注释，将抛出异常，详细说明每个子类。

## 调试模式 {#debug-mode}

您还可以在调试模式下运行应用，该模式允许控制台打印详细的错误消息。

第一种选择是更改位于 BBj 安装的 `cfg/` 目录中的 `config.bbx` 文件。向文件中添加行 `SET DEBUG=1` 并保存更改。

此外，在企业管理器中，您可以将以下内容添加为程序参数：`DEBUG`

完成其中任何一个操作都可以让浏览器控制台打印错误消息。
