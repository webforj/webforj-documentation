---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
description: >-
  Share common field properties across webforJ input components, including
  labels, helper text, required state, spellcheck, and prefix or suffix slots.
_i18n_hash: 56462cf4d659ce15f72a624acae60f0f
---
<JavadocLink type="foundation" location="com/webforj/component/field/DwcField"/>

webforJ 框架支持十一种类型的字段组件，包括四种带遮罩的字段，每种字段都有不同的行为和实现，以满足各种输入需求。尽管这些组件的实现有所不同，但本文描述了所有字段类之间的共享属性。

:::info
本节描述了 webforJ 中各种字段组件的共同特征，并不是一个可以实例化和使用的类。
:::

## 共享字段属性 {#shared-field-properties}

### 标签 {#label}

字段标签是与字段相关联的描述性文本或标题，可以在构造函数中定义或通过使用 `setLabel()` 方法设置。标签提供了简要的说明或提示，帮助用户理解特定字段的目的或期望输入。字段标签对可用性至关重要，并在可访问性中发挥着关键作用，因为它们使屏幕阅读器和辅助技术能够提供准确的信息并促进键盘导航。

### 辅助文本 {#helper-text}

每个字段都可以使用 `setHelperText()` 方法在输入下方显示辅助文本。此辅助文本提供了关于可用输入的额外上下文或解释，确保用户拥有做出明智选择所需的信息。

### 必填 {#required}

您可以调用 `setRequired(true)` 方法来要求用户在提交表单之前提供一个值。该属性与字段标签协同工作，提供了一个视觉指示，表明某个字段是必要的。这种视觉提示帮助用户准确填写表单。

:::info
字段组件内置的视觉验证可以通知用户必填字段为空或用户已删除值。
:::

### 拼写检查 {#spellcheck}

通过使用 `setSpellCheck(true)`，您可以允许浏览器或用户代理检查用户输入文本的拼写并识别任何错误。

### 前缀和后缀 {#prefix-and-suffix}

插槽提供了灵活的选项，增强字段组件的功能。您可以在字段内嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片以及其他有益组件，以进一步向用户明确意图。字段有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在字段中显示的选项前后插入各种组件。以下是使用 `TextField` 字段的示例：

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

:::info
由于所有字段组件都是从单个 web 组件构建的，它们共享以下阴影部分和 CSS 属性值
:::

<TableBuilder name="Field" />

## 主题 {#topics}

<DocCardList className="topics-section" />
