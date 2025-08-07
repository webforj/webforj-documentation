---
sidebar_position: 15
title: File Save
_i18n_hash: 477f92765ae539fd69106297baa9a0da
---
# 文件保存对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` 是一个模态对话框，旨在允许用户将文件保存到服务器文件系统的指定位置。该对话框在用户提供文件名并确认操作或取消对话框之前，阻止应用程序执行。

```java
OptionDialog.showFileSaveDialog("保存您的文件");
```

## 用法 {#usages}

`FileSaveDialog` 提供了一种简化的方法，将文件保存到文件系统，提供可配置的选项用于文件命名和处理现有文件。

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## 结果 {#result}

`FileSaveDialog` 返回所选路径作为字符串。如果用户取消对话框，结果将为 `null`。

:::important 对话框目的
此对话框实际上并不会导致任何文件被保存，而是返回用户所选择的文件名。
:::

:::info
结果字符串是从 `show()` 方法或等效的 `OptionDialog` 方法返回的，如下所示。
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "保存您的文件", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("已保存文件到: " + path, "选择的路径");
} else {
  OptionDialog.showMessageDialog("未选择路径", "选择的路径",
      MessageDialog.MessageType.ERROR);
}
```

## 已存在行为 {#exists-action}

`FileSaveDialog` 在指定名称的文件已存在时提供可配置的行为：

* **ACCEPT_WITHOUT_ACTION**: 选择被接受，无需额外用户操作。
* **ERROR_DIALOGUE**: 用户会看到错误对话框；选择不被允许。
* **CONFIRMATION_DIALOGUE**: 用户会看到提示确认的对话框。这是默认设置。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## 选择模式 {#selection-mode}

`FileSaveDialog` 支持不同的选择模式，允许您根据具体需要定制选择方法：

1. **FILES**: 仅允许选择文件。
2. **DIRECTORIES**: 仅允许选择目录。
3. **FILES_AND_DIRECTORIES**: 允许选择文件和目录。

## 初始路径 {#initial-path}

使用初始路径指定对话框打开的目录。这可以帮助用户在逻辑目录中开始其保存操作。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## 限制 {#restriction}

您可以使用 `setRestricted(boolean restricted)` 方法将对话框限制在特定目录中，从而防止用户导航到其他位置。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## 文件名 {#filename}

设置保存操作的默认文件名，以指导用户并减少错误。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
dialog.setName("report.xls");
String result = dialog.show();
```

## 国际化 (i18n) {#internationalization-i18n}

组件中的标题、描述、标签和消息可以使用 `FileSaveI18n` 类完全自定义。这确保对话框可以根据不同的本地化或个性化需求进行调整。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("选择");
i18n.setCancel("取消");
dialog.setI18n(i18n);
```

## 过滤器 {#filters}

`FileSaveDialog` 允许您设置过滤器，以限制可保存的文件类型，通过 `setFilters(List<FileSaveFilter> filters)` 方法实现。

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### 自定义过滤器 {#custom-filters}

您可以启用自定义过滤器，以允许用户使用 `setCustomFilters(boolean customFilters)` 方法定义自己的文件过滤器。过滤器默认保存在本地存储中，并在后续对话框调用时恢复。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## 最佳实践 {#best-practices}

* **预定义文件名**: 在适用情况下提供一个逻辑默认文件名。
* **确认覆盖**: 对于 `ExistsAction` 使用 `CONFIRMATION_DIALOGUE` 以防止意外覆盖。
* **直观的初始路径**: 设置与用户期望一致的初始路径。
* **国际化**: 自定义对话框文本以提升国际受众的可用性。
* **文件类型过滤器**: 利用过滤器限制文件类型，引导用户选择有效的文件扩展名。
