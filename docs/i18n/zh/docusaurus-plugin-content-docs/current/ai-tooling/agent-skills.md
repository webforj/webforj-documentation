---
title: Agent Skills
sidebar_position: 10
description: >-
  Install Agent Skills so AI coding assistants follow webforJ workflows for
  building forms, adding servlets, styling apps, and creating components.
_i18n_hash: 6dc21bfd21fb27f2e71cb2265f6cde8c
---
代理技能教AI编码助手如何使用正确的API、设计令牌和组件模式来构建webforJ应用程序。助手不再依赖于猜测框架约定，而是加载一个技能并遵循结构化的工作流程，以便在第一次尝试时生成可编译的代码，并遵循最佳实践。

:::tip 使用插件
以下技能随**[webforJ AI插件](/docs/ai-tooling)**一起提供，并与[MCP服务器](/docs/ai-tooling/mcp)一起打包。一次安装为您的助手提供了这两个部分。
:::

技能遵循开放的[代理技能](https://agentskills.io/specification)标准，并在多个AI助手之间工作，包括Claude Code、GitHub Copilot、Cursor、Gemini CLI、OpenAI Codex等。技能告诉助手它处理哪种任务；当您的提示匹配时，助手会自动加载它。例如，询问“使用蓝色调为此应用程序配色”会触发`webforj-styling-apps`技能，该技能引导助手查找有效的DWC令牌、编写范围CSS，并在将任何内容写入磁盘之前验证每个变量名称。

## 为什么使用技能？ {#why-use-skills}

MCP服务器按需提供准确的webforJ信息，但单独使用时，它不会告诉助手_何时_查找某个信息，_哪种_方法适合该任务，或者_以什么顺序_进行操作。这时技能便派上了用场。

技能为助手提供了特定任务的剧本：如何对眼前的工作进行分类，哪些webforJ模式适合，在哪一步咨询哪些MCP工具，以及如何在将结果交回之前验证输出。结果是生成一致且遵循约定的webforJ代码，而不是一堆技术上有效但风格上不匹配的代码片段。

## 技能与MCP的区别 {#how-skills-differ-from-mcp}

技能和[webforJ MCP服务器](/docs/ai-tooling/mcp)各自扮演互补的角色。MCP服务器提供助手可以调用的实时工具，以获取信息或生成输出。技能提供了工作流程，告诉助手_何时_调用这些工具，按什么顺序操作，以及如何验证结果。

| | MCP服务器 | 代理技能 |
|---|---|---|
| **提供的内容** | 助手按需调用的工具（文档搜索、脚手架、主题生成、令牌验证） | 指导助手如何处理任务的工作流程和决策表 |
| **何时行动** | 当助手决定调用某个工具时 | 自动进行，当助手检测到匹配的任务时 |
| **最好用于** | 回答特定问题，生成工件 | 需要一致webforJ方法的端到端任务 |

实际上，这两个工具结合使用效果最佳——并且[webforJ AI插件](https://github.com/webforj/webforj-ai)将它们作为一个安装包打包。

## 安装 {#installation}

安装**[webforJ AI插件](/docs/ai-tooling)** - 它将以下两个技能与MCP服务器一起提供。对于不支持插件的客户端，[webforJ AI仓库](https://github.com/webforj/webforj-ai#clients)列出了每个工具读取的技能目录，您可以手动复制技能文件夹。

## 可用技能 {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: 添加REST端点、Webhook和自定义Servlet
  </AccordionSummary>
  <AccordionDetails>
    <div>

在您需要非UI的HTTP路径时使用此功能 - REST端点、Webhook处理程序或第三方Servlet（例如Swagger UI或Spring Web）。助手为您的项目选择合适的方法（Spring `webforj.exclude-urls`、将`WebforjServlet`重映射到子路径，或通过`webforj.conf`代理）并连接端点而不干扰webforJ的UI路由。

**何时触发**

- *“在`/api/orders`处添加REST端点。”*
- *“为Stripe连接一个Webhook处理器。”*
- *“在`/api/docs`处挂载Swagger UI。”*
- *“公开一个与webforJ UI并行运行的自定义Servlet。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: 创建具有绑定、验证和输入掩码的表单
  </AccordionSummary>
  <AccordionDetails>
    <div>

在webforJ应用程序中进行表单工作的任何情况中使用此功能：数据输入表单、与Java bean的双向绑定、Jakarta验证、掩码输入组件（电话、货币、IBAN、日期）、将表格列格式化为货币或百分比，以及响应式多列布局。助手通过`BindingContext`、`Masked*Field`组件、表格掩码渲染器和`ColumnsLayout`进行路由。

**何时触发**

- *“创建一个与我的`User` bean绑定的注册表单。”*
- *“添加一个格式实时输入的电话号码输入框。”*
- *“将此表格列格式化为货币。”*
- *“使用`@NotEmpty`和自定义电子邮件检查器验证此字段。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: 封装Web组件、JS库或组合
  </AccordionSummary>
  <AccordionDetails>
    <div>

在您需要一个可重用的Java组件，封装任何源时使用此功能 - 一个现有的自定义元素库、一个纯JavaScript库，或一组现有的webforJ组件的组合。助手为这个任务选择合适的webforJ基础类，正确的模式连接属性、事件和插槽，并生成遵循webforJ约定的测试。

**何时触发**

- *“将此自定义元素库封装为webforJ组件。”*
- *“将这些webforJ组件组合成一个可重用的卡片。”*
- *“将这个纯JavaScript库集成为一个webforJ组件。”*
- *“将这个浏览器API公开为webforJ工具。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: 安排定时器、去抖和异步工作
  </AccordionSummary>
  <AccordionDetails>
    <div>

在周期性任务、轮询、去抖的实时搜索、节流和长时间运行的后台工作（在运行时更新UI）中使用此功能。助手选择正确的基本构件（`Interval`、`Debouncer`、`Environment.runLater`、`PendingResult`），并避免来自原始`java.util.Timer`、`javax.swing.Timer`或在webforJ环境外创建的线程的运行时陷阱，所有这些在接触UI组件时都会抛出`IllegalStateException`。

**何时触发**

- *“每30秒刷新一次仪表板。”*
- *“添加一个搜索实时输入去抖器。”*
- *“在后台运行这个CPU密集型的工作并更新进度条。”*
- *“轮询这个REST端点，直到返回`done`。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: 添加国际化和翻译支持
  </AccordionSummary>
  <AccordionDetails>
    <div>

在任何国际化工作中使用此功能：加载消息包、在运行时切换语言、自动检测用户的浏览器语言环境，以及翻译组件标签。助手通过webforJ 25.12的`BundleTranslationResolver`、`HasTranslation`关注、`LocaleObserver`和可插拔的自定义解析器进行路由，覆盖Spring和纯webforJ路径。

**何时触发**

- *“添加支持英语和西班牙语的多语言。”*
- *“检测用户的浏览器语言环境并在启动时应用。”*
- *“在导航栏中添加语言切换器。”*
- *“将所有硬编码字符串移动到消息包中。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: 通过登录和基于角色的访问保护路由
  </AccordionSummary>
  <AccordionDetails>
    <div>

在任何保护webforJ应用程序路由的情况下使用此功能：登录和登出、基于角色的访问、公共着陆页面、仅限管理员的部分、所有权规则和默认安全策略。助手在classPath中包含Spring Boot时优先选择Spring Security，反之则回退到webforJ的普通安全框架。它应用正确的注释（`@AnonymousAccess`、`@PermitAll`、`@RolesAllowed`、`@RouteAccess`、`@RegisteredEvaluator`），并解释哪些是终止的，哪些是可组合的，以确保默认安全性照常工作。

**何时触发**

- *“保护`/admin`，使只有具有`ADMIN`角色的用户才能查看。”*
- *“添加一个任何人都可以访问的公共着陆页面。”*
- *“在标题中显示已登录用户的名称。”*
- *“仅允许用户编辑他们拥有的记录。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: 使用DWC设计令牌为应用程序设置主题
  </AccordionSummary>
  <AccordionDetails>
    <div>

在任何涉及webforJ应用程序的视觉工作中使用此功能：调色板重新色彩、组件级样式、布局和间距、排版、完整主题、表格外观或协调的Google Charts颜色。助手编写遵循DWC设计令牌的CSS，正确作用域选择器，并将每个`--dwc-*`引用与您的webforJ版本的真实目录进行验证 - 以便暗模式和主题切换保持正常工作。

**何时触发**

- *“用蓝色调为这个应用程序设置主题。”*
- *“将dwc-button样式调整为符合品牌指南。”*
- *“让这个布局更紧凑 - 调整间距和排版。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: 使用OpenRewrite跨webforJ主版本升级
  </AccordionSummary>
  <AccordionDetails>
    <div>

在主要版本升级中使用此功能。助手运行官方的`webforj-rewrite` OpenRewrite配方以升级到目标版本，这会提高`<webforj.version>`和Java发布版本，重写重命名的API和类型，并在每个需要手动决策的移除方法处插入`TODO webforJ <major>:`注释。对于没有发布配方的旧版本目标（例如从24到25），它将引导您进行手动回退。

**何时触发**

- *“将此应用程序从webforJ 25升级到26。”*
- *“运行重写配方并解决TODO。”*
- *“由于没有配方，手动从webforJ 24迁移到25。”*
- *“升级后我需要修复哪些被移除的API？”*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
