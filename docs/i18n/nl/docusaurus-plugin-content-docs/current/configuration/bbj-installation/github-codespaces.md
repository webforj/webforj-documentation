---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: e9d0c9402dcba748eea3671a39562b83
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) is geconfigureerd om te draaien in Github Codespaces. Codespaces is een cloud-gebaseerde ontwikkelomgeving en stelt je in staat om webforJ-applicaties direct in je browser te ontwikkelen en uit te voeren. Volg de onderstaande stappen om te beginnen met ontwikkelen met deze tool:

## 1. Navigeer naar de HelloWorldJava repository {#1-navigate-to-the-helloworldjava-repository}

Om te beginnen, moet je naar de HelloWorldJava repository gaan, die te vinden is [via deze link](https://github.com/webforj/webforj-hello-world). Klik daar op de groene **"Use this template"** knop en vervolgens op de optie **"Open in a codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Je programma uitvoeren {#2-running-your-program}

Nadat je gewacht hebt tot de codespace is geladen, zou je een browser versie van VS Studio Code moeten zien met het "HelloWorldJava" voorbeeldprogramma geladen. Hier kun je het voorbeeldprogramma uitvoeren, of beginnen met ontwikkelen.

Om een programma te compileren, open je de terminal in VS Code en voer je het commando `mvn install` uit.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Als alles succesvol is voltooid, zou je de boodschap `BUILD SUCCESS` moeten zien.

:::warning WAARSCHUWING 
Zorg ervoor dat je het commando `mvn install` gebruikt in plaats van de ingebouwde Maven-interface van VS Code voor het installeren van je programma.
:::

Zodra dit is gedaan, moet je naar een specifieke webadres navigeren om je programma te zien. Klik hiervoor eerst op het tabblad **"Ports"** onderaan VS Code. Hier zie je twee poorten, 8888 en een andere, vermeld.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klik op de kleine **"Open in Browser"** knop, die op een globe lijkt, in de sectie **"Local Address"** van het **Ports** tabblad, wat een nieuw tabblad in je browser opent.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wanneer het browsertabblad is geopend, wil je iets toevoegen aan het einde van de URL om ervoor te zorgen dat je app wordt uitgevoerd. Voeg eerst een `/webapp` toe aan het einde van het webadres, dat eindigt op `github.dev`. Voeg daarna de juiste app- en klasnaam (indien van toepassing) toe om de gewenste app te tonen. Volg [deze gids](./configuration) om te zien hoe je de URL correct configureert.

:::success Tip
Als je het standaard "Hello World" programma wilt draaien, voeg je simpelweg `/hworld` toe na de `/webapp` in de URL:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Zodra dit is gedaan, zou je je app in de browser moeten zien draaien en kun je deze aanpassen in de VS Code-instantie die draait binnen codespaces.
