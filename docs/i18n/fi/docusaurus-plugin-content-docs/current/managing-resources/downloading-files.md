---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 49bec97825977b7d05e97522debfa0d8
---
webforJ tarjoaa menetelmiä tiedostojen lataamisen aloittamiseksi, mikä helpottaa tiedostojen toimittamista käyttäjille selaimen kautta. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>-luokka tarjoaa useita tapoja ladata tiedostoja, olipa ne sitten syötevirroista, tavutaulukoista, fyysisistä tiedostoista tai resursseista.

## Tiedostojen lataaminen `InputStream`-käyttöliittymällä {#downloading-files-using-inputstream}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink>-metodi mahdollistaa tiedoston lähettämisen asiakkaalle syötevirran avulla. Tämä on erityisen hyödyllistä, kun tiedoston sisältö luodaan dynaamisesti tai luetaan ulkoisesta lähteestä.

```java
InputStream inputStream = new FileInputStream("polku/näyte.pdf");
Page.getCurrent().download(inputStream, "näyte.pdf");
```

- **inputStream**: Syötevirta, joka edustaa tiedoston sisältöä.
- **fileName**: Nimi, jonka alla tiedosto ladataan asiakkaalle.

## Tiedostojen lataaminen tavutaulukoita käyttäen {#downloading-files-using-byte-arrays}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink>-metodi mahdollistaa tiedostojen lataamisen tavutaulukon avulla, joka edustaa tiedoston sisältöä. Tämä metodi on hyödyllinen, kun tiedoston sisältö luodaan tai käsitellään muistissa.

```java
byte[] content = Files.readAllBytes(Paths.get("polku/dokumentti.txt"));
Page.getCurrent().download(content, "dokumentti.txt");
```

- **content**: Tavutaulukko, joka edustaa tiedoston sisältöä.
- **fileName**: Latauksen mukainen tiedoston nimi.

## Fyysisten tiedostojen lataaminen {#downloading-physical-files}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink>-metodia käytetään tiedoston lataamiseen, joka sijaitsee palvelimella.

```java
File file = new File("polku/kuva.png");
Page.getCurrent().download(file, "kuva.png");
```

```java
// Tässä tapauksessa alkuperäistä tiedoston nimeä käytetään latauksessa.
Page.getCurrent().download(new File("polku/raportti.pdf"));
```

- **file**: Lataettava fyysinen tiedosto.
- **fileName**: Tiedoston nimi asiakkaalle näkyvässä muodossa.

## Resurssien lataaminen {#downloading-resources}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink>-metodi mahdollistaa resurssien lataamisen, jotka sijaitsevat sovelluksen kontekstissa tai joita osoitetaan polulla.

```java
Page.getCurrent().download("context://resources/manual.pdf", "käyttäjän-opas.pdf");
```

- **path**: Polku fyysiseen tiedostoon tai [kontekstin URL-osoite](./assets-protocols#the-context-protocol).
- **fileName**: Nimi, jonka alla tiedosto ladataan asiakkaalle.
