---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 166b39dabe94c73ecf6310dfdc1ba626
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Werkbalken bieden gebruikers snelle toegang tot kernacties en navigatie-elementen. De webforJ `Toolbar` component is een horizontale container die een set actieknoppen, pictogrammen of andere componenten kan bevatten. Het is goed geschikt voor het beheren van pagina-controles en het huisvesten van belangrijke functies zoals een zoekbalk of een notificatieknop.

<!-- INTRO_END -->

## Organizing toolbar content {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een gemakkelijk toegankelijke en consistente lay-out. Standaard neemt deze de volledige breedte van zijn bovenliggende element in beslag en biedt vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Start**: Bevat meestal een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een startknop.
- **Titel**: Gebruikt voor app-namen of logo's.
- **Inhoud**: Voor acties die veel aandacht vereisen, zoals zoeken of navigatie.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elk slot heeft een methode voor het toevoegen van componenten: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo laat zien hoe je een `Toolbar` aan een [AppLayout](./app-layout) toevoegt en alle ondersteunde slots effectief benut. Voor meer informatie over het implementeren van werkbalken binnen een `AppLayout`, zie [Sticky toolbars](./app-layout#sticky-toolbars) en [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java']}
/>

## Compact mode {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is handig wanneer je meer inhoud op het scherm moet passen, vooral in apps met gestapelde werkbalken of beperkte ruimte. De werkbalk gedraagt zich nog steeds hetzelfde—alleen de hoogte is verminderd. Deze modus wordt vaak gebruikt in headers, zijbalken of lay-outs waar de ruimte krap is.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` in toolbars {#progressbar-in-toolbars}

Een `ProgressBar` dient als een visuele indicator voor lopende processen, zoals het laden van gegevens, het uploaden van bestanden of het voltooien van stappen in een stroom. Wanneer deze in een `Toolbar` is geplaatst, staat de `ProgressBar` netjes langs de onderrand, waardoor deze niet opdringerig is en toch duidelijk de voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de werkbalk, zoals knoppen of labels, zonder de lay-out te verstoren.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Styling {#styling}

### Themes {#themes}

`Toolbar` componenten bevatten <JavadocLink type="foundation" location="com/webforj/component/Theme">zeven ingebouwde thema's</JavadocLink> voor snelle visuele aanpassing:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
