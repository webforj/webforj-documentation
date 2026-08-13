---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: b01357f571ce256abb8b390cebdbf5cc
---
您可以使用项目的 POM 文件配置 webforJ，该文件旨在使应用程序的部署变得简单。以下部分概述了您可以更改的各种选项，以实现所需的结果。

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

可以更改 `<configuration>` 标签内的标签来配置您的应用程序。编辑来自 [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) 启动库的默认 POM 文件中的以下行将导致这些更改：

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

- **`<deployurl>`** 此标签是可以访问项目安装的 webforJ 端点的 URL。对于本地运行其应用程序的用户，使用默认端口 8888。对于运行 Docker 的用户，端口应更改为在 [配置 Docker 容器](./docker#2-configuration) 时输入的端口。

- **`<classname>`** 此标签应包含您希望运行的应用程序的包名和类名。这将是项目中唯一的扩展 `App` 类的类，并从基本 URL 运行。

- **`<publishname>`** 此标签指定发布 URL 中应用程序的名称。通常，要运行您的程序，您将导航到类似 `http://localhost:8888/webapp/<publishname>` 的 URL，用 `<publishname>` 替换 `<publishname>` 标签中的值。然后，将运行 `<classname>` 标签指定的程序。

- **`<debug>`** 调试标签可以设置为 true 或 false，并将决定浏览器控制台是否显示您的程序抛出的错误消息。

## 运行特定程序 {#running-a-specific-program}

有两种方法可以在您的应用程序中运行特定程序：

1. 将程序放在扩展 `App` 的类的 `run()` 方法中。
2. 在您的 webforJ 应用程序中利用 [路由](../../routing/overview) 为程序提供专用 URL。

## webforJ 如何选择入口点 {#how-webforj-selects-an-entry-point}

应用程序的入口点由 POM 文件中指定的 `<classname>` 决定。
如果 POM 文件中未指定入口点，系统将启动入口点搜索。

### 入口点搜索 {#entry-point-search}

1. 如果有一个单一的类扩展 `App` 类，则它将成为入口点。
2. 如果多个类扩展 `App`，系统会检查是否有一个具有 `com.webforj.annotation.AppEntry` 注释。被标记为 `@AppEntry` 的单一类将成为入口点。
    :::warning
    如果多个类被标记为 `@AppEntry`，则会抛出异常，列出所有发现的类。
    :::

如果有多个类扩展 `App` 且没有一个被注解为 `@AppEntry`，则会抛出异常，详细说明每个子类。

## 调试模式 {#debug-mode}

您还可以在调试模式下运行应用程序，这允许控制台打印全面的错误信息。

第一种选择是更改 `config.bbx` 文件，该文件位于 BBj 安装的 `cfg/` 目录中。向文件中添加行 `SET DEBUG=1` 并保存更改。

此外，在企业管理器中，您可以将以下内容作为程序参数添加：`DEBUG`

完成这两项中的任何一项都允许浏览器控制台打印错误消息。
