---
title: 故障排除
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: c517c84b00b3b7eb6bb9d485c528c4f7
---
# 故障排除 <DocChip chip='since' label='26.03' /> {#troubleshooting}

本页列出了设置 BBjShip、部署应用、启动开发运行或使用 `bbjship` 命令行时可能遇到的消息。对于每条消息，本页都会说明其原因及应对方法。

Maven 目标执行失败时，构建会以一条简短消息结束，其中先显示目标名称，再显示原因。本页列出的就是原因部分：

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## 凭据和连接 {#credentials-and-connection}

这些消息来自所有与 BBjShip 通信的目标：`bbj-setup`、`bbj-deploy`、`bbj-undeploy` 和 `watch`。

| 消息 | 原因 | 应对方法 |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | 未配置用户名或密码，因此目标会使用 `admin` 和 `admin123` 登录。 | 在 Enterprise Manager 中更改密码并配置新的凭据，参见[设置](/docs/configuration/bbjship/deploying#settings)。 |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | 只配置了用户名或密码中的一项。 | 两项都配置，或都不配置以使用出厂账户。`bbj-deploy` 和 `bbj-undeploy` 也接受令牌。 |
| `BBj deployment requires bbj.deployment.url...` | 未配置部署 URL。 | 设置 `<bbj><deployment><url>`，或传入 `-Dwebforj.bbj.deployment.url=<URL>`。 |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | URL 使用了其他协议，或包含用户名、查询参数或片段。 | 使用 `http://<host>:<port>/webforj-bbjship` 或 `https://<host>:<port>/webforj-bbjship`，并将凭据放在 `<admin>` 中。 |
| `Connect to ... failed: Connection refused` | 部署 URL 指定的主机和端口上没有服务响应。 | 启动 BBjServices，并确认 URL 中的主机和端口与 BBjServices Web 服务器一致。 |
| `could not read the BBjShip version at <URL>, HTTP 404...` | URL 未指向 BBjShip，或尚未安装 BBjShip，或该端口未启用其端点。 | 检查路径 `/webforj-bbjship` 和端口。在 BBj 计算机上运行 `mvn webforj:bbj-setup`，或在服务器上运行 `bbjship remote enable`。 |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices 拒绝了用户名和密码或令牌。 | 检查凭据。在 Enterprise Manager 中续期已过期或已撤销的令牌。 |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | 已安装的 BBjShip 与 `webforj-maven-plugin` 版本不同。 | 在本机上运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。在服务器上，使用 BBj Plugin Manager 安装匹配版本的 BBjShip。 |
| `could not establish a trusted HTTPS connection to <URL>...` | Java 不信任 BBjServices Web 服务器的证书。 | 在 BBj 计算机上为 URL 的主机名安装证书，或将该证书添加到构建所用的 Java 信任库中。 |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | 部署 URL 是指向另一台计算机的 `http://` URL。目标会在发送任何内容之前停止。启用 `allowUnencrypted` 后，同一消息会变成警告，目标将继续执行。 | 使用带 SSL 端口的 `https://` URL，或在两端都允许 HTTP，参见[从另一台计算机进行部署](/docs/configuration/bbjship/deploying#deploying-from-another-machine)。 |

## 设置 {#setup}

这些消息来自 `mvn webforj:bbj-setup`，以及项目首次[开发运行](/docs/configuration/deploy-reload/bbjservices)时的设置过程；两者设置 BBjShip 的方式相同。

| 消息 | 原因 | 应对方法 |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | 目标执行失败，后面会显示原因。如果原因与工件解析有关，则表示 Maven 无法下载与 `webforj-maven-plugin` 相同版本的 `webforj-bbjship-plugin`。 | 阅读原因。如果是工件解析问题，请检查构建所用的仓库和网络连接。 |
| `BBj setup is local only. Run setup on the BBj host` | 部署 URL 指向另一台计算机。 | 在 BBj 计算机上运行设置，或使用 [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager) 在该计算机上安装 BBjShip。 |
| `BBj setup cannot resolve the deployment URL host <host>...` | 无法识别部署 URL 的主机名。 | 使用 `localhost` 或本机名称。 |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | 开发运行配置了令牌。开发运行只能使用用户名和密码登录。 | 配置 `<admin><username>` 和 `<admin><password>`。 |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | 目标在本机上未找到 BBj 安装。 | 将 `<bbj><home>`、`-Dwebforj.bbj.home` 或 `BBJ_HOME` 变量设置为 BBj 安装文件夹。 |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | 配置的文件夹不是完整的 BBj 安装目录。 | 将设置指向包含 BBj 的 `bin` 和 `cfg` 目录的文件夹。 |
| `Installed BBjShip version <version> does not match <version>...` | 安装了其他版本的 BBjShip，例如在升级 webforJ 后出现这种情况。 | 运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。已部署的应用会保留。 |
| `Installed BBjShip version is missing...` | 已安装的 BBjShip 文件夹不完整。 | 运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。 |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | BBj 的 `plugins` 文件夹或其中的 BBjShip 文件夹是符号链接，或包含符号链接。 | 安装到普通文件夹中。 |
| `BBj setup command failed. Check credentials and local BBjServices.` 后接命令输出 | 用于启用部署端点的命令执行失败。输出中会说明原因，例如登录被拒绝或 Web 服务器未监听指定端口。 | 检查凭据、BBjServices 是否正在运行，以及部署 URL 中的端口是否为 BBjServices Web 服务器的端口。 |
| `BBj setup command timed out. Check local BBjServices` | 命令未能在 120 秒内完成。 | 检查 BBjServices 是否正在运行并能响应，然后再次运行目标。 |
| `Previous BBjShip files are preserved at <folder>` | 替换操作失败，旧版本的文件保留在该文件夹中。 | 修复失败原因，然后再次运行目标。 |

## 部署和取消部署 {#deploy-and-undeploy}

### 上传前 {#before-the-upload}

这些消息来自 `bbj-package`、`bbj-deploy` 和 `bbj-undeploy`，此时尚未有任何内容到达 BBjShip。

| 消息 | 原因 | 应对方法 |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | 项目的打包格式不是 jar。 | 设置 `<packaging>jar</packaging>`。 |
| `BBj deployment requires the packaged application jar...` | 运行目标时，同一次构建中没有执行 `package` 阶段。 | 运行 `mvn install`，或运行 `mvn package webforj:bbj-deploy`。 |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | 应用的运行时类路径中包含 `webforj-engine`。 | 从 `webforj` 依赖项中排除它，参见[不使用 install 插件](/docs/configuration/bbjship/existing-projects#without-the-install-plugin)。 |
| `BBj deployment requires webforj-foundation on the runtime classpath` | 应用在运行时没有 webforJ 依赖项。 | 在 `compile` 作用域中添加 `webforj` 依赖项。 |
| `BBj deployment dependency is unresolved...` | 应用的某个依赖项没有对应文件。 | 检查 Maven 是否解析了项目的所有依赖项。 |
| `Publish name <name> is invalid...` | 发布名称不符合[发布名称](/docs/configuration/bbjship/deploying#publish-names)中的规则。 | 选择其他 `<publishName>`。 |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices 将该名称用于自身地址。 | 选择其他 `<publishName>`。 |
| `BBj deployment archive could not be written...` | `bbj-package` 无法写入 `target/bbj/<publish name>.zip`。原因会随后显示。 | 阅读原因。 |

### BBjShip 的响应 {#answers-of-bbjship}

当 BBjShip 拒绝部署或取消部署请求时，消息会以 `BBj deployment failed with HTTP <status>` 或 `BBj undeployment failed with HTTP <status>` 开头，后面接具体原因。

| 状态 | 原因 | 原因说明 | 应对方法 |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` 或 `...is reserved by BBjServices...` | 发布名称被拒绝。 | 选择其他发布名称。 |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | 无法读取应用的 `webforj.conf`，或某个[应用设置](/docs/configuration/bbjship/deploying#app-settings)的类型不正确。 | 根据详细说明修复文件。 |
| 400 | `The uploaded body is not a BBj deployment archive...`、`Invalid BBj deployment archive entry: <entry>` 或其他与归档有关的原因 | 该归档不是由构建插件写入的，或写入后被修改过。 | 部署构建生成的归档，并确保其 webforJ 版本与 BBjShip 相同。 |
| 401 | `Check the BBj administration credentials or token` | 登录请求被拒绝。 | 检查凭据或续期令牌。 |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | 请求通过 HTTP 从另一台计算机发出。 | 使用该端点的 HTTPS 地址，或在 BBj 计算机上运行 `bbjship remote enable --allow-unencrypted`。 |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | 开发运行请求到达了一台未启用开发运行的计算机。 | 在 BBj 计算机上运行 `bbjship development enable`。 |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | 取消部署请求指定的应用并非由 BBjShip 部署。 | 使用 `bbjship apps list` 检查发布名称。 |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | 应用的入口类已使用另一个发布名称运行。 | 取消部署另一个应用，或使用它的名称进行部署。 |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Enterprise Manager 中已注册的应用使用了该名称。 | 选择其他发布名称，或在 Enterprise Manager 中移除该应用。 |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | 另一个应用使用了仅大小写不同的同名名称。 | 选择其他发布名称。 |
| 409 | `<name> is a development run. Stop the watch first` | 该应用当前正在进行开发运行。 | 使用 `Ctrl+C` 停止 watch，然后再部署。 |
| 409 | `<name> is deployed from an archive. Undeploy it first` | 开发运行使用了已部署应用的名称。 | 取消部署该应用，或为开发运行设置其他 `<publishName>`。 |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | 归档大于 BBjShip 允许的大小。 | 移除应用运行时不需要的依赖项。 |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices 在安装或移除应用时发生故障。 | 查看 BBjServices 日志。 |

如果部署以 `did not confirm the requested application. Check the BBjShip version and BBjServices log` 结束，则表示请求已到达 BBjShip，但未收到应用确认。请确认 BBjShip 与 `webforj-maven-plugin` 版本相同，然后查看 BBjServices 日志。

### 运行中的应用 {#the-running-app}

| 消息 | 原因 | 应对方法 |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | 应用使用了 `icons://`，但没有打包资源。 | 在 `webforj.conf` 中添加 `webforj.assetsSource = packaged`，然后重新部署。 |

## 开发运行 {#development-runs}

这些消息来自配置为使用 BBjShip 的项目中的 `mvn` 或 `mvn compile webforj:watch`。开发运行失败时，会以 `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine` 开头，后面接来自[设置](#setup)、[凭据和连接](#credentials-and-connection)或 [BBjShip 的响应](#answers-of-bbjship)的原因。

| 消息 | 原因 | 应对方法 |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | 项目尚未编译，watch 就已启动。 | 运行 `mvn compile webforj:watch`，或在原型项目中运行 `mvn`。 |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` 指定的文件夹无法由 BBjServices 找到。 | 设置 BBj 计算机上类文件夹的绝对路径。 |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | 运行过程查找的位置没有 BBjServices 属性文件。 | 将 `<bbj><properties>` 或 `-Dwebforj.bbj.properties` 设置为该文件的路径。 |
| `BBjServices did not stop within 180 seconds` | 为应用热替换设置，运行过程请求停止 BBjServices，但它仍在运行。 | 手动停止 BBjServices，再重新启动，然后再次运行 watch。 |
| `BBjServices start program is missing: <program>` | BBj 安装中没有用于启动 BBjServices 的程序。 | 手动启动 BBjServices，然后再次运行 watch。 |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices 在重启后未能重新启动。 | 手动启动 BBjServices，然后再次运行 watch。 |
| `BBjShip did not answer within 120 seconds` | BBjServices 已启动，但部署端点没有响应。 | 检查 BBjServices 日志，然后再次运行 watch。 |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices 启动时使用的 Java 参数与写入属性文件的参数不同。 | 在 Enterprise Manager 和该文件中检查 BBjServices 的 Java 设置。 |
| `webforJ watch: development run of <name> was not stopped. <reason>` | 构建停止时无法移除该应用。 | 使用 `bbjship apps undeploy <name>` 移除该应用，或重新启动 watch，由其替换该应用。 |

首次添加热替换设置的开发运行会重启 BBjServices。在 macOS 和 Linux 上，它会使用 `bin/bbjservices` 启动 BBjServices。在 Windows 上，如果已注册 `BBjServices` Windows 服务，则会启动该服务；否则会运行 `bin\BBjServices.exe -noservice`。通过 `bin/bbjservices` 或 `-noservice` 启动的 BBjServices 会在操作系统的服务管理器之外运行，直到计算机重启。

## 命令行 {#command-line}

每条 `bbjship` 命令都在 BBjServices 中运行，因此 BBjServices 必须处于运行状态。否则，命令无法启动。

参数无效时，命令会以退出代码 `2` 结束，并显示指出需要指定内容的消息：

| 消息 | 应对方法 |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | 指定一条命令，参见[命令行](/docs/configuration/bbjship/command-line)。 |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | 使用该命令支持的选项，具体选项请查看 `--help`。 |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | 为选项提供一个值。 |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | 提供一个端口号。 |
| `Specify <option> only once` | 移除重复的选项。 |
| `Specify only one of --password and --password-stdin` | 两者只保留一个。 |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | 同时提供用户名和密码。消息会指出缺少哪一项。 |
| `Specify an application name or --all`, `Specify one application name` | 为 `apps undeploy` 提供一个发布名称，或使用 `--all`。 |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | 为 `apps deploy` 提供一个 `.zip` 文件。 |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | 检查路径。文件由 BBjServices 读取，因此 BBjServices 的操作系统用户需要有权读取该文件。 |
| `Development archives are deployed by the watch, not by apps deploy` | 部署 `bbj-package` 生成的归档，而不是开发运行生成的归档。 |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | 重命名归档。文件名去掉 `.zip` 后就是发布名称。 |

命令执行失败时，会以退出代码 `1` 结束，并显示 `Command failed.`，后面接具体原因：

| 原因 | 原因说明 | 应对方法 |
|---|---|---|
| BBjServices 登录失败 | 用户名或密码错误。 | 检查凭据。 |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` 或 `--ssl-port` 指定的端口不是 BBjServices Web 服务器正在监听的端口。原有端点会保留。 | 使用 Enterprise Manager 中为 Web 服务器配置的端口。 |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` 指定的应用并非由 BBjShip 部署。 | 使用 `bbjship apps list` 检查名称。 |
| `Another deployment is in progress` | 另一个部署或取消部署操作占用了计算机 30 秒。 | 等待操作完成，然后再次运行命令。 |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Enterprise Manager 中的另一个应用使用了 Web 上下文 `webforj-bbjship` 或 `webforj-bbjship-assets`。 | 在 Enterprise Manager 中重命名或移除此上下文。 |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Enterprise Manager 中的类路径 `_webforj_bbjship_cli` 或 `_webforj_bbjship` 已被修改。 | 在 Enterprise Manager 中移除这两个类路径，然后再次运行命令，命令会重新创建它们。 |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | BBjShip 文件夹不完整。 | 重新安装 BBjShip。 |
| `BBjServices does not manage web contexts. Update BBj` | BBj 版本过旧，无法支持 BBjShip。 | 安装与 webforJ 匹配的 BBj 版本。 |

如果对 `Continue? [y/N]` 的回答不是 `y` 或 `Y`，`reset` 会以退出代码 `1` 结束，并显示 `Reset cancelled`。如果命令恢复了 BBjServices 的 Java 设置，则会以 `Restart BBjServices now to apply the restored Java settings` 结束。重启 BBjServices，即可在不使用热替换设置的情况下运行它。

## Windows {#windows}

| 情况 | 应对方法 |
|---|---|
| 使用 BBjShip 程序运行 `bbj.exe` 时没有任何输出。 | 在 `<BBj home>\plugins\webforj-bbjship` 中运行 `bbjship.cmd`，以查看命令输出。 |
| `--password-stdin` 以退出代码 `2` 结束，并显示 `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | 使用 `--password` 传入密码，或设置 `WEBFORJ_BBJ_USERNAME` 和 `WEBFORJ_BBJ_PASSWORD`。 |
| `reset` 以退出代码 `2` 结束，并显示 `Standard input is not available on Windows. Run reset --yes to confirm`. | 运行 `bbjship.cmd reset --yes`。 |
| `apps undeploy` 或 `reset` 显示 `Not removed: <folder>. <reason>`，并以退出代码 `0` 结束。 | BBjServices 仍打开着应用文件。该应用已从 Enterprise Manager 中移除。重启 BBjServices。该文件夹会在下次部署同名应用时随之移除，或可在重启后手动删除。 |
| 在已安装的版本上安装 BBjShip 失败，原因是文件正在使用。 | BBjServices 仍打开着已安装 BBjShip 的文件。重启 BBjServices，然后重新安装。 |
| 开发运行重启 BBjServices 后，它没有作为 Windows 服务运行。 | 因为未注册 `BBjServices` 服务，开发运行使用 `BBjServices.exe -noservice` 启动了它。它会在 Windows 服务管理器之外运行，直到计算机重启。 |
| `bbjship.cmd` 命令无法启动。 | 启动 BBjServices。每条 `bbjship` 命令都需要 BBjServices 正在运行。 |
