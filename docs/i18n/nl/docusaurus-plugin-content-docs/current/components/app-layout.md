---
title: AppLayout
sidebar_position: 5
_i18n_hash: 07c685c4fce66e48d5a4e6660b7bc991
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout`-component biedt je een kant-en-klaar paginavoorbeeld met een vaste header en footer, een lade die in en uit schuift, en een scrollbaar inhoudsgebied. Samen dekken deze secties de indelingsbehoeften van dashboards, beheerderspanelen en de meeste interfaces met meerdere secties.

<!-- INTRO_END -->

## Kenmerken {#features}

De webforJ App Layout is een component waarmee je veelvoorkomende app-indelingen kunt bouwen.

<ul>
    <li>Gemakkelijk te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere indelingsopties</li>
    <li>Werkt met webforJ Donker Modus</li>
</ul>

Het biedt een header, footer, lade en inhoudssectie die allemaal zijn ingebouwd in een responsieve component die gemakkelijk kan worden aangepast om snel veelvoorkomende app-indelingen, zoals een dashboard, te bouwen. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Elk deel van de indeling is een `Div`, dat elke geldige webforJ-controle kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten met viewport-fit=cover. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het apparaatdisplay te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De onderstaande codevoorbeeld resulteert in een app met een inklapbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een header bevat. De demo gebruikt de dwc-icon-button webcomponent om een lade toggle-knop te creëren. De knop heeft de data-drawer-toggle-attribuut dat de DwcAppLayout instrueert om te luisteren naar klikgebeurtenissen van die component om de lade-status te togglen.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Volledig breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de header en de footer in de off-screen modus. De off-screen modus betekent dat de header- en footerpositie zal worden verschoven om naast de geopende lade te passen. Het uitschakelen van deze modus zorgt ervoor dat de header en footer de volledige beschikbare ruimte innemen en de bovenste en onderste positie van de lade verschuiven om in overeenstemming te zijn met de header en footer.

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
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Meerdere werkbalken {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal werkbalken dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actieknoppen, iconen of andere controles bevat. Om een extra werkbalk toe te voegen, gebruik eenvoudig de `addToHeader()`-methode om een andere `Toolbar`-component toe te voegen.

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
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Plakkerige werkbalken {#sticky-toolbars}

Een plakkerige werkbalk is een werkbalk die zichtbaar blijft bovenaan de pagina wanneer de gebruiker naar beneden scrollt, maar waarvan de hoogte van de navigatiebalk is samengevouwen om meer ruimte te creëren voor de inhoud van de pagina. Gewoonlijk bevat deze soort werkbalk een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige werkbalken te maken met de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de `AppLayout.setHeaderReveal()` optie.

Wanneer `AppLayout.setHeaderReveal(true)` is ingesteld, is de header zichtbaar bij de eerste render, en vervolgens verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer omhoog begint te scrollen, wordt de header onthuld.

Met behulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te regelen hoeveel van de header navigatiebalk verborgen zal zijn.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobiele navigatie-indeling {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie aan de onderkant van de app te bieden. Dit type navigatie is specifiek populair in mobiele apps.

Let op hoe de lade in de volgende demo verborgen is. De AppLayout-widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net als bij `AppLayout.setHeaderReveal()` wordt `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de footer zichtbaar bij de eerste render en wordt deze verborgen wanneer de gebruiker naar boven begint te scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, wordt de footer onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay wordt gepopoverd. Het is mogelijk om de breakpoint te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breakpoint moet een geldige [mediaquery](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Lade-utiliteiten {#drawer-utilities}

De `AppLayout` lade-utiliteiten zijn ontworpen voor geïntegreerde navigatie en contextmenu's binnen de hoofd indeling van de app, terwijl zelfstandige [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor extra inhoud, filters of meldingen. Deze sectie richt zich op de ingebouwde ladefuncties en utiliteiten die door AppLayout worden aangeboden.

### Lade breakpoint {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breakpoint genoemd. Popover-modus betekent dat de lade over het inhoudsgebied met een overlay wordt gepopoverd. Het is mogelijk om de breakpoint te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breakpoint moet een geldige mediaquery zijn.

Bijvoorbeeld, in het volgende voorbeeld is de lade breakpoint geconfigureerd op 500px of minder.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade titel {#drawer-title}

De `AppLayout`-component biedt een `addToDrawerTitle()`-methode om een aangepaste titel te definiëren die in de lade-header moet worden weergegeven. 

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout`-component stelt je in staat om aangepaste componenten zoals knoppen of iconen in het **actiesgebied van de lade-header** te plaatsen met behulp van de `addToDrawerHeaderActions()`-methode.

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

Lade-acties verschijnen in de **rechtonderste hoek** van de lade-header.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ-klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` te togglen. Het komt overeen met het client-side `<dwc-app-drawer-toggle>` element en is standaard gestileerd om zich te gedragen als een traditionele hamburger menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt het "menu-2" pictogram van de Tabler iconenset standaard. Het past automatisch de `data-drawer-toggle` attribuut toe om te integreren met het client-side ladegedrag.

```java
// Geen evenementenregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade-toggle zal direct werken - geen handmatige gebeurtenislisters nodig.
```
## Stijling {#styling}

<TableBuilder name="AppLayout" />
