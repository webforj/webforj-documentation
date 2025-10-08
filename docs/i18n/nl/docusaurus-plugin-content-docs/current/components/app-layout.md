---
title: AppLayout
sidebar_position: 5
_i18n_hash: e6da714fff4ce713ceb5b486b8ab0026
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` is een uitgebreide responsieve lay-outcomponent die een header, een footer, een lade en een inhoudssectie biedt. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Deze component kan worden gebruikt om veelvoorkomende app-lay-outs te bouwen, zoals een dashboard.

## Features {#features}

De webforJ App Layout is een component waarmee je veelvoorkomende app-lay-outs kunt bouwen.

<ul>
    <li>Eenvoudig te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere lay-outopties</li>
    <li>Werkt met webforJ Dark Mode</li>
</ul>

Het biedt een header, footer, lade en inhoudssectie, allemaal ingebouwd in een responsieve component die eenvoudig kan worden aangepast om snel veelvoorkomende app-lay-outs zoals een dashboard te bouwen. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

 elk deel van de lay-out is een `Div`, die elke geldige webforJ-besturing kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten die viewport-fit=cover bevat. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het apparaatdisplay te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overview {#overview}

De volgende codevoorbeeld resulteert in een app met een opvouwbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een header bevat. De demo gebruikt de dwc-icon-button webcomponent om een knop voor het schakelen van de lade te maken. De knop heeft de data-drawer-toggle-attribuut dat de DwcAppLayout instrueert om naar klik gebeurtenissen van die component te luisteren om de lade-status te schakelen.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de header en de footer in de off-screen modus. De off-screen modus betekent dat de positie van de header en de footer wordt verschoven om naast de geopende lade te passen. Het uitschakelen van deze modus zorgt ervoor dat de header en footer de volledige beschikbare ruimte innemen en de bovenste en onderste positie van de lade verschuiven om bij de header en de footer te passen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Meerdere werkbalken {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal werkbalken dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actiekoppen, pictogrammen of andere besturingselementen bevat. Om een extra werkbalk toe te voegen, gebruik je eenvoudig de `addToHeader()`-methode om een andere `Toolbar`-component toe te voegen.

De volgende demo laat zien hoe je twee werkbalken kunt gebruiken. De eerste huisvest de togglesknop van de lade en de titel van de app. De tweede werkbalk huisvest een secundair navigatiemenu.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Plak werkbalken {#sticky-toolbars}

Een plakkerige werkbalk is een werkbalk die zichtbaar blijft bovenaan de pagina wanneer de gebruiker naar beneden scrollt, maar de hoogte van de navigatiebalk wordt samengevoegd om meer ruimte beschikbaar te maken voor de inhoud van de pagina. Gewoonlijk bevat dit soort werkbalk een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige werkbalken te maken met behulp van de CSS-aanpassingseigenschap `--dwc-app-layout-header-collapse-height` en de optie `AppLayout.setHeaderReveal()`.

Wanneer `AppLayout.setHeaderReveal(true)` wordt ingesteld, is de header zichtbaar bij de eerste weergave en wordt verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer omhoog begint te scrollen, wordt de header onthuld.

Met behulp van de CSS-aanpassingseigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te regelen hoeveel van de header navigatiebalk verborgen zal zijn.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobiele navigatie lay-out {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie onderaan de app te bieden. Dit type navigatie is vooral populair in mobiele apps.

Let op hoe de lade verborgen is in de volgende demo. De AppLayout-widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net als `AppLayout.setHeaderReveal()` wordt `AppLayout.setFooterReveal()` ook ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de footer zichtbaar bij de eerste weergave en wordt deze verborgen wanneer de gebruiker omhoog begint te scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, wordt de footer onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade omgeschakeld naar de popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over de inhoudsgebied popt met een overlay. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()`-methode en de breakpoint moet een geldige [mediaquery](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
/>

## Ladehulpmiddelen {#drawer-utilities}

De `AppLayout` ladehulpmiddelen zijn ontworpen voor geïntegreerde navigaties en contextuele menu's binnen de hoofdapp-lay-out, terwijl zelfstandige [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor extra inhoud, filters of meldingen. Dit gedeelte richt zich op de ingebouwde lade functies en hulpmiddelen die door AppLayout worden geleverd.

### Lade breakpoint {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade omgeschakeld naar de popover-modus. Dit wordt de breakpoint genoemd. Popover-modus betekent dat de lade over de inhoudsgebied popt met een overlay. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()`-methode en de breakpoint moet een geldige mediaquery zijn.

Bijvoorbeeld, in het volgende voorbeeld is de lade breakpoint geconfigureerd op 500px of minder.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()` methode voor het definiëren van een aangepaste titel die in de ladeheader wordt weergegeven. 

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout` component stelt je in staat om aangepaste componenten zoals knoppen of pictogrammen in het **ladeheader-actiesgebied** te plaatsen met behulp van de `addToDrawerHeaderActions()` methode.

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

Het is mogelijk om meerdere componenten als argumenten door te geven:

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profiel")
);
```

Lade-acties verschijnen in de **rechts uitgelijnde sectie** van de header van de lade.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ-klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` om te schakelen. Het mappt naar het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om te gedragen als een traditionele hamburger menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt het "menu-2" icoon uit de Tabler-iconenset standaard. Het past automatisch het `data-drawer-toggle` attribuut toe om te integreren met het client-side ladegedrag.

```java
// Geen evenementregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade toggle werkt direct—geen handmatige gebeurtenislijsten nodig.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
