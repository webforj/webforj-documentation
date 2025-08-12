---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 288d408cb058dbaa417fea651698123a
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

webforJ 框架支持七种不同类型的字段组件，每种组件具有不同的行为和实现，以满足不同输入的需求。尽管这些组件在实现上有变体，但本文描述了所有字段类之间的共享属性。

:::info
本节描述了 webforJ 中各种字段组件的共同特性，而不是可以实例化和使用的类。
:::

## 共享字段属性 {#shared-field-properties}

### 标签 {#label}

字段标签是与字段相关联的描述性文本或标题，可以在构造函数中定义或通过使用 `setLabel()` 方法定义。标签提供简要说明或提示，帮助用户理解该字段的目的或期望的输入。字段标签对于可用性非常重要，并在可访问性中发挥着关键作用，因为它们允许屏幕阅读器和辅助技术提供准确的信息并促进键盘导航。

### 帮助文本 {#helper-text}

每个字段可以使用 `setHelperText()` 方法在输入框下方显示帮助文本。此帮助文本提供关于可用输入的额外上下文或解释，确保用户拥有必要的信息以做出明智的选择。

### 必填 {#required}

您可以调用 `setRequired(true)` 方法，要求用户在提交表单之前提供值。此属性与字段标签协同工作，提供视觉指示，表明该字段是必要的。这种视觉提示帮助个人准确填写表单。

:::info
字段组件包含内置的视觉验证，以通知用户何时必填字段为空或用户移除了值。
:::

### 拼写检查 {#spellcheck}

通过使用 `setSpellCheck(true)`，您可以允许浏览器或用户代理验证用户输入文本的拼写并识别任何错误。

### 前缀和后缀 {#prefix-and-suffix}

插槽提供灵活的选项，以改善字段组件的功能。您可以在字段中嵌入图标、标签、加载指示器、清除/重置功能、头像/个人资料图片和其他有用的组件，以进一步向用户阐明预期含义。字段有两个插槽：`prefix` 和 `suffix` 插槽。使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法将各种组件插入到字段中显示选项之前和之后。以下是使用 `TextField` 字段的示例：

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

:::info
由于所有字段组件都是基于单一的 web 组件构建的，它们共享以下 Shadow Parts 和 CSS 属性值
:::

<TableBuilder name="Field" />

## 主题 {#topics}

<DocCardList className="topics-section" />
