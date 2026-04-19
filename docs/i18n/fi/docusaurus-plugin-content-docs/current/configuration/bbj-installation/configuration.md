---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: f6ca2e9ca82e9592c4e0c8b7726164ce
---
Voit konfiguroida webforJ:n projektin POM-tiedoston avulla, joka on suunniteltu helpottamaan sovelluksen käyttöönottoa. Seuraavissa osioissa on esitelty erilaisia vaihtoehtoja, joita voit muuttaa halutun tuloksen saavuttamiseksi.

## Moottorin poissulkeminen {#engine-exclusion}

Kun käytetään `BBjServices`:ia, `webforj-engine`-riippuvuus tulisi sulkea pois, koska moottorin tarjoamat ominaisuudet ovat jo saatavilla.

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions> 
  </dependency>
</dependencies>
```

## POM-tiedoston tunnisteet {#pom-file-tags}

`<configuration>`-tunnisteen sisällä olevia tunnisteita voidaan muuttaa sovelluksesi konfiguroimiseksi. Muokkaamalla seuraavia rivejä oletus POM-tiedostossa, joka tulee [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) aloitustallennuksesta, saat aikaan seuraavat muutokset:

```xml {13-16} showLineNumbers
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-install-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>install</goal>
      </goals>
  </execution>
  </executions>
  <configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
  </configuration>
</plugin>
```

- **`<deployurl>`** Tämä tunniste on URL-osoite, josta webforJ:n päätepiste projektin asennusta varten voidaan saavuttaa. Paikallisesti sovellustaan ajavat käyttäjät käyttävät oletusporttia 8888. Dockeria käytettäessä porttia tulisi muuttaa porttiin, joka syötettiin [Docker-kontin konfiguroinnissa](./docker#2-configuration).

- **`<classname>`** Tämä tunniste sisältää paketin ja luokan nimen sovelluksesta, jonka haluat suorittaa. Tämä on ainoa luokka projektissasi, joka laajentaa `App`-luokkaa ja käynnistyy perus-URL-osoitteesta.

- **`<publishname>`** Tämä tunniste määrittää sovelluksen nimen julkaistussa URL-osoitteessa. Yleisesti ottaen, jotta voit suorittaa ohjelmasi, siirryt URL-osoitteeseen, joka muistuttaa `http://localhost:8888/webapp/<publishname>`, korvaten `<publishname>` tunnisteen arvolla. Tällöin `<classname>`-tunnisteella määritelty ohjelma suoritetaan.

- **`<debug>`** Debug-tunniste voidaan asettaa arvoksi tosi tai epätosi, ja se määrää, näytetäänkö selaimen konsolissa ohjelmasi heittämiä virheviestejä.

## Tietyn ohjelman suorittaminen {#running-a-specific-program}

On kaksi tapaa suorittaa tietty ohjelma sovelluksessasi:

1. Aseta ohjelma `run()`-menetelmään luokassa, joka laajentaa `App`.
2. Hyödynnä [reititystä](../../routing/overview) webforJ-sovelluksessasi antaa ohjelmalle oma URL-osoitteensa.

## Kuinka webforJ valitsee sisääntulopisteen {#how-webforj-selects-an-entry-point}

Sovelluksen sisääntulopiste määritetään POM-tiedostossa määritellyn `<classname>`:n avulla. 
Jos POM-tiedostossa ei ole määritelty sisääntulopistettä, järjestelmä aloittaa sisääntulopisteen haun.

### Sisääntulopisteen haku {#entry-point-search}

1. Jos on olemassa yksi luokka, joka laajentaa `App`-luokkaa, siitä tulee sisääntulopiste.
2. Jos useat luokat laajentavat `App`-luokkaa, järjestelmä tarkistaa, onko jollain niistä `com.webforj.annotation.AppEntry`-annotaatio. Yksi annotaatioon merkitty luokka `@AppEntry` tulee sisääntulopisteeksi.
    :::warning
    Jos useat luokat on merkitty `@AppEntry`:llä, heitetään poikkeus, joka luettelee kaikki löytyneet luokat.
    :::

Jos on useita luokkia, jotka laajentavat `App`-luokkaa eikä kukaan niistä ole merkitty `@AppEntry`:llä, heitetään poikkeus, jossa kerrotaan jokaisesta aliluokasta.

## Debug-tila {#debug-mode}

On myös mahdollista suorittaa sovelluksesi debug-tilassa, joka mahdollistaa konsolin tulostaa yksityiskohtaisia virheviestejä.

Ensimmäinen vaihtoehto on muuttaa `config.bbx`-tiedostoa, joka löytyy BBj-asennuksesi `cfg/`-hakemistosta. Lisää tiedostoon rivi `SET DEBUG=1` ja tallenna muutoksesi.

Lisäksi Enterprise Managerissa voit lisätä seuraavan ohjelman argumenttina: `DEBUG`

Molempien vaihtoehtojen toteuttaminen mahdollistaa selaimen konsolin tulostaa virheviestejä.
