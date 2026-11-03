---
title: 命令行
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: a80e91a6626a5c018604f40e37a5ff00
---
# 命令行 <DocChip chip='since' label='26.03' /> {#command-line}

`bbjship` 命令行用于在 BBj 机器上管理 BBjShip。它位于 `<BBj home>/plugins/webforj-bbjship`，在 Windows 上对应 `bbjship.cmd`。每条命令都在 BBjServices 中运行，因此执行任何命令前都必须先启动 BBjServices。

```text
bbjship <group> <command> [options]
```

## 命令 {#commands}

| 命令 | 说明 |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | 启用或更新部署端点，并打印其地址。 |
| [`remote disable`](#remote-disable) | 移除部署端点。已部署的应用会继续运行。 |
| [`remote status`](#remote-status) | 打印端点地址、是否启用了开发运行，以及是否允许来自其他机器的未加密请求。 |
| [`apps deploy ARCHIVE`](#apps-deploy) | 部署部署归档包。 |
| [`apps list [--long]`](#apps-list) | 列出 BBjShip 部署的应用。 |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | 移除 BBjShip 部署的单个或全部应用。 |
| [`development enable`, `development disable`](#development) | 在此机器上启用或关闭[开发运行](/docs/configuration/deploy-reload/bbjservices)。 |
| [`reset [--yes] [--properties FILE]`](#reset) | 移除所有应用以及 BBjShip 设置的所有内容。 |

## 通用选项 {#options}

| 设置 | 默认值 | 说明 |
|---|---|---|
| `--username USER` | 未设置 | BBj 管理员用户名。 |
| `--password PASSWORD` | 未设置 | BBj 管理员密码。 |
| `--password-stdin` | 关闭 | 从标准输入的第一行读取密码。Windows 上不可用。 |
| `--help`, `-h` | | 打印命令行或某条命令的用法。 |
| `--version`, `-V` | | 打印 BBjShip 版本。 |

通过 `--password` 提供的密码会在命令运行期间显示在进程列表中，供这台机器上的其他用户查看。在 macOS 和 Linux 上，脚本可以改为通过标准输入传递密码：

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

在所有系统上，都可以使用环境变量，避免密码出现在命令行中：

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Windows 上无法使用标准输入，因此 `--password-stdin` 会以退出代码 `2` 结束。请将密码作为值传递，或通过环境变量传递：

```text
bbjship.cmd remote enable --username admin --password <value>
```

同时指定 `--password` 和 `--password-stdin`、仅指定用户名而未指定密码，或仅指定密码而未指定用户名，都会以退出代码 `2` 结束，并显示说明需要指定哪些内容的消息。

如果未提供凭据，命令会使用出厂账户 `admin` 和密码 `admin123`，并打印警告。

## 环境变量 {#environment-variables}

| 变量 | 默认值 | 说明 |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | 未设置 | 未提供 `--username` 时使用的用户名。 |
| `WEBFORJ_BBJ_PASSWORD` | 未设置 | 未提供 `--password` 或 `--password-stdin` 时使用的密码。 |
| `NO_COLOR` | 未设置 | 关闭彩色输出。 |

## 远程管理 {#remote}

`remote` 命令用于管理部署端点。

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

启用或更新部署端点，并打印其地址。

| 设置 | 默认值 | 说明 |
|---|---|---|
| `--port PORT` | `8888` | 端点使用的 BBjServices Web 服务器 HTTP 端口。 |
| `--ssl-port PORT` | 无 | 端点使用的 BBjServices Web 服务器 SSL 端口。如果未指定，端点就没有 HTTPS 地址。 |
| `--allow-unencrypted` | 关闭 | 允许从其他机器通过 HTTP 提交管理凭据。如果未指定，来自其他机器的请求必须使用 SSL 端口，否则端点会返回 HTTP 403。无论是否指定此选项，来自 BBj 机器本身的请求都会使用 HTTP。 |

这些端口必须是 BBjServices Web 服务器已在监听的端口。否则，`remote enable` 会以退出代码 `1` 结束，指出无响应的端口，并保留之前的端点。

每次运行 `remote enable` 都会重新记录端口和 `--allow-unencrypted` 设置。不带该选项运行时，会关闭此设置并打印 `Refused unencrypted requests from another machine`。带该选项运行时，会打印 `Allowed unencrypted requests from another machine`。从其他机器通过 HTTP 部署的构建还需要设置 [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine)。

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

移除部署端点。已部署的应用会继续运行。

<!-- vale Google.WordListCase = NO -->
:::warning 构建无法部署
`remote disable` 会移除部署端点。在再次运行 `remote enable` 之前，部署到该端点的构建和 CI 作业都会失败。禁用端点前，请记下端口和端点地址。已部署的应用会继续运行。
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

打印端点地址、是否启用了开发运行，以及是否允许来自其他机器的未加密请求。

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## 应用 {#apps}

`apps` 命令只会列出和移除 BBjShip 部署的应用，不会影响在 Enterprise Manager 中注册的应用。

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

部署部署归档包。`apps deploy` 会安装由 [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying) 生成的归档包，无需部署端点。去掉 `.zip` 后的文件名就是[发布名称](/docs/configuration/bbjship/deploying#publish-names)。BBjServices 会读取该归档包，因此 BBjServices 的操作系统用户必须具有读取权限。

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning 会替换应用
使用已部署应用的名称部署归档包，会替换该应用。之前的部署文件夹会被移除，应用正在运行的会话也会结束。如果之后可能需要回退，请保留之前的归档包。
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

列出 BBjShip 部署的应用。

| 设置 | 默认值 | 说明 |
|---|---|---|
| `--long`, `-l` | 关闭 | 同时显示每个应用的部署文件夹和类文件夹。 |

```text
$ bbjship apps list
NAME       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

移除 BBjShip 部署的单个或全部应用。

| 设置 | 默认值 | 说明 |
|---|---|---|
| `--all` | 关闭 | 取消部署 BBjShip 部署的所有应用。 |

:::warning 无法撤销
`apps undeploy` 会从 Enterprise Manager 中移除该应用、结束其正在运行的会话，并删除其部署文件夹。`apps undeploy --all` 会对 BBjShip 部署的所有应用执行这些操作。此操作无法撤销。请保留每个应用的部署归档包，例如 `target/bbj/<publish name>.zip`，以便重新部署。
:::

## 开发 {#development}

新安装中默认关闭开发运行；在同一台机器上首次运行 `webforj:watch` 时会启用开发运行。`bbjship development disable` 会关闭开发运行，直到这台机器上再次运行 `webforj:watch` 才会重新启用。从其他机器发起的开发运行会被拒绝，因为它首先执行的设置操作只能在 BBj 机器上运行。

### `development enable` {#development-enable}

```text
bbjship development enable
```

在此机器上启用[开发运行](/docs/configuration/deploy-reload/bbjservices)。

### `development disable` {#development-disable}

```text
bbjship development disable
```

在此机器上关闭[开发运行](/docs/configuration/deploy-reload/bbjservices)。

## 重置 {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` 会取消部署 BBjShip 部署的所有应用，移除部署端点、开发开关和 `--allow-unencrypted` 设置，恢复开发运行修改过的 BBjServices 属性文件，并删除 BBjShip 写入的文件。此命令不会停止 BBjServices。恢复属性文件后，请重启 BBjServices 以应用更改。插件文件夹会保留。

| 设置 | 默认值 | 说明 |
|---|---|---|
| `--yes` | 关闭 | 跳过确认步骤。 |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | `reset` 要恢复的 BBjServices 属性文件。 |

除非指定 `--yes`，否则命令会打印操作内容并要求确认。在 Windows 上，不带 `--yes` 运行 `reset` 会在登录前以退出代码 `2` 结束。

:::warning 请先备份
`reset` 无法撤销。它会移除 BBjShip 部署的所有应用的文件夹，并结束这些应用正在运行的会话。运行前，请保留每个应用的部署归档包，例如构建生成的 `target/bbj/<publish name>.zip`，并备份 `<BBj home>/cfg/BBj.properties`。如果 BBjShip 运行时文件夹中保存了该文件的副本，`reset` 会将该副本覆盖回 `cfg/BBj.properties`，因此首次开发运行后对该文件所做的更改都会丢失。之后请重启 BBjServices，以应用恢复的 Java 设置。
:::

## 退出代码 {#exit-codes}

| 代码 | 含义 |
|---|---|
| `0` | 命令执行成功。 |
| `1` | 命令执行失败并打印了原因，或 `reset` 已取消。 |
| `2` | 参数无效，或命令在 Windows 上需要标准输入。消息会指出需要指定的内容。 |

[故障排除](/docs/configuration/bbjship/troubleshooting#command-line)列出了各条消息及相应的处理方法。
