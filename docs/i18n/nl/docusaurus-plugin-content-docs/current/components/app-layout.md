---
title: AppLayout
sidebar_position: 5
_i18n_hash: 7bc8b2a8bfc772644cf2107199615515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` is een uitgebreide responsieve lay-outcomponent die een header, een footer, een lade en een contentsectie biedt. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Deze component kan worden gebruikt om veelvoorkomende app-structuren te bouwen, zoals een dashboard.

## Kenmerken {#features}

De webforJ App Layout is een component waarmee veelvoorkomende app-lay-outs kunnen worden gebouwd.

<ul>
    <li>Gemakkelijk te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere lay-outopties</li>
    <li>Werkt met webforJ Dark Mode</li>
</ul>

Het biedt een header, footer, lade en contentsectie die allemaal zijn gebouwd in een responsieve component die eenvoudig kan worden aangepast om snel veelvoorkomende app-structuren zoals een dashboard te bouwen. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Elk deel van de lay-out is een `Div`, die elke geldige webforJ-controle kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten die viewport-fit=cover bevat. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het apparaatdisplay te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De volgende codesample resulteert in een app met een inklapbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties bevat, evenals een header. De demo gebruikt de dwc-icon-button webcomponent om een knop voor het omzetten van de lade te creëren. De knop heeft het data-drawer-toggle attribuut dat de DwcAppLayout instrueert om op klikgebeurtenissen van die component te luisteren om de lade-status te wisselen.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de header en de footer in de off-screen modus. De off-screen modus betekent dat de positie van de header en de footer wordt verschoven om naast de geopende lade te passen. Het uitschakelen van deze modus zal ervoor zorgen dat de header en footer de volledige beschikbare ruimte innemen en de boven- en onderpositie van de lade verschuiven om met de header en footer te passen.

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

De volgende demo laat zien hoe je twee werkbalken kunt gebruiken. De eerste bevat de knop voor het omzetten van de lade en de titel van de app. De tweede werkbalk bevat een secundaire navigatiemenu.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Plakkerige werkbalken {#sticky-toolbars}

Een plakkerige werkbalk is een werkbalk die zichtbaar blijft aan de bovenkant van de pagina wanneer de gebruiker naar beneden scrolt, maar de hoogte van de navigatiebalk is samengevouwen om meer ruimte beschikbaar te maken voor de inhoud van de pagina. Gewoonlijk bevat dit soort werkbalk een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige werkbalken te creëren met behulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de `AppLayout.setHeaderReveal()` optie.

Wanneer `AppLayout.setHeaderReveal(true)` is ingesteld, zal de header zichtbaar zijn bij de eerste weergave, en vervolgens verborgen worden wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer naar boven begint te scrollen, wordt de header onthuld.

Met behulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te controleren hoeveel van de header navigatiebalk verborgen zal zijn.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobiele navigatie-indeling {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie onderaan de app te bieden. Dit type navigatie is specifiek populair in mobiele apps.

Let op hoe de lade verborgen is in de volgende demo. De AppLayout widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net als `AppLayout.setHeaderReveal()`, wordt ook `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de footer zichtbaar bij de eerste weergave en wordt deze verborgen wanneer de gebruiker begint met scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, wordt de footer onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breakpoint te configureren met behulp van de `setDrawerBreakpoint()` methode en de breakpoint moet een geldige [mediaquery](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Lade-hulpmiddelen {#drawer-utilities}

De `AppLayout` lade-hulpmiddelen zijn ontworpen voor geïntegreerde navigatie en contextmenu's binnen de hoofdapp-lay-out, terwijl standalone [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor aanvullende inhoud, filters of meldingen. Deze sectie richt zich op de ingebouwde lade-functies en hulpmiddelen die door AppLayout worden aangeboden.

### Lade-breakpoint {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breakpoint genoemd. Popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()` methode en de breakpoint moet een geldige mediaquery zijn.

Bijvoorbeeld, in de volgende sample is de lade-breakpoint geconfigureerd op 500px of minder.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade-titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()` methode om een aangepaste titel te definiëren die in de lade-header wordt weergegeven.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout` component staat je toe om aangepaste componenten zoals knoppen of pictogrammen in het **actiesgebied van de lade-header** te plaatsen met behulp van de `addToDrawerHeaderActions()` methode.

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

Lade-acties verschijnen in het **rechtsuitgelijnde gedeelte** van de header van de lade.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` om te schakelen. Het mappt naar het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om zich te gedragen als een traditioneel hamburger menu pictogram, dit gedrag kan worden gepersonaliseerd.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt standaard het "menu-2" pictogram van de Tabler-pictogramset. Het past automatisch het `data-drawer-toggle` attribuut toe om zich te integreren met het client-side lade gedrag.

```java
// Geen gebeurtenisregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade-omschakelaar werkt direct - geen handmatige gebeurtenislijsten nodig.
```
## Stijling {#styling}

<TableBuilder name="AppLayout" />
