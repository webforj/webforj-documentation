---
sidebar_position: 1
title: Testing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 34ef676ce8690df9732e6bee7af56206
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ 测试

在 webforJ 应用程序中，测试涉及单元测试和端到端 (E2E) 测试的组合，以确保应用程序的稳定性和可靠性。每种测试类型在维护应用程序质量方面都有独特的目的。

## 单元测试 {#unit-testing}

单元测试侧重于验证单个组件或后端逻辑的独立性。通过遵循标准的 Java 测试实践，例如使用 [JUnit](https://junit.org/junit5/)，开发人员可以有效地验证特定的应用程序逻辑，并确保每个“单元”按预期执行。

## 端到端 (E2E) 测试 {#end-to-end-e2e-testing}

端到端测试对于验证 webforJ 应用程序中的用户体验至关重要，这些应用生成动态的单页 web 界面。这些测试模拟用户交互并验证整个应用程序的功能。

使用像 [**Selenium**](https://www.selenium.dev/) 和 [**Playwright**](https://playwright.dev/java/docs/intro) 这样的工具，您可以：

- 自动化浏览器交互，例如按钮点击和表单提交。
- 验证动态 UI 组件的一致渲染和交互性。
- 确保在不同浏览器和设备之间的一致性行为。

## 结合测试策略 {#combining-testing-strategies}

通过结合单元测试和 E2E 测试：

1. **隔离问题**：通过单元测试及早发现和解决组件级错误。
2. **确保可靠性**：通过 E2E 测试验证完整的用户旅程和系统集成。

## 主题 {#topics}

<DocCardList className="topics-section" />
