---
title: AppLayout
sidebar_position: 5
_i18n_hash: 0aea09dee535e578082dd6df642503d4
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

De `AppLayout` component biedt een kant-en-klare pagin structuur met een vaste kop en voettekst, een lade die naar binnen en naar buiten schuift, en een scrollbaar inhoudsgebied. Samen dekken deze secties de lay-outbehoeften van dashboards, beheerderspanelen en de meeste interfaces met meerdere secties.

<!-- INTRO_END -->

## Kenmerken {#features}

De webforJ App Layout is een component die het mogelijk maakt om gemeenschappelijke app-lay-outs te bouwen.

<ul>
    <li>Gemakkelijk te gebruiken en aan te passen</li>
    <li>Responsief ontwerp</li>
    <li>Meerdere lay-outopties</li>
    <li>Werkt met webforJ Donkere modus</li>
</ul>

Het biedt een kop, voettekst, lade en inhoudsectie, allemaal ingebouwd in een responsieve component die gemakkelijk kan worden aangepast om snel gemeenschappelijke app-lay-outs, zoals een dashboard, te bouwen. De kop en voettekst zijn vast, de lade schuift in en uit het viewport, en de inhoud is scrollbaar.

Elk deel van de lay-out is een `Div`, die elke geldige webforJ-controle kan bevatten. Voor de beste resultaten moet de app een viewport meta-tag bevatten met viewport-fit=cover. De meta-tag zorgt ervoor dat het viewport wordt geschaald om het display van het apparaat te vullen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overzicht {#overview}

De volgende codevoorbeelden resulteren in een app met een inklapbare zijbalk die een logo en tabbladen voor verschillende inhoudsopties en een kop bevat. De demo gebruikt de dwc-icon-button webcomponent om een knop voor het schakelen van de lade te maken. De knop heeft de data-drawer-toggle-attribuut dat de DwcAppLayout instrueert om op klik gebeurtenissen van die component te luisteren om de staat van de lade te schakelen.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Volledige breedte navigatiebalk {#full-width-navbar}

Standaard rendert de AppLayout de kop en de voettekst in de off-screen modus. De off-screen modus betekent dat de positie van de kop en voettekst wordt verschoven om naast de geopende lade te passen. Het uitschakelen van deze modus zal ervoor zorgen dat de kop en voettekst de volledige beschikbare ruimte innemen en de boven- en onderpositie van de lade verschuiven om bij de kop en voettekst te passen.

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

## Meerdere toolbars {#multiple-toolbars}

De navigatiebalk heeft geen limiet aan het aantal toolbars dat je kunt toevoegen. Een `Toolbar` is een horizontale containercomponent die een set actiekoppelingen, pictogrammen of andere bedieningselementen bevat. Om een extra toolbar toe te voegen, gebruik je eenvoudig de `addToHeader()`-methode om een andere `Toolbar`-component toe te voegen.

De volgende demo toont hoe je twee toolbars kunt gebruiken, de eerste bevat de toggle-knop van de lade en de titel van de app. De tweede toolbar bevat een secundair navigatiemenu.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Plakkerige toolbars {#sticky-toolbars}

Een plakkerige toolbar is een toolbar die zichtbaar blijft bovenaan de pagina wanneer de gebruiker naar beneden scrollt, maar die de hoogte van de navigatiebalk is samengevallen om meer ruimte beschikbaar te maken voor de inhoud van de pagina. Gewoonlijk bevat dit soort toolbar een vast navigatiemenu dat relevant is voor de huidige pagina.

Het is mogelijk om plakkerige toolbars te maken met behulp van de CSS-aangepaste eigenschap `--dwc-app-layout-header-collapse-height` en de optie `AppLayout.setHeaderReveal()`.

Wanneer `AppLayout.setHeaderReveal(true)` wordt aangeroepen, zal de kop zichtbaar zijn bij de eerste render en vervolgens verborgen worden wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer omhoog begint te scrollen, zal de kop worden onthuld.

Met behulp van de CSS-aangepaste eigenschap `--dwc-app-layout-header-collapse-height` is het mogelijk om te controleren hoeveel van de kopnavigatiebalk zal worden verborgen.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobiele navigatie lay-out {#mobile-navigation-layout}

De onderste navigatiebalk kan worden gebruikt om een andere versie van de navigatie onderaan de app te bieden. Dit type navigatie is specifiek populair in mobiele apps.

Let op hoe de lade verborgen is in de volgende demo. De AppLayout-widget ondersteunt drie ladeposities: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` en `DrawerPlacement.HIDDEN`.

Net zoals `AppLayout.setHeaderReveal()`, wordt `AppLayout.setFooterReveal()` ondersteund. Wanneer `AppLayout.setFooterReveal(true)` wordt aangeroepen, is de voettekst zichtbaar bij de eerste render en wordt vervolgens verborgen wanneer de gebruiker begint te scrollen. Zodra de gebruiker weer naar beneden begint te scrollen, zal de voettekst worden onthuld.

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breekpunt genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breekpunt te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breekpunt moet een geldige [mediaquery](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) zijn.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Lade-hulpmiddelen {#drawer-utilities}

De `AppLayout` lade-hulpmiddelen zijn ontworpen voor geïntegreerde navigatie en contextuele menu's binnen de hoofdlayout van de app, terwijl zelfstandige [`Drawer`](https://docs.webforj.com/docs/components/drawer) componenten flexibele, onafhankelijke schuifpanelen bieden die overal in je app kunnen worden gebruikt voor extra inhoud, filters of meldingen. Dit gedeelte richt zich op de ingebouwde lade-functies en -hulpmiddelen die door AppLayout worden aangeboden.

### Lade-breekpunt {#drawer-breakpoint}

Standaard, wanneer de schermbreedte 800px of minder is, wordt de lade overgeschakeld naar popover-modus. Dit wordt de breekpunt genoemd. De popover-modus betekent dat de lade over het inhoudsgebied met een overlay zal verschijnen. Het is mogelijk om de breekpunt te configureren met behulp van de `setDrawerBreakpoint()`-methode en de breekpunt moet een geldige mediaquery zijn.

Bijvoorbeeld, in het volgende voorbeeld is de lade-breekpunt geconfigureerd op 500px of minder.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Lade-titel {#drawer-title}

De `AppLayout` component biedt een `addToDrawerTitle()`-methode om een aangepaste titel te definiëren die in de lade-kop moet worden weergegeven. 

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Lade-acties {#drawer-actions}

De `AppLayout` component laat je aangepaste componenten, zoals knoppen of pictogrammen, plaatsen in de **lade-kopacties gebied** met behulp van de `addToDrawerHeaderActions()`-methode.

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

Lade-acties verschijnen in de **rechtergedeelte** van de kop van de lade.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

De [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is een server-side webforJ klasse die een knop vertegenwoordigt die wordt gebruikt om de zichtbaarheid van een navigatielade in een `AppLayout` te schakelen. Het map naar het client-side `<dwc-app-drawer-toggle>` element en is standaard gestyled om zich te gedragen als een traditionele hamburger-menu-icoon, dit gedrag kan worden aangepast.

### Overzicht {#overview-1}

De `AppDrawerToggle` breidt `IconButton` uit en gebruikt het "menu-2" pictogram uit de Tabler-pictogramset standaard. Het past automatisch de `data-drawer-toggle`-attribuut toe om te integreren met het client-side lade-gedrag.

```java
// Geen evenementregistratie vereist:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// De lade-toggle werkt direct - geen handmatige gebeurtenislijst nodig.
```
## Stijl {#styling}

<TableBuilder name="AppLayout" />
