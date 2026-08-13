---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: e51aa2e6a5a0f6c37a18c404c1104684
---
webforJ模型上下文协议（MCP）服务器将AI编码助手集成到webforJ的文档、API、设计令牌和脚手架工具中。助手可以向服务器询问，而不是猜测框架约定，从而获得基于真实webforJ的答案。

:::tip 使用插件
除非你只想要MCP服务器，否则安装**[webforJ AI插件](/docs/ai-tooling)**，因为它将此服务器与匹配的[代理技能](/docs/ai-tooling/agent-skills)捆绑在一个安装中。
:::

## 什么是MCP？{#whats-an-mcp}

模型上下文协议是一个开放标准，允许AI助手按需调用外部工具。webforJ MCP服务器实现了该协议，因此你的助手可以：

- 在webforJ文档中查找信息，而不是虚构方法名称
- 从官方Maven原型构建新的webforJ项目
- 从品牌颜色生成可访问的DWC主题
- 读取DWC组件的真实样式表面，并在它进入你的CSS之前验证任何`--dwc-*`令牌

:::warning AI仍然可能出错
MCP服务器显著提高了准确性，但在复杂场景中，AI助手仍可能生成错误的代码。在发布之前，务必审核和测试生成的代码。
:::

## 安装 {#installation}

要获得完整体验，请安装**[webforJ AI插件](/docs/ai-tooling)**。它会将此服务器与助手需要使用的代理技能一起配置。

如果你只想要MCP服务器（没有技能），请将你的客户端指向`https://mcp.webforj.com/mcp`：

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

在Copilot CLI上的推荐路径是**[webforJ AI插件](/docs/ai-tooling)**- 它为你一步注册MCP服务器。有关MCP单独设置的原始信息，请参见[webforJ AI存储库](https://github.com/webforj/webforj-ai#clients)中的每个客户端说明。

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

添加到你的VS Code设置：

```json
"mcp": {
  "servers": {
    "webforj-mcp": {
      "url": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

添加到`~/.gemini/settings.json`：

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "httpUrl": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

添加到`~/.codex/config.toml`：

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### 其他客户端 {#other-clients}

Cursor、Kiro、Goose、Junie、Antigravity以及其他任何MCP-over-HTTP客户端也有效- 它们只是使用自己的配置格式。有关每个客户端的确切代码片段，请参见[每个客户端安装指南](https://github.com/webforj/webforj-ai#clients)。

## 服务器可以做什么 {#capabilities}

当MCP服务器连接时，你的AI助手获得以下功能。它们都可以通过自然语言请求触发- 助手会自动选择正确的功能。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>定位正确的webforJ版本</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      在回答与版本相关的问题（任何样式或API特定问题）之前，助手会确定你正在使用的webforJ版本。当可用时，它会读取`pom.xml`，否则会询问你。之后的每个答案都基于该版本。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>在webforJ知识库中查找信息</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手可以查询整个webforJ知识库，从中获取基于真实框架的答案。结果范围根据你询问的内容而定——API问题、指南、代码示例或Kotlin DSL。

      **示例提示：**
      ```
      "查找webforJ Button组件的事件处理示例"

      "如何在webforJ中使用@Route设置路由？"

      "给我展示一个webforJ表单验证示例"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>搭建一个新的webforJ项目</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手根据你的要求（原型、Spring集成、名称、组）生成新的webforJ应用程序的正确Maven原型命令。

      **原型：**
      - `hello-world` - 带有示例组件的启动应用
      - `blank` - 最小项目结构
      - `tabs` - 标签式界面布局
      - `sidemenu` - 侧导航布局

      **风格：**
      - `webforj` - 标准webforJ应用
      - `webforj-spring` - 与Spring Boot集成的webforJ

      **示例提示：**
      ```
      "使用sidemenu原型创建一个名为CustomerPortal的webforJ项目"

      "生成一个名为Dashboard的webforJ Spring Boot项目，并使用tabs布局"
      ```

      :::tip 可用的原型
      有关原型的完整列表，请参见[原型目录](/docs/building-ui/archetypes/overview)。
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>生成DWC主题</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      从单一品牌颜色开始，助手生成完整的DWC主题：主要、成功、警告、危险、信息、默认和灰色调色板，具有自动文本对比。输出包括样式表和`@AppTheme` / `@StyleSheet`接线。

      **示例提示：**
      ```
      "从品牌颜色#6366f1生成一个webforJ主题"

      "创建一个以HSL 220, 70, 50为主色的可访问主题"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>正确样式化DWC组件</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手在编写任何CSS之前，读取每个DWC组件的真实样式表面——CSS自定义属性、影子部分、反射属性和插槽。它还可以枚举每个DWC标签，并解析webforJ Java类名称（`Button`、`TextField`）到它们的DWC等价项。

      **示例提示：**
      ```
      "dwc-button暴露了哪些CSS变量和部分？"

      "给我看dwc-dialog上可用的每个插槽"

      "webforJ TextField类映射到哪个DWC标签？"
      ```

      将其与[样式应用代理技能](/docs/ai-tooling/agent-skills)配对，形成端到端样式工作流程。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>处理DWC设计令牌</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手可以列出你webforJ版本的`--dwc-*`令牌的权威目录——调色板种子、阴影、表面、间距、排版、边框——按照前缀或子字符串进行过滤。它还将验证你提供的任何CSS、Java或Markdown源，与真实令牌目录进行比对，并将未知名称标记为建议修正。

      **示例提示：**
      ```
      "列出每个--dwc-space-*令牌"

      "验证app.css是否存在未知的--dwc-*令牌"

      "哪些主调色板的阴影可用？"
      ```

      验证会在令牌悄悄失败为CSS之前，捕获拼写错误和伪造的令牌。
    </div>
  </AccordionDetails>
</Accordion>

## 撰写良好提示 {#writing-good-prompts}

只有在助手认为相关时，才会咨询MCP服务器。养成几个习惯可以保持它高效：

- **命名框架。** 在提示中提到“webforJ”，以便助手使用MCP服务器，而不是它的通用Java知识。
- **要具体。** `"创建一个名为InventorySystem的webforJ项目，使用sidemenu原型和Spring Boot"`优于`"制作一个应用"`。
- **请求验证。** 像`"验证与webforJ文档相符"`或`"检查此CSS是否有坏的--dwc-*令牌"`这样的短语会促使助手使用工具，而不是猜测。

如果你的助手仍然在不咨询服务器的情况下回答，请安装[webforJ AI插件](https://github.com/webforj/webforj-ai) - 它提供匹配的代理技能，提示助手自动使用MCP工具处理webforJ任务。

## 常见问题 {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么AI助手没有使用MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数助手只有在认为问题需要时，才会寻找MCP。两个解决办法：

      1. **安装[webforJ AI插件](https://github.com/webforj/webforj-ai)**，它将服务器与代理技能配对，这些技能会告诉助手在处理webforJ任务时使用MCP。
      2. **在你的提示中明确说明**：在问题中包括“webforJ”，对于顽固的情况可以说“使用webforJ MCP服务器回答”。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何验证MCP连接是否正常？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用MCP检查器：

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      然后在检查器中连接到`https://mcp.webforj.com/mcp`并探索可用工具。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何报告问题？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用[webforJ MCP问题模板](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)打开一个票据。包括提示、预期结果和你得到的结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
