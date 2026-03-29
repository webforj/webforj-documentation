---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: f6ca2e9ca82e9592c4e0c8b7726164ce
---
您可以使用项目的 POM 文件配置 webforJ，该文件旨在简化应用程序的部署。以下部分概述了您可以更改的各种选项，以实现所需的结果。

## 引擎排除 {#engine-exclusion}

在使用 `BBjServices` 运行时，应排除 `webforj-engine` 依赖项，因为引擎提供的功能已经可用。

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

可以更改 `<configuration>` 标签内的标签来配置您的应用程序。编辑默认 POM 文件中的以下行，该文件随 [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) 起始存储库一起提供，将导致这些更改：

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

- **`<deployurl>`** 此标签是可以访问项目安装的 webforJ 端点的 URL。对于本地运行应用程序的用户，使用默认端口 8888。对于使用 Docker 运行的用户，端口应更改为在 [配置 Docker 容器时](./docker#2-configuration) 输入的端口。

- **`<classname>`** 此标签应包含您希望运行的应用程序的包和类名。这将是您项目中唯一扩展 `App` 类并从基本 URL 运行的类。

- **`<publishname>`** 此标签指定发布 URL 中应用程序的名称。通常，要运行您的程序，您将导航到类似于 `http://localhost:8888/webapp/<publishname>` 的 URL，将 `<publishname>` 替换为 `<publishname>` 标签中的值。接着，将运行 `<classname>` 标签指定的程序。

- **`<debug>`** debug 标签可以设置为 true 或 false，确定浏览器控制台是否显示您的程序抛出的错误消息。

## 运行特定程序 {#running-a-specific-program}

有两种方法可以在您的应用程序中运行特定程序：

1. 将程序放置在扩展 `App` 的类的 `run()` 方法中。
2. 在您的 webforJ 应用中利用 [路由](../../routing/overview) 为程序提供专用 URL。

## webforJ 如何选择入口点 {#how-webforj-selects-an-entry-point}

应用程序的入口点由 POM 文件中指定的 `<classname>` 决定。如果 POM 文件中未指定入口点，系统将开始入口点搜索。

### 入口点搜索 {#entry-point-search}

1. 如果有一个类扩展 `App` 类，则该类将成为入口点。
2. 如果多个类扩展 `App`，系统会检查是否有一个具有 `com.webforj.annotation.AppEntry` 注释。被 `@AppEntry` 注释的单个类将成为入口点。
    :::warning
    如果多个类都被 `@AppEntry` 注释，则会抛出异常，列出所有发现的类。
    :::

如果有多个扩展 `App` 的类，并且它们都没有被 `@AppEntry` 注释，则会抛出异常，详细说明每个子类。

## 调试模式 {#debug-mode}

也可以在调试模式下运行您的应用程序，这允许控制台打印全面的错误消息。

第一种选择是更改位于 BBj 安装的 `cfg/` 目录中的 `config.bbx` 文件。添加 `SET DEBUG=1` 行到文件中并保存更改。

此外，在企业管理器中，您可以将以下内容添加为程序参数：`DEBUG`

完成这两个步骤中的任意一个都可以让浏览器控制台打印错误消息。
