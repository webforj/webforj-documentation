---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  What the bundler does across the build, the development watch, running
  frontend tests, tuning a compiler, and producing a minified production bundle.
_i18n_hash: 0fe6e8ed747a106be1fedf5a2506f803
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

打包工具作为 [webforJ 构建插件](/docs/configuration/build-plugin) 的目标运行。只需如上所示添加插件一次，正常的 `mvn package` 或 `gradle build` 就会生成一个包含前端编译的应用程序，而 `mvn test` 则会与 Java 测试一起运行前端测试。本文涵盖了打包工具在这些步骤中所做的事情。

## 开发监视 {#the-development-watch}

`watch` 步骤是您在开发期间手动运行的步骤，伴随着应用程序一起运行。它编译前端一次，然后在每次更改时重新构建并刷新浏览器。

```bash
mvn compile webforj:watch spring-boot:run
```

webforJ 项目将此设置为其默认目标，因此不带任何参数的 `mvn` 会同时启动监视和应用程序。它驱动的重新加载行为，即在脚本更改重新加载视图时在位置上应用样式表更改，已在 [前端监视](/docs/configuration/deploy-reload/frontend-watch) 中涵盖。

## 前端测试 {#frontend-tests}

`test` 步骤在测试阶段对 `src/main/frontend` 运行 Bun 测试运行器，因此 `mvn test` 会与 Java 测试一起运行前端测试。当源根没有测试文件时，此步骤会被跳过，失败的前端测试会导致构建失败，因此，前端出现故障会像 Java 测试故障一样停止发布。有关编写这些测试的信息，请参见 [前端测试](/docs/testing/frontend-testing)。

## 调整编译器 {#tuning-a-compiler}

编译器从 `src/main/frontend/bun.config.ts` 中读取其设置，按扩展 ID 进行键入，因此设置会在构建时到达正确的编译器。有关给 SCSS 编译器提供加载路径的示例，请参见 [SCSS 和 Sass](/docs/managing-resources/bundler/extensions/scss)。

## 生产打包 {#the-production-bundle}

`bundle` 步骤在 `prepare-package` 期间运行，因此打包应用程序会为生产编译其前端。生产构建与开发构建在两个重要方面不同，这些在应用程序部署后会显现出来。

- **哈希文件名。** 每个输出文件的名称中都带有其内容的哈希值。因此，浏览器可以长时间缓存文件，因为内容的更改会生成新的名称，而新名称会强制进行新的获取。缓存保持安全，无需手动版本提升。
- **压缩输出。** 空格和死代码被剥离，因此浏览器下载的文件大小尽可能小。

开发构建跳过这两项。它保持稳定的名称和可读的输出，以便监视可以直接替换一个文件，您可以在调试时查看加载的内容。

因为压缩是此步骤的一部分，使用打包工具的项目不需要其他任何东西即可发送压缩的 CSS 和 JavaScript。对于通过 [资源注解](/docs/managing-resources/importing-assets) 加载资源的应用，而不使用打包工具， [压缩插件](/docs/configuration/minifier-plugin) 则负责该生产压缩。

## 渴望打包 {#eager-bundle}

默认情况下，每个视图仅加载它使用的前端，当创建该类的组件时，因此视图不支付它不呈现的内容。

渴望模式在应用启动时将整个前端作为单个包加载，而不是按视图加载。使用 `eager` 选项开启它：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

默认情况下，渴望是关闭的，每视图模型适合大多数应用程序。当您希望从一开始就将整个前端放在位上，而不是在视图呈现时加载时，请使用它。
