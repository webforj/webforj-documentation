---
title: Getting Started
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 81825a3ba8656a8aee4820dee71da732
---
<DocChip chip='since' label='26.02' />

craftforJ wordt geleverd met webforJ, dus er hoeft niets apart te worden gedownload. Deze pagina bespreekt wat je app nodig heeft voordat craftforJ verschijnt, en hoe je het kunt openen.

:::tip Al ingeschakeld in gegenereerde projecten
Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of vanuit een webforJ [archetype](/docs/building-ui/archetypes/overview) worden geleverd met craftforJ ingeschakeld. Als je vanaf daar bent begonnen, voer je app uit en sla je door naar [Opening craftforJ](#opening-craftforj).
:::

## Vereisten {#requirements}

craftforJ hecht zich aan een app alleen wanneer aan al het volgende is voldaan. Als een van hen niet aan de voorwaarden voldoet, verschijnt er niets op de pagina.

### Voeg de afhankelijkheid toe {#add-the-dependency}

Voeg `webforj-devtools` toe aan je project als het er nog niet in staat:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debug-modus en de craftforJ-vlag {#debug-mode-and-the-craftforj-flag}

Voeg de volgende eigenschappen toe aan je project. Als je een standaard webforJ-app hebt, stel je de eigenschappen in `webforj.conf` in. Voor een webforJ-project dat gebruikmaakt van [Spring](/docs/integrations/spring/overview), stel je de eigenschappen in `application.properties` in.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ functioneert alleen wanneer beide eigenschappen zijn ingeschakeld; een app die in productie gaat met de debug-modus aan, geeft je brontree niet bloot.

### Een lokale browser en een ontwikkelaarslicentie {#a-local-browser-and-a-developer-license}

Open de app vanaf de machine die deze uitvoert en zorg ervoor dat je een geldige ontwikkelaarslicentie hebt. Om craftforJ vanaf een andere machine te bereiken, voeg je het adres toe aan [`hosts-allowed`](/docs/craftforj/configuration#access).

Zodra deze zaken zijn geregeld, herstart je de app en vernieuw je de pagina.

## Opening craftforJ {#opening-craftforj}

Wanneer craftforJ actief is, verschijnt er een triggerknop boven je app. Klik erop om craftforJ te openen, of druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> van waar dan ook in de app. dezelfde sneltoets sluit craftforJ weer, en je kunt de trigger naar elke hoek verslepen die je past.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4" type="video/mp4" />
  </video>
</div>

De tabs dekken de [componentenboom](/docs/craftforj/inspector), [routes](/docs/craftforj/routes), het [thema](/docs/craftforj/theme) en de [assistent](/docs/craftforj/ai). Instellingen en app-informatie bevinden zich daarnaast.

- **De trigger** is de knop die craftforJ opent en sluit. Het blijft uit de weg terwijl craftforJ gesloten is.
- **De tabstrip** loopt langs de rand die het dichtst bij de app is en schakelt tussen wat craftforJ je laat zien.
- **Het venster menu** bevat alles over waar craftforJ zich bevindt, behandeld in [Where craftforJ sits](#where-craftforj-sits).

:::info Sneltoetsen op macOS
craftforJ schrijft elke sneltoets met de modifiers van het platform waarop je je bevindt, dus <kbd>Alt</kbd> verschijnt als <kbd>⌥</kbd> en <kbd>Ctrl</kbd> als <kbd>⌘</kbd>. Druk op <kbd>Shift</kbd> + <kbd>?</kbd> in craftforJ om de huidige lijst te zien.
:::

## Where craftforJ sits {#where-craftforj-sits}

craftforJ zweeft standaard boven je app. Sleep het naar elke plaats op de pagina, verander de grootte vanaf elke rand en minimaliseer het terug naar zijn trigger wanneer je de app voor jezelf wilt. Door het naar een rand van de pagina te slepen, dockt het daar, volledige hoogte of volledige breedte, en elke rand behoudt de grootte die je het hebt gegeven. Door het van de rand te slepen, zweeft het weer.

:::info Docking bedekt de app, het herstelt deze niet
craftforJ wordt bovenop de pagina getekend. Je app wordt niet opnieuw weergegeven, en niets erin beweegt uit de weg, zodat alles wat onder craftforJ zit verborgen is terwijl het daar is. Om te zien wat eronder zit, verplaats je craftforJ naar een andere rand of haal je het van de pagina.
:::

![craftforJ aangemeerd aan de rechterkant van een app-pagina, die deze rand van de app bedekt](/img/craftforj/getting-started/docking.png#rounded-border)

Om de app helemaal niet te bedekken, verplaats je craftforJ van de pagina en in een browservenster of tabblad van zichzelf, wat geschikt is voor een tweede monitor. Het inspecteert nog steeds je app via de pagina die het opende, dus laat die pagina open. Navigeer er vanaf weg of sluit het af en craftforJ heeft niets meer te inspecteren totdat je de app opnieuw opent.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4" type="video/mp4" />
  </video>
</div>

Kies een tabblad in plaats van een venster als je Chrome's splitsweergave gebruikt, die je app en craftforJ naast elkaar plaatst en alleen echte tabbladen accepteert. Klik met de rechtermuisknop op het tabblad van je app, voeg het toe aan een nieuwe splitsweergave en kies dan het craftforJ-tabblad.

:::info Split view is een Chrome-functie
Chrome biedt de zij-aan-zij indeling, niet craftforJ. Andere browsers hebben geen equivalent, dus craftforJ opent in andere browsers in een gewoon tabblad waarnaar je overschakelt. craftforJ zelf werkt in beide gevallen hetzelfde.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4" type="video/mp4" />
  </video>
</div>

:::tip Bewegen terwijl de assistent aan het schrijven is
Het verplaatsen van craftforJ naar een ander venster beëindigt een antwoord dat nog aan het streamen is. craftforJ vraagt eerst, en alles wat tot dat moment is geschreven, blijft in de chat.
:::

## Making a first change {#making-a-first-change}

1. Druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> om een component te kiezen.
2. Hover over iets in je app en klik erop.
3. De boom selecteert dat component, en de zijbalk wordt gevuld met de eigenschappen.
4. Wijzig een eigenschap. De draaiende app wordt onmiddellijk bijgewerkt.

De wijziging heeft alleen invloed op de app voor je. Je bestanden blijven onaangeroerd totdat je de wijziging herzien en toepassen, wat wordt behandeld in [Writing changes to source](/docs/craftforj/source-changes).

![craftforJ open naast een draaiende app met een geselecteerd component](/img/craftforj/getting-started/first-open.png#rounded-border)

Als er helemaal niets verschijnt, werk dan door [Troubleshooting](/docs/craftforj/troubleshooting).
