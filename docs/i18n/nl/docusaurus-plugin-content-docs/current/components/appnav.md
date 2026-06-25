---
title: AppNav
sidebar_position: 6
_i18n_hash: 859da44bd50a1b3e985139da624ed4d4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

De `AppNav` component creëert een zijmenu uit `AppNavItem` items. Items kunnen linken naar interne weergaven of externe bronnen, genest zijn onder bovenliggende items om hiërarchische menu's te vormen, en kunnen iconen, badges of andere componenten bevatten om gebruikers meer context in één oogopslag te geven.

<!-- INTRO_END -->

## Items toevoegen en nestelen {#adding-and-nesting-items}

`AppNavItem` instanties worden gebruikt om de `AppNav` structuur te vullen. Deze items kunnen eenvoudige links zijn of geneste groepskoppen die kind-items bevatten. Groepskoppen zonder links fungeren als uitvouwbare containers.

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

:::tip Groepsitems linken
Topniveau-items in een navigatiestructuur zijn doorgaans bedoeld om uitvouwbaar te zijn — niet om op te klikken. Een `path` instellen op dergelijke items kan gebruikers in verwarring brengen die verwachten dat deze sub-items onthullen in plaats van ergens anders naartoe te navigeren.

Als je wilt dat de groepskop een aangepaste actie triggert (zoals het openen van externe documentatie), houd dan het groepspad leeg en voeg in plaats daarvan een interactief element toe zoals een [`IconButton`](./icon#icon-buttons) aan de suffix van het item. Dit houdt de gebruikerservaring consistent en overzichtelijk.
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

Elke `AppNavItem` kan navigeren naar een interne weergave of een externe link. Je kunt dit definiëren met statische paden of geregistreerde weergaveklassen.

### Statische paden {#static-paths}

Gebruik stringpaden om links direct te definiëren:

```java
AppNavItem docs = new AppNavItem("Documentatie", "/docs");
AppNavItem help = new AppNavItem("Hulp", "https://support.example.com");
```

### Geregistreerde weergaven {#registered-views}

Als je weergaven zijn geregistreerd bij de [router](../routing/overview), kun je de klasse doorgeven in plaats van een hardcoded URL:

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

- **`SELF`** (standaard): Opent in de huidige weergave.
- **`BLANK`**: Opent in een nieuw tabblad of venster.
- **`PARENT`**: Opent in de bovenliggende browsecontext.
- **`TOP`**: Opent in de topniveau browsecontext.

```java
AppNavItem help = new AppNavItem("Hulp", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix en suffix {#prefix-and-suffix}

`AppNavItem` ondersteunt prefix- en suffix-componenten. Gebruik deze om visuele duidelijkheid te bieden met iconen, badges of knoppen.

- **Prefix**: verschijnt vóór het label, nuttig voor iconen.
- **Suffix**: verschijnt na het label, geweldig voor badges of acties.

```java
AppNavItem notifications = new AppNavItem("Waarschuwingen");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Auto-openende groepen {#auto-opening-groups}

Gebruik `setAutoOpen(true)` op de `AppNav` component om automatisch geneste groepen uit te vouwen wanneer de app wordt vernieuwd.

```java
nav.setAutoOpen(true);
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
