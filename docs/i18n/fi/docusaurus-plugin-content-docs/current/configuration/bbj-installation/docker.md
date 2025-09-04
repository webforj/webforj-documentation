---
sidebar_position: 1
title: Docker
_i18n_hash: ec7566378c3ec80f071b7391742ec353
---
# Docker-asennus

Tässä asiakirjan osassa käsitellään vaiheita, jotka käyttäjien on suoritettava, jos he haluavat kehittää Dockerin avulla. Muutokset koodiisi tehdään kehityskoneellasi, ja tuloksena oleva sovellus suoritetaan Dockerissa.

## 1. Dockerin lataaminen {#1-downloading-docker}

Dockerin asennusprosessi vaihtelee hieman Windows-, Mac- ja Linux-käyttäjien välillä. Katso alla oleva osio, joka vastaa käyttöjärjestelmääsi.

### Windows {#windows}

:::info
On suositeltavaa ladata uusin versio Windows Subsystem for Linuxista. Lisätietoja löytyy [tällä linkillä](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Lataa Docker Desktop:**
>- Vieraile Docker Desktop for Windows -lataussivulla: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Napsauta "Get Docker Desktop for Windows" -painiketta ladataksesi asennustiedoston.

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennustiedosto.
>- Seuraa asennusohjelman ohjeita ja varmista, että otat käyttöön Hyper-V (jos kehotetaan), koska Docker Windowsille käyttää Hyper-V:tä virtualisointiin.
>- Kun asennus on valmis, Docker Desktop alkaa käynnistyä automaattisesti.

**3. Tarkista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

### Mac {#mac}

**1. Lataa Docker Desktop:**
>- Vieraile Docker Desktop for Mac -lataussivulla: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennustiedosto.
>- Kun asennus on valmis, Docker Desktop alkaa käynnistyä automaattisesti.

**3. Tarkista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

## 2. Konfigurointi {#2-configuration}

Kun Docker Desktop on ladattu, etsi uusin webforJ -image, joka on tällä hetkellä nimellä `webforj/sandbox`.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Napsauta tagilistaa nähdäksesi käytettävissä olevat vaihtoehdot

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Valitse "rc" uusimmalle rakennelmalle

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

TPullaa kuva aloittaaksesi säilösi

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Kun lataus on valmis, napsauta suorituspainiketta, joka avaa konfigurointiasetukset

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Avaa "Valinnaiset asetukset" -valikko

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Valitse haluttu isäntäsatama, josta voit nähdä sovelluksesi toimivan Dockerissa

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Napsauta "Suorita" aloittaaksesi säilön

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Tärkeä
Muista ottaa talteen antamasi mukautettu isäntäsataman numero, sillä sitä tarvitaan myöhemmin.
:::

## 3. Sovelluksesi suorittaminen {#3-running-your-app}

Kun säilö on luotu, webforJ-sovelluksia voidaan suorittaa säilössä sen sijaan, että niitä suoritettaisiin paikallisesti. Aluksi on tarpeen konfiguroida projektisi POM-tiedosto oikein. Kun tämä on tehty, siirtyminen tiettyyn URL-osoitteeseen selaimessa näyttää sovelluksen.

### POM-tiedoston konfigurointi {#configuring-your-pom-file}

WebforJ-projektin suorittaminen Docker-säilössä vaatii webforJ Install -lisäosien käyttöä, joka voidaan konfiguroida POM-tiedostosi avulla:

Luo uusi `<plugin>`-merkintä POM-tiedoston `<plugins>`-osioon. Seuraava koodi esittää aloitusmerkinnän, jota voidaan käyttää ja muokata tarpeen mukaan projektiasi varten:

:::important
Jos POM-tiedostossasi ei ole `<plugins>`-osuutta, luo sellainen.
:::

```xml
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

Kun olet luonut edellä olevan kaltaisen merkinnän, mukauta seuraavat tiedot:

- Muuta `<deployurl>`-merkintä käyttämään porttinumeroa, joka vastaa **Isäntäsatamaa**, jonka olet määrittänyt säilöllesi edellisessä vaiheessa.

- Varmista, että `<classname>`-merkintä vastaa haluamasi sovelluksen nimeä.

- Jos `<username>`- ja `<password>`-tunnuksesi ovat erilaiset BBj-asennuksessasi, muuta nämä.

### Aloitusprojektin käyttäminen {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Sovelluksen käynnistäminen {#launching-the-app}

Kun tämä on tehty, suorita `mvn install` projektikansiossasi. Tämä suorittaa webforJ-install-lisäosan ja sallii sinun käyttää sovellustasi. Näet sovelluksen menemällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vaihda `YourHostPort` Dockerin avulla määrittämääsi isäntäsatamaan ja `YourPublishName` on vaihdettu POM-tiedoston `<publishname>`-tagin sisällä olevaan tekstiin. Jos kaikki on tehty oikein, sinun pitäisi nähdä sovelluksesi näyttävän.
