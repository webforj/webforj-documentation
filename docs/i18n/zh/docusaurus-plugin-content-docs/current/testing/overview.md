---
sidebar_position: 1
title: 测试
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 58ac266506a221d71e22c723189aa08f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ 测试

webforJ 应用中的测试包括单元测试、前端测试和端到端（E2E）测试，每种测试都有其独特作用，有助于保持应用的稳定性和可靠性。

## 单元测试 {#unit-testing}

单元测试侧重于独立验证各个组件或后端逻辑。遵循标准的 Java 测试实践（例如使用 [JUnit](https://junit.org/junit5/)），开发者可以验证特定的应用逻辑，并检查每个“单元”是否按预期运行。

对于视图和组件，[TestBed](./testbed/overview) 可以在普通的 JUnit 测试中进行无浏览器测试，无需浏览器或服务器。测试会将视图添加到页面，像用户一样与其交互，然后从 Java 中检查结果。

## 前端测试 {#frontend-testing}

前端测试涵盖项目通过[前端打包器](/docs/managing-resources/bundler/overview)编写的源代码。[Bun](https://bun.sh/) 测试运行器会在运行 Java 测试的同一构建过程中运行这些测试，因此 TypeScript 组件或客户端逻辑片段可以像后端代码一样得到验证。请参阅[前端测试](./frontend-testing)。

## 端到端（E2E）测试 {#end-to-end-e2e-testing}

端到端测试用于验证用户在 webforJ 应用中的实际体验。此类应用会生成动态的单页 Web 界面。这些测试会模拟用户交互，并验证整个应用的功能。

借助 [**Selenium**](https://www.selenium.dev/) 和 [**Playwright**](https://playwright.dev/java/docs/intro) 等工具，你可以：

- 通过模拟按钮点击和表单提交来自动执行浏览器交互。
- 验证动态 UI 组件的渲染和交互是否一致。
- 确认应用在不同浏览器和设备上的行为是否一致。

## 结合测试策略 {#combining-testing-strategies}

结合单元测试和 E2E 测试：

1. **隔离问题**：通过单元测试及早检测并解决组件级别的错误。
2. **确认可靠性**：通过 E2E 测试验证完整的用户流程和系统集成。

## 主题 {#topics}

<DocCardList className="topics-section" />
