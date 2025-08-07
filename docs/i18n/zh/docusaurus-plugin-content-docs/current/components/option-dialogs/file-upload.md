---
sidebar_position: 20
title: File Upload
_i18n_hash: e25933325d4f0d5a7044a5e0776e3741
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

`FileUploadDialog` 提供了一种选择和上传文件的方式，使用户能够提交文档、图像或应用程序所需的其他文件类型。

## 结果 {#result}

`FileUploadDialog` 返回一个 `UploadedFile` 对象，该对象包含有关上传文件的信息，例如文件名、大小和内容。如果用户在未选择文件的情况下关闭对话框，则结果将为 `null`。

:::important
返回的字符串将由 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### 移动上传的文件 {#moving-uploaded-files}

默认情况下，webforJ 将上传的文件存储在一个临时文件夹中，该文件夹会定期清理。如果您不将文件移动到其他地方，它将被删除。要移动文件，请使用 `move` 方法并指定目标路径。

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("选择要上传的文件");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... 对文件执行某些操作
} catch (IOException e) {
    // 处理异常
}
```
:::tip 已清理的客户端名称
使用 `getSanitizedClientName` 方法获取上传文件名称的已清理版本。此方法有助于防止安全风险，例如目录遍历攻击或文件名中的无效字符，确保您的文件存储系统的完整性和安全性。
:::

## 过滤器 {#filters}

`FileUploadDialog` 允许您设置过滤器，以限制可以选择上传的文件类型。您可以使用 `setFilters(List<FileChooserFilter> filters)` 方法配置过滤器。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "上传文件", 
    Arrays.asList(new FileChooserFilter("文本文件", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning 过滤器验证
服务器不会对上传的文件进行过滤器验证。过滤器仅在用户界面中应用，以指导用户的选择。您必须实现服务器端验证，以确保上传的文件符合您应用程序的要求。
:::

## 最大大小 {#max-size}

可以设置上传的最大文件大小，以确保用户不会上传您的应用程序无法处理的过大文件。您可以使用 `setMaxFileSize(long maxSize)` 方法进行配置，其中 maxSize 以字节为单位指定。

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // 设置最大大小为 2 MB
```

## 国际化 (i18n) {#internationalization-i18n}

组件中的标题、描述、标签和消息可以通过 `FileUploadI18n` 类完全自定义。这种灵活性使您可以根据特定的本地化要求或个性化偏好调整对话框界面。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("上传文件");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("上传");
i18n.setCancel("取消");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**：确保提示信息清楚地解释了用户被要求上传什么。
2. **适当的过滤器**：设置与所需文件类型匹配的文件过滤器，以确保用户上传相关文件。
3. **合逻辑的初始路径**：设置为用户提供有用起点的初始路径，以便选择文件。
4. **限制目录导航**：在必要时，将对话框限制在特定目录，以防止用户导航到未经授权的区域。
5. **一致的主题**：将对话框和上传字段的主题与您的应用程序设计保持一致，以提升用户体验。
6. **减少过度使用**：适度使用文件上传对话框，以避免用户沮丧。仅在需要特定用户文件上传的操作中保留它们。
