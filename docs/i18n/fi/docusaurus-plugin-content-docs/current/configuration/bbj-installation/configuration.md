---
title: Asenna laajennus
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: 1019285125db3439621951fe22047837
---
:::danger Poistettu ja tullaan poistamaan
webforJ-asennusliitännäinen on poistettu käytöstä ja se tullaan poistamaan tulevassa julkaisussa. Älä aloita uusia projekteja sen kanssa. Siirrä olemassa olevat projektit [BBjShip](/docs/configuration/bbjship/overview), joka asentaa, julkaisee ja suorittaa webforJ-sovelluksia BBjPalveluissa. [Olemassa olevat projektit](/docs/configuration/bbjship/existing-projects) luettelee muutokset, joita projekti tarvitsee.
:::

Voit konfiguroida webforJ:n projektin POM-tiedoston avulla, joka on suunniteltu helpottamaan sovelluksen julkaisemista. Seuraavat osiot kuvaavat erilaisia vaihtoehtoja, joita voit muuttaa halutun tuloksen saavuttamiseksi.

## Moottorin poissulkeminen {#engine-exclusion}

Kun käytetään `BBjServices`, `webforj-engine`-riippuvuus tulisi sulkea pois, koska moottorin tarjoamat ominaisuudet ovat jo käytettävissä.

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

`<configuration>`-tunnisteen sisällä olevia tunnisteita voidaan muuttaa sovelluksesi konfiguroimiseksi. Muokkaamalla seuraavia rivejä oletusarvoisessa POM-tiedostossa, joka tulee [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) -aloitusrepositorystä, saadaan seuraavat muutokset:

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

- **`<deployurl>`** Tämä tunniste on URL-osoite, josta webforJ-projektin asennus päätepiste on käytettävissä. Paikallisesti sovellustaan ajaville käyttäjille käytetään oletusporttia 8888. Dockeria käyttävien käyttäjien tulisi muuttaa portti siihen porttiin, joka on syötetty [Docker-kontin konfiguroinnissa](./docker#2-configuration).

- **`<classname>`** Tämä tunniste tulisi sisältää paketti ja luokan nimi sovelluksesta, jonka haluat suorittaa. Tämä on ainoa luokka projektissasi, joka laajentaa `App`-luokkaa ja suoritetaan perus-URL:sta.

- **`<publishname>`** Tämä tunniste määrittelee sovelluksen nimen julkaistussa URL-osoitteessa. Yleensä ohjelmasi suorittamiseksi navigoit URL-osoitteeseen, joka on samanlainen kuin `http://localhost:8888/webapp/<publishname>`, korvaten `<publishname>` tunnisteen arvolla. Sitten `<classname>`-tunnisteessa määritelty ohjelma suoritetaan.

- **`<debug>`** Debug-tunnistetta voidaan asettaa arvoon true tai false, ja se määrää, näytetäänkö selaimen konsolissa virheilmoituksia, joita ohjelmasi heittää.

## Tietyn ohjelman suorittaminen {#running-a-specific-program}

On olemassa kaksi tapaa suorittaa tietty ohjelma sovelluksessasi:

1. Aseta ohjelma `run()`-metodiin luokassa, joka laajentaa `App`.
2. Hyödynnä [reititystä](../../routing/overview) webforJ-sovelluksessasi antaaksesi ohjelmalle omistetun URL-osoitteen.

## Kuinka webforJ valitsee pääsytien {#how-webforj-selects-an-entry-point}

Sovelluksen pääsykohta määritellään POM-tiedostossa määritellyn `<classname>`-tunnisteen perusteella. 
Jos POM-tiedostossa ei ole määritelty pääsykohtaa, järjestelmä aloittaa pääsykohdan haun.

### Pääsykohdan haku {#entry-point-search}

1. Jos on olemassa ainoa luokka, joka laajentaa `App`-luokkaa, siitä tulee pääsykohta.
2. Jos useat luokat laajentavat `App`:a, järjestelmä tarkistaa, onko yhdellä niistä `com.webforj.annotation.AppEntry`-annotaatio. Ainoa luokka, joka on merkattu `@AppEntry`:llä, tulee pääsykohdaksi.
    :::warning
    Jos useat luokat on merkitty `@AppEntry`:llä, poikkeus heitetään, jossa luetellaan kaikki löydetyt luokat.
    :::

Jos useita luokkia laajentaa `App`-luokkaa eikä yksikään niistä ole merkitty `@AppEntry`:llä, heitetään poikkeus, joka yksityiskohtaisesti kuvaa jokaisen alaluokan.

## Virheiden korjaustila {#debug-mode}

Myös sovelluksesi voi suorittaa virheiden korjaustilassa, mikä mahdollistaa konsolin tulostaa kattavia virheilmoituksia.

Ensimmäinen vaihtoehto on muuttaa `config.bbx`-tiedostoa, joka sijaitsee BBj-asennuksesi `cfg/`-hakemistossa. Lisää rivi `SET DEBUG=1` tiedostoon ja tallenna muutoksesi.

Lisäksi Enterprise Managerissa voit lisätä seuraavan ohjelman argumenttina: `DEBUG`

Jomman kumman vaihtoehdon toteuttaminen mahdollistaa selaimen konsolin virheilmoitusten tulostamisen.
