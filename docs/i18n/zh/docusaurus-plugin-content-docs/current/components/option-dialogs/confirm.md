---
title: Confirm
sidebar_position: 5
_i18n_hash: 712808f446f16655074e93cda2231286
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

一个 `ConfirmDialog` 是一个模态对话框，旨在允许用户从最多 3 个选项中选择一个。对话框在用户与其交互或因超时关闭之前阻止应用程序执行。

<!-- INTRO_END -->

## 用法 {#usages}

`ConfirmDialog` 提供了一种方式来询问用户确认或在多个选项之间进行选择，例如 `是/否` 或 `确定/取消`，确保他们确认自己的行为。

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## 类型 {#types}

### 选项类型 {#option-type}

`ConfirmDialog` 支持以下选项类型，决定在对话框中显示的按钮：

1. **`OK`**: 显示一个 `OK` 按钮。
2. **`OK_CANCEL`**: 显示 `OK` 和 `Cancel` 按钮。
3. **`ABORT_RETRY_IGNORE`**: 显示 `Abort`、`Retry` 和 `Ignore` 按钮。
4. **`YES_NO_CANCEL`**: 显示 `Yes`、`No` 和 `Cancel` 按钮。
5. **`YES_NO`**: 显示 `Yes` 和 `No` 按钮。
6. **`RETRY_CANCEL`**: 显示 `Retry` 和 `Cancel` 按钮。
7. **`CUSTOM`**: 显示指定的自定义按钮。

### 消息类型 {#message-type}

`ConfirmDialog` 支持以下消息类型。当你配置一种类型时，对话框在消息旁显示一个图标，并且对话框的主题根据 webforJ 设计系统规则更新。

1. `PLAIN`: 显示没有图标的消息，使用默认主题。
2. `ERROR`: 在消息旁显示错误图标，并应用错误主题。
3. `QUESTION`: 在消息旁显示问号图标，使用主主题。
4. `WARNING`: 在消息旁显示警告图标，并应用警告主题。
5. `INFO`: 在消息旁显示信息图标，使用信息主题。

在以下示例中，代码配置了一个类型为 `CUSTOM` 的确认对话框，带有自定义标题和消息。

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## 结果 {#result}

`ConfirmDialog` 根据用户与对话框的交互返回结果。该结果指示用户点击了哪个按钮或对话框是否因超时而被关闭。

:::important
结果将从 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

`ConfirmDialog.Result` 枚举包括以下可能的结果：

1. **`OK`**: 用户点击了 `OK` 按钮。
2. **`CANCEL`**: 用户点击了 `CANCEL` 按钮。
3. **`YES`**: 用户点击了 `YES` 按钮。
4. **`NO`**: 用户点击了 `NO` 按钮。
5. **`ABORT`**: 用户点击了 `ABORT` 按钮。
6. **`RETRY`**: 用户点击了 `RETRY` 按钮。
7. **`IGNORE`**: 用户点击了 `IGNORE` 按钮。
8. **`FIRST_CUSTOM_BUTTON`**: 用户点击了第一个自定义按钮。
9. **`SECOND_CUSTOM_BUTTON`**: 用户点击了第二个自定义按钮。
10. **`THIRD_CUSTOM_BUTTON`**: 用户点击了第三个自定义按钮。
11. **`TIMEOUT`**: 对话框超时。
12. **`UNKNOWN`**: 未知结果，通常用作默认或错误状态。

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("更改已被丢弃", "已丢弃", "知道了");
} else {
  OptionDialog.showMessageDialog(
    "更改已保存", "已保存", "知道了", MessageDialog.MessageType.INFO);
}
```

## 默认按钮 {#default-button}

`ConfirmDialog` 允许你指定一个在对话框显示时预选的默认按钮。这增强了用户体验，因为用户可以通过按下 <kbd>Enter</kbd> 键快速确认建议的操作。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "您确认吗？", "确认", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // 第二个按钮
dialog.show();
```

## 按钮文本 {#buttons-text}

您可以使用 `setButtonText(ConfirmDialog.Button button, String text)` 方法配置按钮的文本。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "您确认吗？", "确认", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "当然");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "不行");
dialog.show();
```

## HTML 处理 {#html-processing}

默认情况下，确认对话框处理和渲染 HTML 内容。你可以通过配置对话框以显示原始文本来关闭此功能。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>您确认吗？</b>", "确认",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`ConfirmDialog` 允许您设置超时时间，超时后对话框会自动关闭。此功能对于不需要用户立即交互的非关键确认或操作非常有用。

您可以使用 `setTimeout(int timeout)` 方法配置对话框的超时。超时持续时间以秒为单位。如果在指定时间内没有用户交互，则对话框会自动关闭。

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "您确认吗？", "确认", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "您决定的时间太长了", "超时", "知道了",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "您点击了是", "是", "知道了",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "您点击了否", "否", "知道了",
        MessageDialog.MessageType.INFO);
    break;
}
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**: 确保提示消息清楚地解释用户被要求确认的操作。避免模糊。
2. **适当的选项类型**: 选择与操作上下文匹配的选项类型。对于简单的是/否决策，使用直接的选项。对于更复杂的场景，提供额外的按钮，如“取消”，以便用户可以在不做出选择的情况下退回。
3. **逻辑默认按钮**: 设置与最可能或推荐用户操作相符的默认按钮，以简化决策过程。
4. **一致的主题**: 将对话框和按钮主题与应用的设计对齐，以实现一致的用户体验。
5. **审慎使用超时**: 对于非关键确认设置超时，确保用户有足够的时间阅读和理解提示。
6. **减少过度使用**: 明智地使用确认对话框，以避免用户沮丧。将其保留用于需要用户明确确认的关键操作。
