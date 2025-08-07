---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: b04acdedbd800790417edfe940160bf2
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

webforJ框架支持七种不同类型的字段组件，每种组件具有不同的行为和实现，以满足各种输入需求。虽然这些组件在实现上各有差异，但本文描述了所有字段类之间的共同属性。

:::info
本节描述了webforJ中各种字段组件的共同特征，并不是一个可以实例化和使用的类。
:::

## 共享字段属性 {#shared-field-properties}

### 标签 {#label}

字段标签是与字段相关联的描述性文本或标题，可以在构造函数中定义或使用`setLabel()`方法设置。标签提供简要的解释或提示，帮助用户理解特定字段的用途或预期输入。字段标签对可用性很重要，并在无障碍性中发挥关键作用，因为它们允许屏幕阅读器和辅助技术提供准确的信息，促进键盘导航。

### 帮助文本 {#helper-text}

每个字段可以使用`setHelperText()`方法在输入框下方显示帮助文本。此帮助文本提供有关可用输入的额外上下文或解释，确保用户拥有做出明智选择所需的信息。

### 必填 {#required}

您可以调用`setRequired(true)`方法来要求用户在提交表单之前提供一个值。此属性与字段标签协同工作，提供视觉指示，表明某个字段是必要的。这种视觉提示帮助用户准确填写表单。

:::info
字段组件包含内置的视觉验证，通知用户当必填字段为空或用户移除值时。
:::

### 拼写检查 {#spellcheck}

通过使用`setSpellCheck(true)`，您可以允许浏览器或用户代理验证用户输入的文本的拼写并识别任何错误。

### 前缀和后缀 {#prefix-and-suffix}

插槽提供了灵活的选项，以增强字段组件的功能。您可以在字段内嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片和其他有用的组件，以进一步澄清用户的意图。字段有两个插槽：`prefix`和`suffix`插槽。使用`setPrefixComponent()`和`setSuffixComponent()`方法在字段中显示选项之前和之后插入各种组件。以下是使用`TextField`字段的示例：

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## 样式 {#styling}

:::info
由于所有字段组件都是从单一的Web组件构建而成，它们共享以下Shadow Parts和CSS属性值
:::

<TableBuilder name="Field" />

## 主题 {#topics}

<DocCardList className="topics-section" />
