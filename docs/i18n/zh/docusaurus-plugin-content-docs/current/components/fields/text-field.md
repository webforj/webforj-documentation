---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 2f01abb14f66d83dd6edd3cc18ac560e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField` 组件允许用户在单行中输入和编辑文本。你可以配置字段以显示特定的虚拟键盘，例如数字键盘、电子邮件输入键盘、电话输入键盘或 URL 输入键盘。该组件还提供内置验证，用于拒绝不符合指定类型的值。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="Field" />

`TextField` 适用于各种需要输入或编辑文本的场景。以下是一些适合使用 `TextField` 的示例：

1. **表单输入**：`TextField` 常用于表单中，用于收集用户输入，例如姓名、地址或评论。如果输入内容通常足够短，可以放在一行中，那么在应用中使用 `TextField` 是最佳选择。

2. **搜索功能**：`TextField` 可用作搜索框，供用户输入搜索查询。

3. **文本编辑**：`TextField` 非常适合需要编辑文本或创建内容的应用，例如文档编辑器、聊天应用或记事应用。

## 类型 {#types}

你可以使用 `setType()` 方法指定 TextField 的类型。同样，你可以使用 `getType()` 方法获取类型，该方法会返回一个枚举值。

- `Type.TEXT`：这是 `TextField` 的默认类型，会自动从输入值中移除换行符。

- `Type.EMAIL`：此类型用于输入电子邮件地址。它会根据标准电子邮件语法验证输入内容。此外，它还会为兼容的浏览器和设备提供动态键盘，通过加入 <kbd>.com</kbd> 和 <kbd>@</kbd> 等常用按键来简化输入过程。

  :::note
  此验证可确认电子邮件地址的格式，但不能保证该电子邮件地址确实存在。
  :::

- `Type.TEL`：此类型用于输入电话号码。在某些配备动态键盘的设备上，字段会显示电话键盘。

- `Type.URL`：此类型用于输入 URL。它会验证用户输入的 URL 是否包含方案和域名，例如：https://webforj.com。此外，它还会为兼容的浏览器和设备提供动态键盘，通过加入 <kbd>:</kbd>、<kbd>/</kbd> 和 <kbd>.com</kbd> 等常用按键来简化输入过程。

- `Type.SEARCH`：用于输入搜索字符串的单行文本字段。换行符会自动从输入值中移除。

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## 字段值 {#field-value}

`TextField` 的值表示当前用户输入的字符串。在 webforJ 中，可以使用 `getValue()` 方法访问该值，或通过 `setValue(String)` 以编程方式更新该值。

```java
//Set the inital content
textField.setValue("Initial content");

//Retrieve the current value
String text = textField.getValue();
```

如果对没有当前值的字段调用 `getValue()` 方法，该方法会返回一个空字符串（`""`）。

此行为与 HTML `<input type="text">` 元素通过 JavaScript 提供其值的方式一致。

:::tip 将值处理与验证结合使用
应用诸如 [模式](#pattern-matching)、[最小长度](#setminlength) 或 [最大长度](#setmaxlength) 等约束，以定义何时将值视为有效。
:::

## 占位文本 {#placeholder-text}

你可以使用 `setPlaceholder()` 方法为 `TextField` 设置占位文本。字段为空时会显示占位文本，提示用户在 `TextField` 中输入适当的内容。

## 选中文本 {#selected-text}

你可以与 `TextField` 类交互，以获取用户选中的文本并了解用户的选区信息。你可以使用 `getSelectedText()` 方法获取 `TextField` 中选中的文本。此功能通常会与事件配合使用。

```java
TextField textField = new TextField("Enter something...");
Button getSelectionBtn = new Button("Get Selected Text");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Selected text: " + selected);
});
```

同样，你也可以使用 `getSelectionRange()` 方法获取 `TextField` 当前的选区范围。该方法会返回一个 `SelectionRange` 对象，表示选中文本的起始和结束索引。

:::tip 值变更与选区
使用 `onValueChange()` 处理字段值的变更。其 `ValueChangeEvent<String>` 会通过 `event.getValue()` 提供新值，但不包含选区数据。如需读取当前选区，请在字段上调用 `getSelectedText()` 或 `getSelectionRange()`。
:::

## 模式匹配 {#pattern-matching}

你可以使用 `setPattern()` 方法，通过正则表达式为 `TextField` 定义验证规则。设置模式会添加一项约束验证，要求输入值与指定模式匹配。

模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，并由浏览器进行解释。内部会应用 `u`（Unicode）标志，以确保准确匹配 Unicode 码点。不要在模式两侧加上正斜杠（`/`），因为它们不是必需的，并且会被视为字面字符。

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // e.g. ABC12
```

如果未提供模式，或模式语法无效，则会忽略此验证规则。

:::tip 上下文帮助
为 `TextField` 使用复杂模式时，可以考虑组合使用 `setLabel()`、`setHelperText()` 和 `setTooltipText()` 方法，
以提供额外提示和指导。
:::

## 最小长度和最大长度 {#minimum-and-maximum-length}

`TextField` 组件支持根据用户输入的字符数进行约束验证。可以使用 `setMinLength()` 和 `setMaxLength()` 方法进行控制。使用这两个方法可以定义可接受输入长度的明确范围。

:::info 长度要求
默认情况下，如果输入值超出范围，字段会显示提示信息，告知用户需要缩短或延长输入内容。你可以使用 `setInvalidMessage()` 方法覆盖此提示信息。
:::

### `setMinLength()` {#setminlength}

此方法设置字段被视为有效所需输入的**最小 UTF-16 代码单元数**。该值必须是整数，且不应超过配置的最大长度。

```java
textField.setMinLength(5); // User must enter at least 5 characters
```

如果输入的字符少于最小要求，输入内容将无法通过约束验证。此规则仅在用户更改字段值时生效。

### `setMaxLength()` {#setmaxlength}

此方法设置文本字段允许输入的**最大 UTF-16 代码单元数**。该值必须大于或等于 `0`。如果未设置，或设置为无效值，则不会强制执行最大长度限制。

```java
textField.setMaxLength(20); // User cannot enter more than 20 characters
```

如果输入长度超过最大长度，字段将无法通过约束验证。与 `setMinLength()` 类似，此验证仅在用户更改值时触发。

## 最佳实践 {#best-practices}

以下章节概述了使用 `TextField` 的一些建议做法。

- **提供清晰的标签和说明**：为 `TextField` 设置清晰的标签，说明用户应输入哪种信息。提供额外的说明文字或占位值，以引导用户并明确输入要求。

- **拼写检查和自动完成**：在适用时，可以考虑使用 `setSpellCheck()` 启用拼写检查，和/或在 `TextField` 中使用自动完成。这些功能可以根据先前输入或预定义选项提供建议值，帮助用户更快地输入信息并减少错误。

- **无障碍性**：提供清晰的标签、键盘导航和辅助技术支持，让残障用户也能使用该字段。
