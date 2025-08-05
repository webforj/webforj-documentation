---
title: JRebel
_i18n_hash: 9e2b7ce994eb40e656cf61dc4967ec7e
---
JRebel is een Java-ontwikkelingstool die integreert met de JVM om codewijzigingen te detecteren en gewijzigde klassen direct in het geheugen te vervangen, zodat ontwikkelaars codewijzigingen onmiddellijk kunnen zien zonder de server opnieuw te starten.

Wanneer een wijziging wordt aangebracht in een klasse, methode of veld, compileert en injecteert JRebel de bijgewerkte bytecode ter plaatse, waardoor een volledige herstart van de server niet nodig is. Door wijzigingen direct op de draaiende applicatie toe te passen, stroomlijnt JRebel de ontwikkelworkflow, bespaart tijd en behoudt de status van de app, inclusief gebruikerssessies.

## Installatie {#installation}

De officiële JRebel-website biedt [snelle startinstructies](https://www.jrebel.com/products/jrebel/learn) om het product aan de praat te krijgen in verschillende populaire IDE's. Volg deze instructies om JRebel in uw ontwikkelomgeving te integreren.

Nadat de setup is voltooid, opent u een webforJ-project en zorgt u ervoor dat de jetty `scan`-eigenschap in het `pom.xml`-bestand is ingesteld op `0` om de automatische herstart van de server uit te schakelen. Zodra dit is gedaan, gebruikt u de volgende opdracht:

```bash
mvn jetty:run
```

Als dit correct is gedaan, zal JRebel logboekinformatie naar de terminal uitvoeren, en wijzigingen die aan uw programma zijn aangebracht, zouden op aanvraag moeten worden weerspiegeld.

:::info Uw wijzigingen zien
Als een wijziging wordt aangebracht in een weergave of component die al wordt weergegeven, zal JRebel geen herlaadbeurt van de pagina forceren, aangezien de server niet wordt herstart.
:::
