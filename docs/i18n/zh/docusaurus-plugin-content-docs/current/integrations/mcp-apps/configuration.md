---
title: 配置 MCP 应用
sidebar_position: 30
description: >-
  Configure the public app origin, allowed embedding clients, and external
  browser domains.
_i18n_hash: 6d6d861d57b9a398007bd9a792e9ec1f
---
将MCP应用程序设置添加到`application.properties`，或者在使用标准webforJ设置时添加到`webforj.conf`。设置客户端可以访问应用程序的地址，然后仅添加部署所需的客户端和浏览器来源。

## 设置应用程序来源 {#app-origin}

`webforj.origin`是用于应用程序资源、内容安全策略和webforJ组件URL的公共来源。在本地测试中，它是应用程序的地址：

```Ini
webforj.origin=http://localhost:8080
```

当隧道或反向代理公开应用程序时，使用MCP客户端可以访问的公共来源：

```Ini
webforj.origin=https://example.trycloudflare.com
```

在此属性中不要包含`/mcp`。该路径属于MCP端点，而不是应用程序来源。

## 允许嵌入客户端 {#allowed-origins}

`webforj.mcp.allowed-origins`控制哪些浏览器来源可以发起跨源请求并嵌入视图。对于在代表性来源`http://127.0.0.1:6274`上运行的本地[MCPJam](./testing#mcpjam)浏览器，使用：

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

使用客户端浏览器地址栏中显示的来源，因为本地工具可以选择不同的端口。隧道地址不是允许的客户端来源；它属于`webforj.origin`。

webforJ已允许已知的Codex应用程序和Claude Desktop沙盒来源模式。仅为另一个客户端来源添加此属性。通配符如`https://*.example.com`匹配主机标签，而不是任意URL文本。

## 允许外部资源和连接 {#browser-domains}

嵌入的框架以限制性的内容安全策略开始。当UI必须从另一个来源加载脚本、样式、图像、字体或其他浏览器资源时，添加`resource-domains`：

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

当框架中的浏览器代码必须连接到外部API、WebSocket或类似端点时，添加`connect-domains`：

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

这些属性扩展了嵌入框架可以加载或联系的内容。它们不允许另一个客户端嵌入应用程序；为此，请使用`allowed-origins`。

## 配置标准部署 {#standard-deployment}

Spring Boot从`application.properties`读取这些值。标准servlet部署使用`webforj.conf`，具有等效值：

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

仅添加应用程序所需的域。 [客户端测试](./testing)显示在哪里找到本地客户端来源以及何时需要公共应用程序来源。
