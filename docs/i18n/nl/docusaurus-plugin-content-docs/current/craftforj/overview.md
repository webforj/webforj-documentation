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
_i18n_hash: 771ef18b5a680d5b9260f06a2704f877
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

**craftforJ** is de visuele ontwikkelomgeving die met webforJ wordt meegeleverd. Deze draait in je app in de ontwikkelmodus en geeft je een liveweergave van de componenten die je Java-code heeft gemaakt. Je kunt een component selecteren, de eigenschappen ervan wijzigen, de app direct zien bijwerken en de wijzigingen die je wilt behouden terugschrijven naar het Java-bestand waarin de component is gemaakt.

<!-- INTRO_END -->

Omdat craftforJ de app via webforJ zelf uitleest, beschrijft het de app in de termen waarin je die hebt geschreven. De boomstructuur toont je componenten in plaats van de markup die de browser heeft gerenderd, de eigenschappen zijn de eigenschappen die je componenten declareren en de routes zijn de routes die je router heeft geregistreerd, samen met de toegangsregels die je eraan hebt gekoppeld.

<VideoPlayer video="craftforj/intro" width={1920} height={1080} title="Intro" />

## Wat je ermee kunt doen {#what-you-can-do-with-it}

- **[Componenten inspecteren](/docs/craftforj/inspector)** - bekijk de componentenboom, selecteer een component door erop te klikken op de pagina en wijzig de eigenschappen terwijl de app actief is.
- **[Wijzigingen naar de broncode schrijven](/docs/craftforj/source-changes)** - bekijk je live bewerkingen als een diff en pas ze toe op je Java-bestanden.
- **[Met routes werken](/docs/craftforj/routes)** - bekijk de routingtabel, navigeer naar een willekeurige route en wijzig de toegangsregels die ervoor zijn vastgelegd.
- **[De app vormgeven](/docs/craftforj/theme)** - pas de ontwerptokens aan waarop je app is gebaseerd en sla het resultaat op in je stylesheet.
- **[De AI-agent gebruiken](/docs/craftforj/ai)** - een codeeragent in de actieve app die vrijelijk Java-code schrijft, die code compileert en deze na jouw goedkeuring toepast.

## Hoe het verschilt van een debugger {#how-it-differs-from-a-debugger}

Een debugger pauzeert je code en toont de toestand van je variabelen op dat moment. craftforJ laat de app actief en toont de interface die je code heeft voortgebracht, zodat je met het resultaat werkt in plaats van met de uitvoering. Ze beantwoorden verschillende vragen en worden vaak samen gebruikt.

## Alleen in de ontwikkelmodus {#development-mode-only}

Voor craftforJ moeten twee afzonderlijke instellingen zijn ingeschakeld. Standaard reageert het alleen op de browser die op dezelfde machine als de app draait. Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of met een webforJ-[archetype](/docs/building-ui/archetypes/overview) schakelen dit voor je in, zodat het beschikbaar is zodra je ze voor het eerst uitvoert. Zie [Beveiliging](/docs/craftforj/security) voor informatie over waartoe craftforJ toegang heeft en hoe je controleert of het in productie is uitgeschakeld.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
