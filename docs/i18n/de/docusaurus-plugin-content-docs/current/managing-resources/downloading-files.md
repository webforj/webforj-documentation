---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 49bec97825977b7d05e97522debfa0d8
---
webforJ bietet Methoden zum Initiieren von Datei-Downloads, was es einfach macht, Dateien über den Browser an Benutzer zu liefern. Die <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> Klasse bietet mehrere Möglichkeiten, Dateien herunterzuladen, unabhängig davon, ob sie aus Eingabeströmen, Byte-Arrays, physischen Dateien oder Ressourcen stammen.

## Dateien mit `InputStream` herunterladen {#downloading-files-using-inputstream}

Die <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> Methode ermöglicht es Ihnen, eine Datei an den Client über einen Eingabestream zu senden. Dies ist besonders nützlich, wenn der Dateiinhalts dynamisch generiert oder aus einer externen Quelle gelesen wird.

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream**: Der Eingabestream, der den Dateiinhalt darstellt.
- **fileName**: Der Name, unter dem die Datei vom Client heruntergeladen wird.

## Dateien mit Byte-Arrays herunterladen {#downloading-files-using-byte-arrays}

Die <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> Methode ermöglicht das Herunterladen von Dateien mithilfe eines Byte-Arrays, das den Inhalt der Datei darstellt. Diese Methode ist nützlich, wenn der Dateiinhalt im Speicher generiert oder verarbeitet wird.

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content**: Das Byte-Array, das den Dateiinhalt darstellt.
- **fileName**: Der Name der heruntergeladenen Datei.

## Physische Dateien herunterladen {#downloading-physical-files}

Die <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> Methode wird verwendet, um eine Datei herunterzuladen, die auf dem Server existiert.

```java
File file = new File("path/to/image.png");
Page.getCurrent().download(file, "image.png");
```

```java
// In diesem Fall wird der ursprüngliche Dateiname für den Download verwendet.
Page.getCurrent().download(new File("path/to/report.pdf"));
```

- **file**: Die physische Datei, die heruntergeladen werden soll.
- **fileName**: Der Name der Datei, wie sie dem Client angezeigt wird.

## Ressourcen herunterladen {#downloading-resources}

Die <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> Methode ermöglicht das Herunterladen von Ressourcen, die sich im Kontext der App befinden oder durch einen Pfad angegeben sind.

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path**: Der Pfad zu einer physischen Datei oder einer [Kontext-URL](./assets-protocols#the-context-protocol).
- **fileName**: Der Name, unter dem die Datei vom Client heruntergeladen wird.
