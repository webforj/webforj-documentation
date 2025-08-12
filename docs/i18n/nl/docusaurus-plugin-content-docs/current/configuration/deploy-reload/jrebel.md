---
title: JRebel
_i18n_hash: e0a60884cfab5835f788e6f225047d2c
---
JRebel is een Java-ontwikkelingshulpmiddel dat integreert met de JVM om codewijzigingen te detecteren en gewijzigde klassen direct in het geheugen te vervangen, zodat ontwikkelaars codewijzigingen onmiddellijk kunnen zien zonder de server opnieuw op te starten.

Wanneer er een wijziging wordt aangebracht aan een klasse, methode of veld, compileert en injecteert JRebel de bijgewerkte bytecode on-the-fly, waardoor de noodzaak voor een volledige herstart van de server wordt geëlimineerd. Door wijzigingen direct toe te passen op de draaiende app, stroomlijnt JRebel de ontwikkelingsworkflow, bespaart tijd en behoudt de status van de app, inclusief gebruikerssessies.

## Installatie {#installation}

De officiële JRebel-website biedt [snelle startinstructies](https://www.jrebel.com/products/jrebel/learn) om het product aan de praat te krijgen in verschillende populaire IDE's. Volg deze instructies om JRebel in uw ontwikkelomgeving te integreren.

Nadat de installatie is voltooid, opent u een webforJ-project en zorgt u ervoor dat de jetty `scan` eigenschap in het `pom.xml` bestand is ingesteld op `0` om de automatische herstart van de server uit te schakelen. Zodra dit is gedaan, gebruik de volgende opdracht:

```bash
mvn jetty:run
```

Als het goed is gedaan, zal JRebel logginginformatie naar de terminal afgeven en zouden de wijzigingen die in uw programma zijn aangebracht op aanvraag moeten worden weerspiegeld.

:::info Uw wijzigingen bekijken
Als er een wijziging wordt aangebracht aan een weergave of component die al wordt weergegeven, zal JRebel geen herlaad van de pagina afdwingen, aangezien de server niet opnieuw wordt opgestart.
:::
