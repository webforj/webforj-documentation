---
title: AppLayout
sidebar_position: 5
_i18n_hash: 8b9351e865e2651e84f0ae16ef5efc21
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` component biedt je een kant-en-klare pagin structuur met een vaste header en footer, een lade die in en uit schuift, en een scrollbaar contentgebied. Samen dekken deze secties de layoutbehoeften van dashboards, adminpanelen en de meeste multi-sectie interfaces.

<!-- INTRO_END -->

## Kenmerken {#features}

De webforJ App Layout is een component waarmee je veelvoorkomende app-layouts kunt bouwen.

<ul>
    <li>Eenvoudig te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere layoutopties</li>
    <li>Werkt met webforJ Donkere Mode</li>
</ul>

Het biedt een header, footer, lade en contentsectie als onderdeel van een responsieve component die eenvoudig kan worden aangepast om snel veelvoorkomende app-layouts zoals een dashboard te bouwen. De header en footer zijn vast, de lade schuift in en uit het viewport, en de content is scrollbaar.

Elke part van de layout is een `Div`, dat elk geldig webforJ-besturingselement kan bevatten. Voor de beste resultaten moet de app een meta-tag voor het viewport bevatten die viewport-fit=cover bevat. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het apparaatdisplay te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De volgende code-sample resulteert in een app met een inklapbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een header bevat. De demo gebruikt de dwc-icon-button webcomponent om een knop te maken voor het toggelen van de lade. De knop heeft het data-drawer-toggle attribuut dat de DwcAppLayout instrueert om te luisteren naar click events van die component om de lade status te toggelen.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de header en de footer in de off-screen modus. De off-screen modus betekent dat de positie van de header en de footer zal worden verschoven om naast de geopende lade te passen. Het uitschakelen van deze modus zal ervoor zorgen dat de header en footer de volledige beschikbare ruimte innemen en de boven- en onderpositie van de lade verschuiven om te passen bij de header en de footer.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Meerdere werkbalken {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal werkbalken dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actieknoppen, pictogrammen of andere besturingselementen bevat. Om een extra werkbalk toe te voegen, gebruik je eenvoudig de `addToHeader()` methode om een andere `Toolbar` component toe te voegen.

De volgende demo toont hoe je twee werkbalken kunt gebruiken. De eerste bevat de knop om de lade te toggelen en de titel van de app. De tweede werkbalk bevat een secundair navigatiemenu.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Plakbare werkbalken {#sticky-toolbars}

Een plakbare werkbalk is een werkbalk die zichtbaar blijft bovenaan de pagina wanneer de gebruiker naar beneden scrollt, maar de hoogte van de navigatiebalk is ingeklapt om meer ruimte beschikbaar te maken voor de inhoud van de pagina. Gewoonlijk bevat deze soort werkbalk een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakbare werkbalken te creëren met behulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de `AppLayout.setHeaderReveal()` optie.

Wanneer `AppLayout.setHeaderReveal(true)` wordt aangeroepen, zal de header zichtbaar zijn bij de eerste weergave, en dan verborgen wanneer de gebruiker begint naar beneden te scrollen. Zodra de gebruiker weer omhoog begint te scrollen, zal de header weer zichtbaar worden.

Met behulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te regelen hoeveel van de header navigatiebalk verborgen zal zijn.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobiele navigatie-layout {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie aan de onderkant van de app te bieden. Dit soort navigatie is specifiek populair in mobiele apps.

Opmerking hoe de lade verborgen is in de volgende demo. De AppLayout widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, en `DrawerPlacement.HIDDEN`.

Net als `AppLayout.setHeaderReveal()`, wordt ook `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de footer zichtbaar bij de eerste weergave en verborgen wanneer de gebruiker begint omhoog te scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, zal de footer worden onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade gewijzigd naar popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over het inhoudsgebied verschijnt met een overlay. Het is mogelijk om de breakpoint te configureren met behulp van de `setDrawerBreakpoint()` methode en de breakpoint moet een geldige [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Lade-hulpmiddelen {#drawer-utilities}

De `AppLayout` lade-hulpmiddelen zijn ontworpen voor geïntegreerde navigatie en contextmenu's binnen de hoofdapp-layout, terwijl standalone [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor aanvullende content, filters of meldingen. Deze sectie richt zich op de ingebouwde lade functies en hulpmiddelen geboden door AppLayout.

### Lade-breakpoint {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade gewijzigd naar popover-modus. Dit wordt de breakpoint genoemd. Popover-modus betekent dat de lade over het inhoudsgebied verschijnt met een overlay. Het is mogelijk om de breakpoint te configureren met behulp van de `setDrawerBreakpoint()` methode en de breakpoint moet een geldige media query zijn.

Bijvoorbeeld, in de volgende sample is de lade-breakpoint geconfigureerd om 500px of minder te zijn.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade-titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()` methode voor het definiëren van een aangepaste titel die in de lade-header moet worden weergegeven. 

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout` component staat je toe om aangepaste componenten zoals knoppen of pictogrammen in de **lade-header actiegedeelte** te plaatsen met behulp van de `addToDrawerHeaderActions()` methode.

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

Lade-acties verschijnen in de **rechts uitgelijnde sectie** van de lade-header.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ-klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` te toggelen. Het komt overeen met het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om zich te gedragen als een traditioneel hamburger menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt standaard het "menu-2" pictogram uit de Tabler iconenset. Het past automatisch het `data-drawer-toggle` attribuut toe om te integreren met het client-side ladegedrag.

```java
// Geen registratie van evenementen vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade-toggle werkt direct—geen handmatige evenementluisteraars nodig.
```
## Stijling {#styling}

<TableBuilder name="AppLayout" />
