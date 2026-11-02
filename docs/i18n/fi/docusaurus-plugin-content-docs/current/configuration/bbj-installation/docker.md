---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 6c644b1d4f54cfa22e3b978714a5849e
---
# Docker-asennus

:::danger Vanhentunut ja poistetaan käytöstä
Docker-asennus `webforj/sandbox`-kuvalla on vanhentunut ja se poistetaan tulevissa versioissa. Älä aloita uusia projekteja sen kanssa. Siirrä olemassa olevat projektit [BBjShip](/docs/configuration/bbjship/overview), joka asentaa, julkaisee ja suorittaa webforJ-sovelluksia BBjServices-sovelluksissa. [Olemassa olevat projektit](/docs/configuration/bbjship/existing-projects) sisältää luettelon muutoksista, joita projekti tarvitsee.
:::

Tässä asiakirjan osassa käsitellään vaiheita, jotka tarvitaan käyttäjille, jotka haluavat kehittää Dockerin avulla. Muutoksia koodissasi tehdään kehityskoneellasi, ja lopullista sovellusta ajetaan Dockerissa.

## 1. Dockerin lataaminen {#1-downloading-docker}

Dockerin asennusprosessi vaihtelee hieman Windows-, Mac- ja Linux-käyttäjien välillä. Katso alla oleva osa, joka vastaa käyttöjärjestelmääsi.

### Windows {#windows}

:::info
On suositeltavaa ladata uusin versio Windows Subsystem for Linuxista. Lisätietoja löytyy [tästä linkistä](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Windows -lataussivulle: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Napsauta "Hae Docker Desktop for Windows" -painiketta ladataksesi asennusohjelman.

**2. Asenna Docker Desktop:**
>- Suorita ladattu asennusohjelma.
>- Seuraa asennusohjelmaa ja varmista, että Hyper-V on käytössä (jos kehotetaan), sillä Docker Windowsissa käyttää Hyper-V:tä virtualisointiin.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Vahvista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

### Mac {#mac}

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Mac -lataussivulle: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Asenna Docker Desktop:**
>- Suorita ladattu asennusohjelma.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Vahvista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

## 2. Määritys {#2-configuration}

Kun Docker Desktop on ladattu, etsi uusin webforJ-kuva, joka on tällä hetkellä nimellä `webforj/sandbox`.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Napsauta tagiluetteloa nähdäksesi käytettävissä olevat vaihtoehdot.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Valitse "rc" uusimmalle rakennukselle.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Hae kuva aloittaaksesi säiliön.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Kun lataus on valmis, napsauta Käynnistä-painiketta, joka avaa määrityksen asetukset.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Avaa "Valinnaiset asetukset" -valikko.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Valitse haluamasi isäntäportti, jolla voit nähdä sovelluksesi ajavan Dockerissa.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Napsauta "Aloita" käynnistäksesi säiliön.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Tärkeää
Muista ottaa huomioon antamasi mukautetun isäntäporttinumeron, sillä sitä tarvitaan myöhemmin.
:::

## 3. Sovelluksen suorittaminen {#3-running-your-app}

Kun säiliö on luotu, webforJ-sovelluksia voidaan suorittaa säiliössä sen sijaan, että ne suoritettaisiin paikallisesti. Ensinnäkin on tarpeen määrätä projektisi POM-tiedosto oikein. Kun tämä on tehty, erityiseen URL-osoitteeseen meneminen selaimessa näyttää sovelluksen.

### POM-tiedoston määritys {#configuring-your-pom-file}

WebforJ-projektin suorittaminen Docker-säiliössä vaatii webforJ-asennuspluginin käyttöä, joka voidaan määrittää POM-tiedostosi avulla:

Luo uusi `<plugin>`-merkintä `<plugins>`-osioon POM-tiedostossa. Seuraava koodi näyttää aloitusmerkinnän, jota voidaan käyttää ja muokata tarpeidesi mukaan:

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

Kun vastaava merkintä on luotu, räätälöi seuraavat tiedot:

- Muuta `<deployurl>`-merkintä käyttämään porttinumeroa, joka vastaa säiliösi aiemmin määrittämääsi **Isäntäporttia**.

- Varmista, että `<classname>`-merkintä vastaa sovelluksen nimeä, jota haluat ajaa.

- Jos BBj-asennuksen `<username>` ja `<password>` -tiedot poikkeavat, muuta ne.

### Aloitustyökalun käyttäminen {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Sovelluksen käynnistys {#launching-the-app}

Kun tämä on tehty, suorita `mvn install` projektikansiossasi. Tämä suorittaa webforJ-asennuspluginin ja sallii pääsyn sovellukseesi. Näyttääksesi sovelluksen sinun on mentävä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerissa määrittämälläsi isäntäportilla ja `YourPublishName` on korvattu POM-tiedoston `<publishname>`-tagin sisällä olevalla tekstillä. Jos kaikki on tehty oikein, sinun pitäisi nähdä sovelluksesi renderöitynä.
