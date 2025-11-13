---
title: Asenna lisäosa
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 1a3e48999554631e4f15a67c80385111
---
Voit konfiguroida webforJ:n projektin POM-tiedoston avulla, joka on suunniteltu helpottamaan sovelluksen käyttöönottoa. Seuraavat osiot kuvaavat erilaisia vaihtoehtoja, joita voit muuttaa halutun tuloksen saavuttamiseksi.

## Moottorin poissulkeminen {#engine-exclusion}

Kun käytät `BBjServices`:ia, `webforj-engine`-riippuvuus tulisi jättää pois, koska moottorin tarjoamat toiminnot ovat jo saatavilla.

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

Tagsit `<configuration>`-tagin sisällä voidaan muuttaa sovelluksesi konfiguroimiseksi. Muokkaamalla seuraavia rivejä oletus POM-tiedostossa, joka tulee [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) -aloitusrepositoriosta, saat aikaan seuraavat muutokset:

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

- **`<deployurl>`** Tämä tagi on URL, jossa webforJ:n päätepiste projektin asennusta varten voidaan saavuttaa. Paikallisesti sovellustaan ajavat käyttäjät käyttävät oletusporttia 8888. Dockeria käyttävien käyttäjien on vaihdettava portti siihen, joka syötettiin [Docker-konttia konfiguroidessa](./docker#2-configuration).

- **`<classname>`** Tämä tagi sisältää paketin ja luokan nimen sovelluksesta, jota haluat suorittaa. Tämä on ainut luokka projektissasi, joka laajentaa `App`-luokkaa ja käynnistyy perus-URL:stä.

- **`<publishname>`** Tämä tagi määrittää sovelluksen nimen julkaistussa URL:ssä. Yleensä ohjelmasi suorittamiseksi navigoit URL-osoitteeseen, joka on samanlainen kuin `http://localhost:8888/webapp/<publishname>`, korvaten `<publishname>`-tagin arvolla. Tällöin `<classname>`-tagissa ilmoitettu ohjelma suoritetaan.

- **`<debug>`** Debug-tagia voidaan asettaa arvoon tosi tai epätosi, ja se määrittää, näytetäänkö selaimen konsolissa virheviestejä, joita ohjelmasi heittää. 

## Tietyn ohjelman suorittaminen {#running-a-specific-program}

On kaksi tapaa suorittaa tietty ohjelma sovelluksessasi:

1. Aseta ohjelma `run()`-metodiin luokassa, joka laajentaa `App`.
2. Hyödynnä [reititystä](../../routing/overview) webforJ-sovelluksessasi antaaksesi ohjelmalle omistetun URL: n.

## Kuinka webforJ valitsee aloituspisteen {#how-webforj-selects-an-entry-point}

Sovelluksen aloituspiste määräytyy POM-tiedostossa määritetyn `<classname>`-tagin mukaan. Jos aloituspistettä ei ole määritetty POM-tiedostossa, järjestelmä aloittaa aloituspisteen haun.

### Aloituspisteen haku {#entry-point-search}

1. Jos on olemassa yksi luokka, joka laajentaa `App`-luokkaa, siitä tulee aloituspiste.
2. Jos useat luokat laajentavat `App`:a, järjestelmä tarkistaa, onko jollain niistä `com.webforj.annotation.AppEntry`-annotaatio. Ainut annotaatio, jolla on `@AppEntry`, tulee aloituspisteeksi.
    :::warning
    Jos usealla luokalla on `@AppEntry`-annotaatio, poikkeus heitetään, ja kaikki löydetyt luokat luetellaan.
    :::

Jos on useita luokkia, jotka laajentavat `App`:a eikä yksikään niistä ole merkitty `@AppEntry`, heitetään poikkeus, jossa kerrotaan jokaisesta aliluokasta.

## Debug-tila {#debug-mode}

On myös mahdollista suorittaa sovellustasi debug-tilassa, joka sallii konsolin tulostaa kattavia virheviestejä.

Ensimmäinen vaihtoehto on muokata `config.bbx`-tiedostoa, joka löytyy BBj-asennuksesi `cfg/`-kansiosta. Lisää tiedostoon rivi `SET DEBUG=1` ja tallenna muutoksesi.

Lisäksi Enterprise Managerissa voit lisätä seuraavan ohjelmaargumenttina: `DEBUG`

Molempien näiden tekeminen mahdollistaa selaimen konsolin tulostaa virheviestejä.
