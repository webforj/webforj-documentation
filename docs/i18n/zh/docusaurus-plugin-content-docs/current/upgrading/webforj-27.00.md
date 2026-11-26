---
title: 升级到 27.00
description: 从 26.00 升级到 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_title_hash: f521441c4a5da00c1bc05ae7307c3644
_i18n_description_hash: 2ca050c7f4bc5f02fb7c8680550799a3
_i18n_hash: 2757e1b06263f629ef0c1e5c15785122
---
本文档介绍如何将 webforJ 应用从 26.00 升级到 27.00。
以下是现有应用需要进行的更改，以确保其继续顺畅运行。
与往常一样，请参阅 [GitHub 版本概览](https://github.com/webforj/webforj/releases)，了解各版本之间更全面的更改列表。

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## 文本和 HTML 内容 {#text-and-html-content}

早期版本会将用 `<html>` 包裹并传递给 `setText()` 的值视为 HTML。此行为已弃用，并将在 webforJ 27.00 中移除。`webforj.legacyHtmlInText` 设置用于控制此行为：

- `true`（默认值）：用 `<html>` 包裹的值会将其内容作为 HTML 渲染。
- `false`：同样的值会按字面内容显示。

在这两种情况下，`<html>` 包裹标记都不会显示。

用 `<html>` 包裹的值第一次传递给 `setText()` 时，系统会记录一条警告，其中包含组件名称和调用位置，以便将该调用改为使用 `setHtml()`。

## 使用 `@Size` 进行必填绑定 {#required-binding-using-size-annotation}

在 27.00 之前的版本中，使用 [Jakarta 注解](/docs/data-binding/validation/jakarta-validation) `@Size` 会自动将绑定的组件标记为必填。
从现在开始，只有在 `@Size` 定义了 `min` 值时，才会将绑定的组件标记为必填。

```java
// 不再将字段标记为必填
@Size(max = 80, message = "街道名称必须少于 80 个字符")
private String street;
```

```java
// 将字段标记为必填
@Size(min = 2, max = 80, message = "街道名称必须为 2 到 80 个字符")
private String street;
```
