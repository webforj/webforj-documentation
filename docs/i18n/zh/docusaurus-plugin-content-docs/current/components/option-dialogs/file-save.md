---
title: File Save
sidebar_position: 15
description: >-
  Prompt users for a save location with the FileSaveDialog, configuring initial
  path, default filename, and overwrite behavior.
_i18n_hash: 861257322ca233cfa9e0eed5e4d3aa95
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` 是一个模态对话框，旨在允许用户将文件保存到服务器文件系统的指定位置。在用户提供文件名并确认操作或取消对话框之前，该对话框会阻止应用程序执行。

<!-- INTRO_END -->

## 使用方法 {#usages}

`FileSaveDialog` 提供了一种简化的文件保存方法，提供用户可配置的文件命名和处理现有文件的选项。

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## 结果 {#result}

`FileSaveDialog` 返回选定路径作为字符串。如果用户取消对话框，结果将是 `null`。

:::important 对话框目的
此对话框不会实际导致任何文件被保存，而是返回用户选择的文件名。
:::

:::info
所得到的字符串是通过 `show()` 方法或等效的 `OptionDialog` 方法返回的，如下所示。
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "保存您的文件", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("选定路径: " + result, "路径已选择");
} else {
  OptionDialog.showMessageDialog("未选择路径", "路径已选择",
      MessageDialog.MessageType.ERROR);
}
```

## 存在的操作 {#exists-action}

当具有指定名称的文件已存在时，`FileSaveDialog` 提供可配置的行为：

* **ACCEPT_WITHOUT_ACTION**: 选择被接受，没有额外的用户操作。
* **ERROR_DIALOGUE**: 用户呈现一个错误对话框；选择不被允许。
* **CONFIRMATION_DIALOGUE**: 用户呈现一个请求确认的对话框。这是默认设置。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## 选择模式 {#selection-mode}

`FileSaveDialog` 支持不同的选择模式，允许您根据具体需求量身定制选择方法：

1. **FILES**: 仅允许选择文件。
2. **DIRECTORIES**: 仅允许选择目录。
3. **FILES_AND_DIRECTORIES**: 同时允许选择文件和目录。

## 初始路径 {#initial-path}

使用初始路径指定对话框打开的目录。这有助于用户在进行保存操作时从一个合乎逻辑的目录开始。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## 限制 {#restriction}

您可以通过 `setRestricted(boolean restricted)` 方法限制对话框到特定目录，防止用户导航到其外部。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## 文件名 {#filename}

设置默认文件名以指导用户并尽量减少错误。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
dialog.setName("report.xls");
String result = dialog.show();
```

## 国际化 (i18n) {#internationalization-i18n}

使用 `FileChooserI18n` 自定义对话框的标题、描述、标签和信息，以适应不同的区域或应用需求。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("选择");
i18n.setCancel("取消");
dialog.setI18n(i18n);
```

## 过滤器 {#filters}

`FileSaveDialog` 允许设置过滤器，以限制可以保存的文件类型，使用 `setFilters(List<FileChooserFilter> filters)` 方法。

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### 自定义过滤器 {#custom-filters}

您可以启用自定义过滤器，以允许用户定义自己的文件过滤器，使用 `setCustomFilters(boolean customFilters)` 方法。过滤器默认保存在本地存储中，并在后续对话框调用时恢复。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## 最佳实践 {#best-practices}

* **预定义文件名**: 在适用的情况下提供逻辑默认文件名。
* **确认覆盖**: 对于 `ExistsAction` 使用 `CONFIRMATION_DIALOGUE` 以防止意外覆盖。
* **直观的初始路径**: 设置与用户期望相符的初始路径。
* **国际化**: 自定义对话框文本以增强国际受众的可用性。
* **文件类型过滤器**: 利用过滤器限制文件类型，并引导用户朝有效文件扩展名前进。
