---
sidebar_position: 3
title: Bestanden Downloaden
_i18n_hash: 013e64888b44110c108f80adf492be10
---
webforJ biedt methoden om bestandsdownloads te initiëren, waardoor het eenvoudig is om bestanden via de browser aan gebruikers te bezorgen. De <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> klasse biedt verschillende manieren om bestanden te downloaden, of ze nu komen van invoerstromen, byte-arrays, fysieke bestanden of bronnen.

## Bestanden downloaden met `InputStream` {#downloading-files-using-inputstream}

De <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> methode stelt je in staat om een bestand naar de client te sturen met behulp van een invoerstroom. Dit is bijzonder nuttig wanneer de inhoud van het bestand dynamisch wordt gegenereerd of van een externe bron wordt gelezen.

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream**: De invoerstroom die de inhoud van het bestand vertegenwoordigt.
- **fileName**: De naam waaronder het bestand door de client zal worden gedownload.

## Bestanden downloaden met byte-arrays {#downloading-files-using-byte-arrays}

De <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> methode maakt het mogelijk om bestanden te downloaden met behulp van een byte-array die de inhoud van het bestand vertegenwoordigt. Deze methode is nuttig wanneer de inhoud van het bestand in het geheugen wordt gegenereerd of verwerkt.

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content**: De byte-array die de inhoud van het bestand vertegenwoordigt.
- **fileName**: De naam van het gedownloade bestand.

## Fysieke bestanden downloaden {#downloading-physical-files}

De <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> methode wordt gebruikt om een bestand dat op de server bestaat te downloaden.

```java
File file = new File("path/to/image.png");
Page.getCurrent().download(file, "image.png");
```

```java
// In dit geval zal de oorspronkelijke bestandsnaam worden gebruikt voor de download.
Page.getCurrent().download(new File("path/to/report.pdf"));
```

- **file**: Het fysieke bestand dat gedownload moet worden.
- **fileName**: De naam van het bestand zoals het aan de client verschijnt.

## Bronnen downloaden {#downloading-resources}

De <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> methode stelt je in staat om bronnen te downloaden die zich binnen de context van de app bevinden of zijn gespecificeerd door een pad.

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path**: Het pad naar een fysiek bestand of een [context-URL](./assets-protocols#the-context-protocol).
- **fileName**: De naam waaronder het bestand door de client zal worden gedownload.
