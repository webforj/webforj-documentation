---
title: Jetty
sidebar_position: 40
description: >-
  Run a webforJ app on the embedded Jetty server with the Maven Jetty plugin,
  with live reload and hotswap during development.
_i18n_hash: 73514e3b51a43e4a876aefd5cf933577
---
Maven Jetty 插件直接在嵌入式 Jetty 服务器上运行应用程序，来自项目。一个原型项目将 `compile webforj:watch jetty:run` 设置为其默认的 Maven 目标，因此没有参数的 `mvn` 将编译应用程序，启动 [frontend watch](/docs/configuration/deploy-reload/frontend-watch)，并在 Jetty 上提供应用程序。

## 要求 {#requirements}

Jetty 项目在用于开发运行的配置文件中声明开发工具：

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

版本来自 webforJ 材料清单 (BOM)。该配置文件使依赖项不被打包到 war 文件中。由 [archetype](/docs/introduction/getting-started) 创建的项目具有此配置文件。

## 开启实时重载 {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

这些键与 Spring Boot 应用在 `application.properties` 中设置的键相同，列在 [settings](/docs/configuration/deploy-reload/overview#settings) 中。

## 类更改 {#class-changes}

使用 [hotswap tool](/docs/configuration/deploy-reload/hotswap) 配置后，该工具应用类更改，而 Jetty 不会重新部署。两个 Jetty 属性支持这一点，原型项目设置了这两个属性：

- `scan` 的值为 `0`，表示关闭 Jetty 的文件扫描。
- `deployMode` 保持未设置。热替换需要分叉模式，并且插件会选择它。将 `deployMode` 设置为其他值的构建在没有该工具的情况下启动，并记录该信息。

如果没有热替换工具，将 `scan` 设置为以秒为单位的间隔，Jetty 会在编译的类或资源更改时重新部署应用程序：

| 属性 | 描述 | 默认 |
|----------|-------------|---------|
| `scan` | 编译输出的扫描间隔，以秒为单位，设置为 `jetty.scan` 属性。`0` 表示关闭扫描。更长的间隔降低负载并延迟重新部署。 | `1` |

## 使用注意事项 {#usage-considerations}

- **内存和 CPU**：较低的 `scan` 值会在大型项目上增加资源消耗。更长的间隔减少消耗并延迟重新部署。
- **仅限开发**：Jetty 插件不适用于生产部署。
- **会话**：重新部署可能会丢失用户会话。[hotswap tool](/docs/configuration/deploy-reload/hotswap) 可以在不重新部署的情况下应用更改，从而保持会话。
