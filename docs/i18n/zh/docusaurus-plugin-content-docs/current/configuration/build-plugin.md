---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 0c02e741918864a34c35227387259b40
---
# webforJ 构建插件 <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

webforJ 构建插件在您的 Maven 或 Gradle 构建中运行 webforJ 的构建时间工作。您只需添加一次，它就会将其目标绑定到您已经运行的阶段，无需维护单独的前端项目以保持同步。它驱动 [前端打包工具](/docs/managing-resources/bundler/overview)，编译前端、运行前端测试并提供开发监视服务。

## 添加插件 {#adding-the-plugin}

从 [原型](/docs/introduction/getting-started) 创建的 webforJ 项目已经具备该插件。要将其添加到现有项目中：

<Tabs>
<TabItem value="maven" label="Maven">

声明插件时使用 `<extensions>true</extensions>` 将其目标绑定到构建中，无需编写执行块：

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

通过 `buildscript` 类路径依赖添加插件并应用它：

```groovy title="build.gradle"
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-gradle-plugin:${webforjVersion}"
  }
}

apply plugin: 'com.webforj'
```

</TabItem>
</Tabs>

## 目标 {#goals}

该插件绑定了四个目标，每个目标都与您已经运行的阶段相对应，因此正常的 `mvn package` 或 `gradle build` 会生成包含其前端的应用程序，并且 `mvn test` 会与 Java 测试一起运行前端测试。

| Maven 目标 | Gradle 任务 | 阶段 | 功能 |
|------------|-------------|-------|--------------|
| `bundle` | `webforjBundle` | `prepare-package` | 为生产编译前端 |
| `test` | `webforjTest` | `test` | 运行前端测试 |
| `clean` | `webforjCleanFrontend` | `clean` | 删除生成的前端 |
| `watch` | `webforjWatch` | 手动运行 | 在开发期间变更时重新构建 |

`watch` 目标是您在开发期间手动运行的，旁边有应用程序。其重载行为涵盖在 [前端监视](/docs/configuration/deploy-reload/frontend-watch)。

## 选项 {#options}

将选项设置为 Maven `<configuration>`（或命令行上的 `-D` 属性），并作为 Gradle `webforj { }` 扩展值。这两种构建工具相互对应。

| 选项 | Maven 属性 | Gradle | 默认值 | 目的 |
|--------|----------------|--------|---------|---------|
| Bun 版本 | `webforj.bundler.version` | `bunVersion` | 受管理 | 锁定 Bun 版本以实现可重复构建 |
| Bun 二进制文件 | `webforj.bundler.path` | `bunPath` | 下载 | 使用现有的 Bun 二进制文件而不是下载 |
| 缓存目录 | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | 受管的 Bun 二进制文件缓存位置 |
| 源根 | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | 前端入口源所在位置 |
| 工作目录 | `webforj.bundler.workDir` | `workDir` | `target/bundle` | 插件写入其生成的构建文件的位置 |
| 扩展 | `plugins` | `plugins` | — | 按 id 启用或禁用 [扩展](/docs/managing-resources/bundler/extensions/overview)，例如 `webforj-tailwind` |
| 排除包 | `webforj.bundler.excludePackages` | `excludePackages` | — | 在注解扫描期间跳过的包前缀 |
| 渴望 | `webforj.bundler.eager` | `eager` | `false` | 在应用程序启动时而不是按视图加载整个前端，请参见 [渴望打包](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| 测试参数 | `webforj.bundler.testArgs` | `testArgs` | — | 传递给前端测试运行器的额外参数 |
| 跳过测试 | `skipTests`, `maven.test.skip` | — | `false` | 跳过前端测试 |

例如，要锁定 Bun 版本并启用 Tailwind：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <version>1.3.0</version>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  bunVersion = '1.3.0'
  plugins.put('webforj-tailwind', 'true')
}
```

</TabItem>
</Tabs>
