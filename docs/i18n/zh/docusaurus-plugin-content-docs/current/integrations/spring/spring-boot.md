---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 116777dbeb2e6707e2ef867f0dd6d78c
---
Spring Boot是构建Java应用程序的一个流行选择，提供依赖注入、自动配置和嵌入式服务器模型。当使用Spring Boot与webforJ时，您可以通过构造函数注入将服务、存储库和其他Spring管理的bean直接注入到您的UI组件中。

当您将Spring Boot与webforJ一起使用时，您的应用程序以可执行的JAR文件形式运行，并带有嵌入的Tomcat服务器，而不是将WAR文件部署到外部应用程序服务器。这个打包模型简化了部署，并与云原生部署实践保持一致。webforJ的组件模型和路由与Spring的应用程序上下文协同工作，以管理依赖关系和配置。

## 创建Spring Boot应用 {#create-a-spring-boot-app}

您有两种选择来创建一个新的webforJ应用程序与Spring Boot：使用图形化的startforJ工具或Maven命令行。

<!-- vale off -->
### 选项1：使用startforJ {#option-1-using-startforj}
<!-- vale on -->

创建一个新的webforJ应用程序最简单的方法是[使用startforJ](https://docs.webforj.com/startforj)，它根据所选择的webforJ模板生成一个最小的启动项目。这个启动项目包含所有必需的依赖项、配置文件和预制布局，因此您可以立即开始构建。

当您使用[startforJ](https://docs.webforj.com/startforj)创建一个应用程序时，可以通过提供以下信息进行自定义：

- 基本项目元数据（应用程序名称、组ID、工件ID）  
- webforJ版本和Java版本
- 主题颜色和图标
- 模板
- **Flavor** - 选择 **webforJ Spring** 来创建一个Spring Boot项目

使用这些信息，startforJ将根据您选择的模板为Spring Boot创建一个基本项目。您可以选择将项目下载为ZIP文件或直接发布到GitHub。

### 选项2：使用命令行 {#option-2-using-the-command-line}

如果您更喜欢使用命令行，请直接使用官方的webforJ模板生成一个Spring Boot webforJ项目：

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

`flavor`参数告诉模板生成一个Spring Boot项目，而不是标准的webforJ项目。

这将创建一个完整的Spring Boot项目，其中包括：
- Spring Boot父级POM配置
- webforJ Spring Boot启动器依赖
- 带有`@SpringBootApplication`和`@Routify`的主应用程序类
- 示例视图
- Spring和webforJ的配置文件

## 向现有项目添加Spring Boot {#add-spring-boot-to-existing-projects}

如果您有一个现有的webforJ应用程序，您可以通过修改项目配置来添加Spring Boot。此过程涉及更新您的Maven配置、添加Spring依赖项以及转换主应用程序类。

:::info[仅适用于现有项目]
如果您是从头开始创建新项目，请跳过此部分。此指南假设**webforJ版本25.11或更高版本**。
:::

### 步骤1：更新Maven配置 {#step-1-update-maven-configuration}

对您的POM文件进行以下更改：

1. 将打包从WAR更改为JAR：
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. 将Spring Boot设置为父POM：
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>4.0.5</version>
       <relativePath/>
   </parent>
   ```

3. 删除任何WAR特定的配置，例如：
   - `maven-war-plugin`
   - `webapp`目录引用
   - `web.xml`相关配置

如果您已经有一个父POM，您需要导入Spring Boot的材料清单（BOM）：

```xml title="pom.xml"
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>4.0.5</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### 步骤2：添加Spring依赖 {#step-2-add-spring-dependencies}

将webforJ Spring Boot启动器添加到您的依赖项：

:::info[webforJ 25.11+ 简化]
从**webforJ版本25.11**开始，`webforj-spring-boot-starter`传递性地包含所有核心webforJ依赖项。您不再需要显式添加`com.webforj:webforj`依赖项。

对于**25.11之前**的版本，您必须单独包含这两个依赖项。
:::

**对于webforJ 25.11及更高版本：**

```xml title="pom.xml"
<dependencies>
  <!-- 添加Spring Boot启动器（包含webforJ的传递项） -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- 添加devtools -->
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

**对于25.11之前的版本：**

```xml title="pom.xml"
<dependencies>
  <!-- 显式添加webforJ依赖 -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
  </dependency>
    
  <!-- 添加Spring Boot启动器 -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- 添加devtools -->
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

:::tip[webforJ DevTools用于自动浏览器刷新]
`webforj-spring-devtools`依赖项扩展了Spring DevTools，提供自动浏览器刷新。当您在IDE中保存更改时，浏览器会自动重新加载，无需手动干预。有关配置详细信息，请参见[Spring DevTools](/docs/configuration/deploy-reload/spring-devtools)指南。
:::

### 步骤3：更新构建插件 {#step-3-update-build-plugins}

用Spring Boot Maven插件替换Jetty插件。移除任何现有的Jetty配置并添加：

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

### 步骤4：转换您的应用类 {#step-4-convert-your-app-class}

通过添加必要的Spring注解和主方法，将您的主`App`类转换为Spring Boot应用：

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
    
  // 如果您有现有的run()方法，请保留它
  @Override
  public void run() throws WebforjException {
    // 您现有的初始化代码 
  }
}
```

`@SpringBootApplication`注解启用Spring的自动配置和组件扫描。`@Routify`注解保持不变，继续扫描您的视图包以获取路由。

### 步骤5：添加Spring配置 {#step-5-add-spring-configuration}

在`src/main/resources`中创建`application.properties`：

```Ini title="application.properties"
# 应用程序入口点的完全限定类名
webforj.entry = org.example.Application

# 应用程序名称
spring.application.name=Hello World Spring

# 服务器配置
server.port=8080
server.shutdown=immediate

# webforJ DevTools配置
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## 运行Spring Boot应用 {#run-the-spring-boot-app}

配置完成后，使用以下命令运行您的应用：

```bash
mvn spring-boot:run
```

应用程序将在默认的8080端口上启动，带有嵌入的Tomcat服务器。您现有的webforJ视图和路由仍然可以正常工作，但现在您可以注入Spring beans并使用Spring特性。

## 配置

请使用`src/main/resources`中的`application.properties`文件来配置您的应用程序。有关webforJ配置属性的信息，请参见[属性配置](/docs/configuration/properties)。

以下webforJ `application.properties`设置特定于Spring：

| 属性 | 类型 | 描述 | 默认值|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | 字符串 | webforJ servlet的URL映射模式。 | `/*` |
| **`webforj.exclude-urls`** | 列表 | 不应由webforJ处理的URL模式，当映射到根时。 当webforJ映射到根上下文（`/*`）时，这些URL模式将从webforJ处理中排除，并可以由Spring MVC控制器处理。 这允许REST端点和其他Spring MVC映射与webforJ路由共存。 | `[]` |

### 配置差异 {#configuration-differences}

当您切换到Spring Boot时，多个配置方面会发生变化：

| 方面 | 标准webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **打包** | WAR文件 | 可执行JAR |
| **服务器** | 外部（Jetty，Tomcat） | 嵌入的Tomcat |
| **运行命令** | `mvn jetty:run` | `mvn spring-boot:run` |
| **主配置** | 仅`webforj.conf` | `application.properties` + `webforj.conf`  |
| **配置文件** | `webforj-dev.conf`，`webforj-prod.conf` | Spring配置文件，格式为`application-{profile}.properties` |
| **端口配置** | 在插件配置中 | `properties`中的`server.port` |
