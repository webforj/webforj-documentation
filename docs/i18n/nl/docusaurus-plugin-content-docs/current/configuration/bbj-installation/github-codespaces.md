---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: 05f50845efd34767431faacb2e5dc97e
---
import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) is geconfigureerd om te draaien in Github Codespaces. Codespaces is een cloud-gebaseerde ontwikkelingomgeving en stelt je in staat om webforJ-applicaties direct binnen je browser te ontwikkelen en uit te voeren. Volg de onderstaande stappen om met deze tool te beginnen:

## 1. Navigeer naar de HelloWorldJava repository {#1-navigate-to-the-helloworldjava-repository}

Begin met het bezoeken van de HelloWorldJava repository, die je kunt vinden [via deze link](https://github.com/webforj/webforj-hello-world). Klik eenmaal daar op de groene **"Use this template"** knop en vervolgens op de optie **"Open in a codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Je programma uitvoeren {#2-running-your-program}

Nadat je hebt gewacht tot de codespace is geladen, zou je een browserversie van VS Studio Code moeten zien met het "HelloWorldJava" voorbeeldprogramma geladen. Vanaf hier kun je het voorbeeldprogramma uitvoeren of beginnen met ontwikkelen.

Om een programma te compileren, open je de terminal in VS Code en voer je het `mvn install` commando uit.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Als alles succesvol is voltooid, zou je de `BUILD SUCCESS` melding moeten zien.

:::warning WAARSCHUWING 
Zorg ervoor dat je het `mvn install` commando gebruikt in plaats van de ingebouwde Maven-interface van VS Code voor het installeren van je programma.
:::

Zodra dit is gedaan, moet je naar een specifiek webadres navigeren om je programma te zien. Klik hiervoor eerst op het **"Ports"** tabblad onderaan VS Code. Hier zie je twee poorten, 8888 en nog een andere, vermeld.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klik op de kleine **"Open in Browser"** knop, die een globe lijkt, in de **"Local Address"** sectie van het **Ports** tabblad, wat een nieuw tabblad in je browser opent.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wanneer het browsertabblad is geopend, wil je het einde van de URL aanpassen om ervoor te zorgen dat je app wordt uitgevoerd. Voeg eerst een `/webapp` toe aan het einde van het webadres, dat eindigt op `github.dev`. Voeg daarna de juiste app- en klasse-naam (indien van toepassing) toe om de gewenste app weer te geven. Om te zien hoe je de URL correct configureert, [volg deze gids](./configuration).

:::success Tip
Als je het standaard "Hello World" programma wilt uitvoeren, voeg dan eenvoudig `/hworld` toe na de `/webapp` in de URL:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::


Zodra dit is gedaan, zou je je app in de browser moeten zien draaien en kun je deze aanpassen in de VS Code-instantie die binnen codespaces draait.
