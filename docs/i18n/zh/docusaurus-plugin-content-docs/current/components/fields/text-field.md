---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: e972f03e1d4deb1802bc4f56104e61b3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField` 组件允许用户在单行中输入和编辑文本。您可以配置该字段以显示特定的虚拟键盘，例如数字键盘、电子邮件输入、电话号码输入或 URL 输入。该组件还提供内置验证，以拒绝不符合指定类型的值。

<!-- INTRO_END -->

## 用法 {#usages}

<ParentLink parent="Field" />

`TextField` 适用于需要文本输入或编辑的各种场景。以下是使用 `TextField` 的一些示例：

1. **表单输入**：`TextField` 通常用于表单中捕获用户输入，例如姓名、地址或评论。当输入内容通常可以适合一行时，在应用程序中使用 `TextField` 是最佳选择。

2. **搜索功能**：`TextField` 可用于提供搜索框，允许用户输入搜索查询。

3. **文本编辑**：`TextField` 非常适合需要文本编辑或内容创作的应用程序，例如文档编辑器、聊天应用或记事应用。

## 类型 {#types}

您可以使用 `setType()` 方法指定 `TextField` 的类型。类似地，您可以使用 `getType()` 方法检索该类型，该方法将返回一个枚举值。

- `Type.TEXT`：这是 `TextField` 的默认类型，并自动从输入值中去除换行符。

- `Type.EMAIL`：此类型用于输入电子邮件地址。它根据标准电子邮件语法验证输入。此外，它为兼容的浏览器和设备提供动态键盘，通过包含常用键（如 <kbd>.com</kbd> 和 <kbd>@</kbd>）简化输入过程。

  :::note
  尽管此验证确认了电子邮件地址的格式，但并不保证该电子邮件存在。
  :::

- `Type.TEL`：此类型用于输入电话号码。该字段将在某些设备中显示动态键盘上的电话号码键盘。

- `Type.URL`：此类型用于输入 URL。它验证用户输入的 URL 是否包含方案和域名，例如： https://webforj.com。此外，它为兼容的浏览器和设备提供动态键盘，通过包含常用键（如 <kbd>:</kbd>、<kbd>/</kbd> 和 <kbd>.com</kbd>）简化输入过程。

- `Type.SEARCH`：用于输入搜索字符串的单行文本字段。换行符会从输入值中自动删除。

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## 字段值 {#field-value}

`TextField` 的值表示当前用户输入的字符串。在 webforJ 中，可以使用 `getValue()` 方法访问该值，或通过 `setValue(String)` 方法以编程方式更新。

```java
// 设置初始内容
textField.setValue("初始内容");

// 获取当前值
String text = textField.getValue();
```

如果在没有当前值的字段上使用 `getValue()` 方法，它将返回空字符串 (`""`)。

此行为与 HTML `<input type="text">` 元素通过 JavaScript 曝露其值的方式一致。

:::tip 结合值处理和验证
应用像 [pattern](#pattern-matching)、[minimum length](#setminlength) 或 [maximum length](#setmaxlength) 这样的约束，以定义何时值被视为有效。
:::

## 占位符文本 {#placeholder-text}

您可以使用 `setPlaceholder()` 方法为 `TextField` 设置占位符文本。当字段为空时，显示占位符文本，帮助提示用户在 `TextField` 中输入适当的内容。

## 选中文本 {#selected-text}

可以与 `TextField` 类进行交互，以检索用户选中的文本，并获取有关用户选择的信息。您可以使用 `getSelectedText()` 方法检索 `TextField` 中的选中文本。此行为通常与事件一起使用。

```java
TextField textField = new TextField("输入内容...");
Button getSelectionBtn = new Button("获取选中文本");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("选中文本: " + selected);
});
```

同样，可以使用 `getSelectionRange()` 方法检索 `TextField` 的当前选择范围。该方法返回一个 `SelectionRange` 对象，表示选中文本的起始和结束索引。

:::tip 使用 `getSelectedText()` 与事件负载
尽管您可以在事件处理程序中手动调用 `getSelectedText()`，但更有效的方法是使用事件负载中提供的选择数据，例如在 `SelectionChangeEvent` 中，以避免额外查找。
:::

## 模式匹配 {#pattern-matching}

您可以使用 `setPattern()` 方法通过正则表达式定义 `TextField` 的验证规则。设置模式会添加一个约束验证，要求输入值匹配指定的模式。

模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，由浏览器解释。`u`（Unicode）标志会在内部应用，以确保准确匹配 Unicode 代码点。请勿将模式括在正斜杠（`/`）中，因为这是不需要的，并会被视为字面字符。

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // 例如 ABC12
```

如果未提供模式或语法无效，则忽略验证规则。

:::tip 上下文帮助
使用 `TextField` 的复杂模式时，请考虑结合使用 `setLabel()`、`setHelperText()` 和 `setTooltipText()` 方法，以提供额外的提示和指导。
:::

## 最小和最大长度 {#minimum-and-maximum-length}

`TextField` 组件支持基于用户输入字符数的约束验证。这可以使用 `setMinLength()` 和 `setMaxLength()` 方法进行控制。使用这两个方法定义可接受输入长度的明确界限。

:::info 字符长度要求
默认情况下，当输入值超出范围时，字段会显示一条消息，指示用户是否需要缩短或延长输入。此消息可以使用 `setInvalidMessage()` 方法覆盖。
:::

### `setMinLength()` {#setminlength}

该方法设置字段被视为有效所需输入的**最小 UTF-16 代码单元数**。该值必须是整数，且不得超过配置的最大长度。

```java
textField.setMinLength(5); // 用户必须输入至少 5 个字符
```

如果输入的字符数少于所需的最小值，则输入将未通过约束验证。此规则仅在用户更改字段值时适用。

### `setMaxLength()` {#setmaxlength}

该方法设置文本字段中允许的**最大 UTF-16 代码单元数**。该值必须为 `0` 或更大。如果未设置或设置为无效值，则不强制执行最大限制。

```java
textField.setMaxLength(20); // 用户不能输入超过 20 个字符
```

如果输入长度超过最小长度，字段将未通过约束验证。与 `setMinLength()` 一样，此验证仅在用户更改值时触发。

## 最佳实践 {#best-practices}

以下部分概述了一些建议的 `TextField` 使用最佳实践。

- **提供清晰的标签和说明**：清晰标记 `TextField`，以指示用户应输入的信息类型。提供额外的说明文本或占位符值，以指导用户并设定对所需输入的预期。

- **拼写检查和自动补全**：在适用时，可以考虑使用 `setSpellCheck()` 进行拼写检查和/或在 `TextField` 中使用自动补全。这些功能可以帮助用户更快地输入信息并减少错误，通过根据以前的输入或预定义选项提供建议值。

- **无障碍使用**：以无障碍为目标使用 `TextField` 组件，遵守无障碍标准，例如适当标记、键盘导航支持以及与辅助技术的兼容性。确保残疾用户能够有效地与 `TextField` 进行交互。
