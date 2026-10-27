---
sidebar_position: 3
title: 生产环境加固
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: 62e3e574855705f8b97295f4ebe5169b
---
webforJ的 [服务器驱动模型](/docs/architecture/client-server) 和内置的对 [常见威胁](/docs/security/application-security/common-threats) 的保护涵盖了很多，但安全的部署仍然依赖于你如何操作应用程序。以下步骤补充了整体情况。

## 加密每个连接 {#encrypt-every-connection}

生产流量仅在HTTPS上运行。在应用程序前面的容器、代理或负载均衡器终止TLS，并将任何明文HTTP请求重定向到其安全对应项，以便凭据和会话标识符永远不会以未加密的形式传输。

## 不信任浏览器发送的内容 {#trust-nothing-from-the-browser}

被操控的客户端可以发送任何内容。在持久化或对其进行操作之前，重新验证你的代码接收到的每个值，即使是你的接口已经约束的值。[客户端/服务器交互](/docs/architecture/client-server) 文章解释了为什么服务器是规则能够真正生效的唯一地方。

webforJ的 [数据绑定和验证](/docs/data-binding/validation/overview) 在这里有所帮助：因为绑定在服务器上的Java中运行，你附加到模型的约束，包括 [雅加达验证](/docs/data-binding/validation/jakarta-validation)，都是在服务器端强制执行，而不仅仅是在浏览器中。把这当作你的完整性层，而不是作为对抗注入或标记攻击的防御，这些攻击仍然需要在 [常见威胁](/docs/security/application-security/common-threats) 文章中描述的处理。

## 禁用和隐藏并不是安全 {#disabled-and-hidden-arent-security}

`setEnabled(false)` 和 `setVisible(false)` 是界面提示，而不是访问控制。webforJ 将控件的禁用状态反映给客户端，但它并不能阻止被操控的客户端重新启用该控件并触发其动作。永远不要依赖于禁用或隐藏的控件来防止某些事情的发生。

将真正的规则放在服务器端处理程序中：在执行操作之前确认用户被允许并且前提条件成立，正如你在控件一直处于启用状态时所做的那样。禁用状态指导诚实用户；而服务器端规则则阻止不诚实的用户。

## 限制你的视图 {#lock-down-your-views}

通过 [路由安全](/docs/security/overview) 给视图设置门槛，以便每个视图都需要正确的身份验证和角色。给予人们尽可能窄的访问权限，以便他们工作，并优先采取默认安全策略，其中未标记的路由仍然需要登录。

## 将秘密保留在外部 {#keep-secrets-external}

凭据、密钥和令牌不应存放在代码或你的代码库中。相反，请从环境或外部来源拉取它们，如 [管理秘密](/docs/security/application-security/managing-secrets) 中所示。

## 关闭开发工具 {#leave-development-tooling-off}

[craftforJ](/docs/craftforj) 是开发环境，用于检查正在运行的应用程序并将更改写回其Java源代码。它需要 `webforj.debug` 和 `webforj.devtools.craftforj.enabled`，默认情况下只针对运行应用程序的机器进行响应。使用 [startforJ](https://docs.webforj.com/startforj) 创建的项目或来自webforJ [原型](/docs/building-ui/archetypes/overview) 的项目都启用了这两个设置，以便进行开发，因此请确认它们，而不是假设。

检查在实际部署的配置中这两个属性是否未设置或为`false`，包括仅在生产中适用的任何环境变量或配置文件。然后加载已部署的应用程序，并确认页面上不出现 craftforJ 触发器。有关完整信息，请参见 [craftforJ 安全](/docs/craftforj/security)。

## 保持依赖项最新 {#stay-current-on-dependencies}

你引入的库是比你自己代码更大的风险来源。跟踪通告，定期更新 webforJ 及其他依赖项，当一个传递库的修补版本在拉入它的库之前发布时，在你的 `pom.xml` 中将固定版本固定。

## 安静失败 {#fail-quietly}

不要让堆栈跟踪、文件路径或内部标识符到达最终用户。在你的服务器日志中记录详细信息，并在界面中呈现一个简单的通用消息。通过 webforJ 的 [错误处理](/docs/advanced/error-handling) 注册一个自定义处理程序，以便未捕获的异常显示一个受控页面，而不是原始诊断信息。

## 负责任地披露 {#disclose-responsibly}

发现 webforJ 本身的可能缺陷？通过 GitHub 的 [私密漏洞报告](https://github.com/webforj/webforj/security/advisories) 私下报告，而不是公开问题或拉取请求，以便在细节被知晓之前能提出修复。
