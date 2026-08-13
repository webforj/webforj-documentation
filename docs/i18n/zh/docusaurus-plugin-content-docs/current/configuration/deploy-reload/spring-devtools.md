---
title: Spring Boot
sidebar_position: 30
description: >-
  Set up live reload in a Spring Boot webforJ app, with the development tools
  delivered by the webforJ build plugin.
_i18n_hash: 2fa5b74377a864e82b67db98ee8c9c04
---
在Spring Boot应用程序中，[webforJ构建插件](/docs/configuration/build-plugin)提供了开发运行所需的开发工具。项目未声明对它们的任何依赖，并且它们从未成为打包应用的一部分。

## 需求 {#requirements}

启动器依赖和构建插件。由[原型](/docs/introduction/getting-started)创建的项目同时具有这两者。

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-boot-starter</artifactId>
</dependency>
```

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-spring-boot-starter'
}
```

并且将[webforJ插件应用于构建](/docs/configuration/build-plugin#adding-the-plugin)。

</TabItem>
</Tabs>

## 开启实时重载 {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

像往常一样启动应用程序，使用Maven运行`mvn`或使用Gradle运行`./gradlew bootRun`。Java更改在编译后应用，样式表和图像更改即时生效，以及位于`src/main/frontend`下的源代码通过[前端监视](/docs/configuration/deploy-reload/frontend-watch)重新构建。其余键列在[设置](/docs/configuration/deploy-reload/overview#settings)中。

## Spring DevTools {#spring-devtools}

Spring DevTools是可选的，实时重载在没有它的情况下也可以工作。要使用其重启模型，请添加它的依赖：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
}
```

</TabItem>
</Tabs>

在存在Spring DevTools的情况下，编译更改会重启Spring上下文，并且浏览器在重启完成时刷新。如果同时配置了[热替换工具](/docs/configuration/deploy-reload/hotswap)，该工具将应用类更新并且重启保持关闭状态。

## 生产构建 {#production-builds}

`mvn package`和`./gradlew bootJar`将生成一个不包含开发工具的应用程序，无需排除、配置文件或属性。`webforj.devtools.livereload.enabled`属性在打包应用程序中没有效果。
