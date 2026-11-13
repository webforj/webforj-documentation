---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Inspect the component tree of a running webforJ app, change components live,
  and write the changes you keep back into your Java source.
sidebar_class_name: new-content
_i18n_hash: 727e01ac5960e204363665aba672d6c7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# craftforJ <DocChip chip='since' label='26.02' />
<!-- vale on -->

**craftforJ** is de visuele ontwikkelomgeving die met webforJ wordt meegeleverd. De omgeving draait in de ontwikkelmodus binnen je app en geeft je een liveweergave van de componenten die je Java-code heeft gemaakt. Je kunt een component selecteren, de eigenschappen ervan wijzigen, de app direct zien bijwerken en de wijzigingen die je wilt bewaren terugschrijven naar het Java-bestand waarin de component is gemaakt.

<!-- INTRO_END -->

Omdat craftforJ de app via webforJ zelf uitleest, beschrijft het de app in de termen waarin je die hebt geschreven. De boomstructuur toont je componenten in plaats van de markup die de browser heeft weergegeven, de eigenschappen zijn de eigenschappen die je componenten definiëren en de routes zijn de routes die je router heeft geregistreerd, samen met de toegangsregels die je eraan hebt gekoppeld.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Wat je ermee kunt doen {#what-you-can-do-with-it}

- **[Componenten inspecteren](/docs/craftforj/inspector)** - blader door de componentenboom, selecteer een component door erop te klikken in de pagina en wijzig de eigenschappen terwijl de app draait.
- **[Wijzigingen naar de broncode schrijven](/docs/craftforj/source-changes)** - bekijk je livebewerkingen als een diff en pas ze toe op je Java-bestanden.
- **[Met routes werken](/docs/craftforj/routes)** - bekijk de routeringstabel, navigeer naar een willekeurige route en wijzig de toegangsregels die eraan zijn gekoppeld.
- **[De app een thema geven](/docs/craftforj/theme)** - pas de ontwerptokens aan waarop je app is gebaseerd en sla het resultaat op in je stylesheet.
- **[De AI-agent gebruiken](/docs/craftforj/ai)** - een codeeragent in de draaiende app die zelfstandig Java-code schrijft, die code compileert en de wijzigingen met jouw goedkeuring toepast.

## Hoe het verschilt van een debugger {#how-it-differs-from-a-debugger}

Een debugger pauzeert je code en toont de status van je variabelen op dat moment. craftforJ laat de app draaien en toont de interface die je code heeft voortgebracht, zodat je met het resultaat werkt in plaats van met de uitvoering. Ze beantwoorden verschillende vragen en worden vaak samen gebruikt.

## Alleen in de ontwikkelmodus {#development-mode-only}

Voor craftforJ moeten twee afzonderlijke instellingen zijn ingeschakeld. Standaard reageert het alleen op de browser die draait op dezelfde machine als de app. Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of met een webforJ-[archetype](/docs/building-ui/archetypes/overview) schakelen dit voor je in, zodat het beschikbaar is zodra je ze voor het eerst uitvoert. Zie [Beveiliging](/docs/craftforj/security) voor meer informatie over wat craftforJ kan bereiken en hoe je kunt controleren of het in productie is uitgeschakeld.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
