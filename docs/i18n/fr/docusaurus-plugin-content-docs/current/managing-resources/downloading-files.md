---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 013e64888b44110c108f80adf492be10
---
webforJ fournit des méthodes pour initier des téléchargements de fichiers, facilitant ainsi la livraison de fichiers aux utilisateurs via le navigateur. La <JavadocLink type="foundation" location="com/webforj/Page" code='true'>classe Page</JavadocLink> offre plusieurs façons de télécharger des fichiers, qu'ils proviennent de flux d'entrée, de tableaux d'octets, de fichiers physiques ou de ressources.

## Télécharger des fichiers à l'aide de `InputStream` {#downloading-files-using-inputstream}

La <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>méthode download(InputStream inputStream, String fileName)</JavadocLink> vous permet d'envoyer un fichier au client en utilisant un flux d'entrée. Cela est particulièrement utile lorsque le contenu du fichier est généré dynamiquement ou lu à partir d'une source externe.

```java
InputStream inputStream = new FileInputStream("path/to/sample.pdf");
Page.getCurrent().download(inputStream, "sample.pdf");
```

- **inputStream** : Le flux d'entrée représentant le contenu du fichier.
- **fileName** : Le nom sous lequel le fichier sera téléchargé par le client.

## Télécharger des fichiers à l'aide de tableaux d'octets {#downloading-files-using-byte-arrays}

La <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>méthode download(byte[] content, String fileName)</JavadocLink> permet de télécharger des fichiers en utilisant un tableau d'octets qui représente le contenu du fichier. Cette méthode est utile lorsque le contenu du fichier est généré ou traité en mémoire.

```java
byte[] content = Files.readAllBytes(Paths.get("path/to/document.txt"));
Page.getCurrent().download(content, "document.txt");
```

- **content** : Le tableau d'octets représentant le contenu du fichier.
- **fileName** : Le nom du fichier téléchargé.

## Télécharger des fichiers physiques {#downloading-physical-files}

La <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>méthode download(File file, String fileName)</JavadocLink> est utilisée pour télécharger un fichier qui existe sur le serveur.

```java
File file = new File("path/to/image.png");
Page.getCurrent().download(file, "image.png");
```

```java
// Dans ce cas, le nom de fichier original sera utilisé pour le téléchargement.
Page.getCurrent().download(new File("path/to/report.pdf"));
```

- **file** : Le fichier physique à télécharger.
- **fileName** : Le nom du fichier tel qu'il apparaît au client.

## Télécharger des ressources {#downloading-resources}

La <JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>méthode download(String path, String fileName)</JavadocLink> permet de télécharger des ressources situées dans le contexte de l'application ou spécifiées par un chemin.

```java
Page.getCurrent().download("context://resources/manual.pdf", "user-manual.pdf");
```

- **path** : Le chemin vers un fichier physique ou une [URL de contexte](./assets-protocols#the-context-protocol).
- **fileName** : Le nom sous lequel le fichier sera téléchargé par le client.
