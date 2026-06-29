---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: b127e22d65b9a0ee8fc5b58b542aee36
---
webforJ的[服务器驱动模型](/docs/architecture/client-server)和内置的防护措施针对[常见威胁](/docs/security/application-security/common-threats)提供了很多保护，但安全的部署仍然取决于您如何操作应用程序。下面的步骤完善了这一点。

## 加密每个连接 {#encrypt-every-connection}

仅通过HTTPS运行生产流量。在应用程序前的容器、代理或负载均衡器上终止TLS，将任何纯HTTP请求重定向到其安全等价物，以确保凭据和会话标识符不会以未加密形式传输。

## 不要信任浏览器中的任何内容 {#trust-nothing-from-the-browser}

被操控的客户端可以发送任何内容。在您持久化或对接收到的每个值采取行动之前，重新验证您的代码接收到的每个值，即使这些值已经在您的界面中被约束。[客户端/服务器交互](/docs/architecture/client-server)文章解释了为什么服务器是唯一真正能够保持规则的地方。

webforJ的[数据绑定和验证](/docs/data-binding/validation/overview)对此有所帮助：因为绑定在服务器的Java中运行，您附加到模型的约束（包括[Jakarta验证](/docs/data-binding/validation/jakarta-validation)）是在服务器端强制实施的，而不仅仅是在浏览器中。将其视为您的完整性层，不要将其视为防御注入或标记攻击的手段，这些攻击仍需要在[常见威胁](/docs/security/application-security/common-threats)文章中描述的处理方式。

## 禁用和隐藏并不是安全 {#disabled-and-hidden-arent-security}

`setEnabled(false)`和`setVisible(false)`是接口提示，而不是访问控制。webforJ将控件的禁用状态镜像到客户端，但它并不能阻止被操控的客户端重新启用该控件并触发其操作。永远不要依靠禁用或隐藏的控件来防止某些事情发生。

将真实的规则放在服务器端处理程序中：在执行操作之前确认用户被允许，并且前提条件成立，正如您在控件一直启用的情况下所做的那样。禁用状态引导诚实的用户；而服务器端规则则阻止不诚实的用户。

## 锁定视图 {#lock-down-your-views}

通过[路由安全](/docs/security/overview)为视图设置门槛，以便每个视图都要求正确的身份验证和角色。给予用户最窄的访问权限，以便他们能工作，并且倾向于默认安全的姿态，即使是未标记的路由仍需登录。

## 保持机密外部 {#keep-secrets-external}

凭据、密钥和令牌不应存放在代码或您的代码库中。相反，从环境或外部源中获取它们，如[管理机密](/docs/security/application-security/managing-secrets)所示。

## 保持依赖项最新 {#stay-current-on-dependencies}

您引入的库是比自己的代码更大的风险源。追踪公告，定期更新webforJ和其他依赖项，并且当一个传递库的修复版本在拉入它的库之前发布时，在您的`pom.xml`中固定该修复版本。

## 安静地失败 {#fail-quietly}

不要让堆栈跟踪、文件路径或内部标识符暴露给最终用户。在服务器日志中记录详细信息，并在界面中呈现一个简单、通用的信息。通过webforJ的[错误处理](/docs/advanced/error-handling)注册一个自定义处理程序，以便未捕获的异常表面一个受控的页面，而不是原始的诊断信息。

## 负责地披露 {#disclose-responsibly}

发现webforJ本身可能的缺陷？请通过GitHub的[私密漏洞报告](https://github.com/webforj/webforj/security/advisories)私下报告，而不是公开问题或拉取请求，以便在详情被知晓之前修复。
