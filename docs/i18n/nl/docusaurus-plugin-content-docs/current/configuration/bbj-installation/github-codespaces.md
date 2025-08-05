---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: ece2c4669673edd4de7ed74c967c9e4f
---
import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) is geconfigureerd om te draaien in Github Codespaces. Codespaces is een cloud-gebaseerde ontwikkelomgeving en stelt je in staat om webforJ-toepassingen direct binnen je browser te ontwikkelen en uit te voeren. Volg de onderstaande stappen om aan de slag te gaan met deze tool:

## 1. Navigeer naar de HelloWorldJava repository {#1-navigate-to-the-helloworldjava-repository}

Om te beginnen, moet je naar de HelloWorldJava repository gaan, die te vinden is [via deze link](https://github.com/webforj/webforj-hello-world). Klik daar op de groene **"Gebruik deze template"** knop en vervolgens op de optie **"Open in een codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Je programma uitvoeren {#2-running-your-program}

Nadat je hebt gewacht tot de codespace is geladen, zou er een browserversie van VS Studio Code moeten openen met het "HelloWorldJava" voorbeeldprogramma geladen. Van hieruit kun je het voorbeeldprogramma uitvoeren of beginnen met ontwikkelen.

Om een programma te compileren, open je de terminal in VS Code en voer je het commando `mvn install` uit.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Als alles succesvol is voltooid, zou je de boodschap `BUILD SUCCESS` moeten zien.

:::warning WAARSCHUWING 
Zorg ervoor dat je het commando `mvn install` gebruikt in plaats van de ingebouwde Maven-interface van VS Code voor het installeren van je programma.
:::

Zodra dit is gedaan, moet je naar een specifiek webadres navigeren om je programma te zien. Klik hiervoor eerst op het tabblad **"Ports"** onderaan VS Code. Hier zie je twee poorten, 8888, en een andere, vermeld.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klik op de kleine **"Open in Browser"** knop, die op een globe lijkt, in de sectie **"Local Address"** van het **Ports** tabblad, wat een nieuw tabblad in je browser opent.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wanneer het browsertabblad is geopend, moet je aan het einde van de URL iets toevoegen om ervoor te zorgen dat je app wordt uitgevoerd. Voeg eerst een `/webapp` toe aan het einde van het webadres, dat eindigt op `github.dev`. Voeg daarna de juiste app- en klassenaam (indien van toepassing) toe om de gewenste app te tonen. Om te zien hoe je de URL correct kunt instellen, [volg deze gids](./configuration).

:::success Tip
Als je het standaard "Hello World" programma wilt uitvoeren, voeg dan eenvoudig `/hworld` toe na de `/webapp` in de URL:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Zodra dit is gedaan, zou je je app in de browser moeten zien draaien en kun je het aanpassen in de VS Code-instantie die binnen codespaces draait.
