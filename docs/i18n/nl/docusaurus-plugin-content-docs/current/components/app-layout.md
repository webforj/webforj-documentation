---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 559d0c63a8e61e2e3d79086aa08922c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` component biedt je een kant-en-klare paginstructuur met een vaste koptekst en voettekst, een lade die in en uit schuift, en een scrollbaar inhoudsgebied. Samen dekken deze secties de layoutbehoeften van dashboards, beheerderspanelen en de meeste interfaces met meerdere secties.

<!-- INTRO_END -->

## Kenmerken {#features}

De webforJ App Layout is een component die het mogelijk maakt om veelvoorkomende app-layouts te bouwen.

<ul>
    <li>Gemakkelijk te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere layoutopties</li>
    <li>Werkt met webforJ Donkere Modus</li>
</ul>

Het biedt een koptekst, voettekst, lade en inhoudssectie, allemaal ingebouwd in een responsieve component die eenvoudig kan worden aangepast om snel veelvoorkomende app-layouts, zoals een dashboard, te bouwen. De koptekst en voettekst zijn vast, de lade schuift in en uit het viewport en de inhoud is scrollbaar.

Elk onderdeel van de layout is een `Div`, die elke geldige webforJ-controle kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten met `viewport-fit=cover`. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het scherm van het apparaat te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De volgende codevoorbeelden zullen resulteren in een app met een inklapbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een koptekst bevat. De demo gebruikt de dwc-icon-button webcomponent om een knop voor het toggelen van de lade te maken. De knop heeft de data-drawer-toggle attribuut die de DwcAppLayout instrueert om naar klikgebeurtenissen van die component te luisteren om de lade-status te schakelen.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de koptekst en de voettekst in de off-screen modus. De off-screen modus betekent dat de positie van de koptekst en de voettekst zal verschuiven om naast de geopende lade te passen. Het uitschakelen van deze modus zorgt ervoor dat de koptekst en voettekst de volledige beschikbare ruimte innemen en de boven- en onderpositie van de lade zullen verschuiven om met de koptekst en de voettekst overeen te komen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutfullnavbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Meerdere werkbalken {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal werkbalken die je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actieknoppen, iconen of andere controles bevat. Om een extra werkbalk toe te voegen, gebruik je eenvoudig de `addToHeader()` methode om een andere `Toolbar` component toe te voegen.

De volgende demo laat zien hoe je twee werkbalken kunt gebruiken. De eerste bevat de toggle-knop van de lade en de titel van de app. De tweede werkbalk bevat een secundair navigatiemenu.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Plakkerige werkbalken {#sticky-toolbars}

Een plakkerige werkbalk is een werkbalk die aan de bovenkant van de pagina zichtbaar blijft wanneer de gebruiker naar beneden scrollt, maar de hoogte van de navigatiebalk wordt verkleind om meer ruimte voor de inhoud van de pagina beschikbaar te maken. Gewoonlijk bevat dit soort werkbalk een vaste navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige werkbalken te maken met de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de `AppLayout.setHeaderReveal()` optie.

Wanneer `AppLayout.setHeaderReveal(true)` is ingesteld, zal de koptekst zichtbaar zijn bij de eerste render, en vervolgens verborgen worden wanneer de gebruiker begint met naar beneden scrollen. Zodra de gebruiker weer naar boven begint te scrollen, zal de koptekst weer zichtbaar worden.

Met behulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te regelen hoeveel van de koptekst navigatiebalk verborgen zal zijn.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobiele navigatie-layout {#mobile-navigation-layout}

De onderkant van de navigatiebalk kan worden gebruikt om een andere versie van de navigatie onder in de app te bieden. Dit type navigatie is speciaal populair in mobiele apps.

Let op hoe de lade verborgen is in de volgende demo. De AppLayout-widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net als `AppLayout.setHeaderReveal()`, wordt `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, zal de voettekst zichtbaar zijn bij de eerste render en dan verborgen worden wanneer de gebruiker begint met naar boven scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, zal de voettekst zichtbaar worden.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade omgeschakeld naar de popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over het inhoudsgebied verschijnt met een overlay. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()` methode en de breakpoint moet een geldige [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Lade-utiliteiten {#drawer-utilities}

De `AppLayout` lade-utiliteiten zijn ontworpen voor geïntegreerde navigatie en contextuele menu's binnen de hoofdapp-layout, terwijl staan-alone [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor extra inhoud, filters of meldingen. Deze sectie richt zich op de ingebouwde ladefuncties en utiliteiten die door AppLayout worden geboden.

### Lade breakpoint {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade omgeschakeld naar de popover-modus. Dit wordt de breakpoint genoemd. Popover-modus betekent dat de lade over het inhoudsgebied verschijnt met een overlay. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()` methode en de breakpoint moet een geldige media query zijn.

Bijvoorbeeld, in de volgende sample is de lade breakpoint geconfigureerd op 500px of minder.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()` methode voor het definiëren van een aangepaste titel om weer te geven in de lade koptekst.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout` component maakt het mogelijk om aangepaste componenten zoals knoppen of iconen in het **lade koptekstactiesgebied** te plaatsen met behulp van de `addToDrawerHeaderActions()` methode.

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

Lade-acties verschijnen in de **rechts uitgelijnde sectie** van de lade-koptekst.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ klasse die een knop weergeeft die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` te toggelen. Het komt overeen met het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om zich als een traditionele hamburger-menu-icoon te gedragen; dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` is een uitbreiding van `IconButton` en gebruikt standaard het "menu-2" icoon van de Tabler-icoonset. Het past automatisch de `data-drawer-toggle` attribuut toe om te integreren met het client-side ladegedrag.

```java
// Geen gebeurtenisregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade toggle zal out of the box werken—geen handmatige gebeurtenislijsten nodig.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
