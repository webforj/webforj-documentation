---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0d350dce3bf30a09bc54f089107b7a25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField` 组件允许用户输入和编辑单行文本。您可以配置该字段以显示特定的虚拟键盘，例如数字键盘、电子邮件输入、电话输入或 URL 输入。该组件还提供内置验证，以拒绝不符合指定类型的值。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="Field" />

`TextField` 适用于需要文本输入或编辑的各种场景。以下是使用 `TextField` 的一些示例：

1. **表单输入**：`TextField` 通常用于表单中，以捕获用户输入，例如姓名、地址或评论。当输入通常足够短，可以容纳在一行内时，最好在应用中使用 `TextField`。

2. **搜索功能**：`TextField` 可用于提供搜索框，允许用户输入搜索查询。

3. **文本编辑**：`TextField` 非常适合需要文本编辑或内容创建的应用，例如文档编辑器、聊天应用或笔记应用。

## 类型 {#types}

您可以使用 `setType()` 方法指定 `TextField` 的类型。同样，您可以使用 `getType()` 方法检索类型，该方法将返回一个枚举值。

- `Type.TEXT`：这是 `TextField` 的默认类型，并会自动删除输入值中的换行符。

- `Type.EMAIL`：该类型用于输入电子邮件地址。它根据标准电子邮件语法验证输入。此外，它为兼容的浏览器和设备提供动态键盘，简化输入过程，包括常用键，例如 <kbd>.com</kbd> 和 <kbd>@</kbd>。

  :::note
  尽管此验证确认了电子邮件地址的格式，但无法保证电子邮件存在。
  :::

- `Type.TEL`：该类型用于输入电话号码。在某些具有动态键盘的设备上，该字段将显示电话键盘。

- `Type.URL`：该类型用于输入 URL。它验证用户输入的 URL 是否包含方案和域名，例如： https://webforj.com。此外，它为兼容的浏览器和设备提供动态键盘，简化输入过程，包括常用键，例如 <kbd>:</kbd>、<kbd>/</kbd> 和 <kbd>.com</kbd>。

- `Type.SEARCH`：单行文本字段，用于输入搜索字符串。输入值中的换行符会自动删除。

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## 字段值 {#field-value}

`TextField` 的值表示当前用户输入的字符串。在 webforJ 中，可以使用 `getValue()` 方法访问该值，或通过 `setValue(String)` 方法以编程方式更新。

```java
//设置初始内容
textField.setValue("初始内容");

//检索当前值
String text = textField.getValue();
```

如果在没有当前值的字段上使用 `getValue()` 方法，它将返回空字符串（`""`）。

这种行为与 HTML `<input type="text">` 元素通过 JavaScript 暴露其值的方式一致。

:::tip 结合值处理与验证
应用约束，如 [模式](#pattern-matching)、[最小长度](#setminlength) 或 [最大长度](#setmaxlength)，来定义值何时被视为有效。
:::

## 占位符文本 {#placeholder-text}

您可以使用 `setPlaceholder()` 方法为 `TextField` 设置占位符文本。当字段为空时，显示占位符文本，以帮助提示用户在 `TextField` 中输入适当的内容。

## 选定文本 {#selected-text}

可以与 `TextField` 类进行交互，以检索用户选定的文本，并获取有关用户选择的信息。您可以使用 `getSelectedText()` 方法在 `TextField` 中检索选定的文本。此行为通常与事件结合使用。

```java
TextField textField = new TextField("输入内容...");
Button getSelectionBtn = new Button("获取选定文本");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("选定文本: " + selected);
});
```

同样，可以使用 `getSelectionRange()` 方法检索 `TextField` 的当前选择范围。此方法返回一个 `SelectionRange` 对象，表示所选文本的起始和结束索引。

:::tip 值更改与选择
使用 `onValueChange()` 来处理字段值的更改。其 `ValueChangeEvent<String>` 通过 `event.getValue()` 提供新值，但不包含选择数据。要读取当前选择，请调用字段上的 `getSelectedText()` 或 `getSelectionRange()`。
:::

## 模式匹配 {#pattern-matching}

您可以使用 `setPattern()` 方法定义 `TextField` 的验证规则，使用正则表达式。设置模式会添加一个约束验证，要求输入值与指定模式匹配。

模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，由浏览器解析。内部应用 `u`（Unicode）标志，以确保 Unicode 代码点的准确匹配。不要用正斜杠（`/`）将模式包裹起来，因为这些不是必需的，并将被视为字面字符。

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // 例如 ABC12
```

如果未提供模式，或者语法无效，则验证规则将被忽略。

:::tip 上下文帮助
在为 `TextField` 使用复杂模式时，考虑结合使用 `setLabel()`、`setHelperText()` 和 `setTooltipText()` 方法，提供额外的提示和指导。
:::

## 最小和最大长度 {#minimum-and-maximum-length}

`TextField` 组件支持基于用户输入的字符数的约束验证。这可以使用 `setMinLength()` 和 `setMaxLength()` 方法进行控制。使用这两个方法定义可接受输入长度的明确边界。

:::info 长度要求
默认情况下，当输入值超出范围时，字段会显示一条消息，指示用户需要缩短或延长输入。该消息可以使用 `setInvalidMessage()` 方法覆盖。
:::

### `setMinLength()` {#setminlength}

该方法设置字段被视为有效所需输入的 **最小 UTF-16 代码单元数**。该值必须是整数，并且不能超过配置的最大长度。

```java
textField.setMinLength(5); // 用户必须输入至少 5 个字符
```

如果输入的字符少于最小要求，则输入将未通过约束验证。这条规则仅在用户更改字段值时适用。

### `setMaxLength()` {#setmaxlength}

该方法设置文本字段中允许的 **最大 UTF-16 代码单元数**。该值必须是 `0` 或更大。如果未设置或设置为无效值，则不会强制执行最大限制。

```java
textField.setMaxLength(20); // 用户无法输入超过 20 个字符
```

如果输入长度超过最小长度，字段将未通过约束验证。与 `setMinLength()` 一样，这项验证仅在用户更改值时触发。

## 最佳实践 {#best-practices}

以下部分概述了一些使用 `TextField` 的建议最佳实践。

- **提供清晰的标签和说明**：明确标记 `TextField`，以指示用户应输入的信息类型。提供额外的说明文本或占位符值以指导用户，并设定对所需输入的期望。

- **拼写检查和自动完成**：在适用的情况下，考虑使用 `setSpellCheck()` 进行拼写检查和/或在 `TextField` 中使用自动完成。这些功能可以帮助用户更快地输入信息，并通过提供基于先前输入或预定义选项的建议值来减少错误。

- **可访问性**：提供清晰的标签、键盘导航和对辅助技术的支持，以便残疾用户能够使用该字段。
