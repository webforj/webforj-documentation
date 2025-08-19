---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 5075588c497508fc77d7f76b1b412cf4
---
Naast het [registreren van routes met de `@Route` annotaties](./defining-routes), is het mogelijk om routes dynamisch te registreren, bij te werken of te verwijderen tijdens runtime op basis van app-logica, gebruikersrollen of andere voorwaarden. Deze flexibiliteit stelt je in staat om navigatie dynamischer te beheren, in plaats van routes statisch te definiëren tijdens het compileren.

## Dynamisch routes registreren {#registering-routes-dynamically}

Je kunt een route dynamisch registreren met de `RouteRegistry` klasse, die toegankelijk is via de `Router`. Dit stelt je in staat om nieuwe routes tijdens runtime toe te voegen, waardoor flexibele navigatie mogelijk is.

### Voorbeeld: Dynamisch een route registreren {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer de instellingenroute dynamisch
registry.register("/settings", SettingsView.class);

// Navigeer naar de instellingenweergave
router.navigate(SettingsView.class);
```

In dit voorbeeld wordt de `/settings` route dynamisch geregistreerd en navigeert de app naar de nieuw geregistreerde weergave.

## Voorwaardelijke route registratie {#conditional-route-registration}

Vaak moeten routes worden toegevoegd of verwijderd op basis van specifieke voorwaarden, zoals gebruikersrollen of de staat van de app. Met dynamische routing kun je routes voorwaardelijk registreren of deregistreren tijdens runtime.

### Voorbeeld: Voorwaardelijke registratie op basis van gebruikersrol {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Controleer de gebruikersrol en registreer de juiste routes
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

Net zoals routes dynamisch kunnen worden toegevoegd, kunnen ze ook tijdens runtime worden verwijderd wanneer ze niet meer nodig zijn, of wanneer de context van de app verandert.

### Voorbeeld: Een geregistreerde route verwijderen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Verwijder de route voor de instellingenweergave
registry.unregister("/settings");

// Optioneel, verwijderen op basis van componentklasse
registry.unregister(SettingsView.class);
```

In dit voorbeeld wordt de `/settings` route dynamisch verwijderd wanneer deze niet langer vereist is.

## Routes registreren bij app-opstart {#registering-routes-at-app-startup}

Je kunt dynamische routes registreren tijdens de initialisatie van de app, zodat bepaalde weergaven beschikbaar zijn op basis van de omgeving of configuratie bij opstart.

### Voorbeeld: Routes registreren tijdens de app-opstart {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registreer een debugweergave alleen in de ontwikkelmodus
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In dit voorbeeld:
- Een `DebugView` wordt dynamisch geregistreerd tijdens de opstart van de app, maar alleen als de app draait in de ontwikkelmodus.

## Dynamisch `@Route` geannoteerde componenten registreren {#registering-route-annotated-components-dynamically}

Naast het handmatig definiëren van routes, is het mogelijk om componenten die al zijn geannoteerd met `@Route` dynamisch te registreren. Dit is nuttig wanneer je vooraf geannoteerde klassen wilt gebruiken, maar ze dynamisch wilt registreren op basis van app-logica.

### Voorbeeld: Een `@Route` geannoteerde component registreren {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Logica voor profielweergave
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer de ProfileView dynamisch met zijn @Route annotatie
registry.register(ProfileView.class);

// Navigeer naar de ProfileView
router.navigate(ProfileView.class);
```

In dit voorbeeld:
- De `ProfileView` klasse is geannoteerd met `@Route("profile")`.
- De route wordt dynamisch geregistreerd tijdens runtime met `registry.register(ProfileView.class)`.

## Routes registreren vanuit een hele package {#registering-routes-from-an-entire-package}

Als je app een groot aantal routes bevat die zijn georganiseerd binnen een package, kun je alle `@Route`-geannoteerde componenten uit de package dynamisch registreren.

### Voorbeeld: Alle routes uit een package registreren {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer alle routes binnen de "com.myapp.admin" package
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In dit voorbeeld:
- De `ofPackage` methode scant de `com.myapp.admin` package en registreert alle klassen die zijn geannoteerd met `@Route`.
- Dit is bijzonder nuttig voor grote applicaties met talrijke routes georganiseerd per package.

## Geregistreerde routes ophalen {#retrieving-registered-routes}

Om een lijst van alle dynamisch geregistreerde routes op te halen, gebruik je de `RouteRegistry` klasse. Dit is nuttig wanneer je beschikbaar routes programmatisch wilt beheren of weergeven.

### Voorbeeld: Alle geregistreerde routes ophalen en weergeven {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

In dit voorbeeld haalt de app alle momenteel geregistreerde routes op en print hun paden.

## Dynamisch route-aliases beheren {#managing-route-aliases-dynamically}

webforJ laat je toe om meerdere aliassen voor een enkele weergave te registreren. Dit betekent dat gebruikers dezelfde weergave kunnen openen via verschillende URL-paden.

### Voorbeeld: Dynamisch route-aliases registreren {#example-registering-route-aliases-dynamically}

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
