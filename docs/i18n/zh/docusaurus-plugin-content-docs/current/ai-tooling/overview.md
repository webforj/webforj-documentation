---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: 44bdaad98af3599ab5fcf57c6a4756c1
---
**webforJ AI 插件**是将您的 AI 编码助手连接到 webforJ 的推荐方式。一次安装为您的助手提供了完整的工具包：实时访问 webforJ 文档、项目搭建、主题生成、设计令牌验证，以及教它如何正确使用所有这些工具的结构化工作流程。

## 您获得的内容 {#what-you-get}

安装插件可以在一个步骤中连接两个互补的部分：

- **[webforJ MCP 服务器](/docs/ai-tooling/mcp)** - 助手可以按需调用的实时工具：在 webforJ 知识库中查找内容、搭建 Maven 项目、生成 DWC 主题、读取任何 DWC 组件的样式表面，并在它们进入您的 CSS 之前验证 `--dwc-*` 令牌。
- **[代理技能](/docs/ai-tooling/agent-skills)** - 结构化工作流程，告诉助手_何时_寻找这些工具，按什么顺序执行，以及如何验证结果。涵盖从头到尾构建可重用组件和样式化 webforJ 应用程序。

它们共同将一个在 webforJ 约定中猜测的 AI 助手转变为一个遵循这些约定的助手。

除此之外，webforJ 还提供了一种不同类型的助手：

- **[craftforJ 助手](/docs/ai-tooling/craftforj-assistant)** - 一个在您的*运行中*应用程序内部工作的编码代理。它可以自由编写 Java，编译每次编辑，应用更改，并在您的应用程序重新启动后继续工作，同时读取实时组件树、修改属性、导航路由和调整主题。无须安装，因为它随 webforJ 一同提供。

:::warning AI 仍然可能出错
即使有插件，AI 助手在复杂场景中也可能生成不正确的代码。在发布之前，始终审查和测试生成的代码。
:::

## 安装 {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

在 Claude Code 内进行验证：

```
/plugin
/mcp
```

`webforj` 插件显示在 **已安装** 下。MCP 服务器作为 `plugin:webforj:webforj-mcp` 出现在连接的服务器中。

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

然后打开 Codex 会话，运行 `/plugins`，选择 `webforj`，并按 **空格** 启用它。

Codex 不像其他客户端那样通过提示匹配自动加载技能。明确调用它们：
Codex 不像其他客户端那样通过提示匹配自动加载技能。明确调用它们：

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP 工具可以在没有 `$` 前缀的情况下自动工作。

</TabItem>
</Tabs>

### 其他客户端 {#other-clients}

Cursor、Kiro、Goose、Junie、Antigravity，以及任何其他兼容代理技能的客户端也支持该插件 - 他们只是使用手动配置，而不是市场命令。请参阅 [每个客户端安装指南](https://github.com/webforj/webforj-ai#clients) 以获取确切步骤。

## 使用它 {#using-it}

安装后，大多数助手会根据您的提示自动加载正确的部分：

- *"将这个自定义元素库包装为 webforJ 组件。"* - 触发创建组件技能
- *"使用 DWC 设计令牌对这个视图进行样式化。"* - 触发样式应用技能
- *"搭建一个新的 webforJ 侧边菜单项目，名为 CustomerPortal。"* - 调用 MCP 项目搭建器
- *"从品牌颜色 `#6366f1` 生成主题。"* - 调用 MCP 主题生成器
- *"找到关于 `@Route` 和路由的 webforJ 文档。"* - 调用 MCP 知识搜索

为了获得最佳效果，请始终在您的提示中提及 **webforJ** - 这是助手用来选择插件而不是一般 Java 知识的信号。

## 更新和卸载 {#updating-and-uninstalling}

每个受支持的客户端都有自己的更新和卸载命令。请参阅 [webforj-ai README](https://github.com/webforj/webforj-ai#clients) 获取每个客户端的指令。
