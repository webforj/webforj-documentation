---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 46ea0f38e27d84ef944e7a26fd0c5666
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` is een uitgebreide responsieve lay-outcomponent die een koptekst, een voettekst, een laden en een inhoudssectie biedt. De koptekst en voettekst zijn vast, het laden schuift in en uit het viewport, en de inhoud is scrollbaar.

Deze component kan worden gebruikt om veelvoorkomende app-lay-outs te bouwen, zoals een dashboard.

## Kenmerken {#features}

De webforJ App Layout is een component die het bouwen van veelvoorkomende app-lay-outs mogelijk maakt.

<ul>
    <li>Eenvoudig te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere lay-outopties</li>
    <li>Werkt met de webforJ Donkere Modus</li>
</ul>

Het biedt een koptekst, voettekst, laden en inhoudssectie, allemaal ingebouwd in een responsieve component die eenvoudig kan worden aangepast om snel veelvoorkomende app-lay-outs zoals een dashboard te bouwen. De koptekst en voettekst zijn vast, het laden schuift in en uit het viewport, en de inhoud is scrollbaar.

Elke sectie van de lay-out is een `Div`, die elke geldige webforJ-besturing kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten met viewport-fit=cover. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het apparaatdisplay te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De volgende codevoorbeelden resulteert in een app met een opvouwbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een koptekst bevat. De demo maakt gebruik van de dwc-icon-button webcomponent om een knop voor het toggelen van het laden te maken. De knop heeft de data-drawer-toggle-attribuut die de DwcAppLayout instructie geeft om te luisteren naar klikgebeurtenissen van die component om de laadstatus te toggelen.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de koptekst en de voettekst in de off-screen modus. De off-screen modus betekent dat de positie van de koptekst en de voettekst wordt verschoven om naast het geopende laden te passen. Het uitschakelen van deze modus zorgt ervoor dat de koptekst en voettekst de volledige beschikbare ruimte innemen en de boven- en onderkant van het laden worden verschoven om bij de koptekst en de voettekst te passen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Meerdere werkbalken {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal werkbalken dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een reeks actieknoppen, pictogrammen of andere besturingselementen bevat. Gebruik gewoon de `addToHeader()`-methode om een andere `Toolbar`-component toe te voegen.

De volgende demo toont hoe je twee werkbalken kunt gebruiken. De eerste bevat de knop voor het toggelen van het laden en de titel van de app. De tweede werkbalk bevat een secundair navigatiemenu.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Plakkerige werkbalken {#sticky-toolbars}

Een plakkerige werkbalk is een werkbalk die zichtbaar blijft bovenaan de pagina wanneer de gebruiker naar beneden scrollt, maar de hoogte van de navigatiebalk wordt samengevoegd om meer ruimte beschikbaar te maken voor de inhoud van de pagina. Gewoonlijk bevat deze soort werkbalk een vaste navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om een plakkerige werkbalk te maken met behulp van de CSS-aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de optie `AppLayout.setHeaderReveal()`.

Wanneer `AppLayout.setHeaderReveal(true)` wordt aangeroepen, is de koptekst zichtbaar bij de eerste rendering en wordt deze verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer omhoog begint te scrollen, wordt de koptekst onthuld.

Met behulp van de CSS-aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te regelen hoeveel van de koptekst-navigatiebalk verborgen zal zijn.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobiele navigatie lay-out {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie aan de onderkant van de app te bieden. Dit type navigatie is vooral populair in mobiele apps.

Let op hoe het laden in de volgende demo verborgen is. De AppLayout-widget ondersteunt drie ladenposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net als bij `AppLayout.setHeaderReveal()` wordt `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de voettekst zichtbaar bij de eerste render en wordt deze verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, wordt de voettekst onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breekpunt genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breekpunt te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breekpunt moet een geldige [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Lade utilities {#drawer-utilities}

De `AppLayout` lade utilities zijn ontworpen voor geïntegreerde navigatie en contextmenu's binnen de hoofdapp-lay-out, terwijl standalone [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor extra inhoud, filters of meldingen. Deze sectie richt zich op de ingebouwde lade functies en utilities die door AppLayout worden geboden.

### Lade breekpunt {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breekpunt genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breekpunt te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breekpunt moet een geldige media query zijn.

Bijvoorbeeld, in het volgende voorbeeld is de lade breekpunt geconfigureerd op 500px of minder.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()` methode voor het definiëren van een aangepaste titel om weer te geven in de laderkop.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade acties {#drawer-actions}

De `AppLayout` component stelt je in staat om aangepaste componenten zoals knoppen of pictogrammen in het **ladekopactiesgebied** te plaatsen met behulp van de `addToDrawerHeaderActions()`-methode.

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

Lade-acties verschijnen in de **rechts uitgelijnde sectie** van de kop van de lade.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatie-lade in een `AppLayout` te toggelen. Het komt overeen met het client-side `<dwc-app-drawer-toggle>` element en is standaard gestileerd om zich te gedragen als een traditionele hamburger-menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt standaard het "menu-2" pictogram van de Tabler pictogramset. Het past automatisch het `data-drawer-toggle` attribuut toe om te integreren met het client-side ladegedrag.

```java
// Geen evenementregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De laad-toggelen werkt direct—geen handmatige gebeurtenislijsten nodig.
```
## Stijlen {#styling}

<TableBuilder name="AppLayout" />
