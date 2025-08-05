---
title: AppNav
sidebar_position: 6
_i18n_hash: 47432ed72280efdc4d1b48e72d95b87d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

De `AppNav` component in webforJ biedt een flexibele en georganiseerde zij-navigatiemenustructuur met ondersteuning voor zowel platte als hiërarchische structuren. Elke toegang is een `AppNavItem`, dat een eenvoudige link of een groep kan vertegenwoordigen die sub-items bevat. Items kunnen worden gekoppeld aan interne weergaven of externe bronnen, verrijkt met pictogrammen, onderscheidingen of andere componenten.

## Items toevoegen en nesten {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links zijn of geneste groepkoppen die kinditems bevatten. Groepkoppen zonder links fungeren als uitvouwbare containers.

Gebruik `addItem()` om items in de navigatie op te nemen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Groepitems koppelen
Bovenliggende items in een navigatieboom zijn doorgaans bedoeld om uitvouwbaar te zijn—geen klikbare links. Het instellen van een `path` op dergelijke items kan gebruikers verwarren die verwachten dat ze sub-items onthullen in plaats van elders naartoe te navigeren.

Als je wilt dat de groepkop een aangepaste actie activeert (zoals het openen van externe documentatie), houd dan het pad van de groep leeg en voeg in plaats daarvan een interactieve controle zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de gebruikerservaring consistent en schoon.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Items koppelen {#linking-items}

Elk `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met behulp van statische paden of geregistreerde weergave Klassen.

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

Controleer hoe links openen met `setTarget()`. Dit is vooral handig voor externe links of pop-out weergaven.

- **`SELF`** (standaard): Opent in de huidige weergave.
- **`BLANK`**: Opent in een nieuw tabblad of venster.
- **`PARENT`**: Opent in de bovenliggende browsecontext.
- **`TOP`**: Opent in de bovenliggende browsecontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix en suffix {#prefix-and-suffix}

`AppNavItem` ondersteunt prefix- en suffixcomponenten. Gebruik deze om visuele duidelijkheid te bieden met pictogrammen, onderscheidingen of knoppen.

- **Prefix**: verschijnt voor het label, nuttig voor pictogrammen.
- **Suffix**: verschijnt na het label, geweldig voor onderscheidingen of acties.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisch uitvouwen van groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om geneste groepen automatisch uit te vouwen wanneer de app wordt vernieuwd.

```java
nav.setAutoOpen(true);
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
