---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 46d6f92d638a214c8b5a0a8632ef0150
---
Spring Boot 是构建 Java 应用的热门选择，提供依赖注入、自动配置和嵌入式服务器模型。当将 Spring Boot 与 webforJ 结合使用时，您可以通过构造函数注入将服务、存储库和其他 Spring 管理的 bean 直接注入到 UI 组件中。

使用 Spring Boot 与 webforJ 时，您的应用作为可执行的 JAR 运行，并且自带嵌入式 Tomcat 服务器，而不是将 WAR 文件部署到外部应用服务器。这种打包模型简化了部署，并符合云原生部署实践。webforJ 的组件模型和路由与 Spring 的应用上下文协同工作，以管理依赖关系和配置。

## 创建 Spring Boot 应用 {#create-a-spring-boot-app}

您有两种创建新 webforJ 应用与 Spring Boot 的选项：使用图形化的 startforJ 工具或 Maven 命令行。

<!-- vale off -->
### 选项 1：使用 startforJ {#option-1-using-startforj}
<!-- vale on -->

创建新 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它根据所选的 webforJ 原型生成一个最小的启动项目。此启动项目包括所有必需的依赖项、配置文件和预制布局，因此您可以立即开始在其上构建。

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，您可以通过提供以下信息进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **Flavor** - 选择 **webforJ Spring** 来创建一个 Spring Boot 项目

使用这些信息，startforJ 将从您选择的原型创建一个配置为 Spring Boot 的基本项目。
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

这将创建一个完整的 Spring Boot 项目，包括：
- Spring Boot 父 POM 配置
- webforJ Spring Boot 启动器依赖
- 带有 `@SpringBootApplication` 和 `@Routify` 的主应用类
- 示例视图
- Spring 和 webforJ 的配置文件

## 将 Spring Boot 添加到现有项目 {#add-spring-boot-to-existing-projects}

如果您有一个现有的 webforJ 应用，可以通过修改项目配置来添加 Spring Boot。此过程包括更新 Maven 配置、添加 Spring 依赖项和转换主应用类。

:::info[仅适用于现有项目]
如果您正在从头开始创建新项目，请跳过此部分。
:::

### 第 1 步：更新 Maven 配置 {#step-1-update-maven-configuration}

对您的 POM 文件进行以下更改：

1. 将打包类型从 WAR 更改为 JAR：
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. 将 Spring Boot 设置为父 POM：
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. 删除任何 WAR 特定的配置，例如：
   - `maven-war-plugin`
   - `webapp` 目录引用
   - 与 `web.xml` 相关的配置

如果您已经有一个父 POM，您需要导入 Spring Boot 材料清单 (BOM)：

```xml title="pom.xml"
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.5.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 第 2 步：添加 Spring 依赖 {#step-2-add-spring-dependencies}

将 webforJ Spring Boot 启动器添加到您的依赖项中。保留您现有的 webforJ 依赖项：

```xml title="pom.xml"
<dependencies>
    <!-- 您现有的 webforJ 依赖项 -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- 添加 Spring Boot 启动器 -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- 添加 devtools -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-spring-devtools</artifactId>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <optional>true</optional>
    </dependency>
</dependencies>
```

:::tip[webforJ DevTools 进行自动浏览器刷新]
`webforj-spring-devtools` 依赖扩展了 Spring DevTools，自动进行浏览器刷新。当您在 IDE 中保存更改时，浏览器会自动重新加载，无需手动干预。有关配置详细信息，请参见 [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) 指南。
:::

### 第 3 步：更新构建插件 {#step-3-update-build-plugins}

用 Spring Boot Maven 插件替换 Jetty 插件。移除任何现有的 Jetty 配置并添加：

```xml title="pom.xml"
<build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <excludeDevtools>true</excludeDevtools>
        </configuration>
      </plugin>
    </plugins>
</build>
```

### 第 4 步：转换您的应用类 {#step-4-convert-your-app-class}

通过添加必要的 Spring 注释和主方法，将您的主 `App` 类转换为 Spring Boot 应用：

```java title="Application.java"
package com.example;

import com.webforj.App;
import com.webforj.annotation.Routify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify(packages = "com.example.views")
public class Application extends App {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    // 如果您有现有的 run() 方法，请保留
    @Override
    public void run() throws WebforjException {
      // 您现有的初始化代码 
    }
}
```

`@SpringBootApplication` 注释启用 Spring 的自动配置和组件扫描。`@Routify` 注释保持不变，继续扫描您的视图包以获取路由。

### 第 5 步：添加 Spring 配置 {#step-5-add-spring-configuration}

在 `src/main/resources` 中创建 `application.properties`：

```Ini title="application.properties"
# 应用入口点的完全限定类名
webforj.entry = org.example.Application

# 应用名称
spring.application.name=Hello World Spring

# 服务器配置
server.port=8080
server.shutdown=immediate

# webforJ DevTools 配置
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## 运行 Spring Boot 应用 {#run-the-spring-boot-app}

配置完成后，使用以下命令运行您的应用：

```bash
mvn spring-boot:run
```

应用默认在 8080 端口上启动，并带有嵌入式 Tomcat 服务器。您现有的 webforJ 视图和路由依然正常工作，但现在您可以注入 Spring bean 并使用 Spring 功能。

## 配置

使用 `src/main/resources` 中的 `application.properties` 文件配置您的应用。
有关 webforJ 配置属性的信息，请参见 [属性配置](/docs/configuration/properties)。

以下 webforJ `application.properties` 设置是特定于 Spring 框架的：

| 属性 | 类型 | 描述 | 默认 |
|------|------|------|------|
| **`webforj.servletMapping`** | 字符串 | webforJ servlet 的 URL 映射模式。 | `/*` |
| **`webforj.excludeUrls`** | 列表 | 映射到根目录时不应由 webforJ 处理的 URL 模式。当 webforJ 被映射到根上下文 (`/*`) 时，这些 URL 模式将被排除在 webforJ 处理之外，并且可以由 Spring MVC 控制器处理。这允许 REST 端点和其他 Spring MVC 映射与 webforJ 路由共存。 | `[]` |

### 配置差异 {#configuration-differences}

切换到 Spring Boot 时，几个配置方面会有所变化：

| 方面 | 标准 webforJ | Spring Boot webforJ |
|------|--------------|----------------------|
| **打包** | WAR 文件 | 可执行 JAR |
| **服务器** | 外部（Jetty, Tomcat） | 嵌入式 Tomcat |
| **运行命令** | `mvn jetty:run` | `mvn spring-boot:run` |
| **主配置** | 仅 `webforj.conf` | `application.properties` + `webforj.conf` |
| **配置文件** | `webforj-dev.conf`, `webforj-prod.conf` | Spring 配置文件与 `application-{profile}.properties` |
| **端口配置** | 在插件配置中 | 属性中的 `server.port` |
