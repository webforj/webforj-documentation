---
sidebar_position: 2
title: Github Codespaces
description: >-
  Run the webforj-hello-world starter in a GitHub Codespace to develop and
  preview webforJ apps directly from the browser.
_i18n_hash: ffbe6dd8d2c6c81e95e7e97dbb8ff32e
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) on säädetty toimimaan Github Codespaces -ympäristössä. Codespaces on pilvipohjainen kehitysympäristö, joka mahdollistaa webforJ-sovellusten kehittämisen ja suorittamisen suoraan selaimessasi. Kehittämisen aloittamiseksi seuraa alla olevia vaiheita:

## 1. Siirry HelloWorldJava-repositioon {#1-navigate-to-the-helloworldjava-repository}

Aloittaaksesi sinun täytyy siirtyä HelloWorldJava-repositioon, joka löytyy [tästä linkistä](https://github.com/webforj/webforj-hello-world). Kun olet siellä, napsauta vihreää **"Use this template"** -painiketta ja sitten **"Open in a codespace"** -vaihtoehtoa.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Ohjelmasi suorittaminen {#2-running-your-program}

Odottamisen jälkeen, että codespace latautuu, sinun pitäisi nähdä selaimessa VS Studio Code -versio, jossa on "HelloWorldJava" esimerkkiohjelma ladattuna. Täältä voit suorittaa esimerkkiohjelman tai aloittaa kehittämisen.

Käännät ohjelman avaamalla terminaali VS Codessa ja suorittamalla `mvn install` -komennon.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Jos kaikki onnistuu, sinun pitäisi nähdä `BUILD SUCCESS` -viesti.

:::warning WARNING
Varmista, että käytät `mvn install` -komentoa ohjelmasi asentamiseen sen sijaan, että käyttäisit VS Code:n sisäänrakennettua Maven-käyttöliittymää.
:::

Kun tämä on tehty, sinun täytyy siirtyä tiettyyn verkkosivustoon nähdäksesi ohjelmasi. Tämä tehdään napsauttamalla ensin VS Code -ikkunan alareunassa olevaa **"Ports"**-välilehteä. Täällä näet kaksi porttia, 8888 ja yhden muun, lueteltuna.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Napsauta **"Local Address"** -osion **Ports**-välilehdellä pientä **"Open in Browser"** -painiketta, joka näyttää maapallolta, avaten uuden välilehden selaimessasi.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Kun selainvälilehti on auki, haluat lisätä URL-osoitteen loppuun varmistaaksesi, että sovelluksesi käynnistyy. Ensinnäkin lisää `/webapp` verkkosivun osoitteen loppuun, joka päättyy `github.dev`:hen. Tämän jälkeen lisää tarvittaessa oikea sovellus- ja luokan nimi näyttääksesi halutun sovelluksen. Näet, kuinka URL-osoite konfiguroidaan oikein, [seuraa tätä opasta](./configuration).

:::success Tip
Jos haluat suorittaa oletusarvoisen "Hello World" -ohjelman, lisää yksinkertaisesti `/hworld` `/webapp` -osuuden jälkeen URL-osoitteeseen:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Kun tämä on tehty, sinun pitäisi nähdä sovelluksesi toimivan selaimessa, ja voit muokata sitä Codespaces-ympäristössä toimivassa VS Code -instanssissa.
