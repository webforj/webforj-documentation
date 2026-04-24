---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 98d3f61447c289339f92fc4872e734f1
---
代理技能教AI编码助手如何使用正确的API、设计令牌和组件模式构建webforJ应用。助手不再猜测框架约定，而是加载技能并遵循结构化工作流程，从而在首次尝试时生成可编译的代码并遵循最佳实践。

:::tip 使用插件
以下技能与**[webforJ AI插件](/docs/integrations/ai-tooling)**和[MCP服务器](/docs/integrations/ai-tooling/mcp)一起提供。一次安装即可让您的助手获得这两个组件。
:::

技能遵循开放的[代理技能](https://agentskills.io/specification)标准，适用于多种AI助手，包括Claude Code、GitHub Copilot、Cursor、Gemini CLI、OpenAI Codex等。技能告诉助手其处理的任务类型；当您的提示匹配时，助手会自动加载它。例如，询问“用蓝色调为这个应用主题”会触发`webforj-styling-apps`技能，该技能引导助手查找有效的DWC令牌、编写作用域CSS并在写入任何内容之前验证每个变量名称。

## 为什么使用技能？ {#why-use-skills}

MCP服务器按需提供准确的webforJ信息，但单独使用时不会告知助手_何时_查找某些内容、_哪种_方法适合任务或_按什么顺序_执行操作。这就是技能的用武之地。

技能为助手提供任务特定的剧本：如何对其面前的工作进行分类，哪些webforJ模式适用，哪些MCP工具在每个步骤中查阅，以及如何在返还结果之前验证输出。结果是一致的、遵循约定的webforJ代码，而不是一系列技术上有效但风格不匹配的代码片段。

## 技能与MCP的不同之处 {#how-skills-differ-from-mcp}

技能与[webforJ MCP服务器](/docs/integrations/ai-tooling/mcp)在互补角色上提供服务。MCP服务器提供助手可以调用的实时工具，以获取信息或生成输出。技能提供了告诉助手_何时_使用这些工具、按什么顺序执行以及如何验证结果的工作流程。

| | MCP服务器 | 代理技能 |
|---|---|---|
| **提供的内容** | 助手根据需要调用的工具（文档搜索、脚手架、主题生成、令牌验证） | 指导助手如何处理任务的工作流程和决策表 |
| **何时行动** | 当助手决定调用工具时 | 当助手检测到匹配任务时自动 |
| **最佳适用场景** | 回答具体问题，生成工件 | 需要一致的webforJ方法的端到端任务 |

在实践中，这两者的结合效果最好 - 而且[webforJ AI插件](https://github.com/webforj/webforj-ai)作为一个安装包提供它们。

## 安装 {#installation}

安装**[webforJ AI插件](/docs/integrations/ai-tooling)** - 它与MCP服务器一起提供以下两个技能。对于不支持插件的客户端，[webforJ AI存储库](https://github.com/webforj/webforj-ai#clients)列出了每个工具读取的技能目录，因此您可以手动复制技能文件夹。

## 可用技能 {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: 从网络组件库、JavaScript库或现有的webforJ组件构建可重用的webforJ组件
  </AccordionSummary>
  <AccordionDetails>
    <div>

当您需要任何源的可重用Java组件时使用此技能 - 一个现有的自定义元素库、一个普通的JavaScript库或现有webforJ组件的组合。助手为此任务选择合适的webforJ基类，使用正确的模式配置属性、事件和插槽，并生成遵循webforJ约定的测试。

**何时触发**

- *“将此自定义元素库包装为webforJ组件。”*
- *“将这些webforJ组件组合成一个可重用的卡片。”*
- *“将此普通JavaScript库集成为webforJ组件。”*
- *“将此浏览器API暴露为webforJ工具。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: 使用DWC设计令牌系统为webforJ应用编写主题和样式
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于任何webforJ应用的视觉工作：调色板重新设计、组件级样式、布局和间距、排版、完整主题、表格外观或协调的Google Charts颜色。助手编写遵循DWC设计令牌的CSS，正确作用域选择器，并针对您的webforJ版本验证每个`--dwc-*`引用的真实目录 - 以保持暗模式和主题切换的正常使用。

**何时触发**

- *“用蓝色调为这个应用主题。”*
- *“使dwc-button的样式符合品牌指南。”*
- *“使此布局更紧凑 - 调整间距和排版。”*

</div>
  </AccordionDetails>
</Accordion>
