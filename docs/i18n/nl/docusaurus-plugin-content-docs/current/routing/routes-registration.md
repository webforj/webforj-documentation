---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 0fadade88e7248bc679d489ed50b537d
---
Naast het [registreren van routes met de `@Route` annotaties](./defining-routes), is het mogelijk om routes dynamisch te registreren, bij te werken of te verwijderen tijdens runtime op basis van app-logica, gebruikersrollen of andere voorwaarden. Deze flexibiliteit stelt je in staat om navigatie dynamischer te beheren, in plaats van routes statisch te definiëren op compileertijd.

## Dynamisch registreren van routes {#registering-routes-dynamically}

Je kunt een route dynamisch registreren met de `RouteRegistry` klasse, die toegankelijk is via de `Router`. Dit stelt je in staat om nieuwe routes toe te voegen tijdens runtime, wat flexibele navigatie mogelijk maakt.

### Voorbeeld: Een dynamische route registreren {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer de instellingenroute dynamisch
registry.register("/settings", SettingsView.class);

// Navigeer naar de instellingenweergave
router.navigate(SettingsView.class);
```

In dit voorbeeld wordt de `/settings` route dynamisch geregistreerd en navigeert de app naar de nieuw geregistreerde weergave.

## Voorwaardelijke route-registratie {#conditional-route-registration}

Vaak moeten routes worden toegevoegd of verwijderd op basis van specifieke voorwaarden, zoals gebruikersrollen of de status van de app. Met dynamische routing kun je routes voorwaardelijk registreren of deregistreren tijdens runtime.

### Voorbeeld: Voorwaardelijke registratie op basis van gebruikersrol {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Controleer gebruikersrol en registreer geschikte routes
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navigeer naar het juiste dashboard
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

In dit voorbeeld:
- De `/editor/dashboard` of `/viewer/dashboard` route wordt dynamisch geregistreerd op basis van de rol van de gebruiker.
- De app navigeert naar het juiste dashboard op basis van de toegangsrechten van de gebruiker.

## Routes verwijderen {#removing-routes}

Net zoals routes dynamisch kunnen worden toegevoegd, kunnen ze ook worden verwijderd tijdens runtime wanneer ze niet langer nodig zijn, of wanneer de context van de app verandert.

### Voorbeeld: Een geregistreerde route verwijderen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Verwijder de route voor de instellingenweergave
registry.unregister("/settings");

// Optioneel: verwijder op componentklasse
registry.unregister(SettingsView.class);
```

In dit voorbeeld wordt de `/settings` route dynamisch verwijderd wanneer deze niet langer vereist is.

## Registreren van routes bij app-opstarten {#registering-routes-at-app-startup}

Je kunt dynamische routes registreren tijdens de initialisatie van de app, zodat bepaalde weergaven beschikbaar zijn op basis van de omgeving of configuratie bij opstarten.

### Voorbeeld: Registreren van routes tijdens app-opstarten {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registreer een debug-weergave alleen in de ontwikkelingsmodus
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In dit voorbeeld:
- Een `DebugView` wordt dynamisch geregistreerd tijdens de opstart van de app, maar alleen als de app draait in de ontwikkelingsmodus.

## Dynamisch registreren van `@Route` geannoteerde componenten {#registering-route-annotated-components-dynamically}

Naast het handmatig definiëren van routes, is het mogelijk om componenten die al zijn geannoteerd met `@Route` dynamisch te registreren. Dit is nuttig wanneer je gebruik wilt maken van vooraf geannoteerde klassen maar deze dynamisch wilt registreren op basis van app-logica.

### Voorbeeld: Een `@Route` geannoteerde component registreren {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Logica voor de profielweergave
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer de ProfileView dynamisch met zijn @Route annotatie
registry.register(ProfileView.class);

// Navigeer naar de ProfileView
router.navigate(ProfileView.class);
```

In dit voorbeeld:
- De klasse `ProfileView` is geannoteerd met `@Route("profile")`.
- De route wordt dynamisch geregistreerd tijdens runtime met `registry.register(ProfileView.class)`.

## Routes registreren vanuit een geheel pakket {#registering-routes-from-an-entire-package}

Als je app een groot aantal routes heeft die zijn georganiseerd binnen een pakket, kun je alle `@Route`-geannoteerde componenten uit het pakket dynamisch registreren.

### Voorbeeld: Registreren van alle routes uit een pakket {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer alle routes binnen het "com.myapp.admin" pakket
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In dit voorbeeld:
- De methode `ofPackage` scant het `com.myapp.admin` pakket en registreert alle klassen die zijn geannoteerd met `@Route`.
- Dit is bijzonder nuttig voor grote applicaties met talrijke routes die in pakketten zijn georganiseerd.

:::info Aangepaste route-ontdekking
Met versie 25.11 kunnen integratiekaders hun eigen route-ontdekkingsmechanisme bieden via de `RouteRegistryProvider` SPI. Dit maakt kader-specifieke functies mogelijk, zoals dependency injection voor dynamisch geregistreerde routes. Zie [Route Registry Provider](/docs/advanced/route-registry-provider) voor meer details.
:::

## Opgehaalde geregistreerde routes {#retrieving-registered-routes}

Om een lijst van alle dynamisch geregistreerde routes op te halen, gebruik je de `RouteRegistry` klasse. Dit is nuttig wanneer je beschikbaar routes programmatisch wilt beheren of weergeven.

### Voorbeeld: Alle geregistreerde routes ophalen en weergeven {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

In dit voorbeeld haalt de app alle momenteel geregistreerde routes op en print hun paden.

## Dynamisch beheren van route-aliasen {#managing-route-aliases-dynamically}

webforJ stelt je in staat om meerdere aliassen voor één enkele weergave te registreren. Dit betekent dat gebruikers dezelfde weergave kunnen openen met verschillende URL-paden.

### Voorbeeld: Dynamisch registreren van route-aliasen {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer een primaire route
registry.register("/contact", ContactView.class);

// Registreer aliassen voor de contactweergave
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In dit voorbeeld is de `ContactView` toegankelijk via drie verschillende paden: `/contact`, `/support` en `/help`.
