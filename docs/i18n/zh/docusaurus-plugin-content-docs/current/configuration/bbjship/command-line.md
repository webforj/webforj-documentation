---
title: Command Line
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: 976400b6e439ac682858351e8f9ade17
---
# 命令行 <DocChip chip='since' label='26.03' /> {#command-line}

`bbjship` 命令行管理 BBjShip 在 BBj 机器上的所有操作。它位于 `<BBj home>/plugins/webforj-bbjship`，在 Windows 上是 `bbjship.cmd`。每个命令都在 BBjServices 内部运行，因此 BBjServices 必须在每次命令运行时处于运行状态。

```text
bbjship <group> <command> [options]
```

## 命令 {#commands}

| 命令 | 描述 |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT]`](#remote-enable) | 启用或更新部署端点并打印其地址。 |
| [`remote disable`](#remote-disable) | 移除部署端点。已部署的应用程序继续运行。 |
| [`remote status`](#remote-status) | 打印端点地址以及开发运行是否已启用。 |
| [`apps deploy ARCHIVE`](#apps-deploy) | 部署一个部署档案。 |
| [`apps list [--long]`](#apps-list) | 列出 BBjShip 部署的应用程序。 |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | 移除 BBjShip 部署的一个或所有应用程序。 |
| [`development enable`, `development disable`](#development) | 打开或关闭该机器上的 [开发运行](/docs/configuration/deploy-reload/bbjservices)。 |
| [`reset [--yes] [--properties FILE]`](#reset) | 移除每个应用程序及 BBjShip 设置的所有内容。 |

## 共享选项 {#options}

| 设置 | 默认 | 描述 |
|---|---|---|
| `--username USER` | 未设置 | BBj 管理员用户名。 |
| `--password PASSWORD` | 未设置 | BBj 管理员密码。 |
| `--password-stdin` | 关闭 | 从标准输入的第一行读取密码。Windows 上不可用。 |
| `--help`, `-h` | | 打印命令行或某个命令的用法。 |
| `--version`, `-V` | | 打印 BBjShip 版本。 |

通过 `--password` 给出的密码在命令运行时对机器上的其他用户是可见的。在 macOS 和 Linux 上，可以通过脚本从标准输入传递密码：

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

在每个系统上，变量可以将密码保持在命令行之外：

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

在 Windows 上，标准输入不可用，因此 `--password-stdin` 返回退出代码 `2`。将密码作为值或通过变量传递：

```text
bbjship.cmd remote enable --username admin --password <value>
```

同时提供 `--password` 和 `--password-stdin`，或者没有密码的用户名，或没有用户名的密码都将返回退出代码 `2` 并显示所需的规范信息。

没有凭证时，该命令使用工厂账号 `admin` 和密码 `admin123`，同时打印一个警告。

## 环境变量 {#environment-variables}

| 变量 | 默认 | 描述 |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | 未设置 | 当未提供 `--username` 时的用户名。 |
| `WEBFORJ_BBJ_PASSWORD` | 未设置 | 当未提供 `--password` 或 `--password-stdin` 时的密码。 |
| `NO_COLOR` | 未设置 | 关闭彩色输出。 |

## 远程 {#remote}

`remote` 命令管理部署端点。

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT]
```

启用或更新部署端点并打印其地址。

| 设置 | 默认 | 描述 |
|---|---|---|
| `--port PORT` | `8888` | BBjServices Web 服务器的 HTTP 端口。 |
| `--ssl-port PORT` | 无 | BBjServices Web 服务器的 SSL 端口。如果没有指定，则端点没有 HTTPS 地址。 |

这些端口必须是 BBjServices Web 服务器已经监听的端口。否则，`remote enable` 将返回退出代码 `1`，并指出未响应的端口，并保留先前的端点。

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

移除部署端点。已部署的应用程序继续运行。

<!-- vale Google.WordListCase = NO -->
:::warning 构建无法部署
`remote disable` 移除部署端点。在你再次运行 `remote enable` 之前，向其部署的构建和 CI 作业将失败。请在禁用它之前注意端口和端点地址。已部署的应用程序继续运行。
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

打印端点地址以及开发运行是否已启用。

```text
$ bbjship remote status
启用 http://localhost:8888/webforj-bbjship
启用 https://localhost:8443/webforj-bbjship
开发运行禁用
```

## 应用程序 {#apps}

`apps` 命令仅列出和移除 BBjShip 部署的应用程序。企业管理器中注册的应用程序不受影响。

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

部署一个部署档案。`apps deploy` 安装由 [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying) 生成的档案，不需要部署端点。去掉 `.zip` 的文件名是 [发布名称](/docs/configuration/bbjship/deploying#publish-names)。BBjServices 读取该档案，因此 BBjServices 的操作系统用户需要对其有读访问权限。

```text
$ bbjship apps deploy inventory.zip
已部署 inventory /webapp/inventory
```

:::warning 替换应用程序
在已经部署的应用程序下以同名部署的档案将替换该应用程序。先前的部署文件夹将被移除，应用程序的运行会话将结束。如果可能需要回滚，请保留先前的档案。
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

列出 BBjShip 部署的应用程序。

| 设置 | 默认 | 描述 |
|---|---|---|
| `--long`, `-l` | 关闭 | 添加每个应用程序的部署文件夹和类文件夹。 |

```text
$ bbjship apps list
名称       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

移除 BBjShip 部署的一个或所有应用程序。

| 设置 | 默认 | 描述 |
|---|---|---|
| `--all` | 关闭 | 移除 BBjShip 部署的所有应用程序。 |

:::warning 无法撤消
`apps undeploy` 将会从企业管理器中移除应用程序，结束其运行会话，并删除其部署文件夹。`apps undeploy --all` 将对 BBjShip 部署的每个应用程序进行此操作。这无法撤消。请保留每个应用程序的部署档案，例如 `target/bbj/<publish name>.zip`，以便可以再次部署它。
:::

## 开发 {#development}

在新安装上，开发运行处于关闭状态，并且在同一台机器上首次运行 `webforj:watch` 会将其打开。`bbjship development disable` 将在这台机器上关闭开发运行，直到下一个 `webforj:watch` 重新将其打开。来自其他机器的开发运行请求会被拒绝，因为它首先运行的设置仅在 BBj 机器上工作。

### `development enable` {#development-enable}

```text
bbjship development enable
```

在该机器上启用 [开发运行](/docs/configuration/deploy-reload/bbjservices)。

### `development disable` {#development-disable}

```text
bbjship development disable
```

在该机器上禁用 [开发运行](/docs/configuration/deploy-reload/bbjservices)。

## 重置 {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` 将移除 BBjShip 部署的所有应用程序，移除部署端点和开发开关，恢复开发运行所更改的 BBjServices 属性文件，并删除 BBjShip 写入的文件。该命令从不停止 BBjServices。当恢复属性文件后，重新启动 BBjServices 以应用更改。插件文件夹保持不变。

| 设置 | 默认 | 描述 |
|---|---|---|
| `--yes` | 关闭 | 跳过确认。 |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | `reset` 恢复的 BBjServices 属性文件。 |

该命令会打印所做的操作并询问确认，除非提供 `--yes`。在 Windows 上，`reset` 在未提供 `--yes` 的情况下返回退出代码 `2`，然后登录之前会中断。

:::warning 请先备份
`reset` 无法撤消。它移除 BBjShip 部署的每个应用程序的文件夹并结束其运行会话。在运行之前，请保留每个应用程序的部署档案，例如构建的 `target/bbj/<publish name>.zip`，并备份 `<BBj home>/cfg/BBj.properties`。如果文件的副本保存在 BBjShip 运行时文件夹中，`reset` 会将该副本放回 `cfg/BBj.properties`，因此您在第一次开发运行之后对文件所做的任何更改都将丢失。此后，请重新启动 BBjServices 以应用恢复的 Java 设置。
:::

## 退出代码 {#exit-codes}

| 代码 | 意义 |
|---|---|
| `0` | 命令成功。 |
| `1` | 命令失败并打印原因，或者 `reset` 被取消。 |
| `2` | 参数无效，或者命令在 Windows 上需要标准输入。消息显示所需的规范信息。 |

[故障排除](/docs/configuration/bbjship/troubleshooting#command-line) 列出消息以及应对措施。
