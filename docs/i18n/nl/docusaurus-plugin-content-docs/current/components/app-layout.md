---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 4e36be1fd55d6c17c810ace6ef3f1fd8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` component biedt een kant-en-klare paginastructuur met een vaste header en footer, een lade die in en uit schuift, en een scrollbaar inhoudsgebied. Samen dekken deze secties de lay-outbehoeften van dashboards, beheerpaneel en de meeste interfaces met meerdere secties.

<!-- INTRO_END -->

## Kenmerken {#features}

De webforJ App Layout is een component waarmee veelvoorkomende app-lay-outs kunnen worden gebouwd.

<ul>
    <li>Gebruiksvriendelijk en aanpasbaar</li>
    <li>Responsive ontwerp</li>
    <li>Meerdere lay-outopties</li>
    <li>Werkt met webforJ Donkere Modus</li>
</ul>

Het biedt een header, footer, lade en inhoudssectie die allemaal zijn geïntegreerd in een responsieve component die eenvoudig kan worden aangepast om snel veelvoorkomende app-lay-outs te bouwen, zoals een dashboard. De header en footer zijn vast, de lade schuift in en uit het zicht, en de inhoud is scrollbaar.

Elke deel van de lay-out is een `Div`, die elke geldige webforJ-besturingselement kan bevatten. Voor de beste resultaten moet de app een viewport-meta-tag bevatten die viewport-fit=cover bevat. De meta-tag zorgt ervoor dat de viewport wordt geschaald om het apparaatdisplay in te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

Het volgende codevoorbeeld resulteert in een app met een inklapbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een header bevat. De demo gebruikt de dwc-icon-button webcomponent om een knop voor het schakelen van de lade te maken. De knop heeft de data-drawer-toggle-attribuut dat de DwcAppLayout instrueert om naar klikgebeurtenissen van die component te luisteren om de status van de lade te schakelen.

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

Standaard rendert de AppLayout de header en de footer in de off-screen modus. De off-screen modus betekent dat de positie van de header en de footer zal worden verschoven om naast de geopende lade te passen. Het uitschakelen van deze modus zorgt ervoor dat de header en footer de volledige beschikbare ruimte innemen en dat de lade boven- en onderpositie zich aanpast aan de header en de footer.

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

## Meerdere toolsbars {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal toolsbars dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actieknoppen, pictogrammen of andere besturingselementen bevat. Om een extra toolbar toe te voegen, kun je eenvoudig de `addToHeader()`-methode gebruiken om een andere `Toolbar`-component toe te voegen.

De volgende demo toont hoe je twee toolbars kunt gebruiken. De eerste bevat de knop voor het schakelen van de lade en de titel van de app. De tweede toolbar bevat een secundair navigatiemenu.

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

## Plakkerige toolsbars {#sticky-toolbars}

Een plakkerige toolbar is een toolbar die zichtbaar blijft bovenaan de pagina wanneer de gebruiker naar beneden scrollt, maar de hoogte van de navigatiebalk wordt ingeklapt om meer ruimte voor de inhoud van de pagina te maken. Gewoonlijk bevat dit soort toolbar een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige toolbars te maken met de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de optie `AppLayout.setHeaderReveal()`.

Wanneer `AppLayout.setHeaderReveal(true)` wordt aangeroepen, is de header zichtbaar bij de eerste weergave, en wordt deze verborgen wanneer de gebruiker begint met naar beneden scrollen. Zodra de gebruiker weer omhoog scrollt, wordt de header weer zichtbaar.

Met de hulp van de CSS aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te regelen hoeveel van de header navigatiebalk verborgen zal zijn.

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

## Mobiele navigatie lay-out {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie onderaan de app te bieden. Dit type navigatie is specifiek populair in mobiele apps.

Let op hoe de lade verborgen is in de volgende demo. De AppLayout-widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, en `DrawerPlacement.HIDDEN`.

Net zoals `AppLayout.setHeaderReveal()`, wordt ook `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de footer zichtbaar bij eerste weergave en wordt deze verborgen wanneer de gebruiker begint met omhoog scrollen. Zodra de gebruiker weer naar beneden scrollt, wordt de footer weer zichtbaar.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()`-methode en de breakpoint moet een geldige [mediaquery](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

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

## Ladehulpmiddelen {#drawer-utilities}

De `AppLayout` ladehulpmiddelen zijn ontworpen voor geïntegreerde navigatie en contextuele menu's binnen de hoofdapp-lay-out, terwijl zelfstandige [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor extra inhoud, filters of meldingen. Deze sectie richt zich op de ingebouwde ladefuncties en -hulpmiddelen die door AppLayout worden aangeboden.

### Lade breakpoint {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breakpoint genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breakpoint te configureren met de `setDrawerBreakpoint()`-methode en de breakpoint moet een geldige mediaquery zijn.

Bijvoorbeeld, in het volgende voorbeeld is de lade breakpoint geconfigureerd om 500px of minder te zijn.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()`-methode voor het definiëren van een aangepaste titel die in de ladeheader wordt weergegeven.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Ladeacties {#drawer-actions}

De `AppLayout` component stelt je in staat om aangepaste componenten zoals knoppen of pictogrammen in het **ladeheaderactiesgebied** te plaatsen met behulp van de `addToDrawerHeaderActions()`-methode.

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

Ladeacties verschijnen in het **rechts uitgelijnde gedeelte** van de header van de lade.

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


## `AppDrawerToggle` <DocChip chip='name' label="dwc-icon-button" /> <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` te schakelen. Het komt overeen met het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om zich te gedragen als een traditionele hamburger menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt standaard het "menu-2" pictogram uit de Tabler iconenset. Het past automatisch het `data-drawer-toggle` attribuut toe om te integreren met het client-side ladegedrag.

```java
// Geen gebeurtenisregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade-schakelknop werkt direct—geen handmatige gebeurtenislijst vereist.
```
## Stijl {#styling}

<TableBuilder name="AppLayout" />
