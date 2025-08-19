---
title: Toolbar
sidebar_position: 145
_i18n_hash: 446d71b3e376810254bbbf6ffee43aa9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Werkbalken bieden gebruikers snelle toegang tot kernacties en navigatie-elementen. De webforJ `Toolbar`-component is een horizontale container die een set actieknoppen, pictogrammen of andere componenten kan bevatten. Het is goed geschikt voor het beheren van pagina-instellingen en het huisvesten van belangrijke functies zoals een zoekbalk of een notificatieknop.

## Organiseren van werkbalkinhoud {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een gemakkelijk toegankelijke en consistente lay-out. Standaard neemt deze de volledige breedte van zijn ouder-element in en biedt vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Begin**: Bevat meestal een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een startknop.
- **Titel**: Gebruikt voor app-namen of logo's.
- **Inhoud**: Voor acties met hoge aandacht zoals zoeken of navigeren.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elke slot heeft een methode voor het toevoegen van componenten: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo laat zien hoe je een `Toolbar` aan een [AppLayout](./app-layout) toevoegt en alle ondersteunde slots effectief benut.
Om meer te lezen over het implementeren van werkbalken binnen een `AppLayout`, zie [Sticky werkbalken](./app-layout#sticky-toolbars) en [Mobiele navigatielay-out](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Compacte modus {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is handig wanneer je meer inhoud op het scherm wilt passen, vooral in apps met gestapelde werkbalken of beperkte ruimte. De werkbalk functioneert nog steeds hetzelfde—alleen de hoogte wordt verkleind. Deze modus wordt vaak gebruikt in headers, zijbalken of lay-outs waar de ruimte krap is.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in werkbalken {#progressbar-in-toolbars}

Een `ProgressBar` dient als visuele indicator voor lopende processen, zoals het laden van gegevens, het uploaden van bestanden of het voltooien van stappen in een flow. Wanneer deze binnen een `Toolbar` is geplaatst, aligneert de `ProgressBar` netjes langs de onderrand, waardoor deze niet opvalt, terwijl het toch duidelijk de voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de werkbalk zoals knoppen of labels zonder de lay-out te verstoren.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Stijlen {#styling}

### Thema's {#themes}

`Toolbar`-componenten omvatten <JavadocLink type="foundation" location="com/webforj/component/Theme">zeven ingebouwde thema's</JavadocLink> voor snelle visuele aanpassing:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
