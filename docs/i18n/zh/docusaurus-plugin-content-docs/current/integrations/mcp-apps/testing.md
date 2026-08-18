---
title: Test an MCP App
sidebar_position: 10
description: >-
  Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app,
  Claude Desktop, and MCPJam.
_i18n_hash: fb9683202651a3aca86843cf27c0626e
---
webforJ MCP 应用可以在任何支持 MCP 应用的主机上运行。这里的说明涵盖了通过可访问的 HTTPS 端点运行的 Codex 应用和 Claude Desktop，以及通过 localhost 运行的 MCPJam。来自 [Spring Boot 设置](./spring) 的最小无输入 `inventory` 工具足以确认主机可以发现该工具并呈现 Inventory 视图。

## 远程客户端 {#remote-clients}

Codex 应用和 Claude Desktop 从开发机器之外连接。它们无法访问 `http://localhost:8080/mcp`，因此正在运行的应用需要一个公共 HTTPS MCP URL。

### 暴露一个本地应用 {#expose-a-local-app}

使用 [Cloudflare Tunnel](https://developers.cloudflare.com/tunnel/setup/) 来保留并打印一个公共 HTTPS 来源，该来源转发到默认本地端口 `8080` 上的应用。您可以在应用之前启动隧道：

```bash
cloudflared tunnel --url http://localhost:8080
```

该命令打印一个 HTTPS 来源，例如 `https://example.trycloudflare.com`。将该打印的来源设置在 `src/main/resources/application.properties` 中：

```Ini
webforj.origin=https://example.trycloudflare.com
```

通过其正常工作流程启动应用。来源没有 `/mcp`；客户端 URL 添加 `/mcp`：

```text
https://example.trycloudflare.com/mcp
```

:::warning[开发隧道]

开发隧道使应用可以公开访问。使用测试数据，期望每次快速隧道启动时都有一个新主机名，并在主机名必须保持相同的情况下使用稳定的托管隧道。
:::

### Codex 应用 {#codex-app}

<!-- Video: Connect and test the inventory MCP App in the Codex app. -->

OpenAI 的 [Plugins guide](https://developers.openai.com/codex/plugins) 涵盖了当前的插件控制。

1. 在 **Settings** 中，打开 **Plugins** 并选择 **Add MCP server**。
2. 输入公共 MCP URL：

```text
https://example.trycloudflare.com/mcp
```

3. 添加服务器，然后开始新的 Codex 对话。
4. 提示 Codex 应用：

```text
打开库存应用。
```

5. 确认呈现的 Inventory 视图显示。

<!-- vale Google.Headings = NO -->
### Claude Desktop {#claude-desktop}

<!-- Video: Connect and test the inventory MCP App in Claude Desktop. -->
<!-- vale Google.Headings = YES -->

Claude Desktop 的远程自定义连接器通过 Anthropic 基础设施进行中介，因此它也需要公共 HTTPS MCP URL。Anthropic 的 [connectors guide](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) 涵盖了当前的连接器控制。

1. 打开 **Settings**，选择 **Connectors**，然后点击添加按钮。
2. 选择 **Add custom connector**，输入名称，并使用公共 MCP URL：

```text
https://example.trycloudflare.com/mcp
```

3. 添加连接器。
4. 在对话中，提示 Claude Desktop：

```text
打开库存应用。
```

6. 确认呈现的 Inventory 视图显示。

如果服务器需要 OAuth 2.0，则在调用工具之前完成登录流程。

:::tip[在提示中命名 MCP 服务器]

如果 Codex 或 Claude 没有选择预期的操作，请在提示中包含 MCP 服务器名称。这可能发生在多个工具可能适用或提示过于模糊的情况下。例如：`使用库存 MCP 服务器，打开库存应用。`
:::

## MCPJam {#mcpjam}

[MCPJam](https://github.com/MCPJam/inspector) 可以直接连接到运行在同一台机器上的 MCP 服务器。使用本地检查器获取一个普通的 HTTP 端点；托管的 MCPJam 应用只能接受 HTTPS 端点。

1. 启动本地检查器并打开它打印的 localhost URL：

```bash
npx @mcpjam/inspector@latest
```

2. 在启动 webforJ 应用之前，配置其本地来源并允许 MCPJam 浏览器来源。如果检查器打印了不同的代表性 MCPJam 来源，请替换下面的内容：

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` 设置渲染的 MCP 应用加载其 webforJ 资源的位置。`webforj.mcp.allowed-origins` 允许 MCPJam 页面嵌入并与应用通信。

3. 通过其正常工作流程启动 webforJ 应用。

4. 在 MCPJam 中，打开 **Connect** 并选择 **Add server**。输入名称，选择 **HTTP** 作为传输，并使用本地 MCP 端点：

```text
http://localhost:8080/mcp
```

5. 选择 **No Authentication**，然后连接服务器。成功连接后，服务器的工具将可以在 MCPJam 中使用。
6. 打开 **Playground**，然后在左侧导航中打开 **Tools**。
7. 选择 `inventory` 并点击 **Run**。该工具不需要输入，其 Inventory 视图在对话中呈现。

:::warning[MCPJam 内容安全政策模式]

在运行该工具之前，在 Playground 工具栏中将 **Content Security Policy (CSP) Mode** 设置为 **Permissive**。严格模式会阻止在当前 webforJ 启动过程中使用的动态 JavaScript 评估。仅在您信任的 MCP 服务器和应用代码与之配合使用时，使用允许模式。
:::

## 验证应用 {#verify-the-app}

为每个客户端使用以下基线：

- 客户端连接到 MCP 端点。
- `inventory` 工具可见。
- 调用 `inventory` 会呈现 **Inventory** 标题。
- 渲染的 UI 是交互式的。

在基线正常工作后，如果 MCP 应用需要这些功能，请添加 [打开输入](./opening-apps)、[操作和更新](./actions-updates) 和 [主机交互](./host-interaction)。

## 故障排除 {#troubleshooting}

| 问题 | 检查 |
| --- | --- |
| 客户端无法连接 | 确认应用正在运行，隧道正在为远程客户端运行，并且完整的客户端 URL 以 `/mcp` 结尾。 |
| 工具可见但资源或打开失败 | 确认 `webforj.origin` 与当前应用来源匹配，并且应用正在运行。 |
| MCPJam 是空白或加载时出现内容安全政策 `eval` 错误 | 关闭 **Strict**。 |
| 元数据已过时 | 重新连接客户端或开始新对话。 |
