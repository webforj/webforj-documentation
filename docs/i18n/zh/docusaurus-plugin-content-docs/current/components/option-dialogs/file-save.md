---
title: File Save
sidebar_position: 15
_i18n_hash: 7cad72847c86a30f8ad6000a283a51c2
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` 是一个模态对话框，旨在允许用户将文件保存到服务器文件系统的指定位置。该对话框会阻止应用程序执行，直到用户提供文件名并确认操作或取消对话框。

<!-- INTRO_END -->

## 使用方法 {#usages}

`FileSaveDialog` 提供了一种简化的文件保存方法，提供用户可配置的文件命名和处理现有文件的选项。

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## 结果 {#result}

`FileSaveDialog` 返回所选路径作为字符串。如果用户取消对话框，结果将为 `null`。

:::important 对话框目的
该对话框实际上并不会导致任何文件被保存，而是返回用户选择的文件名。
:::

:::info
结果字符串是从 `show()` 方法或等效的 `OptionDialog` 方法返回的，如下所示。
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "保存您的文件", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("已保存文件到: " + path, "路径已选择");
} else {
  OptionDialog.showMessageDialog("未选择路径", "路径已选择",
      MessageDialog.MessageType.ERROR);
}
```

## 文件存在操作 {#exists-action}

`FileSaveDialog` 在文件名已存在时提供可配置的行为：

* **ACCEPT_WITHOUT_ACTION**：选择被接受，不需要额外用户操作。
* **ERROR_DIALOGUE**：用户会看到一个错误对话框；不允许该选择。
* **CONFIRMATION_DIALOGUE**：用户会看到一个请求确认的对话框。这是默认设置。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## 选择模式 {#selection-mode}

`FileSaveDialog` 支持不同的选择模式，允许您根据具体需求定制选择方法：

1. **FILES**：仅允许选择文件。
2. **DIRECTORIES**：仅允许选择目录。
3. **FILES_AND_DIRECTORIES**：允许同时选择文件和目录。

## 初始路径 {#initial-path}

使用初始路径指定对话框打开的目录。这有助于用户在逻辑目录中开始其保存操作。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## 限制 {#restriction}

您可以通过 `setRestricted(boolean restricted)` 方法限制对话框到特定目录，防止用户导航到外部。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "保存您的文件", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## 文件名 {#filename}

为保存操作设置默认文件名，以指导用户并减少错误。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
dialog.setName("report.xls");
String result = dialog.show();
```

## 国际化 (i18n) {#internationalization-i18n}

组件内的标题、描述、标签和消息可以通过 `FileSaveI18n` 类完全自定义。这确保对话框能够根据各种本地化或个性化需求进行调整。

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("保存您的文件");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("选择");
i18n.setCancel("取消");
dialog.setI18n(i18n);
```

## 过滤器 {#filters}

`FileSaveDialog` 允许您设置过滤器以限制可以保存的文件类型，使用 `setFilters(List<FileSaveFilter> filters)` 方法。

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

* **预定义文件名**：在适用时提供一个逻辑的默认文件名。
* **确认覆盖**：使用 `CONFIRMATION_DIALOGUE` 作为 `ExistsAction`，以防意外覆盖。
* **直观初始路径**：设置符合用户期望的初始路径。
* **国际化**：自定义对话框文本以增强国际用户的可用性。
* **文件类型过滤器**：利用过滤器限制文件类型，引导用户选择有效的文件扩展名。
