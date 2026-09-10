---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 09a13bb6da32b3c4c0e77d4e44c1acb4
---
# webforJ 构建插件 <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

webforJ 构建插件将在您的 Maven 或 Gradle 构建中执行 webforJ 的构建时工作。您只需要添加一次，它就会将其目标绑定到您已经运行的阶段，无需维护单独的前端项目。它驱动 [前端打包工具](/docs/managing-resources/bundler/overview)，编译前端，运行前端测试，提供开发监视，并将一个 [热替换工具](/docs/configuration/deploy-reload/hotswap) 附加到它启动的应用程序。

## 添加插件 {#adding-the-plugin}

通过 [原型](/docs/introduction/getting-started) 创建的 webforJ 项目已经包含该插件。要将其添加到现有项目中：

<Tabs>
<TabItem value="maven" label="Maven">

通过声明插件并设置 `<extensions>true</extensions>` 将其目标绑定到构建中，而无需编写执行块：

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

## 目标和任务 {#goals-and-tasks}

三个目标绑定到您已经运行的阶段，因此正常的 `mvn package` 或 `./gradlew build` 会生成一个包含已编译前端的应用程序，并且测试阶段将同时运行前端测试和 Java 测试。监视是在开发过程中手动启动的：

| Maven 目标 | Gradle 任务 | 运行 | 做什么 |
|------------|-------------|------|--------|
| `bundle` | `webforjBundle` | `prepare-package`，在每个 jar 和 war 之前 | 编译打包应用程序的前端 |
| `test` | `webforjTest` | 在测试阶段 | 运行前端测试 |
| `clean` | `webforjCleanFrontend` | 在清理阶段 | 删除生成的前端 |
| `watch` | `webforjWatch` | 手动，和应用并行 | 在开发过程中变更时重新构建 |
| `push-keys` | `webforjPushKeys` | 手动， 每次部署一次 | 生成 [推送通知](/docs/advanced/push-notifications) 的密钥对并打印配置行 |

在运行应用程序之前，首先启动监视，示例为 `mvn compile webforj:watch spring-boot:run`。一个原型项目将其设置为默认目标，因此仅需 `mvn` 即可启动所有内容。其重载行为在 [前端监视](/docs/configuration/deploy-reload/frontend-watch) 中有介绍。

使用 Maven 的 `-DskipTests` 或 Gradle 的 `-PskipTests` 跳过前端测试和 Java 测试。

## 选项 {#options}

将选项设置为 Maven `<configuration>` 元素，或作为 Gradle `webforj { }` 扩展值。每个 Maven 选项（除 `plugins` 和 `hotswap` 外）也接受命令行上的 `-D` 属性。这两种构建工具彼此镜像：

| Maven 元素 | Maven 属性 | Gradle | 默认 | 目的 |
|------------|------------|--------|------|------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | 管理 | 固定 Bun 版本以实现可重现的构建 |
| `bunPath` | `webforj.bundler.path` | `bunPath` | 下载 | 使用现有的 Bun 可执行文件，而不是下载 |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | 管理的 Bun 二进制文件缓存的位置 |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | 前端入口源文件的位置 |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | 插件写入其生成的构建文件的位置 |
| `plugins` | — | `plugins` | — | 根据 id 开启或关闭 [扩展](/docs/managing-resources/bundler/extensions/overview)，例如 `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | 在注解扫描过程中跳过的包前缀 |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | 在应用启动时加载整个前端，而不是按视图加载，参见 [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | 传递给前端测试运行器的额外参数 |
| `hotswap` | — | `hotswap` | — | 附加一个类更新工具到构建启动的应用程序，参见 [热替换](/docs/configuration/deploy-reload/hotswap) |

例如，要固定 Bun 版本并开启 Tailwind：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <bunVersion>1.3.0</bunVersion>
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
