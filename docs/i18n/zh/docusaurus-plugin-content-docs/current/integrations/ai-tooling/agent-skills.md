---
title: Agent Skills
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: cf22942f0e73a936bef31cf8a3a9a043
---
代理技能教会AI编码助手如何使用正确的API、设计令牌和组件模式构建webforJ应用程序。AI助手加载技能并遵循其结构化工作流，以生成编译且遵循最佳实践的代码，而无需猜测框架约定。

技能遵循开放的 [Agent Skills](https://agentskills.io/specification) 规范，并在多个AI助手中工作，包括Claude Code、VS Code中的GitHub Copilot和Cursor。每个技能都是一个单独的目录，其中包含一个描述技能目的和工作流的 `SKILL.md` 文件，以及用于支持文档和辅助脚本的 `references/` 和 `scripts/` 目录。

webforJ的代理技能可在GitHub存储库[webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills)中获取。安装这些技能后，当AI检测到相关任务时，将自动加载这些文件。例如，要求AI“使用蓝色调为此应用程序进行主题设置”会触发 `styling-apps` 技能，该技能引导AI查找有效的DWC令牌、编写作用域CSS并验证每个变量名称，然后生成输出。

## 为什么要使用技能？ {#why-use-skills}

没有技能时，AI助手通常会生成看似合理但在实际中失败的webforJ代码。常见问题包括：

- 发明不存在的 `--dwc-*` 令牌名称（CSS编译但无效）
- 为组件包装器使用错误的基本类（`Composite` 而不是 `ElementComposite`，或反之）
- 缺少 `PropertyDescriptor` 模式、事件注解或关注接口
- 硬编码颜色导致暗黑模式失败
- 跳过捕捉静默故障的验证步骤

技能通过为每种任务类型提供准确的决策表、查找脚本和验证检查表，消除了这些问题。

## 技能与MCP的区别 {#how-skills-differ-from-mcp}

技能和 [webforJ MCP服务器](./mcp) 执行互补的角色。MCP提供AI在运行时可以调用的实时工具，以搜索文档或生成项目。技能提供静态知识和逐步工作流程，引导AI如何处理任务。

| | MCP服务器 | 代理技能 |
|---|---|---|
| **提供的内容** | 实时工具：文档搜索、项目搭建、主题生成 | 静态知识：工作流程、决策表、参考文档、辅助脚本 |
| **何时执行** | 按需，当AI调用工具时 | 自动，当AI检测到符合条件的任务时 |
| **最佳用途** | 查找特定API、生成起始项目、创建主题调色板 | 需要遵循框架约定和多步工作流程的端到端任务 |

在实践中，两者结合得很好。MCP服务器的 `webforj-create-theme` 工具从单一颜色生成有效的调色板，然后 `styling-apps` 技能引导AI通过组件级样式和暗黑模式验证使用该调色板。

技能是从磁盘读取的静态文件——它们不会增加运行时开销或进行外部API调用。AI在相关时将技能的参考材料加载到其上下文窗口中，这使用了一些上下文令牌，但针对框架特定工作的输出质量显著更高。

## 安装 {#installation}

克隆 [webforJ代理技能存储库](https://github.com/webforj/webforJ-agent-skills)，然后将技能文件夹复制到您的AI工具所期望的位置。每个工具支持两个范围：

- **项目范围**：技能仅在该项目中可用
- **用户范围**：技能在您所有项目中均可用

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforj-agent-skills

# 项目范围
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# 用户范围
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforj-agent-skills

# 项目范围
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# 用户范围
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforj-agent-skills

# 项目范围
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# 用户范围
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[使用哪个范围]
在与团队协作时使用 **项目范围**，以便项目中的每个人都可以受益于相同的技能。在您进行多个webforJ项目时使用 **用户范围**，希望技能在每个项目中都可用，而无需将它们复制到每个存储库中。
:::

## 可用技能 {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: 从网页组件库、JavaScript库或现有的webforJ组件构建可重用的webforJ组件
  </AccordionSummary>
  <AccordionDetails>
    <div>

[此技能](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) 引导AI助手从任何源构建可重用的Java组件，无论是现有的网页组件库、普通JavaScript库，还是现有的webforJ组件的组合。

**内容覆盖**

该技能定义了五条创建组件的路径，并教会AI根据任务选择正确的路径：

| 路径 | 何时使用 | 基本类 |
|---|---|---|
| 包装现有的Custom Element库 | 库中包含Custom Elements（`<x-button>`，`<x-dialog>`） | `ElementComposite` / `ElementCompositeContainer` |
| 构建一个Custom Element，然后包装它 | 新的视觉组件或包装普通JS库 | `ElementComposite` / `ElementCompositeContainer` |
| 组合webforJ组件 | 将现有的webforJ组件组合成可重用单元 | `Composite<T>` |
| 扩展HTML元素 | 与没有Shadow DOM的一次性轻量集成 | `Div`、`Span`等 |
| 页面级实用程序 | 无DOM小部件的浏览器API或全局功能 | 普通Java类 + `EventDispatcher` |

**工作流程**

对于Custom Element包装（最常见的路径），该技能引导AI遵循结构化工作流程：

1. **设置**：将第三方JS/CSS下载到项目的 `src/main/resources/static/libs/` 目录中。该技能指示AI优先使用本地资源而不是CDN链接以确保离线可靠性。
2. **提取组件数据**：使用包含的 `extract_components.mjs` 脚本解析Custom Elements Manifest并生成每个组件属性、事件、插槽和CSS自定义属性的结构化规范。
3. **编写Java包装器**：创建具有 `PropertyDescriptor` 字段、事件类、插槽方法和关注接口的 `ElementComposite` 或 `ElementCompositeContainer` 类，全部遵循webforJ约定。
4. **编写测试**：使用 `PropertyDescriptorTester` 和结构化测试模式为属性、插槽和事件生成JUnit 5测试。

**参考材料**

该技能包括八个参考文档，涵盖 `ElementComposite` 模式、组件组合、属性描述符、事件处理、JavaScript互操作、测试模式和常见反模式。

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: 使用DWC设计令牌系统为webforJ应用程序设置主题和样式
  </AccordionSummary>
  <AccordionDetails>
    <div>

[此技能](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) 教会AI助手如何使用DWC设计令牌系统为webforJ应用程序设置样式。核心原则是所有视觉值使用 `--dwc-*` CSS自定义属性。该技能通过提供验证步骤和查找脚本来强制这点，防止AI发明令牌名称或硬编码颜色。

**内容覆盖**

| 任务 | 技能教会的方法 |
|------|--------------------|
| 颜色重新方案 | 覆盖 `:root` 中的调色板色调、饱和度和对比度令牌 |
| 组件样式 | 首先查找组件的CSS变量，仅在必要时回退到 `::part()` |
| 布局和间距 | 使用 `--dwc-space-*` 和 `--dwc-size-*` 令牌 |
| 排版 | 使用 `--dwc-font-*` 令牌 |
| 完整主题 | 使用语义令牌重新映射进行调色板配置 |
| 表格样式 | 仅使用 `::part()` 选择器（表格不公开任何CSS变量） |
| Google Charts | 通过 `Assets.contentOf()` 和Gson加载的JSON主题文件 |

**工作流程**

该技能执行严格的查找前写纪律：

1. **分类任务**：确定这是调色板重新方案、组件样式、布局工作还是完整主题。
2. **扫描应用**：读取Java源代码以查找使用的每个组件、主题变体和扩展。
3. **查找每个组件**：运行包含的 `component_styles.py` 脚本以检索每个组件支持的确切CSS变量、`::part()`名称和反射属性。AI在完成此步骤之前不会编写任何CSS。
4. **编写CSS**：生成遵循DWC约定的嵌套、精简CSS：首先是全局令牌，然后是组件CSS变量，然后是 `::part()` 覆盖项作为最后手段。
5. **验证**：重新运行查找脚本并验证输出中每个令牌、部分名称和选择器实际上存在。修复任何失败的内容。

**技能强制执行的关键规则**

- **仅七种调色板**：`primary`、`success`、`warning`、`danger`、`info`、`default`和`gray`。如 `secondary` 或 `accent` 的名称在DWC中不存在，会默默失败。
- **无硬编码颜色**：每个颜色值必须是 `var()` 引用，包括在 `box-shadow` 和 `border` 之内。硬编码值会导致暗黑模式失败。
- **CSS变量优先于 `::part()`**：组件CSS变量是预期的样式API。 `::part()` 是没有变量的情况的逃生阀。
- **作用域选择器**：在具有 `theme` 或 `expanse` 属性的组件上，裸标签选择器会覆盖所有变体。该技能要求 `:not([theme])` 或 `[theme~="value"]` 进行范围限定。

</div>
  </AccordionDetails>
</Accordion>
