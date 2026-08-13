---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 8664ccf60a8cd3a84330aabbc75c3a3b
---
Spring Boot 是构建 Java 应用的热门选择，提供依赖注入、自动配置和嵌入式服务器模型。当将 Spring Boot 与 webforJ 一起使用时，可以通过构造函数注入将服务、存储库和其他 Spring 管理的 Bean 直接注入到用户界面组件中。

使用 Spring Boot 和 webforJ 时，您的应用作为可执行的 JAR 与嵌入式 Tomcat 服务器运行，而不是将 WAR 文件部署到外部应用服务器。此打包模型简化了部署并与云原生部署实践保持一致。webforJ 的组件模型和路由与 Spring 的应用上下文一起工作，以管理依赖关系和配置。

## 创建一个 Spring Boot 应用 {#create-a-spring-boot-app}

您可以通过图形界面的 startforJ 工具或 Maven 命令行创建新的 webforJ 应用，有两种选择。

<!-- vale off -->
### 选项 1: 使用 startforJ {#option-1-using-startforj}
<!-- vale on -->

创建新的 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它基于所选的 webforJ 原型生成一个最小的启动项目。该启动项目包括所有必需的依赖项、配置文件和预制布局，您可以立即在其上开始构建。

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以通过提供以下信息来进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **Flavor** - 选择 **webforJ Spring** 来创建一个 Spring Boot 项目

使用这些信息，startforJ 将从您选择的原型中创建一个基本项目，配置为 Spring Boot。
您可以选择将项目作为 ZIP 文件下载或直接发布到 GitHub。

### 选项 2: 使用命令行 {#option-2-using-the-command-line}

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

`flavor` 参数告诉原型生成 Spring Boot 项目，而不是标准的 webforJ 项目。

这会创建一个完整的 Spring Boot 项目，包括：
- Spring Boot 父 POM 配置
- webforJ Spring Boot 启动依赖项
- 带有 `@SpringBootApplication` 和 `@Routify` 的主应用类
- 示例视图
- Spring 和 webforJ 的配置文件

## 运行 Spring Boot 应用 {#run-the-spring-boot-app}

原型项目设置其默认 Maven 目标，因此 `mvn` 不带参数会编译应用，启动 [frontend watch](/docs/configuration/deploy-reload/frontend-watch)，并运行应用：

```bash
mvn
```

应用默认在 8080 端口上启动一个嵌入式 Tomcat 服务器。您的现有 webforJ 视图和路由的工作方式与之前完全相同，但现在您可以注入 Spring Beans 并使用 Spring 功能。

## 配置 {#configuration}

使用位于 `src/main/resources` 中的 `application.properties` 文件来配置您的应用。
有关 webforJ 配置属性的信息，请参阅 [属性配置](/docs/configuration/properties)。

以下 webforJ `application.properties` 设置特定于 Spring：

| 属性 | 类型 | 描述 | 默认值 |
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | webforJ servlet 的 URL 映射模式。 | `/*` |
| **`webforj.exclude-urls`** | List | 当映射到根时，webforJ 不应处理的 URL 模式。当 webforJ 映射到根上下文 (`/*`) 时，这些 URL 模式将被排除在 webforJ 的处理之外，可以由 Spring MVC 控制器处理。这允许 REST 端点和其他 Spring MVC 映射与 webforJ 路由共存。 | `[]` |

### 配置差异 {#configuration-differences}

当切换到 Spring Boot 时，几个配置方面会发生变化：

| 方面 | 标准 webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **打包** | WAR 文件 | 可执行 JAR |
| **服务器** | 外部（Jetty，Tomcat） | 嵌入式 Tomcat |
| **运行命令** | `mvn jetty:run` | `mvn spring-boot:run` |
| **主配置** | 仅 `webforj.conf` | `application.properties` + `webforj.conf` |
| **配置文件** | `webforj-dev.conf`，`webforj-prod.conf` | Spring 配置文件，格式为 `application-{profile}.properties` |
| **端口配置** | 在插件配置中 | `server.port` 在属性中 |
