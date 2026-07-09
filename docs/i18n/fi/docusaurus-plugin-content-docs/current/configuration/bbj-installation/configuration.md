---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: b01357f571ce256abb8b390cebdbf5cc
---
Voit konfiguroida webforJ:n projektin POM-tiedoston avulla, joka on suunniteltu helpottamaan sovelluksen käyttöönottoa. Seuraavat osiot kuvaavat erilaisia vaihtoehtoja, joita voit muuttaa saadaksesi halutun tuloksen.

## Engine exclusion {#engine-exclusion}

Kun käytät `BBjServices`:ia, `webforj-engine`-riippuvuus tulisi sulkea pois, koska moottorin tarjoamat ominaisuudet ovat jo saatavilla.

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

## POM file tags {#pom-file-tags}

`<configuration>`-tunnisteen sisällä olevia tunnisteita voidaan muuttaa sovelluksesi konfiguroimiseksi. Muokkaamalla seuraavia rivejä oletus POM-tiedostossa, joka tulee [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) -alustasta, saavutetaan nämä muutokset:

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

- **`<deployurl>`** Tämä tunniste on URL-osoite, josta webforJ-päätepiste projektin asennusta varten voidaan tavoittaa. Käyttäjät, jotka suorittavat sovellusta paikallisesti, käyttävät oletusporttia 8888. Dockeria käyttäville käyttäjille portti tulisi vaihtaa siihen porttiin, joka syötettiin [Docker-kontin konfiguroinnissa](./docker#2-configuration).

- **`<classname>`** Tämä tunniste tulisi sisältää paketti ja luokan nimi, jota haluat suorittaa. Tämä on ainut luokka projektissasi, joka laajentaa `App`-luokkaa ja suorittaa perus-URL:stä.

- **`<publishname>`** Tämä tunniste määrittelee sovelluksen nimen julkaistussa URL-osoitteessa. Yleensä ohjelmasi suorittamiseksi navigoit URL-osoitteeseen, joka on samanlainen kuin `http://localhost:8888/webapp/<publishname>`, jolloin `<publishname>` korvataan `<publishname>`-tunnisteessa olevalla arvolla. Sitten `<classname>`-tunnisteessa määritetty ohjelma ladataan.

- **`<debug>`** Debug-tunniste voidaan asettaa todeksi tai epätodeksi, ja se määrittää, näyttävätkö selaimen konsoli virheilmoituksia, joita ohjelmasi heittää.

## Running a specific program {#running-a-specific-program}

On kaksi tapaa suorittaa tietty ohjelma sovelluksessasi:

1. Aseta ohjelma `run()`-menetelmään luokassa, joka laajentaa `App`.
2. Hyödynnä [reititystä](../../routing/overview) webforJ-sovelluksessa antaaksesi ohjelmalle omistetun URL-osoitteen.

## How webforJ selects an entry point {#how-webforj-selects-an-entry-point}

Sovelluksen sisäänkäynnin määrittää POM-tiedostossa ilmoitettu `<classname>`. 
Jos POM-tiedostossa ei ole määritetty sisäänkäyntiä, järjestelmä aloittaa sisäänkäynnin haun.

### Entry point search {#entry-point-search}

1. Jos on olemassa yksi luokka, joka laajentaa `App`-luokkaa, siitä tulee sisäänkäynti.
2. Jos useat luokat laajentavat `App`:a, järjestelmä tarkistaa, onko jollain niistä `com.webforj.annotation.AppEntry`-annotaatio. Yksittäinen luokka, joka on merkitty `@AppEntry`:llä, tulee sisäänkäynneksi.
    :::warning
    Jos useilla luokilla on `@AppEntry`-annotaatio, heitetään poikkeus, jossa luetellaan kaikki löytyneet luokat.
    :::

Jos on useita luokkia, jotka laajentavat `App`-luokkaa, eikä yksikään niistä ole merkitty `@AppEntry`:llä, heitetään poikkeus, jossa kerrotaan jokaisesta aliluokasta.

## Debug mode {#debug-mode}

On myös mahdollista suorittaa sovellustasi debug-tilassa, mikä sallii konsolin tulostaa kattavia virheilmoituksia.

Ensimmäinen vaihtoehto on muuttaa `config.bbx`-tiedostoa, joka löytyy BBj-asennuksesi `cfg/`-hakemistosta. Lisää tiedostoon rivi `SET DEBUG=1` ja tallenna muutoksesi.

Lisäksi Enterprise Managerissa voit lisätä seuraavan ohjelmaargumenttina: `DEBUG`

Molempien toteuttaminen sallii selaimen konsolin tulostaa virheilmoituksia.
