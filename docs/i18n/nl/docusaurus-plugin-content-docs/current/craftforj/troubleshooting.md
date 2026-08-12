---
title: Troubleshooting
sidebar_position: 11
description: >-
  Fix the common cases where craftforJ doesn't appear, a feature is unavailable,
  or the assistant doesn't answer.
_i18n_hash: fcc5f7188c92523c0fb500bfc7b0ce58
---
### Niets verschijnt op de pagina {#nothing-appears-on-the-page}

craftforJ wordt alleen geactiveerd wanneer aan elke vereiste in [Aan de slag](/docs/craftforj/getting-started#requirements) is voldaan, en het toont helemaal niets wanneer er een ontbreekt. Controleer ze op volgorde: de `webforj-devtools` afhankelijkheid op de classpath, de debugmodus, de craftforJ-eigenschap, een browser op de machine die de app draait, en een geldige ontwikkelaarslicentie. Een configuratiebestand op de verkeerde locatie, of een profiel dat een van de eigenschappen overschrijft, produceert precies hetzelfde resultaat als de eigenschap die is uitgeschakeld.

### Een functie is niet beschikbaar {#a-feature-is-unavailable}

craftforJ toont een uitgeschakelde functie in plaats van deze te verbergen, dus een controle die aanwezig is maar als niet-ondersteund is gemarkeerd, is opzettelijk uitgeschakeld. Het kan zijn dat het is uitgeschakeld met een [functie vlag](/docs/craftforj/configuration#feature-flags) in de configuratie van de app, of dat de versie van `webforj-devtools` op je classpath een oudere versie is.

Schrijven naar de source heeft ook een projectroot nodig die craftforJ kan vinden. Controleer degene die het heeft gedetecteerd in [App-info](/docs/craftforj/app-info), en stel [`project-root`](/docs/craftforj/configuration#project-root) in als deze verkeerd is.

### Java-validatie is zwakker dan verwacht {#java-validation-is-weaker-than-expected}

De [compile validatie](/docs/craftforj/ai#it-writes-java) van de assistent heeft een JDK nodig. Controleer de Java-versie in [App-info](/docs/craftforj/app-info), en draai de app op een JDK in plaats van een JRE.

### craftforJ lijkt verouderd na een update {#craftforj-looks-out-of-date-after-an-update}

Je browser heeft de vorige versie gecached. Laad de pagina opnieuw of open de app in een privévenster. Als het probleem aanhoudt, bevestig dan welke versie van `webforj-devtools` daadwerkelijk op de classpath staat in [App-info](/docs/craftforj/app-info), aangezien een oude jar in je lokale Maven-repository er hetzelfde uitziet vanuit de browser.

### De assistent geeft geen antwoord {#the-assistant-doesnt-answer}

De assistent heeft een geconfigureerde provider en een model nodig dat tools kan aanroepen. Een model zonder toolondersteuning kan een gesprek voeren, maar kan niets inspecteren of wijzigen. Een lokaal model dat steeds de draad kwijt raakt in het gesprek, draait meestal met een te klein contextvenster.

Als een lokaal model is geconfigureerd en bereikbaar maar elke aanvraag wordt geweigerd, verwerpt de modelserver de oorsprong van de pagina. Voor Ollama, sta de oorsprong toe en start het opnieuw:

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

Op Linux, stel `OLLAMA_ORIGINS` in op de omgeving waaruit Ollama wordt gestart en herstart het.

### craftforJ zegt dat de app opnieuw wordt opgestart {#craftforj-says-the-app-is-restarting}

Je app verdwijnt regelmatig tijdens de ontwikkeling, elke keer dat deze opnieuw wordt opgebouwd. craftforJ rapporteert wat er gebeurt in plaats van te bevriezen, dus het toont wanneer de app opnieuw wordt opgestart of de pagina wordt herladen, en de bedieningselementen blijven inactief totdat de app terug is. Het maakt automatisch opnieuw verbinding met je selectie en je openstaande werk blijft intact, dus er is niets te doen dan wachten. Als het meldt dat het de app helemaal niet kan bereiken, bevestig dan dat de app nog steeds draait en laad de pagina opnieuw.

### De app blijft opnieuw opstarten {#the-app-keeps-restarting}

Een wijziging aan de source veroorzaakt een herstart van de app, zoals beschreven in [Nadat je hebt toegepast](/docs/craftforj/source-changes#after-you-apply). Herstarts die plaatsvinden zonder een aangebrachte wijziging komen van de bestandsbewaker van je build en niet van craftforJ.

### Logs verzamelen {#collecting-logs}

Voordat je een probleem meldt, zet je gedetailleerde logging aan in de instellingen van craftforJ, wis je log, reproduceer het probleem, en download vervolgens de log. Voeg deze toe samen met de inhoud van [App-info](/docs/craftforj/app-info).
