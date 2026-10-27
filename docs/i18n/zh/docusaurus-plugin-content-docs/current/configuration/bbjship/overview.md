---
title: BBjShip
sidebar_position: 1
hide_table_of_contents: true
description: >-
  Deploy webforJ apps to BBjServices with BBjShip and the webforJ Maven plugin,
  and run them on your local BBj during development.
_i18n_hash: c2a89de1c4f3ad8b0f4bc5d364b27a67
---
# BBjShip <DocChip chip='since' label='26.03' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

BBjShip 是一个 BBj 插件，可将 webforJ 应用程序部署到 BBjServices。您可以使用 [webforJ 构建插件](/docs/configuration/build-plugin) 在 BBj 机器或其他机器（例如 CI 服务器）上构建和部署应用程序，应用程序将在 BBjServices 中运行，像在 Enterprise Manager 中管理的任何其他应用程序一样。

BBjShip 替代了在 [与 BBjServices 一起运行](/docs/configuration/bbj-installation/overview) 中描述的 webforJ 安装插件，该插件已经不再推荐使用，并将在未来版本中移除。

:::info 仅限 Maven
BBjShip 通过 `webforj-maven-plugin` 与 Maven 项目兼容。webforJ Gradle 插件不支持 BBjShip。
:::

## 工作原理 {#how-it-works}

部署经过三个部分：

1. **webforJ 构建插件**：将应用程序及其依赖项打包，并上传到 BBjShip
2. **BBjShip**：在 BBjServices 中安装应用程序，并在 `/webapp/<发布名称>` 下发布
3. **BBjServices**：运行应用程序，您可以像管理任何其他应用程序一样在 Enterprise Manager 中管理它

在开发过程中，同样的构建插件会从您机器的 BBjServices 运行您的应用程序。您编译的更改将直接影响正在运行的应用程序，无需进行部署，打开的页面会自动更新，详见 [BBjServices](/docs/configuration/deploy-reload/bbjservices) 中的重新部署和实时重载。

## 主题 {#topics}

<DocCardList className="topics-section" />
