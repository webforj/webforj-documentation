---
title: 现有项目
sidebar_position: 3
description: >-
  Move an existing webforJ project from the webforJ install plugin to BBjShip
  with one OpenRewrite run, and what the migration changes in the build and the
  configuration.
_i18n_hash: 36b02a0227a79d8b8aa9929d930c3e75
---
一个使用已弃用的 webforJ 安装插件进行部署的项目通过一次 OpenRewrite 运行迁移到 BBjShip。该运行更改项目的 `pom.xml` 和 `webforj.conf`。Java 代码、样式表和资源保持不变。

## 使用 OpenRewrite 进行迁移 {#migrate-with-openrewrite}

将 OpenRewrite Maven 插件添加到您的 `pom.xml`，使用 webforJ 26.03 配方：

```xml title="pom.xml"
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>com.webforj.rewrite.v26.UpgradeWebforj_26_03</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>26.03</version>
    </dependency>
  </dependencies>
</plugin>
```

运行迁移，然后检查更改：

```bash
mvn rewrite:run
git diff
```

迁移后项目使用 BBjShip 进行部署。`mvn install` 部署应用，而 `mvn` 启动 [开发运行](/docs/configuration/deploy-reload/bbjservices)。迁移后从您的 `pom.xml` 中移除 OpenRewrite 插件。[自动升级](/docs/upgrading/automated-upgrades#previewing-changes) 显示如何在运行之前预览更改。

## 迁移更改内容 {#what-the-migration-changes}

### 构建插件 {#the-build-plugin}

安装插件变为 [webforJ 构建插件](/docs/configuration/build-plugin)，带有 `<extensions>true</extensions>`，因此 `mvn install` 不需要执行即可部署。其设置移入 `<bbj>` 块：

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
  <configuration>
    <bbj>
      <publishName>inventory</publishName>
      <deployment>
        <url>http://localhost:8888/webforj-bbjship</url>
      </deployment>
    </bbj>
  </configuration>
</plugin>
```

| 安装插件 | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`，路径为 `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`、`<password>`、`<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` 在 `webforj.conf` 中 |
| `<debug>` | `webforj.debug` 在 `webforj.conf` 中 |

[部署](/docs/configuration/bbjship/deploying#settings) 列出了 `<bbj>` 块的每个设置。

### webforJ 引擎 {#the-webforj-engine}

BBjServices 提供了 webforJ 引擎，因此您的应用程序不能自带引擎。`webforj` 依赖项已经排除了 `webforj-engine`。迁移再次在 `provided` 范围内声明它，以便您的 IDE 仍然可以编译：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### 配置文件 {#webforjconf}

入口类和调试标志从安装插件移动到 `webforj.conf`，与 BBjShip 应用运行时的设置在一起：

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` 从应用服务 `ws://` 静态文件和 `icons://` 图标。如果没有它，使用 `icons://` 的应用将无法在 BBjServices 中启动。文件中已设置的键保持不变。

### 开发运行 {#development-runs}

迁移在构建插件的配置中添加了 HotswapAgent：

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

它还将 `compile webforj:watch` 设置为构建的默认目标，因此 `mvn` 在您自己机器的 BBjServices 中运行应用程序：

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

请参见 [BBjServices](/docs/configuration/deploy-reload/bbjservices) 中的重新部署和实时重新加载。

## 没有安装插件 {#without-the-install-plugin}

一个从未使用安装插件的项目通过手动方式获得相同的配置：包含 `<bbj>` 块的 [构建插件](#the-build-plugin)，`webforj-engine` [排除和 `provided` 声明](#the-webforj-engine)，以及 [`webforj.conf`](#webforjconf) 其包含 `webforj.assetsSource = packaged`。该项目使用 `jar` 打包，且 `webforj` 依赖项排除了 `webforj-engine`：

```xml title="pom.xml"
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
```
