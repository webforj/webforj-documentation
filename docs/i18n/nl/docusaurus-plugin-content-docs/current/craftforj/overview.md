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
_i18n_hash: e5fd219fd4360d24535c26696d1c9d1d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** is de visuele ontwikkelomgeving die wordt meegeleverd met webforJ. Het draait binnen je app in de ontwikkelingsmodus en biedt je een live weergave van de componenten die je Java-code heeft gemaakt. Je kunt een component selecteren, de eigenschappen ervan veranderen, de lopende app onmiddellijk zien bijwerken en de wijzigingen die je wilt behouden terugschrijven naar het Java-bestand dat ze heeft aangemaakt.

<!-- INTRO_END -->

Omdat craftforJ de app door webforJ zelf leest, beschrijft het de app in de termen waarin je deze hebt geschreven. De boomlijst toont je componenten in plaats van de markup die de browser heeft gerenderd, de eigenschappen zijn die welke je componenten verklaren, en de routes zijn die welke je router heeft geregistreerd, samen met de toegangsregels die je eraan hebt toegevoegd.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Wat je ermee kunt doen {#what-you-can-do-with-it}

- **[Componenten inspecteren](/docs/craftforj/inspector)** - doorblader de componentenboom, selecteer een component door erop te klikken op de pagina en verander de eigenschappen terwijl de app draait.
- **[Wijzigingen naar de bron schrijven](/docs/craftforj/source-changes)** - bekijk je live bewerkingen als een diff en pas ze toe op je Java-bestanden.
- **[Werken met routes](/docs/craftforj/routes)** - bekijk de routeringstabel, navigeer naar elke route en verander de toegangsregels die erop zijn verklaard.
- **[Theme de app](/docs/craftforj/theme)** - pas de ontwerptokens aan waaruit je app is opgebouwd en sla het resultaat op in je stylesheet.
- **[Gebruik de AI-agent](/docs/craftforj/ai)** - een coderingsagent binnen de draaiende app die vrij Java schrijft, wat hij heeft geschreven compileert en het toepast met jouw goedkeuring.

## Hoe het verschilt van een debugger {#how-it-differs-from-a-debugger}

Een debugger pauzeert je code en toont je de toestand van je variabelen op dat moment. craftforJ laat de app draaien en toont je de interface die je code heeft geproduceerd, zodat je met het resultaat werkt in plaats van met de uitvoering. De twee beantwoorden verschillende vragen en worden vaak samen gebruikt.

## Alleen in ontwikkelingsmodus {#development-mode-only}

craftforJ vereist dat twee aparte instellingen zijn ingeschakeld, en standaard reageert het alleen op de browser die op dezelfde machine als de app draait. Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of vanuit een webforJ [archetype](/docs/building-ui/archetypes/overview) schakelen het voor jou in, zodat het beschikbaar is de eerste keer dat je ze uitvoert. Zie [Veiligheid](/docs/craftforj/security) voor wat craftforJ kan bereiken en hoe je kunt bevestigen dat het uit staat in productie.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
