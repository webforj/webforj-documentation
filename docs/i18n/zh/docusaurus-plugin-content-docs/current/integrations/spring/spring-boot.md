---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 98535c8b91fd041be0dcb32840ef9302
---
Spring Boot 是构建 Java 应用的热门选择，提供依赖注入、自动配置和嵌入式服务器模型。将 Spring Boot 与 webforJ 搭配使用时，你可以通过构造函数注入，将服务、存储库和其他由 Spring 管理的 Bean 直接注入 UI 组件。

使用 Spring Boot 与 webforJ 时，你的应用会作为带有嵌入式 Tomcat 服务器的可执行 JAR 运行，而不是将 WAR 文件部署到外部应用服务器。这种打包方式简化了部署流程，也符合云原生部署实践。webforJ 的组件模型和路由可与 Spring 应用上下文协同工作，以管理依赖项和配置。

## 创建 Spring Boot 应用 {#create-a-spring-boot-app}

你有两种方式可以创建新的 Spring Boot webforJ 应用：使用图形化的 startforJ 工具，或使用 Maven 命令行。

<!-- vale off -->
### 选项 1：使用 startforJ {#option-1-using-startforj}
<!-- vale on -->

创建新的 webforJ 应用最简单的方式是使用 [startforJ](https://docs.webforj.com/startforj)，它会根据你选择的 webforJ 原型生成一个最精简的入门项目。这个入门项目包含所有必需的依赖项、配置文件和预制布局，让你可以立即开始构建应用。

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，你可以通过提供以下信息来自定义项目：

- 基本项目元数据（应用名称、Group ID、Artifact ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **运行于** - 选择 **Spring Boot** 以创建 Spring Boot 项目

根据这些信息，startforJ 会基于你选择的原型创建一个配置为使用 Spring Boot 的基础项目。
你可以将项目下载为 ZIP 文件，也可以直接发布到 GitHub。

### 选项 2：使用命令行 {#option-2-using-the-command-line}

如果你更喜欢使用命令行，可以通过官方 webforJ 原型直接生成 Spring Boot webforJ 项目：

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

`flavor` 参数会告知原型生成 Spring Boot 项目，而不是标准的 webforJ 项目。

这会创建一个完整的 Spring Boot 项目，其中包含：
- Spring Boot 父 POM 配置
- webforJ Spring Boot starter 依赖项
- 带有 `@SpringBootApplication` 和 `@Routify` 的主应用类
- 示例视图
- Spring 和 webforJ 的配置文件

## 运行 Spring Boot 应用 {#run-the-spring-boot-app}

原型项目会设置默认的 Maven 目标，因此不带参数运行 `mvn` 会编译应用、启动[前端监视](/docs/configuration/deploy-reload/frontend-watch)，然后运行应用：

```bash
mvn
```

应用默认会在 8080 端口通过嵌入式 Tomcat 服务器启动。你现有的 webforJ 视图和路由会照常运行，但现在你可以注入 Spring Bean 并使用 Spring 功能。

## 配置 {#configuration}

使用 `src/main/resources` 中的 `application.properties` 文件来配置应用。
有关 webforJ 配置属性的信息，请参阅[属性配置](/docs/configuration/properties)。

以下 webforJ `application.properties` 设置专用于 Spring：

| 属性 | 类型 | 描述 | 默认值|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | webforJ servlet 的 URL 映射模式。 | `/*` |
| **`webforj.exclude-urls`** | List | 映射到根路径时不应由 webforJ 处理的 URL 模式。当 webforJ 映射到根上下文（`/*`）时，这些 URL 模式将排除在 webforJ 处理范围之外，可由 Spring MVC 控制器处理。这样，REST 端点和其他 Spring MVC 映射便可与 webforJ 路由共存。 | `[]` |

### 配置差异 {#configuration-differences}

切换到 Spring Boot 时，配置的几个方面会发生变化：

| 方面 | 标准 webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **打包** | WAR 文件 | 可执行 JAR |
| **服务器** | 外部（Jetty、Tomcat） | 嵌入式 Tomcat |
| **运行命令** | `mvn` | `mvn` |
| **主配置** | 仅 `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profiles** | `webforj-dev.conf`、`webforj-prod.conf` | 使用 `application-{profile}.properties` 的 Spring profiles |
| **端口配置** | 在插件配置中 | properties 中的 `server.port` |

:::info 使用 `mvn` 运行，而不是 `mvn spring-boot:run`
`mvn spring-boot:run` 只会启动应用，不会启动[前端监视](/docs/configuration/deploy-reload/frontend-watch)，因此如果项目中存在 `src/main/frontend` 下的源文件，它们不会在应用运行期间进行编译或重新构建。直接运行 `mvn` 会执行项目的默认目标，先启动监视，再启动应用。
:::
