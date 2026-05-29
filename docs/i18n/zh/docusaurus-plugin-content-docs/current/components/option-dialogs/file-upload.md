---
title: File Upload
sidebar_position: 20
_i18n_hash: fc6515e16590085708ed61b3aedff9f1
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

一个 `FileUploadDialog` 是一个模态对话框，旨在允许用户从其本地文件系统上传文件。该对话框会阻止应用程序执行，直到用户选择要上传的文件或关闭对话框。

<!-- INTRO_END -->

## 用法 {#usages}

`FileUploadDialog` 提供一种选择和上传文件的方法，使用户能够提交应用程序所需的文档、图像或其他文件类型。使用 `showFileUploadDialog()` 来显示对话框并捕获上传的文件。

```java
UploadedFile result = OptionDialog.showFileUploadDialog("上传文件");
```

## 结果 {#result}

`FileUploadDialog` 返回一个 `UploadedFile` 对象，该对象包含有关上传文件的信息，例如其名称、大小和内容。如果用户在未选择文件的情况下关闭对话框，则结果将为 `null`。

:::important
结果字符串将从 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### 移动上传的文件 {#moving-uploaded-files}

默认情况下，webforJ 将上传的文件存储在一个定期清理的临时文件夹中。如果您不将文件移动到其他地方，它将被删除。要移动文件，请使用 `move` 方法并指定目标路径。

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("选择要上传的文件");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... 对文件进行一些操作
} catch (IOException e) {
  // 处理异常
}
```
:::tip 清理过的客户端名称
使用 `getSanitizedClientName` 方法获取上传文件名称的清理版本。此方法有助于防止安全风险，例如目录遍历攻击或文件名称中的无效字符，从而确保您的文件存储系统的完整性和安全性。
:::

## 过滤器 {#filters}

`FileUploadDialog` 允许您设置过滤器以限制可以选择上传的文件类型。您可以使用 `setFilters(List<FileChooserFilter> filters)` 方法配置过滤器。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "上传文件", 
  Arrays.asList(new FileChooserFilter("文本文件", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning 过滤器验证
服务器不会根据过滤器验证上传的文件。过滤器仅在UI中应用，以指导用户的选择。您必须实现服务器端验证，以确保上传的文件满足您的应用程序的要求。
:::

## 最大大小 {#max-size}

您可以设置上传的最大文件大小，以确保用户不会上传过大而无法处理的文件。这可以使用 `setMaxFileSize(long maxSize)` 方法进行配置，maxSize 以字节为单位指定。

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // 设置最大大小为 2 MB
```

## 国际化 (i18n) {#internationalization-i18n}

组件内的标题、描述、标签和消息可以完全使用 `FileUploadI18n` 类进行自定义。此灵活性使您能够根据特定的本地化要求或个性化偏好定制对话框界面。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("上传文件");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("上传");
i18n.setCancel("取消");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**：确保提示消息清晰解释用户被要求上传的内容。
2. **适当的过滤器**：设定匹配所需文件类型的文件过滤器，以确保用户上传相关文件。
3. **逻辑初始路径**：设置初始路径，为用户的文件选择提供有用的起点。
4. **限制目录导航**：在必要时限制对话框到特定目录，以防止用户导航到未授权区域。
5. **一致的主题**：使对话框和上传字段的主题与应用程序的设计保持一致，以实现统一的用户体验。
6. **减少过度使用**：谨慎使用文件上传对话框，以避免用户挫败感。仅保留用于需要特定用户文件上传的操作。
