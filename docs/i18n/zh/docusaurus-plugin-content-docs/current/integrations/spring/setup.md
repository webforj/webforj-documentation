---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot 是构建 Java 应用的热门选择，提供了依赖注入、自动配置和嵌入式服务器模型。当您将 Spring Boot 与 webforJ 一起使用时，可以通过构造函数注入将服务、存储库和其他 Spring 管理的 bean 直接注入到您的 UI 组件中。

使用 Spring Boot 和 webforJ，您的应用以可执行 JAR 运行，配备嵌入的 Tomcat 服务器，而不是将 WAR 文件部署到外部应用服务器。这种打包模型简化了部署，并与云原生部署实践相一致。webforJ 的组件模型和路由与 Spring 的应用上下文一起工作，以管理依赖关系和配置。

## 创建一个 Spring Boot 应用 {#create-a-spring-boot-app}

您有两个选项可以使用 Spring Boot 创建一个新的 webforJ 应用：使用图形化的 startforJ 工具或 Maven 命令行。

### 选项 1：使用 startforJ {#option-1-using-startforj}

创建新的 webforJ 应用最简单的方法是 [startforJ](https://docs.webforj.com/startforj)，它基于选定的 webforJ 原型生成一个最小的启动项目。此启动项目包括所有必需的依赖关系、配置文件和预制布局，因此您可以立即开始构建。

当您使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以通过提供以下信息进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- **Flavor** - 选择 **webforJ Spring** 以创建 Spring Boot 项目

使用这些信息，startforJ 将从您选择的原型创建一个针对 Spring Boot 配置的基本项目。
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

这会创建一个完整的 Spring Boot 项目，包含：
- Spring Boot 父 POM 配置
- webforJ Spring Boot 启动器依赖
- 带有 `@SpringBootApplication` 和 `@Routify` 的主应用类
- 示例视图
- Spring 和 webforJ 的配置文件

## 将 Spring Boot 添加到现有项目 {#add-spring-boot-to-existing-projects}

如果您有一个现有的 webforJ 应用，可以通过修改项目配置将 Spring Boot 添加到其中。此过程涉及更新 Maven 配置、添加 Spring 依赖项和转换主应用类。

:::info[仅适用于现有项目]
如果您正在从头创建新项目，请跳过此部分。
:::

### 步骤 1：更新 Maven 配置 {#step-1-update-maven-configuration}

对您的 POM 文件进行以下更改：

1. 将打包方式从 WAR 更改为 JAR：
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

3. 删除任何 WAR 相关的配置，例如：
   - `maven-war-plugin`
   - `webapp` 目录引用
   - `web.xml` 相关配置

如果您已经有一个父 POM，则需要导入 Spring Boot 物料清单（BOM）：

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

### 步骤 2：添加 Spring 依赖项 {#step-2-add-spring-dependencies}

将 webforJ Spring Boot 启动器添加到您的依赖中。保留您现有的 webforJ 依赖：

```xml title="pom.xml"
<dependencies>
    <!-- 您现有的 webforJ 依赖 -->
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

:::tip[webforJ DevTools 以实现自动浏览器刷新]
`webforj-spring-devtools` 依赖扩展了 Spring DevTools，提供自动浏览器刷新。当您在 IDE 中保存更改时，浏览器会自动重新加载，无需手动干预。有关配置的详细信息，请参见 [Spring DevTools](spring-devtools) 指南。
:::

### 步骤 3：更新构建插件 {#step-3-update-build-plugins}

用 Spring Boot Maven 插件替换 Jetty 插件。删除任何现有的 Jetty 配置并添加：

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

### 步骤 4：转换您的应用类 {#step-4-convert-your-app-class}

通过添加必要的 Spring 注解和主方法，将您的主 `App` 类转换为 Spring Boot 应用：

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

`@SpringBootApplication` 注解启用 Spring 的自动配置和组件扫描。`@Routify` 注解保持不变，继续扫描您的视图包中的路由。

### 步骤 5：添加 Spring 配置 {#step-5-add-spring-configuration}

在 `src/main/resources` 中创建 `application.properties`：

```Ini title="application.properties"
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

您现有的 `webforj.conf` 文件继续生效。将其指向您的主类：

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## 运行 Spring Boot 应用 {#run-the-spring-boot-app}

一旦配置完成，使用以下命令运行您的应用：

```bash
mvn spring-boot:run
```

该应用默认以嵌入的 Tomcat 服务器在 8080 端口启动。您现有的 webforJ 视图和路由的功能与之前完全相同，但现在您可以注入 Spring bean 并使用 Spring 特性。

## 配置差异 {#configuration-differences}

当您切换到 Spring Boot 时，几个配置方面会发生变化：

| 方面 | 标准 webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **打包** | WAR 文件 | 可执行 JAR |
| **服务器** | 外部（Jetty，Tomcat） | 嵌入式 Tomcat |
| **运行命令** | `mvn jetty:run` | `mvn spring-boot:run` |
| **主配置** | 仅 `webforj.conf` | `webforj.conf` + `application.properties` |
| **配置文件** | `webforj-dev.conf`，`webforj-prod.conf` | 使用 `application-{profile}.properties` 的 Spring 配置文件 |
| **端口配置** | 在插件配置中 | 属性中的 `server.port` |
