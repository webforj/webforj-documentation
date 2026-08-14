---
title: Troubleshooting
sidebar_position: 11
description: >-
  Fix the common cases where craftforJ doesn't appear, a feature is unavailable,
  or the assistant doesn't answer.
_i18n_hash: fcc5f7188c92523c0fb500bfc7b0ce58
---
### 页面上没有内容 {#nothing-appears-on-the-page}

craftforJ 仅在满足 [入门指南](/docs/craftforj/getting-started#requirements) 中的每个要求时才会附加，并且当缺少某项要求时完全不显示内容。请按顺序检查：类路径上的 `webforj-devtools` 依赖，调试模式，craftforJ 属性，运行应用程序的机器上的浏览器，以及有效的开发者许可证。如果配置文件位置不正确，或者覆盖其中一个属性的配置文件，则会产生与属性关闭相同的结果。

### 无法使用某个功能 {#a-feature-is-unavailable}

craftforJ 显示禁用的功能而不是隐藏它，因此存在但标记为不支持的控件是故意关闭的。要么是在应用的配置中通过 [功能标志](/docs/craftforj/configuration#feature-flags) 禁用，要么是类路径上的 `webforj-devtools` 版本早于该功能。

写入源代码还需要 craftforJ 能找到的项目根目录。请检查 [应用信息](/docs/craftforj/app-info) 中检测到的项目根目录，如果错误，请设置 [`project-root`](/docs/craftforj/configuration#project-root)。

### Java 验证不如预期强 {#java-validation-is-weaker-than-expected}

助手的 [编译验证](/docs/craftforj/ai#it-writes-java) 需要 JDK。请检查 [应用信息](/docs/craftforj/app-info) 中的 Java 版本，并在 JDK 上而不是 JRE 上运行应用程序。

### craftforJ 在更新后看起来过时 {#craftforj-looks-out-of-date-after-an-update}

您的浏览器缓存了先前的版本。强制刷新页面，或在隐私窗口中打开应用程序。如果问题仍然存在，请确认 [应用信息](/docs/craftforj/app-info) 中实际在类路径上的 `webforj-devtools` 版本，因为本地 Maven 仓库中的旧 jar 在浏览器中看起来相同。

### 助手不回答 {#the-assistant-doesnt-answer}

助手需要一个配置好的提供者和一个可以调用工具的模型。没有工具支持的模型可以进行对话，但无法检查或更改任何内容。一个经常失去对话记录的本地模型通常是因为上下文窗口太小。

如果配置和可访问的本地模型但每个请求都被拒绝，模型服务器正在拒绝页面的来源。对于 Ollama，允许来源并重新启动它：

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

在 Linux 上，设置 Ollama 启动时的环境中的 `OLLAMA_ORIGINS` 并重新启动它。

### craftforJ 说应用程序正在重新启动 {#craftforj-says-the-app-is-restarting}

您的应用在开发过程中会定期消失，每次重建时都是如此。craftforJ 报告发生的情况而不是冻结，因此它显示应用程序正在重新启动或页面正在重新加载，并且其控件在应用程序恢复之前保持无响应。它会在恢复时自动重新连接，您的选择和待处理工作将保持完整，因此只需等待即可。如果它报告无法完全访问应用程序，请确认应用程序仍在运行并重新加载页面。

### 应用程序持续重启 {#the-app-keeps-restarting}

对源代码应用更改会重新启动应用，如 [应用更改后](/docs/craftforj/source-changes#after-you-apply) 所述。没有应用更改而发生的重启来自构建的文件监视器，而不是来自 craftforJ。

### 收集日志 {#collecting-logs}

在报告问题之前，请在 craftforJ 设置中打开详细日志记录，清除日志，重现问题，然后下载日志。将其与 [应用信息](/docs/craftforj/app-info) 的内容一起附上。
