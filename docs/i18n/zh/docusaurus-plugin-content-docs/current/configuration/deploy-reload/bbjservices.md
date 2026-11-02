---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: >-
  Set up live reload and hotswap for a webforJ app that runs in the BBjServices
  of your own machine through BBjShip.
_i18n_hash: 46a42789a4b05b258a057e9acae1ef12
---
在 BBjServices 中，构建不会启动自己的服务器。`mvn compile webforj:watch` 通过 [BBjShip](/docs/configuration/bbjship/overview) 从项目直接在您自己的机器上运行应用程序。带有 BBj 特性的原型项目将此设置为其默认的 Maven 目标，因此不带参数的 `mvn` 会编译应用程序，启动 [frontend watch](/docs/configuration/deploy-reload/frontend-watch)，并运行应用程序。

## 需求 {#requirements}

设置为 BBjShip 的 Maven 项目，参见 [入门指南](/docs/configuration/bbjship/getting-started) 或 [现有项目](/docs/configuration/bbjship/existing-projects)，要求在与项目相同的机器上安装 BBj。BBjServices 从构建编译到的文件夹中加载类，因此另一台机器上的 BBj 服务器只能进行 [部署](/docs/configuration/bbjship/deploying)，没有实时重载或热替换功能。

## 开启实时重载 {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

其余键已列在 [设置](/docs/configuration/deploy-reload/overview#settings)。

## 类变更 {#class-changes}

在 BBjServices 中，类变更仅通过 [热替换工具](/docs/configuration/deploy-reload/hotswap) 到达正在运行的应用程序。如果没有这个工具，已编译的变更不会应用，页面也不会重新加载，直到监视重新启动。原型项目配置 HotswapAgent：

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

该工具附加到 BBjServices，而不是构建。第一次运行时会将其添加到 BBjServices 的 Java 参数中，并重新启动一次 BBjServices。后续运行无需重新启动。在 macOS 和 Linux 上，以及在 BBjServices 未注册为 Windows 服务时，重新启动的 BBjServices 将在操作系统的服务管理器外部运行，直到机器重新启动。[故障排除](/docs/configuration/bbjship/troubleshooting#development-runs) 列出了开发运行的信息和对每个信息的处理方式。

:::warning 重启 BBjServices
第一次运行会更改 BBjServices 的 Java 设置，在 `<BBj home>/cfg/BBj.properties` 中，并重新启动 BBjServices。此重启将结束该机器上所有正在运行的 BBj 会话，而不仅仅是 webforJ 的会话，因此建议在没有其他人使用该 BBj 时运行它。在第一次更改之前，文件的副本将保存在 `<BBj home>/plugins/.webforj-bbjship-deployments` 中，命名为 `BBj.properties.before-webforj`。如果您手动更改了文件，请先备份该文件。`mvn webforj:bbj-setup` 会进行相同的更改。
:::

依赖项更改需要重新启动监视。

### BBjServices 的 Java {#java-of-bbjservices}

有了 HotswapAgent，[类结构变更](/docs/configuration/deploy-reload/hotswap#class-structure-changes) 依赖于 BBjServices 运行的 Java，而不是构建的 Java。监视不会更改它。要在 [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) 上运行 BBjServices：

1. 在企业管理器中，转到 **BBjServices**，**Java 设置**。
2. 在 **应用程序** 下选择 **BBjServices**，并设置 Java home。
3. 重新启动 BBjServices。企业管理器中的 **Java 详细信息** 将显示其运行的 JDK 和版本。

请参见 [先决条件](/docs/introduction/prerequisites#java-development-kit-jdk-21) 以安装 JetBrains Runtime。

## 生产构建 {#production-builds}

`mvn install` 将打包的应用程序部署而不包含开发工具，详见 [部署](/docs/configuration/bbjship/deploying)。`webforj.devtools.livereload.enabled` 属性在打包应用程序中无效。
