---
sidebar_position: 5
title: Confirm
_i18n_hash: 99babacee9e77d9376b00554e47d7ca3
---
# 确认对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` 是一个模态对话框，旨在允许用户从最多 3 个选项中选择一个。对话框在用户与之互动或因超时而关闭之前，阻止应用程序执行。

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "您确认吗？",
    "确认",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## 用法 {#usages}

`ConfirmDialog` 提供了一种方式来询问用户确认或者在多个选项中进行选择，例如 `是/否` 或 `确定/取消`，确保他们确认自己的操作。

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## 类型 {#types}

### 选项类型 {#option-type}

`ConfirmDialog` 支持以下选项类型，这些类型决定了对话框中显示的按钮：

1. **`OK`**：显示一个 `OK` 按钮。
2. **`OK_CANCEL`**：显示 `OK` 和 `取消` 按钮。
3. **`ABORT_RETRY_IGNORE`**：显示 `中止`、`重试` 和 `忽略` 按钮。
4. **`YES_NO_CANCEL`**：显示 `是`、`否` 和 `取消` 按钮。
5. **`YES_NO`**：显示 `是` 和 `否` 按钮。
6. **`RETRY_CANCEL`**：显示 `重试` 和 `取消` 按钮。
7. **`CUSTOM`**：显示自定义按钮，具体由用户指定。

### 消息类型 {#message-type}

`ConfirmDialog` 支持以下消息类型。当您配置一种类型时，对话框将在消息旁边显示一个图标，并根据 webforJ 设计系统规则更新对话框的主题。

1. `PLAIN`：在不带图标的情况下显示消息，使用默认主题。
2. `ERROR`：在消息旁边显示错误图标，并应用错误主题。
3. `QUESTION`：在消息旁边显示问号图标，使用主主题。
4. `WARNING`：在消息旁边显示警告图标，并应用警告主题。
5. `INFO`：在消息旁边显示信息图标，使用信息主题。

在以下示例中，代码配置了一个类型为 `CUSTOM` 的确认对话框，包含自定义标题和消息。

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## 结果 {#result}

`ConfirmDialog` 根据用户与对话框的交互返回一个结果。该结果指示用户单击了哪个按钮，或者是否因超时而关闭了对话框。

:::important
结果将从 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

`ConfirmDialog.Result` 枚举包含以下可能的结果：

1. **`OK`**：用户单击了 `OK` 按钮。
2. **`CANCEL`**：用户单击了 `CANCEL` 按钮。
3. **`YES`**：用户单击了 `YES` 按钮。
4. **`NO`**：用户单击了 `NO` 按钮。
5. **`ABORT`**：用户单击了 `ABORT` 按钮。
6. **`RETRY`**：用户单击了 `RETRY` 按钮。
7. **`IGNORE`**：用户单击了 `IGNORE` 按钮。
8. **`FIRST_CUSTOM_BUTTON`**：用户单击了第一个自定义按钮。
9. **`SECOND_CUSTOM_BUTTON`**：用户单击了第二个自定义按钮。
10. **`THIRD_CUSTOM_BUTTON`**：用户单击了第三个自定义按钮。
11. **`TIMEOUT`**：对话框超时。
12. **`UNKNOWN`**：未知结果，通常用作默认或错误状态。

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("更改已丢弃", "丢弃", "明白了");
} else {
    OptionDialog.showMessageDialog(
        "更改已保存", "已保存", "明白了", MessageDialog.MessageType.INFO);
}
```

## 默认按钮 {#default-button}

`ConfirmDialog` 允许您指定一个默认按钮，当对话框显示时该按钮将被预选。这增强了用户体验，提供了建议的操作，用户可以通过按 <kbd>Enter</kbd> 键快速确认。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "您确定吗？", "确认", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // 第二个按钮
dialog.show();
```

## 按钮文本 {#buttons-text}

您可以使用 `setButtonText(ConfirmDialog.Button button, String text)` 方法配置按钮的文本。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "您确定吗？", "确认", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "绝对是");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "不行");
dialog.show();
```

## HTML 处理 {#html-processing}

默认情况下，确认对话框处理并渲染 HTML 内容。您可以通过配置它以显示原始文本来关闭此功能。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>您确定吗？</b>", "确认",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`ConfirmDialog` 允许您设置一个超时持续时间，之后对话框会自动关闭。此功能对于不需要用户立即交互的非关键确认或操作非常有用。

您可以使用 `setTimeout(int timeout)` 方法为对话框配置超时。超时时间以秒为单位。如果指定的时间在没有用户交互的情况下过去，对话框将自动关闭。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "您确定吗？", "确认", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "您花了太长时间来决定", "超时", "明白了",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "您单击了是", "是", "明白了",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "您单击了否", "否", "明白了",
        MessageDialog.MessageType.INFO);
    break;
}
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**：确保提示消息清晰地解释了用户被要求确认的操作。避免模棱两可。
2. **适当的选项类型**：选择与操作上下文匹配的选项类型。对于简单的是/否决策，使用直接的选项。对于更复杂的场景，提供其他按钮，如“取消”，以允许用户在不做出选择的情况下后退。
3. **逻辑默认按钮**：设置一个与用户最可能或建议的操作对齐的默认按钮，以简化决策过程。
4. **一致的主题**：将对话框和按钮主题与应用程序的设计对齐，以实现无缝的用户体验。
5. **谨慎使用超时**：为非关键确认设置超时，以确保用户有足够的时间阅读和理解提示。
6. **减少过度使用**：谨慎使用确认对话框，以避免用户挫败感。仅为需要用户明确确认的关键操作保留它们。
