---
sidebar_position: 4
_i18n_hash: a41d592f84a4dcd32f5398f3e57621a4
---
# Lokale Installatie

Dit gedeelte van de documentatie behandelt de stappen die vereist zijn voor gebruikers die webforJ willen gebruiken voor web- en/of app-ontwikkeling met een lokale BBj-instantie op hun machine. Deze installatie staat gebruikers niet toe bij te dragen aan de webforJ funderingscode zelf.
<br/>

:::info
Deze handleiding behandelt de installatie op een Windows-systeem - de installatie stappen kunnen variëren voor Mac/Linux besturingssystemen.
:::
<br/>

De installatie wordt onderverdeeld in de volgende stappen:


1. BBj downloaden en installeren
2. Gebruik van de BBj Plugin Manager om je app te maken
3. Je app starten


:::tip Vereisten
Voordat je begint, zorg ervoor dat je de nodige [vereisten](../../introduction/prerequisites) voor het opzetten en gebruiken van webforJ hebt doorgenomen. Dit zorgt ervoor dat je alle vereiste tools en configuraties hebt voordat je met je project begint.
:::


## 1. BBj downloaden en installeren {#1-bbj-download-and-installation}

<b>Zorg ervoor dat je tijdens deze stap de BBj-versie installeert die overeenkomt met dezelfde webforJ-versie.</b><br/><br/>

[Deze video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kan helpen met de installatie van BBj als je hulp nodig hebt bij de installatie. Het installatiegedeelte van de BASIS-website is te vinden [op deze link](https://basis.cloud/download-product)

:::tip
Het wordt aanbevolen om de nieuwste stabiele revisie van BBj te gebruiken en "BBj" uit de lijst met opties te selecteren, zonder Barista of Addon.
:::


<a name='section3'></a>

## 2. Installeer en configureer de webforJ-plugin

Zodra BBj is geïnstalleerd, kan de Plugin Manager worden geopend om de benodigde tools voor het configureren van webforJ te installeren. Typ om te beginnen "Plugin Manager" in het startmenu of de Finder.

![Plugin manager configuratie](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nadat de plugin manager is geopend, navigeer je naar het tabblad "Beschikbare Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_2l.png#rounded-border)

Zorg ervoor dat je het vakje "Toon versies in ontwikkeling" aanvinkt.

![Plugin manager configuratie](/img/bbj-installation/local/Step_3l.png#rounded-border)

De DWCJ-vermelding zou nu zichtbaar moeten zijn in de lijst van beschikbare plugins om te downloaden. Klik op deze vermelding in de lijst om deze te selecteren.

![Plugin manager configuratie](/img/bbj-installation/local/Step_4l.png#rounded-border)

Met de DWCJ-vermelding geselecteerd, klik op de knop "Installeren".

![Plugin manager configuratie](/img/bbj-installation/local/Step_5l.png#rounded-border)

Zodra de plugin is geïnstalleerd, klik je op het tabblad "Geïnstalleerde Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_6l.png#rounded-border)

Dit tabblad toont geïnstalleerde plugins, die nu de DWCJ-vermelding zouden moeten bevatten. Klik op de vermelding in de lijst.

![Plugin manager configuratie](/img/bbj-installation/local/Step_7l.png#rounded-border)

Met de DWCJ-vermelding geselecteerd, klik op de knop "Configureer".

![Plugin manager configuratie](/img/bbj-installation/local/Step_8l.png#rounded-border)

In het venster dat opent, klik je op de knop "Enable Maven Remote Install" linksonder in het venster.

![Plugin manager configuratie](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

Alternatief, navigeer naar de `bin`-directory binnen je `bbx`-map en voer de volgende opdracht uit:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Een dialoog moet verschijnen waarin staat dat de externe installatie is ingeschakeld. Klik op "OK" om deze dialoog te sluiten.

## 3. Gebruik van het starterproject
Zodra BBj en de vereiste webforJ-plugin zijn geïnstalleerd en geconfigureerd, kun je een nieuw, gestructureerd project aanmaken vanuit de opdrachtregel. Dit project wordt geleverd met de noodzakelijke tools om je eerste webforJ-programma uit te voeren.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. De app starten

Zodra dit is gedaan, voer je een `mvn install` uit in je projectdirectory. Dit zal de webforJ install plugin uitvoeren, en je in staat stellen toegang te krijgen tot je app. Om de app te zien, ga je naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de hostpoort die je hebt geconfigureerd met Docker, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>`-tag van de POM. 
Als het goed is gedaan, zou je je app moeten zien renderen.
