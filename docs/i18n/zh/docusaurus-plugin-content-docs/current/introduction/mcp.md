---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: caf6cb2973387f33706be4c4416a594c
---
webforJ模型上下文协议（MCP）服务器为AI助手提供了直接访问官方webforJ文档、经过验证的代码示例和特定于框架的模式的能力，从而允许更准确的回答，并专门为webforJ开发自动生成项目。

## 什么是MCP？

模型上下文协议是一个开放标准，使AI助手能够与外部工具和文档连接。webforJ MCP服务器实现了该协议，以提供：

- **知识搜索** - 在webforJ文档、代码示例和模式中进行自然语言搜索
- **项目生成** - 从官方模板创建具有适当结构的webforJ应用程序
- **主题创建** - 生成遵循webforJ设计模式的可访问CSS主题

## 为什么使用MCP？

虽然AI编码助手在回答基本问题方面表现出色，但它们在处理跨多个文档部分的复杂webforJ特定查询时却很吃力。如果没有直接访问官方来源，它们可能会：

- 生成webforJ中不存在的方法
- 引用过时或不正确的API模式  
- 提供无法编译的代码
- 将webforJ语法与其他Java框架混淆
- 误解特定于webforJ的模式

通过MCP集成，AI响应与实际的webforJ文档、代码示例和框架模式相联系，提供可验证的答案，并直接链接到官方来源以进行深入探索。

:::warning AI仍然可能会犯错误
虽然MCP通过提供访问官方webforJ资源来显著提高准确性，但并不能保证完美的代码生成。AI助手在复杂场景中仍可能犯错。在用于生产之前，请始终验证生成的代码并经过彻底测试。
:::

## 安装

webforJ MCP服务器托管在`https://mcp.webforj.com`，具有两个端点：

- **MCP端点** (`/mcp`) - 用于Claude、VS Code、Cursor
- **SSE端点** (`/sse`) - 用于遗留客户端

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

将此配置添加到您的VS Code settings.json文件中：

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

使用Claude CLI命令注册服务器：

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

这将自动在您的Claude Code环境中配置MCP服务器。

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

通过Claude Desktop设置中的集成面板添加此服务器：

1. 打开Claude Desktop并进入设置
2. 点击侧边栏中的“集成”
3. 点击“添加集成”，并粘贴URL：`https://mcp.webforj.com/mcp`
4. 按照设置向导完成配置

有关详细说明，请参阅[官方集成指南](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp)。

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

工具是MCP服务器提供给AI助手的专业功能。当您提出问题或请求时，AI可以调用这些工具来搜索文档、生成项目或创建主题。每个工具接受特定的参数，并返回结构化数据，帮助AI提供准确的上下文感知支持。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - 搜索文档和示例
  </AccordionSummary>
  <AccordionDetails>
    <div>
      该工具提供跨整个webforJ文档生态系统的语义搜索功能。它理解不同框架概念之间的上下文和关系，返回相关的文档部分、API参考和可运行的代码示例。

      **示例查询：**
      ```
      "搜索webforJ文档以获取带图标的按钮组件示例"

      "在最新文档中查找webforJ表单验证模式"

      "向我展示当前webforJ路由设置，使用@Route注解"

      "在webforJ文档中搜索FlexLayout响应式设计模式"

      "在官方文档中查找webforJ Web组件集成"
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
      使用官方Maven原型构建完整的webforJ应用程序。该工具创建标准化的项目目录布局，并基于所选模板包含启动代码。生成的项目包括可立即开发和部署的可用构建系统、资源文件夹和配置文件。

      **示例提示：**
      ```
      "创建一个名为CustomerPortal的webforJ项目，使用hello-world原型"

      "生成一个名为Dashboard的webforJ Spring Boot项目，采用标签布局"

      "为AdminPanel项目创建一个新的webforJ应用，使用侧边菜单原型"

      "生成一个名为TestApp的webforJ空项目，使用com.example的groupId"

      "使用侧边菜单原型生成一个名为InventorySystem的webforJ项目，采用Spring Boot"
      ```

      使用此工具时，您可以从多个项目模板中选择：

      **原型**（项目模板）：
      - `hello-world` - 带有示例组件的基础应用，展示webforJ的功能
      - `blank` - 从头开始的最小项目结构
      - `tabs` - 预构建的选项卡界面布局，适用于多视图应用
      - `sidemenu` - 管理面板或仪表板的侧边导航菜单布局

      **风味**（框架集成）：
      - `webforj` - 标准webforJ应用
      - `webforj-spring` - webforJ与Spring Boot集成，支持依赖注入和企业功能

      :::tip 可用原型
      webforJ附带多个预定义原型，帮助您快速入门。有关可用原型的完整列表，请参阅[原型目录](../building-ui/archetypes/overview)。
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
      使用[DWC HueCraft](https://huecraft.dwc.style/)生成webforJ主题配置。该工具创建完整的CSS自定义属性集，包括主要、次要、成功、警告、危险和中立色变体。

      **示例请求：**
      ```
      "为我们的企业品牌生成主色为HSL 220, 70, 50的webforJ主题"

      "创建名为'ocean'的webforJ可访问主题，主色为#0066CC"

      "生成一个webforJ主题，使用我们的品牌色#FF5733"

      "为我们的应用创建主色为HSL 30, 100, 50的webforJ主题，名为'sunset'"

      "生成主RGB为44, 123, 229的可访问webforJ主题"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## 可用提示 {#available-prompts}

提示是预配置的AI指令，结合多个工具和工作流程以完成常见任务。它们引导AI通过特定步骤和参数，提供可靠、可重复的结果，以支持每个支持的工作流程。

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - 创建并运行一个webforJ应用
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `appName`（必需） - 应用名称（例如MyApp、TodoList、Dashboard）
      - `archetype`（必需） - 从以下选项中选择：`blank`、`hello-world`、`tabs`、`sidemenu`
      - `runServer`（可选） - 自动运行开发服务器（是/否）
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - 从主色生成webforJ主题
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **参数：**
      - `primaryColor`（必需） - 颜色格式为hex（#FF5733）、rgb（255,87,51）或hsl（9,100,60）
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - 具备自主解决问题的高级搜索
  </AccordionSummary>
  <AccordionDetails>
    <div>
      该提示将AI配置为：

      1. 广泛搜索知识库
      2. 编写完整的生产就绪代码
      3. 使用`mvn compile`编译项目，以验证没有构建错误
      4. 迭代修复错误，直到一切正常工作
    </div>
  </AccordionDetails>
</Accordion>

### 如何使用提示

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code和Claude Code">

1. 在聊天中输入 <kbd>/</kbd> 以查看可用提示
2. 从下拉菜单中选择一个提示
3. 在被提示时填写所需参数

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. 点击提示输入区域中的**+**（加号）图标
2. 从菜单中选择**“从webforJ添加”**
3. 选择所需的提示（例如`create-app`、`create-theme`、`search-webforj`）
4. Claude将提示您输入所需的参数
5. 按要求填写参数

:::tip 验证MCP是否已连接
查看输入区域右下角的工具图标，以确认webforJ MCP服务器已连接。
:::

</TabItem>
</Tabs>

## 最佳实践

为了获得最准确、最新的webforJ支持，请遵循以下准则，以充分利用MCP服务器功能。

### 确保MCP服务器的使用

AI模型可能会跳过MCP服务器，如果他们认为自己已经知道答案。为了确保实际使用MCP服务器：

- **明确提及webforJ**：在查询中始终提到“webforJ”，以触发特定于框架的搜索
- **请求最新信息**：包括短语“最新webforJ文档”或“当前webforJ模式”
- **请求经过验证的示例**：请求“可用的webforJ代码示例”，以强制查阅文档
- **引用特定版本**：提到您的webforJ版本（例如“webforJ `25.02`”）以获取准确结果

### 编写具体提示

**好的示例：**
```
"搜索webforJ文档以获取按钮组件事件处理的示例"

"创建一个名为InventorySystem的webforJ项目，使用侧边菜单原型，与Spring Boot集成"

"生成一个主色为HSL 220, 70, 50的webforJ主题，用于企业品牌"
```

**差的示例：**
```
"按钮是如何工作的"

"制作一个应用"

"把它做成蓝色"
```

### 强制使用MCP工具

如果AI在没有使用MCP服务器的情况下提供普通答案：

1. **明确要求**：“使用webforJ MCP服务器搜索`[query]`”
2. **请求文档参考**：“在webforJ文档中查找如何`[query]`”
3. **请求验证**：“对照webforJ文档验证此解决方案”
4. **要求特定于框架**：始终在查询中包含“webforJ”

## AI定制 {#ai-customization}

配置您的AI助手，自动使用MCP服务器并遵循webforJ最佳实践。添加项目特定的指令，以便您的AI助手在任何时候都能使用MCP服务器，遵循webforJ文档标准，并提供符合您团队要求的准确、最新的答案。

### 项目配置文件

- 对于**VS Code和Copilot**，创建`.github/copilot-instructions.md`
- 对于**Claude Code**，在项目根目录创建`CLAUDE.md`

在创建的markdown文件中添加以下内容：
```markdown
## 使用webforJ MCP服务器回答任何webforJ问题

- 始终调用“webforj-knowledge-base”工具以获取与问题相关的文档
- 对照官方文档验证所有API签名
- 在未检查之前，绝不要假定方法名称或参数存在

在建议之前始终验证代码是否可以编译，使用`mvn compile`。
```

## 常见问题

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>为什么AI不使用webforJ MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      大多数AI助手需要明确的指令来使用MCP服务器。使用[AI定制](#ai-customization)部分中的指令配置您的AI客户端。如果没有这些指令，AI助手可能会默认使用其训练数据，而不是查询MCP服务器。

      **快速修复：**
      在您的提示中包括“使用webforJ MCP”或创建适当的配置文件（`.github/copilot-instructions.md`或`CLAUDE.md`）。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何验证MCP连接是否正常工作</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      使用MCP检查工具调试连接：

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      等待消息：`🔍 MCP Inspector正在运行，地址为http://127.0.0.1:6274`（端口可能会有所不同）

      然后在检查工具中：
      1. 输入MCP服务器URL：`https://mcp.webforj.com/mcp`
      2. 点击“连接”以建立连接
      3. 查看可用工具并测试查询
      4. 监控请求/响应日志进行调试
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>MCP和SSE端点之间有什么区别？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      webforJ MCP服务器提供两个端点：

      - **MCP端点** (`/mcp`) - 针对Claude、VS Code、Cursor的现代协议
      - **SSE端点** (`/sse`) - 针对Windsurf等遗留客户端的服务器发送事件

      大多数用户应该使用MCP端点。只有在您的客户端不支持标准的MCP协议时，才使用SSE。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>是否可以在没有配置文件的情况下使用MCP服务器？</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      可以，但不推荐。没有配置文件，您必须在每次对话中手动提示AI使用MCP服务器。配置文件会自动指示AI在每次交互中使用MCP服务器，因此您不必每次重复指令。

      **手动方法：**
      提示以：“使用webforJ MCP服务器来…”

      **替代方案：使用预配置的提示**
      MCP服务器提供不需要配置文件的提示：
      - `/create-app` - 生成新的webforJ应用程序
      - `/create-theme` - 创建可访问的CSS主题
      - `/search-webforj` - 高级文档搜索

      有关详细信息，请参阅[可用提示](#available-prompts)。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>如何贡献或报告问题</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **报告问题：** [webforJ MCP反馈](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **需要报告的常见问题：**
      - 搜索结果中过时的文档
      - 缺少的API方法或组件
      - 不正确的代码示例
      - 工具执行错误

      报告问题时，请包括您的查询、预期结果和实际结果。
    </div>
  </AccordionDetails>
</Accordion>
<br />
