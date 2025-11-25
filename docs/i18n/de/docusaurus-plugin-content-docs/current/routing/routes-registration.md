---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 0fadade88e7248bc679d489ed50b537d
---
Zusätzlich zur [Registrierung von Routen mit den `@Route`-Annotations](./defining-routes) ist es möglich, Routen zur Laufzeit dynamisch zu registrieren, zu aktualisieren oder zu entfernen, basierend auf der Logik der Anwendung, Benutzerrollen oder anderen Bedingungen. Diese Flexibilität ermöglicht es Ihnen, die Navigation dynamischer zu verwalten, anstatt Routen statisch zur Kompilierzeit zu definieren.

## Dynamische Routenregistrierung {#registering-routes-dynamically}

Sie können eine Route dynamisch mit der Klasse `RouteRegistry` registrieren, die über den `Router` zugänglich ist. Dadurch können Sie während der Laufzeit neue Routen hinzufügen, was eine flexible Navigation ermöglicht.

### Beispiel: Dynamische Route registrieren {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie die Einstellungen-Route dynamisch
registry.register("/settings", SettingsView.class);

// Navigieren Sie zur Einstellungen-Ansicht
router.navigate(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch registriert und die App navigiert zur neu registrierten Ansicht.

## Bedingte Routenregistrierung {#conditional-route-registration}

Oftmals müssen Routen basierend auf spezifischen Bedingungen wie Benutzerrollen oder dem Zustand der Anwendung hinzugefügt oder entfernt werden. Mit dynamischem Routing können Sie Routen zur Laufzeit bedingt registrieren oder abmelden.

### Beispiel: Bedingte Registrierung basierend auf der Benutzerrolle {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Überprüfen Sie die Benutzerrolle und registrieren Sie geeignete Routen
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navigieren Sie zum entsprechenden Dashboard
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

In diesem Beispiel:
- Die Route `/editor/dashboard` oder `/viewer/dashboard` wird dynamisch basierend auf der Rolle des Benutzers registriert.
- Die App navigiert zum entsprechenden Dashboard basierend auf den Zugriffsrechten des Benutzers.

## Routen entfernen {#removing-routes}

Genauso wie Routen dynamisch hinzugefügt werden können, können sie auch zur Laufzeit entfernt werden, wenn sie nicht mehr benötigt werden oder sich der Kontext der Anwendung ändert.

### Beispiel: Entfernen einer registrierten Route {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Entfernen Sie die Route für die Einstellungen-Ansicht
registry.unregister("/settings");

// Optional, entfernen Sie nach Komponentenklasse
registry.unregister(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch entfernt, wenn sie nicht mehr erforderlich ist.

## Routen bei der App-Initialisierung registrieren {#registering-routes-at-app-startup}

Sie können dynamische Routen während der Initialisierung der App registrieren, sodass bestimmte Ansichten basierend auf der Umgebung oder Konfiguration beim Start verfügbar sind.

### Beispiel: Routen während des App-Starts registrieren {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registrieren Sie eine Debug-Ansicht nur im Entwicklungsmodus
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In diesem Beispiel:
- Eine `DebugView` wird während des App-Starts dynamisch registriert, aber nur wenn die App im Entwicklungsmodus ausgeführt wird.

## Dynamisches Registrieren von `@Route`-annotierten Komponenten {#registering-route-annotated-components-dynamically}

Neben der manuellen Definition von Routen ist es möglich, bereits mit `@Route` annotierte Komponenten dynamisch zu registrieren. Dies ist nützlich, wenn Sie bereits annotierte Klassen verwenden möchten, diese aber dynamisch basierend auf der Logik der App registrieren möchten.

### Beispiel: Registrieren einer `@Route`-annotierten Komponente {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Logik der Profilansicht
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie die ProfileView dynamisch mit ihrer @Route-Annotation
registry.register(ProfileView.class);

// Navigieren Sie zur ProfileView
router.navigate(ProfileView.class);
```

In diesem Beispiel:
- Die Klasse `ProfileView` ist mit `@Route("profile")` annotiert.
- Die Route wird zur Laufzeit dynamisch mit `registry.register(ProfileView.class)` registriert.

## Routen aus einem gesamten Paket registrieren {#registering-routes-from-an-entire-package}

Wenn Ihre App eine große Anzahl von Routen hat, die innerhalb eines Pakets organisiert sind, können Sie alle `@Route`-annotierten Komponenten aus dem Paket dynamisch registrieren.

### Beispiel: Registrieren aller Routen aus einem Paket {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie alle Routen im Paket "com.myapp.admin"
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In diesem Beispiel:
- Die Methode `ofPackage` durchsucht das Paket `com.myapp.admin` und registriert alle Klassen, die mit `@Route` annotiert sind.
- Dies ist besonders nützlich für große Anwendungen mit zahlreichen Routen, die nach Paketen organisiert sind.

:::info Benutzerdefinierte Routenentdeckung
Seit 25.11 können Integrationsframeworks ihren eigenen Routenentdeckungsmechanismus über die `RouteRegistryProvider` SPI bereitstellen. Dies ermöglicht framework-spezifische Funktionen wie Dependency Injection für dynamisch registrierte Routen. Siehe [Route Registry Provider](/docs/advanced/route-registry-provider) für Einzelheiten.
:::

## Registrierte Routen abrufen {#retrieving-registered-routes}

Um eine Liste aller dynamisch registrierten Routen abzurufen, verwenden Sie die Klasse `RouteRegistry`. Dies ist nützlich, wenn Sie verfügbare Routen programmgesteuert verwalten oder anzeigen müssen.

### Beispiel: Abrufen und Anzeigen aller registrierten Routen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Pfad: " + route.getPath()));
```

In diesem Beispiel ruft die App alle derzeit registrierten Routen ab und gibt deren Pfade aus.

## Dynamisches Verwalten von Routenaliasen {#managing-route-aliases-dynamically}

webforJ ermöglicht es Ihnen, mehrere Aliase für eine einzige Ansicht zu registrieren. Das bedeutet, dass Benutzer auf dieselbe Ansicht über verschiedene URL-Pfade zugreifen können.

### Beispiel: Dynamisches Registrieren von Routenaliasen {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie eine primäre Route
registry.register("/contact", ContactView.class);

// Registrieren Sie Aliase für die Kontaktansicht
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In diesem Beispiel ist die `ContactView` über drei unterschiedliche Pfade zugänglich: `/contact`, `/support` und `/help`.
