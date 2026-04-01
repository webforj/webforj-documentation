---
title: Toolbar
sidebar_position: 145
_i18n_hash: e3329a54fd8feccd96e15e1b19c3c97d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieden gebruikers snelle toegang tot kernactiviteiten en navigatie-elementen. De webforJ `Toolbar` component is een horizontale container die een set actieknoppen, pictogrammen of andere componenten kan bevatten. Het is bijzonder geschikt voor het beheren van pagina-instellingen en het huisvesten van belangrijke functies zoals een zoekbalk of een notificatieknop.

<!-- INTRO_END -->

## Inhoud van de toolbar organiseren {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een gemakkelijk toegankelijke en consistente lay-out. Standaard neemt het de volledige breedte van zijn bovenliggende element in beslag en biedt het vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Start**: Bevat meestal een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een startknop.
- **Titel**: Gebruikt voor app-namen of logo's.
- **Inhoud**: Voor acties met hoge aandacht zoals zoeken of navigeren.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elk slot heeft een methode voor het toevoegen van componenten: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo toont hoe je een `Toolbar` toevoegt aan een [AppLayout](./app-layout) en alle ondersteunde slots effectief benut. 
Om meer te lezen over het implementeren van toolbars binnen een `AppLayout`, zie [Sticky toolbars](./app-layout#sticky-toolbars) en [Mobiele navigatielayout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Compacte modus {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is nuttig wanneer je meer inhoud op het scherm wilt passen, vooral in apps met gestapelde toolbars of beperkte ruimte. De toolbar gedraagt zich nog steeds hetzelfde—alleen de hoogte is verminderd. Deze modus wordt vaak gebruikt in kopteksten, zijbalken of lay-outs waar ruimte schaars is.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in toolbars {#progressbar-in-toolbars}

Een `ProgressBar` dient als visuele indicator voor lopende processen, zoals het laden van gegevens, het uploaden van bestanden of het voltooien van stappen in een flow. Wanneer deze binnen een `Toolbar` is geplaatst, wordt de `ProgressBar` netjes langs de onderrand uitgelijnd, waardoor deze onopvallend is terwijl deze duidelijk voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de toolbar zoals knoppen of labels zonder de lay-out te verstoren.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Styling {#styling}

### Thema's {#themes}

`Toolbar` componenten bevatten <JavadocLink type="foundation" location="com/webforj/component/Theme">zeven ingebouwde thema's</JavadocLink> voor snelle visuele aanpassing:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
