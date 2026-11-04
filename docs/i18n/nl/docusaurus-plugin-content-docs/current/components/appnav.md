---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: c4ad786639b189058a38c808bd99ec8b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

De `AppNav` component creëert een zij-navigatiemenu uit `AppNavItem` items. Elementen kunnen linken naar interne weergaven of externe bronnen, onder ouderitems genest zijn om hiërarchische menu's te vormen, en iconen, badges, of andere componenten bevatten om gebruikers meer context te geven in één oogopslag.

<!-- INTRO_END -->

## Items toevoegen en nesten <DocChip chip="name" label="dwc-app-nav-item" /> {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links zijn of geneste groepskoppen die child items bevatten. Groepskoppen zonder links fungeren als uitvouwbare containers.

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

:::tip Groepitems linken
Top-level items in een navigatieboom zijn meestal bedoeld om uitbreidbaar te zijn, niet om klikbare links te zijn. Een `path` instellen op dergelijke items kan gebruikers in de war brengen die verwachten dat ze sub-items onthullen in plaats van ergens anders naartoe te navigeren.

Als je wilt dat de groepskop een aangepaste actie activeert (zoals het openen van externe documentatie), houd het groepspad dan leeg en voeg in plaats daarvan een interactief controle-element zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de UX consistent en overzichtelijk.
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

## Items linken {#linking-items}

Elk `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met statische paden of geregistreerde view-classes.

### Statische paden {#static-paths}

Gebruik stringpaden om links rechtstreeks te definiëren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Geregistreerde weergaven {#registered-views}

Als je weergaven zijn geregistreerd bij de [router](../routing/overview), kun je de klasse doorgeven in plaats van een hardcoded URL:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Als je geannoteerde route ondersteuning biedt voor [routeparameters](../routing/route-patterns#named-parameters), kun je ook een `ParametersBag` doorgeven:

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

Beheer hoe links openen met `setTarget()`. Dit is vooral nuttig voor externe links of pop-out weergaven.

- **`SELF`** (standaard): opent in de huidige weergave.
- **`BLANK`**: opent in een nieuw tabblad of venster.
- **`PARENT`**: opent in de bovenliggende browse-context.
- **`TOP`**: opent in de bovenste browse-context.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Voorvoegsels en achtervoegsels {#prefix-and-suffix}

`AppNavItem` ondersteunt voorvoegsel- en achtervoegselcomponenten. Gebruik deze om visuele helderheid te bieden met iconen, badges of knoppen.

- **Voorvoegsel**: verschijnt voor het label, nuttig voor iconen.
- **Achtervoegsel**: verschijnt na het label, geweldig voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Auto-opeenstapeling groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om automatisch geneste groepen uit te vouwen wanneer de app wordt vernieuwd.

```java
nav.setAutoOpen(true);
```

## Sectie_labels <DocChip chip='since' label='26.02' /> <DocChip chip="name" label="dwc-app-nav-label" /> {#section-labels}

`AppNavLabel` is een niet-interactieve kop die een reeks items titelt. Een label geldt voor elk volgend item tot het volgende label of het einde van het menu, waardoor een lange lijst van top-level items als een paar benoemde groepen kan worden gelezen zonder ze te nesten.

Labels worden toegevoegd met `add()` in plaats van `addItem()`, en de volgorde van de oproepen definieert de secties:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

De nav verbergt automatisch een label wanneer de sectie geen zichtbare items heeft, zodat een label verdwijnt wanneer een [zoekopdracht](#search) de items eruit filtert of wanneer ze allemaal zijn [vastgezet](#pinning) aan de bovenkant van het menu.

### Label voorvoegsel en achtervoegsel {#label-prefix-and-suffix}

Net als `AppNavItem`, ondersteunt een label prefix- en suffixcomponenten. Geef een voorvoegsel door aan de constructor, of stel een van beide daarna in:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Het onderstaande voorbeeld groepeert een menu onder drie labels, waarvan de eerste een [`Icon`](./icon) voorvoegsel en een [`Badge`](./badge) achtervoegsel heeft. Dashboard bevindt zich boven het eerste label, dus het behoort tot geen sectie.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Vastzetten <DocChip chip='since' label='26.01' /> {#pinning}

Vastzetten stelt een gebruiker in staat om de items die ze het meest bereiken in een groep aan de bovenkant van de navigatie te tillen, zodat een diep menu nog steeds een korte lijst van favorieten binnen één klik houdt. Het is standaard uitgeschakeld. Zet het aan via de vastzetconfiguratie:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Zodra het is ingeschakeld, toont elk navigeerbaar bladitem een vastzettoggle. De toggle wordt onthuld bij hover en bij toetsenbordfocus, zodat het bereikbaar blijft zonder muis. Het activeren ervan verplaatst het item naar de vaste groep bovenaan de navigatie.

Een paar regels bepalen wat kan worden vastgezet en hoe de groep zich gedraagt:

- Alleen navigeerbare bladitems zijn vastzetbaar. Groepskoppen (items met kinderen) zijn nooit vastzetbaar.
- De vaste groep verschijnt alleen zodra er iets is vastgezet, en verdwijnt weer wanneer het laatste item is losgemaakt.
- Losmaken retourneert een item naar zijn exacte oorspronkelijke positie, inclusief items die genest zijn op verschillende niveaus binnen groepen.
- Het item wordt verplaatst, niet gekopieerd, waardoor alle prefix- of suffixinhoud en alle aan dit item gekoppelde luisteraars blijven werken terwijl het zich in de vaste groep bevindt.

De demo hieronder heeft vastzetten ingeschakeld met een aangepaste groeps titel en Dashboard vastgezet bij het laden. Hover of focus een bladitem om de vastzettoggle te onthullen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Een item vastzetten bij de start {#starting-an-item-pinned}

Begin een item in de vaste groep door de vaste status in te stellen. Gebruik `isPinned()` om de huidige status te lezen.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Vastzetten moet zijn ingeschakeld
`setPinned(true)` heeft alleen effect wanneer vastzetten is ingeschakeld op de `AppNav` via `getPinning().setEnabled(true)`. Zonder dat heeft de oproep geen effect.
:::

### Titel van de vaste groep {#pinned-group-title}

De vaste groep is standaard gelabeld als `Vastgezet`. Wijzig het om bij je app te passen:

```java
nav.getPinning().setTitle("Favorieten");
```

### Vastzet sleutels {#pin-keys}

Elke vastzetbare item heeft een sleutel die het identificeert voor persistentie en voor de [vastzet gebeurtenis](#reacting-to-pin-changes). Wanneer je er geen instelt, valt de sleutel terug op het pad van het item, zodat `getPinKey()` altijd een bruikbare waarde retourneert.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Stel een expliciete sleutel in wanneer het pad tijdens runtime kan veranderen. Een stabiele sleutel houdt een pin gekoppeld aan het juiste item tijdens herlaadbeurten, zelfs als de URL verschuift.

### Autosave naar lokale opslag {#autosave}

Pins leven alleen voor de huidige paginaweergave tenzij je ze persistent maakt. Autosave is de eenvoudigste optie: het slaat de set van vastgezette items op in de lokale opslag van de browser en herstelt ze bij het herladen. Het is standaard uitgeschakeld. Het heeft een stabiele `id` (of naam) op de component nodig voor de opslag sleutel, en de `AppNav(String id)` constructor is de handige manier om er een in te stellen:

```java
AppNav nav = new AppNav("main-nav"); // geeft autosave een stabiele opslag sleutel
nav.getPinning().setAutosave(true);
```

:::info Autosave heeft een id nodig
Zonder `id` (of naam) op de component, doet autosave stilletjes niets, aangezien het geen stabiele sleutel heeft om onder op te slaan. Persistentie is per browser, dus pins volgen een gebruiker niet naar een ander apparaat of browser.
:::

### Aangepaste persistentie {#custom-persistence}

Voor persistentie die je zelf beheert, bijvoorbeeld per gebruiker op de server, zet je autosave uit en stuur je het zelf aan via de [vastzet gebeurtenis](#reacting-to-pin-changes) en `setPinned`:

```java
nav.getPinning().setAutosave(false);

// bewaar de huidige set van vastgezette sleutels telkens wanneer deze verandert
nav.onPin(event -> savePins(event.getKeys()));

// herstel op lading elke opgeslagen sleutel
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reageren op vastzetwijzigingen {#reacting-to-pin-changes}

De vastzetgebeurtenis wordt geactiveerd wanneer een item wordt vastgezet of losgemaakt. Het draagt het item dat is gewijzigd, zijn sleutel, de nieuwe vaste status, en de volledige geordende set van vastgezette sleutels:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // het item dat is gewijzigd, of null als het niet meer in de navigatie zit
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // elke vaste sleutel, in vaste volgorde
});
```

`getItem()` lost het item op door de vastzet sleutel te matchen, en retourneert `null` wanneer het item niet langer deel uitmaakt van de navigatie.

### Vastzeticonen {#pin-icons}

De toggle gebruikt het ingebouwde `dwc:pin` icoon zolang een item niet is vastgezet en `dwc:pinned-off` terwijl het is vastgezet. Vervang deze door je eigen via `setUnpinnedIcon` en `setPinnedIcon`, die elke `IconDefinition` accepteren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Vastzettoggle op touchscreens {#pin-toggle-on-touchscreens}

Touchscreens hebben geen hover om de pin te onthullen, dus de toggle is daar standaard verborgen. Houd het zichtbaar en aanraakbaar op touchscreens met `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Zoekopdracht <DocChip chip='since' label='26.01' /> {#search}

Het zoekveld filtert het menu op basis van het itemlabel terwijl de gebruiker typt. Het is standaard uitgeschakeld. Je kunt het tonen en een placeholder geven via de zoekconfiguratie:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Zoeken");
```

Naarmate de gebruiker typt, filtert de nav items op label, opent elk groep dat een overeenkomst bevat, en toont een lege boodschap wanneer er niets overeenkomt. Vastgezette snelkoppelingen blijven zichtbaar tijdens het zoeken, zodat de favorieten van een gebruiker binnen één klik bereikbaar blijven, zelfs midden in de filter.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Lege boodschap {#search-empty-message}

Stel de boodschap in die wordt weergegeven wanneer een zoekopdracht geen resultaten oplevert. Gewone tekst wordt weergegeven als tekst:

```java
nav.getSearch().setEmptyMessage("Geen items gevonden");
```

### Zoekopdracht aansturen vanuit je eigen veld {#custom-search-box}

Verberg het ingebouwde veld en stuur de filter vanuit een invoer van jouw keuze. Duw de huidige term erin via `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Om te reageren op wat de gebruiker typt in het ingebouwde veld, luister je naar de zoekgebeurtenis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling van `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
