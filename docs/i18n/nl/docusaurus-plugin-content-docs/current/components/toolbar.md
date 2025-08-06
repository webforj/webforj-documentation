---
title: Toolbar
sidebar_position: 145
_i18n_hash: 171c46f92903112a08194d130d89f2c7
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieden gebruikers snelle toegang tot belangrijke acties en navigatie-elementen. De webforJ `Toolbar` component is een horizontale container die een set actieknoppen, iconen of andere componenten kan bevatten. Het is bijzonder geschikt voor het beheren van pagina-controles en het huisvesten van belangrijke functies zoals een zoekbalk of een notificatieknop.

## Organiseren van toolbar-inhoud {#organizing-toolbar-content}

De `Toolbar` organiseert essentiële componenten in een gemakkelijk toegankelijke en consistente lay-out. Standaard neemt het de volledige breedte van zijn bovenliggende element in en biedt vier plaatsingsgebieden, of _slots_, voor het organiseren van componenten:

- **Start**: Bevat meestal een <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> of een home-knop.
- **Titel**: Gebruik voor app-namen of logo's.
- **Inhoud**: Voor acties die veel aandacht vereisen, zoals zoeken of navigatie.
- **Einde**: Minder frequente acties, zoals gebruikersprofiel of hulp.

Elke slot heeft een methode voor het toevoegen van componenten: `addToStart()`, `addToTitle()`, `addToContent()`, en `addToEnd()`.

De volgende demo toont hoe je een `Toolbar` aan een [AppLayout](./app-layout) kunt toevoegen en alle ondersteunde slots effectief kunt gebruiken.
Om meer te lezen over het implementeren van toolbars binnen een `AppLayout`, zie [Sticky toolbars](./app-layout#sticky-toolbars) en [Mobiele navigatie-indeling](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Compacte modus {#compact-mode}

Gebruik `setCompact(true)` om de padding rond een `Toolbar` te verminderen. Dit is handig wanneer je meer inhoud op het scherm moet passen, vooral in apps met gestapelde toolbars of beperkte ruimte. De toolbar gedraagt zich nog steeds hetzelfde - alleen de hoogte is verminderd. Deze modus wordt vaak gebruikt in headers, zijbalken of indelingen waar de ruimte krap is.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in toolbars {#progressbar-in-toolbars}

Een `ProgressBar` dient als een visuele indicator voor voortgangsprocessen, zoals gegevens laden, bestanden uploaden of stappen in een flow voltooien. Wanneer geplaatst in een `Toolbar`, wordt de `ProgressBar` net onderaan uitgelijnd, waardoor deze discreet blijft terwijl deze nog steeds duidelijk voortgang aan gebruikers communiceert.

Je kunt het combineren met andere componenten in de toolbar zoals knoppen of labels zonder de lay-out te verstoren.

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
