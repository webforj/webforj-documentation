---
sidebar_position: 3
title: Downloading Files
_i18n_hash: 013e64888b44110c108f80adf492be10
---
webforJ tarjoaa menetelmiä tiedostojen lataamisen käynnistämiseen, mikä helpottaa tiedostojen tarjoamista käyttäjille selaimen kautta. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> -luokka tarjoaa useita tapoja ladata tiedostoja, olivatpa ne sitten syötevirroista, tavutaulukosta, fyysisistä tiedostoista tai resursseista.

## Tiedostojen lataaminen `InputStream`-käytön avulla {#downloading-files-using-inputstream}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.InputStream,java.lang.String)'>download(InputStream inputStream, String fileName)</JavadocLink> -menetelmä mahdollistaa tiedoston lähettämisen asiakkaalle syötevirran avulla. Tämä on erityisen hyödyllistä, kun tiedoston sisältö generoidaan dynaamisesti tai luetaan ulkoiselta lähteeltä.

```java
InputStream inputStream = new FileInputStream("polku/näyte.pdf");
Page.getCurrent().download(inputStream, "näyte.pdf");
```

- **inputStream**: Syötevirta, joka edustaa tiedoston sisältöä.
- **fileName**: Nimi, jolla tiedosto ladataan asiakkaalle.

## Tiedostojen lataaminen tavutaulukoiden avulla {#downloading-files-using-byte-arrays}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(byte%5B%5D,java.lang.String)'>download(byte[] content, String fileName)</JavadocLink> -menetelmä mahdollistaa tiedostojen lataamisen käyttämällä tavutaulukkoa, joka edustaa tiedoston sisältöä. Tämä menetelmä on hyödyllinen, kun tiedoston sisältö on generoitu tai käsitelty muistissa.

```java
byte[] content = Files.readAllBytes(Paths.get("polku/dokumentti.txt"));
Page.getCurrent().download(content, "dokumentti.txt");
```

- **content**: Tavutaulukko, joka edustaa tiedoston sisältöä.
- **fileName**: Latauksen aikana käytettävä tiedoston nimi.

## Fyysisten tiedostojen lataaminen {#downloading-physical-files}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.io.File,java.lang.String)'>download(File file, String fileName)</JavadocLink> -menetelmää käytetään lataamaan tiedosto, joka sijaitsee palvelimella.

```java
File file = new File("polku/kuva.png");
Page.getCurrent().download(file, "kuva.png");
```

```java
// Tässä tapauksessa alkuperäistä tiedoston nimeä käytetään lataamisessa.
Page.getCurrent().download(new File("polku/raportti.pdf"));
```

- **file**: Ladattava fyysinen tiedosto.
- **fileName**: Tiedoston nimi, kuten se näkyy asiakkaalle.

## Resurssien lataaminen {#downloading-resources}

<JavadocLink type="foundation" location="com/webforj/Page" code='true' suffix='#download(java.lang.String,java.lang.String)'>download(String path, String fileName)</JavadocLink> -menetelmä mahdollistaa resurssien lataamisen, jotka sijaitsevat sovelluksen kontekstissa tai on määritelty polun avulla.

```java
Page.getCurrent().download("context://resources/manual.pdf", "käyttäjän_opas.pdf");
```

- **path**: Polku fyysiseen tiedostoon tai [kontekstin URL-osoite](./assets-protocols#the-context-protocol).
- **fileName**: Nimi, jolla tiedosto ladataan asiakkaalle.
