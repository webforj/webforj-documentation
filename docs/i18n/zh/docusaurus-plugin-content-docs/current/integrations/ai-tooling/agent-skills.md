---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 0458a29cc4337ff83f08afb415097a1c
---
代理技能教导 AI 编码助手如何使用正确的 API、设计令牌和组件模式构建 webforJ 应用程序。助手无需猜测框架约定，而是加载技能并遵循结构化工作流程，以在第一次尝试时生成可以编译的代码并遵循最佳实践。

:::tip 使用插件
以下技能与 **[webforJ AI 插件](/docs/integrations/ai-tooling)** 一起提供，配合 [MCP 服务器](/docs/integrations/ai-tooling/mcp) 使用。一键安装即可使您的助手获得这两个组件。
:::

技能遵循开放的 [代理技能](https://agentskills.io/specification) 标准，并在许多 AI 助手中有效，包括 Claude Code、GitHub Copilot、Cursor、Gemini CLI、OpenAI Codex等。技能告诉助手它处理哪种任务；当您的提示匹配时，助手会自动加载它。例如，询问“用蓝色调为这个应用程序设定主题”会触发 `webforj-styling-apps` 技能，该技能引导助手查找有效的 DWC 令牌、编写作用域 CSS，并在写入磁盘之前验证每个变量名称。

## 为什么使用技能？ {#why-use-skills}

MCP 服务器随时提供准确的 webforJ 信息，但它本身并没有告诉助手 _何时_ 查找某些内容、_哪种_ 方法适合任务，或 _以什么顺序_ 进行操作。这就是技能的用武之地。

技能为助手提供特定任务的操作手册：如何对面前的工作进行分类、适合的 webforJ 模式、在每一步中需要咨询的 MCP 工具，以及如何验证输出再返回。结果是生成一致、遵循惯例的 webforJ 代码，而不是一组技术上有效但在风格上不匹配的代码片段。

## 技能与 MCP 的区别 {#how-skills-differ-from-mcp}

技能与 [webforJ MCP 服务器](/docs/integrations/ai-tooling/mcp) 相辅相成。MCP 服务器提供助手可以调用的实时工具来获取信息或生成输出。技能提供工作流程，告诉助手 _何时_ 需要使用这些工具、以何种顺序进行操作以及如何验证结果。

| | MCP 服务器 | 代理技能 |
|---|---|---|
| **提供的内容** | 助手按需调用的工具（文档搜索、脚手架、主题生成、令牌验证） | 指导助手如何处理任务的工作流和决策表 |
| **何时执行** | 当助手决定调用工具时 | 当助手检测到匹配任务时，自动执行 |
| **最佳用途** | 回答具体问题、生成工件 | 需要一致 webforJ 方法的端到端任务 |

实际上，这两者配合使用效果最佳 - 并且 [webforJ AI 插件](https://github.com/webforj/webforj-ai) 将其作为一个安装包提供。

## 安装 {#installation}

安装 **[webforJ AI 插件](/docs/integrations/ai-tooling)** - 它与 MCP 服务器一起提供下面的两个技能。对于不支持插件的客户端， [webforJ AI 存储库](https://github.com/webforj/webforj-ai#clients) 列出了每个工具读取的技能目录，因此您可以手动复制技能文件夹。

## 可用技能 {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: 添加 REST 端点、Webhook 和自定义 Servlet
  </AccordionSummary>
  <AccordionDetails>
    <div>

当您需要非 UI HTTP 路径时使用：REST 端点、Webhook 处理程序，或第三方 Servlet，例如 Swagger UI 或 Spring Web。助手会为您的项目选择正确的方法（Spring `webforj.exclude-urls`、将 `WebforjServlet` 重新映射到子路径或通过 `webforj.conf` 代理）并在不干扰 webforJ 的 UI 路由的情况下连接端点。

**何时触发**

- *“在 `/api/orders` 添加 REST 端点。”*
- *“为 Stripe 连接一个 Webhook 处理程序。”*
- *“在 `/api/docs` 挂载 Swagger UI。”*
- *“公开一个与 webforJ UI 一起运行的自定义 Servlet。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: 构建带绑定、验证和输入掩码的表单
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于 webforJ 应用程序中的任何表单工作：数据输入表单、与 Java bean 的双向绑定、Jakarta 验证、掩码输入组件（电话、货币、IBAN、日期）、格式化表格列为货币或百分比，和响应式多列布局。助手通过 `BindingContext`、`Masked*Field` 组件、表格掩码渲染器和 `ColumnsLayout` 路由。

**何时触发**

- *“构建一个绑定到我的 `User` bean 的注册表单。”*
- *“添加一个格式随输入变化的电话号码输入。”*
- *“将此表格列格式化为货币。”*
- *“使用 `@NotEmpty` 和自定义电子邮件检查器验证此字段。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: 封装 Web 组件、JS 库或组合
  </AccordionSummary>
  <AccordionDetails>
    <div>

当您需要一个围绕任何源的可重用 Java 组件时使用 - 现有的 Custom Element 库、普通 JavaScript 库或现有 webforJ 组件的组合。助手会选择适合该任务的 webforJ 基类，使用正确的模式连接属性、事件和插槽，并产生遵循 webforJ 约定的测试。

**何时触发**

- *“将这个 Custom Element 库封装为 webforJ 组件。”*
- *“将这些 webforJ 组件组合成一个可重用的卡片。”*
- *“将这个普通 JavaScript 库集成为一个 webforJ 组件。”*
- *“将这个浏览器 API 公开为 webforJ 实用工具。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: 安排定时器、去抖和异步工作
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于定期任务、轮询、输入时的去抖、节流和更新 UI 的长时间后台工作。助手会选择正确的原语（`Interval`，`Debouncer`，`Environment.runLater`，`PendingResult`），并避免因直接使用 `java.util.Timer`、`javax.swing.Timer` 或在 webforJ 环境外部创建的线程而导致的运行时陷阱，后者一触碰 UI 组件就会抛出 `IllegalStateException`。

**何时触发**

- *“每 30 秒刷新一次仪表板。”*
- *“添加一个输入时的去抖。”*
- *“在后台运行这个 CPU 密集型工作并更新进度条。”*
- *“轮询这个 REST 端点直到它返回 `done`。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: 添加 i18n 和翻译支持
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于任何国际化工作：加载消息包、运行时切换语言、自动检测用户的浏览器语言和翻译组件标签。助手通过 webforJ 25.12 的 `BundleTranslationResolver`、`HasTranslation` 关注点、`LocaleObserver` 和可插拔的自定义解析程序进行路由，覆盖 Spring 和普通 webforJ 路径。

**何时触发**

- *“添加支持英语和西班牙语的多语言功能。”*
- *“检测用户的浏览器语言并在启动时应用。”*
- *“在导航栏中添加语言切换器。”*
- *“将所有硬编码字符串移入消息包。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: 用登录和基于角色的访问保护路由
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于保护 webforJ 应用程序中路由的任何内容：登录和登出、基于角色的访问、公共着陆页、仅限管理员的部分、所有权规则和默认安全策略。助手在 Spring Boot 在类路径上时更倾向于使用 Spring Security，否则退回到 webforJ 的普通安全框架。它应用正确的注解（`@AnonymousAccess`，`@PermitAll`，`@RolesAllowed`，`@RouteAccess`，`@RegisteredEvaluator`），并解释哪些是终端注解，哪些是可组合的，以便默认安全策略仍然有效。

**何时触发**

- *“保护 `/admin` 使只有具有 `ADMIN` 角色的用户才能查看。”*
- *“添加一个任何人都可以访问的公共着陆页。”*
- *“在页眉中显示登录用户的名称。”*
- *“只允许用户编辑他们拥有的记录。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: 使用 DWC 设计令牌为应用程序设置主题
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于 webforJ 应用程序中的任何视觉工作：调色板重新配色、组件级样式、布局和间距、排版、完整主题、表格外观或协调的 Google Charts 颜色。助手编写符合 DWC 设计令牌的 CSS，正确地作用域选择器，并检查每个 `--dwc-*` 引用是否与您的 webforJ 版本的实际目录匹配，从而确保暗模式和主题切换功能正常。

**何时触发**

- *“用蓝色调为这个应用程序设定主题。”*
- *“将 dwc-button 样式调整为符合品牌规范。”*
- *“使这个布局更紧凑 - 调整间距和排版。”*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: 使用 OpenRewrite 在 webforJ 主要版本之间进行升级
  </AccordionSummary>
  <AccordionDetails>
    <div>

用于主要版本升级。助手执行针对目标版本的官方 `webforj-rewrite` OpenRewrite 配方，升级 `<webforj.version>` 和 Java 版本，重写重命名的 API 和类型，并在每个需要手动决策的移除方法上插入 `TODO webforJ <major>:` 注释。对于没有发布配方的旧版本目标（例如 24 到 25），它会引导您完成手动回退。

**何时触发**

- *“将这个应用程序从 webforJ 25 升级到 26。”*
- *“运行重写配方并解决 TODOs。”*
- *“由于没有配方，手动从 webforJ 24 迁移到 25。”*
- *“升级后我需要修复哪些移除的 API？”*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
