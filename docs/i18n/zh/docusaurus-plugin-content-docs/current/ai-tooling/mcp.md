---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: fb0e068ee7d7a489237e021b24a883b0
---
webforJ模型上下文协议（MCP）服务器将人工智能编码助手集成到webforJ的文档、API、设计令牌和搭建工具中。助手不是猜测框架约定，而是询问服务器，从而得到基于真实webforJ的回答。

:::tip 使用插件
除非你确定只想要MCP服务器，否则安装**[webforJ AI插件](/docs/ai-tooling)** - 它将此服务器与匹配的[代理技能](/docs/ai-tooling/agent-skills)打包在一个安装中。
:::

## 什么是MCP？ {#whats-an-mcp}

模型上下文协议是一种开放标准，它允许人工智能助手按需调用外部工具。webforJ MCP服务器实现了该协议，以便你的助手可以：

- 在webforJ文档中查找信息，而不是凭空想象方法名称
- 根据官方Maven原型搭建新的webforJ项目
- 从品牌颜色生成可访问的DWC主题
- 读取DWC组件的真实样式表面，并在CSS中应用任何`--dwc-*`令牌之前进行验证

:::warning 人工智能仍然可能犯错误
MCP服务器显著提高了准确性，但人工智能助手在复杂场景中仍可能生成错误代码。在发布之前，请始终审查和测试生成的代码。
:::

## 安装 {#installation}

要获得完整体验，请安装**[webforJ AI插件](/docs/ai-tooling)** - 它会将此服务器与助手使用所需的代理技能一起配置。

如果你只想要MCP服务器（无技能），请将你的客户端指向`https://mcp.webforj.com/mcp`：

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

在Copilot CLI上推荐的路径是**[webforJ AI插件](/docs/ai-tooling)** - 它会一步到位为你注册MCP服务器。有关原始的仅限MCP的设置，请参阅[webforJ AI仓库](https://github.com/webforj/webforj-ai#clients)中的每个客户端说明。

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

添加到你的VS Code设置中：

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

Cursor、Kiro、Goose、Junie、Antigravity及任何其他支持MCP-over-HTTP的客户端也可以使用 - 它们仅使用自己的配置格式。有关每个客户端的确切代码片段，请参阅[每个客户端的安装指南](https://github.com/webforj/webforj-ai#clients)。

## 服务器的功能 {#capabilities}

当MCP服务器连接时，你的AI助手获得以下功能。它们中的任何一个都可以通过自然语言请求触发 - 助手会自动选择正确的。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>定位正确的webforJ版本</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      在回答版本敏感的问题（任何样式或API特定内容）之前，助手会确定你所使用的webforJ版本。它会在可用时读取`pom.xml`，否则会向你询问。每个后续回答都限制在该版本范围内。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>查找网页forJ知识库中的信息</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手可以查询完整的webforJ知识库，以获取基于真实框架的答案。结果将根据你所询问的内容进行限制 - 一个API问题、一个指南、一个代码示例或Kotlin DSL。

      **示例提示：**
      ```
      "查找webforJ按钮组件事件处理示例"

      "如何使用@Route在webforJ中设置路由？"

      "给我展示一个webforJ表单验证示例"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>搭建新的webforJ项目</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手根据你的需求（原型、Spring集成、名称、组）生成新的webforJ应用的正确Maven原型命令。

      **原型：**
      - `hello-world` - 带有示例组件的入门应用
      - `blank` - 最小项目结构
      - `tabs` - 选项卡式界面布局
      - `sidemenu` - 侧导航布局

      **类型：**
      - `webforj` - 标准webforJ应用
      - `webforj-spring` - 集成Spring Boot的webforJ

      **示例提示：**
      ```
      "创建一个名为CustomerPortal，使用sidemenu原型的webforJ项目"

      "生成一个名为Dashboard的webforJ Spring Boot项目，使用选项卡布局"
      ```

      :::tip 可用原型
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
      从单一品牌颜色，助手生成完整的DWC主题：主色、成功、警告、危险、信息、默认和灰色调色板，自动生成文本对比度。输出包括样式表以及`@AppTheme` / `@StyleSheet`连接。

      **示例提示：**
      ```
      "从品牌颜色#6366f1生成webforJ主题"

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
      助手在编写任何CSS之前，会读取每个DWC组件的真实样式表面 - CSS自定义属性、阴影部分、反映属性和插槽。它还可以列出每个DWC标签，并将webforJ Java类名称（`Button`、`TextField`）解析为它们的DWC等效项。

      **示例提示：**
      ```
      "dwc-button暴露了哪些CSS变量和部分？"

      "给我展示dwc-dialog上可用的所有插槽"

      "webforJ TextField类映射到哪个DWC标签？"
      ```

      将其与[样式应用代理技能](/docs/ai-tooling/agent-skills)配对，以实现端到端的样式工作流。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>处理DWC设计令牌</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手可以列出你webforJ版本的`--dwc-*`令牌的权威目录 - 调色板种子、色调、表面、间距、排版、边框 - 按前缀或子字符串过滤。它还会验证你提供的任何CSS、Java或Markdown源是否匹配真实令牌目录，并标记未知名称，附带建议的修正。

      **示例提示：**
      ```
      "列出每个--dwc-space-*令牌"

      "验证app.css中是否有未知的--dwc-*令牌"

      "有哪些可用的主调色板色调？"
      ```

      验证可以在发布时捕捉到拼写错误和虚构的令牌，这样它们不会作为静默失败的CSS发布。
    </div>
  </AccordionDetails>
</Accordion>

## 撰写良好提示 {#writing-good-prompts}

只有在助手认为相关时，才会咨询MCP服务器。以下几点习惯可以使其保持活跃：

- **命名框架。** 在提示中提到“webforJ”，使助手调用MCP服务器，而不是它的通用Java知识。
- **要具体。** `"创建一个名为InventorySystem的webforJ项目，使用sidemenu原型和Spring Boot"`胜过`"创建一个应用程序"`。
- **请求验证。** 短语如`"验证是否符合webforJ文档"`或`"检查这个CSS是否存在不良的--dwc-*令牌"`提醒助手使用工具，而不是猜测。

如果你的助手仍然在未咨询服务器的情况下回答，请安装[webforJ AI插件](https://github.com/webforj/webforj-ai) - 它提供匹配的代理技能，提示助手自动使用MCP工具进行webforJ任务。

## 常见问题 {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么AI助手不使用MCP服务器？</p>
    <p>为什么AI助手不使用MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数助手只有在认为问题需要时才会调用MCP。解决方法：

      1. **安装[webforJ AI插件](https://github.com/webforj/webforj-ai)**，它将服务器与代理技能配对，告知助手在webforJ任务中使用MCP。
      2. **在提示中明确指出**：在问题中包含“webforJ”，对于顽固的情况，请说“使用webforJ MCP服务器回答”。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何验证MCP连接是否正常工作？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用MCP检查器：

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      然后在检查器中连接到`https://mcp.webforj.com/mcp`，并探索可用工具。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何报告问题？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用[webforJ MCP问题模板](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)打开一个票据。包括提示、预期结果和你获得的结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
