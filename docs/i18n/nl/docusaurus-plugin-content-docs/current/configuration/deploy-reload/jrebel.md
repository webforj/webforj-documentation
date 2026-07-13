---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: 639c97ac6892efd7261824c13b7162da
---
JRebel is een Java-ontwikkelingshulpmiddel dat integreert met de JVM om codewijzigingen te detecteren en gewijzigde klassen direct in het geheugen te vervangen, zodat ontwikkelaars codewijzigingen onmiddellijk kunnen zien zonder de server opnieuw te starten.

Wanneer er een wijziging wordt aangebracht in een klasse, methode of veld, compileert en injecteert JRebel de bijgewerkte bytecode on-the-fly, waardoor de noodzaak voor een volledige herstart van de server wordt geëlimineerd. Door wijzigingen direct op de draaiende app toe te passen, stroomlijnt JRebel de ontwikkelingsworkflow, bespaart het tijd en behoudt het de status van de app, inclusief gebruikerssessies.

:::tip Frontend wijzigingen
Wijzigingen onder `src/main/frontend` worden afgehandeld door de [frontend watch](/docs/configuration/deploy-reload/frontend-watch), die deze opnieuw opbouwt en de browser bijwerkt naast de server.
:::

## Installatie {#installation}

De officiële JRebel-website biedt [snelle startinstructies](https://www.jrebel.com/products/jrebel/learn) om het product aan de praat te krijgen in verschillende populaire IDE's. Volg deze instructies om JRebel in uw ontwikkelomgeving te integreren.

Wanneer de installatie is voltooid, open een webforJ-project en zorg ervoor dat de jetty `scan`-eigenschap in het `pom.xml`-bestand is ingesteld op `0` om de automatische herstart van de server uit te schakelen. Zodra dit is gedaan, gebruik dan de volgende opdracht:

```bash
mvn jetty:run
```

Als dit goed is gedaan, zal JRebel logboekinformatie naar de terminal outputten, en wijzigingen die aan uw programma zijn aangebracht, zouden op verzoek moeten worden weergegeven.

:::info Uw wijzigingen zien
Als er een wijziging is aangebracht in een weergave of component die al wordt weergegeven, zal JRebel geen verplichte herlaad van de pagina afdwingen, aangezien de server niet opnieuw wordt gestart.
:::
