---
title: 安装 BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: 1f408734cddab0f6acd5a9446a2d51e9
---
# 安装 BBjShip <DocChip chip='since' label='26.03' />

BBjShip 运行在 BBjServices 内部，因此它总是安装在运行 BBjServices 的机器上。一旦安装并启用其部署端点，您的构建通过 BBjServices Web 服务器上的地址 `/webforj-bbjship` 部署到该服务，无论是在同一台机器上还是在其他机器上。

安装 BBjShip 的方式取决于 BBjServices 所在的位置：

| BBjServices 所在位置 | 安装 BBjShip 的人 | 安装方式 |
|---|---|---|
| 您自己的机器 | webforJ 构建插件 | 使用 `mvn` 自动安装，或通过 `mvn webforj:bbj-setup` 一次性安装 |
| 共享或生产服务器 | 该服务器的管理员 | BBj 插件管理器，然后运行 `bbjship remote enable` |

在这两种情况下，请安装与您的应用程序的 webforJ 版本匹配的 BBjShip 版本。当两者不同时，部署将会失败。

## 在您自己的机器上 {#on-your-own-machine}

当 BBj 在与您的构建相同的机器上运行时，您不需要自己安装 BBjShip。为 BBjShip 配置的项目的第一次 [开发运行](/docs/configuration/deploy-reload/bbjservices) 将安装它并启用其部署端点：

```bash
mvn
```

### 通过 Maven 插件 {#through-the-maven-plugin}

要在开始任何开发运行之前通过 `mvn install` 进行部署，请从您项目的文件夹中一次性安装 BBjShip：

```bash
mvn webforj:bbj-setup
```

该目标会安装与 `webforj-maven-plugin` 匹配的 BBjShip 版本，并启用部署 URL 端口上的部署端点。它需要：

- BBjServices 运行并在部署 URL 的端口上响应
- 一个名称为您的机器的部署 URL，例如 `http://localhost:8888/webforj-bbjship`
- BBj 管理员用户名和密码，来自 `<admin>` 或 `webforj.bbj.admin.*` 属性。如果没有这些，目标将使用工厂帐户 `admin` 和 `admin123` 并打印警告。
- BBj 安装文件夹，来自 `<home>`、`webforj.bbj.home` 或 `BBJ_HOME`。如果没有他们，目标将自行检测。

在 webforJ 升级之后，用新版本替换已安装的 BBjShip：

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

当您再次运行目标时，已部署的应用将仍然保持部署状态。

:::tip 在 Windows 上使用的文件
在 Windows 上，当 BBjServices 打开已安装的 BBjShip 的文件时，替换可能会失败。请重新启动 BBjServices 并再次运行目标，参见 [故障排除](/docs/configuration/bbjship/troubleshooting#windows)。
:::

## 在服务器上 {#on-a-server}

在共享或生产 BBj 服务器上，管理员在该服务器上安装 BBjShip 一次。然后，开发人员和 CI 流水线从他们自己的机器通过 HTTPS 部署到它。

1. 使用 [BBj 插件管理器](#through-the-bbj-plugin-manager) 安装 BBjShip。
2. 使用 BBjServices Web 服务器的 HTTPS 端口 [启用部署端点](#enabling-the-deployment-endpoint)。
3. 在企业管理器中为进行部署的构建创建授权令牌，参见 [从另一台机器进行部署](/docs/configuration/bbjship/deploying#deploying-from-another-machine)。

### 通过 BBj 插件管理器 {#through-the-bbj-plugin-manager}

在 [BBj 插件管理器](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) 中，从 `BBj-Plugins` 组织的 `webforj-bbjship` 存储库中安装 BBjShip。一个标签安装一个 webforJ 版本，`master` 安装开发快照。之后可以在“已安装”选项卡上进行更新。

### 启用部署端点 {#enabling-the-deployment-endpoint}

部署端点是构建部署的地址。使用您的 BBj 管理员帐户和 BBjServices Web 服务器监听的端口在服务器上启用它。`--password` 将密码作为值传递，其他机器的用户在命令运行时可以在进程列表中看到它。在 macOS 和 Linux 上，`--password-stdin` 从标准输入读取，`WEBFORJ_BBJ_PASSWORD` 防止在每个系统的命令行中显示密码：

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
已启用 http://localhost:8888/webforj-bbjship
已启用 https://localhost:8443/webforj-bbjship
```

如果没有 `--port`，则端点使用端口 8888。如果没有 `--ssl-port`，则没有 HTTPS 地址。BBjServices 必须在运行中。有关所有命令，请参见 [命令行](/docs/configuration/bbjship/command-line)。

:::tip Windows 上的密码
在 Windows 上，运行 `bbjship.cmd` 并通过 `--password` 或 `WEBFORJ_BBJ_PASSWORD` 传递密码。`--password-stdin` 在此不可用，并将以退出代码 `2` 结束。
:::

## 卸载 {#uninstalling}

首先运行 `bbjship reset`。这将撤销所有应用并删除 BBjShip 在企业管理器中设置的所有内容。然后在 BBj 插件管理器中卸载 BBjShip。

:::warning 请先备份
`bbjship reset` 是不可撤销的。请先备份 `<BBj home>/cfg/BBj.properties` 和保存每个应用的部署档案，参见 [重置](/docs/configuration/bbjship/command-line#reset)。
:::
