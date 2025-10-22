---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: cfe1c4447876aff3ab7ba15b26966cba
---
webforJ模型上下文协议（MCP）服务器为AI助手提供直接访问官方webforJ文档、经过验证的代码示例和特定于框架的模式，从而允许生成更精确的答案和专门针对webforJ开发的自动化项目。

## 什么是MCP？

模型上下文协议是一种开放标准，允许AI助手与外部工具和文档连接。webforJ MCP服务器实现了此协议，以提供：

- **知识搜索** - 在webforJ文档、代码示例和模式中进行自然语言搜索
- **项目生成** - 从官方模板创建结构正确的webforJ应用程序
- **主题创建** - 根据webforJ设计模式生成可访问的CSS主题

## 为什么使用MCP？

虽然AI编码助手在回答基本问题方面表现出色，但它们在处理跨多个文档部分的复杂webforJ特定查询时则显得力不从心。如果没有直接访问官方来源，它们可能会：

- 生成在webforJ中不存在的方法
- 引用过时或不正确的API模式  
- 提供无法编译的代码
- 将webforJ语法与其他Java框架混淆
- 误解webforJ特定模式

通过MCP集成，AI的响应与实际的webforJ文档、代码示例和框架模式相结合，提供可验证的答案，并直接链接到官方来源，以便进行深度探索。

:::warning AI仍然可能出错
虽然MCP通过提供对官方webforJ资源的访问显著提高了准确性，但并不保证完美的代码生成。AI助手在复杂情况下仍可能出错。在投入生产之前，请始终验证生成的代码并进行彻底测试。
:::

## 安装

webforJ MCP服务器托管在`https://mcp.webforj.com`，具有两个终端：

- **MCP终端** (`/mcp`) - 适用于Claude、VS Code、Cursor
- **SSE终端** (`/sse`) - 适用于遗留客户端

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

将此配置添加到您的VS Code settings.json文件：

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

将此配置添加到您的Cursor设置中：

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

使用Claude CLI命令注册该服务器：

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

这将自动在您的Claude Code环境中配置MCP服务器。

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

使用Claude Desktop设置中的集成面板添加该服务器：

1. 打开Claude Desktop并转到设置
2. 点击侧边栏中的“集成”
3. 点击“添加集成”，并粘贴URL：`https://mcp.webforj.com/mcp`
4. 按照设置向导完成配置

有关详细说明，请参见[官方集成指南](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp)。

</TabItem>
<TabItem value="windsurf" label="Windsurf">

将此服务器配置添加到您的Windsurf MCP设置中：

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

工具是MCP服务器为AI助手提供的特定功能。当您提出问题或请求时，AI可以调用这些工具来搜索文档、生成项目或创建主题。每个工具接受特定参数并返回结构化数据，帮助AI提供准确、具有上下文意识的帮助。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - 搜索文档和示例
  </AccordionSummary>
  <AccordionDetails>
    <div>
      此工具提供整个webforJ文档生态系统的语义搜索能力。它理解上下文和不同框架概念之间的关系，返回相关文档部分、API参考和工作代码示例。

      **示例查询：**
      ```
      "搜索webforJ文档中的按钮组件及图标示例"

      "在最新文档中查找webforJ表单验证模式"

      "给我显示当前webforJ路由设置和@Route注解"

      "搜索webforJ文档中的FlexLayout响应式设计模式"

      "查找官方文档中webforJ网页组件集成"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - 生成新的webforJ项目  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用官方Maven原型搭建完整的webforJ应用程序。该工具创建标准的项目目录结构，并根据所选模板包含起始代码。生成的项目包括一个即用的构建系统、资源文件夹和配置文件以供即时开发和部署。

      **示例提示：**
      ```
      "创建一个名为CustomerPortal的webforJ项目，使用hello-world原型"

      "生成一个名为Dashboard的带有标签布局的webforJ Spring Boot项目"

      "为AdminPanel项目创建一个新的webforJ应用，使用sidemenu原型"

      "生成一个名为TestApp的空白webforJ项目，带有com.example的groupId"

      "创建一个使用sidemenu原型与Spring Boot的webforJ项目InventorySystem"
      ```

      使用此工具时，您可以从几个项目模板中选择：

      **原型**（项目模板）：
      - `hello-world` - 带有样本组件的基本应用，用于演示webforJ功能
      - `blank` - 从头开始的最小项目结构
      - `tabs` - 为多视图应用构建的预制标签化界面布局
      - `sidemenu` - 针对管理面板或仪表板的侧导航菜单布局

      **风格**（框架集成）：
      - `webforj` - 标准webforJ应用
      - `webforj-spring` - 与Spring Boot集成的webforJ，支持依赖注入和企业功能

      :::tip 可用原型
      webforJ配备了多个预定义的原型，帮助您快速入门。有关可用原型的完整列表，请参见[原型目录](../building-ui/archetypes/overview)。
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - 创建可访问的CSS主题
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用[DWC HueCraft](https://huecraft.dwc.style/)生成webforJ主题配置。该工具创建完整的CSS自定义属性集，包括主要、次要、成功、警告、危险和中性颜色变体。

      **示例请求：**
      ```
      "生成一个以HSL 220, 70, 50为主要颜色的webforJ主题，用于我们的企业品牌"

      "创建一个名为'ocean'的webforJ可访问主题，主要颜色为#0066CC"

      "生成一个使用我们品牌颜色#FF5733的webforJ主题"

      "创建一个以HSL 30, 100, 50命名的webforJ主题，用于我们的应用"

      "生成一个主要RGB为44, 123, 229的可访问webforJ主题"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## 可用提示 {#available-prompts}

提示是预配置的AI指令，将多个工具和工作流结合在一起以完成常见任务。它们指导AI通过特定步骤和参数提供可靠、一致的结果。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - 创建并运行webforJ应用
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `appName`（必需） - 应用名称（例如：MyApp、TodoList、Dashboard）
      - `archetype`（必需） - 可选择：`blank`、`hello-world`、`tabs`、`sidemenu`
      - `runServer`（可选） - 自动运行开发服务器（是/否）
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - 根据主要颜色生成webforJ主题
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `primaryColor`（必需） - 颜色格式为十六进制（#FF5733）、rgb（255,87,51）或hsl（9,100,60）
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - 高级搜索与自主解决问题
  </AccordionSummary>
  <AccordionDetails>
    <div>
      提示使AI能够：

      1. 广泛搜索知识库
      2. 编写完整的、可生产的代码
      3. 使用`mvn compile`编译项目，以验证是否存在构建错误
      4. 迭代修复错误，直到一切正常
    </div>
  </AccordionDetails>
</Accordion>

### 如何使用提示

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code和Claude Code">

1. 在聊天中输入 <kbd>/</kbd> 以查看可用提示
2. 从下拉菜单中选择一个提示
3. 在提示时填写所需的参数

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. 点击提示输入区域中的 **+**（加号）图标
2. 从菜单中选择 **"从webforJ添加"**
3. 选择所需的提示（例如：`create-app`、`create-theme`、`search-webforj`）
4. Claude会提示您输入所需的参数
5. 按要求填写参数

:::tip 验证MCP已连接
查看输入区域右下角的工具图标以确认webforJ MCP服务器已连接。
:::

</TabItem>
</Tabs>

## 最佳实践

为了获得最准确和最新的webforJ帮助，请遵循以下指导原则，以充分利用MCP服务器的特性。

### 确保使用MCP服务器

AI模型可能会跳过MCP服务器，如果它们认为自己已经知道答案。要确保实际使用MCP服务器：

- **明确提及webforJ**：在查询中始终提到“webforJ”以触发特定于框架的搜索
- **请求当前信息**：包含诸如“最新webforJ文档”或“当前webforJ模式”等短语
- **请求经过验证的示例**：请求“有效的webforJ代码示例”以强制查找文档
- **引用特定版本**：提及您的webforJ版本（例如：“webforJ `25.02`”）以获取准确的结果

### 编写特定的提示

**好的示例：**
```
"搜索webforJ文档中的按钮组件事件处理示例"

"使用sidemenu原型创建一个名为InventorySystem的webforJ项目，使用Spring Boot"

"生成一个以HSL 220, 70, 50为主要颜色的webforJ主题，用于企业品牌"
```

**不好的示例：**
```
"按钮怎么用"

"做一个应用"

"让它变蓝"
```

### 强制使用MCP工具

如果AI提供的答案过于通用而没有使用MCP服务器：

1. **明确请求**： “使用webforJ MCP服务器搜索`[query]`”
2. **请求文档引用**： “在webforJ文档中查找如何`[query]`”
3. **请求验证**： “根据webforJ文档验证此解决方案”
4. **关注框架特性**：在查询中始终包含“webforJ”

## AI自定义 {#ai-customization}

配置您的AI助手以自动使用MCP服务器并遵循webforJ最佳实践。添加项目特定的指令，使您的AI助手始终使用MCP服务器，遵循webforJ文档标准，并提供准确、最新的答案，以满足您团队的需求。

### 项目配置文件

- 对于 **VS Code和Copilot**，创建`.github/copilot-instructions.md`
- 对于 **Claude Code**，在项目根目录下创建`CLAUDE.md`

在创建的markdown文件中添加以下内容：
```markdown
## 使用webforJ MCP服务器回答任何webforJ问题

- 當問題涉及webforJ時，始终调用“webforj-knowledge-base”工具获取相关文档
- 所有API签名都应根据官方文档进行验证
- 永远不要在未检查的情况下假设方法名称或参数存在

在建议之前，始终验证代码是否可以编译，命令为`mvn compile`。
```

## 常见问题

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么AI不使用webforJ MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数AI助手需要明确的指令才能使用MCP服务器。根据[AI自定义](#ai-customization)部分中的指令配置您的AI客户端。没有这些指令，AI助手可能会默认使用其训练数据，而不是查询MCP服务器。

      **快速修复：**
      在提示中包含“使用webforJ MCP”或创建适当的配置文件（`.github/copilot-instructions.md`或`CLAUDE.md`）。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何验证MCP连接是否正常工作</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用MCP检查器调试连接：

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      等待消息：`🔍 MCP Inspector is up and running at http://127.0.0.1:6274`（端口可能会有所不同）

      然后在检查器中：
      1. 输入MCP服务器URL：`https://mcp.webforj.com/mcp`
      2. 点击“连接”以建立连接
      3. 查看可用工具和测试查询
      4. 监控请求/响应日志以进行调试
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>MCP和SSE终端有什么区别？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      webforJ MCP服务器提供两个终端：

      - **MCP终端** (`/mcp`) - 适用于Claude、VS Code、Cursor的现代协议
      - **SSE终端** (`/sse`) - 针对Legacy客户端（如Windsurf）的服务器推送事件

      大多数用户应使用MCP终端。只有在您的客户端不支持标准MCP协议时才使用SSE。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>是否可以在没有配置文件的情况下使用MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      可以，但不推荐。在没有配置文件的情况下，您必须在每次对话中手动提示AI使用MCP服务器。配置文件会自动指示AI在每次交互中使用MCP服务器，因此您无需每次都重复指令。

      **手动方法：**
      在提示的开头使用：“使用webforJ MCP服务器来……”

      **替代方案：使用预配置的提示**
      MCP服务器提供可以在没有配置文件的情况下工作的提示：
      - `/create-app` - 生成新的webforJ应用
      - `/create-theme` - 创建可访问的CSS主题
      - `/search-webforj` - 高级文档搜索

      有关详细信息，请参见[可用提示](#available-prompts)。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何贡献或报告问题</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **报告问题：** [webforJ MCP问题模板](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **需要报告的常见问题：**
      - 搜索结果中的过时文档
      - 缺少API方法或组件
      - 错误的代码示例
      - 工具执行错误

      在报告问题时，请包括您的查询、预期结果和实际结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
