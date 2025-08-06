---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 49bec97825977b7d05e97522debfa0d8
---
webforJ proporciona métodos para iniciar descargas de archivos, facilitando la entrega de archivos a los usuarios a través del navegador. La <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Página</JavadocLink> clase ofrece varias maneras de descargar archivos, ya sea que provengan de flujos de entrada, arreglos de bytes, archivos físicos o recursos.

## Descargando archivos usando `InputStream` {#downloading-files-using-inputstream}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> te permite enviar un archivo al cliente usando un flujo de entrada. Esto es particularmente útil cuando el contenido del archivo se genera dinámicamente o se lee de una fuente externa.

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream**: El flujo de entrada que representa el contenido del archivo.
- **fileName**: El nombre bajo el cual el archivo será descargado por el cliente.

## Descargando archivos usando arreglos de bytes {#downloading-files-using-byte-arrays}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> permite descargar archivos usando un arreglo de bytes que representa el contenido del archivo. Este método es útil cuando el contenido del archivo se genera o procesa en memoria.

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content**: El arreglo de bytes que representa el contenido del archivo.
- **fileName**: El nombre del archivo descargado.

## Descargando archivos físicos {#downloading-physical-files}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> se usa para descargar un archivo que existe en el servidor.

```java
File file = new File("path/to/image.png");
Page.getCurrent().download(file, "image.png");
```

```java
// En este caso, el nombre original del archivo será usado para la descarga.
Page.getCurrent().download(new File("path/to/report.pdf"));
```

- **file**: El archivo físico que debe ser descargado.
- **fileName**: El nombre del archivo tal como aparece al cliente.

## Descargando recursos {#downloading-resources}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> permite descargar recursos ubicados dentro del contexto de la aplicación o especificados por una ruta.

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path**: La ruta a un archivo físico o a una [URL de contexto](./assets-protocols#the-context-protocol).
- **fileName**: El nombre bajo el cual el archivo será descargado por el cliente.
