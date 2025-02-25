---
sidebar_position: 3
title: Downloading Files
---

webforJ provides methods to initiate file downloads, making it easy to deliver files to users through the browser. The [`Page`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/Page.html) class offers several ways to download files, whether they come from input streams, byte arrays, physical files, or resources.

## Downloading files using `InputStream`

The [`download(InputStream inputStream, String fileName)`](https://javadoc.io/static/com.webforj/webforj-foundation/24.21/com/webforj/Page.html#download(java.io.InputStream,java.lang.String)) method allows you to send a file to the client using an input stream. This is particularly useful when the file content is generated dynamically or read from an external source.

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream**: The input stream representing the file content.
- **fileName**: The name under which the file will be downloaded by the client.

## Downloading files using Byte Arrays

The [`download(byte[] content, String fileName)`](https://javadoc.io/static/com.webforj/webforj-foundation/24.21/com/webforj/Page.html#download(byte%5B%5D,java.lang.String)) method allows downloading files using a byte array that represents the file's content. This method is useful when file content is generated or processed in memory.

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content**: The byte array representing the file content.
- **fileName**: The name of the downloaded file.

## Downloading physical files

The [`download(File file, String fileName)`](https://javadoc.io/static/com.webforj/webforj-foundation/24.21/com/webforj/Page.html#download(java.io.File,java.lang.String)) method is used to download a file that exists on the server.

```java
File file = new File("path/to/image.png");
Page.getCurrent().download(file, "image.png");
```

```java
// In this case, the original file name will be used for the download.
Page.getCurrent().download(new File("path/to/report.pdf"));
```

- **file**: The physical file to be downloaded.
- **fileName**: The name of the file as it appears to the client.

## Downloading resources

The [`download(String path, String fileName)`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/Page.html) method enables downloading resources located within the app's context or specified by a path.

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path**: The path to a physical file or a [context URL](./assets-protocols#the-context-protocol).
- **fileName**: The name under which the file will be downloaded by the client.