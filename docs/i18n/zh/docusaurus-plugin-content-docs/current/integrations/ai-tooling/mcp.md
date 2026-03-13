---
title: MCP Server
sidebar_position: 5
_i18n_hash: a45888cf39bbbce0002177da8fe95eba
---
webforJ 模型上下文协议（MCP）服务器为 AI 助手提供直接访问官方 webforJ 文档、经验证的代码示例和框架特定模式的能力，使其能够提供更准确的答案并自动生成专门针对 webforJ 开发的项目。

## 什么是 MCP？

模型上下文协议是一种开放标准，使 AI 助手能够与外部工具和文档连接。webforJ MCP 服务器实现了这一协议，提供：

- **知识搜索** - 在 webforJ 文档、代码示例和模式中进行自然语言搜索
- **项目生成** - 从官方模板创建具有适当结构的 webforJ 应用程序
- **主题创建** - 生成遵循 webforJ 设计模式的可访问 CSS 主题

## 为何使用 MCP？

虽然 AI 编码助手在回答基本问题方面表现出色，但它们在处理跨多个文档部分的复杂 webforJ 特定查询时表现不佳。如果没有直接访问官方来源，它们可能会：

- 生成 webforJ 中不存在的方法
- 引用过时或不正确的 API 模式  
- 提供无法编译的代码
- 混淆 webforJ 语法与其他 Java 框架
- 错误理解 webforJ 特定模式

通过 MCP 集成，AI 的响应能够基于实际的 webforJ 文档、代码示例和框架模式，提供可验证的答案，并附带直接链接到官方来源以便深入探索。

:::warning AI 仍然可能出错
虽然 MCP 通过提供访问官方 webforJ 资源的能力显著提高了准确性，但它并不保证完美的代码生成。AI 助手在复杂场景中仍可能出错。在生产环境中使用之前，始终验证生成的代码并进行彻底测试。
:::

## 安装

webforJ MCP 服务器托管在 `https://mcp.webforj.com`，有两个端点：

- **MCP 端点** (`/mcp`) - 针对 Claude、VS Code、Cursor
- **SSE 端点** (`/sse`) - 针对旧版客户端

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

将以下配置添加到您的 VS Code settings.json 文件中：

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
<TabItem value="cursor" label="Cursor">

将以下配置添加到您的 Cursor 设置中：

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

使用 Claude CLI 命令注册服务器：

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

这将自动配置您 Claude Code 环境中的 MCP 服务器。

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

使用 Claude Desktop 设置中的集成面板添加此服务器：

1. 打开 Claude Desktop 并转到设置
2. 点击侧边栏中的“集成”
3. 点击“添加集成”并粘贴 URL: `https://mcp.webforj.com/mcp`
4. 按照设置向导完成配置

有关详细说明，请参见 [官方集成指南](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp)。

</TabItem>
<TabItem value="windsurf" label="Windsurf">

将以下服务器配置添加到您的 Windsurf MCP 设置中：

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## 可用工具

工具是 MCP 服务器为 AI 助手提供的专门功能。当您提问或发出请求时，AI 可以调用这些工具以搜索文档、生成项目或创建主题。每个工具接受特定参数并返回结构化数据，以帮助 AI 提供准确、上下文相关的支持。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - 搜索文档和示例
  </AccordionSummary>
  <AccordionDetails>
    <div>
      此工具提供整个 webforJ 文档生态系统的语义搜索能力。它理解不同框架概念之间的上下文和关系，返回相关的文档部分、API 参考和可工作的代码示例。

      **示例查询：**
      ```
      "搜索 webforJ 文档中的按钮组件及图标示例"

      "在最新文档中查找 webforJ 表单验证模式"

      "给我展示带有 @Route 注解的当前 webforJ 路由设置"

      "搜索 webforJ 文档中的 FlexLayout 响应式设计模式"

      "在官方文档中查找 webforJ 网络组件集成"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - 生成新的 webforJ 项目  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用官方 Maven 原型搭建完整的 webforJ 应用程序。该工具创建标准化的项目目录布局，并根据所选模板包含入门代码。生成的项目包含一个即用的构建系统、资源文件夹和配置文件，便于立即开发和部署。

      **示例提示：**
      ```
      "使用 hello-world 原型创建名为 CustomerPortal 的 webforJ 项目"

      "生成一个名为 Dashboard 的 webforJ Spring Boot 项目，采用标签布局"

      "为 AdminPanel 项目创建一个新的 webforJ 应用程序，采用 sidemenu 原型"

      "生成一个名为 TestApp 的 webforJ 空白项目，groupId 为 com.example"

      "使用 sidemenu 原型并采用 Spring Boot 创建名为 InventorySystem 的 webforJ 项目"
      ```

      使用此工具时，您可以从多个项目模板中进行选择：

      **原型**（项目模板）：
      - `hello-world` - 一个基本应用，包含示例组件以演示 webforJ 功能
      - `blank` - 从头开始的最小项目结构
      - `tabs` - 预构建的选项卡界面布局，适用于多视图应用
      - `sidemenu` - 管理面板或仪表板的侧边导航菜单布局

      **框架整合**（框架集成）：
      - `webforj` - 标准的 webforJ 应用
      - `webforj-spring` - webforJ 与 Spring Boot 集成，支持依赖注入和企业功能

      :::tip 可用的原型
      webforJ 附带多个预定义的原型，以帮助您快速入门。有关可用原型的完整列表，请参见 [原型目录](/docs/building-ui/archetypes/overview)。
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - 创建可访问的 CSS 主题
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用 [DWC HueCraft](https://huecraft.dwc.style/) 生成 webforJ 主题配置。该工具创建完整的 CSS 自定义属性集，包含主色、次色、成功、警告、危险和中性色变体。

      **示例请求：**
      ```
      "生成一个将 HSL 220, 70, 50 作为主色的 webforj 主题，用于我们的企业品牌"

      "创建名为 'ocean' 的 webforJ 可访问主题，主色为 #0066CC"

      "生成一个使用我们品牌色 #FF5733 的 webforJ 主题"

      "为我们的应用创建名为 'sunset' 的 webforJ 主题，主色为 HSL 30, 100, 50"

      "生成主色为 RGB 44, 123, 229 的可访问 webforJ 主题"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## 可用提示 {#available-prompts}

提示是预配置的 AI 指令，结合多个工具和工作流程以处理常见任务。它们引导 AI 通过特定步骤和参数交付可靠、可重复的结果，以支持每个工作流。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - 创建并运行 webforJ 应用程序
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `appName`（必需） - 应用名称（例如，MyApp、TodoList、Dashboard）
      - `archetype`（必需） - 选择：`blank`、`hello-world`、`tabs`、`sidemenu`
      - `runServer`（可选） - 自动运行开发服务器（yes/no）
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - 从主色生成 webforJ 主题
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `primaryColor`（必需） - 颜色格式为 hex (#FF5733)、rgb (255,87,51) 或 hsl (9,100,60)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - 具有自主解决问题的高级搜索
  </AccordionSummary>
  <AccordionDetails>
    <div>
      该提示配置 AI 以：

      1. 广泛搜索知识库
      2. 编写完整的生产就绪代码
      3. 使用 `mvn compile` 编译项目以验证没有构建错误
      4. 逐步解决错误，直到一切正常运行
    </div>
  </AccordionDetails>
</Accordion>

### 如何使用提示

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code 和 Claude Code">

1. 在聊天中输入 <kbd>/</kbd> 查看可用提示
2. 从下拉菜单中选择一个提示
3. 填写所需的参数

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. 点击提示输入区域中的 **+**（加号）图标
2. 从菜单中选择 **"从 webforJ 添加"**
3. 选择所需提示（例如，`create-app`、`create-theme`、`search-webforj`）
4. Claude 会提示您输入所需的参数
5. 按要求填写参数

:::tip 验证 MCP 是否连接
查看输入区域右下角的工具图标以确认 webforJ MCP 服务器已连接。
:::

</TabItem>
</Tabs>

## 最佳实践

为了获得最准确和最新的 webforJ 支持，请遵循以下指南，以充分利用 MCP 服务器的功能。

### 确保使用 MCP 服务器

AI 模型可能会跳过 MCP 服务器，认为它们已经知道答案。要确保实际使用 MCP 服务器：

- **明确提到 webforJ**: 在查询中始终提到 "webforJ" 以触发框架特定的搜索
- **请求当前信息**: 包括“最新 webforJ 文档”或“当前 webforJ 模式”等短语
- **请求验证的示例**: 请求“有效的 webforJ 代码示例”以强制查阅文档
- **引用特定版本**: 提及您的 webforJ 版本（例如，“webforJ `25.02`”）以获得准确结果

### 编写具体提示

**好的示例：**
```
"搜索 webforJ 文档中的按钮组件事件处理及示例"

"使用 sidemenu 原型创建名为 InventorySystem 的 webforJ 项目，采用 Spring Boot"

"生成一个将 HSL 220, 70, 50 作为主色的 webforJ 主题，用于企业品牌"
```

**差的示例：**
```
"按钮是如何工作的"

"制作一个应用程序"

"把它做成蓝色"
```

### 强制使用 MCP 工具

如果 AI 提供的答案过于通用，而没有使用 MCP 服务器：

1. **明确请求**: "使用 webforJ MCP 服务器搜索 `[query]`"
2. **请求文档引用**: "在 webforJ 文档中查找如何 `[query]`"
3. **请求验证**: "根据 webforJ 文档验证该解决方案"
4. **框架特定**: 始终在查询中包括 "webforJ"

## AI 自定义 {#ai-customization}

配置您的 AI 助手，使其自动使用 MCP 服务器并遵循 webforJ 的最佳实践。添加项目特定的说明，以便您的 AI 助手始终使用 MCP 服务器，遵循 webforJ 文档标准，并提供符合您团队要求的准确、最新的答案。

### 项目配置文件

- 对于 **VS Code 和 Copilot**，创建 `.github/copilot-instructions.md`
- 对于 **Claude Code**，在项目根目录创建 `CLAUDE.md`

在创建的 Markdown 文件中添加以下内容：
```markdown
## 使用 webforJ MCP 服务器回答任何 webforJ 问题

- 在提问时始终调用 "webforj-knowledge-base" 工具以获取与问题相关的文档
- 所有 API 签名须与官方文档进行验证
- 在没有检查的情况下，绝不假设方法名或参数存在

建议在建议之前始终验证代码是否通过 `mvn compile` 编译。
```

## 常见问题

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么 AI 不使用 webforJ MCP 服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数 AI 助手需要明确的指示才能使用 MCP 服务器。使用 [AI 自定义](#ai-customization) 部分中的说明配置您的 AI 客户端。在没有这些说明的情况下，AI 助手可能会默认使用其训练数据，而不是查询 MCP 服务器。

      **快速解决办法：**
      包括“使用 webforJ MCP”在您的提示中，或创建合适的配置文件（`.github/copilot-instructions.md` 或 `CLAUDE.md`）。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何验证 MCP 连接是否正常</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用 MCP 检查器调试连接：

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      等待消息：`🔍 MCP Inspector is up and running at http://127.0.0.1:6274`（端口可能会有所不同）

      然后在检查器中：
      1. 输入 MCP 服务器 URL: `https://mcp.webforj.com/mcp`
      2. 点击“连接”以建立连接
      3. 查看可用工具并测试查询
      4. 监控请求/响应日志以进行调试
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>MCP 和 SSE 端点有什么区别？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      webforJ MCP 服务器提供两个端点：

      - **MCP 端点** (`/mcp`) - 针对 Claude、VS Code、Cursor 的现代协议
      - **SSE 端点** (`/sse`) - 服务器推送事件，针对 Windsurf 等旧客户端

      大多数用户应使用 MCP 端点。仅在客户端不支持标准 MCP 协议时才使用 SSE。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>是否可以在没有配置文件的情况下使用 MCP 服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      可以，但不推荐。在没有配置文件的情况下，您必须在每次对话中手动提示 AI 使用 MCP 服务器。配置文件会自动指示 AI 在每次交互中使用 MCP 服务器，因此您不必每次都重复说明。

      **手动方法：**
      提示以：“使用 webforJ MCP 服务器来...”

      **替代方法：使用预配置提示**
      MCP 服务器提供可以在没有配置文件的情况下工作的提示：
      - `/create-app` - 生成新的 webforJ 应用程序
      - `/create-theme` - 创建可访问的 CSS 主题
      - `/search-webforj` - 高级文档搜索

      有关详细信息，请参见 [可用提示](#available-prompts)。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何贡献或报告问题</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **报告问题：** [webforJ MCP 问题模板](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **常见问题需报告：**
      - 搜索结果中文档过时
      - 缺失 API 方法或组件
      - 错误的代码示例
      - 工具执行错误

      在报告问题时，请包括您的查询、预期结果和实际结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
