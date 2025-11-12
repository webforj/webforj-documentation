---
title: 安装插件
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 1a3e48999554631e4f15a67c80385111
---
您可以通过项目的 POM 文件配置 webforJ，该文件旨在简化应用程序的部署。以下部分概述了您可以更改的各种选项，以实现所需的结果。

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

可以更改 `<configuration>` 标签内的标签以配置您的应用程序。编辑与 [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) 启动仓库一起提供的默认 POM 文件中的以下行将导致这些更改：

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

- **`<deployurl>`** 此标签是可以访问项目安装的 webforJ 端点的 URL。对于在本地运行其应用程序的用户，默认端口为 8888。对于运行 Docker 的用户，端口应更改为在 [配置 Docker 容器时](./docker#2-configuration) 输入的端口。

- **`<classname>`** 此标签应包含您希望运行的应用程序的包和类名。它将是您项目中扩展 `App` 类并从基础 URL 运行的单个类。

- **`<publishname>`** 此标签指定应用程序在发布 URL 中的名称。通常，要运行您的程序，您将导航到类似于 `http://localhost:8888/webapp/<publishname>` 的 URL，用 `<publishname>` 替换 `<publishname>` 标签中的值。然后，指定的由 `<classname>` 标签运行的程序将被执行。

- **`<debug>`** debug 标签可以设置为 true 或 false，这将决定浏览器控制台是否显示您程序抛出的错误消息。

## 运行特定程序 {#running-a-specific-program}

有两种方法可以在您的应用程序中运行特定程序：

1. 将程序放置在扩展 `App` 的类的 `run()` 方法中。
2. 在您的 webforJ 应用程序中利用 [路由](../../routing/overview) 为程序提供专用 URL。

## webforJ 如何选择入口点 {#how-webforj-selects-an-entry-point}

应用程序的入口点由 POM 文件中指定的 `<classname>` 决定。
如果 POM 文件中未指定入口点，系统将开始入口点搜索。

### 入口点搜索 {#entry-point-search}

1. 如果有单个类扩展 `App` 类，则该类将成为入口点。
2. 如果多个类扩展 `App`，系统会检查是否有一个具有 `com.webforj.annotation.AppEntry` 注解。带有 `@AppEntry` 注解的单个类将成为入口点。
    :::warning
    如果多个类都标注了 `@AppEntry`，则会抛出异常，列出所有发现的类。
    :::

如果有多个类扩展 `App`，且没有任何类带有 `@AppEntry` 注解，则抛出异常，详细说明每个子类。

## 调试模式 {#debug-mode}

您还可以在调试模式下运行您的应用程序，这允许控制台输出详细的错误消息。

第一种选择是更改在您的 BBj 安装的 `cfg/` 目录中找到的 `config.bbx` 文件。在文件中添加行 `SET DEBUG=1` 并保存您的更改。

此外，在企业管理器中，您可以将以下内容作为程序参数添加：`DEBUG`

完成这些步骤中的任何一个将允许浏览器控制台打印错误消息。
