---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 411ef7de8459154002fa2a620f073b0d
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` 是一个模态对话框，旨在允许用户从本地文件系统上传文件。此对话框在用户选择要上传的文件或关闭对话框之前会阻止应用程序执行。

<!-- INTRO_END -->

:::tip 内联组件
如果您想要一个直接在页面布局中渲染的文件选择器，而不是对话框，请考虑使用 [`Upload`](/docs/components/upload) 组件。
:::

## 使用场景 {#usages}

`FileUploadDialog` 提供选择和上传文件的方式，使用户能够提交文档、图像或应用程序所需的其他文件类型。使用 `showFileUploadDialog()` 显示对话框并捕获上传的文件。

```java
UploadedFile result = OptionDialog.showFileUploadDialog("上传文件");
```

## 结果 {#result}

`FileUploadDialog` 返回一个 `UploadedFile` 对象，该对象包含有关上传文件的信息，如其名称、大小和内容。如果用户在关闭对话框之前没有选择文件，结果将为 `null`。

:::important
生成的 `UploadedFile` 对象是从 `show()` 方法或等效的 `OptionDialog` 方法返回的，如下所示。
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### 移动上传的文件 {#moving-uploaded-files}

默认情况下，webforJ 将上传的文件存储在一个定期清理的临时文件夹中。如果不将文件移动到其他地方，它将被删除。要移动文件，请使用 `move` 方法并指定目标路径。

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("选择要上传的文件");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... 对文件执行某些操作
} catch (IOException e) {
  // 处理异常
}
```
:::tip 消毒的客户端名称
使用 `getSanitizedClientName` 方法获取上传文件名称的消毒版本。该方法有助于防止安全风险，例如目录遍历攻击或文件名中的无效字符，从而确保您的文件存储系统的完整性和安全性。
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
服务器不会根据过滤器验证上传的文件。过滤器仅在用户界面中应用，以引导用户的选择。您必须实现服务器端验证，以确保上传的文件符合您的应用程序要求。
:::

## 最大大小 {#max-size}

使用 `setMaxFileSize(Number maxSize)` 设置上传的最大大小（以字节为单位）。

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // 将最大大小设置为 2 MB
```

## 国际化 (i18n) {#internationalization-i18n}

组件内的标题、描述、标签和消息可以使用 `FileUploadI18n` 类完全自定义。这种灵活性使您能够根据特定的本地化要求或个性化偏好调整对话框界面。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("上传文件");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("上传");
i18n.setCancel("取消");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简明的提示**：确保提示消息清楚地解释用户被要求上传什么。
2. **适当的过滤器**：设置与所需文件类型匹配的文件过滤器，以确保用户上传相关文件。
3. **一致的主题**：使对话框和上传字段的主题与您的应用程序设计相匹配。
4. **减少滥用**：节制使用文件上传对话框，以避免用户沮丧。仅在需要特定用户文件上传的操作中保留它们。
