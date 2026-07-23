---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
sidebar_class_name: new-content
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: db80016ad151e338c6e353caaa7070d9
---
**webforJ AI 插件**是将您的 AI 编码助手连接到 webforJ 的推荐方式。一次安装为您的助手提供全套工具包：实时访问 webforJ 文档、项目脚手架、主题生成、设计令牌验证，以及教会它如何正确使用这些工具的结构化工作流程。

## 您将获得的内容 {#what-you-get}

安装插件将两个互补的部分连接在一起：

- **[webforJ MCP 服务器](/docs/ai-tooling/mcp)** - 助手可以按需调用的实时工具：查找 webforJ 知识库中的内容、构建 Maven 项目、生成 DWC 主题、读取任何 DWC 组件的样式表面，并在 CSS 中验证 `--dwc-*` 令牌。
- **[代理技能](/docs/ai-tooling/agent-skills)** - 结构化工作流程，告诉助手_何时_去调用这些工具、以什么顺序做事情以及如何验证结果。涵盖了构建可重用组件和端到端样式化 webforJ 应用程序。

它们共同将一个对 webforJ 约定进行猜测的 AI 助手转变为一个遵循这些约定的助手。

:::warning AI 仍然可能出错
即使有插件，AI 助手在复杂场景中也可能生成错误的代码。在发布之前，始终检查和测试生成的代码。
:::

## 安装 {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

在 Claude Code 中确认：

```
/plugin
/mcp
```

`webforj` 插件会出现在 **已安装** 项下。MCP 服务器作为 `plugin:webforj:webforj-mcp` 显示在连接的服务器下。

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

验证：

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

从命令面板中运行 `Chat: Install Plugin From Source`，然后粘贴：

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

验证：

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

然后打开 Codex 会话，运行 `/plugins`，选择 `webforj`，并按 **空格键** 启用它。

Codex 不会像其他客户端那样通过提示匹配自动加载技能。需要显式调用它们：
```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP 工具在没有 `$` 前缀的情况下自动工作。

</TabItem>
</Tabs>

### 其他客户端 {#other-clients}

Cursor、Kiro、Goose、Junie、Antigravity 和任何其他兼容代理技能的客户端也支持该插件 - 它们只是使用手动配置而不是市场命令。请参阅 [每个客户端的安装指南](https://github.com/webforj/webforj-ai#clients) 了解确切步骤。

## 使用 {#using-it}

一旦安装，大多数助手会根据您的提示自动加载正确的部分：

- *“将这个自定义元素库包装为 webforJ 组件。”* - 触发创建组件技能
- *“用 DWC 设计令牌样式化这个视图。”* - 触发样式化应用的技能
- *“构建一个名为 CustomerPortal 的新的 webforJ 侧边栏项目。”* - 调用 MCP 项目脚手架
- *“从品牌颜色 `#6366f1` 生成一个主题。”* - 调用 MCP 主题生成器
- *“查找有关 `@Route` 和路由的 webforJ 文档。”* - 调用 MCP 知识搜索

为了获得最佳效果，始终在提示中提到 **webforJ** - 这就是助手用来调用插件而不是一般 Java 知识的提示。

## 更新和卸载 {#updating-and-uninstalling}

每个受支持的客户端都有自己的更新和卸载命令。请参阅 [webforj-ai README](https://github.com/webforj/webforj-ai#clients) 查看每个客户端的说明。
