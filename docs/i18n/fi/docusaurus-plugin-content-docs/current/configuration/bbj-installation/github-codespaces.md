---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: e9d0c9402dcba748eea3671a39562b83
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) on ollut määritelty toimimaan Github Codespacesissa. Codespaces on pilvipohjainen kehitysympäristö, joka mahdollistaa webforJ-sovellusten kehittämisen ja suorittamisen suoraan selaimessasi. Aloittaaksesi kehittämisen tämän työkalun kanssa, seuraa alla olevia vaiheita:

## 1. Siirry HelloWorldJava-repositorioon {#1-navigate-to-the-helloworldjava-repository}

Aloittaaksesi sinun täytyy mennä HelloWorldJava-repositorioon, joka löytyy [tästä linkistä](https://github.com/webforj/webforj-hello-world). Kun olet siellä, napsauta vihreää **"Käytä tätä mallia"** -painiketta ja sitten **"Avaa kooditilassa"** -vaihtoehtoa.

![Kooditilan painikkeet](/img/bbj-installation/github/1.png#rounded-border)

## 2. Ohjelman suorittaminen {#2-running-your-program}

Kun kooditilan lataaminen on valmis, sinun pitäisi nähdä selaimessa VS Studio Coden versio, jossa "HelloWorldJava" esimerkkiohjelma on ladattuna. Täältä voit suorittaa esimerkkiohjelman tai aloittaa kehittämisen.

Kompiloidaksesi ohjelman, avaa terminaali VS Codessa ja suorita komento `mvn install`.

![Maven Asennus](/img/bbj-installation/github/2.png#rounded-border)

Jos kaikki sujuu onnistuneesti, näet `BUILD SUCCESS` -viestin.

:::warning VAROITUS
Varmista, että käytät `mvn install` -komentoa sen sijaan, että käyttäisit VS Coden sisäänrakennettua Maven-käyttöliittymää ohjelmasi asentamiseen.
:::

Kun tämä on tehty, sinun täytyy siirtyä tiettyyn verkkosoitteeseen nähdäksesi ohjelmasi. Tämä tapahtuu ensin napsauttamalla **"Ports"**-välilehteä VS Coden alareunassa. Täällä näet kaksi porttia, 8888, ja yhden muun, lueteltuna.

![Edistetään Portteja](/img/bbj-installation/github/3.png#rounded-border)

Napsauta pientä **"Avaa selaimessa"** -painiketta, joka näyttää maapallon, **"Local Address"** -osiossa **Ports**-välilehdellä, mikä avaa uuden välilehden selaimessasi.

![Selaimen Painike](/img/bbj-installation/github/4.png#rounded-border)

Kun selaimen välilehti on avoinna, haluat lisätä URL-osoitteen loppuun varmistaaksesi, että sovelluksesi ajetaan. Ensiksi lisää `/webapp` verkkosivuston osoitteen loppuun, joka päättyy `github.dev`. Tämän jälkeen lisää oikea sovellus- ja luokanimi (jos sovellettavissa) näyttämään haluttu sovellus. Näet, miten URL-osoite on oikein määritettävä, [seuraamalla tätä opasta](./configuration).

:::success Vinkki
Jos haluat suorittaa oletus "Hello World" -ohjelman, lisää yksinkertaisesti `/hworld` `/webapp` jälkeen URL-osoitteeseen:
<br />

![Muokattu URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Kun tämä on tehty, sinun pitäisi nähdä sovelluksesi toimivan selaimessa, ja voit muokata sitä VS Code -instanssissa, joka toimii kooditiloissa.
