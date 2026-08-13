---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 7283cd36346dd18b131a5393db8e8fd3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

De `AppNav` component creëert een zijmenu uit `AppNavItem` items. Items kunnen linken naar interne weergaven of externe bronnen, genest zijn onder bovenliggende items om hiërarchische menu's te vormen, en kunnen iconen, badges of andere componenten bevatten om gebruikers meer context in één oogopslag te geven.

<!-- INTRO_END -->

## Item toevoegen en nestelen {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links zijn of geneste groep koppen die kinditems bevatten. Groepkoppen zonder links fungeren als uitbreidbare containers.

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

:::tip Groep Items linken
Hoofditems in een navigatieboom zijn doorgaans bedoeld om uitbreidbaar te zijn — niet als klikbare links. Het instellen van een `path` op dergelijke items kan gebruikers verwarren die verwachten dat ze sub-items onthullen in plaats van elders te navigeren.

Als je wilt dat de groepkop een aangepaste actie triggert (zoals het openen van externe documentatie), houd het groepspad leeg en voeg in plaats daarvan een interactieve controle zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de UX consistent en schoon.
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

Elke `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met behulp van statische paden of geregistreerde weergaveklassen.

### Statische paden {#static-paths}

Gebruik stringpaden om links direct te definiëren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Geregistreerde weergaven {#registered-views}

Als je weergaven zijn geregistreerd bij de [router](../routing/overview), kun je de klasse doorgeven in plaats van een hardcoded URL:

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

- **`SELF`** (standaard): opent in de huidige weergave.
- **`BLANK`**: opent in een nieuw tabblad of venster.
- **`PARENT`**: opent in de bovenliggende browscontext.
- **`TOP`**: opent in de bovenste browscontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix en suffix {#prefix-and-suffix}

`AppNavItem` ondersteunt prefix- en suffixcomponenten. Gebruik deze om visuele duidelijkheid te bieden met iconen, badges of knoppen.

- **Prefix**: verschijnt voor het label, nuttig voor iconen.
- **Suffix**: verschijnt na het label, ideaal voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisch openen groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om geneste groepen automatisch uit te breiden wanneer de app opnieuw wordt geladen.

```java
nav.setAutoOpen(true);
```

## Vastpinnen <DocChip chip='since' label='26.01' /> {#pinning}

Vastpinnen stelt een gebruiker in staat om de items waar ze het meest naar grijpen in een groep bovenaan de navigatie te brengen, zodat een diep menu nog steeds een korte lijst met favorieten binnen één klik houdt. Het is standaard uitgeschakeld. Zet het aan via de pinning-configuratie:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Eenmaal ingeschakeld toont elk navigeerbaar bladeren item een pin-schakelaar. De schakelaar wordt onthuld bij hover en bij toetsenbordfocus, zodat deze bereikbaar blijft zonder een muis. Activeren verplaatst het item naar de gepinde groep bovenaan de navigatie.

Een paar regels bepalen wat kan worden vastgepind en hoe de groep zich gedraagt:

- Alleen navigeerbare bladerenitems zijn pinnable. Groepkoppen (items met kinderen) zijn nooit pinnable.
- De gepinde groep verschijnt alleen zodra iets is vastgepind, en verdwijnt weer wanneer het laatste item is losgekoppeld.
- Loskoppelen retourneert een item naar zijn exacte oorspronkelijke positie, inclusief items die meerdere niveaus diep in groepen genest zijn.
- Het item wordt verplaatst, niet gekopieerd, zodat elke prefix- of suffixinhoud en luisteraars die eraan zijn gekoppeld blijven werken terwijl het in de gepinde groep zit.

De demo hieronder heeft pinning ingeschakeld met een aangepaste groepsnaam en Dashboard gepind bij het laden. Hover of focus een bladeren item om de pin-schakelaar te onthullen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Een item gestart met pinning {#starting-an-item-pinned}

Start een item in de gepinde groep door de pinned-status in te stellen. Gebruik `isPinned()` om de huidige status te lezen.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Vastpinnen moet zijn ingeschakeld
`setPinned(true)` heeft alleen effect wanneer pinning is ingeschakeld op de `AppNav` via `getPinning().setEnabled(true)`. Zonder dit heeft de aanroep geen effect.
:::

### Titel van de gepinde groep {#pinned-group-title}

De gepinde groep is standaard gelabeld als `Pinned`. Wijzig dit om bij je app te passen:

```java
nav.getPinning().setTitle("Favorieten");
```

### Pin-sleutels {#pin-keys}

Elk pinnable item heeft een sleutel die het identificeert voor persistentie en voor de [pin-gebeurtenis](#reacting-to-pin-changes). Wanneer je er geen instelt, valt de sleutel terug op het pad van het item, zodat `getPinKey()` altijd een bruikbare waarde retourneert.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Stel een expliciete sleutel in wanneer het pad op runtime kan veranderen. Een stabiele sleutel houdt een pin gekoppeld aan het juiste item tijdens het vernieuwen, zelfs als de URL verschuift.

### Autosave naar lokale opslag {#autosave}

Pins leven alleen voor de huidige paginaweergave tenzij je ze persistent maakt. Autosave is de eenvoudigste optie: het slaat de set van gepinde items op in de lokale opslag van de browser en herstelt ze bij het opnieuw laden. Het is standaard uitgeschakeld. Het heeft een stabiele `id` (of naam) op de component nodig voor de opslag sleutel, en de `AppNav(String id)` constructor is de handige manier om er een in te stellen:

```java
AppNav nav = new AppNav("main-nav"); // geeft autosave een stabiele opslag sleutel
nav.getPinning().setAutosave(true);
```

:::info Autosave heeft een id nodig
Zonder een `id` (of naam) op de component, doet autosave stilletjes niets, omdat het geen stabiele sleutel heeft om onder op te slaan. Persistentie is per browser, dus pins volgen een gebruiker niet naar een ander apparaat of browser.
:::

### Aangepaste persistentie {#custom-persistence}

Voor persistentie die je zelf beheert, bijvoorbeeld per gebruiker op de server, zet autosave uit en stuur het zelf aan via de [pin-gebeurtenis](#reacting-to-pin-changes) en `setPinned`:

```java
nav.getPinning().setAutosave(false);

// blijft de huidige set van gepinde sleutels steeds bijwerken wanneer deze verandert
nav.onPin(event -> savePins(event.getKeys()));

// bij het laden elke opgeslagen sleutel herstellen
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reageren op pin-wijzigingen {#reacting-to-pin-changes}

De pin-gebeurtenis wordt geactiveerd telkens wanneer een item wordt gepind of losgekoppeld. Het draagt het item dat is veranderd, zijn sleutel, de nieuwe pinned-status en de volledige geordineerde set gepinde sleutels:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // het item dat is veranderd, of null als het niet langer deel uitmaakt van de navigatie
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // elke gepinde sleutel, in gepinde volgorde
});
```

`getItem()` lost het item op door zijn pin-sleutel te matchen en retourneert `null` wanneer het item niet langer deel uitmaakt van de navigatie.

### Pin-iconen {#pin-icons}

De schakelaar gebruikt het ingebouwde `dwc:pin` icoon terwijl een item niet is gepind en `dwc:pinned-off` terwijl het is gepind. Vervang deze door je eigen via `setUnpinnedIcon` en `setPinnedIcon`, die elke `IconDefinition` accepteren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Pin-schakelaar op touchscreen {#pin-toggle-on-touchscreens}

Touchscreens hebben geen hover om de pin te onthullen, dus de schakelaar is daar standaard verborgen. Houd het zichtbaar en aanraakbaar op touchscreens met `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Zoekfunctie <DocChip chip='since' label='26.01' /> {#search}

Het zoekveld filtert het menu op itemlabel terwijl de gebruiker typt. Het is standaard uitgeschakeld. Je kunt het tonen en een placeholder geven via de zoekconfiguratie:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Zoeken");
```

Terwijl de gebruiker typt, filtert de navigatie items op label, opent het elke groep die een overeenkomst bevat, en toont een lege boodschap wanneer er niets overeenkomt. Gepinde snelkoppelingen blijven zichtbaar tijdens het zoeken, zodat de favorieten van een gebruiker binnen één klik bereikbaar blijven, zelfs mid-filter.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Lege boodschap {#search-empty-message}

Stel de boodschap in die wordt weergegeven wanneer een zoekopdracht geen resultaten oplevert. Eenvoudige tekst wordt als tekst weergegeven:

```java
nav.getSearch().setEmptyMessage("Geen items gevonden");
```

### De zoekfunctie aansturen vanuit je eigen veld {#custom-search-box}

Verberg het ingebouwde veld en voed de filter vanaf een invoer die je zelf maakt. Duw de huidige term erin via `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Om te reageren op wat de gebruiker typt in het ingebouwde veld, luister naar de zoekgebeurtenis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
