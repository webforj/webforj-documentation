---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 7cb4ddbb9aea86ff6f501296b42c5bbf
---
# webforJ构建插件 <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

webforJ构建插件在您的Maven或Gradle构建中运行webforJ的构建时间工作。只需添加一次，它就会将其目标绑定到您已经运行的阶段，无需维护单独的前端项目。它驱动[前端打包器](/docs/managing-resources/bundler/overview)，编译前端，运行前端测试，提供开发监视，并将[热替换工具](/docs/configuration/deploy-reload/hotswap)附加到它启动的应用程序。

## 添加插件 {#adding-the-plugin}

从[原型](/docs/introduction/getting-started)创建的webforJ项目已经包含该插件。要将其添加到现有项目中：

<Tabs>
<TabItem value="maven" label="Maven">

使用`<extensions>true</extensions>`声明插件，将其目标绑定到构建中，无需编写执行块：

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

通过`buildscript`类路径依赖添加插件并应用它：

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

三个目标绑定到您已经运行的阶段，因此正常的`mvn package`或`./gradlew build`会生成包含其前端的应用程序，并且测试阶段会与Java测试一起运行前端测试。监视是您在开发过程中手动启动的目标：

| Maven目标 | Gradle任务 | 运行 | 作用 |
|------------|-------------|------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`，在每个jar和war之前 | 为打包应用程序编译前端 |
| `test` | `webforjTest` | 与测试阶段一起 | 运行前端测试 |
| `clean` | `webforjCleanFrontend` | 与清理阶段一起 | 删除生成的前端 |
| `watch` | `webforjWatch` | 手动，与应用程序并行 | 在开发过程中更改时重新构建 |

在运行应用程序的目标之前启动监视，例如`mvn compile webforj:watch spring-boot:run`。原型项目将其设置为默认目标，因此仅需`mvn`即可启动所有内容。其重新加载行为在[前端监视](/docs/configuration/deploy-reload/frontend-watch)中介绍。

跳过前端测试以及Java测试，使用`-DskipTests`或`-Dmaven.test.skip`与Maven，使用`-PskipTests`与Gradle。

## 选项 {#options}

将选项设置为Maven `<configuration>`元素，或作为Gradle `webforj { }`扩展值。除`plugins`和`hotswap`外，每个Maven选项也接受命令行上的`-D`属性。这两个构建工具彼此镜像：

| Maven元素 | Maven属性 | Gradle | 默认值 | 目的 |
|---------------|----------------|--------|---------|---------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | 管理的 | 固定Bun版本以实现可复现的构建 |
| `bunPath` | `webforj.bundler.path` | `bunPath` | 下载 | 使用现有的Bun二进制文件而不是下载 |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | 存储管理的Bun二进制文件的缓存位置 |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | 存放前端入口源的路径 |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | 插件写入其生成的构建文件的路径 |
| `plugins` | — | `plugins` | — | 根据ID打开或关闭[扩展](/docs/managing-resources/bundler/extensions/overview)，例如`webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | 在注解扫描过程中跳过的包前缀 |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | 在应用程序启动时加载整个前端，而不是按视图加载，参见[急切打包](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | 传递给前端测试运行器的额外参数 |
| `hotswap` | — | `hotswap` | — | 将类更新工具附加到构建启动的应用程序，参见[热替换](/docs/configuration/deploy-reload/hotswap) |

例如，固定Bun版本并启用Tailwind：

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
