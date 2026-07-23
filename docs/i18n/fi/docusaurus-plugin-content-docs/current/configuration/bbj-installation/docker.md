---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 4981bf7da8b63063a781d946d29895e6
---
# Docker-asennus

Tässä asiakirjan osassa käsitellään vaiheita, jotka käyttäjien on suoritettava, jos he haluavat kehittää käyttäen Dockeria. Koodisi muutokset
tehdään kehityskoneellasi, ja tuloksena oleva sovellus suoritetaan Dockerissa.

## 1. Dockerin lataaminen {#1-downloading-docker}

Dockerin asennusprosessi vaihtelee hieman Windows-, Mac- ja Linux-käyttäjien kesken. Katso alla oleva osio, joka vastaa käyttöjärjestelmääsi.

### Windows {#windows}

:::info
On suositeltavaa ladata uusin versio Windows Subsystem for Linuxista. Lisätietoja löytyy [tästä linkistä](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Windows lataussivulle: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Napsauta "Hanki Docker Desktop for Windows" -painiketta ladataksesi asennusohjelman.

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennusohjelma.
>- Seuraa asennus aihetta ja varmista, että Hyper-V on käytössä (jos kehotetaan), sillä Docker for Windows käyttää Hyper-V:tä virtualisointiin.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Varmista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

### Mac {#mac}

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Mac lataussivulle: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennusohjelma.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Varmista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

## 2. Konfigurointi {#2-configuration}

Kun Docker Desktop on ladattu, etsi uusin webforJ-kuva, joka on tällä hetkellä nimellä `webforj/sandbox`.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Napsauta tagiluetteloa nähdäksesi saatavilla olevat vaihtoehdot

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Valitse "rc" uusimmasta versiosta

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Hae kuva aloittaaksesi säilön

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Kun lataus on valmis, napsauta Suorita-painiketta, joka avaa konfigurointiasetukset

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Avaa "Valinnaiset asetukset" -valikko

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Valitse haluamasi isäntäportti, josta voit nähdä sovelluksesi toimivan Dockerissa

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Napsauta "Suorita" aloittaaksesi säilön

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Tärkeää
Muista merkitä antamasi mukautettu isäntäportti, sillä sitä tarvitaan myöhemmin.
:::

## 3. Sovelluksen suorittaminen {#3-running-your-app}

Kun säilö on luotu, webforJ-sovellukset voidaan suorittaa säilössä sen sijaan, että ne suoritettaisiin paikallisesti. Ensin on tarpeen 
konfiguroida projektisi POM-tiedosto oikein. Kun tämä on tehty, tiettyyn URL-osoitteeseen siirtyminen selaimessa näyttää sovelluksen.

### POM-tiedoston konfigurointi {#configuring-your-pom-file}

webforJ-projektin suorittaminen Docker-säilössä vaatii webforJ Asennuspluginin käyttöä, joka voidaan konfiguroida POM-tiedostosi avulla:


Luo uusi `<plugin>`-merkintä `<plugins>`-osioon POM-tiedostossa. Alla oleva koodi näyttää alkumerkinnän, jota voidaan käyttää ja muokata tarpeen mukaan:

:::important
Jos POM-tiedostossasi ei ole `<plugins>`-osiota, luo sellainen.
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

Kun vastaava merkintä on luotu, muokkaa seuraavia tietoja:

- Muuta `<deployurl>`-merkintä käyttämään porttinumeroa, joka vastaa aiemmin säilyttävää **Isäntäporttia**, jonka määritit säilöllesi.

- Varmista, että `<classname>`-merkintä vastaa sen sovelluksen nimeä, jonka haluat suorittaa.

- Jos `<username>` ja `<password>` -todistustiedot eroavat BBjn asennuksestasi, muuta nämä.

### Aloitusprojektin käyttäminen {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Sovelluksen julkaiseminen {#launching-the-app}

Kun tämä on tehty, suorita `mvn install` projektisi hakemistossa. Tämä suorittaa webforJ-asennuspluginin ja
antaa sinun käyttää sovellustasi. Näet sovelluksen siirtymällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerilla määrittämälläsi isäntäportilla ja `YourPublishName` on korvattu POM-tiedoston `<publishname>`-tagin sisällä olevalla tekstillä.
Jos kaikki on tehty oikein, näet sovelluksesi renderöitynä.
