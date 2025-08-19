---
title: AppNav
sidebar_position: 6
_i18n_hash: 1e9ac3fc8372d76faee53a4b9ee2cf88
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

De `AppNav` component in webforJ biedt een flexibele en georganiseerde zij-navigatiemenu met ondersteuning voor zowel vlakke als hiërarchische structuren. Elke invoer is een `AppNavItem`, die een eenvoudige link of een groep met sub-items kan vertegenwoordigen. Items kunnen worden gekoppeld aan interne weergaven of externe bronnen, versterkt met pictogrammen, badges of andere componenten.

## Items toevoegen en nesten {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links of geneste groepsheaders zijn die child items bevatten. Groepsheaders zonder links fungeren als uitbreidbare containers.

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

:::tip Groep Items Koppelen
Hoofditems in een navigatieboom zijn meestal bedoeld om uitbreidbaar te zijn—niet om klikbare links te zijn. Het instellen van een `path` op dergelijke items kan gebruikers in de war brengen die verwachten dat ze sub-items onthullen in plaats van ergens anders naartoe te navigeren.

Als je wilt dat de groepsheader een aangepaste actie trigger (zoals het openen van externe documentatie), houd het groepspad dan leeg en voeg in plaats daarvan een interactieve controle zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de gebruikerservaring consistent en overzichtelijk.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Items Koppelen {#linking-items}

Elke `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met statische paden of geregistreerde weergaveklassen.

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

Beheer hoe links openen met `setTarget()`. Dit is vooral nuttig voor externe links of pop-out weergaven.

- **`SELF`** (standaard): Opent in de huidige weergave.
- **`BLANK`**: Opent in een nieuw tabblad of venster.
- **`PARENT`**: Opent in de bovenliggende browse-context.
- **`TOP`**: Opent in de top-niveau browse-context.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

`AppNavItem` ondersteunt voorvoegsel- en achtervoegselcomponenten. Gebruik deze om visuele duidelijkheid te bieden met pictogrammen, badges of knoppen.

- **Voorvoegsel**: verschijnt voor het label, handig voor pictogrammen.
- **Achtervoegsel**: verschijnt na het label, geweldig voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Auto-openen groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om geneste groepen automatisch uit te vouwen wanneer de app wordt vernieuwd.

```java
nav.setAutoOpen(true);
```

## Stylen `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
