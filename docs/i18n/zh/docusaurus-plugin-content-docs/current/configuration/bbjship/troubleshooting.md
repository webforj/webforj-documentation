---
title: 故障排除
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: 2db5dad70f56c06a6aab75752a9c260f
---
# 故障排查 <DocChip chip='since' label='26.03' /> {#troubleshooting}

本页列出了设置 BBjShip、部署应用、启动开发运行或使用 `bbjship` 命令行时可能遇到的消息。对于每条消息，本页都会说明原因和处理方法。

Maven 目标执行失败时，构建会以一条简短消息结束：先显示目标名称，再说明原因。本页列出的就是原因部分：

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## 凭据和连接 {#credentials-and-connection}

这些消息可能来自所有与 BBjShip 通信的目标：`bbj-setup`、`bbj-deploy`、`bbj-undeploy` 和 `watch`。

| 消息 | 原因 | 处理方法 |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | 用户名和密码均未配置，因此该目标使用 `admin` 和出厂密码 `admin123` 登录。 | 在 Enterprise Manager 中更改密码并配置新凭据，参见[设置](/docs/configuration/bbjship/deploying#settings)。 |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | 用户名和密码只配置了其中一项。 | 配置两项，或都不配置以使用出厂账户。`bbj-deploy` 和 `bbj-undeploy` 也接受令牌。 |
| `BBj deployment requires bbj.deployment.url...` | 未配置部署 URL。 | 设置 `<bbj><deployment><url>`，或传入 `-Dwebforj.bbj.deployment.url=<URL>`。 |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | URL 使用了其他协议，或包含用户名、查询参数或片段。 | 使用 `http://<host>:<port>/webforj-bbjship` 或 `https://<host>:<port>/webforj-bbjship`，并将凭据放在 `<admin>` 中。 |
| `Connect to ... failed: Connection refused` | 部署 URL 指定的主机和端口上没有服务响应。 | 启动 BBjServices，并确认 URL 中的主机和端口与 BBjServices Web 服务器相符。 |
| `could not read the BBjShip version at <URL>, HTTP 404...` | URL 未指向 BBjShip，或尚未安装 BBjShip，或该端口未启用其端点。 | 检查路径 `/webforj-bbjship` 和端口。在 BBj 计算机上运行 `mvn webforj:bbj-setup`，或者在服务器上运行 `bbjship remote enable`。 |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices 拒绝了用户名和密码，或令牌。 | 检查凭据。在 Enterprise Manager 中续期已过期或已撤销的令牌。 |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | 已安装的 BBjShip 与 `webforj-maven-plugin` 版本不同。 | 在自己的计算机上运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。在服务器上，使用 BBj Plugin Manager 安装匹配版本的 BBjShip。 |
| `could not establish a trusted HTTPS connection to <URL>...` | Java 不信任 BBjServices Web 服务器的证书。 | 在 BBj 计算机上为 URL 的主机名安装证书，或将其证书添加到构建所用的 Java 信任库中。 |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | 部署 URL 是指向另一台计算机的 `http://` URL。该目标会在发送任何内容之前停止。使用 `allowUnencrypted` 时，同一消息会作为警告显示，且目标会继续执行。 | 使用带 SSL 端口的 `https://` URL，或在两端都允许 HTTP，参见[从另一台计算机部署](/docs/configuration/bbjship/deploying#deploying-from-another-machine)。 |

## 设置 {#setup}

这些消息来自 `mvn webforj:bbj-setup`，以及项目的首次[开发运行](/docs/configuration/deploy-reload/bbjservices)。首次开发运行会以相同方式设置 BBjShip。

| 消息 | 原因 | 处理方法 |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | 目标执行失败，后面会显示原因。如果原因与制品解析有关，说明 Maven 无法下载与 `webforj-maven-plugin` 版本相同的 `webforj-bbjship-plugin`。 | 阅读原因。如果是制品解析问题，请检查构建使用的仓库和网络连接。 |
| `BBj setup is local only. Run setup on the BBj host` | 部署 URL 指向另一台计算机。 | 在 BBj 计算机上运行设置，或使用 [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager) 在该计算机上安装 BBjShip。 |
| `BBj setup cannot resolve the deployment URL host <host>...` | 无法识别部署 URL 的主机名。 | 使用 `localhost` 或本机名称。 |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | 开发运行配置了令牌。开发运行只能使用用户名和密码登录。 | 配置 `<admin><username>` 和 `<admin><password>`。 |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | 该目标未在本机找到 BBj 安装。 | 将 `<bbj><home>`、`-Dwebforj.bbj.home` 或 `BBJ_HOME` 变量设为 BBj 安装文件夹。 |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | 配置的文件夹不是完整的 BBj 安装目录。 | 将设置指向包含 BBj 的 `bin` 和 `cfg` 文件夹的目录。 |
| `Installed BBjShip version <version> does not match <version>...` | 已安装了其他版本的 BBjShip，例如升级 webforJ 后出现此情况。 | 运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。已部署的应用会保留。 |
| `Installed BBjShip version is missing...` | 已安装的 BBjShip 文件夹不完整。 | 运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。 |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | BBj 的 `plugins` 文件夹或其中的 BBjShip 文件夹是符号链接，或包含符号链接。 | 安装到普通文件夹中。 |
| `BBj setup command failed. Check credentials and local BBjServices.` 后跟命令输出 | 用于启用部署端点的命令执行失败。输出会说明原因，例如登录被拒绝或 Web 服务器未监听该端口。 | 检查凭据、BBjServices 是否正在运行，以及部署 URL 的端口是否为 BBjServices Web 服务器使用的端口。 |
| `BBj setup command timed out. Check local BBjServices` | 命令在 120 秒内未完成。 | 检查 BBjServices 是否正在运行并能正常响应，然后再次运行该目标。 |
| `Previous BBjShip files are preserved at <folder>` | 替换操作失败，旧版本的文件已保留在该文件夹中。 | 修复失败原因，然后再次运行该目标。 |

## 部署和取消部署 {#deploy-and-undeploy}

### 上传之前 {#before-the-upload}

这些消息来自 `bbj-package`、`bbj-deploy` 和 `bbj-undeploy`，此时尚未有任何内容发送到 BBjShip。

| 消息 | 原因 | 处理方法 |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | 项目打包类型不是 jar。 | 设置 `<packaging>jar</packaging>`。 |
| `BBj deployment requires the packaged application jar...` | 运行该目标时，同一次构建中未执行 `package` 阶段。 | 运行 `mvn install`，或运行 `mvn package webforj:bbj-deploy`。 |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | 应用的运行时类路径中包含 `webforj-engine`。 | 将其从 `webforj` 依赖项中排除，参见[不使用安装插件](/docs/configuration/bbjship/existing-projects#without-the-install-plugin)。 |
| `BBj deployment requires webforj-foundation on the runtime classpath` | 应用在运行时没有 webforJ 依赖项。 | 添加作用域为 `compile` 的 `webforj` 依赖项。 |
| `BBj deployment dependency is unresolved...` | 应用的某个依赖项没有对应文件。 | 确认 Maven 能解析项目的所有依赖项。 |
| `Publish name <name> is invalid...` | 发布名称不符合[发布名称](/docs/configuration/bbjship/deploying#publish-names)中的规则。 | 选择其他 `<publishName>`。 |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices 将该名称用于自身地址。 | 选择其他 `<publishName>`。 |
| `BBj deployment archive could not be written...` | `bbj-package` 无法写入 `target/bbj/<publish name>.zip`。后面会显示原因。 | 阅读原因。 |

### BBjShip 的响应 {#answers-of-bbjship}

BBjShip 拒绝部署或取消部署时，消息以 `BBj deployment failed with HTTP <status>` 或 `BBj undeployment failed with HTTP <status>` 开头，后面跟着原因。

| 状态码 | 原因 | 原因说明 | 处理方法 |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` 或 `...is reserved by BBjServices...` | 发布名称被拒绝。 | 选择其他发布名称。 |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | 无法读取应用的 `webforj.conf`，或某个[应用设置](/docs/configuration/bbjship/deploying#app-settings)的类型不正确。 | 按照详细信息修复文件。 |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` 或其他与归档有关的原因 | 该归档不是由构建插件生成的，或者生成后被修改过。 | 部署由构建生成的归档，并确保其 webforJ 版本与 BBjShip 相同。 |
| 401 | `Check the BBj administration credentials or token` | 登录被拒绝。 | 检查凭据或续期令牌。 |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | 请求通过来自另一台计算机的 HTTP 连接到达。 | 使用端点的 HTTPS 地址，或在 BBj 计算机上运行 `bbjship remote enable --allow-unencrypted`。 |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | 开发运行请求到达了一台已关闭开发运行的计算机。 | 在 BBj 计算机上运行 `bbjship development enable`。 |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | 取消部署请求指定的应用并非由 BBjShip 部署。 | 使用 `bbjship apps list` 检查发布名称。 |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | 应用的入口类已使用另一个发布名称运行。 | 先取消部署另一个应用，或使用该应用的名称进行部署。 |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | 已在 Enterprise Manager 中注册的应用使用了该名称。 | 选择其他发布名称，或在 Enterprise Manager 中移除该应用。 |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | 另一个应用使用了仅大小写不同的同一名称。 | 选择其他发布名称。 |
| 409 | `<name> is a development run. Stop the watch first` | 该应用的开发运行仍处于活动状态。 | 按 <kbd>Ctrl</kbd>+<kbd>C</kbd> 停止 watch，然后再部署。 |
| 409 | `<name> is deployed from an archive. Undeploy it first` | 开发运行使用了已部署应用的名称。 | 先取消部署该应用，或为开发运行设置其他 `<publishName>`。 |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | 归档大于 BBjShip 允许的大小。 | 移除应用运行时不需要的依赖项。 |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices 在安装或移除应用时发生错误。 | 查看 BBjServices 日志。 |

如果部署以 `did not confirm the requested application. Check the BBjShip version and BBjServices log` 结束，说明请求已到达 BBjShip，但未收到应用确认。请检查 BBjShip 和 `webforj-maven-plugin` 的版本是否相同，然后查看 BBjServices 日志。

### 正在运行的应用 {#the-running-app}

| 消息 | 原因 | 处理方法 |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | 应用使用了 `icons://`，但未打包资源。 | 在 `webforj.conf` 中添加 `webforj.assetsSource = packaged`，然后重新部署。 |

## 开发运行 {#development-runs}

这些消息来自配置为使用 BBjShip 的项目中的 `mvn` 或 `mvn compile webforj:watch`。开发运行失败时，消息会以 `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine` 开头，后面跟着来自[设置](#setup)、[凭据和连接](#credentials-and-connection)或 [BBjShip 的响应](#answers-of-bbjship)的原因。

| 消息 | 原因 | 处理方法 |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | watch 在项目编译前就已启动。 | 在原型项目中运行 `mvn compile webforj:watch`，或运行 `mvn`。 |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` 指定的文件夹无法由 BBjServices 找到。 | 设置 BBj 计算机上类文件夹的绝对路径。 |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | 运行程序要查找的位置没有 BBjServices 属性文件。 | 将 `<bbj><properties>` 或 `-Dwebforj.bbj.properties` 设为该文件的路径。 |
| `BBjServices did not stop within 180 seconds` | 为应用热交换设置，开发运行请求停止 BBjServices，但 BBjServices 一直未停止。 | 手动停止 BBjServices，再重新启动，然后再次运行 watch。 |
| `BBjServices start program is missing: <program>` | BBj 安装中没有用于启动 BBjServices 的程序。 | 手动启动 BBjServices，然后再次运行 watch。 |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices 重启后未能恢复运行。 | 手动启动 BBjServices，然后再次运行 watch。 |
| `BBjShip did not answer within 120 seconds` | BBjServices 已启动，但部署端点没有响应。 | 检查 BBjServices 日志，然后再次运行 watch。 |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices 启动时使用的 Java 参数与写入属性文件中的参数不同。 | 在 Enterprise Manager 和该文件中检查 BBjServices 的 Java 设置。 |
| `webforJ watch: development run of <name> was not stopped. <reason>` | 构建停止时无法移除应用。 | 使用 `bbjship apps undeploy <name>` 移除应用，或重新启动 watch；重新启动会替换该应用。 |

首次添加热交换设置的开发运行会重启 BBjServices。在 macOS 和 Linux 上，它会通过 `bin/bbjservices` 启动 BBjServices。在 Windows 上，如果已注册 `BBjServices` Windows 服务，则会启动该服务；否则会运行 `bin\BBjServices.exe -noservice`。通过 `bin/bbjservices` 或 `-noservice` 启动的 BBjServices 会在操作系统服务管理器之外运行，直到计算机重启。

## 命令行 {#command-line}

每条 `bbjship` 命令都在 BBjServices 内运行，因此 BBjServices 必须处于运行状态。若未运行，命令将无法启动。

无效参数会以退出代码 `2` 和一条指出需要指定什么的消息结束：

| 消息 | 处理方法 |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | 指定命令，参见[命令行](/docs/configuration/bbjship/command-line)。 |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | 使用该命令支持的选项；可通过 `--help` 查看选项列表。 |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | 为选项提供一个值。 |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | 提供端口号。 |
| `Specify <option> only once` | 移除重复的选项。 |
| `Specify only one of --password and --password-stdin` | 只保留其中一个选项。 |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | 同时提供用户名和密码。消息会指出缺少哪一项。 |
| `Specify an application name or --all`, `Specify one application name` | 为 `apps undeploy` 提供一个发布名称，或指定 `--all`。 |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | 为 `apps deploy` 提供一个 `.zip` 文件。 |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | 检查路径。文件由 BBjServices 读取，因此 BBjServices 的操作系统用户必须有权读取该文件。 |
| `Development archives are deployed by the watch, not by apps deploy` | 部署由 `bbj-package` 生成的归档，而不是开发运行生成的归档。 |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | 重命名归档。去掉 `.zip` 后的文件名就是发布名称。 |

命令执行失败时，会以退出代码 `1` 和 `Command failed.` 结束，后面跟着原因：

| 原因 | 原因说明 | 处理方法 |
|---|---|---|
| BBjServices 登录失败 | 用户名或密码错误。 | 检查凭据。 |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` 或 `--ssl-port` 指定的端口不是 BBjServices Web 服务器正在监听的端口。原有端点会保留。 | 使用 Enterprise Manager 中为 Web 服务器配置的端口。 |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` 指定的应用并非由 BBjShip 部署。 | 使用 `bbjship apps list` 检查名称。 |
| `Another deployment is in progress` | 另一项部署或取消部署操作占用该计算机已达 30 秒。 | 等待其完成，然后再次运行命令。 |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Enterprise Manager 中的另一个应用使用了 Web 上下文 `webforj-bbjship` 或 `webforj-bbjship-assets`。 | 在 Enterprise Manager 中重命名或移除该上下文。 |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Enterprise Manager 中的类路径 `_webforj_bbjship_cli` 或 `_webforj_bbjship` 已被修改。 | 在 Enterprise Manager 中移除这两个类路径，然后再次运行命令；命令会重新创建它们。 |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | BBjShip 文件夹不完整。 | 重新安装 BBjShip。 |
| `BBjServices does not manage web contexts. Update BBj` | BBj 版本过旧，无法支持 BBjShip。 | 安装与 webforJ 匹配的 BBj 版本。 |

如果对 `Continue? [y/N]` 的回答不是 `y` 或 `Y`，`reset` 会以退出代码 `1` 和 `Reset cancelled` 结束。恢复 BBjServices 的 Java 设置后，命令会以 `Restart BBjServices now to apply the restored Java settings` 结束。重启 BBjServices，使其在不使用热交换设置的情况下运行。

## Windows {#windows}

| 情况 | 处理方法 |
|---|---|
| 使用 BBjShip 程序运行 `bbj.exe` 时没有任何输出。 | 在 `<BBj home>\plugins\webforj-bbjship` 中运行 `bbjship.cmd`，它会显示命令输出。 |
| `--password-stdin` 以退出代码 `2` 和 `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD` 结束。 | 使用 `--password` 传递密码，或设置 `WEBFORJ_BBJ_USERNAME` 和 `WEBFORJ_BBJ_PASSWORD`。 |
| `reset` 以退出代码 `2` 和 `Standard input is not available on Windows. Run reset --yes to confirm` 结束。 | 运行 `bbjship.cmd reset --yes`。 |
| `apps undeploy` 或 `reset` 输出 `Not removed: <folder>. <reason>`，并以退出代码 `0` 结束。 | BBjServices 仍持有该应用的文件。该应用已从 Enterprise Manager 中移除。重启 BBjServices。下次部署同名应用时会一并处理该文件夹，也可以在重启后手动删除。 |
| 在已安装的版本上安装 BBjShip 失败，因为其文件正在使用中。 | BBjServices 仍持有已安装的 BBjShip 文件。重启 BBjServices，然后重新安装。 |
| 开发运行重启 BBjServices 后，BBjServices 未以 Windows 服务方式运行。 | 由于未注册 `BBjServices` 服务，开发运行使用 `BBjServices.exe -noservice` 启动了它。它会在 Windows 服务管理器之外运行，直到计算机重启。 |
| `bbjship.cmd` 命令无法启动。 | 启动 BBjServices。运行任何 `bbjship` 命令都需要 BBjServices 正在运行。 |
