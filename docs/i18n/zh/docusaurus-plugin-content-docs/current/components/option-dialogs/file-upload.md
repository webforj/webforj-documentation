---
title: File Upload
sidebar_position: 20
_i18n_hash: 0c52346e43f2f615464dde85f39d7cd0
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` 是一个模态对话框，旨在允许用户从本地文件系统上传文件。该对话框会阻止应用程序执行，直到用户选择要上传的文件或关闭对话框。

<!-- INTRO_END -->

## 使用方法 {#usages}

`FileUploadDialog` 提供了一种选择和上传文件的方法，使用户能够提交应用程序所需的文档、图像或其他文件类型。使用 `showFileUploadDialog()` 显示对话框并捕获上传的文件。

```java
UploadedFile result = OptionDialog.showFileUploadDialog("上传文件");
```

## 结果 {#result}

`FileUploadDialog` 返回一个 `UploadedFile` 对象，该对象包含有关上传文件的信息，例如其名称、大小和内容。如果用户在未选择文件的情况下关闭对话框，则结果将为 `null`。

:::important
结果字符串将从 `show()` 方法或相应的 `OptionDialog` 方法返回，如下所示。
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
  // ... 处理文件
} catch (IOException e) {
  // 处理异常
}
```
:::tip 已清理的客户端名称
使用 `getSanitizedClientName` 方法来获取上传文件名称的清理版本。该方法有助于防止安全风险，例如目录遍历攻击或文件名称中的无效字符，确保文件存储系统的完整性和安全性。
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
服务器不会根据过滤器验证上传的文件。过滤器仅在用户界面中应用，以指导用户的选择。您必须实现服务器端验证，以确保上传的文件符合应用程序的要求。
:::

## 最大大小 {#max-size}

可以设置文件上传的最大大小，以确保用户不会上传过大文件，以至于应用程序无法处理。此设置可以通过 `setMaxFileSize(long maxSize)` 方法配置，其中 maxSize 以字节为单位指定。

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // 将最大大小设置为 2 MB
```

## 国际化 (i18n) {#internationalization-i18n}

组件中的标题、描述、标签和消息可通过 `FileUploadI18n` 类完全自定义。这种灵活性使您能够调整对话框界面，以满足特定的本地化要求或个性化偏好。

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("上传文件");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("上传");
i18n.setCancel("取消");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**：确保提示消息清楚解释用户被要求上传的内容。
2. **适当的过滤器**：设置与所需文件类型匹配的文件过滤器，以确保用户上传相关文件。
3. **逻辑初始路径**：设置初始路径，为用户的文件选择提供有用的起点。
4. **限制目录导航**：在必要时限制对话框到特定目录，以防止用户导航到未授权区域。
5. **一致主题**：将对话框和上传字段的主题与您的应用程序设计对齐，以实现一致的用户体验。
6. **最小化过度使用**：适度使用文件上传对话框，以避免用户沮丧。将其保留用于需要特定用户文件上传的操作。
