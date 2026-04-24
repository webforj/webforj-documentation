---
title: webforJ AI Plugin
sidebar_position: 1
slug: /integrations/ai-tooling
sidebar_class_name: new-content
_i18n_hash: e02b32f83a943a803532854ffd334a9b
---
**webforJ AI 插件** 是将您的 AI 编程助手连接到 webforJ 的推荐方式。一次安装为您的助手提供了完整的工具包：实时访问 webforJ 文档、项目脚手架、主题生成、设计令牌验证和结构化工作流程，教会它如何正确使用这些工具。

## 您将获得什么 {#what-you-get}

安装插件将两块互补的部分在一个步骤中连接：

- **[webforJ MCP 服务器](/docs/integrations/ai-tooling/mcp)** - 助手可以按需调用的实时工具：查询 webforJ 知识库、脚手架 Maven 项目、生成 DWC 主题、读取任何 DWC 组件的样式表面，并在它们进入您的 CSS 之前验证 `--dwc-*` 令牌。
- **[代理技能](/docs/integrations/ai-tooling/agent-skills)** - 结构化工作流程，告诉助手 _何时_ 使用这些工具，以何种顺序进行操作，以及如何验证结果。涵盖构建可重用组件和端到端样式 webforJ 应用。

它们共同将一个对 webforJ 约定仅仅进行猜测的 AI 助手转变为一个遵循这些约定的助手。

:::warning AI 仍然可能会犯错误
即使安装了插件，AI 助手在复杂场景中仍可能生成不正确的代码。在发布之前始终检查和测试生成的代码。
:::

## 安装 {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

在 Claude Code 中验证：

```
/plugin
/mcp
```

`webforj` 插件出现在 **已安装** 下。MCP 服务器以 `plugin:webforj:webforj-mcp` 形式出现在连接的服务器下。

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

然后打开 Codex 会话，运行 `/plugins`，选择 `webforj`，并按 **空格** 以启用它。

Codex 不会像其他客户端一样根据提示匹配自动加载技能。请明确调用它们：
Codex 不会像其他客户端一样根据提示匹配自动加载技能。请明确调用它们：

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP 工具在没有 `$` 前缀的情况下自动工作。

</TabItem>
</Tabs>

### 其他客户端 {#other-clients}

Cursor、Kiro、Goose、Junie、Antigravity 和任何其他兼容代理技能的客户端也支持该插件 - 它们只是使用手动配置，而不是市场命令。请参见 [每个客户端安装指南](https://github.com/webforj/webforj-ai#clients) 以获取准确步骤。

## 使用它 {#using-it}

安装后，大多数助手会根据您的提示自动加载正确的部分：

- *“将此自定义元素库包装为 webforJ 组件。”* - 触发创建组件技能
- *“用 DWC 设计令牌样式化此视图。”* - 触发样式应用技能
- *“脚手架一个名为 CustomerPortal 的新 webforJ 侧边菜单项目。”* - 调用 MCP 项目脚手架
- *“从品牌颜色 `#6366f1` 生成主题。”* - 调用 MCP 主题生成器
- *“查找有关 `@Route` 和路由的 webforJ 文档。”* - 调用 MCP 知识搜索

为了获得最佳效果，请始终在提示中提到 **webforJ** - 这是助手用来调用插件而非一般 Java 知识的提示。

## 更新和卸载 {#updating-and-uninstalling}

每个兼容的客户端都有自己的更新和卸载命令。请参见 [webforj-ai README](https://github.com/webforj/webforj-ai#clients) 以获取每个客户端的说明。
