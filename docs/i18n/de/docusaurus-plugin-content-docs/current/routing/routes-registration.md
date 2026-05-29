---
sidebar_position: 11
title: Routes Registration
_i18n_hash: def139d3db58322c269afef10acdf5fd
---
Neben der [Registrierung von Routen mit den `@Route`-Anmerkungen](./defining-routes) ist es möglich, Routen zur Laufzeit dynamisch zu registrieren, zu aktualisieren oder zu entfernen, basierend auf der Anwendungslogik, Benutzerrollen oder anderen Bedingungen. Diese Flexibilität ermöglicht es Ihnen, die Navigation dynamischer zu verwalten, anstatt Routen zur Compile-Zeit statisch zu definieren.

## Dynamisches Registrieren von Routen {#registering-routes-dynamically}

Sie können eine Route dynamisch mit der Klasse `RouteRegistry` registrieren, die über den `Router` zugänglich ist. Dadurch können Sie während der Laufzeit neue Routen hinzufügen, was eine flexible Navigation ermöglicht.

### Beispiel: Dynamisches Registrieren einer Route {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Die Einstellungen-Route dynamisch registrieren
registry.register("/settings", SettingsView.class);

// Zur Einstellungen-Ansicht navigieren
router.navigate(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch registriert, und die App navigiert zur neu registrierten Ansicht.

## Bedingte Routenregistrierung {#conditional-route-registration}

Oft müssen Routen basierend auf bestimmten Bedingungen wie Benutzerrollen oder dem Zustand der App hinzugefügt oder entfernt werden. Mit dynamischem Routing können Sie Routen zur Laufzeit bedingt registrieren oder abmelden.

### Beispiel: Bedingte Registrierung basierend auf der Benutzerrolle {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Benutzerrolle überprüfen und entsprechende Routen registrieren
if (user.hasRole("editor")) {
  registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Zur entsprechenden Dashboard navigieren
if (user.hasRole("editor")) {
  router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  router.navigate(ViewerDashboardView.class);
}
```

In diesem Beispiel:
- Die Route `/editor/dashboard` oder `/viewer/dashboard` wird basierend auf der Benutzerrolle dynamisch registriert.
- Die App navigiert zum entsprechenden Dashboard basierend auf den Zugriffsrechten des Benutzers.

## Routen entfernen {#removing-routes}

Ebenso wie Routen dynamisch hinzugefügt werden können, können sie auch zur Laufzeit entfernt werden, wenn sie nicht mehr benötigt werden oder sich der Kontext der App ändert.

### Beispiel: Entfernen einer registrierten Route {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Die Route für die Einstellungen-Ansicht entfernen
registry.unregister("/settings");

// Optional nach Komponentenklasse entfernen
registry.unregister(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch entfernt, wenn sie nicht mehr benötigt wird.

## Routen beim App-Startup registrieren {#registering-routes-at-app-startup}

Sie können dynamische Routen während der App-Initialisierung registrieren, sodass bestimmte Ansichten basierend auf der Umgebung oder Konfiguration beim Startup verfügbar sind.

### Beispiel: Routen während des App-Startups registrieren {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Eine Debug-Ansicht nur im Entwicklungsmodus registrieren
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In diesem Beispiel:
- Eine `DebugView` wird während des App-Startups dynamisch registriert, aber nur, wenn die App im Entwicklungsmodus läuft.

## Dynamisches Registrieren von `@Route` annotierten Komponenten {#registering-route-annotated-components-dynamically}

Neben der manuellen Definition von Routen ist es möglich, bereits mit `@Route` annotierte Komponenten dynamisch zu registrieren. Dies ist nützlich, wenn Sie vorannotierte Klassen nutzen möchten, diese jedoch dynamisch basierend auf der Anwendungslogik registrieren möchten.

### Beispiel: Registrieren einer `@Route` annotierten Komponente {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
  // Logik der Profilansicht
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Dynamisches Registrieren der ProfileView mit ihrer @Route Annotation
registry.register(ProfileView.class);

// Zur ProfileView navigieren
router.navigate(ProfileView.class);
```

In diesem Beispiel:
- Die `ProfileView`-Klasse ist mit `@Route("profile")` annotiert.
- Die Route wird zur Laufzeit dynamisch mit `registry.register(ProfileView.class)` registriert.

## Routen aus einem gesamten Paket registrieren {#registering-routes-from-an-entire-package}

Wenn Ihre App eine große Anzahl von Routen enthält, die innerhalb eines Pakets organisiert sind, können Sie alle `@Route`-annotierten Komponenten aus diesem Paket dynamisch registrieren.

### Beispiel: Registrieren aller Routen aus einem Paket {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Alle Routen im Paket "com.myapp.admin" registrieren
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In diesem Beispiel:
- Die Methode `ofPackage` durchsucht das Paket `com.myapp.admin` und registriert alle Klassen, die mit `@Route` annotiert sind.
- Dies ist besonders nützlich für große Anwendungen mit zahlreichen Routen, die nach Paketen organisiert sind.

:::info Benutzerdefinierte Routenentdeckung
Beginnend mit 25.11 können Integrationsframeworks ihren eigenen Routenentdeckungsmechanismus über das `RouteRegistryProvider` SPI bereitstellen. Dies ermöglicht framework-spezifische Funktionen wie Dependency Injection für dynamisch registrierte Routen. Siehe [Route Registry Provider](/docs/advanced/route-registry-provider) für Details.
:::

## Abfragen registrierter Routen {#retrieving-registered-routes}

Um eine Liste aller dynamisch registrierten Routen abzurufen, verwenden Sie die Klasse `RouteRegistry`. Dies ist nützlich, wenn Sie verfügbar Routen programmatisch verwalten oder anzeigen müssen.

### Beispiel: Abrufen und Anzeigen aller registrierten Routen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Pfad: " + route.getPath()));
```

In diesem Beispiel ruft die App alle derzeit registrierten Routen ab und gibt deren Pfade aus.

## Dynamisches Verwalten von Routen-Aliassen {#managing-route-aliases-dynamically}

webforJ ermöglicht es Ihnen, mehrere Aliasse für eine einzige Ansicht zu registrieren. Das bedeutet, dass Benutzer dieselbe Ansicht über verschiedene URL-Pfade erreichen können.

### Beispiel: Dynamisches Registrieren von Routen-Aliassen {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Eine Hauptroute registrieren
registry.register("/contact", ContactView.class);

// Aliasse für die Kontaktansicht registrieren
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In diesem Beispiel ist die `ContactView` über drei verschiedene Pfade zugänglich: `/contact`, `/support` und `/help`.
