---
sidebar_position: 1
title: Docker
_i18n_hash: 8cc797ca5ca7e8ba3a8cd9f3aec41d74
---
# Dockerin asennus

Tässä asiakirjan osassa käsitellään vaiheita, jotka käyttäjien tulee käydä läpi, jos he haluavat kehittää käyttäen Dockeria. Muutokset koodissasi tehdään kehityskoneellasi, ja tuloksena oleva sovellus suoritetaan Dockerissa.

## 1. Dockerin lataaminen {#1-downloading-docker}

Dockerin asennusprosessi vaihtelee hieman Windows-, Mac- ja Linux-käyttäjien välillä. Katso alla oleva osio, joka vastaa käyttöjärjestelmääsi.

### Windows {#windows}

:::info
Suosittelemme lataamaan Windows Subsystem for Linuxin uusimman version. Lisätietoja löytyy [tästä linkistä](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Windows lataussivulle: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Napsauta "Get Docker Desktop for Windows" -painiketta ladataksesi asennustiedoston.

**2. Asenna Docker Desktop:**
>- Suorita ladattu asennustiedosto.
>- Seuraa asennusvelhoa ja varmista, että Hyper-V on käytössä (jos sitä kysytään), koska Docker for Windows käyttää Hyper-V:tä virtualisointiin.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Vahvista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

### Mac {#mac}

**1. Lataa Docker Desktop:**
>- Siirry Docker Desktop for Mac lataussivulle: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Asenna Docker Desktop:**
>- Suorita ladattu asennustiedosto.
>- Kun asennus on valmis, Docker Desktop käynnistyy automaattisesti.

**3. Vahvista asennus:**
>- Avaa terminaali ja suorita komento `docker --version` varmistaaksesi, että Docker on asennettu ja toimii oikein.

## 2. Konfigurointi {#2-configuration}

Kun Docker Desktop on ladattu, etsi uusinta webforJ-kuvaa, joka on tällä hetkellä nimellä `webforj/sandbox`.

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Napsauta tag-listaa nähdäksesi käytettävissä olevat vaihtoehdot

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Valitse "rc" viimeisimmän version vuoksi

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Hae kuva käynnistääksesi konttisi

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Kun lataus on valmis, napsauta suorituspainiketta, joka avaa konfigurointiasetukset

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Avaa "Valinnaiset asetukset" -valikko

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Valitse haluamasi isäntäportti, josta voit nähdä sovelluksesi toimivan Dockerissa

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Napsauta "Suorita" käynnistääksesi kontin

![DWCJ Kuva Haku](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Tärkeää
Varmista, että muistat antamasi mukautetun isäntäportin numeron, koska sitä tarvitaan myöhemmin.
:::

## 3. Sovelluksesi suorittaminen {#3-running-your-app}

Kun kontti on luotu, webforJ-sovelluksia voidaan suorittaa kontin sisällä sen sijaan, että ne suoritettaisiin paikallisesti. Ensinnäkin on tarpeen konfiguroida projektisi POM-tiedosto oikein. Kun tämä on tehty, siirtyminen tiettyyn URL-osoitteeseen selaimessa näyttää sovelluksen.

### POM-tiedostosi konfigurointi {#configuring-your-pom-file}

WebforJ-projektin suorittaminen Docker-kontissa vaatii webforJ Install Pluginin käyttöä, joka voidaan konfiguroida POM-tiedostosi avulla:

Luo uusi `<plugin>`-merkintä `<plugins>`-osioon POM-tiedostossa. Alla oleva koodi näyttää aloitusmerkinnän, jota voidaan käyttää ja muokata tarpeen mukaan projektisi vaatimusten mukaiseksi:

:::important
Jos POM-tiedostossasi ei ole `<plugins>`-osaa, luo sellainen.
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

Kun yllä olevaan kaltaista merkintää on muokattu, mukauta seuraavat tiedot:

- Muuta `<deployurl>`-merkintä käyttämään porttinumeroa, joka vastaa **Isäntäporttia**, jonka konfigurointi tehtiin edellisessä vaiheessa.

- Varmista, että `<classname>`-merkintä vastaa sen sovelluksen nimeä, jonka haluat suorittaa.

- Jos `<username>` ja `<password>` -tunnuksesi ovat erilaiset BBj-asennuksellesi, muuta nämä.

### Aloitusprojektin käyttäminen {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Sovelluksen käynnistäminen {#launching-the-app}

Kun tämä on tehty, suorita `mvn install` projektikansiossasi. Tämä suorittaa webforJ-install-pluginin ja mahdollistaa sovelluksesi käyttämisen. Näet sovelluksen siirtymällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerille konfiguroimallasi isäntäportilla ja `YourPublishName` korvataan POMin `<publishname>`-tagin sisältämällä tekstillä. Jos kaikki on tehty oikein, sinun pitäisi nähdä sovelluksesi renderöityvän.
