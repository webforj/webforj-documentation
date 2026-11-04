---
title: 部署
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 77099210b317740da114b7881490bd09
---
# 部署 <DocChip chip='since' label='26.03' /> {#deploying}

webforJ Maven 插件通过三个目标将应用部署到 [BBjShip](/docs/configuration/bbjship/overview)：

| 目标 | 运行时机 | 功能 |
|---|---|---|
| `bbj-package` | `package` 阶段 | 写入部署归档 `target/bbj/<publish name>.zip` |
| `bbj-deploy` | `install` 阶段 | 写入归档并将其部署到 BBjShip |
| `bbj-undeploy` | 手动运行 | 从 BBjServices 中移除应用 |

只有在插件声明了 `<extensions>true</extensions>` 和 `<bbj>` 块时，这些阶段才会生效。请参阅[配置项目](/docs/configuration/bbjship/existing-projects)。

## 部署 {#deploy}

```bash
mvn install
```

如果没有启用扩展，请运行 `mvn package webforj:bbj-deploy`。部署完成后，目标会输出应用的地址：

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

部署失败时，先前的版本会继续运行。

:::warning 替换应用
部署会替换已具有相同发布名称的应用。先前的部署文件夹会被移除，该应用正在运行的会话也会结束。如果之后可能需要回退，请保留先前版本的归档。
:::

[故障排除](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy)列出了部署失败时的消息，以及相应的处理方法。

## 打包但不部署 {#package-without-deploying}

`bbj-package` 会写入相同的归档，但不需要部署 URL 或凭据。CI 作业可以将 `target/bbj/<publish name>.zip` 附加到构建产物中，然后由 BBj 管理员在 BBj 计算机上使用 [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps) 进行部署。

## 取消部署 {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

应用会停止运行，并连同其文件一起从 Enterprise Manager 中移除。

:::warning 无法撤销
`bbj-undeploy` 会结束应用正在运行的会话，并删除其部署文件夹。此操作无法撤销。如果之后可能需要再次部署应用，请保留 `target/bbj/<publish name>.zip` 中的归档。
:::

## 发布名称 {#publish-names}

发布名称默认为构建的最终名称，并显示在应用地址中。它必须以字母或数字开头，只能包含字母、数字、`_`、`.` 和 `-`，且不能以点结尾。归档写入前会拒绝保留名称，例如 BBjServices 路径 `webapp` 和 `files`，或 Windows 设备名称 `con`。

每个入口类只能属于一个已部署的应用。若尝试使用第二个名称部署该类，将会失败并显示：

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## 从另一台计算机部署 {#deploying-from-another-machine}

从另一台计算机（例如 CI 服务器）部署时，需要通过 HTTPS 使用授权令牌：

1. 在 BBj 计算机上，使用 SSL 端口[启用端点](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint)。
2. 在 Enterprise Manager 中，前往 Security 下的 Authentication Tokens 创建令牌。也可以在此处续期或撤销令牌。
3. 将令牌传递给构建：

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

BBjServices Web 服务器的证书必须与 URL 中的主机名匹配，或者位于构建所用的 Java 信任库中。Java 不信任 BBj 的默认证书，因为该证书是自签名的。

向另一台计算机指定 `http://` URL 时，双方都会拒绝请求。目标会在发送任何内容之前停止，而 BBjShip 会对从另一台计算机通过 HTTP 发来的请求返回 HTTP 403。如果 URL 指向 BBj 计算机本身（`localhost` 或其自身地址之一），则可以通过 HTTP 使用。

如果仍要通过 HTTP 向另一台计算机部署（例如部署到自己计算机上的虚拟机），则必须在双方都允许。在 BBj 计算机上，使用端点端口运行 `bbjship remote enable --allow-unencrypted`。在构建中，设置 `<deployment><allowUnencrypted>true</allowUnencrypted>`，或者传入以下属性：

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=http://bbj.example.com:8888/webforj-bbjship \
  -Dwebforj.bbj.deployment.allowUnencrypted=true
```

之后，目标会输出警告并继续部署。在此连接上，凭据会以可读形式通过网络传输。

## 应用设置 {#app-settings}

每次部署时，BBjShip 都会读取应用的 `webforj.conf`，并将其中的这些属性应用到 Enterprise Manager 中的应用。重新部署时会应用已更改的值。未设置的属性将保留 BBjServices 的默认值。

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| 属性 | 默认值 | 说明 |
|---|---|---|
| `webforj.assetsSource` | 未设置 | `packaged` 会从应用中通过 `/webforj/<publish name>/` 提供 `ws://` 静态文件和 `icons://` 图标。如果未设置该属性，使用 `icons://` 的应用将无法启动。 |
| `webforj.showClientConfirmationDialog` | `false` | 浏览器是否会在页面关闭前请求确认。 |
| `webforj.disallowConsole` | `true` | 是否禁止在应用中使用控制台。 |
| `webforj.secure` | BBjServices 默认值 | 应用是否通过安全连接运行。 |
| `webforj.configFile` | BBjServices 默认值 | 应用运行时所用 BBj 配置文件的路径，BBjServices 会在 BBj 计算机上读取该文件。路径会按原样传递，因此可以指定仅服务器上存在的文件。 |
| `webforj.programArguments` | 无 | 应用的程序参数，可以是字符串列表，也可以是单个字符串。 |

`webforj.conf` 无效时，部署会失败并返回 HTTP 400 和原因说明。

## 设置 {#settings}

设置位于 `webforj-maven-plugin` 配置的 `<bbj>` 块中，如[构建插件](/docs/configuration/bbjship/existing-projects#the-build-plugin)所示。命令行中传入的每个属性都会覆盖对应元素的值。

| Maven 元素 | Maven 属性 | 默认值 | 说明 |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | 自动检测 | BBj 安装文件夹，供 `bbj-setup` 和 `watch` 使用。 |
| `<publishName>` | `webforj.bbj.publishName` | 构建的最终名称 | 应用发布时使用的名称，请参阅[发布名称](#publish-names)。 |
| `<deployment><url>` | `webforj.bbj.deployment.url` | 无，必填 | BBjShip 的地址，格式为 `http://<host>:<HTTP port>/webforj-bbjship` 或 `https://<host>:<SSL port>/webforj-bbjship`。 |
| `<deployment><allowUnencrypted>` | `webforj.bbj.deployment.allowUnencrypted` | `false` | 允许 `bbj-deploy` 和 `bbj-undeploy` 通过指向另一台计算机的 `http://` URL 发送凭据，请参阅[从另一台计算机部署](#deploying-from-another-machine)。 |
| `<admin><username>` | `webforj.bbj.admin.username` | 用户名和密码均未提供时为 `admin` | BBj 管理员用户名。 |
| `<admin><password>` | `webforj.bbj.admin.password` | 用户名和密码均未提供时为 `admin123` | BBj 管理员密码。 |
| `<admin><token>` | `webforj.bbj.admin.token` | 空 | 用于 `bbj-deploy` 和 `bbj-undeploy` 的授权令牌。优先级高于用户名和密码。 |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | [开发运行](/docs/configuration/deploy-reload/bbjservices)写入热替换设置的 BBjServices 属性文件。 |
| `<development><classFolder>` | — | 构建输出目录 | 开发运行的类文件夹，BBjServices 会读取此文件夹。 |
| — | `webforj.bbj.force` | `false` | 允许 `bbj-setup` 替换已安装的其他版本 BBjShip。仅限属性使用。 |

只提供用户名而未提供密码，或只提供密码而未提供用户名，都会导致目标停止运行。
