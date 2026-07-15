---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: c6cfdc6b07db675d741ea4a096f286ca
---
# Lokale Installatie

Dit gedeelte van de documentatie behandelt de stappen die alleen vereist zijn voor gebruikers die webforJ willen gebruiken voor web- en/of app-ontwikkeling met een lokale BBj-instantie op hun machine. Deze installatie stelt gebruikers niet in staat bij te dragen aan de webforJ foundation code zelf.
<br/>

:::info
Deze handleiding behandelt de installatie op een Windows-systeem - de installatie
stappen kunnen variëren voor Mac/Linux OS-apparaten.
:::
<br/>

De installatie wordt opgesplitst in de volgende stappen:

1. BBj-download en installatie
2. Gebruik van de BBj Plugin Manager om je app te maken
3. Je app starten

:::tip Vereisten
Voordat je begint, zorg ervoor dat je de noodzakelijke [vereisten](../../introduction/prerequisites) voor het opzetten en gebruiken van webforJ hebt doorgenomen. Dit zorgt ervoor dat je alle benodigde tools en configuraties hebt voordat je met je project begint.
:::


## 1. BBj-download en installatie {#1-bbj-download-and-installation}

<b>Bij het volgen van deze stap, zorg ervoor dat je de BBj-versie installeert die overeenkomt met dezelfde webforJ-versie.</b><br/><br/>

[Deze video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kan helpen bij de installatie van BBj als je hulp nodig hebt bij de opzet. Het installatiegedeelte van de BASIS-website is te vinden [via deze link](https://basis.cloud/download-product).

:::tip
Het wordt aanbevolen om de nieuwste stabiele revisie van BBj te gebruiken, en om "BBj" te selecteren uit de lijst van opties, zonder Barista of Addon.
:::


<a name='section3'></a>

## 2. Installeer en configureer de webforJ-plugin

Zodra BBj is geïnstalleerd, kan de Plugin Manager worden geopend om de nodige tools voor het configureren van webforJ te installeren. Om te beginnen, typ "Plugin Manager" in het startmenu of Finder.

![Plugin manager configuratie](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nadat de plugin manager is geopend, navigeer naar het tabblad "Beschikbare Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_2l.png#rounded-border)

Zodra je in dit gedeelte bent, vink je het selectievakje "Toon versies in ontwikkeling" aan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_3l.png#rounded-border)

De DWCJ-invoer zou nu zichtbaar moeten zijn in de lijst met beschikbare plugins voor download. Klik op deze invoer in de lijst om het te selecteren.

![Plugin manager configuratie](/img/bbj-installation/local/Step_4l.png#rounded-border)

Met de DWCJ-invoer geselecteerd, klik je op de knop "Installeren".

![Plugin manager configuratie](/img/bbj-installation/local/Step_5l.png#rounded-border)

Zodra de plugin is geïnstalleerd, klik je op het tabblad "Geïnstalleerde Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_6l.png#rounded-border)

Dit tabblad toont geïnstalleerde plugins, die nu de DWCJ-invoer moeten bevatten. Klik op de invoer in de lijst.

![Plugin manager configuratie](/img/bbj-installation/local/Step_7l.png#rounded-border)

Met de DWCJ-invoer geselecteerd, klik je op de knop "Configureren".

![Plugin manager configuratie](/img/bbj-installation/local/Step_8l.png#rounded-border)

In het geopende venster klik je op de knop "Maven Remote Install inschakelen" linksonder in het venster.

![Plugin manager configuratie](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip

Alternatief kun je navigeren naar de `bin`-directory binnen je `bbx`-map en de volgende opdracht uitvoeren:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Een dialoogvenster zou moeten verschijnen waarin staat dat de externe installatie is ingeschakeld. Klik op "OK" om dit dialoogvenster te sluiten.


## 3. Gebruik het starterproject
Zodra BBj en de vereiste webforJ-plugin zijn geïnstalleerd en geconfigureerd, kun je een nieuw, gestructureerd project vanaf de opdrachtregel maken. Dit project wordt geleverd met de nodige tools om je eerste webforJ-programma uit te voeren.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. De app starten

Zodra dit is gedaan, voer je `mvn install` uit in je projectdirectory. Dit zal de webforJ-installatieplugin uitvoeren, en je in staat stellen toegang te krijgen tot je app. Om de app te zien, moet je naar de volgende URL gaan:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-poort die je met Docker hebt geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>`-tag van de POM. Als het correct is gedaan, zou je moeten zien dat je app wordt weergegeven.
