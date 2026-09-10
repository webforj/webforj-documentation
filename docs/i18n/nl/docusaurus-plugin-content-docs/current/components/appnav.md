---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: afb61d8d44c3f5dcb03f533954baafc1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

De `AppNav` component creëert een zij navigatiemenu uit `AppNavItem` items. Items kunnen linken naar interne weergaven of externe bronnen, genest onder bovenliggende items om hiërarchische menu's te vormen, en kunnen iconen, badges of andere componenten bevatten om gebruikers meer context in één oogopslag te geven.

<!-- INTRO_END -->

## Items toevoegen en nestelen {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links zijn of geneste groepskoppen die kinditems bevatten. Groepskoppen zonder links fungeren als uitklapbare containers.

Gebruik `addItem()` om items aan de navigatie toe te voegen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Koppelen van Groep Items
Hoofdniveau-items in een navigatieboom zijn doorgaans bedoeld om uitklapbaar te zijn – niet als klikbare links. Een `path` instellen op dergelijke items kan verwarring veroorzaken bij gebruikers die verwachten dat ze sub-items onthullen in plaats van ergens anders naar te navigeren.

Als je wilt dat de groepskop een aangepaste actie triggert (zoals het openen van externe documentatie), houd dan de groepspad leeg en voeg in plaats daarvan een interactieve controle zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de gebruikerservaring consistent en schoon.
:::

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## Items Koppelen {#linking-items}

Elk `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met behulp van statische paden of geregistreerde weergaveklassen.

### Statische paden {#static-paths}

Gebruik stringpaden om links direct te definiëren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Geregistreerde weergaven {#registered-views}

Als je weergaven geregistreerd zijn bij de [router](../routing/overview), kun je de klasse in plaats van een vaste URL doorgeven:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Als je geannoteerde route [routeparameters](../routing/route-patterns#named-parameters) ondersteunt, kun je ook een `ParametersBag` doorgeven:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Met queryparameters {#with-query-parameters}

Geef een `ParametersBag` door om querystrings op te nemen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Doelgedrag {#target-behavior}

Controleer hoe links openen met `setTarget()`. Dit is vooral handig voor externe links of pop-out weergaven.

- **`SELF`** (standaard): Opent in de huidige weergave.
- **`BLANK`**: Opent in een nieuw tabblad of venster.
- **`PARENT`**: Opent in de bovenliggende browscontext.
- **`TOP`**: Opent in de bovenste browscontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix en suffix {#prefix-and-suffix}

`AppNavItem` ondersteunt prefix- en suffixcomponenten. Gebruik deze om visuele duidelijkheid te bieden met iconen, badges of knoppen.

- **Prefix**: verschijnt vóór het label, nuttig voor iconen.
- **Suffix**: verschijnt na het label, geweldig voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Auto-openen van groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om automatisch geneste groepen uit te vouwen wanneer de app wordt ververst.

```java
nav.setAutoOpen(true);
```

## Sectie labels <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` is een niet-interactieve kop die een serie items benoemt. Een label is van toepassing op elk item dat daarop volgt, tot het volgende label of het einde van het menu, waardoor een lange lijst van hoofdniveau-items als een paar benoemde groepen kan worden gelezen zonder ze te nesten.

Labels worden toegevoegd met `add()` in plaats van `addItem()`, en de volgorde van de aanroepen definieert de secties:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

De navigatie verbergt een label automatisch wanneer de sectie geen zichtbare items heeft, zodat een label verdwijnt wanneer een [zoekopdracht](#search) zijn items eruit filtert of wanneer ze allemaal zijn [vastgepind](#pinning) bovenaan het menu.

### Label prefix en suffix {#label-prefix-and-suffix}

Net als `AppNavItem` ondersteunt een label prefix- en suffixcomponenten. Geef een prefix door aan de constructor of stel er een in als dat nodig is:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Het onderstaande voorbeeld groepeert een menu onder drie labels, waarvan de eerste een [`Icon`](./icon) prefix en een [`Badge`](./badge) suffix heeft. Dashboard staat boven het eerste label, dus het behoort tot geen enkele sectie.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Vasten <DocChip chip='since' label='26.01' /> {#pinning}

Vasten stelt een gebruiker in staat om de items waar ze het meest naar verlangen omhoog te tillen in een groep bovenaan de navigatie, zodat een diep menu nog steeds een korte lijst van favorieten binnen één klik houdt. Het staat standaard uit. Zet het aan via de pinconfiguratie:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Zodra het is ingeschakeld, toont elke navigeerbare leaf item een pin-toggle. De toggle wordt onthuld bij hover en op toetsenbordfocus, zodat deze bereikbaar blijft zonder muis. Het activeren ervan verplaatst het item naar de vastgezette groep bovenaan de navigatie.

Een paar regels bepalen wat kan worden vastgezet en hoe de groep zich gedraagt:

- Alleen navigeerbare leaf items zijn vastzetbaar. Groepskoppen (items met kinderen) zijn nooit vastzetbaar.
- De vastgezette groep verschijnt pas als er iets is vastgezet, en verdwijnt weer wanneer het laatste item is losgemaakt.
- Losmaken brengt een item terug naar zijn exacte oorspronkelijke positie, inclusief items die meerdere niveaus diep in groepen zijn genest.
- Het item wordt verplaatst, niet gekopieerd, zodat alle prefix- of suffixinhoud en alle luisteraars die eraan zijn gekoppeld blijven werken terwijl het zich in de vastgezette groep bevindt.

De demo hieronder heeft vastzetten ingeschakeld met een aangepaste groepsnaam en Dashboard vastgezet bij het laden. Hover of focus een leaf item om de pin-toggle zichtbaar te maken.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Een item vastgezet starten {#starting-an-item-pinned}

Begin een item in de vastgezette groep door de vaste staat in te stellen. Gebruik `isPinned()` om de huidige staat te lezen.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Vasten moet ingeschakeld zijn
`setPinned(true)` heeft alleen effect wanneer vastzetten is ingeschakeld op de `AppNav` via `getPinning().setEnabled(true)`. Zonder dit heeft de aanroep geen effect.
:::

### Titel van de vastgezette groep {#pinned-group-title}

De vastgezette groep is standaard gelabeld als `Pinned`. Verander het om bij je app te passen:

```java
nav.getPinning().setTitle("Favorieten");
```

### Pin-sleutels {#pin-keys}

Elk vastzetbaar item heeft een sleutel die het identificeert voor persistentie en voor de [pin-gebeurtenis](#reacting-to-pin-changes). Wanneer je er geen instelt, valt de sleutel terug op het pad van het item, zodat `getPinKey()` altijd een bruikbare waarde retourneert.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Stel een expliciete sleutel in wanneer het pad tijdens runtime kan veranderen. Een stabiele sleutel zorgt ervoor dat een pin overeenkomt met het juiste item bij het herladen, zelfs als de URL verschuift.

### Autosave naar lokale opslag {#autosave}

Pinnen leeft alleen voor de huidige paginaweergave, tenzij je ze persistent maakt. Autosave is de eenvoudigste optie: het slaat de set van vastgezette items op in de lokale opslag van de browser en herstelt ze bij het herladen. Het is standaard uit. Het heeft een stabiele `id` (of naam) op de component nodig voor de opslag sleutel, en de `AppNav(String id)` constructor is de handige manier om er een in te stellen:

```java
AppNav nav = new AppNav("main-nav"); // geeft autosave een stabiele opslag sleutel
nav.getPinning().setAutosave(true);
```

:::info Autosave heeft een id nodig
Zonder `id` (of naam) op de component, doet autosave stilletjes niets, omdat het geen stabiele sleutel heeft om op te slaan. Persistentie is per browser, dus pins volgen een gebruiker niet naar een ander apparaat of browser.
:::

### Aangepaste persistentie {#custom-persistence}

Voor persistentie die je zelf beheert, bijvoorbeeld per gebruiker op de server, zet autosave uit en stuur het zelf aan via de [pin-gebeurtenis](#reacting-to-pin-changes) en `setPinned`:

```java
nav.getPinning().setAutosave(false);

// bewaar de huidige set van vastgezette sleutels wanneer deze verandert
nav.onPin(event -> savePins(event.getKeys()));

// bij laden, herstel elke opgeslagen sleutel
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reageren op pinwijzigingen {#reacting-to-pin-changes}

De pin-gebeurtenis wordt geactiveerd telkens wanneer een item is vastgezet of losgemaakt. Deze draagt het item dat is gewijzigd, zijn sleutel, de nieuwe vaste staat en de volledige geordende set van vastgezette sleutels:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // het item dat is gewijzigd, of null als het niet langer in de navigatie is
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // elke vastgezette sleutel, in vaste volgorde
});
```

`getItem()` reconstrueert het item door de pin-sleutel te matchen, en retourneert `null` wanneer het item niet langer deel uitmaakt van de navigatie.

### Pin-iconen {#pin-icons}

De toggle gebruikt het ingebouwde `dwc:pin` icoon terwijl een item niet is vastgezet en `dwc:pinned-off` terwijl het is vastgezet. Vervang deze door de jouwe via `setUnpinnedIcon` en `setPinnedIcon`, die elke `IconDefinition` accepteren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Pin-toggle op touchscreens {#pin-toggle-on-touchscreens}

Touchscreens hebben geen hover om de pin te onthullen, dus de toggle is daar standaard verborgen. Houd het zichtbaar en aanraakbaar op touchscreens met `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Zoekopdracht <DocChip chip='since' label='26.01' /> {#search}

Het zoekveld filtert het menu op itemlabel terwijl de gebruiker typt. Het staat standaard uit. Je kunt het tonen en een placeholder geven via de zoekconfiguratie:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Zoeken");
```

Terwijl de gebruiker typt, filtert de navigatie items op label, opent elke groep die een overeenkomst bevat en toont een lege boodschap wanneer er niets overeenkomt. Vaste snelkoppelingen blijven zichtbaar terwijl je zoekt, zodat de favorieten van een gebruiker binnen één klik bereikbaar blijven, zelfs tijdens het filteren.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Lege boodschap {#search-empty-message}

Stel de boodschap in die wordt getoond wanneer een zoekopdracht geen resultaten oplevert. Gewone tekst wordt weergegeven als tekst:

```java
nav.getSearch().setEmptyMessage("Geen items gevonden");
```

### Zoekopdracht aansturen vanuit je eigen veld {#custom-search-box}

Verberg het ingebouwde veld en voed de filter vanuit een invoer van jou. Duw de huidige term erin via `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Om te reageren op wat de gebruiker typt in het ingebouwde veld, luister je naar de zoekgebeurtenis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Stijlen van `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
