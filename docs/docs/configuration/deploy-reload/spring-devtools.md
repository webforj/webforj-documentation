---
title: Spring Boot
sidebar_position: 30
description: Set up live reload in a Spring Boot webforJ app, with the development tools delivered by the webforJ build plugin.
---

In a Spring Boot app, the [webforJ build plugin](/docs/configuration/build-plugin) delivers the development tools to development runs. The project declares no dependency for them, and they're never part of the packaged app.

## Requirements {#requirements}

The starter dependency and the build plugin. A project created from an [archetype](/docs/introduction/getting-started) has both.

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

with the [webforJ plugin applied to the build](/docs/configuration/build-plugin#adding-the-plugin).

</TabItem>
</Tabs>

## Turning live reload on {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

Start the app as usual, `mvn` with Maven or `./gradlew bootRun` with Gradle. Java changes apply after a compile, stylesheet and image changes apply in place, and sources under `src/main/frontend` rebuild through the [frontend watch](/docs/configuration/deploy-reload/frontend-watch). The remaining keys are listed in the [settings](/docs/configuration/deploy-reload/overview#settings).

## Spring DevTools {#spring-devtools}

Spring DevTools is optional, live reload works without it. To use its restart model, add its dependency:

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

With Spring DevTools present, a compiled change restarts the Spring context and the browser refreshes when the restart completes. With a [hotswap tool](/docs/configuration/deploy-reload/hotswap) configured as well, the tool applies the class updates and the restart stays off.

## Production builds {#production-builds}

`mvn package` and `./gradlew bootJar` produce an app without development tools, with no exclusion, profile, or property required. The `webforj.devtools.livereload.enabled` property has no effect in a packaged app.
