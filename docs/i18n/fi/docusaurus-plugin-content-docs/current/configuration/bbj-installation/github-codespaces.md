---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: ece2c4669673edd4de7ed74c967c9e4f
---
import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) on osoitettu toimimaan Github Codespacesissa. Codespaces on pilvipohjainen kehitysympäristö, joka mahdollistaa webforJ-sovellusten kehittämisen ja suorittamisen suoraan selaimessasi. Aloittaaksesi kehittämisen tämän työkalun kanssa, seuraa alla olevia vaiheita:

## 1. Siirry HelloWorldJava-repositorioon {#1-navigate-to-the-helloworldjava-repository}

Aloittaaksesi sinun tulee siirtyä HelloWorldJava-repositorioon, joka löytyy [tästä linkistä](https://github.com/webforj/webforj-hello-world). Kun olet siellä, napsauta vihreää **"Käytä tätä mallia"** -painiketta, ja sitten **"Avaa kooditilassa"** vaihtoehtoa.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Ohjelmasi suorittaminen {#2-running-your-program}

Kun kooditilan lataaminen on valmis, sinun pitäisi nähdä selaimessasi VS Studio Code -versio, jossa on "HelloWorldJava" esimerkkiohjelma ladattuna. Täältä voit suorittaa esimerkkiohjelman tai aloittaa kehittämisen.

Voit kääntää ohjelmaa avaamalla terminaalin VS Code:ssa ja suorittamalla `mvn install` komennon.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Jos kaikki onnistuu, sinun pitäisi nähdä `BUILD SUCCESS` -viesti.

:::warning VAROITUS
Varmista, että käytät `mvn install` komentoa sen sijaan, että käyttäisit VS Code:n sisäänrakennettua Maven käyttöliittymää ohjelmasi asentamiseen.
:::

Kun tämä on tehty, sinun on navigoitava tiettyyn verkkosivustoon nähdäksesi ohjelmasi. Tämän tekemiseksi napsauta ensin **"Ports"**-välilehteä VS Code:n alareunassa. Täällä näet kaksi porttia, 8888, ja yhden muun, lueteltuna.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Napsauta pientä **"Avaa selaimessa"** -painiketta, joka muistuttaa maapalloa, **"Paikallinen osoite"** -osiossa **Ports**-välilehdellä, joka avaa uuden välilehden selaimessasi.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Kun selaimen välilehti on avoinna, sinun tulee lisätä URL-osoitteen loppuun, jotta sovelluksesi käynnistyy. Ensinnäkin, lisää `/webapp` verkkosivuston osoitteen loppuun, jonka tulee päättyä `github.dev`. Tämän jälkeen lisää oikea sovellus- ja luokan nimi (jos sovellettavissa) halutun sovelluksen näyttämiseksi. Näet, kuinka URL voidaan konfiguroida oikein, [seuraa tätä opasta](./configuration).

:::success Vinkki
Jos haluat suorittaa oletusarvoisen "Hello World" -ohjelman, lisää yksinkertaisesti `/hworld` `/webapp` jälkeen URL-osoitteeseen:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Kun tämä on tehty, sinun pitäisi nähdä sovelluksesi toimivana selaimessa, ja voit muokata sitä VS Code -instanssissa, joka toimii kooditiloissa.
