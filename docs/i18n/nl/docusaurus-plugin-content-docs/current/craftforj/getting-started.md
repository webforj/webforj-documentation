---
title: Aan de Slag
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: fb5860ba7349ef5523c2dfb6bc425d9b
---
<DocChip chip='since' label='26.02' />

craftforJ wordt geleverd met webforJ, dus er is niets apart te downloaden. Deze pagina behandelt wat je app nodig heeft voordat craftforJ verschijnt, en hoe je het kunt openen.

:::tip Al ingeschakeld in gegenereerde projecten
Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of vanuit een webforJ [archetype](/docs/building-ui/archetypes/overview) komen met craftforJ ingeschakeld. Als je vanaf een van deze projecten bent begonnen, voer je app uit en sla je door naar [Opening craftforJ](#opening-craftforj).
:::

## Vereisten {#requirements}

craftforJ voegt zich alleen bij een app als aan al de volgende voorwaarden is voldaan. Als er aan een van deze voorwaarden niet wordt voldaan, verschijnt er niets op de pagina.

### Voeg de afhankelijkheid toe {#add-the-dependency}

Voeg `webforj-devtools` toe aan je project als het er nog niet in staat:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debugmodus en de craftforJ-vlag {#debug-mode-and-the-craftforj-flag}

Voeg de volgende eigenschappen toe aan je project. Als je een standaard webforJ-app hebt, stel je de eigenschappen in `webforj.conf` in. Voor een webforJ-project dat gebruikmaakt van [Spring](/docs/integrations/spring/overview), stel je de eigenschappen in `application.properties` in.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ functioneert alleen wanneer beide eigenschappen zijn ingeschakeld; daarom stelt een app die in productie gaat met de debugmodus ingeschakeld je bronboom niet bloot.

### Een lokale browser en een ontwikkelaarslicentie {#a-local-browser-and-a-developer-license}

Open de app vanaf de machine die deze uitvoert en zorg ervoor dat je een geldige ontwikkelaarslicentie hebt. Om craftforJ vanaf een andere machine te bereiken, voeg je het adres toe aan [`hosts-allowed`](/docs/craftforj/configuration#access).

Zodra deze zaken zijn geregeld, start je de app opnieuw en laad je de pagina.

## Opening craftforJ {#opening-craftforj}

Wanneer craftforJ actief is, verschijnt er een triggerknop boven je app. Klik erop om craftforJ te openen, of druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> vanuit elke locatie in de app. Dezelfde sneltoets sluit craftforJ weer, en je kunt de trigger naar elke hoek slepen die je wilt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

De tabbladen dekken de [componentboom](/docs/craftforj/inspector), [routes](/docs/craftforj/routes), het [thema](/docs/craftforj/theme) en de [assistent](/docs/craftforj/ai). Instellingen en app-informatie bevinden zich naast hen.

- **De trigger** is de knop die craftforJ opent en sluit. Hij blijft uit de weg terwijl craftforJ gesloten is.
- **De tabstrip** loopt langs de rand die dichtst bij de app ligt en schakelt tussen wat craftforJ je laat zien.
- **Het venster menu** bevat alles over waar craftforJ zich bevindt, behandeld in [Waar craftforJ zit](#where-craftforj-sits).

:::info Sneltoetsen op macOS
craftforJ schrijft elke sneltoets met de modifiers van het platform waarop je je bevindt, dus <kbd>Alt</kbd> verschijnt als <kbd>⌥</kbd> en <kbd>Ctrl</kbd> als <kbd>⌘</kbd>. Druk op <kbd>Shift</kbd> + <kbd>?</kbd> in craftforJ om de huidige lijst te zien.
:::

## Waar craftforJ zit {#where-craftforj-sits}

craftforJ zweeft standaard boven je app. Sleep het overal op de pagina, verander de grootte vanuit elke rand, en minimaliseer het terug naar zijn trigger wanneer je de app voor jezelf wilt hebben. Door het naar een rand van de pagina te slepen, wordt het daar vastgezet, volledige hoogte of volledige breedte, en elke rand behoudt de grootte die je het hebt gegeven. Als je het van de rand weg sleept, zweeft het weer.

:::info Vastzetten bedekt de app, het hervloeid niet
craftforJ wordt bovenop de pagina getekend. Je app past zijn grootte niet aan, en niets erin verschuift, dus wat ook onder craftforJ zit, is verborgen terwijl het daar is. Om te zien wat eronder zit, verplaats je craftforJ naar een andere rand of haal je het van de pagina af.
:::

![craftforJ vastgezet aan de rechterkant van een app-pagina, die die rand van de app bedekt](/img/craftforj/getting-started/docking.png#rounded-border)

Om te stoppen met het bedekken van de app, verplaats je craftforJ naar buiten de pagina en in een eigen browservenster of -tab, wat goed is voor een tweede monitor. Het inspecteert nog steeds je app via de pagina die het heeft geopend, dus laat die pagina open. Navigeer ervan weg of sluit het en craftforJ heeft niets meer om te inspecteren totdat je de app opnieuw opent.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Kies een tabblad in plaats van een venster als je de split view van Chrome gebruikt, die je app en craftforJ naast elkaar plaatst en alleen echte tabbladen accepteert. Klik met de rechtermuisknop op het tabblad van je app, voeg het toe aan een nieuwe split view, en kies vervolgens het craftforJ-tabblad.

:::info Split view is een Chrome-functie
Chrome biedt de zij-aan-zij indeling, niet craftforJ. Andere browsers hebben geen gelijkwaardige functie, dus craftforJ in andere browsers opent in een gewone tabblad waarop je overschakelt. craftforJ zelf werkt op dezelfde manier, ongeacht de indeling.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Verplaatsen terwijl de assistent schrijft
Het verplaatsen van craftforJ naar een ander venster beëindigt een reactie die nog aan het streamen is. craftforJ vraagt eerst, en alles wat tot dat moment is geschreven blijft in de chat.
:::

## Een eerste wijziging aanbrengen {#making-a-first-change}

1. Druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> om een component te selecteren.
2. Hover over iets in je app en klik erop.
3. De boom selecteert die component, en de zijbalk vult zich met zijn eigenschappen.
4. Wijzig een eigenschap. De draaiende app werkt onmiddellijk bij.

De wijziging heeft alleen invloed op de app voor je. Je bestanden blijven onaangeroerd totdat je de wijziging beoordeelt en toepast, wat wordt behandeld in [Writing changes to source](/docs/craftforj/source-changes).

![craftforJ open naast een draaiende app met een geselecteerde component](/img/craftforj/getting-started/first-open.png#rounded-border)

Als er helemaal niets verschijnt, werk dan door [Troubleshooting](/docs/craftforj/troubleshooting).
