---
sidebar_position: 1
title: Docker
_i18n_hash: 49f4e9eb5470926c186e323e4d67377f
---
# Docker-asennus

Tässä asiakirjan osassa käsitellään vaiheita, jotka tarvitaan käyttäjille, jotka haluavat kehittää käyttämällä Dockeria. Muutokset koodissasi tehdään kehityskoneellasi, ja lopullinen sovellus käynnistetään Dockerissa.

## 1. Dockerin lataaminen {#1-downloading-docker}

Dockerin asennusprosessi vaihtelee hieman Windows-, Mac- ja Linux-käyttäjien välillä. Katso alla oleva osio, joka vastaa käyttöjärjestelmääsi.

### Windows {#windows}

:::info
Suositellaan lataamaan uusin versio Windows Subsystem for Linuxista. Lisätietoja löytyy [tästä linkistä](https://learn.microsoft.com/en-us/windows/wsl/install).
:::

**1. Lataa Docker Desktop:**
>- Vieraile Docker Desktop for Windows lataussivulla: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Napsauta "Get Docker Desktop for Windows" -painiketta ladataksesi asennustiedoston.

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennustiedosto.
>- Seuraa asennusvelhoa ja varmista, että olet ottanut Hyper-V:n käyttöön (jos sitä pyydetään), sillä Docker for Windows käyttää Hyper-V:tä virtualisointiin.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Vahvista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

### Mac {#mac}

**1. Lataa Docker Desktop:**
>- Vieraile Docker Desktop for Mac lataussivulla: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Asenna Docker Desktop:**
>- Suorita lataamasi asennustiedosto.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Vahvista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

## 2. Konfigurointi {#2-configuration}

Kun Docker Desktop on ladattu, etsi uusin webforJ- kuva, joka on tällä hetkellä nimellä `webforj/sandbox`.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Napsauta tagiluetteloa nähdäksesi käytettävissä olevat vaihtoehdot

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Valitse "rc" viimeiselle rakennukselle

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Hae kuva aloittaaksesi säiliön

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Kun lataus on valmis, napsauta käynnistä-painiketta, joka avaa kokoonpanon asetukset

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Avaa "Valinnaiset asetukset" -valikko

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Valitse haluttu isäntäportti, jolla voit nähdä sovelluksesi toimivan Dockerissa

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Napsauta "Käynnistä" säiliön käynnistämiseksi

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Tärkeä
Varmista, että muistat antamasi mukautetun isäntäportin numeron, koska sitä tarvitaan myöhemmin.
:::

## 3. Sovelluksesi käynnistäminen {#3-running-your-app}

Kun säiliö on luotu, webforJ-sovelluksia voidaan suorittaa säiliössä sen sijaan, että niitä suoritetaan paikallisesti. Ensin on tarpeen konfiguroida projektisi POM-tiedosto oikein. Kun tämä on tehty, siirtyminen tiettyyn URL-osoitteeseen selaimessa näyttää sovelluksen.

### POM-tiedostosi konfigurointi {#configuring-your-pom-file}

webforJ-projektin suorittaminen Docker-säiliössä vaatii webforJ Install Pluginin käyttöä, joka voidaan konfiguroida POM-tiedostosi avulla:

Luo uusi `<plugin>`-merkintä `<plugins>`-osioon POM:issa. Seuraava koodi näyttää aloitusmerkinnän, jota voidaan käyttää ja muokata tarpeen mukaan projektillesi:

:::important
Jos POM-tiedostosi ei sisällä `<plugins>`-osaa, luo yksi.
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

Kun yllä olevan kaltainen merkintä on luotu, personoi seuraavat tiedot:

- Muuta `<deployurl>`-merkintä käyttämään porttinumeroa, joka vastaa **Isäntäporttia**, jonka määritit säiliöllesi edellisessä vaiheessa.

- Varmista, että `<classname>`-merkintä vastaa sovelluksen nimeä, jonka haluat suorittaa.

- Jos `<username>` ja `<password>` -tunnuksesi ovat erilaiset BBj-asennuksellesi, muuta nämä.

### Käynnistäjäprojektin käyttäminen {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Sovelluksen käynnistäminen {#launching-the-app}

Kun tämä on tehty, suorita `mvn install` projektisi hakemistossa. Tämä suorittaa webforJ-installointi pluginin ja mahdollistaa pääsyn sovellukseesi. Näet sovelluksen siirtymällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerille määrittämällä isäntäportilla, ja `YourPublishName` korvataan POM:in `<publishname>`-tagin sisällöllä. Jos kaikki on oikein tehty, sinun pitäisi nähdä sovelluksesi renderöitynä.
