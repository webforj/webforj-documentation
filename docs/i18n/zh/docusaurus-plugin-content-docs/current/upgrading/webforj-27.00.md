---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_hash: 85416371e550d0aedae0a0771aff67be
---
本文件作为将 webforJ 应用程序从 26.00 升级到 27.00 的指南。
以下是现有应用程序继续顺利运行所需的更改。
如往常一样，请查看 [GitHub 发布概述](https://github.com/webforj/webforj/releases) 以获取不同版本之间更改的更全面列表。

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## 文本和 HTML 内容 {#text-and-html-content}

早期版本将包裹在 `<html>` 中并传递给 `setText()` 的值视为 HTML。此行为已被弃用，并在 webforJ 27.00 中删除。`webforj.legacyHtmlInText` 设置控制此行为：

- `true`（默认）：包裹在 `<html>` 中的值将其内容呈现为 HTML。
- `false`：同样的值将被原样显示。

在这两种情况下，`<html>` 包裹都不会被显示。

第一次传递包裹在 `<html>` 中的值到 `setText()` 时，将记录一个警告，其中包含组件名称和调用位置，以便可以将调用移至 `setHtml()`。
