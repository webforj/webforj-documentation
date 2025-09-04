---
sidebar_position: 3
title: Downloading Files
---

webforJ provides methods to initiate file downloads, making it easy to deliver files to users through the browser. The <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> class offers several ways to download files, whether they come from input streams, byte arrays, physical files, or resources.

## Downloading files using `InputStream` {#downloading-files-using-inputstream}

The <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> method allows you to send a file to the client using an input stream. This is particularly useful when the file content is generated dynamically or read from an external source.

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream**: The input stream representing the file content.
- **fileName**: The name under which the file will be downloaded by the client.

## Downloading files using byte arrays {#downloading-files-using-byte-arrays}

The <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> method allows downloading files using a byte array that represents the file's content. This method is useful when file content is generated or processed in memory.

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content**: The byte array representing the file content.
- **fileName**: The name of the downloaded file.

## Downloading physical files {#downloading-physical-files}

The <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> method is used to download a file that exists on the server.

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

## Downloading resources {#downloading-resources}

The <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> method enables downloading resources located within the app's context or specified by a path.

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path**: The path to a physical file or a [context URL](./assets-protocols#the-context-protocol).
- **fileName**: The name under which the file will be downloaded by the client.