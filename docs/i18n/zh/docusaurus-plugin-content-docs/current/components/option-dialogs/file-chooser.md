---
title: File Chooser
sidebar_position: 10
_i18n_hash: 49a069004ead8d962b32e132183819e8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` 是一个模态对话框，旨在允许用户从服务器文件系统中选择文件或目录。该对话框在用户进行选择或关闭对话框之前会阻止应用程序执行。

<!-- INTRO_END -->

## 用法 {#usages}

`FileChooserDialog` 提供了一种从文件系统中选择文件或目录的方法，使用户能够选择用于保存数据的目录，或执行文件操作。

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## 结果 {#result}

`FileChooserDialog` 返回所选文件或目录的字符串。如果用户在不进行选择的情况下关闭对话框，结果将为 `null`。

:::info
结果字符串将从 `show()` 方法或等效的 `OptionDialog` 方法中返回，如下所示。
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "选择一个文件", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("您选择了: " + result, "选择已完成");
} else {
    OptionDialog.showMessageDialog("未做选择", "选择已取消");
}
```

## 选择模式 {#selection-mode}

`FileChooserDialog` 支持不同的选择模式，使您能够根据具体需求调整选择方式：

1. **FILES**: 仅允许选择文件。
2. **DIRECTORIES**: 仅允许选择目录。
3. **FILES_AND_DIRECTORIES**: 允许同时选择文件和目录。

## 初始路径 {#initial-path}

`FileChooserDialog` 允许您指定一个初始路径，当对话框显示时将打开该路径。这可以为用户的文件选择提供一个起始点。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/home/user");
String result = dialog.show();
```

## 限制 {#restriction}

您可以通过 `setRestricted(boolean restricted)` 方法限制对话框到特定目录，从而防止用户导航到其外部。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## 过滤器 {#filters}

当选择模式为 `FILES` 时，`FileChooserDialog` 允许您设置过滤器以限制列出的文件类型。您可以使用 `setFilters(List<FileChooserFilter> filters)` 方法配置过滤器。

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### 自定义过滤器 {#custom-filters}

您可以通过使用 `setCustomFilters(boolean customFilters)` 方法启用自定义过滤器功能，允许用户添加自定义过滤器。自定义过滤器默认将保存在浏览器的本地存储中，并在对话框再次显示时恢复。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## 国际化 (i18n) {#internationalization-i18n}

组件中的标题、描述、标签和消息都是完全可定制的，使用 `FileChooserI18n` 类。这种灵活性允许您根据特定的本地化要求或个性化偏好定制对话框界面。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("选择");
i18n.setCancel("取消");
dialog.setI18n(i18n);
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**: 确保提示信息清楚地解释用户需要选择的内容。
2. **适当的选择模式**: 选择与所需用户操作匹配的选择模式，以确保准确和相关的选择。
3. **逻辑初始路径**: 设置初始路径，使用户在选择时提供有用的起始点。
4. **限制目录导航**: 在必要时将对话框限制到特定目录，以防止用户导航到未授权的区域。
