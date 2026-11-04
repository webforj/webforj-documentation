---
title: 安装 BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: b5b4d6d8d79017066eecfcda86557633
---
# 安装 BBjShip <DocChip chip='since' label='26.03' />

BBjShip 在 BBjServices 中运行，因此始终安装在运行 BBjServices 的计算机上。安装完成并启用部署端点后，构建会通过 BBjServices Web 服务器上的 `/webforj-bbjship` 地址部署到 BBjShip，既可以从同一台计算机部署，也可以从其他计算机部署。

BBjShip 的安装方式取决于 BBjServices 的运行位置：

| BBjServices 的运行位置 | BBjShip 的安装者 | 安装方式 |
|---|---|---|
| 你自己的计算机 | webforJ 构建插件 | 通过 `mvn` 自动安装，或使用 `mvn webforj:bbj-setup` 安装一次 |
| 共享服务器或生产服务器 | 该服务器的管理员 | 使用 BBj Plugin Manager 安装，然后运行 `bbjship remote enable` |

无论采用哪种方式，安装的 BBjShip 版本都必须与应用的 webforJ 版本匹配。两者版本不同时，部署会失败。

## 在你自己的计算机上 {#on-your-own-machine}

当 BBj 与构建运行在同一台计算机上时，你无需自行安装 BBjShip。项目首次[开发运行](/docs/configuration/deploy-reload/bbjservices)时，如果项目配置为使用 BBjShip，就会安装 BBjShip 并启用其部署端点：

```bash
mvn
```

### 通过 Maven 插件 {#through-the-maven-plugin}

如果你想在开始任何开发运行之前，先通过 `mvn install` 进行部署，请在项目目录中安装一次 BBjShip：

```bash
mvn webforj:bbj-setup
```

该目标会安装与 `webforj-maven-plugin` 匹配的 BBjShip 版本，并在部署 URL 使用的端口上启用部署端点。它需要：

- BBjServices 正在运行，并在部署 URL 使用的端口上响应
- 部署 URL 指向你的计算机，例如 `http://localhost:8888/webforj-bbjship`
- BBj 管理员用户名和密码，来自 `<admin>` 或 `webforj.bbj.admin.*` 属性。如果未提供，该目标会使用出厂账户 `admin` 和密码 `admin123`，并打印一条警告。
- BBj 安装目录，来自 `<home>`、`webforj.bbj.home` 或 `BBJ_HOME`。如果未提供，该目标会自动检测。

升级 webforJ 后，请将已安装的 BBjShip 替换为新版本：

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

再次运行该目标不会卸载已部署的应用。

:::tip Windows 上正在使用的文件
在 Windows 上，如果 BBjServices 正在占用已安装 BBjShip 的文件，替换操作可能会失败。请重启 BBjServices，然后再次运行该目标，参见[故障排除](/docs/configuration/bbjship/troubleshooting#windows)。
:::

## 在服务器上 {#on-a-server}

对于共享或生产 BBj 服务器，管理员只需在该服务器上安装一次 BBjShip。之后，开发人员和 CI 流水线就可以通过 HTTPS 从自己的计算机向其部署。

1. 使用 [BBj Plugin Manager](#through-the-bbj-plugin-manager) 安装 BBjShip。
2. 使用 BBjServices Web 服务器的 HTTPS 端口[启用部署端点](#enabling-the-deployment-endpoint)。
3. 在 Enterprise Manager 中为执行部署的构建创建授权令牌，参见[从另一台计算机部署](/docs/configuration/bbjship/deploying#deploying-from-another-machine)。

### 通过 BBj Plugin Manager {#through-the-bbj-plugin-manager}

在 [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) 中，从 `BBj-Plugins` 组织的 `webforj-bbjship` 仓库安装 BBjShip。使用标签安装 webforJ 发布版本，使用 `master` 安装开发快照。之后可在 Installed 选项卡中更新。

### 启用部署端点 {#enabling-the-deployment-endpoint}

部署端点是构建用于部署的地址。请使用 BBj 管理员账户，以及 BBjServices Web 服务器正在监听的端口，在服务器上启用该端点。`--password` 将密码作为参数值传入；命令运行时，其他用户可以在进程列表中看到该密码。在 macOS 和 Linux 上，`--password-stdin` 会从标准输入读取密码；在所有系统上，使用 `WEBFORJ_BBJ_PASSWORD` 都可以避免将密码放在命令行中：

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

如果未指定 `--port`，端点将使用端口 8888。如果未指定 `--ssl-port`，则不会配置 HTTPS 地址。其他计算机上的构建应部署到 HTTPS 地址；除非指定 `--allow-unencrypted`，否则端点会对它们通过 HTTP 发送的请求响应 HTTP 403。BBjServices 必须处于运行状态。所有命令请参见[命令行](/docs/configuration/bbjship/command-line)。

:::tip Windows 上的密码
在 Windows 上，请运行 `bbjship.cmd`，并通过 `--password` 或 `WEBFORJ_BBJ_PASSWORD` 传入密码。此处不支持 `--password-stdin`，使用后会以退出代码 `2` 结束。
:::

## 卸载 {#uninstalling}

请先运行 `bbjship reset`。该命令会卸载所有应用，并移除 BBjShip 在 Enterprise Manager 中所做的全部设置。然后在 BBj Plugin Manager 中卸载 BBjShip。

:::warning 请先备份
`bbjship reset` 无法撤销。请先备份 `<BBj home>/cfg/BBj.properties`，并保留每个应用的部署归档，参见 [Reset](/docs/configuration/bbjship/command-line#reset)。
:::
