---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: updated-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 70642cf4584ff6eb218a11d53cd33a89
---
# webforJ构建插件 <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

webforJ构建插件在您的Maven或Gradle构建中运行webforJ的构建时间工作。您只需添加一次，它就会将其目标绑定到您已经运行的阶段，无需保持同步的独立前端项目。它驱动[前端打包工具](/docs/managing-resources/bundler/overview)，编译前端、运行前端测试、提供开发监视服务，并将[热替换工具](/docs/configuration/deploy-reload/hotswap)附加到它启动的应用。

## 添加插件 {#adding-the-plugin}

从[原型](/docs/introduction/getting-started)创建的webforJ项目已经包含了插件。要将其添加到现有项目中：

<Tabs>
<TabItem value="maven" label="Maven">

通过声明插件 `<extensions>true</extensions>` 将其目标绑定到构建，而无需编写执行块：

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

三个目标绑定到您已经运行的阶段，因此普通的 `mvn package` 或 `./gradlew build` 会生成一个包含其前端编译的应用，并且测试阶段会与Java测试一起运行前端测试。监视是您在开发期间手动启动的：

| Maven目标  | Gradle任务   | 运行  | 功能                           |
|------------|--------------|-------|--------------------------------|
| `bundle`   | `webforjBundle` | `prepare-package`，在每个jar和war之前 | 为打包的应用编译前端         |
| `test`     | `webforjTest` | 与测试阶段一起 | 运行前端测试                   |
| `clean`    | `webforjCleanFrontend` | 与清理阶段一起 | 删除生成的前端               |
| `watch`    | `webforjWatch` | 手动，和应用一起 | 在开发期间变更时重建         |
| `push-keys`| `webforjPushKeys` | 手动，每次部署一次 | 生成[推送通知](/docs/advanced/push-notifications)的密钥对并打印配置行 |
| `bbj-setup`| 仅Maven       | 手动，在BBj机器上 | 在本地BBj中安装[BBjShip](/docs/configuration/bbjship/installing#through-the-maven-plugin)并启用其部署端点 |
| `bbj-package` | 仅Maven   | 与打包阶段一起，有`<bbj>`块 | 写入[BBj部署归档](/docs/configuration/bbjship/deploying#package-without-deploying) |
| `bbj-deploy` | 仅Maven    | 与安装阶段一起，有`<bbj>`块 | [将应用部署](/docs/configuration/bbjship/deploying#deploy)到BBjServices |
| `bbj-undeploy` | 仅Maven  | 手动 | [从BBjServices移除应用](/docs/configuration/bbjship/deploying#undeploy) |

在运行应用之前启动监视作为目标，例如`mvn compile webforj:watch spring-boot:run`。原型项目将其设置为默认目标，因此单独使用`mvn`可以启动所有内容。它的重载行为在[前端监视](/docs/configuration/deploy-reload/frontend-watch)中进行了说明。

跳过前端测试以及Java测试，使用`-DskipTests`（Maven）或`-PskipTests`（Gradle）。

## 选项 {#options}

将选项设置为Maven `<configuration>` 元素，或设置为Gradle `webforj { }` 扩展值。除`plugins`和`hotswap`外，所有Maven选项还接受命令行上的 `-D` 属性。这两个构建工具是互相镜像的：

| Maven元素         | Maven属性                     | Gradle       | 默认值                      | 目的                          |
|-------------------|-------------------------------|--------------|----------------------------|-------------------------------|
| `bunVersion`      | `webforj.bundler.version`     | `bunVersion` | managed                    | 锁定Bun版本以实现可重复构建   |
| `bunPath`        | `webforj.bundler.path`        | `bunPath`    | download                   | 使用现有Bun二进制文件而不是下载 |
| `cacheDir`        | `webforj.bundler.cacheDir`    | `cacheDir`   | `${user.home}/.webforj/bun` | 缓存管理的Bun二进制文件的路径 |
| `sourceRoot`      | `webforj.bundler.sourceRoot`  | `sourceRoot`  | `src/main/frontend`       | 前端入口源文件所在位置       |
| `workDir`        | `webforj.bundler.workDir`     | `workDir`    | `target/bundle`           | 插件写入其生成的构建文件的路径 |
| `plugins`         | —                             | `plugins`    | —                          | 通过id开启或关闭一个[扩展](/docs/managing-resources/bundler/extensions/overview)，例如`webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | —                          | 在注解扫描过程中跳过的包前缀 |
| `eager`           | `webforj.bundler.eager`       | `eager`      | `false`                   | 在应用启动时而不是按视图加载整个前端，见[急切打包](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs`        | `webforj.bundler.testArgs`    | `testArgs`   | —                          | 传递给前端测试运行程序的额外参数 |
| `hotswap`         | —                             | `hotswap`    | —                          | 将类更新工具附加到构建启动的应用，见[热替换](/docs/configuration/deploy-reload/hotswap) |

例如，要锁定Bun版本并开启Tailwind：

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
