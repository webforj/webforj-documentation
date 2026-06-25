---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: dfb60fdaf7a9ffd31ee6fb920e0e8289
---
JRebel is een Java-ontwikkelingshulpmiddel dat integreert met de JVM om codewijzigingen te detecteren en gewijzigde klassen direct in het geheugen te vervangen, waardoor ontwikkelaars onmiddellijk codewijzigingen kunnen zien zonder de server opnieuw te starten.

Wanneer een wijziging wordt aangebracht in een klasse, methode of veld, compileert JRebel de bijgewerkte bytecode on-the-fly en injecteert deze, wat de noodzaak van een volledige serverherstart elimineert. Door wijzigingen direct toe te passen op de draaiende app, stroomlijnt JRebel de ontwikkelingsworkflow, bespaart het tijd en behoudt het de app-status, inclusief gebruikerssessies.

:::tip Frontend wijzigingen
Wijzigingen onder `src/main/frontend` worden behandeld door de [frontend watch](/docs/configuration/deploy-reload/frontend-watch), die deze opnieuw opbouwt en de browser vernieuwt naast de server.
:::

## Installatie {#installation}

De officiële JRebel-website biedt [snelle startinstructies](https://www.jrebel.com/products/jrebel/learn) om het product aan de praat te krijgen in verschillende populaire IDE's. Volg deze instructies om JRebel in uw ontwikkelomgeving te integreren.

Nadat de installatie is voltooid, opent u een webforJ-project en zorgt u ervoor dat de jetty `scan` eigenschap in het `pom.xml`-bestand is ingesteld op `0` om de automatische herstart van de server uit te schakelen. Zodra dit is gedaan, gebruikt u de volgende opdracht:

```bash
mvn jetty:run
```

Als het goed is gedaan, zal JRebel logboekinformatie naar de terminal uitvoeren en zouden wijzigingen die u in uw programma aanbrengt op verzoek zichtbaar moeten zijn.

:::info Uw wijzigingen bekijken
Als er een wijziging wordt aangebracht in een weergave of component die al wordt weergegeven, zal JRebel geen herlaad van de pagina afdwingen, omdat de server niet opnieuw wordt opgestart.
:::
