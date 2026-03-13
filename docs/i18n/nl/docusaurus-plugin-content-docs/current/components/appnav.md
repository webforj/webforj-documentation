---
title: AppNav
sidebar_position: 6
_i18n_hash: d4756db6bed23bc005fbcd2be222b4ea
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

De `AppNav` component creëert een zij navigatiemenu uit `AppNavItem` ingangen. Items kunnen linken naar interne weergaven of externe bronnen, onder ouders items genest worden om hiërarchische menu's te vormen, en iconen, badges of andere componenten dragen om gebruikers sneller context te geven.

<!-- INTRO_END -->

## Items toevoegen en nestelen {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links zijn of geneste groepshoofden die kinditems bevatten. Groepshoofden zonder links fungeren als uitvouwbare containers.

Gebruik `addItem()` om items aan de navigatie toe te voegen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Gebruikers", "/admin/users"));
admin.addItem(new AppNavItem("Instellingen", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Groep Items Koppelen
Hoogwaardige items in een navigatiestructuur zijn doorgaans bedoeld om uitvouwbaar te zijn—en niet als klikbare links. Een `path` instellen op dergelijke items kan verwarring bij gebruikers veroorzaken die verwachten dat ze sub-items onthullen in plaats van ergens anders naartoe te navigeren.

Als je wilt dat de groepsheader een aangepaste actie trigger (zoals het openen van externe documentatie), houd het groepspad leeg en voeg in plaats daarvan een interactieve controle zoals een [`IconButton`](./icon#icon-buttons) toe aan de suffix van het item. Dit houdt de gebruikerservaring consistent en overzichtelijk.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Items Koppelen {#linking-items}

Elke `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met behulp van statische paden of geregistreerde weergaveklassen.

### Statische paden {#static-paths}

Gebruik stringpaden om links direct te definiëren:

```java
AppNavItem docs = new AppNavItem("Documentatie", "/docs");
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
AppNavItem advanced = new AppNavItem("Gevorderd", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Doelgedrag {#target-behavior}

Beheer hoe links openen met `setTarget()`. Dit is vooral nuttig voor externe links of pop-out weergaven.

- **`SELF`** (standaard): Open in de huidige weergave.
- **`BLANK`**: Open in een nieuw tabblad of venster.
- **`PARENT`**: Open in de bovenliggende browse-context.
- **`TOP`**: Open in de bovenste browse-context.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix en suffix {#prefix-and-suffix}

`AppNavItem` ondersteunt prefix- en suffixcomponenten. Gebruik deze om visuele duidelijkheid te bieden met iconen, badges of knoppen.

- **Prefix**: verschijnt voor het label, nuttig voor iconen.
- **Suffix**: verschijnt na het label, geweldig voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisch openen groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om automatisch geneste groepen uit te vouwen wanneer de app wordt vernieuwd.

```java
nav.setAutoOpen(true);
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
