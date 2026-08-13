---
sidebar_position: 2
title: Github Codespaces
description: >-
  Run the webforj-hello-world starter in a GitHub Codespace to develop and
  preview webforJ apps directly from the browser.
_i18n_hash: ffbe6dd8d2c6c81e95e7e97dbb8ff32e
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) is geconfigureerd om te draaien in Github Codespaces. Codespaces is een cloudgebaseerde ontwikkelomgeving, en stelt je in staat om webforJ-applicaties rechtstreeks vanuit je browser te ontwikkelen en uit te voeren. Volg de onderstaande stappen om te beginnen met ontwikkelen met dit hulpmiddel:

## 1. Navigeer naar de HelloWorldJava repository {#1-navigate-to-the-helloworldjava-repository}

Om te beginnen, moet je naar de HelloWorldJava repository gaan, die je kunt vinden [via deze link](https://github.com/webforj/webforj-hello-world). Klik daar op de groene **"Use this template"** knop, en vervolgens op de optie **"Open in a codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Voer je programma uit {#2-running-your-program}

Nadat je hebt gewacht tot de codespace is geladen, zou je een browserversie van VS Studio Code moeten zien openen met het "HelloWorldJava" voorbeeldprogramma geladen. Vanaf hier kun je het voorbeeldprogramma uitvoeren of beginnen met ontwikkelen.

Om een programma te compileren, open de terminal in VS Code en voer het commando `mvn install` uit.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Als alles succesvol is voltooid, zou je het bericht `BUILD SUCCESS` moeten zien.

:::warning WAARSCHUWING
Zorg ervoor dat je het commando `mvn install` gebruikt in plaats van de ingebouwde Maven-interface van VS Code om je programma te installeren.
:::

Zodra dit is gedaan, moet je naar een specifieke webadres navigeren om je programma te zien. Klik hiervoor eerst op het tabblad **"Ports"** onderaan VS Code. Hier zie je twee poorten, 8888, en een andere, vermeld.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klik op de kleine **"Open in Browser"** knop, die eruitziet als een globe, in de sectie **"Local Address"** van het tabblad **Ports**, wat een nieuw tabblad in je browser opent.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wanneer het browsertabblad is geopend, wil je aan het einde van de URL toevoegen om ervoor te zorgen dat je app wordt uitgevoerd. Voeg eerst een `/webapp` toe aan het einde van het webadres, dat eindigt op `github.dev`. Voeg daarna de juiste app- en klasnaam (indien van toepassing) toe om de gewenste app te tonen. Om te zien hoe je de URL correct configureert, [volg deze gids](./configuration).

:::success Tip
Als je het standaard "Hello World" programma wilt uitvoeren, voeg dan eenvoudig `/hworld` toe na de `/webapp` in de URL:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Zodra dit is gedaan, zou je je app in de browser moeten zien draaien, en kun je deze aanpassen in de VS Code-instantie die draait binnen codespaces.
