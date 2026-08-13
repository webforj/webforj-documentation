---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 8dcb4d5bcecce36e656de87218bd3359
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieden gebruikers snelle toegang tot kernacties en navigatie-elementen. De webforJ `Toolbar` component is een horizontale container die een set actieknoppen, pictogrammen of andere componenten kan bevatten. Het is bijzonder geschikt voor het beheren van pagina bedieningen en het huisvesten van belangrijke functies zoals een zoekbalk of een meldingsknop.

<!-- INTRO_END -->

## Organisatie van toolbarinhoud {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een eenvoudig toegankelijk en consistent lay-out. Standaard neemt het de volledige breedte van zijn ouder element in en biedt vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Start**: Bevat doorgaans een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een home-knop.
- **Titel**: Gebruikt voor app-namen of logo's.
- **Inhoud**: Voor acties met hoge aandacht zoals zoeken of navigatie.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elk slot heeft een methode om componenten toe te voegen: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo laat zien hoe je een `Toolbar` toevoegt aan een [AppLayout](./app-layout) en gebruik maakt van alle ondersteunde slots.

Om meer te lezen over het implementeren van toolbars binnen een `AppLayout`, zie [Sticky toolbars](./app-layout#sticky-toolbars) en [Mobiel navigatielay-out](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/frontend/css/toolbar/toolbar-slots-view.css',
]}
/>

## Compacte modus {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is handig wanneer je meer inhoud op het scherm wilt passen, vooral in apps met gestapelde toolbars of beperkte ruimte. De toolbar gedraagt zich nog steeds hetzelfde—alleen de hoogte is verminderd. Deze modus wordt vaak gebruikt in kopteksten, zijbalken, of lay-outs waar de ruimte beperkt is.

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

Een `ProgressBar` dient als visuele indicator voor lopende processen, zoals het laden van gegevens, het uploaden van bestanden, of het voltooien van stappen in een flow. Wanneer deze in een `Toolbar` wordt geplaatst, sluit de `ProgressBar` netjes aan de onderrand aan, waardoor deze onopvallend blijft terwijl deze toch duidelijk de voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de toolbar zoals knoppen of labels zonder de lay-out te verstoren.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Styling {#styling}

### Thema's {#themes}

`Toolbar` componenten omvatten <JavadocLink type="foundation" location="com/webforj/component/Theme">zeven ingebouwde thema's</JavadocLink> voor snelle visuele aanpassing:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
