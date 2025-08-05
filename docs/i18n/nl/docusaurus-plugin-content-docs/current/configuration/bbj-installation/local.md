---
sidebar_position: 4
_i18n_hash: 9cf48c2124910b9a10a8ec4c5cd82205
---
# Lokale Installatie

Dit gedeelte van de documentatie behandelt de stappen die alleen vereist zijn voor gebruikers die webforJ willen gebruiken voor web- en/of app-ontwikkeling met een lokale BBj-instantie op hun machine. Deze installatie staat gebruikers niet toe bij te dragen aan de webforJ foundation code zelf.
<br/>

:::info
Deze handleiding behandelt de installatie op een Windows-systeem - de installatiestappen kunnen variëren voor Mac/Linux-besturingssystemen.
:::
<br/>

De installatie wordt onderverdeeld in de volgende stappen:

1. BBj downloaden en installeren
2. De BBj Plugin Manager gebruiken om je app te maken
3. Je app lanceren

:::tip Vereisten
Voordat je begint, zorg ervoor dat je de noodzakelijke [vereisten](../../introduction/prerequisites) voor het opzetten en gebruiken van webforJ hebt doorgenomen. Dit zorgt ervoor dat je alle benodigde tools en configuraties hebt voordat je met je project begint.
:::

## 1. BBj downloaden en installeren {#1-bbj-download-and-installation}

<b>Bij het volgen van deze stap, zorg ervoor dat je de BBj-versie installeert die overeenkomt met dezelfde webforJ-versie.</b><br/><br/>

[Deze video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kan helpen bij de installatie van BBj als je hulp nodig hebt met de setup. Het installatiegedeelte van de BASIS-website is te vinden [via deze link](https://basis.cloud/download-product).

:::tip
Het wordt aanbevolen om de nieuwste stabiele revisiebouw van BBj te gebruiken en "BBj" uit de lijst van opties te selecteren, zonder Barista of Addon.
:::

<a name='section3'></a>

## 2. Installeer en configureer de webforJ-plugin

Zodra BBj is geïnstalleerd, kan de Plugin Manager worden geopend om de tools te installeren die nodig zijn om webforJ te configureren. Typ om te beginnen "Plugin Manager" in het startmenu of Finder.

![Plugin manager configuratie](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nadat de plugin manager is geopend, navigeer naar het tabblad "Beschikbare Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_2l.png#rounded-border)

Zorg ervoor dat je het selectievakje "Toon versies in ontwikkeling" aanvinkt.

![Plugin manager configuratie](/img/bbj-installation/local/Step_3l.png#rounded-border)

De DWCJ-entry zou nu zichtbaar moeten zijn in de lijst met beschikbare plugins voor download. Klik op deze entry in de lijst om deze te selecteren.

![Plugin manager configuratie](/img/bbj-installation/local/Step_4l.png#rounded-border)

Klik met de DWCJ-entry geselecteerd op de knop "Installeren".

![Plugin manager configuratie](/img/bbj-installation/local/Step_5l.png#rounded-border)

Zodra de plugin is geïnstalleerd, klik je op het tabblad "Geïnstalleerde Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_6l.png#rounded-border)

Dit tabblad toont de geïnstalleerde plugins, die nu de DWCJ-entry zouden moeten bevatten. Klik op de entry in de lijst.

![Plugin manager configuratie](/img/bbj-installation/local/Step_7l.png#rounded-border)

Met de DWCJ-entry geselecteerd, klik je op de knop "Configureren".

![Plugin manager configuratie](/img/bbj-installation/local/Step_8l.png#rounded-border)

In het venster dat opent, klik je op de knop "Maven Remote Install inschakelen" linksonder in het venster.

![Plugin manager configuratie](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

Alternatief, navigeer naar de `bin` directory binnen je `bbx` map en voer de volgende opdracht uit:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Een dialoogvenster zou moeten verschijnen waarin staat dat de remote installatie is ingeschakeld. Klik op "OK" om dit dialoogvenster te sluiten.

## 3. Gebruik maken van het starterproject
Zodra BBj en de vereiste webforJ-plugin zijn geïnstalleerd en geconfigureerd, kun je een nieuw, gestructureerd project maken vanuit de opdrachtregel. Dit project komt met de noodzakelijke tools om je eerste webforJ-programma uit te voeren.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. De app lanceren

Als dit is gedaan, voer je een `mvn install` uit in je projectdirectory. Dit zal de webforJ-installatieplugin uitvoeren en je in staat stellen je app te openen. Om de app te zien, ga je naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-poort die je met Docker hebt geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>` tag van de POM. 
Als alles goed is gedaan, zou je je app moeten kunnen weergeven.
