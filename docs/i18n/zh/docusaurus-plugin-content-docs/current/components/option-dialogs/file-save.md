---
title: File Save
sidebar_position: 15
_i18n_hash: 728fd2b9211c993aed9b00abddb29b3e
---
```jsx
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` 是一个模态对话框，旨在允许用户将文件保存到服务器文件系统的指定位置。对话框在用户提供文件名并确认操作或取消对话框之前，会阻止应用程序执行。

<!-- INTRO_END -->

## 用法 {#usages}

`FileSaveDialog` 提供了一个简化的方法，用于将文件保存到文件系统，提供用户可配置的文件命名和处理现有文件的选项。

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## 结果 {#result}

`FileSaveDialog` 返回选定的路径作为字符串。如果用户取消对话框，结果将为 `null`。

:::important 对话框目的
此对话框实际上不会导致任何文件被保存，但返回用户选择的文件名。
:::

:::info
生成的字符串通过 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "保存您的文件", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("保存的文件路径: " + path, "选定路径");
} else {
  OptionDialog.showMessageDialog("未选择路径", "选定路径",
      MessageDialog.MessageType.ERROR);
}
```

## 存在的操作 {#exists-action}

`FileSaveDialog` 在指定名称的文件已存在时提供可配置的行为：

* **ACCEPT_WITHOUT_ACTION**: 选择被接受，无需额外的用户操作。
* **ERROR_DIALOGUE**: 用户将看到一个错误对话框；选择不被允许。
* **CONFIRMATION_DIALOGUE**: 用户将看到一个请求确认的对话框。这是默认设置。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## 选择模式 {#selection-mode}

`FileSaveDialog` 支持不同的选择模式，允许您根据特定需求调整选择方法：

1. **FILES**: 仅允许选择文件。
2. **DIRECTORIES**: 仅允许选择目录。
3. **FILES_AND_DIRECTORIES**: 允许选择文件和目录。

## 初始路径 {#initial-path}

使用初始路径指定对话框打开的目录。这可以帮助用户在保存操作中从逻辑目录开始。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## 限制 {#restriction}

您可以使用 `setRestricted(boolean restricted)` 方法将对话框限制在特定目录，防止用户导航到外部。

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

组件中的标题、描述、标签和消息可以使用 `FileSaveI18n` 类进行完全自定义。这确保对话框可以根据各种本地化或个性化要求进行调整。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("选择");
i18n.setCancel("取消");
dialog.setI18n(i18n);
```

## 过滤器 {#filters}

`FileSaveDialog` 允许您设置过滤器，以限制可以保存的文件类型，使用 `setFilters(List<FileSaveFilter> filters)` 方法。

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### 自定义过滤器 {#custom-filters}

您可以启用自定义过滤器，以允许用户使用 `setCustomFilters(boolean customFilters)` 方法定义自己的文件过滤器。默认情况下，过滤器保存在本地存储中，并在后续对话框调用时恢复。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## 最佳实践 {#best-practices}

* **预定义文件名**: 在适用的地方提供一个逻辑默认文件名。
* **确认覆盖**: 对于 `ExistsAction` 使用 `CONFIRMATION_DIALOGUE` 以防止意外覆盖。
* **直观的初始路径**: 设置与用户期望一致的初始路径。
* **国际化**: 自定义对话框文本以增强国际用户的可用性。
* **文件类型过滤器**: 利用过滤器限制文件类型，引导用户选择有效的文件扩展名。
```
