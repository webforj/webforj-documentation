---
title: Toolbar
sidebar_position: 145
_i18n_hash: a0f2d1a3d39ff0d195a5150ea6130710
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Workbalken bieden gebruikers snelle toegang tot kernacties en navigatie-elementen. De webforJ `Toolbar` component is een horizontale container die een set actieknoppen, iconen of andere componenten kan bevatten. Het is goed geschikt voor het beheren van pagina-instellingen en het huisvesten van belangrijke functies zoals een zoekbalk of een notificatieknop.

<!-- INTRO_END -->

## Inhoud van de werkbalk organiseren {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een gemakkelijk toegankelijke en consistente lay-out. Standaard neemt het de volledige breedte van zijn bovenliggende element in beslag en biedt het vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Start**: Bevat meestal een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een startknop.
- **Titel**: Gebruikt voor app-namen of logo's.
- **Inhoud**: Voor acties die veel aandacht vereisen, zoals zoeken of navigeren.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elk slot heeft een methode om componenten toe te voegen: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo toont hoe je een `Toolbar` toevoegt aan een [AppLayout](./app-layout) en effectief gebruikmaakt van alle ondersteunde slots.
Voor meer informatie over het implementeren van werkbalken binnen een `AppLayout`, zie [Plakkerige werkbalken](./app-layout#sticky-toolbars) en [Mobiele navigatielay-out](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
cssURL='/css/toolbar/toolbar-slots-view.css'
height='300px'
/>

## Compacte modus {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is handig wanneer je meer inhoud op het scherm wilt passen, vooral in apps met gestapelde werkbalken of beperkte ruimte. De werkbalk gedraagt zich nog steeds hetzelfde—alleen de hoogte is verminderd. Deze modus wordt vaak gebruikt in headers, zijbalken of lay-outs waar de ruimte beperkt is.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in werkbalken {#progressbar-in-toolbars}

Een `ProgressBar` functioneert als een visuele indicator voor aanhoudende processen, zoals het laden van gegevens, het uploaden van bestanden of het voltooien van stappen in een stroom. Wanneer deze binnen een `Toolbar` is geplaatst, staat de `ProgressBar` keurig langs de onderrand, waardoor deze niet opvalt terwijl deze toch duidelijk de voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de werkbalk zoals knoppen of labels zonder de lay-out te verstoren.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Stylen {#styling}

### Thema's {#themes}

`Toolbar` componenten bevatten <JavadocLink type="foundation" location="com/webforj/component/Theme">zeven ingebouwde thema's</JavadocLink> voor snelle visuele aanpassing:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
