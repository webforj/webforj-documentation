---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 4f5eaa3e9738a7f959de22544589d240
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

De `AppNav` component creëert een zij navigatiemenu uit `AppNavItem` items. Items kunnen linken naar interne weergaven of externe bronnen, genest onder bovenliggende items om hiërarchische menu's te vormen, en kunnen iconen, badges of andere componenten bevatten om gebruikers meer context in één oogopslag te geven.

<!-- INTRO_END -->

## Items toevoegen en nesten {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links of geneste groepskoppen zijn die kinditems bevatten. Groepskoppen zonder links fungeren als uitvouwbare containers.

Gebruik `addItem()` om items aan de navigatie toe te voegen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Beheer");
admin.addItem(new AppNavItem("Gebruikers", "/admin/users"));
admin.addItem(new AppNavItem("Instellingen", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Groepselementen linken
Bovenliggende items in een navigatieboom zijn doorgaans bedoeld om uitvouwbaar te zijn - niet als klikbare links. Een `path` instellen op zulke items kan verwarrend zijn voor gebruikers die verwachten dat ze sub-items onthullen in plaats van ergens anders naartoe te navigeren.

Als je wilt dat de groepskop een aangepaste actie activeert (zoals het openen van externe documentatie), houd dan het groepspad leeg en voeg in plaats daarvan een interactief element zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de UX consistent en overzichtelijk.
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

Elk `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met statische paden of geregistreerde weergaveklassen.

### Statische paden {#static-paths}

Gebruik stringpaden om links direct te definiëren:

```java
AppNavItem docs = new AppNavItem("Documenten", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Geregistreerde weergaven {#registered-views}

Als je weergaven geregistreerd zijn bij de [router](../routing/overview), kun je de klasse doorgeven in plaats van een hardcoded URL:

```java
AppNavItem settings = new AppNavItem("Instellingen", SettingsView.class);
```

Als je geannoteerde route [routeparameters](../routing/route-patterns#named-parameters) ondersteunt, kun je ook een `ParametersBag` doorgeven:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Gebruiker", UserView.class, params);
```

### Met queryparameters {#with-query-parameters}

Geef een `ParametersBag` door om querystrings op te nemen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Geavanceerd", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Doelgedrag {#target-behavior}

Beheer hoe links openen met `setTarget()`. Dit is vooral nuttig voor externe links of pop-out weergaven.

- **`SELF`** (standaard): opent in de huidige weergave.
- **`BLANK`**: opent in een nieuw tabblad of venster.
- **`PARENT`**: opent in de bovenliggende browsecontext.
- **`TOP`**: opent in de bovenste browsecontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix en suffix {#prefix-and-suffix}

`AppNavItem` ondersteunt prefix- en suffixcomponenten. Gebruik deze om visuele duidelijkheid te bieden met iconen, badges of knoppen.

- **Prefix**: verschijnt voor het label, nuttig voor iconen.
- **Suffix**: verschijnt na het label, geweldig voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Alarmen");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisch openen van groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om automatisch geneste groepen uit te breiden wanneer de app wordt ververst.

```java
nav.setAutoOpen(true);
```

## Sectietitels <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` is een niet-interactieve kop die een reeks items tituleert. Een label is van toepassing op elk item dat erop volgt, tot de volgende label of het einde van het menu, zodat een lange lijst van bovenliggende items als een paar benoemde groepen kan worden gelezen zonder ze te nesten.

Labels worden toegevoegd met `add()` in plaats van `addItem()`, en de volgorde van de aanroepen definieert de secties:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analyses"));
nav.addItem(new AppNavItem("Overzicht", OverviewView.class));
nav.addItem(new AppNavItem("Rapporten", ReportsView.class));

nav.add(new AppNavLabel("Overig"));
nav.addItem(new AppNavItem("Instellingen", SettingsView.class));
```

De navigatie verbergt een label automatisch wanneer de sectie geen zichtbare items heeft, zodat een label verdwijnt wanneer een [zoekopdracht](#search) zijn items filtert of wanneer al deze [vastgepind](#pinning) zijn aan de bovenkant van het menu.

### Label prefix en suffix {#label-prefix-and-suffix}

Net als `AppNavItem` ondersteunt een label prefix- en suffixcomponenten. Geef een prefix door aan de constructor, of stel een van beide daarna in:

```java
AppNavLabel analytics = new AppNavLabel("Analyses", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Het onderstaande voorbeeld groepeert een menu onder drie labels, waarvan de eerste een [`Icon`](./icon) prefix en een [`Badge`](./badge) suffix draagt. Dashboard zit boven het eerste label, dus het behoort tot geen sectie.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Vastpinnen <DocChip chip='since' label='26.01' /> {#pinning}

Vastpinnen stelt een gebruiker in staat om de items die ze het meest nodig hebben naar een groep bovenaan de navigatie te tillen, zodat een diep menu nog steeds een korte lijst van favorieten binnen één klik houdt. Het is standaard uitgeschakeld. Zet het aan via de pinconfiguratie:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Eenmaal ingeschakeld, toont elk navigeerbaar bladitem een pin-schakelaar. De schakelaar wordt onthuld bij hover en bij toetsenbordfocus, zodat deze bereikbaar blijft zonder een muis. Activatie verplaatst het item naar de vaste groep bovenaan de navigatie.

Een paar regels bepalen wat kan worden vastgepind en hoe de groep zich gedraagt:

- Alleen navigeerbare bladitems zijn vast te pinnen. Groepskoppen (items met kinderen) zijn nooit vast te pinnen.
- De vaste groep verschijnt alleen wanneer iets is vastgepind, en verdwijnt weer wanneer het laatste item is losgehaald.
- Het losmaken retourneert een item naar zijn exacte oorspronkelijke positie, inclusief items die meerdere niveaus diep binnen groepen zijn genest.
- Het item wordt verplaatst, niet gekopieerd, zodat elke prefix- of suffixinhoud en eventuele luisteraars die eraan zijn gekoppeld blijven werken terwijl het in de vaste groep zit.

De onderstaande demo heeft vastpinnen ingeschakeld met een aangepaste groottitel en Dashboard vastgepind bij het laden. Hover of focus een bladitem om de pin-schakelaar te onthullen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Een item vast gepind starten {#starting-an-item-pinned}

Start een item in de vaste groep door de vaste status in te stellen. Gebruik `isPinned()` om de huidige status te lezen.

```java
AppNavItem reports = new AppNavItem("Rapporten", "/reports");
reports.setPinned(true);
```

:::info Vastpinnen moet ingeschakeld zijn
`setPinned(true)` heeft alleen effect wanneer vastpinnen is ingeschakeld op de `AppNav` via `getPinning().setEnabled(true)`. Zonder dit heeft de aanroep geen effect.
:::

### Titel van de vaste groep {#pinned-group-title}

De vaste groep is standaard gelabeld met `Vast`. Verander het om bij je app te passen:

```java
nav.getPinning().setTitle("Favorieten");
```

### Pin-sleutels {#pin-keys}

Elk vast te pinnen item heeft een sleutel die het identificeert voor persistente opslag en voor het [pin-evenement](#reacting-to-pin-changes). Wanneer je er geen instelt, valt de sleutel terug op het pad van het item, zodat `getPinKey()` altijd een bruikbare waarde retourneert.

```java
AppNavItem reports = new AppNavItem("Rapporten", "/reports");
reports.setPinKey("reports");
```

Stel een expliciete sleutel in wanneer het pad tijdens runtime kan veranderen. Een stabiele sleutel houdt een pin gekoppeld aan het juiste item tussen herlaadbeurten, zelfs als de URL verandert.

### Auto-opslag naar lokale opslag {#autosave}

Pinnen leeft alleen voor de huidige paginaweergave, tenzij je ze persistent maakt. Auto-opslag is de eenvoudigste optie: het slaat de set van vastgepinde items op in de lokale opslag van de browser en stelt ze weer beschikbaar bij herladen. Het is standaard uitgeschakeld. Het heeft een stabiele `id` (of naam) op de component nodig voor de opslag sleutel, en de `AppNav(String id)` constructor is de handige manier om er een in te stellen:

```java
AppNav nav = new AppNav("main-nav"); // geeft auto-opslag een stabiele opslag sleutel
nav.getPinning().setAutosave(true);
```

:::info Auto-opslag heeft een id nodig
Zonder een `id` (of naam) op de component doet auto-opslag stilletjes niets, aangezien het geen stabiele sleutel heeft om onder op te slaan. Persistentie is per browser, dus pins volgen een gebruiker niet naar een ander apparaat of browser.
:::

### Aangepaste persistentie {#custom-persistence}

Voor persistentie die je zelf beheert, bijvoorbeeld per gebruiker op de server, zet de auto-opslag uit en bestuur het zelf via het [pin-evenement](#reacting-to-pin-changes) en `setPinned`:

```java
nav.getPinning().setAutosave(false);

// bewaar de huidige set vastgepinde sleutels telkens wanneer deze verandert
nav.onPin(event -> savePins(event.getKeys()));

// bij laden, herstel elke opgeslagen sleutel
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reageren op pin-wijzigingen {#reacting-to-pin-changes}

Het pin-evenement wordt geactiveerd telkens wanneer een item wordt vastgepind of losgemaakt. Het bevat het item dat is veranderd, de sleutel, de nieuwe vaste status en de volledige geordende set van vaste sleutels:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // het item dat is veranderd, of null als het niet langer in de navigatie zit
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // elke vaste sleutel, in volgorde
});
```

`getItem()` herkent het item door zijn pin-sleutel en retourneert `null` als het item niet langer deel uitmaakt van de navigatie.

### Pin-iconen {#pin-icons}

De schakelaar gebruikt het ingebouwde `dwc:pin` icoon terwijl een item niet is vastgepind en `dwc:pinned-off` terwijl het is vastgepind. Vervang deze door de jouwe via `setUnpinnedIcon` en `setPinnedIcon`, die elke `IconDefinition` accepteren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Pin-schakelaar op touchscreens {#pin-toggle-on-touchscreens}

Touchscreens hebben geen hover om de pin te onthullen, dus de schakelaar is daar standaard verborgen. Houd deze zichtbaar en aanraakbaar op touchscreens met `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Zoekfunctie <DocChip chip='since' label='26.01' /> {#search}

Het zoekveld filtert het menu op itemlabel terwijl de gebruiker typt. Het is standaard uitgeschakeld. Je kunt het tonen en een placeholder geven via de zoekconfiguratie:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Zoeken");
```

Terwijl de gebruiker typt, filtert de navigatie items op label, opent elke groep die een overeenkomst bevat, en toont een lege boodschap wanneer er niets overeenkomt. Vastgepinde snelkoppelingen blijven zichtbaar tijdens het zoeken, zodat de favorieten van een gebruiker binnen één klik blijven, zelfs tijdens het filteren.

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

### Zoekfunctie aansteken vanuit je eigen veld {#custom-search-box}

Verberg het ingebouwde veld en voed de filter vanaf een invoer van jezelf. Duw de huidige term erin via `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Om te reageren op wat de gebruiker typt in het ingebouwde veld, luister naar het zoekevenement:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
