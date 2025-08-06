---
sidebar_position: 10
title: File Chooser
_i18n_hash: d0efdadb8ec1e44cfab2fb26f95efa0d
---
# 文件选择对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` 是一个模态对话框，旨在允许用户从服务器文件系统中选择文件或目录。该对话框会阻止应用程序执行，直到用户做出选择或关闭对话框。

```java
OptionDialog.showFileChooserDialog("选择一个文件");
```

## 用法 {#usages}

`FileChooserDialog` 提供了一种从文件系统中选择文件或目录的方法，使用户能够选择保存数据的目录或执行文件操作。

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## 结果 {#result}

`FileChooserDialog` 返回所选文件或目录的字符串。如果用户在不做出选择的情况下关闭对话框，结果将为 `null`。

:::info
返回的字符串将通过 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。 
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "选择一个文件", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("您选择了: " + result, "已做出选择");
} else {
    OptionDialog.showMessageDialog("未做出选择", "选择已取消");
}
```

## 选择模式 {#selection-mode}

`FileChooserDialog` 支持不同的选择模式，允许您根据具体需求调整选择方法：

1. **FILES**: 仅允许选择文件。
2. **DIRECTORIES**: 仅允许选择目录。
3. **FILES_AND_DIRECTORIES**: 允许选择文件和目录。

## 初始路径 {#initial-path}

`FileChooserDialog` 允许您指定一个初始路径，该对话框在显示时将打开到该路径。这可以为用户提供文件选择的起始点。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/home/user");
String result = dialog.show();
```

## 限制 {#restriction}

您可以使用 `setRestricted(boolean restricted)` 方法将对话框限制在特定目录，防止用户导航到其他地方。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## 过滤器 {#filters}

当选择模式为 `FILES` 时，`FileChooserDialog` 允许您设置过滤器，以限制列出的文件类型。您可以使用 `setFilters(List<FileChooserFilter> filters)` 方法配置过滤器。

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### 自定义过滤器 {#custom-filters}

您可以通过使用 `setCustomFilters(boolean customFilters)` 方法启用自定义过滤器功能，以允许用户添加自定义过滤器。自定义过滤器默认将保存在浏览器的本地存储中，并在对话框再次显示时恢复。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## 国际化 (i18n) {#internationalization-i18n}

组件中的标题、描述、标签和消息都可以使用 `FileChooserI18n` 类进行完全自定义。这种灵活性使您能够根据具体的本地化要求或个性化偏好来调整对话框界面。

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("选择一个文件", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("选择");
i18n.setCancel("取消");
dialog.setI18n(i18n);
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**: 确保提示消息清楚解释用户被要求选择的内容。
2. **适当的选择模式**: 选择与所需用户操作相匹配的选择模式，以确保准确和相关的选择。
3. **合逻辑的初始路径**: 设置提供用户有用起点的初始路径。
4. **限制目录导航**: 在必要时限制对话框到特定目录，以防止用户导航到未经授权的区域。
