---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 855d2d04609e239338e5a18ebe1b02c4
---
Spring Boot 是构建 Java 应用程序的热门选择，提供依赖注入、自动配置和嵌入式服务器模型。当将 Spring Boot 与 webforJ 一起使用时，您可以通过构造函数注入将服务、存储库和其他 Spring 管理的 bean 直接注入到您的 UI 组件中。

当您将 Spring Boot 与 webforJ 一起使用时，您的应用程序作为一个可执行的 JAR 文件运行，内嵌 Tomcat 服务器，而不是将 WAR 文件部署到外部应用服务器。这种打包模型简化了部署，并与云原生部署实践一致。webforJ 的组件模型和路由与 Spring 的应用上下文协同工作，以管理依赖关系和配置。

## 创建一个 Spring Boot 应用 {#create-a-spring-boot-app}

您有两种选择可以使用 Spring Boot 创建新的 webforJ 应用：使用图形化的 startforJ 工具或 Maven 命令行。

### 选项 1：使用 startforJ {#option-1-using-startforj}

创建新的 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它根据选择的 webforJ 原型生成一个最小的启动项目。该启动项目包括所有必需的依赖项、配置文件和预制布局，因此您可以立即开始构建。

当您使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，您可以通过提供以下信息来进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **运行于** - 选择 **Spring Boot** 来创建 Spring Boot 项目

使用这些信息，startforJ 将根据您选择的原型创建一个配置为 Spring Boot 的基本项目。
您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

### 选项 2：使用命令行 {#option-2-using-the-command-line}

如果您更喜欢使用命令行，可以直接使用官方 webforJ 原型生成一个 Spring Boot webforJ 项目：

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

`flavor` 参数告诉原型生成一个 Spring Boot 项目，而不是标准的 webforJ 项目。

这将创建一个完整的 Spring Boot 项目，其中包括：
- Spring Boot 父 POM 配置
- webforJ Spring Boot 启动依赖
- 带 `@SpringBootApplication` 和 `@Routify` 的主应用类
- 示例视图
- Spring 和 webforJ 的配置文件

## 运行 Spring Boot 应用 {#run-the-spring-boot-app}

原型项目设置其默认的 Maven 目标，因此 `mvn` 没有参数时会编译应用、启动 [前端监视](/docs/configuration/deploy-reload/frontend-watch) 并运行应用：

```bash
mvn
```

默认情况下，应用会在端口 8080 上启动一个内嵌的 Tomcat 服务器。您现有的 webforJ 视图和路由照常工作，但现在您可以注入 Spring beans 并使用 Spring 功能。

## 配置 {#configuration}

使用 `src/main/resources` 中的 `application.properties` 文件来配置您的应用。
有关 webforJ 配置属性的信息，请参见 [属性配置](/docs/configuration/properties)。

以下 webforJ `application.properties` 设置是特定于 Spring 的：

| 属性 | 类型 | 描述 | 默认 |
|------|------|------|------|
| **`webforj.servlet-mapping`** | 字符串 | webforJ servlet 的 URL 映射模式。 | `/*` |
| **`webforj.exclude-urls`** | 列表 | 不应由 webforJ 处理的 URL 模式，当映射到根时。如果将 webforJ 映射到根上下文（`/*`），这些 URL 模式将被排除在 webforJ 处理之外，可由 Spring MVC 控制器处理。这允许 REST 端点和其他 Spring MVC 映射与 webforJ 路由共存。 | `[]` |

### 配置差异 {#configuration-differences}

当您切换到 Spring Boot 时，几个配置方面会改变：

| 方面 | 标准 webforJ | Spring Boot webforJ |
|------|--------------|---------------------|
| **打包** | WAR 文件 | 可执行 JAR |
| **服务器** | 外部（Jetty、Tomcat） | 嵌入式 Tomcat |
| **运行命令** | `mvn jetty:run` | `mvn spring-boot:run` |
| **主配置** | 仅 `webforj.conf` | `application.properties` + `webforj.conf` |
| **配置文件** | `webforj-dev.conf`、`webforj-prod.conf` | Spring 配置文件，使用 `application-{profile}.properties` |
| **端口配置** | 在插件配置中 | 属性中的 `server.port` |
