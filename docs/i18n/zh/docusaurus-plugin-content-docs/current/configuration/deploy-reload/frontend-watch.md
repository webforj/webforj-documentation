---
title: 前端监视
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 8307e05aa7a4c55b75fe8667be1f6b27
---
前端监视器在应用运行时重建`src/main/frontend`下的源代码，并将输出发送到浏览器。这是[前端打包工具](/docs/managing-resources/bundler/overview)的开发部分，需要将`webforj.devtools.livereload.enabled`设置为开启，请参阅[设置](/docs/configuration/deploy-reload/overview#settings)。

## 运行监视 {#running-the-watch}

在启动应用的目标之前运行`watch`目标。原型项目将其设置为默认目标，因此不带参数的`mvn`将同时运行两者：

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

要将监视作为独立的构建步骤运行，请参阅[构建和测试](/docs/managing-resources/bundler/build-and-tests#the-development-watch)。

## 输出的应用方式 {#how-the-output-applies}

浏览器的操作取决于生成的输出，而不是编辑的文件：

| 输出 | 浏览器操作 |
|---|---|
| 样式表，来自`.css`、`.scss`、`.sass`或`.less`源 | 就地应用。无需重载，表单数据和滚动位置保持不变。 |
| 图像 | 就地替换。无需重载。 |
| 任何其他输出，如编译后的`.ts`、`.tsx`或`.js` | 视图重载。 |

当一次重建生成多个文件时，仅当每个文件都合格时，浏览器才会就地应用它们。否则会重载一次，因此更改不会部分应用。

## 在服务器重启期间 {#during-a-server-restart}

没有[热替换工具](/docs/configuration/deploy-reload/hotswap)的Java更改会重启服务器。在重启期间：

- 应用的样式将保留在页面上。
- 指示符会在服务器关闭时显示。它仅在重启时出现，而不在手动重载时出现。
- 应用准备好后，页面才会重载，而不会提前。

`@BundleEntry`的添加或移除在重启完成时生效。
