---
title: 故障排除
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: d9235c64dc2d56c5acc41e60b2a2f7df
---
# 故障排除 <DocChip chip='since' label='26.03' /> {#troubleshooting}

本页面列出您在设置 BBjShip、部署应用程序、启动开发运行或使用 `bbjship` 命令行时可能会遇到的消息。对于每条消息，提供原因以及应采取的措施。

失败的 Maven 目标会以目标的简短消息结束，后跟原因。原因是本页面列出的部分：

```text
BBj 部署失败。请检查应用程序包、依赖项和部署设置：BBj 部署失败，HTTP 409。条目类 com.example.Application 已作为清单发布。请先取消部署清单
```

## 凭证与连接 {#credentials-and-connection}

这些消息来自与 BBjShip 交互的每个目标：`bbj-setup`、`bbj-deploy`、`bbj-undeploy` 和 `watch`。

| 消息 | 原因 | 应该做什么 |
|---|---|---|
| `警告：BBj 工厂管理员账户 admin 正在使用其工厂密码...` | 没有配置用户名或密码，因此目标以 `admin` 和 `admin123` 登录。 | 在企业管理器中更改密码并配置新凭证，参见 [设置](/docs/configuration/bbjship/deploying#settings)。 |
| `缺少 BBj 管理用户名称`，`缺少 BBj 管理密码` | 仅其中一个用户名称和密码已配置。 | 配置两者，或者两者都不使用工厂账户。`bbj-deploy` 和 `bbj-undeploy` 还接受一个令牌。 |
| `BBj 部署需要 bbj.deployment.url...` | 没有配置部署 URL。 | 设置 `<bbj><deployment><url>` 或传递 `-Dwebforj.bbj.deployment.url=<URL>`。 |
| `BBj 部署 URL 必须是没有凭证、查询或片段的 HTTP 或 HTTPS URL` | URL 使用其他协议，或者包含用户名、查询或片段。 | 使用 `http://<host>:<port>/webforj-bbjship` 或 `https://<host>:<port>/webforj-bbjship`，并将凭证放入 `<admin>` 中。 |
| `连接到 ... 失败：连接被拒绝` | 在部署 URL 的主机和端口上没有任何响应。 | 启动 BBjServices，检查 URL 的主机和端口是否与 BBjServices 网络服务器相符。 |
| `无法读取 <URL> 处的 BBjShip 版本，HTTP 404...` | URL 不指向 BBjShip，或者 BBjShip 未安装或其端点未在该端口启用。 | 检查路径 `/webforj-bbjship` 和端口。在 BBj 机器上，运行 `mvn webforj:bbj-setup`，或者在服务器上运行 `bbjship remote enable`。 |
| `失败，HTTP 401。检查 BBj 管理凭证或令牌` | BBjServices 拒绝了用户名和密码，或令牌。 | 检查凭证。在企业管理器中续订过期或被撤销的令牌。 |
| `BBjShip <version> 在 <URL> 的版本与 webforJ <version> 不匹配...` | 已安装的 BBjShip 和 `webforj-maven-plugin` 版本不同。 | 在您自己的机器上运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。在服务器上，使用 BBj 插件管理器安装匹配的 BBjShip。 |
| `无法建立与 <URL> 的可信 HTTPS 连接...` | Java 不信任 BBjServices 网络服务器的证书。 | 在 BBj 机器上安装 URL 主机名的证书，或将其证书添加到构建的 Java 信任库中。 |
| `BBj 部署 URL <URL> 将管理凭证以可读形式发送到网络...` | 针对另一个机器的 `http://` URL 的警告。 | 使用带有 SSL 端口的 `https://` URL，参见 [从另一台机器部署](/docs/configuration/bbjship/deploying#deploying-from-another-machine)。 |

## 设置 {#setup}

这些消息来自 `mvn webforj:bbj-setup` 和项目的第一次 [开发运行](/docs/configuration/deploy-reload/bbjservices)，其设置 BBjShip 的方式相同。

| 消息 | 原因 | 应该做什么 |
|---|---|---|
| `BBj 设置失败。检查 BBj 插件工件和设置` | 目标失败。原因随之而来。与工件解析有关的原因意味着 Maven 无法下载 `webforj-bbjship-plugin` 的 `webforj-maven-plugin` 版本。 | 阅读原因。对于工件解析，检查仓库和构建的网络访问。 |
| `BBj 设置仅限本地。请在 BBj 主机上运行设置` | 部署 URL 指向另一台机器。 | 在 BBj 机器上运行设置，或在 BBj 插件管理器中安装 BBjShip。 |
| `BBj 设置无法解析部署 URL 主机 <host>...` | 部署 URL 的主机名未知。 | 使用 `localhost` 或此机器的名称。 |
| `BBj 设置需要管理密码。配置 bbj.admin.password，而不是令牌` | 开发运行已配置令牌。开发运行仅以用户名和密码登录。 | 配置 `<admin><username>` 和 `<admin><password>`。 |
| `无法检测到 BBj 安装。配置 bbj.home 或 BBJ_HOME` | 目标在此机器上找不到 BBj 安装。 | 设置 `<bbj><home>`、`-Dwebforj.bbj.home` 或 `BBJ_HOME` 变量为 BBj 安装文件夹。 |
| `无效的 BBj home。配置本地 BBj 安装`，`BBj 可执行文件丢失或不可执行。检查 bbj.home` | 配置的文件夹不是完整的 BBj 安装。 | 将设置指向包含 BBj 的 `bin` 和 `cfg` 的文件夹。 |
| `已安装的 BBjShip 版本 <version> 与 <version> 不匹配...` | 安装了另一个 BBjShip 版本，例如经过 webforJ 升级后。 | 运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。已部署的应用程序保持部署状态。 |
| `已安装的 BBjShip 版本丢失...` | 已安装的 BBjShip 文件夹不完整。 | 运行 `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`。 |
| `插件目录是符号链接...`，`BBj 插件目标不能包含符号链接` | BBj 的 `plugins` 文件夹，或其中的 BBjShip 文件夹是符号链接或包含符号链接。 | 安装到常规文件夹中。 |
| `BBj 设置命令失败。检查凭证和本地 BBjServices.` 后跟命令的输出 | 启用部署端点的命令失败。输出命名了原因，例如拒绝登录或 Web 服务器不监听的端口。 | 检查凭证，确保 BBjServices 运行，并确保部署 URL 的端口是 BBjServices Web 服务器的端口。 |
| `BBj 设置命令超时。检查本地 BBjServices` | 命令在 120 秒内未完成。 | 检查 BBjServices 是否运行并响应，然后重新运行目标。 |
| `先前的 BBjShip 文件保存在 <folder>` | 替换失败，先前版本的文件保留在该文件夹中。 | 修复失败的原因，然后再次运行目标。 |

## 部署和取消部署 {#deploy-and-undeploy}

### 上传前 {#before-the-upload}

这些消息来自 `bbj-package`、`bbj-deploy` 和 `bbj-undeploy`，在任何内容到达 BBjShip 之前。

| 消息 | 原因 | 应该做什么 |
|---|---|---|
| `BBj 部署需要 jar 打包，此项目打包为 <packaging>` | 项目不是打包为 jar。 | 设置 `<packaging>jar</packaging>`。 |
| `BBj 部署需要打包应用程序 jar...` | 目标在同一构建中未运行 `package` 阶段。 | 运行 `mvn install`，或 `mvn package webforj:bbj-deploy`。 |
| `BBj 部署不得携带 webforj-engine，BBj 是引擎...` | `webforj-engine` 在应用程序的运行时类路径中。 | 从 `webforj` 依赖中排除它，参见 [不使用安装插件](/docs/configuration/bbjship/existing-projects#without-the-install-plugin)。 |
| `BBj 部署要求在运行时类路径中包含 webforj-foundation` | 应用程序在运行时没有 webforJ 依赖。 | 在 `compile` 范围内添加 `webforj` 依赖。 |
| `BBj 部署依赖未解析...` | 应用程序的依赖没有文件。 | 检查 Maven 是否解析项目的所有依赖项。 |
| `发布名称 <name> 是无效的...` | 发布名称违反了 [发布名称](/docs/configuration/bbjship/deploying#publish-names) 中的规则。 | 选择另一个 `<publishName>`。 |
| `发布名称 <name> 被 BBjServices 保留用于其自身的 Web 路径...` | BBjServices 使用该名称作为其自身的地址。 | 选择其他 `<publishName>`。 |
| `无法写入 BBj 部署档案...` | `bbj-package` 无法写入 `target/bbj/<publish name>.zip`。原因随之而来。 | 阅读原因。 |

### BBjShip 的回答 {#answers-of-bbjship}

当 BBjShip 拒绝部署或取消部署时，消息以 `BBj 部署失败，HTTP <status>` 或 `BBj 取消部署失败，HTTP <status>` 开头，后跟原因。

| 状态 | 原因 | 原因 | 应该做什么 |
|---|---|---|---|
| 400 | `发布名称 <name> 是无效的...` 或 `...被 BBjServices 保留...` | 拒绝发布名称。 | 选择其他发布名称。 |
| 400 | `BBj 部署 webforj.conf 无效。<detail>` | 应用程序的 `webforj.conf` 无法读取，或某个 [应用设置](/docs/configuration/bbjship/deploying#app-settings) 类型错误。 | 按照详细说明修复文件。 |
| 400 | `上传的主体不是 BBj 部署档案...`，`无效的 BBj 部署档案条目：<entry>` 或其他有关档案的原因 | 档案不是由构建插件写入的，或在后续更改过。 | 使用与 BBjShip 版本相同的 webforJ 版本部署构建写入的档案。 |
| 401 | `检查 BBj 管理凭证或令牌` | 登录被拒绝。 | 检查凭证或续订令牌。 |
| 403 | `在此 BBj 机器上未启用开发运行。运行 bbjship development enable` | 开发运行到达了关闭开发运行的机器。 | 在 BBj 机器上运行 `bbjship development enable`。 |
| 404 | `没有名为 <name> 的 BBj 应用程序由 BBjShip 部署。检查发布名称` | 取消部署命名了 BBjShip 没有部署的应用程序。 | 使用 `bbjship apps list` 检查发布名称。 |
| 409 | `条目类 <class> 已作为 <name> 发布。请先取消部署 <name>` | 应用程序的条目类已在另一个发布名称下运行。 | 取消部署另一个应用程序，或以其名称部署。 |
| 409 | `已发布的 BBj 应用程序 <name> 不属于 BBjShip...` | 企业管理器中已注册的应用程序已使用该名称。 | 选择其他发布名称，或在企业管理器中删除该应用程序。 |
| 409 | `已发布的 BBj 应用程序 <other> 与 <name> 仅在字母大小写上不同...` | 另一个应用程序在其他字母大小写中使用相同的名称。 | 选择其他发布名称。 |
| 409 | `<name> 是开发运行。请先停止监视` | 应用程序的开发运行处于活动状态。 | 使用 `Ctrl+C` 停止监视，然后进行部署。 |
| 409 | `<name> 是从档案部署的。请先取消部署它` | 开发运行使用已部署应用程序的名称。 | 取消部署应用程序，或为开发运行设置其他 `<publishName>`。 |
| 413 | `BBj 部署档案超过 1 GiB 允许的大小` | 档案大于 BBjShip 可接受的大小。 | 删除应用程序在运行时不需要的依赖项。 |
| 500 | `BBj 部署失败。检查 BBjServices 日志` | BBjServices 在安装或删除应用程序时失败。 | 阅读 BBjServices 日志。 |

以 `未确认请求的应用程序。检查 BBjShip 版本和 BBjServices 日志` 结束的部署已到达 BBjShip，但未获得应用程序的确认。检查 BBjShip 和 `webforj-maven-plugin` 是否具有相同版本，然后阅读 BBjServices 日志。

### 正在运行的应用程序 {#the-running-app}

| 消息 | 原因 | 应该做什么 |
|---|---|---|
| `图标协议要求 webforj.assetsSource = packaged，当与 BBj Services 一起运行时` | 应用程序使用 `icons://`，但没有打包的资产。 | 将 `webforj.assetsSource = packaged` 添加到 `webforj.conf` 并重新部署。 |

## 开发运行 {#development-runs}

这些消息来自在为 BBjShip 配置的项目中使用的 `mvn` 或 `mvn compile webforj:watch`。失败的开发运行以 `BBj 开发运行失败。检查部署设置并确保开发运行在 BBj 机器上已启用` 开头，后跟来自 [设置](#setup)、[凭证与连接](#credentials-and-connection) 或 [BBjShip 的回答](#answers-of-bbjship) 的原因。

| 消息 | 原因 | 应该做什么 |
|---|---|---|
| `类文件夹 <folder> 不存在。在 webforj:watch 之前运行 compile` | 监视在项目编译之前启动。 | 运行 `mvn compile webforj:watch`，或在原型项目中运行 `mvn`。 |
| `BBj 开发类文件夹必须是 BBj 机器上的绝对目录` | `<development><classFolder>` 指定 BBjServices 无法找到的文件夹。 | 设置 BBj 机器上类文件夹的绝对路径。 |
| `未找到 BBjServices 属性文件 <file>。配置 bbj.properties` | BBjServices 属性文件不在运行查找的位置。 | 设置 `<bbj><properties>` 或 `-Dwebforj.bbj.properties` 为该文件。 |
| `BBjServices 在 180 秒内未停止` | 运行请求 BBjServices 停止以应用热插拔设置，但它保持运行。 | 自行停止 BBjServices，然后重新启动，再次运行监视。 |
| `BBjServices 启动程序丢失：<program>` | BBj 安装中没有程序来启动 BBjServices。 | 自行启动 BBjServices，然后再次运行监视。 |
| `BBjServices 未启动。程序以退出代码 <code> 结束`，`BBjServices 在 120 秒内未启动` | BBjServices 在重启后未恢复。 | 自行启动 BBjServices，然后再次运行监视。 |
| `BBjShip 在 120 秒内没有回应` | BBjServices 启动，但部署端点没有响应。 | 检查 BBjServices 日志，然后再次运行监视。 |
| `BBjServices 在没有热插拔设置的情况下重启。检查 <file>` | BBjServices 以与属性文件中写入的 Java 参数不同的参数启动。 | 检查企业管理器和该文件中 BBjServices 的 Java 设置。 |
| `webforJ watch：<name> 的开发运行未停止。<reason>` | 当构建停止时，无法移除应用程序。 | 使用 `bbjship apps undeploy <name>` 移除它，或者再次启动监视，这将替换它。 |

第一次添加热插拔设置的开发运行将重启 BBjServices。它在 macOS 和 Linux 上使用 `bin/bbjservices` 启动 BBjServices。在 Windows 上，当注册了一个时，它将启动 `BBjServices` Windows 服务，否则将运行 `bin\BBjServices.exe -noservice`。通过 `bin/bbjservices` 或 `-noservice` 启动的 BBjServices 在操作系统的服务管理器之外运行，直到机器重启。

## 命令行 {#command-line}

每个 `bbjship` 命令都在 BBjServices 内部运行，因此必须运行 BBjServices。没有它，命令无法启动。

无效的参数以退出代码 `2` 结束，消息说明要指定什么：

| 消息 | 应该做什么 |
|---|---|
| `指定一个命令。使用 --help 获取可用命令`，`未知命令 <command>...` | 命名一个命令，参见 [命令行](/docs/configuration/bbjship/command-line)。 |
| `未知选项 <option>...`，`选项 <option> 对于 <command> 不可用...` | 使用该命令的选项，如 `--help` 列出它们。 |
| `在 --username 后指定用户名`，`在 --password 后指定密码`，`在 --port 后指定端口` | 为该选项赋值。 |
| `指定 --port 从 1 到 65535`，`指定 --ssl-port 从 1 到 65535` | 提供一个端口号。 |
| `仅一次指定 <option>` | 移除重复的选项。 |
| `仅在 --password 和 --password-stdin 之间指定一个` | 保留两个中的一个。 |
| `BBj 管理密码丢失。请指定 --username 和 --password <value> 或 --password-stdin，或设置 WEBFORJ_BBJ_USERNAME 和 WEBFORJ_BBJ_PASSWORD` | 一起提供用户名和密码。消息说明缺失的内容。 |
| `指定一个应用程序名称或 --all`，`指定一个应用程序名称` | 为 `apps undeploy` 提供一个发布名称，或 `--all`。 |
| `指定一个以 .zip 结尾的部署档案路径`，`指定一个部署档案` | 为 `apps deploy` 提供一个 `.zip` 文件。 |
| `部署档案 <path> 不存在`，`部署档案 <path> 无法读取` | 检查路径。BBjServices 读取文件，因此 BBjServices 的操作系统用户需要对其具有读取权限。 |
| `开发档案由监视部署，而不是由 apps deploy` | 部署 `bbj-package` 的档案，而不是开发运行的档案。 |
| `发布名称 <name> 是无效的...`，`发布名称 <name> 被保留...` | 重命名档案。其文件名去掉 `.zip` 是发布名称。 |

失败的命令以退出代码 `1` 结束，并以 `命令失败。` 后跟原因：

| 原因 | 原因 | 应该做什么 |
|---|---|---|
| BBjServices 登录失败 | 用户名或密码错误。 | 检查凭证。 |
| `BBjServices Web 服务器在端口 <port> 上没有响应。请使用 BBjServices Web 服务器的 HTTP 端口` | `--port` 或 `--ssl-port` 不是 BBjServices Web 服务器监听的端口。上一个端点保持不变。 | 使用企业管理器中配置的 Web 服务器端口。 |
| `应用程序 <name> 不是由 BBjShip 部署的` | `apps undeploy` 命名了 BBjShip 没有部署的应用程序。 | 使用 `bbjship apps list` 检查名称。 |
| `另一个部署正在进行` | 另一个部署或取消部署在 30 秒内占用机器。 | 等待其完成，然后再次运行命令。 |
| `BBjShip 上下文属于另一个应用程序`，`资产上下文属于另一个应用程序` | 企业管理器中的另一个应用程序使用 Web 上下文 `webforj-bbjship` 或 `webforj-bbjship-assets`。 | 在企业管理器中重命名或删除该上下文。 |
| `BBjShip 命令类路径属于另一个应用程序`，`BBjShip 基本类路径包含一个未拥有的库` | 企业管理器中的类路径 `_webforj_bbjship_cli` 或 `_webforj_bbjship` 被更改。 | 在企业管理器中删除这两个类路径并再次运行命令，这将重新创建它们。 |
| `BBjShip servlet 丢失。检查插件安装`，`BBjShip 库丢失。检查插件安装` | BBjShip 文件夹不完整。 | 重新安装 BBjShip。 |
| `BBjServices 不管理 Web 上下文。请更新 BBj` | BBj 版本太旧，无法支持 BBjShip。 | 安装与 webforJ 匹配的 BBj 版本。 |

`reset` 以退出代码 `1` 结束，当对 `继续？[y/N]` 的回答不是 `y` 或 `Y` 时为 `重置已取消`。当恢复 BBjServices 的 Java 设置时，以 `现在重启 BBjServices 以应用恢复的 Java 设置` 结束。重启 BBjServices 以在没有热插拔设置的情况下运行它。

## Windows {#windows}

| 情况 | 应该做什么 |
|---|---|
| 运行 `bbj.exe` 但没有打印任何内容。 | 在 `<BBj home>\plugins\webforj-bbjship` 中运行 `bbjship.cmd`，以显示命令的输出。 |
| `--password-stdin` 以退出代码 `2` 和 `Windows 上不可用标准输入。使用 --password <value> 或 WEBFORJ_BBJ_PASSWORD。` 结束。 | 使用 `--password` 传递密码，或设置 `WEBFORJ_BBJ_USERNAME` 和 `WEBFORJ_BBJ_PASSWORD`。 |
| `reset` 以退出代码 `2` 和 `Windows 上不可用标准输入。运行 reset --yes 以确认` 结束。 | 运行 `bbjship.cmd reset --yes`。 |
| `apps undeploy` 或 `reset` 打印 `未删除：<folder>。<reason>` 并以退出代码 `0` 结束。 | BBjServices 仍然保持该应用程序的文件打开。应用程序已从企业管理器中移除。重启 BBjServices。该文件夹在下一个同名部署后删除，或者在重启后手动删除。 |
| 在已安装版本上安装 BBjShip 失败，因为其文件正在使用中。 | BBjServices 仍保持安装的 BBjShip 的文件打开。重启 BBjServices，然后再次安装。 |
| 在开发运行重启其后，BBjServices 并没有作为 Windows 服务运行。 | 开发运行以 `BBjServices.exe -noservice` 启动它，因为没有注册的 `BBjServices` 服务。它在 Windows 服务管理器之外运行，直到机器重启。 |
| `bbjship.cmd` 命令无法启动。 | 启动 BBjServices。每个 `bbjship` 命令都需要它运行。 |
