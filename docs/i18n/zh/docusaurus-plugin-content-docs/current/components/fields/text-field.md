---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: d582e67d9cfff3b1934f0e3b1a8396ab
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

`TextField` 组件允许用户在单行中输入和编辑文本。您可以配置该字段以显示特定的虚拟键盘，例如数字键盘、电子邮件输入、电话输入或 URL 输入。该组件还提供内置验证，以拒绝不符合指定类型的值。

## 用法 {#usages}

`TextField` 适用于需要文本输入或编辑的广泛场景。以下是一些使用 `TextField` 的示例：

1. **表单输入**：`TextField` 通常用于表单以捕获用户输入，例如姓名、地址或评论。当输入内容通常足够短而能够适用于单行时，最好在应用程序中使用 `TextField`。

2. **搜索功能**：`TextField` 可用于提供搜索框，允许用户输入搜索查询。

3. **文本编辑**：`TextField` 非常适合需要文本编辑或内容创建的应用程序，例如文档编辑器、聊天应用或记事应用。

## 类型 {#types}

您可以使用 `setType()` 方法指定 `TextField` 的类型。类似地，您可以使用 `getType()` 方法检索类型，该方法将返回一个枚举值。

- `Type.TEXT`：这是 `TextField` 的默认类型，自动移除输入值中的换行符。

- `Type.EMAIL`：此类型用于输入电子邮件地址。它根据标准电子邮件语法验证输入。此外，它为兼容的浏览器和设备提供了动态键盘，简化输入过程，包括常用的键，如 <kbd>.com</kbd> 和 <kbd>@</kbd>。

  :::note
  虽然此验证确认了电子邮件地址的格式，但并不能保证电子邮件存在。
  :::

- `Type.TEL`：此类型用于输入电话号码。在某些具有动态键盘的设备上，字段将显示一个电话键盘。

- `Type.URL`：此类型用于输入 URL。它验证用户输入的 URL 是否包含方案和域名，例如： https://webforj.com。此外，它为兼容的浏览器和设备提供了动态键盘，简化输入过程，包括常用的键，如 <kbd>:</kbd>、<kbd>/</kbd> 和 <kbd>.com</kbd>。

- `Type.SEARCH`：用于输入搜索字符串的单行文本字段。输入值中的换行符会自动移除。

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## 字段值 {#field-value}

`TextField` 的值表示当前用户输入的字符串。在 webforJ 中，可以通过 `getValue()` 方法访问，或者使用 `setValue(String)` 方法进行程序化更新。

```java
//设置初始内容
textField.setValue("初始内容");

//检索当前值
String text = textField.getValue();
```

如果在没有当前值的字段上使用 `getValue()` 方法，它将返回一个空字符串 (`""`)。

此行为与 HTML `<input type="text">` 元素通过 JavaScript 显示其值的方式一致。

:::tip 结合值处理和验证
应用约束，例如 [pattern](#pattern-matching)、[minimum length](#setminlength) 或 [maximum length](#setmaxlength)，以定义何时值被视为有效。
:::

## 占位符文本 {#placeholder-text}

您可以使用 `setPlaceholder()` 方法为 `TextField` 设置占位符文本。当字段为空时，将显示占位符文本，帮助提示用户在 `TextField` 中输入适当的内容。

## 选择文本 {#selected-text}

可以与 `TextField` 类进行交互，以检索用户的选定文本，并获取关于用户选择的信息。您可以通过 `getSelectedText()` 方法检索 `TextField` 中的选定文本。此行为通常与事件一起使用。

```java
TextField textField = new TextField("输入内容...");
Button getSelectionBtn = new Button("获取选定文本");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("选定文本: " + selected);
});
```

同样，您可以使用 `getSelectionRange()` 方法检索 `TextField` 的当前选择范围。返回一个 `SelectionRange` 对象，表示选定文本的起始和结束索引。

:::tip 使用 `getSelectedText()` 与事件负载
虽然您可以在事件处理程序内手动调用 `getSelectedText()`，但更有效的方法是使用事件负载中提供的选择数据，例如在 `SelectionChangeEvent` 中，以避免额外的查找。
:::

## 模式匹配 {#pattern-matching}

您可以使用 `setPattern()` 方法使用正则表达式定义 `TextField` 的验证规则。设置模式会添加一个约束验证，要求输入值与指定模式匹配。

模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，由浏览器解释。内部应用 `u`（Unicode）标志，以确保准确匹配 Unicode 字符。请勿将模式包裹在正斜杠 (`/`) 中，因为不需要这些字符，并将作为字面字符处理。

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // 例如 ABC12
```

如果未提供模式或语法无效，则验证规则将被忽略。

:::tip 上下文帮助
在为 `TextField` 使用复杂模式时，请考虑结合使用 `setLabel()`、`setHelperText()` 和 `setTooltipText()` 方法，以提供额外的提示和指导。
:::

## 最小和最大长度 {#minimum-and-maximum-length}

`TextField` 组件支持基于用户输入字符数的约束验证。这可以通过 `setMinLength()` 和 `setMaxLength()` 方法控制。使用这两种方法定义可接受输入长度的明确边界。

:::info 长度要求
默认情况下，当输入值超出范围时，字段会显示一条消息，指示用户是否需要缩短或延长输入。这条消息可以通过 `setInvalidMessage()` 方法覆盖。
:::

### `setMinLength()` {#setminlength}

此方法设置字段被视为有效所需输入的 **最小 UTF-16 代码单位数**。值必须是一个整数，并且不得超过配置的最大长度。

```java
textField.setMinLength(5); // 用户必须输入至少 5 个字符
```

如果输入的字符数少于所需的最小字符数，则输入将无法通过约束验证。此规则仅在用户更改字段值时适用。

### `setMaxLength()` {#setmaxlength}

此方法设置文本字段中允许的 **最大 UTF-16 代码单位数**。该值必须为 `0` 或更大。如果未设置或设置为无效值，则不施加最大限制。

```java
textField.setMaxLength(20); // 用户不能输入超过 20 个字符
```

如果输入长度超过最小长度，字段将无法通过约束验证。与 `setMinLength()` 一样，此验证仅在用户更改值时触发。

## 最佳实践 {#best-practices}

以下部分概述了使用 `TextField` 的一些建议最佳实践。

- **提供清晰的标签和说明**：清晰标记 `TextField` 以指示用户应输入的信息类型。提供额外的说明文字或占位符值，以指导用户并设置对所需输入的期望。

- **拼写检查和自动完成**：在适用的情况下，考虑使用 `setSpellCheck()` 进行拼写检查和/或使用 `TextField` 中的自动完成功能。这些功能可以帮助用户更快地输入信息，并通过提供基于先前输入或预定义选项的建议值来减少错误。

- **可访问性**：使用 `TextField` 组件时，应考虑可访问性，遵守可访问性标准，如适当的标签、键盘导航支持以及与辅助技术的兼容性。确保残障人士能够有效地与 `TextField` 进行交互。
