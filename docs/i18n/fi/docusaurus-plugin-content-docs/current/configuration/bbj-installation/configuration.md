---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 3f3e4285abb3b23f9427cdd7b9baa282
---
Voit konfiguroida webforJ:n projektin POM-tiedoston avulla, joka on suunniteltu helpottamaan sovelluksen käyttöönottoa. Seuraavissa osissa on esitelty erilaiset vaihtoehdot, joita voit muuttaa halutun tuloksen saavuttamiseksi.

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

## POM-tiedoston tagit {#pom-file-tags}

`<configuration>`-tagin sisällä olevia tageja voidaan muuttaa sovelluksesi konfiguroimiseksi. Muokkaamalla seuraavia rivejä oletus POM-tiedostossa, joka tulee [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) aloitusrekisteristä, saat seuraavat muutokset:

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

- **`<deployurl>`** Tämä tagi on URL-osoite, josta webforJ-päätepiste projektin asennusta varten voidaan löytää. Paikallisesti sovellustaan ajavat käyttäjät käyttävät oletusporttia 8888. Dockeria käyttäville käyttäjille portti tulee muuttaa siihen porttiin, joka on syötetty [Docker-säilön konfiguroinnissa](./docker#2-configuration).

- **`<classname>`** Tämä tagi tulisi sisältää paketin ja luokan nimen sovelluksesta, jonka haluat suorittaa. Tämä on ainoa luokka projektissasi, joka laajentaa `App`-luokkaa ja suorittaa perus-URL:sta.

- **`<publishname>`** Tämä tagi määrittelee sovelluksen nimen julkaistussa URL-osoitteessa. Yleisesti ottaen ohjelmasi suorittamiseksi navigoit URL-osoitteeseen, joka on samanlainen kuin `http://localhost:8888/webapp/<publishname>`, korvaten `<publishname>` arvolla `<publishname>`-tagissa. Sitten `<classname>`-tagissa määritelty ohjelma suoritetaan.

- **`<debug>`** Debug-tagin voi asettaa toteen tai epätodeksi, ja se määrittää, näytetäänkö selaimen konsolissa virheilmoituksia, joita ohjelmasi heittää.

## Tiety ohjelman suorittaminen {#running-a-specific-program}

On kaksi tapaa suorittaa tietty ohjelma sovelluksessasi:

1. Aseta ohjelma `run()`-metodiin luokassa, joka laajentaa `App`:ia.
2. Hyödynnä [reittauksia](../../routing/overview) webforJ-sovelluksessasi antaaksesi ohjelmalle omistetun URL-osoitteen.

## Kuinka webforJ valitsee aloituspisteen {#how-webforj-selects-an-entry-point}

Sovelluksen aloituspiste määräytyy POM-tiedostossa määriteltyjen `<classname>`-tagin mukaan. 
Jos POM-tiedostossa ei ole määritetty aloituspistettä, järjestelmä alkaa etsiä aloituspistettä.

### Aloituspisteen etsiminen {#entry-point-search}

1. Jos on olemassa yksi luokka, joka laajentaa `App`-luokkaa, siitä tulee aloituspiste.
2. Jos useat luokat laajentavat `App`:a, järjestelmä tarkistaa, onko jollakin `com.webforj.annotation.AppEntry`-annotaatio. Yksi annotaatiolla `@AppEntry` varustettu luokka tulee aloituspisteeksi.
    :::warning
    Mikäli useat luokat on merkitty `@AppEntry`, heitetään poikkeus, jossa luetellaan kaikki löydetyt luokat.
    :::

Jos useita luokkia laajentaa `App`:a, eikä yksikään niistä ole annotoinut `@AppEntry`, heitetään poikkeus, jossa yksityiskohtaisesti kuvataan jokainen aliluokka.

## Debug-tila {#debug-mode}

On myös mahdollista ajaa sovellustasi debug-tilassa, mikä antaa konsolille mahdollisuuden tulostaa kattavia virheilmoituksia.

Ensimmäinen vaihtoehto on muuttaa `config.bbx`-tiedostoa, joka sijaitsee BBj-asennuksesi `cfg/`-hakemistossa. Lisää tiedostoon rivi `SET DEBUG=1` ja tallenna muutoksesi.

Lisäksi Enterprise Managerissa voit lisätä seuraavan ohjelmaargumenttina: `DEBUG`

Molempien näiden suorittaminen mahdollistaa selaimen konsolin tulostaa virheilmoituksia.
