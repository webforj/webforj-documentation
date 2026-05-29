---
sidebar_position: 11
title: Routes Registration
_i18n_hash: def139d3db58322c269afef10acdf5fd
---
In aanvulling op [het registreren van routes met de `@Route` annotaties](./defining-routes), is het mogelijk om routes dynamisch te registreren, bij te werken of te verwijderen tijdens runtime op basis van app-logica, gebruikersrollen of andere voorwaarden. Deze flexibiliteit stelt je in staat om de navigatie dynamischer te beheren, in plaats van routes statisch te definiëren op compilatietijd.

## Dynamisch registreren van routes {#registering-routes-dynamically}

Je kunt een route dynamisch registreren met behulp van de `RouteRegistry`-klasse, die toegankelijk is via de `Router`. Dit maakt het mogelijk om nieuwe routes toe te voegen tijdens runtime, wat flexibele navigatie mogelijk maakt.

### Voorbeeld: Dynamisch registreren van een route {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer de instellingenroute dynamisch
registry.register("/settings", SettingsView.class);

// Navigeer naar de instellingenweergave
router.navigate(SettingsView.class);
```

In dit voorbeeld wordt de `/settings` route dynamisch geregistreerd, en de app navigeert naar de nieuw geregistreerde weergave.

## Voorwaardelijke route-registratie {#conditional-route-registration}

Vaak moeten routes worden toegevoegd of verwijderd op basis van specifieke voorwaarden, zoals gebruikersrollen of de status van de app. Met dynamische routing kun je routes voorwaardelijk registreren of verwijderen tijdens runtime.

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

Net zoals routes dynamisch kunnen worden toegevoegd, kunnen ze ook tijdens runtime worden verwijderd wanneer ze niet langer nodig zijn, of wanneer de context van de app verandert.

### Voorbeeld: Een geregistreerde route verwijderen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Verwijder de route voor de instellingenweergave
registry.unregister("/settings");

// Optioneel, verwijder op klasse-niveau
registry.unregister(SettingsView.class);
```

In dit voorbeeld wordt de `/settings` route dynamisch verwijderd wanneer deze niet langer vereist is.

## Routes registreren bij app-startup {#registering-routes-at-app-startup}

Je kunt dynamische routes registreren tijdens de initiatie van de app, zodat bepaalde weergaven beschikbaar zijn op basis van de omgeving of configuratie bij de opstart.

### Voorbeeld: Routes registreren tijdens app-startup {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registreer een debugweergave alleen in de ontwikkelingsmodus
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

Naast het handmatig definiëren van routes, is het mogelijk om componenten die al zijn geannoteerd met `@Route` dynamisch te registreren. Dit is nuttig wanneer je al geannoteerde klassen wilt gebruiken maar ze dynamisch wilt registreren op basis van app-logica.

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
- De `ProfileView` klasse is geannoteerd met `@Route("profile")`.
- De route wordt dynamisch geregistreerd tijdens runtime met behulp van `registry.register(ProfileView.class)`.

## Routes registreren vanuit een heel pakket {#registering-routes-from-an-entire-package}

Als je app een groot aantal routes heeft die zijn georganiseerd binnen een pakket, kun je alle `@Route`-geannoteerde componenten uit dat pakket dynamisch registreren.

### Voorbeeld: Alle routes uit een pakket registreren {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer alle routes binnen het "com.myapp.admin" pakket
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In dit voorbeeld:
- De `ofPackage` methode scant het `com.myapp.admin` pakket en registreert alle klassen die zijn geannoteerd met `@Route`.
- Dit is bijzonder nuttig voor grote applicaties met talrijke routes georganiseerd per pakket.

:::info Aangepaste route-ontdekking
Bij versies vanaf 25.11 kunnen integratiekaders hun eigen route-ontdekkingsmechanisme bieden via de `RouteRegistryProvider` SPI. Dit maakt kader-specifieke functies mogelijk, zoals afhankelijkheidsinjectie voor dynamisch geregistreerde routes. Zie [Route Registry Provider](/docs/advanced/route-registry-provider) voor meer details.
:::

## Geregistreerde routes ophalen {#retrieving-registered-routes}

Om een lijst van alle dynamisch geregistreerde routes op te halen, gebruik je de `RouteRegistry`-klasse. Dit is nuttig wanneer je routes programmatisch wilt beheren of weergeven.

### Voorbeeld: Alle geregistreerde routes ophalen en weergeven {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Pad: " + route.getPath()));
```

In dit voorbeeld haalt de app alle momenteel geregistreerde routes op en drukt hun paden af.

## Dynamisch beheren van route-aliases {#managing-route-aliases-dynamically}

webforJ stelt je in staat om meerdere aliassen voor een enkele weergave te registreren. Dit betekent dat gebruikers dezelfde weergave kunnen bereiken via verschillende URL-paden.

### Voorbeeld: Dynamisch registreren van route-aliases {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registreer een primaire route
registry.register("/contact", ContactView.class);

// Registreer aliassen voor de contactweergave
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In dit voorbeeld is de `ContactView` toegankelijk via drie verschillende paden: `/contact`, `/support`, en `/help`.
