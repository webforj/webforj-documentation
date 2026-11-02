---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: 5fbe602fa83b6147a07c4e2ad8ac3230
---
# Lokale Installatie

:::danger Verouderd en zal worden verwijderd
De webforJ-plugin van de BBj Plugin Manager die op deze pagina wordt beschreven, is verouderd en zal in een toekomstige release worden verwijderd. Begin geen nieuwe projecten met deze plugin. Verplaats bestaande projecten naar [BBjShip](/docs/configuration/bbjship/overview), dat webforJ-apps installeert, implementeert en uitvoert in BBjServices. [Bestaande projecten](/docs/configuration/bbjship/existing-projects) vermeldt de wijzigingen die een project moet ondergaan.
:::

Dit sectie van de documentatie behandelt de stappen die alleen vereist zijn voor gebruikers die webforJ willen gebruiken voor web- en/of app-ontwikkeling met een lokale BBj-instantie op hun machine. Deze installatie maakt het gebruikers niet mogelijk om bij te dragen aan de webforJ-fondscode zelf.
<br/>

:::info
Deze handleiding behandelt de installatie op een Windows-systeem - de installatie stappen kunnen variëren voor Mac/Linux OS-apparaten.
:::
<br/>

De installatie zal worden onderverdeeld in de volgende stappen:


1. BBj downloaden en installeren
2. Gebruik maken van de BBj Plugin Manager om uw app te creëren
3. Uw app starten


:::tip Vereisten
Voordat u begint, zorg ervoor dat u de nodige [vereisten](../../introduction/prerequisites) hebt doorgenomen voor het instellen en gebruiken van webforJ. Dit zorgt ervoor dat u alle vereiste tools en configuraties hebt voordat u met uw project begint.
:::


## 1. BBj downloaden en installeren {#1-bbj-download-and-installation}

<b>Wanneer u deze stap volgt, zorg ervoor dat u de BBj-versie installeert die overeenkomt met dezelfde webforJ-versie. </b><br/><br/>

[Deze video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kan helpen bij de installatie van BBj als u hulp nodig heeft bij de setup. Het installatiegedeelte van de BASIS-website is te vinden [via deze link](https://basis.cloud/download-product)

:::tip
Het wordt aanbevolen om de nieuwste stabiele revisie van BBj te gebruiken en "BBj" te selecteren uit de lijst met opties, zonder Barista of Addon.
:::

<a name='section3'></a>

## 2. Installeer en configureer de webforJ-plugin

Zodra BBj is geïnstalleerd, kan de Plugin Manager worden geopend om tools te installeren die nodig zijn om webforJ te configureren. Typ om te beginnen "Plugin Manager" in het startmenu of Finder.

![Plugin manager configuratie](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nadat de pluginmanager is geopend, navigeert u naar het tabblad "Beschikbare Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_2l.png#rounded-border)

Selecteer in dit gedeelte het selectievakje "Toon versies onder ontwikkeling"

![Plugin manager configuratie](/img/bbj-installation/local/Step_3l.png#rounded-border)

De DWCJ-invoer zou nu zichtbaar moeten zijn in de lijst met beschikbare plugins voor download. Klik op deze invoer in de lijst om deze te selecteren.

![Plugin manager configuratie](/img/bbj-installation/local/Step_4l.png#rounded-border)

Met de DWCJ-invoer geselecteerd, klikt u op de knop "Installeren".

![Plugin manager configuratie](/img/bbj-installation/local/Step_5l.png#rounded-border)

Zodra de plugin is geïnstalleerd, klikt u op het tabblad "Geïnstalleerde Plugins" bovenaan.

![Plugin manager configuratie](/img/bbj-installation/local/Step_6l.png#rounded-border)

Dit tabblad toont de geïnstalleerde plugins, die nu de DWCJ-invoer moeten bevatten. Klik op de invoer binnen de lijst.

![Plugin manager configuratie](/img/bbj-installation/local/Step_7l.png#rounded-border)

Met de DWCJ-invoer geselecteerd, klikt u op de knop "Configureer".

![Plugin manager configuratie](/img/bbj-installation/local/Step_8l.png#rounded-border)

In het geopende venster, klikt u op de knop "Remote Install Maven inschakelen" linksonder in het venster.


![Plugin manager configuratie](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip

U kunt ook navigeren naar de `bin` map binnen uw `bbx` map en de volgende opdracht uitvoeren:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Een dialoogvenster moet verschijnen waarin staat dat de externe installatie is ingeschakeld. Klik op "OK" om dit dialoogvenster te sluiten.

## 3. Gebruik het starterproject
Zodra BBj en de vereiste webforJ-plugin zijn geïnstalleerd en geconfigureerd, kunt u een nieuw, gestructureerd project maken vanuit de opdrachtregel. Dit project wordt geleverd met de nodige tools om uw eerste webforJ-programma uit te voeren.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. De app starten

Zodra dit is gedaan, voert u een `mvn install` uit in uw projectdirectory. Dit zal de webforJ-installatieplugin uitvoeren en u in staat stellen om toegang te krijgen tot uw app. Om de app te zien, gaat u naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-poort die u met Docker heeft geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen het `<publishname>`-tag van de POM.
Als het correct is gedaan, zou u uw app moeten zien renderen.
