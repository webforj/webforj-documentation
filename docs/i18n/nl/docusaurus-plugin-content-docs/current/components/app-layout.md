---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 7f842a66a5bcca7efe7ee894a0b001b0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` is een uitgebreide responsieve lay-outcomponent die een header, een footer, een lade en een inhoudssectie biedt. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Deze component kan worden gebruikt om gemeenschappelijke app-lay-outs te bouwen, zoals een dashboard.

## Kenmerken {#features}

De webforJ App Layout is een component waarmee gemeenschappelijke app-lay-outs kunnen worden gebouwd.

<ul>
    <li>Gemakkelijk te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere lay-outopties</li>
    <li>Werkt met de webforJ Donkere Modus</li>
</ul>

Het biedt een header, footer, lade, en inhoudssectie die allemaal zijn ingebouwd in een responsieve component die gemakkelijk kan worden aangepast om snel gemeenschappelijke app-lay-outs, zoals een dashboard, te bouwen. De header en footer zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Elk deel van de lay-out is een `Div`, die elke geldige webforJ-besturing kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten die viewport-fit=cover bevat. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het scherm van het apparaat te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De volgende codevoorbeeld resulteert in een app met een inklapbare zijbalk die een logo en tabs voor verschillende inhoudsopties en een header bevat. De demo gebruikt de dwc-icon-button webcomponent om een knop voor het omzetten van de lade te maken. De knop heeft de data-drawer-toggle-attribuut, die de DwcAppLayout instrueert om klikgebeurtenissen van die component te luisteren om de lade-status om te schakelen.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de header en de footer in de off-screen modus. De off-screen modus betekent dat de positie van de header en de footer zal verschuiven om naast de geopende lade te passen. Het uitschakelen van deze modus zal ertoe leiden dat de header en footer de volledige beschikbare ruimte innemen en de bovenste en onderste positie van de lade verschuiven om bij de header en footer te passen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Meerdere werkbalken {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal werkbalken dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actieknoppen, pictogrammen of andere bedieningselementen bevat. Om een extra werkbalk toe te voegen, gebruik je gewoon de `addToHeader()`-methode om een andere `Toolbar`-component toe te voegen.

De volgende demo laat zien hoe je twee werkbalken kunt gebruiken. De eerste bevat de knop voor het omzetten van de lade en de titel van de app. De tweede werkbalk bevat een secundair navigatiemenu.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Plakkerige werkbalken {#sticky-toolbars}

Een plakkerige werkbalk is een werkbalk die zichtbaar blijft bovenaan de pagina wanneer de gebruiker omlaag scrollt, maar de hoogte van de navigatiebalk is samengevouwen om meer ruimte beschikbaar te maken voor de inhoud van de pagina. Meestal bevat dit soort werkbalk een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige werkbalken te creëren met behulp van de CSS-aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de optie `AppLayout.setHeaderReveal()`.

Wanneer `AppLayout.setHeaderReveal(true)` wordt aangeroepen, is de header zichtbaar bij de eerste render, en wordt deze verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer omhoog begint te scrollen, zal de header worden onthuld.

Met de hulp van de CSS-aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te controleren hoeveel van de header-navigatiebalk verborgen zal worden.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobiele navigatie lay-out {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie aan de onderkant van de app te bieden. Dit type navigatie is specifiek populair in mobiele apps.

Let op hoe de lade verborgen is in de volgende demo. De AppLayout-widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net als bij `AppLayout.setHeaderReveal()`, wordt `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de footer aanvankelijk zichtbaar en wordt deze verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer begint te scrollen, zal de footer worden onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, zal de lade worden overgeschakeld naar de popover-modus. Dit wordt de breekpunt genoemd. De popover-modus betekent dat de lade boven de inhoudszone met een overlay zal verschijnen. Het is mogelijk om de breekpunt te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breekpunt moet een geldige [mediaquery](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Lade-hulpmiddelen {#drawer-utilities}

De `AppLayout` lade-hulpmiddelen zijn ontworpen voor geïntegreerde navigatie en contextuele menu's binnen de hoofdapp-lay-out, terwijl zelfstandige [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor aanvullende inhoud, filters of meldingen. Deze sectie richt zich op de ingebouwde lade-functies en hulpmiddelen die door AppLayout worden aangeboden.

### Lade-breekpunt {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, zal de lade worden overgeschakeld naar de popover-modus. Dit wordt de breekpunt genoemd. De popover-modus betekent dat de lade boven de inhoudszone met een overlay zal verschijnen. Het is mogelijk om de breekpunt te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breekpunt moet een geldige mediaquery zijn.

Bijvoorbeeld, in het volgende voorbeeld is de lade-breekpunt geconfigureerd om 500px of minder te zijn.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade-titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()`-methode om een aangepaste titel te definiëren die in de lade-header moet worden weergegeven.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout` component stelt je in staat om aangepaste componenten zoals knoppen of pictogrammen in het **lade-header-actiesgebied** te plaatsen met behulp van de `addToDrawerHeaderActions()`-methode.

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

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` om te schakelen. Het komt overeen met het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om zich te gedragen als een traditionele hamburger menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt standaard het "menu-2" pictogram van de Tabler-iconenset. Het past automatisch het `data-drawer-toggle` attribuut toe om te integreren met het client-side lade gedrag.

```java
// Geen eventregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade-toggle zal out of the box werken—geen handmatige gebeurtenisluisteraars nodig.
```

## Stijlen {#styling}

<TableBuilder name="AppLayout" />
