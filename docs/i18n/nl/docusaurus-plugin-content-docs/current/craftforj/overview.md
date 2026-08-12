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
_i18n_hash: 6b642a9d173c5943acbb99934542e3a3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** is de visuele ontwikkelomgeving die wordt geleverd met webforJ. Het draait binnen je app in de ontwikkelingsmodus en geeft je een live overzicht van de componenten die je Java-code heeft gecreëerd. Je kunt een component selecteren, de eigenschappen ervan wijzigen, direct de lopende app zien bijwerken en de wijzigingen die je wilt behouden terugschrijven naar het Java-bestand dat ze heeft gemaakt.

<!-- INTRO_END -->

Omdat craftforJ de app leest via webforJ zelf, beschrijft het de app in de termen waarin je het hebt geschreven. De boomlijst geeft je componenten weer in plaats van de markup die de browser heeft gerenderd, de eigenschappen zijn die welke je componenten hebben verklaard, en de routes zijn diegene die je router heeft geregistreerd, samen met de toegangsregels die je eraan hebt toegevoegd.

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## Wat je ermee kunt doen {#what-you-can-do-with-it}

- **[Inspecteer componenten](/docs/craftforj/inspector)** - blader door de componentboom, selecteer een component door erop te klikken op de pagina, en wijzig de eigenschappen terwijl de app draait.
- **[Schrijf wijzigingen naar bron](/docs/craftforj/source-changes)** - herzie je live bewerkingen als een diff en pas ze toe op je Java-bestanden.
- **[Werk met routes](/docs/craftforj/routes)** - zie de routeringstabel, navigeer naar een route en wijzig de toegangsregels die eraan zijn verklaard.
- **[Theme de app](/docs/craftforj/theme)** - pas de design tokens aan waaruit je app is opgebouwd en sla het resultaat op in je stylesheet.
- **[Gebruik de AI-agent](/docs/craftforj/ai)** - een coderingsagent binnen de draaiende app die vrij Java schrijft, wat hij heeft geschreven compileert, en het toepast met jouw goedkeuring.

## Hoe het verschilt van een debugger {#how-it-differs-from-a-debugger}

Een debugger pauzeert je code en laat je de staat van je variabelen op dat moment zien. craftforJ laat de app draaien en toont je de interface die je code heeft geproduceerd, zodat je met het resultaat werkt in plaats van met de uitvoering. De twee beantwoorden verschillende vragen en worden vaak samen gebruikt.

## Alleen in ontwikkelingsmodus {#development-mode-only}

craftforJ vereist dat twee afzonderlijke instellingen zijn ingeschakeld, en standaard reageert het alleen op de browser die op dezelfde machine draait als de app. Projecten gemaakt met [startforJ](https://docs.webforj.com/startforj) of vanuit een webforJ [archetype](/docs/building-ui/archetypes/overview) schakelen het automatisch voor je in, zodat het beschikbaar is de eerste keer dat je ze uitvoert. Zie [Beveiliging](/docs/craftforj/security) voor wat craftforJ kan bereiken en hoe je kunt bevestigen dat het in productie is uitgeschakeld.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
