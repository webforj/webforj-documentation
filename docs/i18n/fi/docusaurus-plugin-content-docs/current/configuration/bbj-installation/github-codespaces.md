---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: 05f50845efd34767431faacb2e5dc97e
---

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) on valmisteltu toimimaan Github Codespaces -ympäristössä. Codespaces on pilvipohjainen kehitysympäristö, joka mahdollistaa webforJ-sovellusten kehittämisen ja ajamisen suoraan selaimessasi. Aloittaaksesi kehittämisen tämän työkalun kanssa, seuraa alla olevia vaiheita:

## 1. Siirry HelloWorldJava-repositioon {#1-navigate-to-the-helloworldjava-repository}

Aloittaaksesi sinun täytyy mennä HelloWorldJava-repositioon, joka löytyy [tältä linkiltä](https://github.com/webforj/webforj-hello-world). Kun olet siellä, napsauta vihreää **"Käytä tätä mallia"** -painiketta ja sitten **"Avaa codespacessa"** -vaihtoehtoa.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Ohjelmasi ajaminen {#2-running-your-program}

Kun codespace on latautunut, sinun pitäisi nähdä selaimessa VS Studio Code -versio, jossa on ladattuna "HelloWorldJava" näyteohjelma. Täällä voit ajaa näyteohjelmaa tai aloittaa kehittämisen.

Ohjelman kääntämiseksi avaa terminaali VS Codessa ja suorita `mvn install` -komento.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Jos kaikki sujuu onnistuneesti, sinun pitäisi nähdä `BUILD SUCCESS` -viesti.

:::warning WARNING 
Varmista, että käytät `mvn install` -komentoa sen sijaan, että käyttäisit VS Coden sisäänrakennettua Maven -rajapintaa ohjelmasi asentamiseen.
:::

Kun tämä on tehty, sinun täytyy siirtyä tiettyyn verkkosoitteeseen nähdäksesi ohjelmasi. Tämän tekemiseksi, napsauta ensin **"Ports"** -välilehteä VS Coden alaosassa. Täällä näet kaksi porttia, 8888 ja yhden muun, lueteltuna.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Napsauta pientä **"Avaa selain"** -painiketta, joka muistuttaa maapalloa, **"Paikallinen osoite"** -osiossa **Ports** -välilehdellä, joka avaa uuden välilehden selaimessasi.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Kun selainvälilehti on avattu, haluat lisätä URL-osoitteen loppuun varmistaaksesi, että sovelluksesi ajetaan. Ensin lisää `/webapp` verkkosivuston osoitteen loppuun, joka päättyy `github.dev`. Sen jälkeen lisää oikea sovellus- ja luokan nimi (tarvittaessa) näyttääksesi halutun sovelluksen. Kattavan URL:n konfigurointiohjeen saamiseksi, [seuraa tätä oppaata](./configuration).

:::success Vinkki
Jos haluat ajaa oletus "Hello World" -ohjelmaa, lisää yksinkertaisesti `/hworld` `/webapp` -osoitteen jälkeen:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Kun tämä on tehty, sinun pitäisi nähdä sovelluksesi toimivan selaimessa ja voit muokata sitä VS Code -instanssissa, joka toimii codespacesissä.
