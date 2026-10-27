---
title: Frontend watch
sidebar_position: 20
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 065e4f5ad8868a6758e682f38e2dd3b9
---
前端监视器在应用程序运行时重新构建 `src/main/frontend` 下的源代码，并将输出发送到浏览器。这是 [前端打包器](/docs/managing-resources/bundler/overview) 的开发部分，需要将 `webforj.devtools.livereload.enabled` 设置为开启，请参阅 [设置](/docs/configuration/deploy-reload/overview#settings)。

## 运行监视器 {#running-the-watch}

在启动应用程序的目标之前，运行 `watch` 目标。一个原型项目将其设置为默认目标，因此无参数的 `mvn` 会同时运行两个目标：

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

在 BBjServices 中，监视器直接启动 [开发运行](/docs/configuration/deploy-reload/bbjservices)：

```bash
mvn compile webforj:watch
```

要将监视器作为独立的构建步骤运行，请参见 [构建和测试](/docs/managing-resources/bundler/build-and-tests#the-development-watch)。

## 输出的应用方式 {#how-the-output-applies}

浏览器的操作取决于生成的输出，而不是编辑的文件：

| 输出 | 浏览器操作 |
|---|---|
| 样式表，来自 `.css`、`.scss`、`.sass` 或 `.less` 源 | 原地应用。无需重载，表单数据和滚动位置保持不变。 |
| 图像 | 原地更换。无需重载。 |
| 其他任何输出，例如编译后的 `.ts`、`.tsx` 或 `.js` | 视图重载。 |

当一次重建产生多个文件时，仅在每个文件都符合条件的情况下，浏览器才会原地应用它们。否则，它会重新加载一次，因此更改不会部分应用。

## 在服务器重启期间 {#during-a-server-restart}

没有 [热交换工具](/docs/configuration/deploy-reload/hotswap) 的 Java 更改会重启服务器。在重启过程中：

- 应用的样式保持在页面上。
- 当服务器关闭时，系统会显示一个指示器。该指示器仅在重启时出现，而不是在手动重载时。
- 当应用程序准备就绪时页面重新加载，而不是在此之前。

添加或移除 `@BundleEntry` 的更改在重启完成后生效。
