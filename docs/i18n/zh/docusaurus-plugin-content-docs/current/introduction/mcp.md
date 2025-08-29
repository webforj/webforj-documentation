---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 7b656643222d616e7c44d14ed1de7bd3
---
webforJ 模型上下文协议 (MCP) 服务器为 AI 助手提供了直接访问官方 webforJ 文档、经过验证的代码示例和特定于框架的模式的能力，使得回答更准确，并且能够自动生成专门用于 webforJ 开发的项目。

## 什么是 MCP？

模型上下文协议是一种开放标准，允许 AI 助手与外部工具和文档连接。webforJ MCP 服务器实现了该协议，以提供：

- **知识搜索** - 在 webforJ 文档、代码示例和模式中进行自然语言搜索
- **项目生成** - 根据官方模板创建具有适当结构的 webforJ 应用程序
- **主题创建** - 生成符合 webforJ 设计模式的可访问 CSS 主题

## 为什么使用 MCP？

虽然 AI 编码助手在回答基本问题方面表现很好，但它们在处理跨多个文档部分的复杂 webforJ 特定查询时则显得力不从心。没有直接访问官方来源的能力，它们可能会：

- 生成在 webforJ 中不存在的方法
- 引用过时或不正确的 API 模式
- 提供无法编译的代码
- 将 webforJ 语法与其他 Java 框架混淆
- 误解特定于 webforJ 的模式

通过 MCP 集成，AI 回应与实际的 webforJ 文档、代码示例和框架模式相结合，提供可以验证的答案，并直接链接到官方来源以供深入探索。

:::warning AI 仍然可能出错
虽然 MCP 通过提供访问官方 webforJ 资源的能力显著提高了准确性，但并不能保证完美的代码生成。AI 助手在复杂场景中仍可能出错。使用生成的代码前，请务必进行验证和充分测试。
:::

## 安装

webforJ MCP 服务器托管在 `https://mcp.webforj.com`，具有两个端点：

- **MCP 端点** (`/mcp`) - 用于 Claude、VS Code、Cursor
- **SSE 端点** (`/sse`) - 用于遗留客户

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

这将自动在您的 Claude Code 环境中配置 MCP 服务器。

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

通过 Claude Desktop 设置中的集成面板添加此服务器：

1. 打开 Claude Desktop 并转到设置
2. 在侧边栏中单击“集成”
3. 单击“添加集成”并粘贴 URL：`https://mcp.webforj.com/mcp`
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

工具是 MCP 服务器为 AI 助手提供的专用功能。当您询问问题或提出请求时，AI 可以调用这些工具以搜索文档、生成项目或创建主题。每个工具接受特定参数并返回结构化数据，以帮助 AI 提供准确、上下文感知的帮助。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - 搜索文档和示例
  </AccordionSummary>
  <AccordionDetails>
    <div>
      此工具在整个 webforJ 文档生态系统中提供语义搜索能力。它理解不同框架概念之间的上下文和关系，返回相关的文档部分、API 参考和有效的代码示例。

      **示例查询：**
      ```
      "搜索 webforJ 文档中包含图标示例的按钮组件"

      "查找最新文档中的 webforJ 表单验证模式"

      "向我展示当前的 webforJ 路由设置，包含 @Route 注释"

      "搜索 webforJ 文档中的 FlexLayout 响应式设计模式"

      "查找官方文档中的 webforJ Web 组件集成"
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
      使用官方 Maven 原型搭建完整的 webforJ 应用程序。该工具创建一个标准化的项目目录布局，并根据所选模板包含启动代码。生成的项目包括一个即用的构建系统、资源文件夹和配置文件，以便立即进行开发和部署。

      **示例提示：**
      ```
      "创建一个名为 CustomerPortal 的 webforJ 项目，使用 hello-world 原型"

      "生成一个名为 Dashboard 的 webforJ Spring Boot 项目，带选项卡布局"

      "为 AdminPanel 项目创建一个新的 webforJ 应用程序，使用 sidemenu 原型"

      "生成一个名为 TestApp 的 webforJ 空项目，使用 com.example groupId"

      "使用 sidemenu 原型与 Spring Boot 创建 webforJ 项目 InventorySystem"
      ```

      使用此工具时，您可以从几个项目模板中选择：

      **原型**（项目模板）：
      - `hello-world` - 具有示例组件的基本应用程序，以展示 webforJ 功能
      - `blank` - 从头开始的最小项目结构
      - `tabs` - 预构建的选项卡界面布局，用于多视图应用程序
      - `sidemenu` - 管理面板或仪表板的侧导航菜单布局

      **风格**（框架集成）：
      - `webforj` - 标准 webforJ 应用程序
      - `webforj-spring` - 与 Spring Boot 集成的 webforJ，用于依赖注入和企业功能

      :::tip 可用原型
      webforJ 附带多个预定义原型，以帮助您快速入门。有关可用原型的完整列表，请参见 [原型目录](../building-ui/archetypes/overview)。
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
      使用 [DWC HueCraft](https://huecraft.dwc.style/) 生成 webforJ 主题配置。该工具创建完整的 CSS 自定义属性集，包括主色、次色、成功、警告、危险和中性色变体。

      **示例请求：**
      ```
      "生成一个 webforJ 主题，主色为 HSL 220, 70, 50 代表我们的企业品牌"

      "创建一个名为 'ocean' 的可访问 webforJ 主题，主色为 #0066CC"

      "生成一个使用我们的品牌色 #FF5733 的 webforJ 主题"

      "为我们的应用程序创建一个名为 'sunset' 的 webforJ 主题，主色为 HSL 30, 100, 50"

      "生成一个主色为 RGB 44, 123, 229 的可访问 webforJ 主题"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## 可用提示 {#available-prompts}

提示是预配置的 AI 指令，结合多个工具和工作流程以执行常见任务。它们引导 AI 通过特定步骤和参数提供可靠、可重复的结果。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - 创建并运行一个 webforJ 应用程序
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `appName`（必需） - 应用程序名称（例如：MyApp、TodoList、Dashboard）
      - `archetype`（必需） - 选择：`blank`、`hello-world`、`tabs`、`sidemenu`
      - `runServer`（可选） - 自动运行开发服务器（是/否）
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
      - `primaryColor`（必需） - 以十六进制（#FF5733）、rgb（255,87,51）或 hsl（9,100,60）格式表示的颜色
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - 具有自主解决问题的高级搜索
  </AccordionSummary>
  <AccordionDetails>
    <div>
      此提示配置 AI 执行以下操作：

      1. 广泛搜索知识库
      2. 编写完整的、可生产使用的代码
      3. 使用 `mvn compile` 编译项目，以验证没有构建错误
      4. 迭代修复错误，直到一切正常
    </div>
  </AccordionDetails>
</Accordion>

### 如何使用提示

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code 和 Claude Code">

1. 在聊天中输入 <kbd>/</kbd> 以查看可用提示
2. 从下拉菜单中选择一个提示
3. 在提示时填写所需的参数

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. 单击提示输入区域中的 **+**（加号）图标
2. 从菜单中选择 **"从 webforJ 添加"**
3. 选择所需的提示（例如：`create-app`、`create-theme`、`search-webforj`）
4. Claude 会提示您输入所需的参数
5. 按要求填写参数

:::tip 验证 MCP 是否连接
查看输入区域右下角的工具图标，以确认 webforJ MCP 服务器已连接。
:::

</TabItem>
</Tabs>

## 最佳实践

为了获得最准确和最新的 webforJ 帮助，请遵循以下准则以充分利用 MCP 服务器功能。

### 确保使用 MCP 服务器

AI 模型可能会跳过 MCP 服务器，如果它们认为自己已经知道答案。为了确保实际使用 MCP 服务器：

- **明确提到 webforJ**：在查询中始终提到“webforJ”，以触发特定于框架的搜索
- **请求当前信息**：包括诸如“最新 webforJ 文档”或“当前 webforJ 模式”的短语
- **请求经过验证的示例**：请求“有效的 webforJ 代码示例”以强制查找文档
- **引用特定版本**：提及您使用的 webforJ 版本（例如：“webforJ `25.02`”）以获得准确的结果

### 编写具体的提示

**好的示例：**
```
"搜索 webforJ 文档中按钮组件事件处理的示例"

"创建一个名为 InventorySystem 的 webforJ 项目，使用 sidemenu 原型和 Spring Boot"

"生成一个以 HSL 220, 70, 50 为主色的 webforJ 主题，代表企业品牌"
```

**差的示例：**
```
"按钮是如何工作的"

"制作一个应用程序"

"做成蓝色"
```

### 强制使用 MCP 工具

如果 AI 提供通用答案而没有使用 MCP 服务器：

1. **明确请求**：“使用 webforJ MCP 服务器搜索 `[query]`”
2. **请求文档引用**：“在 webforJ 文档中查找如何 `[query]`”
3. **请求验证**：“将此解决方案与 webforJ 文档进行验证”
4. **具体到框架**：在查询中始终包括“webforJ”

## AI 自定义 {#ai-customization}

配置您的 AI 助手，以自动使用 MCP 服务器并遵循 webforJ 最佳实践。添加项目特定的指令，以便您的 AI 助手始终使用 MCP 服务器，遵循 webforJ 文档标准，并提供符合您团队需求的准确、最新答案。

### 项目配置文件

- 对于 **VS Code 和 Copilot**，创建 `.github/copilot-instructions.md`
- 对于 **Claude Code**，在项目根目录下创建 `CLAUDE.md`

将以下内容添加到创建的 markdown 文件中：
```markdown
## 使用 webforJ MCP 服务器回答任何 webforJ 问题

- 始终调用 "webforj-knowledge-base" 工具以获取与问题相关的文档
- 验证所有 API 签名与官方文档的一致性
- 在没有检查的情况下，绝不假设方法名称或参数存在

在建议之前，请始终验证代码是否能通过 `mvn compile` 编译。
```

## 常见问题

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么 AI 不使用 webforJ MCP 服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数 AI 助手需要明确的指令才能使用 MCP 服务器。根据 [AI 自定义](#ai-customization) 部分中的说明配置您的 AI 客户端。如果没有这些说明，AI 助手可能会默认使用其训练数据而不是查询 MCP 服务器。

      **快速解决方案：**
      在您的提示中包含“使用 webforJ MCP”或创建适当的配置文件（`.github/copilot-instructions.md` 或 `CLAUDE.md`）。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何验证 MCP 连接是否正常工作</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用 MCP 检查器来调试连接：

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      等待消息：`🔍 MCP 检查器在 http://127.0.0.1:6274 上运行`（端口可能会有所不同）

      然后在检查器中：
      1. 输入 MCP 服务器 URL：`https://mcp.webforj.com/mcp`
      2. 单击“连接”以建立连接
      3. 查看可用工具并测试查询
      4. 监视请求/响应日志以进行调试
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>MCP 和 SSE 端点之间有什么区别？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      webforJ MCP 服务器提供两个端点：

      - **MCP 端点** (`/mcp`) - 适用于 Claude、VS Code、Cursor 的现代协议
      - **SSE 端点** (`/sse`) - 适用于像 Windsurf 这样的遗留客户端的服务器推送事件

      大多数用户应使用 MCP 端点。如果您的客户端不支持标准的 MCP 协议，则仅使用 SSE。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>是否可以在没有配置文件的情况下使用 MCP 服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      是的，但不推荐。没有配置文件，您必须在每次对话中手动提示 AI 使用 MCP 服务器。配置文件会自动指示 AI 在每次交互中使用 MCP 服务器，因此您无需每次都重复说明。

      **手动方法：**
      开始提示时使用：“使用 webforJ MCP 服务器...”

      **替代：使用预配置的提示**
      MCP 服务器提供可以在没有配置文件的情况下工作的提示：
      - `/create-app` - 生成新的 webforJ 应用程序
      - `/create-theme` - 创建可访问的 CSS 主题
      - `/search-webforj` - 高级文档搜索

      请参见 [可用提示](#available-prompts) 获取详情。
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
      
      **常见问题报告：**
      - 搜索结果中的文档过时
      - 缺失的 API 方法或组件
      - 不正确的代码示例
      - 工具执行错误

      报告问题时，请包含您的查询、预期结果和实际结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
