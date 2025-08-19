---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 013e64888b44110c108f80adf492be10
---
webforJ proporciona métodos para iniciar descargas de archivos, facilitando la entrega de archivos a los usuarios a través del navegador. La <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> clase ofrece varias formas de descargar archivos, ya sea que provengan de flujos de entrada, matrices de bytes, archivos físicos o recursos.

## Descargando archivos usando `InputStream` {#downloading-files-using-inputstream}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> te permite enviar un archivo al cliente utilizando un flujo de entrada. Esto es particularmente útil cuando el contenido del archivo se genera dinámicamente o se lee de una fuente externa.

```java
InputStream inputStream = new FileInputStream("ruta/a/ejemplo.pdf");
Page.getCurrent().download(inputStream, "ejemplo.pdf");
```

- **inputStream**: El flujo de entrada que representa el contenido del archivo.
- **fileName**: El nombre bajo el cual el archivo será descargado por el cliente.

## Descargando archivos usando matrices de bytes {#downloading-files-using-byte-arrays}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> permite descargar archivos utilizando una matriz de bytes que representa el contenido del archivo. Este método es útil cuando el contenido del archivo se genera o procesa en memoria.

```java
byte[] content = Files.readAllBytes(Paths.get("ruta/a/documento.txt"));
Page.getCurrent().download(content, "documento.txt");
```

- **content**: La matriz de bytes que representa el contenido del archivo.
- **fileName**: El nombre del archivo descargado.

## Descargando archivos físicos {#downloading-physical-files}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> se utiliza para descargar un archivo que existe en el servidor.

```java
File file = new File("ruta/a/imagen.png");
Page.getCurrent().download(file, "imagen.png");
```

```java
// En este caso, se utilizará el nombre original del archivo para la descarga.
Page.getCurrent().download(new File("ruta/a/informe.pdf"));
```

- **file**: El archivo físico que se va a descargar.
- **fileName**: El nombre del archivo tal como aparece al cliente.

## Descargando recursos {#downloading-resources}

El método <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> permite descargar recursos ubicados dentro del contexto de la aplicación o especificados por un camino.

```java
Page.getCurrent().download("context://resources/manual.pdf", "manual-del-usuario.pdf");
```

- **path**: La ruta a un archivo físico o una [URL de contexto](./assets-protocols#the-context-protocol).
- **fileName**: El nombre bajo el cual el archivo será descargado por el cliente.
