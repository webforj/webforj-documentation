---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: 71ebfc077bb8042752cbfea71a971e47
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

`TextField`组件允许用户在单行中输入和编辑文本。您可以配置该字段以显示特定的虚拟键盘，例如数字键盘、电子邮件输入、电话号码输入或URL输入。该组件还提供内置验证，以拒绝不符合指定类型的值。

## 用法 {#usages}

`TextField`适用于需要文本输入或编辑的广泛场景。以下是使用`TextField`的一些示例：

1. **表单输入**：`TextField`通常用于表单中捕获用户输入，例如姓名、地址或评论。当输入通常短到可以适应一行时，最好在应用中使用`TextField`。

2. **搜索功能**：`TextField`可用于提供搜索框，允许用户输入搜索查询。

3. **文本编辑**：`TextField`非常适合需要文本编辑或内容创建的应用，例如文档编辑器、聊天应用或记笔记应用。

## 类型 {#types}

您可以使用`setType()`方法指定`TextField`的类型。同样，您可以使用`getType()`方法检索类型，该方法将返回一个枚举值。

- `Type.TEXT`：这是`TextField`的默认类型，并自动从输入值中删除换行符。

- `Type.EMAIL`：此类型用于输入电子邮件地址。它根据标准电子邮件语法验证输入。此外，它为兼容的浏览器和设备提供一个动态键盘，通过包括常用键（如<kbd>.com</kbd>和<kbd>@</kbd>）简化输入过程。

  :::note
  此验证确认了电子邮件地址的格式，但并不能保证该电子邮件存在。
  :::

- `Type.TEL`：此类型用于输入电话号码。在某些设备上，字段将显示电话键盘。

- `Type.URL`：此类型用于输入URL。它验证用户输入的URL是否包含方案和域名，例如：https://webforj.com。此外，它为兼容的浏览器和设备提供一个动态键盘，通过包括常用键（如<kbd>:</kbd>、<kbd>/</kbd>和<kbd>.com</kbd>）简化输入过程。

- `Type.SEARCH`：一个单行文本字段，用于输入搜索字符串。输入值中的换行符将被自动删除。

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## 字段值 {#field-value}

`TextField`的值表示当前用户输入的字符串。在webforJ中，可以使用`getValue()`方法访问，或通过`setValue(String)`方法进行程序更新。

```java
//设置初始内容
textField.setValue("初始内容");

//检索当前值
String text = textField.getValue();
```

如果在没有当前值的字段上使用`getValue()`方法，它将返回空字符串（`""`）。

这种行为与HTML `<input type="text">`元素通过JavaScript公开其值的方式一致。

:::tip 结合值处理和验证
应用约束，如[模式](#pattern-matching)、[最小长度](#setminlength)或[最大长度](#setmaxlength)，以定义何时值被视为有效。 
:::

## 占位符文本 {#placeholder-text}

您可以使用`setPlaceholder()`方法为`TextField`设置占位符文本。当字段为空时，会显示占位符文本，帮助提示用户在`TextField`中输入适当的内容。

## 选定文本 {#selected-text}

可以与`TextField`类交互，以检索用户选定的文本，并获取用户选择的信息。您可以使用`getSelectedText()`方法在`TextField`中检索选定的文本。这种行为通常与事件一起使用。

```java
TextField textField = new TextField("输入内容...");
Button getSelectionBtn = new Button("获取选定文本");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("选定文本: " + selected);
});
```

同样，可以使用`getSelectionRange()`方法检索`TextField`的当前选择范围。这将返回一个`SelectionRange`对象，表示所选文本的起始和结束索引。

:::tip 使用`getSelectedText()`与事件有效负载
虽然您可以在事件处理程序中手动调用`getSelectedText()`，但使用事件有效负载中提供的选择数据（例如在`SelectionChangeEvent`中）更为高效，以避免额外的查找。
:::

## 模式匹配 {#pattern-matching}

您可以使用`setPattern()`方法使用正则表达式定义`TextField`的验证规则。设置模式会添加约束验证，要求输入值匹配指定的模式。

该模式必须是有效的[JavaScript正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，由浏览器解释。内部应用`u`（Unicode）标志，以确保准确匹配Unicode代码点。不要将模式包裹在斜杠(`/`)中，因为这些不是必需的，并将被视为文字字符。

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // 例如 ABC12
```

如果未提供模式或语法无效，则验证规则将被忽略。

:::tip 上下文帮助
在为`TextField`使用复杂模式时，可以考虑使用`setLabel()`、`setHelperText()`和`setTooltipText()`方法的组合，以提供额外的提示和指导。
:::

## 最小和最大长度 {#minimum-and-maximum-length}

`TextField`组件支持基于用户输入的字符数的约束验证。您可以使用`setMinLength()`和`setMaxLength()`方法控制这一点。使用这两个方法来定义可接受输入长度的明确边界。

:::info 长度要求
默认情况下，当输入值超出范围时，该字段会显示一条消息，指示用户是否需要缩短或延长其输入。此消息可以通过`setInvalidMessage()`方法覆盖。
:::

### `setMinLength()` {#setminlength}

此方法设置字段被视为有效所需输入的**最小UTF-16代码单元数**。该值必须是整数，并且不能超过配置的最大长度。

```java
textField.setMinLength(5); // 用户必须至少输入5个字符
```

如果输入的字符数少于最小要求，则输入将不通过约束验证。此规则仅在用户更改字段值时适用。

### `setMaxLength()` {#setmaxlength}

此方法设置文本字段中允许的**最大UTF-16代码单元数**。该值必须为`0`或更大。如果未设置或设置为无效值，则不强制执行任何最大值。

```java
textField.setMaxLength(20); // 用户不能输入超过20个字符
```

如果输入长度超过最小长度，则字段不通过约束验证。与`setMinLength()`一样，此验证仅在用户更改值时触发。

## 最佳实践 {#best-practices}

以下部分概述了使用`TextField`的一些建议最佳实践。

- **提供清晰的标签和说明**：清晰标记`TextField`以指示用户应输入的信息类型。提供额外的说明文本或占位符值，以引导用户并设置对所需输入的期望。

- **拼写检查和自动完成**：在适用的情况下，考虑使用`setSpellCheck()`进行拼写检查和/或在`TextField`中使用自动完成。这些功能可以帮助用户更快地输入信息，并通过提供基于先前输入或预定义选项的建议值来减少错误。

- **无障碍性**：考虑到无障碍性，使用`TextField`组件，遵循适当的标签、键盘导航支持和与辅助技术的兼容等无障碍标准。确保残障用户能够有效地与`TextField`进行交互。
