---
sidebar_position: 1
title: Docker
_i18n_hash: 642936b8f7fd836ca4510eab19087a8c
---
# Docker-asennus

Tässä asiakirjan osassa käsitellään vaiheita, joita käyttäjien on noudatettava, jos he haluavat kehittää käyttämällä Dockeria. Muutokset koodissasi tehdään kehityskoneellasi, ja lopullista sovellusta suoritetaan Dockerissa. 

## 1. Dockerin lataaminen {#1-downloading-docker}

Dockerin asennusprosessi vaihtelee hieman Windows-, Mac- ja Linux-käyttäjien välillä. Katso alla oleva osio, joka vastaa käyttöjärjestelmääsi.

### Windows {#windows}

:::info
On suositeltavaa ladata uusin versio Windowsin Linux-alijärjestelmästä. Lisätietoja löydät [tästä linkistä](https://learn.microsoft.com/en-us/windows/wsl/install)
::: 

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Windows - lataussivulle: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Napsauta "Hanki Docker Desktop for Windows" -painiketta ladataksesi asennustiedoston.

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennustiedosto.
>- Seuraa asennusohjelmaa ja varmista, että Hyper-V on aktivoitu (jos kehotetaan), sillä Docker for Windows käyttää Hyper-V:tä virtualisointiin.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Varmista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

### Mac {#mac}

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Mac - lataussivulle: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennustiedosto.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Varmista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

## 2. Konfigurointi {#2-configuration}

Kun Docker Desktop on ladattu, etsi uusin webforJ-kuva, joka on tällä hetkellä nimellä `webforj/sandbox`.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Napsauta tagiluetteloa nähdäksesi saatavilla olevat vaihtoehdot.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Valitse "rc" viimeisimmälle versiolle.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Hae kuva aloittaaksesi säiliösi.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Kun lataus on valmis, napsauta käynnistysnappia, jolloin avautuu konfigurointiasetukset.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Avaa "Valinnaiset asetukset" -valikko.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Valitse haluamasi isäntaportti, jolta voit nähdä sovelluksesi toimivan Dockerissa.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Napsauta "Suorita" aloittaaksesi säiliön.

![DWCJ Kuvahaku](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Tärkeää
Varmista, että otat muistiin antamasi mukautetun isäntaporttinumeron, sillä tätä tarvitaan myöhemmin.
:::

## 3. Sovelluksesi suorittaminen {#3-running-your-app}

Kun säiliö on luotu, webforJ-sovelluksia voidaan suorittaa säiliössä sen sijaan, että niitä suoritetaan paikallisesti. Ensinnäkin on tarpeen määrittää projektisi POM-tiedosto oikein. Kun tämä on tehty, siirtyminen tiettyyn URL-osoitteeseen sivustolla näyttää sovelluksen.

### POM-tiedoston määrittäminen {#configuring-your-pom-file}

WebforJ-projektin suorittaminen Docker-säiliössä vaatii webforJ Install Pluginin käyttöä, joka voidaan konfiguroida POM-tiedoston avulla:

Luo uusi `<plugin>`-merkintä `<plugins>`-osioon POM-tiedostossa. Seuraava koodi näyttää aloitusmerkinnän, jota voidaan käyttää ja muokata tarpeen mukaan projektiisi:

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

Kun yllä olevan kaltainen merkintä on luotu, mukauta seuraavat tiedot:

- Muuta `<deployurl>`-merkintä vastaamaan porttinumeroasi, joka vastaa aiemmin määrittämääsi **Isäntaporttia**.

- Varmista, että `<classname>`-merkintä vastaa sen sovelluksen nimeä, jonka haluat suorittaa.

- Jos `<username>` ja `<password>` -tunnuksesi poikkeavat BBj-asennuksestasi, muuta nämä.

### Aloitusprojektin käyttäminen {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Sovelluksen käynnistäminen {#launching-the-app}

Kun tämä on tehty, suorita `mvn install` projektikansiossasi. Tämä suorittaa webforJ-asennusliitännän ja mahdollistaa pääsyn sovellukseesi. Näet sovelluksen siirtymällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerille määrittämälläsi isäntaportilla, ja `YourPublishName` korvataan POM-tiedoston `<publishname>`-tagin sisällöllä. Jos kaikki on tehty oikein, sinun pitäisi nähdä sovelluksesi renderöityvän.
