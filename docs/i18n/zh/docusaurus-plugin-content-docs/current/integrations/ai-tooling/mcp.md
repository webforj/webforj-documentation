---
title: MCP Server
sidebar_position: 5
_i18n_hash: eea9d8f962b10512151bf7c6935f25e0
---
webforJ模型上下文协议（MCP）服务器将AI编码助手集成到webforJ的文档、API、设计令牌和脚手架工具中。助手通过询问服务器而不是猜测框架约定，获取基于真实webforJ的答案。

:::tip 使用插件
除非您确定只想要MCP服务器，否则请安装**[webforJ AI插件](/docs/integrations/ai-tooling)** - 它将该服务器与匹配的[代理技能](/docs/integrations/ai-tooling/agent-skills)捆绑在一起，便于一次安装。
:::

## 什么是MCP？ {#whats-an-mcp}

模型上下文协议是一个开放标准，它允许AI助手按需调用外部工具。webforJ MCP服务器实现了该协议，以便您的助手可以：

- 在webforJ文档中查找内容，而不是想当然地推测方法名称
- 从官方Maven原型中搭建新的webforJ项目
- 从品牌颜色生成可访问的DWC主题
- 读取DWC组件的真实样式表面，并验证任何`--dwc-*`令牌在落入您的CSS之前的有效性

:::warning AI仍然可能出错
MCP服务器显著提高了准确性，但在复杂场景中，AI助手仍然可能生成不正确的代码。在发货之前，请始终审查和测试生成的代码。
:::

## 安装 {#installation}

为了获得完整体验，请安装**[webforJ AI插件](/docs/integrations/ai-tooling)** - 它将该服务器与助手需要的代理技能一起配置。

如果您只想要MCP服务器（没有技能），请将您的客户端指向`https://mcp.webforj.com/mcp`：

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Copilot CLI推荐的路径是**[webforJ AI插件](/docs/integrations/ai-tooling)** - 它一步注册MCP服务器。有关仅MCP设置的原始路径，请参阅[webforJ AI仓库](https://github.com/webforj/webforj-ai#clients)中的每个客户端说明。

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

添加到您的VS Code设置中：

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

Cursor、Kiro、Goose、Junie、Antigravity以及任何其他基于MCP的HTTP客户端也可以工作 - 它们只需使用自己的配置格式。有关每个客户端的确切代码片段，请参见[每个客户端的安装指南](https://github.com/webforj/webforj-ai#clients)。

## 服务器能做什么 {#capabilities}

当MCP服务器连接时，您的AI助手将获得以下功能。它们中的任何一种都可以通过自然语言请求触发 - 助手会自动选择正确的一个。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>定位正确的webforJ版本</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      在回答版本敏感问题（与样式或API相关的任何问题）之前，助手会查明您正在使用的webforJ版本。当可用时，它会读取`pom.xml`，否则会询问您。所有后续答案都将与该版本相关。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>在webforJ知识库中查找信息</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手可以查询完整的webforJ知识库，以获取基于真实框架的答案。结果将与您的提问相关 - API问题、指南、代码示例或Kotlin DSL。

      **示例提示：**
      ```
      "查找webforJ Button组件的事件处理示例"

      "如何在webforJ中使用@Route设置路由？"

      "给我看一个webforJ表单验证的示例"
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
      助手根据您的要求（原型、Spring集成、名称、组）生成新webforJ应用程序的正确Maven原型命令。

      **原型：**
      - `hello-world` - 带有示例组件的入门应用
      - `blank` - 最小项目结构
      - `tabs` - 选项卡界面布局
      - `sidemenu` - 侧导航布局

      **风味：**
      - `webforj` - 标准webforJ应用
      - `webforj-spring` - 与Spring Boot集成的webforJ

      **示例提示：**
      ```
      "创建一个名为CustomerPortal的webforJ项目，使用sidemenu原型"

      "生成一个名为Dashboard的webforJ Spring Boot项目，带有选项卡布局"
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
      从单一品牌颜色，助手生成一个完整的DWC主题：主要、成功、警告、危险、信息、默认和灰色调色板，自动文本对比。输出包括样式表以及`@AppTheme` / `@StyleSheet`的连接。

      **示例提示：**
      ```
      "基于品牌颜色#6366f1生成webforJ主题"

      "创建一个带有HSL 220, 70, 50作为主色的可访问主题"
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
      助手在撰写任何CSS之前，会读取每个DWC组件的真实样式表面 - CSS自定义属性、阴影部件、反射属性和插槽。它还可以列出每个DWC标签，并将webforJ Java类名称（`Button`、`TextField`）解析为其DWC等效项。

      **示例提示：**
      ```
      "dwc-button暴露了哪些CSS变量和部件？"

      "显示dwc-dialog上可用的每个插槽"

      "webforJ的TextField类映射到哪个DWC标签？"
      ```

      将此与[样式应用代理技能](/docs/integrations/ai-tooling/agent-skills)搭配使用，以实现端到端样式工作流程。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>处理DWC设计令牌</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      助手可以列出您webforJ版本的`--dwc-*`令牌的权威目录 - 调色板种子、阴影、表面、间距、排版、边框 - 按前缀或子字符串过滤。它还会根据实际令牌目录验证您提供的任何CSS、Java或Markdown源，并用建议的更正标记未知名称。

      **示例提示：**
      ```
      "列出每个--dwc-space-*令牌"

      "验证app.css中是否有未知的--dwc-*令牌"

      "可用的主调色板阴影有哪些？"
      ```

      验证可以在发布为默默失败的CSS之前捕捉到拼写错误和虚构的令牌。
    </div>
  </AccordionDetails>
</Accordion>

## 如何编写好的提示 {#writing-good-prompts}

只有当您的助手认为相关时，才会咨询MCP服务器。以下几个习惯能保持其参与：

- **提及框架名称。** 在提示中提到“webforJ”，以便助手直接使用MCP服务器，而不是其一般的Java知识。
- **要具体。** `"创建一个名为InventorySystem的webforJ项目，使用sidemenu原型和Spring Boot"` 优于 `"做一个应用程序"`。
- **请求验证。** 诸如`"验证与webforJ文档对比"`或`"检查此CSS中是否有错误的--dwc-*令牌"`等短语会促使助手使用工具，而不是猜测。

如果您的助手仍然在没有咨询服务器的情况下回答，安装[webforJ AI插件](https://github.com/webforj/webforj-ai) - 它配备匹配的代理技能，自动提示助手使用MCP工具来完成webforJ任务。

## 常见问题解答 {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么AI助手不使用MCP服务器？</p>
    <p>为什么AI助手不使用MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数助手只有在他们认为问题需要时才会使用MCP。有两个解决方案：

      1. **安装[webforJ AI插件](https://github.com/webforj/webforj-ai)**，它将服务器与告知助手在webforJ任务中使用MCP的代理技能配对。
      2. **在您的提示中明确说明**：在问题中包含“webforJ”，对于顽固的情况，可以说“使用webforJ MCP服务器来回答”。
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

      然后在检查器中，连接到`https://mcp.webforj.com/mcp`并探索可用工具。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何报告问题？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用[webforJ MCP问题模板](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)打开一个票据。包括提示、预期结果和您得到的结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
