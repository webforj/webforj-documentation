---
title: Toolbar
sidebar_position: 145
_i18n_hash: 54bfdf481b7e149762dc4544c192c6e6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieden gebruikers snelle toegang tot kernacties en navigatie-elementen. De webforJ `Toolbar` component is een horizontale container die een reeks actieknoppen, pictogrammen of andere componenten kan bevatten. Het is goed geschikt voor het beheren van paginacontroles en het huisvesten van belangrijke functies zoals een zoekbalk of een notificatieknop.

<!-- INTRO_END -->

## Organiseren van toolbarinhoud {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een gemakkelijk toegankelijke en consistente indeling. Standaard neemt het de volledige breedte van zijn bovenliggende element in beslag en biedt het vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Start**: Bevat meestal een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een homeknop.
- **Titel**: Gebruikt voor app-namen of logo's.
- **Inhoud**: Voor acties met hoge aandacht zoals zoeken of navigeren.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elk slot heeft een methode voor het toevoegen van componenten: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo toont hoe je een `Toolbar` aan een [AppLayout](./app-layout) toevoegt en alle ondersteunde slots effectief benut.
Om meer te lezen over het implementeren van toolbars binnen een `AppLayout`, zie [Sticky toolbars](./app-layout#sticky-toolbars) en [Mobiele navigatie-indeling](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## Compacte modus {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is handig wanneer je meer inhoud op het scherm wilt passen, vooral in apps met gestapelde toolbars of beperkte ruimte. De toolbar gedraagt zich nog steeds hetzelfde; alleen de hoogte is verminderd. Deze modus wordt vaak gebruikt in kopteksten, zijbalken of indelingen waar ruimte beperkt is.

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

Een `ProgressBar` dient als visuele indicator voor lopende processen, zoals het laden van gegevens, het uploaden van bestanden of het voltooien van stappen in een flow. Wanneer deze binnen een `Toolbar` wordt geplaatst, sluit de `ProgressBar` netjes aan de onderrand aan, waardoor deze niet opdringerig is en toch duidelijk de voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de toolbar zoals knoppen of labels zonder de indeling te verstoren.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Stijlen {#styling}

### Thema's {#themes}

`Toolbar` componenten omvatten <JavadocLink type="foundation" location="com/webforj/component/Theme">zeven ingebouwde thema's</JavadocLink> voor snelle visuele aanpassing:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='475px'
/>

<TableBuilder name="Toolbar" />
