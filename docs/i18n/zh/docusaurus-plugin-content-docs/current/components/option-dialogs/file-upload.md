---
sidebar_position: 20
title: File Upload
_i18n_hash: 1218c7729c6cb025d2d6b4312bd95658
---
# 文件上传对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` 是一个模态对话框，旨在允许用户从本地文件系统上传文件。该对话框会阻止应用程序执行，直到用户选择要上传的文件或关闭对话框。

```java
UploadedFile result = OptionDialog.showFileUploadDialog("上传文件");
```

## 用法 {#usages}

`FileUploadDialog` 提供了一种选择和上传文件的方式，使用户能够提交应用程序所需的文档、图像或其他文件类型。

## 结果 {#result}

`FileUploadDialog` 返回一个 `UploadedFile` 对象，该对象包含有关上传文件的信息，例如其名称、大小和内容。如果用户在关闭对话框时没有选择文件，则结果将为 `null`。

:::important
结果字符串将从 `show()` 方法或下面所示的等效 `OptionDialog` 方法返回。
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### 移动上传的文件 {#moving-uploaded-files}

默认情况下，webforJ 将上传的文件存储在临时文件夹中，该文件夹会定期清理。如果不将文件移动到其他地方，它将被删除。要移动文件，使用 `move` 方法并指定目标路径。

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("选择要上传的文件");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... 对文件执行某些操作
} catch (IOException e) {
    // 处理异常
}
```
:::tip 客户端名称清理 
使用 `getSanitizedClientName` 方法获取上传文件名称的清理版本。此方法有助于防止安全风险，如目录遍历攻击或文件名中的无效字符，确保您的文件存储系统的完整性和安全性。
:::

## 过滤器 {#filters}

`FileUploadDialog` 允许您设置过滤器，以限制可以选择的文件类型进行上传。您可以使用 `setFilters(List<FileChooserFilter> filters)` 方法配置过滤器。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "上传文件", 
    Arrays.asList(new FileChooserFilter("文本文件", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning 过滤器验证 
服务器不会对上传的文件进行过滤器验证。过滤器仅在 UI 中应用，以指导用户的选择。您必须在服务器端实现验证，以确保上传的文件满足您应用程序的要求。
:::

## 最大大小 {#max-size}

可以设置上传的最大文件大小，以确保用户不上传超出您应用程序处理能力的文件。可以使用 `setMaxFileSize(long maxSize)` 方法进行配置，其中 maxSize 以字节为单位指定。

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // 将最大大小设置为 2 MB
```

## 国际化 (i18n) {#internationalization-i18n}

组件中的标题、描述、标签和消息可以使用 `FileUploadI18n` 类进行完全自定义。这种灵活性使您能够根据具体的本地化需求或个性化偏好调整对话框界面。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("上传文件");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("上传");
i18n.setCancel("取消");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**: 确保提示消息清晰地解释用户需要上传的内容。
2. **适当的过滤器**: 设置与所需文件类型匹配的文件过滤器，以确保用户上传相关文件。
3. **合理的初始路径**: 设置初始路径，为用户的文件选择提供有用的起始点。
4. **限制目录导航**: 在必要时限制对话框到特定目录，以防止用户导航到未经授权的区域。
5. **一致的主题**: 将对话框和上传字段主题与您的应用程序设计对齐，以实现一致的用户体验。
6. **减少过度使用**: 控制文件上传对话框的使用，以避免用户的挫败感。仅在需要特定用户文件上传的操作中保留它们。
