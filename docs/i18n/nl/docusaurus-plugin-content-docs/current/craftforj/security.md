---
title: Beveiliging
sidebar_position: 9
description: >-
  What craftforJ can reach in your project, how it restricts access, and how to
  confirm it's disabled in production.
_i18n_hash: 5ffbc5b5c6e6cfcf64143712a21944d5
---
craftforJ leest en schrijft de bron van het project waaraan het is gekoppeld. Deze pagina beschrijft de grenzen daaromheen en hoe je kunt bevestigen dat craftforJ is uitgeschakeld in de builds die je implementeert.

## Twee vereiste instellingen {#two-required-settings}

craftforJ vereist dat beide onderstaande instellingen zijn ingeschakeld:

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Geen van beide doet iets op zichzelf. Een app die in productie gaat met de debugmodus ingeschakeld, exposeert craftforJ niet, en een app die de craftforJ-eigenschap in een gedeeld configuratiebestand heeft, exposeert het niet buiten de debugmodus.

Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of vanuit een webforJ [archetype](/docs/building-ui/archetypes/overview) hebben beide ingeschakeld, zodat craftforJ werkt vanaf de eerste uitvoering. Voordat je implementeert, werk je de [productiechecklist](#in-production) hieronder door.

## Lokale toegang standaard {#local-access-by-default}

Alleen een browser op de machine die de app uitvoert, kan craftforJ bereiken. Alles daarbuiten wordt geweigerd, en dit geldt zonder enige configuratie van jouw kant. Om craftforJ vanaf een andere machine te bereiken, moet je die machine naamgeven in [`hosts-allowed`](/docs/craftforj/configuration#access). Adressen worden letterlijk gematcht, zodat een client zich niet als iets anders kan voordoen.

:::warning De wildcard verwijdert de beperking volledig
Instelling `hosts-allowed = "*"` betekent dat iedereen die je app-poort kan bereiken, je projectbronnen kan lezen en schrijven. Het bestaat voor gesloten omgevingen, zoals een container die alleen door jou bereikbaar is. Gebruik het nergens anders.
:::

## Geen toegevoegde HTTP-oppervlak {#no-added-http-surface}

craftforJ voegt geen HTTP-eindpunt, servlet of filter toe aan je app. Het werkt over de verbinding die je app al heeft, zodat je app exact dezelfde set verzoeken beantwoordt met craftforJ ingeschakeld als zonder.

## Verzoeken komen van jouw pagina {#requests-come-from-your-page}

craftforJ reageert alleen op verzoeken die afkomstig zijn van de pagina die jouw server daadwerkelijk heeft geleverd. Een script dat van elders in de pagina terechtkomt, zoals een gecompromitteerde afhankelijkheid of iets geplakt in een console, kan craftforJ niet aansteken.

## API-sleutels {#api-keys}

Je sleutel wordt op de machine die jouw app uitvoert opgeslagen. De [AI-assistent](/docs/craftforj/ai) draait in de browser, zodat craftforJ de sleutel moet geven om mee te werken, en het houdt die sleutel in het geheugen zolang de pagina open is. Er wordt niets in de browseropslag geschreven, en het sluiten van de pagina laat niks achter.

De assistent praat vervolgens met jouw provider vanuit de browser in plaats van via jouw server. Er is geen relais, geen proxy, geen telemetry en geen derde partij ertussenin.

Wat jouw provider bereikt, is het gesprek zelf, inclusief de delen van jouw app die de assistent heeft bekeken en eventuele schermafbeeldingen die het heeft gemaakt. Houd daar rekening mee voordat je een gehost model wijst op een app die werkt met echte data. Een lokaal draaiend model houdt alles op jouw machine.

## Wat craftforJ kan veranderen {#what-craftforj-can-change}

Met elke functie ingeschakeld, kan craftforJ:

- Elke bronbestand onder jouw projectroot lezen
- Java-bronbestanden schrijven, inclusief route-toegang annotaties
- De stylesheet van jouw app schrijven
- Componenten in de draaiende app wijzigen en verwijderen
- De draaiende app navigeren

Elk van deze kan onafhankelijk worden [uitgeschakeld](/docs/craftforj/configuration#feature-flags), en elke schrijfoperatie naar schijf gaat via een diff die je goedkeurt.

## In productie {#in-production}

Laat craftforJ uitgeschakeld. Het is uitgeschakeld tenzij je het hebt ingeschakeld, dus in de meeste gevallen is er niets te doen. Om te bevestigen:

1. `webforj.devtools.craftforj.enabled` is unset of `false` in de configuratie die je daadwerkelijk implementeert.
2. `webforj.debug` is unset of `false` in diezelfde configuratie.
3. Geen van beide eigenschappen is ingesteld door een omgevingsvariabele of door een profiel dat alleen in productie geldt.
4. Laad de geïmplementeerde app en bevestig dat er geen craftforJ-trigger op de pagina is.

Voor het bredere plaatje, zie [Productieversterking](/docs/security/application-security/production-hardening).

## Rapporteren van een beveiligingsprobleem {#reporting-a-security-issue}

Als je een beveiligingsprobleem in craftforJ vindt, rapporteer het dan via het [webforJ beveiligingsbeleid](https://github.com/webforj/webforj/security) in plaats van in een openbaar probleem.
