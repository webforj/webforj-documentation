---
sidebar_position: 5
title: Confirm
_i18n_hash: 1a5d5c10371b3d751853eb3c3bcbe66f
---
# 确认对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` 是一个模态对话框，旨在允许用户选择最多 3 个选项中的一个。对话框会阻止应用程序运行，直到用户与其交互或由于超时而关闭。

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "您确认吗？",
    "确认",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## 用法 {#usages}

`ConfirmDialog` 提供了一种询问用户确认或选择多个选项的方法，例如 `是/否` 或 `确定/取消`，确保他们承认并确认他们的操作。

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## 类型 {#types}

### 选项类型 {#option-type}

`ConfirmDialog` 支持以下选项类型，这决定了对话框中显示的按钮：

1. **`OK`**：显示一个 `OK` 按钮。
2. **`OK_CANCEL`**：显示 `OK` 和 `取消` 按钮。
3. **`ABORT_RETRY_IGNORE`**：显示 `中止`、`重试` 和 `忽略` 按钮。
4. **`YES_NO_CANCEL`**：显示 `是`、`否` 和 `取消` 按钮。
5. **`YES_NO`**：显示 `是` 和 `否` 按钮。
6. **`RETRY_CANCEL`**：显示 `重试` 和 `取消` 按钮。
7. **`CUSTOM`**：显示自定义按钮，如指定。

### 消息类型 {#message-type}

`ConfirmDialog` 支持以下消息类型。当您配置类型时，对话框会在消息旁显示图标，并且对话框的主题会根据 webforJ 设计系统规则更新。

1. `PLAIN`：以默认主题显示没有图标的消息。
2. `ERROR`：在消息旁边显示一个错误图标，并应用错误主题。
3. `QUESTION`：在消息旁边显示一个问号图标，使用主要主题。
4. `WARNING`：在消息旁边显示一个警告图标，并应用警告主题。
5. `INFO`：在消息旁边显示一个信息图标，使用信息主题。

在以下示例中，代码配置了一个类型为 `CUSTOM` 的确认对话框，并添加了自定义标题和消息。

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## 结果 {#result}

`ConfirmDialog` 根据用户与对话框的交互返回结果。该结果指示用户单击了哪个按钮，或者如果对话框由于超时而被关闭。

:::important
结果将从 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

`ConfirmDialog.Result` 枚举包括以下可能的结果：

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
    OptionDialog.showMessageDialog("更改已丢弃", "已丢弃", "知道了");
} else {
    OptionDialog.showMessageDialog(
        "更改已保存", "已保存", "知道了", MessageDialog.MessageType.INFO);
}
```

## 默认按钮 {#default-button}

`ConfirmDialog` 允许您指定一个默认按钮，该按钮在对话框显示时默认选中。这样可以通过按 <kbd>Enter</kbd> 键快速确认建议的操作，从而增强用户体验。

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
dialog.setButtonText(ConfirmDialog.Button.SECOND, "不");
dialog.show();
```

## HTML 处理 {#html-processing}

默认情况下，确认对话框处理和渲染 HTML 内容。您可以通过配置将其设置为显示原始文本来关闭此功能。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>您确定吗？</b>", "确认",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`ConfirmDialog` 允许您设置一个超时时间，超时后对话框将自动关闭。此功能对于不需要用户立即交互的非关键确认或操作非常有用。

您可以使用 `setTimeout(int timeout)` 方法配置对话框的超时。超时间隔以秒为单位。如果在指定时间内没有用户交互，对话框将自动关闭。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "您确定吗？", "确认", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "您花了太长时间做决定", "超时", "知道了",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "您单击了是", "是", "知道了",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "您单击了否", "否", "知道了",
        MessageDialog.MessageType.INFO);
    break;
}
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**：确保提示消息清楚地解释用户被要求确认的操作。避免模糊。
2. **适当的选项类型**：选择与操作上下文匹配的选项类型。对于简单的是/否决策，使用简单的选项。对于更复杂的场景，提供额外的按钮，如“取消”，以便用户在不做出选择的情况下退出。
3. **逻辑默认按钮**：设置一个符合最可能或推荐的用户操作的默认按钮，以简化决策过程。
4. **一致的主题**：使对话框和按钮主题与您的应用设计保持一致，以提供统一的用户体验。
5. **谨慎使用超时**：为非关键确认设置超时，确保用户有足够的时间阅读和理解提示。
6. **尽量减少过度使用**：审慎使用确认对话框，以避免用户沮丧。仅保留用于需要明确用户确认的关键操作。
