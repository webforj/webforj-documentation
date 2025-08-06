---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 49bec97825977b7d05e97522debfa0d8
---
webforJ 提供了方法来启动文件下载，使得通过浏览器轻松将文件交付给用户。<JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> 类提供了多种下载文件的方式，无论文件来自输入流、字节数组、物理文件还是资源。

## 使用 `InputStream` 下载文件 {#downloading-files-using-inputstream}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> 方法允许你使用输入流将文件发送给客户端。当文件内容动态生成或从外部源读取时，这尤其有用。

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream**: 表示文件内容的输入流。
- **fileName**: 客户端下载文件时使用的文件名。

## 使用字节数组下载文件 {#downloading-files-using-byte-arrays}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> 方法允许使用字节数组下载文件，该数组表示文件的内容。当文件内容在内存中生成或处理时，此方法非常有用。

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content**: 表示文件内容的字节数组。
- **fileName**: 下载文件的名称。

## 下载物理文件 {#downloading-physical-files}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> 方法用于下载服务器上存在的文件。

```java
File file = new File("path/to/image.png");
Page.getCurrent().download(file, "image.png");
```

```java
// 在这种情况下，将使用原始文件名进行下载。
Page.getCurrent().download(new File("path/to/report.pdf"));
```

- **file**: 要下载的物理文件。
- **fileName**: 客户端看到的文件名称。

## 下载资源 {#downloading-resources}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> 方法使得下载位于应用上下文中的资源或通过路径指定的资源成为可能。

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path**: 物理文件的路径或 [上下文 URL](./assets-protocols#the-context-protocol)。
- **fileName**: 客户端下载文件时使用的文件名。
