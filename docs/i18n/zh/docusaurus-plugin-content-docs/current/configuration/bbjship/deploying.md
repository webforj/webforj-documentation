---
title: 部署
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 19fc6feafc7d9e17580d710ced7b962b
---
# 部署 <DocChip chip='since' label='26.03' /> {#deploying}

webforJ Maven 插件将应用程序部署到 [BBjShip](/docs/configuration/bbjship/overview)，有三个目标：

| 目标 | 运行 | 功能 |
|---|---|---|
| `bbj-package` | 在 `package` 阶段 | 编写部署归档 `target/bbj/<发布名称>.zip` |
| `bbj-deploy` | 在 `install` 阶段 | 编写归档并将其部署到 BBjShip |
| `bbj-undeploy` | 手动 | 从 BBjServices 中移除应用程序 |

当插件声明为 `<extensions>true</extensions>` 并包含 `<bbj>` 块时，阶段适用，详见 [配置项目](/docs/configuration/bbjship/existing-projects)。

## 部署 {#deploy}

```bash
mvn install
```

如果没有扩展，运行 `mvn package webforj:bbj-deploy`。目标在部署完成时打印应用程序的地址：

```text
[INFO] BBj 部署清单在 http://localhost:8888/webapp/inventory 完成
```

当部署失败时，先前的版本仍会继续运行。

:::warning 替换应用程序
部署将替换已具有相同发布名称的应用程序。先前的部署文件夹会被移除，该应用程序的运行会话结束。如果可能需要恢复先前版本，请保留归档。
:::

[故障排除](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) 列出了失败部署的消息及其应对措施。

## 无需部署进行打包 {#package-without-deploying}

`bbj-package` 写入相同的归档，没有部署 URL 或凭据。CI 作业可以将 `target/bbj/<发布名称>.zip` 附加到构建中，BBj 管理员将其在 BBj 机器上使用 [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps) 进行部署。

## 取消部署 {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

该应用程序停止并从企业经理中移除，以及其文件。

:::warning 不能恢复
`bbj-undeploy` 结束应用程序的运行会话并删除其部署文件夹。此操作不可恢复。如果您可能要再次部署该应用程序，请保留来自 `target/bbj/<发布名称>.zip` 的归档。
:::

## 发布名称 {#publish-names}

发布名称默认为构建的最终名称，并出现在应用地址中。它以字母或数字开头，仅包含字母、数字、 `_`、 `.` 和 `-`，且不以点结尾。保留的名称，例如 BBjServices 路径 `webapp` 和 `files` 或 Windows 设备名称 `con`，在归档写入之前会被拒绝。

每个条目类属于一个已部署的应用程序。用第二个名称部署时将失败，并显示：

```text
BBj 部署失败，HTTP 409。条目类 com.example.Application 已以 inventory 发布。请先取消部署 inventory
```

## 从另一台机器部署 {#deploying-from-another-machine}

从另一台机器（如 CI 服务器）的部署使用通过 HTTPS 的授权令牌：

1. 在 BBj 机器上， [启用端点](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) ，并使用 SSL 端口。
2. 在企业经理中，创建一个位于安全性下的认证令牌，认证令牌。也可以在此更新和撤销。
3. 将令牌传递给构建：

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

BBjServices web 服务器的证书必须与 URL 的主机名匹配，或在构建的 Java 信任存储中。Java 不信任 BBj 默认的自签名证书。发送到另一台机器的 `http://` URL 会将凭据明文发送到网络，目标会为其打印警告。

## 应用程序设置 {#app-settings}

BBjShip 在每次部署时读取应用程序的 `webforj.conf`，并将这些属性应用于企业经理中的应用程序。重新部署会获取更改的值。未设置的属性将保留 BBjServices 的默认值。

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| 属性 | 默认值 | 描述 |
|---|---|---|
| `webforj.assetsSource` | 未设置 | `packaged` 提供来自应用程序的 `ws://` 静态文件和 `icons://` 图标，位于 `/webforj/<发布名称>/`。没有它，使用 `icons://` 的应用程序将无法启动。 |
| `webforj.showClientConfirmationDialog` | `false` | 浏览器在页面关闭前是否要求确认。 |
| `webforj.disallowConsole` | `true` | 是否禁止在应用程序中使用控制台。 |
| `webforj.secure` | BBjServices 默认值 | 应用程序是否通过安全连接运行。 |
| `webforj.configFile` | BBjServices 默认值 | 应用程序运行的 BBj 配置文件路径，BBjServices 在 BBj 机器上读取。路径将按原样传递，因此它可能仅命名服务器拥有的文件。 |
| `webforj.programArguments` | 无 | 应用程序的程序参数，作为字符串列表或单个字符串。 |

无效的 `webforj.conf` 将导致部署失败，并显示 HTTP 400 和原因。

## 设置 {#settings}

设置位于 `webforj-maven-plugin` 配置的 `<bbj>` 块中，如 [构建插件](/docs/configuration/bbjship/existing-projects#the-build-plugin) 所示。每个属性会覆盖命令行上的其元素。

| Maven 元素 | Maven 属性 | 默认值 | 描述 |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | 检测到的 | BBj 安装文件夹，供 `bbj-setup` 和 `watch` 使用。 |
| `<publishName>` | `webforj.bbj.publishName` | 构建的最终名称 | 应用程序发布的名称，见 [发布名称](#publish-names)。 |
| `<deployment><url>` | `webforj.bbj.deployment.url` | 无，必填 | BBjShip 的地址，`http://<主机>:<HTTP 端口>/webforj-bbjship` 或 `https://<主机>:<SSL 端口>/webforj-bbjship`。 |
| `<admin><username>` | `webforj.bbj.admin.username` | 当用户名称和密码均缺失时为 `admin` | BBj 管理员用户名。 |
| `<admin><password>` | `webforj.bbj.admin.password` | 当用户名和密码均缺失时为 `admin123` | BBj 管理员密码。 |
| `<admin><token>` | `webforj.bbj.admin.token` | 空 | `bbj-deploy` 和 `bbj-undeploy` 的授权令牌。优先于用户名和密码。 |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServices 属性文件，写入热替换设置的 [开发运行](/docs/configuration/deploy-reload/bbjservices)。 |
| `<development><classFolder>` | — | 构建输出目录 | 开发运行的类文件夹，供 BBjServices 读取。 |
| — | `webforj.bbj.force` | `false` | 允许 `bbj-setup` 替换另一版本的已安装 BBjShip。仅属性。 |

没有密码的用户名，或没有用户名的密码，会中止目标。
