---
sidebar_position: 1
title: Testing
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 3c566f2e9edf3bf00e984a01e0b2f049
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ 测试

在 webforJ 应用中，测试结合了单元测试、前端测试和端到端（E2E）测试，每种测试在保持应用的稳定性和可靠性方面都有其独特的目的。

## 单元测试 {#unit-testing}

单元测试侧重于验证单个组件或后端逻辑的独立性。通过遵循标准的 Java 测试实践，例如使用 [JUnit](https://junit.org/junit5/)，开发人员可以有效地验证特定的应用逻辑，确保每个“单元”按预期执行。

## 前端测试 {#frontend-testing}

前端测试涵盖项目作者使用的 [前端打包工具](/docs/managing-resources/bundler/overview)。 [Bun](https://bun.sh/) 测试运行器将它们作为与运行 Java 测试相同的构建的一部分进行运行，因此 TypeScript 组件或客户端逻辑的验证与后端相同。请参见 [前端测试](./frontend-testing)。

## 端到端（E2E）测试 {#end-to-end-e2e-testing}

端到端测试对于验证 webforJ 应用中的用户体验至关重要，这些应用生成动态的单页 Web 界面。这些测试模拟用户交互并验证整个应用的功能。

使用像 [**Selenium**](https://www.selenium.dev/) 和 [**Playwright**](https://playwright.dev/java/docs/intro) 这样的工具，你可以：

- 自动化浏览器交互，例如按钮点击和表单提交。
- 验证动态 UI 组件的一致渲染和交互性。
- 确保不同浏览器和设备之间的行为一致性。

## 结合测试策略 {#combining-testing-strategies}

通过结合单元测试和 E2E 测试：

1. **隔离问题**：通过单元测试及早检测和解决组件级错误。
2. **确保可靠性**：通过 E2E 测试验证完整的用户旅程和系统集成。

## 主题 {#topics}

<DocCardList className="topics-section" />
